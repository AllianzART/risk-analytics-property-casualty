package org.pillarone.riskanalytics.domain.pc.reinsurance.contracts;

import org.apache.commons.lang.ArrayUtils;
import org.pillarone.riskanalytics.core.packets.PacketList;
import org.pillarone.riskanalytics.core.simulation.engine.SimulationScope;
import org.pillarone.riskanalytics.domain.pc.claims.Claim;
import org.pillarone.riskanalytics.domain.pc.claims.ClaimUtilities;
import org.pillarone.riskanalytics.domain.pc.claims.SortClaimsByFractionOfPeriod;
import org.pillarone.riskanalytics.domain.pc.constants.IncludeType;
import org.pillarone.riskanalytics.domain.pc.reinsurance.ReinsuranceResultWithCommisionPacket;
import org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.ReinsuranceContract;
import org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.cover.CoverAttributeStrategyType;
import org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.cover.ICoverAttributeStrategy;
import org.pillarone.riskanalytics.domain.pc.reserves.fasttrack.ClaimDevelopmentLeanPacket;
import org.pillarone.riskanalytics.domain.pc.underwriting.UnderwritingInfo;
import org.pillarone.riskanalytics.domain.pc.underwriting.UnderwritingInfoUtilities;

import java.util.Collections;

/**
 *  This component filters from the incoming claims and underwriting information
 *  the packets whose line is listed in parameter parmCoveredLines and provides
 *  them in the corresponding out Packetlists.
 *  If the parameter contains no line at all, all packets are sent as is to the
 *  next component. Packets are not modified.
 *
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class MultiCoverAttributeReinsuranceContract extends ReinsuranceContract {

    private SimulationScope simulationScope;

    private ICoverAttributeStrategy parmCover = CoverAttributeStrategyType.getStrategy(
            CoverAttributeStrategyType.ALL, ArrayUtils.toMap(new Object[][]{{"reserves", IncludeType.NOTINCLUDED}}));

    /** claims whose source is a covered line         */
    private PacketList<Claim> outFilteredClaims = new PacketList<Claim>(Claim.class);
    // todo(sku): remove the following and related lines as soon as PMO-648 is resolved
    private PacketList<ClaimDevelopmentLeanPacket> outClaimsDevelopmentLeanNet = new PacketList<ClaimDevelopmentLeanPacket>(ClaimDevelopmentLeanPacket.class);
    private PacketList<ClaimDevelopmentLeanPacket> outClaimsDevelopmentLeanGross = new PacketList<ClaimDevelopmentLeanPacket>(ClaimDevelopmentLeanPacket.class);
    private PacketList<ClaimDevelopmentLeanPacket> outClaimsDevelopmentLeanCeded = new PacketList<ClaimDevelopmentLeanPacket>(ClaimDevelopmentLeanPacket.class);

    private PacketList<ReinsuranceResultWithCommisionPacket> outContractFinancials = new PacketList<ReinsuranceResultWithCommisionPacket>(ReinsuranceResultWithCommisionPacket.class);

    private PacketList<UnderwritingInfo> outFilteredUnderwritingInfo = new PacketList<UnderwritingInfo>(UnderwritingInfo.class);

    public void doCalculation() {
        if (parmContractStrategy == null) {
            throw new IllegalStateException("A contract strategy must be set");
        }
        filterInChannels();
        // initialize contract details
        parmContractStrategy.initBookKeepingFigures(outFilteredClaims, outFilteredUnderwritingInfo);

        Collections.sort(outFilteredClaims, SortClaimsByFractionOfPeriod.getInstance());
        if (isSenderWired(getOutUncoveredClaims()) || isSenderWired(outClaimsDevelopmentLeanNet)) {
            calculateClaims(outFilteredClaims, outCoveredClaims, outUncoveredClaims, this);
        }
        else {
            calculateCededClaims(outFilteredClaims, outCoveredClaims, this);
        }

        if (isSenderWired(outNetAfterCoverUnderwritingInfo)) {
            calculateUnderwritingInfos(outFilteredUnderwritingInfo, outCoverUnderwritingInfo, outNetAfterCoverUnderwritingInfo);
        }
        else if (isSenderWired(outCoverUnderwritingInfo) || isSenderWired(outContractFinancials)) {
            calculateCededUnderwritingInfos(outFilteredUnderwritingInfo, outCoverUnderwritingInfo);
        }
        if (inClaims.size() > 0 && inClaims.get(0) instanceof ClaimDevelopmentLeanPacket) {
            for (Claim claim : outFilteredClaims) {
                outClaimsDevelopmentLeanGross.add((ClaimDevelopmentLeanPacket) claim);
            }
        }
        if (outCoveredClaims.size() > 0 && outCoveredClaims.get(0) instanceof ClaimDevelopmentLeanPacket) {
            for (Claim claim : outUncoveredClaims) {
                outClaimsDevelopmentLeanNet.add((ClaimDevelopmentLeanPacket) claim);
            }
            for (Claim claim : outCoveredClaims) {
                outClaimsDevelopmentLeanCeded.add((ClaimDevelopmentLeanPacket) claim);
            }
        }
        if (isSenderWired(outContractFinancials)) {
            ReinsuranceResultWithCommisionPacket result = new ReinsuranceResultWithCommisionPacket();
            UnderwritingInfo underwritingInfo = UnderwritingInfoUtilities.aggregate(outCoverUnderwritingInfo);
            if (underwritingInfo != null) {
                result.setCededPremium(-underwritingInfo.getPremiumWritten());
                result.setCededCommission(underwritingInfo.getCommission());
            }
            result.setCededClaim(ClaimUtilities.aggregateClaims(outCoveredClaims, this).getUltimate());
            outContractFinancials.add(result);
        }
    }

    protected void filterInChannels() {
//        List<LobMarker> coveredLines = parmCoveredLines.getValuesAsObjects(simulationScope.getModel());
//        List<PerilMarker> coveredPerils = parmCoveredPerils.getValuesAsObjects(simulationScope.getModel());
//        List<IReserveMarker> coveredReserves = parmCoveredReserves.getValuesAsObjects(simulationScope.getModel());
//        outFilteredClaims.addAll(ClaimFilterUtilities.filterClaimsByPerilLobReserve(inClaims, coveredPerils, coveredLines, coveredReserves));
//        if (coveredLines.size() == 0) {
//            coveredLines = ClaimFilterUtilities.getLineOfBusiness(outFilteredClaims);
//        }
        outFilteredUnderwritingInfo.addAll(inUnderwritingInfo);
    }

    public SimulationScope getSimulationScope() {
        return simulationScope;
    }

    public void setSimulationScope(SimulationScope simulationScope) {
        this.simulationScope = simulationScope;
    }

    public PacketList<Claim> getOutFilteredClaims() {
        return outFilteredClaims;
    }

    public void setOutFilteredClaims(PacketList<Claim> outFilteredClaims) {
        this.outFilteredClaims = outFilteredClaims;
    }

    public PacketList<ClaimDevelopmentLeanPacket> getOutClaimsDevelopmentLeanNet() {
        return outClaimsDevelopmentLeanNet;
    }

    public void setOutClaimsDevelopmentLeanNet(PacketList<ClaimDevelopmentLeanPacket> outClaimsDevelopmentLeanNet) {
        this.outClaimsDevelopmentLeanNet = outClaimsDevelopmentLeanNet;
    }

    public PacketList<ClaimDevelopmentLeanPacket> getOutClaimsDevelopmentLeanGross() {
        return outClaimsDevelopmentLeanGross;
    }

    public void setOutClaimsDevelopmentLeanGross(PacketList<ClaimDevelopmentLeanPacket> outClaimsDevelopmentLeanGross) {
        this.outClaimsDevelopmentLeanGross = outClaimsDevelopmentLeanGross;
    }

    public PacketList<ClaimDevelopmentLeanPacket> getOutClaimsDevelopmentLeanCeded() {
        return outClaimsDevelopmentLeanCeded;
    }

    public void setOutClaimsDevelopmentLeanCeded(PacketList<ClaimDevelopmentLeanPacket> outClaimsDevelopmentLeanCeded) {
        this.outClaimsDevelopmentLeanCeded = outClaimsDevelopmentLeanCeded;
    }

    public PacketList<ReinsuranceResultWithCommisionPacket> getOutContractFinancials() {
        return outContractFinancials;
    }

    public void setOutContractFinancials(PacketList<ReinsuranceResultWithCommisionPacket> outContractFinancials) {
        this.outContractFinancials = outContractFinancials;
    }

    public PacketList<UnderwritingInfo> getOutFilteredUnderwritingInfo() {
        return outFilteredUnderwritingInfo;
    }

    public void setOutFilteredUnderwritingInfo(PacketList<UnderwritingInfo> outFilteredUnderwritingInfo) {
        this.outFilteredUnderwritingInfo = outFilteredUnderwritingInfo;
    }

    public ICoverAttributeStrategy getParmCover() {
        return parmCover;
    }

    public void setParmCover(ICoverAttributeStrategy parmCover) {
        this.parmCover = parmCover;
    }
}