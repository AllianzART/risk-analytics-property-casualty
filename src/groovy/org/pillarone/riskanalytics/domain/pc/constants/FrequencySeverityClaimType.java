package org.pillarone.riskanalytics.domain.pc.constants;

import java.util.Map;

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public enum FrequencySeverityClaimType {
    SINGLE, AGGREGATED_EVENT;

    public Object getConstructionString(Map parameters) {
        return getClass().getName() + "." + this;
    }
}