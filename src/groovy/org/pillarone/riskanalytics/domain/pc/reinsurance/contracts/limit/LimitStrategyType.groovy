package org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.limit

import org.pillarone.riskanalytics.core.parameterization.AbstractParameterObjectClassifier
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier
import org.pillarone.riskanalytics.core.parameterization.IParameterObject

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
class LimitStrategyType extends AbstractParameterObjectClassifier {

    public static final LimitStrategyType NONE = new LimitStrategyType("none", "NONE", [:])
    public static final LimitStrategyType AAL = new LimitStrategyType("AAL", "AAL", ['aal': 0d])
    public static final LimitStrategyType AAD = new LimitStrategyType("AAD", "AAD", ['aad': 0d])
    public static final LimitStrategyType AALAAD = new LimitStrategyType("AAL, AAD", "AALAAD", ['aal': 0d, 'aad': 0d])
    public static final LimitStrategyType EVENTLIMIT = new LimitStrategyType("event", "EVENT", ['eventLimit': 0d])
    public static final LimitStrategyType EVENTLIMITAAL = new LimitStrategyType("event, AAL", "EVENTLIMITAAL", ['aal': 0d, 'eventLimit': 0d])

    public static final all = [NONE, AAL, AAD, AALAAD, EVENTLIMIT, EVENTLIMITAAL]

    protected static Map types = [:]
    static {
        LimitStrategyType.all.each {
            LimitStrategyType.types[it.toString()] = it
        }
    }

    private LimitStrategyType(String displayName, String typeName, Map parameters) {
        super(displayName, typeName, parameters)
    }


    public static LimitStrategyType valueOf(String type) {
        types[type]
    }

    public List<IParameterObjectClassifier> getClassifiers() {
        all
    }

    public IParameterObject getParameterObject(Map parameters) {
        getStrategy(this, parameters)
    }

    public static ILimitStrategy getNoLimit() {
        getStrategy(LimitStrategyType.NONE, [:])
    }

    public static ILimitStrategy getStrategy(LimitStrategyType type, Map parameters) {
        ILimitStrategy limitStrategy ;
        switch (type) {
            case LimitStrategyType.NONE:
                limitStrategy = new NoneLimitStrategy()
                break
            case LimitStrategyType.AAL:
                limitStrategy = new AalLimitStrategy(aal: (Double) parameters['aal'])
                break
            case LimitStrategyType.AAD:
                limitStrategy = new AadLimitStrategy(aad: (Double) parameters['aad'])
                break
            case LimitStrategyType.AALAAD:
                limitStrategy = new AalAadLimitStrategy(aal: (Double) parameters['aal'], aad: (Double) parameters['aad'])
                break
            case LimitStrategyType.EVENTLIMIT:
                limitStrategy = new EventLimitStrategy(eventLimit: (Double) parameters['eventLimit'])
                break
            case LimitStrategyType.EVENTLIMITAAL:
                limitStrategy = new EventAalLimitStrategy(eventLimit: (Double) parameters['eventLimit'], aal: (Double) parameters['aal'])
                break
        }
        limitStrategy;
    }

    public String getConstructionString(Map parameters) {
        StringBuffer parameterString = new StringBuffer('[')
        parameters.each {k, v ->
            if (v.class.isEnum()) {
                parameterString << "\"$k\":${v.class.name}.$v,"
            }
            else if (v instanceof IParameterObject) {
                parameterString << "\"$k\":${v.type.getConstructionString(v.parameters)},"
            }
            else {
                parameterString << "\"$k\":$v,"
            }
        }
        if (parameterString.size() == 1) {
            parameterString << ':'
        }
        parameterString << ']'
        "org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.limit.LimitStrategyType.getStrategy(${this.class.name}.${typeName.toUpperCase()}, ${parameterString})"
    }
}