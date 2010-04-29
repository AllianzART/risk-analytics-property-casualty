package org.pillarone.riskanalytics.domain.pc.generators.claims

import org.pillarone.riskanalytics.core.parameterization.IParameterObject
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier
import org.pillarone.riskanalytics.domain.pc.constants.Exposure
import org.pillarone.riskanalytics.domain.pc.constants.FrequencyBase
import org.pillarone.riskanalytics.domain.pc.constants.FrequencySeverityClaimType
import org.pillarone.riskanalytics.domain.utils.DistributionModified
import org.pillarone.riskanalytics.domain.utils.DistributionModifier
import org.pillarone.riskanalytics.domain.utils.DistributionType
import org.pillarone.riskanalytics.domain.utils.RandomDistribution

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class FrequencySeverityClaimsGeneratorStrategy implements IParameterObject, IFrequencySingleClaimsGeneratorStrategy {

    FrequencyBase frequencyBase = FrequencyBase.ABSOLUTE
    RandomDistribution frequencyDistribution = DistributionType.getStrategy(DistributionType.CONSTANT, ['constant': 0d])
    DistributionModified frequencyModification = DistributionModifier.getStrategy(DistributionModifier.NONE, [:])
    Exposure claimsSizeBase = Exposure.ABSOLUTE
    RandomDistribution claimsSizeDistribution = DistributionType.getStrategy(DistributionType.CONSTANT, ['constant': 0d])
    DistributionModified claimsSizeModification = DistributionModifier.getStrategy(DistributionModifier.NONE, [:])
    FrequencySeverityClaimType produceClaim = FrequencySeverityClaimType.SINGLE

    public IParameterObjectClassifier getType() {
        return ClaimsGeneratorType.FREQUENCY_SEVERITY
    }

    public Map getParameters() {
        ['frequencyBase': frequencyBase,
                'frequencyDistribution': frequencyDistribution,
                'frequencyModification': frequencyModification,
                'claimsSizeBase': claimsSizeBase,
                'claimsSizeDistribution': claimsSizeDistribution,
                'claimsSizeModification': claimsSizeModification,
                'produceClaim': produceClaim]
    }

    public FrequencySeverityClaimType getProduceClaim() {
        produceClaim
    }
}
