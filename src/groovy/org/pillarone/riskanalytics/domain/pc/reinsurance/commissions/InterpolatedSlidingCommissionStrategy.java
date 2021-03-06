package org.pillarone.riskanalytics.domain.pc.reinsurance.commissions;

import org.pillarone.riskanalytics.core.parameterization.AbstractParameterObject;
import org.pillarone.riskanalytics.core.parameterization.ConstrainedMultiDimensionalParameter;
import org.pillarone.riskanalytics.core.parameterization.ConstraintsFactory;
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier;
import org.pillarone.riskanalytics.core.util.GroovyUtils;
import org.pillarone.riskanalytics.domain.pc.claims.Claim;
import org.pillarone.riskanalytics.domain.pc.underwriting.CededUnderwritingInfo;
import org.pillarone.riskanalytics.domain.utils.InputFormatConverter;
import org.pillarone.riskanalytics.domain.utils.constraint.DoubleConstraints;

import java.util.*;

/**
 * @author jessika.walter (at) intuitive-collaboration (dot) com
 */
public class InterpolatedSlidingCommissionStrategy extends AbstractParameterObject implements ICommissionStrategy {

    public static final String LOSS_RATIO = "Loss Ratio (from)";
    public static final String COMMISSION = "Commission rate";
    public static final int LOSS_RATIO_COLUMN_INDEX = 0;
    public static final int COMMISSION_COLUMN_INDEX = 1;

    private ConstrainedMultiDimensionalParameter commissionBands = new ConstrainedMultiDimensionalParameter(
            GroovyUtils.convertToListOfList(new Object[]{0d, 0d}),
            Arrays.asList(LOSS_RATIO, COMMISSION),
            ConstraintsFactory.getConstraints(DoubleConstraints.IDENTIFIER));

    private TreeMap<Double, List<Double>> commissionRatesPerLossRatio;


    public IParameterObjectClassifier getType() {
        return CommissionStrategyType.INTERPOLATEDSLIDINGCOMMISSION;
    }

    public Map getParameters() {
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put("commissionBands", commissionBands);
        return map;
    }

    public void calculateCommission(List<Claim> claims, List<CededUnderwritingInfo> underwritingInfos, boolean isFirstPeriod, boolean isAdditive) {
        double totalClaims = 0d;
        double totalPremium = 0d;
        for (Claim claim : claims) {
            totalClaims += claim.getUltimate();
        }
        for (CededUnderwritingInfo underwritingInfo : underwritingInfos) {
            totalPremium += underwritingInfo.getPremium();
        }
        double totalLossRatio = totalClaims / totalPremium;
        double commissionRate;
        if (commissionRatesPerLossRatio == null) initCommissionRates();
        double lowestEnteredLossRatio = commissionRatesPerLossRatio.firstKey();
        double highestEnteredLossRatio = commissionRatesPerLossRatio.lastKey();
        double fixedCommissionRate = commissionRatesPerLossRatio.get(highestEnteredLossRatio).get(0);
        if (totalLossRatio < lowestEnteredLossRatio) {
            List<Double> associatedCommissionRates = commissionRatesPerLossRatio.get(lowestEnteredLossRatio);
            commissionRate = associatedCommissionRates.get(associatedCommissionRates.size() - 1);
        }
        else if (commissionRatesPerLossRatio.containsKey(Math.min(totalLossRatio, highestEnteredLossRatio))) {
            commissionRate = commissionRatesPerLossRatio.get(Math.min(totalLossRatio, highestEnteredLossRatio)).get(0);
        }
        else {
            double leftLossRatio = commissionRatesPerLossRatio.floorKey(totalLossRatio);
            double rightLossRatio = commissionRatesPerLossRatio.higherKey(totalLossRatio);
            double leftCommissionValue = commissionRatesPerLossRatio.get(leftLossRatio).get(0);
            int size = commissionRatesPerLossRatio.get(rightLossRatio).size();
            double rightCommissionValue = commissionRatesPerLossRatio.get(rightLossRatio).get(size - 1);
            commissionRate = (rightLossRatio - totalLossRatio) / (rightLossRatio - leftLossRatio) * leftCommissionValue +
                    (totalLossRatio - leftLossRatio) / (rightLossRatio - leftLossRatio) * rightCommissionValue;
        }

        if (isAdditive) {
            for (CededUnderwritingInfo underwritingInfo : underwritingInfos) {
                double premiumWritten = underwritingInfo.getPremium();
                underwritingInfo.setCommission(underwritingInfo.getCommission() - premiumWritten * commissionRate);
                underwritingInfo.setFixedCommission(underwritingInfo.getFixedCommission() - premiumWritten * fixedCommissionRate);
                underwritingInfo.setVariableCommission(underwritingInfo.getVariableCommission() - premiumWritten * (commissionRate - fixedCommissionRate));
            }
        }
        else {
            for (CededUnderwritingInfo underwritingInfo : underwritingInfos) {
                double premiumWritten = underwritingInfo.getPremium();
                underwritingInfo.setCommission(-premiumWritten * commissionRate);
                underwritingInfo.setFixedCommission(-premiumWritten * fixedCommissionRate);
                underwritingInfo.setVariableCommission(-premiumWritten * (commissionRate - fixedCommissionRate));
            }
        }
    }

    public void initCommissionRates() {
        int numberOfEntries = commissionBands.getValueRowCount();
        commissionRatesPerLossRatio = new TreeMap<Double, List<Double>>();
        for (int i = 1; i <= numberOfEntries; i++) {
            double lossRatio = InputFormatConverter.getDouble(commissionBands.getValueAt(i, LOSS_RATIO_COLUMN_INDEX));
            double commissionRate = InputFormatConverter.getDouble(commissionBands.getValueAt(i, COMMISSION_COLUMN_INDEX));
            List<Double> listOfCommissionRates = new ArrayList<Double>();
            if (commissionRatesPerLossRatio.containsKey(lossRatio))
                listOfCommissionRates = commissionRatesPerLossRatio.get(lossRatio);
            listOfCommissionRates.add(commissionRate);
            Collections.sort(listOfCommissionRates);
            commissionRatesPerLossRatio.put(lossRatio, listOfCommissionRates);
        }
    }

    public TreeMap<Double, List<Double>> getCommissionRatesPerLossRatio() {
        return commissionRatesPerLossRatio;
    }

    public void setCommissionRatesPerLossRatio(TreeMap<Double, List<Double>> commissionRatesPerLossRatio) {
        this.commissionRatesPerLossRatio = commissionRatesPerLossRatio;
    }
}
