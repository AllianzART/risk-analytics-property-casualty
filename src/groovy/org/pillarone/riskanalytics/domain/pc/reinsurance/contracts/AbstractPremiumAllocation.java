package org.pillarone.riskanalytics.domain.pc.reinsurance.contracts;

import org.pillarone.riskanalytics.core.parameterization.AbstractParameterObject;
import org.pillarone.riskanalytics.domain.utils.marker.ISegmentMarker;
import org.pillarone.riskanalytics.domain.pc.underwriting.UnderwritingInfo;
import org.pillarone.riskanalytics.domain.pc.underwriting.UnderwritingInfoUtilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
abstract public class AbstractPremiumAllocation extends AbstractParameterObject implements IPremiumAllocationStrategy {

    protected Map<UnderwritingInfo, Double> cededPremiumSharePerGrossUnderwritingInfo = new HashMap<UnderwritingInfo, Double>();

    public double getShare(UnderwritingInfo grossUnderwritingInfo) {
        Double share = cededPremiumSharePerGrossUnderwritingInfo.get(grossUnderwritingInfo);
        return share == null ? 1d : share;
    }

    protected void initUnderwritingInfoShares(List<UnderwritingInfo> grossUnderwritingInfos, Map<ISegmentMarker, Double> segmentShares) {
        if (segmentShares.isEmpty()) {
            proportionalAllocation(grossUnderwritingInfos);
        }
        else {
            Map<ISegmentMarker, Double> segmentPremium = new HashMap<ISegmentMarker, Double>();
            for (UnderwritingInfo underwritingInfo : grossUnderwritingInfos) {
                Double aggregatedPremium = segmentPremium.get(underwritingInfo.getLineOfBusiness());
                if (aggregatedPremium == null) {
                    segmentPremium.put(underwritingInfo.getLineOfBusiness(), underwritingInfo.getPremium());
                }
                else {
                    segmentPremium.put(underwritingInfo.getLineOfBusiness(), underwritingInfo.getPremium() + aggregatedPremium);
                }
            }
            for (UnderwritingInfo underwritingInfo : grossUnderwritingInfos) {
                double premiumShareInSegment = underwritingInfo.getPremium() / segmentPremium.get(underwritingInfo.getLineOfBusiness());
                Double segmentShare = segmentShares.get(underwritingInfo.getLineOfBusiness());
                cededPremiumSharePerGrossUnderwritingInfo.put(underwritingInfo, premiumShareInSegment * (segmentShare == null ? 1 : segmentShare));
            }
        }
    }

    protected void proportionalAllocation(List<UnderwritingInfo> grossUnderwritingInfos) {
        cededPremiumSharePerGrossUnderwritingInfo = new HashMap<UnderwritingInfo, Double>();
        if (grossUnderwritingInfos.size() > 0) {
            double totalPremium = UnderwritingInfoUtilities.aggregate(grossUnderwritingInfos).getPremium();
            if (totalPremium == 0) {
                for (UnderwritingInfo underwritingInfo: grossUnderwritingInfos) {
                    cededPremiumSharePerGrossUnderwritingInfo.put(underwritingInfo, 0d);
                }
            }
            else {
                for (UnderwritingInfo underwritingInfo: grossUnderwritingInfos) {
                    cededPremiumSharePerGrossUnderwritingInfo.put(underwritingInfo, underwritingInfo.getPremium() / totalPremium);
                }
            }
        }
    }
}
