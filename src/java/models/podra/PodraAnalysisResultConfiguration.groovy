package models.podra

model = models.podra.PodraModel
displayName = "CapitalEagle Analysis"
components {
	aggregateFinancials {
		outAlm = "AGGREGATED"
		outTotal = "AGGREGATED"
		outUnderwriting = "AGGREGATED"
	}
	almGenerators {
		outAlmResult = "AGGREGATED"
		outInitialVolume = "AGGREGATED"
		subsubcomponents {
			outAlmResult = "AGGREGATED"
			outInitialVolume = "AGGREGATED"
		}
	}
	claimsGenerators {
		outClaimsLeanDevelopment = "AGGREGATED"
		subsubcomponents {
			outClaimsLeanDevelopment = "AGGREGATED"
		}
	}
	linesOfBusiness {
		outClaimsDevelopmentLeanCeded = "AGGREGATED"
		outClaimsDevelopmentLeanGross = "AGGREGATED"
		outClaimsDevelopmentLeanNet = "AGGREGATED"
		outUnderwritingInfoCeded = "AGGREGATED"
		outUnderwritingInfoGross = "AGGREGATED"
		outUnderwritingInfoNet = "AGGREGATED"
		sublineOfBusiness {
			outClaimsDevelopmentLeanCeded = "AGGREGATED"
			outClaimsDevelopmentLeanGross = "AGGREGATED"
			outClaimsDevelopmentLeanNet = "AGGREGATED"
			outUnderwritingInfoCeded = "AGGREGATED"
			outUnderwritingInfoGross = "AGGREGATED"
			outUnderwritingInfoNet = "AGGREGATED"
		}
	}
	reinsurance {
		outClaimsDevelopmentLeanCeded = "AGGREGATED"
		outClaimsDevelopmentLeanGross = "AGGREGATED"
		outClaimsDevelopmentLeanNet = "AGGREGATED"
		outCoverUnderwritingInfo = "AGGREGATED"
		outNetAfterCoverUnderwritingInfo = "AGGREGATED"
		outUnderwritingInfo = "AGGREGATED"
		subContracts {
			subriContract {
				outClaimsDevelopmentLeanCeded = "AGGREGATED"
				outClaimsDevelopmentLeanGross = "AGGREGATED"
				outClaimsDevelopmentLeanNet = "AGGREGATED"
				outContractFinancials = "AGGREGATED"
				outCoverUnderwritingInfo = "AGGREGATED"
				outFilteredUnderwritingInfo = "AGGREGATED"
				outNetAfterCoverUnderwritingInfo = "AGGREGATED"
			}
		}
	}
	reserveGenerators {
		outClaimsLeanDevelopment = "AGGREGATED"
		outInitialReserves = "AGGREGATED"
		subsubcomponents {
			outClaimsLeanDevelopment = "AGGREGATED"
			outInitialReserves = "AGGREGATED"
		}
	}
}
