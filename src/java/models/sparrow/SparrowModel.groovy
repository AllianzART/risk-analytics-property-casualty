package models.sparrow

import org.pillarone.riskanalytics.core.model.StochasticModel
import org.pillarone.riskanalytics.domain.pc.generators.claims.SingleClaimsGenerator
import org.pillarone.riskanalytics.domain.pc.generators.frequency.FrequencyGenerator
import org.pillarone.riskanalytics.domain.pc.global.GlobalParameters

/**
 * @author: stefan.kunz (at) intuitive-collaboration (dot) com
 */
class SparrowModel extends StochasticModel {

    GlobalParameters globalParameters
    FrequencyGenerator frequencyGenerator
    SingleClaimsGenerator claimsGenerator

    void initComponents() {
        globalParameters = new GlobalParameters()
        frequencyGenerator = new FrequencyGenerator()
        claimsGenerator = new SingleClaimsGenerator()
        addStartComponent frequencyGenerator
    }

    void wireComponents() {
        claimsGenerator.inClaimCount = frequencyGenerator.outFrequency
    }
}