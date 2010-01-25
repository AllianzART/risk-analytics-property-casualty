package org.pillarone.riskanalytics.domain.pc.reinsurance.contracts

import org.pillarone.riskanalytics.domain.assets.VoidTestModel
import org.pillarone.riskanalytics.domain.pc.reserves.fasttrack.ClaimDevelopmentLeanPacket
import org.pillarone.riskanalytics.core.components.Component
import org.pillarone.riskanalytics.core.simulation.engine.SimulationScope
import org.pillarone.riskanalytics.core.util.TestProbe
import org.pillarone.riskanalytics.core.parameterization.ComboBoxTableMultiDimensionalParameter
import org.pillarone.riskanalytics.domain.pc.lob.LobMarker
import org.pillarone.riskanalytics.domain.pc.claims.Claim
import org.pillarone.riskanalytics.domain.pc.constants.ClaimType
import org.pillarone.riskanalytics.domain.pc.underwriting.UnderwritingInfo
import org.pillarone.riskanalytics.domain.pc.generators.severities.Event
import org.pillarone.riskanalytics.core.example.component.TestComponent

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class MultiLineReinsuranceContractTests extends GroovyTestCase {

    static MultiLineReinsuranceContract getQSContract20Percent() {
        return new MultiLineReinsuranceContract(
                parmContractStrategy: ReinsuranceContractStrategyFactory.getContractStrategy(
                        ReinsuranceContractType.QUOTASHARE,
                        ["quotaShare": 0.2,
                         "commission": 0.0,
                         "coveredByReinsurer": 1d]),
                parmInuringPriority: 10,
                parmCoveredLines: new ComboBoxTableMultiDimensionalParameter(['fire'], ['Covered Lines'], LobMarker)
        )
    }

    // todo(sku): to be completed
    void testUsage() {
        Component component1 = new TestComponent()
        Component component2 = new TestComponent()
        Component component3 = new TestComponent()

        Claim claimFire100 = new Claim(origin: component1, claimType: ClaimType.ATTRITIONAL, value: 100d, fractionOfPeriod: 0.2)
        Claim claimHull60 = new Claim(origin: component2, claimType: ClaimType.SINGLE, value: 60d, fractionOfPeriod: 0.3)
        Claim claimLegal200 = new Claim(origin: component3, claimType: ClaimType.SINGLE, value: 200d, fractionOfPeriod: 0.1)

        UnderwritingInfo underwritingInfoFire = new UnderwritingInfo(origin: component1)
        UnderwritingInfo underwritingInfoHull = new UnderwritingInfo(origin: component2)

        MultiLineReinsuranceContract contract = getQSContract20Percent()

        contract.inClaims << claimFire100 << claimHull60 << claimLegal200
        contract.inUnderwritingInfo << underwritingInfoFire << underwritingInfoHull

        def probeContractUWI = new TestProbe(contract, "outCoverUnderwritingInfo")    // needed in order to trigger the calculation of net claims

    }

    void testUsageWithClaimDevelopmentLeanPackets() {
        MultiLineReinsuranceContract contract = MultiLineReinsuranceContractTests.getQSContract20Percent()
        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        contract.simulationScope = simulationScope
        TestComponent origin = new TestComponent()
        Event event1 = new Event()
        Event event2 = new Event()
        Claim originalClaim1 = new Claim(
                value: 10000,
                event: event1,
                fractionOfPeriod: 0.5,
                claimType: ClaimType.ATTRITIONAL,
        )
        Claim originalClaim2 = new Claim(
                value: 500,
                event: event2,
                fractionOfPeriod: 0.5,
                claimType: ClaimType.ATTRITIONAL,
        )
        ClaimDevelopmentLeanPacket claimDevelopment1 = new ClaimDevelopmentLeanPacket(
                        ultimate:10,
                        paid: 6,
                        origin: origin,
                        originalClaim: originalClaim1,
                        event: event1,
                        fractionOfPeriod: 0.5)
        ClaimDevelopmentLeanPacket claimDevelopment2 = new ClaimDevelopmentLeanPacket(
                        ultimate:12,
                        paid: 8,
                        origin: origin,
                        originalClaim: originalClaim2,
                        event: event1,
                        fractionOfPeriod: 0.5)
        contract.inClaims << claimDevelopment1 << claimDevelopment2

        def probeCoveredClaims = new TestProbe(contract, "outCoveredClaims")
        List coveredClaims = probeCoveredClaims.result

        contract.start()

        assertEquals '# ceded claims packets', 2, coveredClaims.size()
        assertEquals 'ceded incurred 0', 2d, coveredClaims[0].incurred
        assertEquals 'ceded incurred 1', 2.4, coveredClaims[1].incurred, 1E-10
        assertEquals 'ceded paid 0', 1.2, coveredClaims[0].paid, 1E-10
        assertEquals 'ceded paid 1', 1.6, coveredClaims[1].paid, 1E-10
        assertEquals 'ceded reserved 0', 0.8, coveredClaims[0].reserved, 1E-10
        assertEquals 'ceded reserved 1', 0.8, coveredClaims[1].reserved, 1E-10
    }
}