package models.mark

import org.pillarone.riskanalytics.core.model.StochasticModel
import org.pillarone.riskanalytics.domain.pc.generators.claims.SingleClaimsGenerator
import org.pillarone.riskanalytics.domain.pc.generators.frequency.FrequencyGenerator
import org.pillarone.riskanalytics.domain.pc.global.GlobalParameters

/**
 * @author: stefan.kunz (at) intuitive-collaboration (dot) com
 */
class MarkModel extends StochasticModel {

    GlobalParameters globalParameters
    FrequencyGenerator frequencyGenerator
    SingleClaimsGenerator claimsGeneratorFire
    SingleClaimsGenerator claimsGeneratorMotor

    void initComponents() {
        globalParameters = new GlobalParameters()
        frequencyGenerator = new FrequencyGenerator() //parmDistribution: DistributionType.getStrategy(FrequencyDistributionType.POISSON, ["lambda": 10]))
        claimsGeneratorFire = new SingleClaimsGenerator() //parmDistribution: DistributionType.getStrategy(ClaimSizeDistributionType.LOGNORMAL, ["mean": 5, "stDev": 10]))
        claimsGeneratorMotor = new SingleClaimsGenerator() //parmDistribution: DistributionType.getStrategy(ClaimSizeDistributionType.LOGNORMAL, ["mean": 5, "stDev": 10]))

        allComponents << frequencyGenerator
        allComponents << claimsGeneratorFire
        allComponents << claimsGeneratorMotor

        addStartComponent frequencyGenerator
    }

    void wireComponents() {
        claimsGeneratorFire.inClaimCount = frequencyGenerator.outFrequency
        claimsGeneratorMotor.inClaimCount = frequencyGenerator.outFrequency
    }
}