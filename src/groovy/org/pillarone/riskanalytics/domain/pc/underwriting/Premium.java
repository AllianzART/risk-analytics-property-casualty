package org.pillarone.riskanalytics.domain.pc.underwriting;

import org.pillarone.riskanalytics.core.packets.SingleValuePacket;

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class Premium extends SingleValuePacket {

    public String getValueLabel() {
        return "premium";
    }
}