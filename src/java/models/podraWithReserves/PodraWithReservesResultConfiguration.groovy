package models.podraWithReserves

model = models.podraWithReserves.PodraWithReservesModel
displayName = "Aggregated Overview"
components {
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
		subcontracts {
			outClaimsDevelopmentLeanCeded = "AGGREGATED"
			outClaimsDevelopmentLeanGross = "AGGREGATED"
			outClaimsDevelopmentLeanNet = "AGGREGATED"
			outCoverUnderwritingInfo = "AGGREGATED"
			outFilteredUnderwritingInfo = "AGGREGATED"
			outNetAfterCoverUnderwritingInfo = "AGGREGATED"
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
