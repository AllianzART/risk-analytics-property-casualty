package org.pillarone.riskanalytics.domain.pc.reinsurance.commissions

import org.pillarone.riskanalytics.core.parameterization.*
import org.pillarone.riskanalytics.domain.utils.constraint.DoubleConstraints
import org.pillarone.riskanalytics.core.simulation.InvalidParameterException

/**
 * Defines the commission on a reinsurance contract or a commission component.
 *
 * @author shartmann (at) munichre (dot) com
 */
public class CommissionStrategyType extends AbstractParameterObjectClassifier {

    public static final CommissionStrategyType NOCOMMISSION = new CommissionStrategyType("no commission", "NOCOMMISSION", [:])
    public static final CommissionStrategyType FIXEDCOMMISSION = new CommissionStrategyType("fixed commission", "FIXEDCOMMISSION", ['commission':0d])
    public static final CommissionStrategyType SLIDINGCOMMISSION = new CommissionStrategyType("sliding commission", "SLIDINGCOMMISSION",
           ['commissionBands': new ConstrainedMultiDimensionalParameter(
                        [[0d], [0d]],
                        [SlidingCommissionStrategy.LOSS_RATIO, SlidingCommissionStrategy.COMMISSION],
                        ConstraintsFactory.getConstraints(DoubleConstraints.IDENTIFIER))])
    public static final CommissionStrategyType INTERPOLATEDSLIDINGCOMMISSION = new CommissionStrategyType("interpolated sliding commission", "INTERPOLATEDSLIDINGCOMMISSION",
           ['commissionBands': new ConstrainedMultiDimensionalParameter(
                        [[0d], [0d]],
                        [SlidingCommissionStrategy.LOSS_RATIO, SlidingCommissionStrategy.COMMISSION],
                        ConstraintsFactory.getConstraints(DoubleConstraints.IDENTIFIER))])
    public static final CommissionStrategyType PROFITCOMMISSION = new CommissionStrategyType("profit commission", "PROFITCOMMISSION",
            ['profitCommissionRatio':0d, 'commissionRatio':0d, 'costRatio':0d, 'lossCarriedForwardEnabled':true, 'initialLossCarriedForward':0d])

    public static final all = [NOCOMMISSION, FIXEDCOMMISSION, SLIDINGCOMMISSION, INTERPOLATEDSLIDINGCOMMISSION, PROFITCOMMISSION]

    protected static Map types = [:]
    static {
        CommissionStrategyType.all.each {
            CommissionStrategyType.types[it.toString()] = it
        }
    }

    private CommissionStrategyType(String displayName, String typeName, Map parameters) {
        super(displayName, typeName, parameters)
    }


    public static CommissionStrategyType valueOf(String type) {
        types[type]
    }

    public List<IParameterObjectClassifier> getClassifiers() {
        all
    }

    public IParameterObject getParameterObject(Map parameters) {
        getStrategy(this, parameters)
    }

    public static ICommissionStrategy getNoCommission() {
        getStrategy(CommissionStrategyType.NOCOMMISSION, [:])
    }

    public static ICommissionStrategy getStrategy(CommissionStrategyType type, Map parameters) {
        ICommissionStrategy commissionStrategy;
        switch (type) {
            case CommissionStrategyType.NOCOMMISSION:
                commissionStrategy = new NoCommissionStrategy()
                break;
            case CommissionStrategyType.FIXEDCOMMISSION:
                commissionStrategy = new FixedCommissionStrategy(
                        commission : (Double) parameters['commission'])
                break;
            case CommissionStrategyType.PROFITCOMMISSION:
                commissionStrategy = new ProfitCommissionStrategy(
                        profitCommissionRatio : (Double) parameters['profitCommissionRatio'],
                        commissionRatio : (Double) parameters['commissionRatio'],
                        costRatio : (Double) parameters['costRatio'],
                        lossCarriedForwardEnabled : (Boolean) parameters['lossCarriedForwardEnabled'],
                        initialLossCarriedForward : (Double) parameters['initialLossCarriedForward'])
                break;
            case CommissionStrategyType.SLIDINGCOMMISSION:
                commissionStrategy = new SlidingCommissionStrategy(
                        commissionBands: (ConstrainedMultiDimensionalParameter) parameters['commissionBands'])
                break;
            case CommissionStrategyType.INTERPOLATEDSLIDINGCOMMISSION:
                commissionStrategy = new InterpolatedSlidingCommissionStrategy(
                        commissionBands: (ConstrainedMultiDimensionalParameter) parameters['commissionBands'])
                break;
            default:
                throw new InvalidParameterException("CommissionStrategyType $type not implemented")
        }
        return commissionStrategy;
    }
}