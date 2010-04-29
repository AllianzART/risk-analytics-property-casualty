package org.pillarone.riskanalytics.domain.pc.generators.claims

import org.pillarone.riskanalytics.core.components.ComposedComponent
import org.pillarone.riskanalytics.core.packets.PacketList
import org.pillarone.riskanalytics.core.wiring.PortReplicatorCategory
import org.pillarone.riskanalytics.core.wiring.WireCategory
import org.pillarone.riskanalytics.core.wiring.WiringUtils
import org.pillarone.riskanalytics.domain.pc.claims.Claim
import org.pillarone.riskanalytics.domain.pc.generators.frequency.Frequency
import org.pillarone.riskanalytics.domain.pc.generators.frequency.FrequencyExtractor
import org.pillarone.riskanalytics.domain.pc.underwriting.UnderwritingInfo

/**
 * @author michael-noe (at) web (dot) de, stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class SingleClaimsGeneratorWithFrequencyExtractor extends ComposedComponent {

    PacketList<Frequency> inFrequency = new PacketList(Frequency)
    PacketList<UnderwritingInfo> inUnderwritingInfo = new PacketList(UnderwritingInfo)
    PacketList<Claim> outClaims = new PacketList(Claim)

    FrequencyExtractor subFrequencyExtractor
    SingleClaimsGenerator subClaimsGenerator

    SingleClaimsGeneratorWithFrequencyExtractor(int extractElement) {
        subFrequencyExtractor = new FrequencyExtractor(extractElement)
        subClaimsGenerator = new SingleClaimsGenerator()
    }

    protected void doCalculation() {
        assert "number of frequency packets has to correspond to the number of underwriting info packets", _
        inFrequency.size() == inUnderwritingInfo.size()
        super.doCalculation()
    }

    public void wire() {
        WiringUtils.use(WireCategory) {
            subClaimsGenerator.inClaimCount = subFrequencyExtractor.outFrequency
        }
        WiringUtils.use(PortReplicatorCategory) {
            subFrequencyExtractor.inFrequency = this.inFrequency
            subClaimsGenerator.inUnderwritingInfo = this.inUnderwritingInfo
            this.outClaims = subClaimsGenerator.outClaims
        }
    }
}
