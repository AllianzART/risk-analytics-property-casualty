package models.podra

import org.pillarone.riskanalytics.core.output.AggregatedCollectingModeStrategy

model = models.podra.PodraModel
displayName = 'Lines of Business'

components.linesOfBusiness.outClaimsCeded = AggregatedCollectingModeStrategy.IDENTIFIER
components.linesOfBusiness.outClaimsGross = AggregatedCollectingModeStrategy.IDENTIFIER
components.linesOfBusiness.outClaimsNet = AggregatedCollectingModeStrategy.IDENTIFIER
components.linesOfBusiness.outUnderwritingInfoCeded = AggregatedCollectingModeStrategy.IDENTIFIER
components.linesOfBusiness.outUnderwritingInfoGross = AggregatedCollectingModeStrategy.IDENTIFIER
components.linesOfBusiness.outUnderwritingInfoNet = AggregatedCollectingModeStrategy.IDENTIFIER
