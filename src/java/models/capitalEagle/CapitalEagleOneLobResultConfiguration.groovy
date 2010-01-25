package models.capitalEagle

import org.pillarone.riskanalytics.core.output.AggregatedCollectingModeStrategy

model=models.capitalEagle.CapitalEagleOneLobModel
displayName='Claims'

components.mtpl.subRiProgram.outClaimsGross=AggregatedCollectingModeStrategy.IDENTIFIER
components.mtpl.subRiProgram.outClaimsNet=AggregatedCollectingModeStrategy.IDENTIFIER
components.mtpl.subRiProgram.subContract1.outCoveredClaims=AggregatedCollectingModeStrategy.IDENTIFIER
components.mtpl.subRiProgram.subContract2.outCoveredClaims=AggregatedCollectingModeStrategy.IDENTIFIER
components.mtpl.subRiProgram.subContract3.outCoveredClaims=AggregatedCollectingModeStrategy.IDENTIFIER
components.mtpl.subClaimsGenerator.subAttritionalClaimsGenerator.outClaims=AggregatedCollectingModeStrategy.IDENTIFIER
components.mtpl.subClaimsGenerator.subSingleClaimsGenerator.outClaims=AggregatedCollectingModeStrategy.IDENTIFIER
