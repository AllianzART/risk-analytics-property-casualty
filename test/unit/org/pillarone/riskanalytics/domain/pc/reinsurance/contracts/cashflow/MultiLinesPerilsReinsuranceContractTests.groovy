package org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.cashflow

import org.apache.commons.lang.NotImplementedException
import org.joda.time.DateTime
import org.joda.time.Period
import org.pillarone.riskanalytics.core.simulation.ContinuousPeriodCounter
import org.pillarone.riskanalytics.core.simulation.engine.IterationScope
import org.pillarone.riskanalytics.core.simulation.engine.PeriodScope
import org.pillarone.riskanalytics.core.simulation.engine.SimulationScope
import org.pillarone.riskanalytics.core.util.TestProbe
import org.pillarone.riskanalytics.domain.assets.VoidTestModel
import org.pillarone.riskanalytics.domain.pc.claims.ClaimWithExposure
import org.pillarone.riskanalytics.domain.pc.reserves.cashflow.ClaimDevelopmentPacket
import org.pillarone.riskanalytics.domain.pc.reserves.cashflow.ClaimDevelopmentWithIBNRPacket
import org.pillarone.riskanalytics.domain.pc.reserves.fasttrack.ClaimDevelopmentLeanPacket

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
class MultiLinesPerilsReinsuranceContractTests extends GroovyTestCase {

    static MultiLinesPerilsReinsuranceContract getContractFullCoverAllLinesPerils() {
        MultiLinesPerilsReinsuranceContract contract = new MultiLinesPerilsReinsuranceContract(
                parmContractStrategy : ReinsuranceContractType.getContractStrategy(
                        ReinsuranceContractType.QUOTASHARE, ["quotaShare": 0.25, "commission": 0.0, "coveredByReinsurer": 1d]),
                parmCoverPeriod : new FullPeriodCoveredStrategy(),
                parmCoveredByReinsurer : 1d,
                parmInuringPriority : 0d)
        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        simulationScope.iterationScope = getIterationScope(new DateTime(2010,1,1,0,0,0,0), Period.years(1))
        contract.simulationScope = simulationScope

        return contract
    }

    static MultiLinesPerilsReinsuranceContract getQuotaShare50FullCoverAllLinesPerils() {
        MultiLinesPerilsReinsuranceContract contract = new MultiLinesPerilsReinsuranceContract(
                parmContractStrategy : ReinsuranceContractType.getContractStrategy(
                        ReinsuranceContractType.QUOTASHARE, ["quotaShare": 0.50, "commission": 0.0, "coveredByReinsurer": 1d]),
                parmCoverPeriod : new FullPeriodCoveredStrategy(),
                parmCoveredByReinsurer : 1d,
                parmInuringPriority : 0d)
        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        simulationScope.iterationScope = getIterationScope(new DateTime(2010,1,1,0,0,0,0), Period.years(1))
        contract.simulationScope = simulationScope

        return contract
    }

    static MultiLinesPerilsReinsuranceContract getContractHalfPeriodCoverAllLinesPerils() {
        MultiLinesPerilsReinsuranceContract contract = new MultiLinesPerilsReinsuranceContract(
                parmContractStrategy : ReinsuranceContractType.getContractStrategy(
                        ReinsuranceContractType.QUOTASHARE, ["quotaShare": 0.25, "commission": 0.0, "coveredByReinsurer": 1d]),
                parmCoverPeriod : CoverPeriodType.getCoverPeriod(
                        CoverPeriodType.PERIOD,
                        ['start': new DateTime(2010,1,1,0,0,0,0), 'end': new DateTime(2010,6,30,0,0,0,0)]),
                parmCoveredByReinsurer : 1d,
                parmInuringPriority : 0d)
        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        simulationScope.iterationScope = getIterationScope(new DateTime(2010,1,1,0,0,0,0), Period.years(1))
        contract.simulationScope = simulationScope
        return contract
    }

    static MultiLinesPerilsReinsuranceContract getContractThreePeriodCoverDelayedAllLinesPerils() {
        MultiLinesPerilsReinsuranceContract contract = new MultiLinesPerilsReinsuranceContract(
                parmContractStrategy : ReinsuranceContractType.getContractStrategy(
                        ReinsuranceContractType.QUOTASHARE, ["quotaShare": 0.25, "commission": 0.0, "coveredByReinsurer": 1d]),
                parmCoverPeriod : CoverPeriodType.getCoverPeriod(
                        CoverPeriodType.PERIOD,
                        ['start': new DateTime(2011,1,1,0,0,0,0), 'end': new DateTime(2013,12,31,0,0,0,0)]),
                parmCoveredByReinsurer : 1d,
                parmInuringPriority : 0d)
        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        simulationScope.iterationScope = getIterationScope(new DateTime(2010,1,1,0,0,0,0), Period.years(1))
        contract.simulationScope = simulationScope
        return contract
    }

    static MultiLinesPerilsReinsuranceContract getAllLinesPerilsQuotaShareContract(int iterationStart, int contractStart, int contractDuration = 1, double quotaShare = 0.5, double inuringPriority = 0) {
        MultiLinesPerilsReinsuranceContract contract = new MultiLinesPerilsReinsuranceContract(
                parmContractStrategy : ReinsuranceContractType.getContractStrategy(
                        ReinsuranceContractType.QUOTASHARE, ["quotaShare": quotaShare, "commission": 0.0, "coveredByReinsurer": 1d]),
                parmCoverPeriod : CoverPeriodType.getCoverPeriod(
                        CoverPeriodType.PERIOD,
                        ['start': new DateTime(contractStart,1,1,0,0,0,0), 'end': new DateTime(contractStart+contractDuration-1,12,31,0,0,0,0)]),
                parmCoveredByReinsurer : 1d,
                parmInuringPriority : 0d)
        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        simulationScope.iterationScope = getIterationScope(new DateTime(iterationStart,1,1,0,0,0,0), Period.years(1))
        contract.simulationScope = simulationScope
        return contract
    }

    static IterationScope getIterationScope(DateTime startSimulation, Period period) {
        PeriodScope periodScope = new PeriodScope()
        periodScope.periodCounter = new ContinuousPeriodCounter(startSimulation, period)
        IterationScope iterationScope = new IterationScope()
        iterationScope.periodScope = periodScope
        iterationScope.prepareNextIteration()
        return iterationScope
    }

    void testOneFullPeriodCover() {
        MultiLinesPerilsReinsuranceContract contract = getContractFullCoverAllLinesPerils()
        ClaimDevelopmentPacket claim1000 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500)
        ClaimDevelopmentPacket claim800 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480)
        contract.inClaims << claim1000 << claim800

        def netClaims = new TestProbe(contract, 'outUncoveredClaims')
        
        contract.doCalculation()

        assertEquals '# gross covered', 2, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'claim1000 gross covered', claim1000, contract.outClaimsGrossInCoveredPeriod[0]
        assertEquals 'claim800 gross covered', claim800, contract.outClaimsGrossInCoveredPeriod[1]

        assertEquals '# ceded', 2, contract.outCoveredClaims.size()
        assertEquals 'claim1000 incurred ceded', 250, contract.outCoveredClaims[0].incurred
        assertEquals 'claim800 incurred ceded', 200, contract.outCoveredClaims[1].incurred
        assertEquals 'claim1000 paid ceded', 125, contract.outCoveredClaims[0].paid
        assertEquals 'claim800 paid ceded', 120, contract.outCoveredClaims[1].paid
        assertEquals 'claim1000 reserved ceded', 125, contract.outCoveredClaims[0].reserved
        assertEquals 'claim800 reserved ceded', 80, contract.outCoveredClaims[1].reserved
        assertEquals 'claim1000 change in reserves ceded', 125, contract.outCoveredClaims[0].changeInReserves
        assertEquals 'claim800 change in reserves ceded', 120, contract.outCoveredClaims[1].changeInReserves

        assertEquals '# net', 2, contract.outUncoveredClaims.size()
        assertEquals 'claim1000 incurred net', 750, contract.outUncoveredClaims[0].incurred
        assertEquals 'claim800 incurred net', 600, contract.outUncoveredClaims[1].incurred
        assertEquals 'claim1000 paid net', 375, contract.outUncoveredClaims[0].paid
        assertEquals 'claim800 paid net', 360, contract.outUncoveredClaims[1].paid
        assertEquals 'claim1000 reserved net', 375, contract.outUncoveredClaims[0].reserved
        assertEquals 'claim800 reserved net', 240, contract.outUncoveredClaims[1].reserved
        assertEquals 'claim1000 change in reserves net', 375, contract.outUncoveredClaims[0].changeInReserves
        assertEquals 'claim800 change in reserves net', 360, contract.outUncoveredClaims[1].changeInReserves
    }

    void testHalfPeriodCover() {
        MultiLinesPerilsReinsuranceContract contract = getContractHalfPeriodCoverAllLinesPerils()
        ClaimDevelopmentPacket claim1000 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500, fractionOfPeriod: 0.25)
        ClaimDevelopmentPacket claim800 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480, fractionOfPeriod: 0.51)
        contract.inClaims << claim1000 << claim800

        SimulationScope simulationScope= new SimulationScope()
        simulationScope.model = new VoidTestModel()
        simulationScope.iterationScope = getIterationScope(new DateTime(2010,1,1,0,0,0,0), Period.years(1))
        contract.simulationScope = simulationScope

        def netClaims = new TestProbe(contract, 'outUncoveredClaims')

        contract.doCalculation()

        assertEquals '# gross covered', 1, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'claim1000 gross covered', claim1000, contract.outClaimsGrossInCoveredPeriod[0]

        assertEquals '# ceded', 1, contract.outCoveredClaims.size()
        assertEquals 'claim1000 incurred ceded', 250, contract.outCoveredClaims[0].incurred
        assertEquals 'claim1000 paid ceded', 125, contract.outCoveredClaims[0].paid
        assertEquals 'claim1000 reserved ceded', 125, contract.outCoveredClaims[0].reserved
        assertEquals 'claim1000 change in reserves ceded', 125, contract.outCoveredClaims[0].changeInReserves

        assertEquals '# net', 1, contract.outUncoveredClaims.size()
        assertEquals 'claim1000 incurred net', 750, contract.outUncoveredClaims[0].incurred
        assertEquals 'claim1000 paid net', 375, contract.outUncoveredClaims[0].paid
        assertEquals 'claim1000 reserved net', 375, contract.outUncoveredClaims[0].reserved
        assertEquals 'claim1000 change in reserves net', 375, contract.outUncoveredClaims[0].changeInReserves
    }

    void testThreePeriodCoverDelayed() {
        MultiLinesPerilsReinsuranceContract contract = getContractThreePeriodCoverDelayedAllLinesPerils()
        ClaimDevelopmentPacket claim1000P0 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500, fractionOfPeriod: 0.25)
        ClaimDevelopmentPacket claim800P0 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480, fractionOfPeriod: 0.51)
        ClaimDevelopmentPacket claim1000P1 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500, fractionOfPeriod: 0.25, originalPeriod: 1)
        ClaimDevelopmentPacket claim800P1 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480, fractionOfPeriod: 0.51, originalPeriod: 1)
        ClaimDevelopmentPacket claim1000P3 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500, fractionOfPeriod: 0.25, originalPeriod: 3)
        ClaimDevelopmentPacket claim800P3 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480, fractionOfPeriod: 0.51, originalPeriod: 3)
        ClaimDevelopmentPacket claim1000P4 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500, fractionOfPeriod: 0.25, originalPeriod: 4)
        ClaimDevelopmentPacket claim800P4 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480, fractionOfPeriod: 0.51, originalPeriod: 4)
        contract.inClaims << claim1000P0 << claim800P0 << claim1000P1 << claim800P1

        def netClaims = new TestProbe(contract, 'outUncoveredClaims')

        contract.doCalculation()
        assertEquals 'year 2010 # gross covered', 0, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'year 2010 # ceded', 0, contract.outCoveredClaims.size()
        assertEquals 'year 2010 # net', 0, contract.outUncoveredClaims.size()
        contract.reset()

        contract.inClaims << claim1000P0 << claim800P0 << claim1000P1 << claim800P1
        contract.parmContractStrategy = ReinsuranceContractType.getTrivial()
        contract.simulationScope.iterationScope.periodScope.prepareNextPeriod()
        contract.doCalculation()
        assertEquals 'year 2011, # gross covered', 2, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'year 2011, claim1000 gross covered', claim1000P1, contract.outClaimsGrossInCoveredPeriod[0]
        assertEquals 'year 2011, claim800 gross covered', claim800P1, contract.outClaimsGrossInCoveredPeriod[1]

        assertEquals 'year 2011, # ceded', 2, contract.outCoveredClaims.size()
        assertEquals 'year 2011, claim1000 incurred ceded', 250, contract.outCoveredClaims[0].incurred
        assertEquals 'year 2011, claim800 incurred ceded', 200, contract.outCoveredClaims[1].incurred
        assertEquals 'year 2011, claim1000 paid ceded', 125, contract.outCoveredClaims[0].paid
        assertEquals 'year 2011, claim800 paid ceded', 120, contract.outCoveredClaims[1].paid
        assertEquals 'year 2011, claim1000 reserved ceded', 125, contract.outCoveredClaims[0].reserved
        assertEquals 'year 2011, claim800 reserved ceded', 80, contract.outCoveredClaims[1].reserved
        assertEquals 'year 2011, claim1000 change in reserves ceded', 125, contract.outCoveredClaims[0].changeInReserves
        assertEquals 'year 2011, claim800 change in reserves ceded', 120, contract.outCoveredClaims[1].changeInReserves

        assertEquals 'year 2011, # net', 2, contract.outUncoveredClaims.size()
        assertEquals 'year 2011, claim1000 incurred net', 750, contract.outUncoveredClaims[0].incurred
        assertEquals 'year 2011, claim800 incurred net', 600, contract.outUncoveredClaims[1].incurred
        assertEquals 'year 2011, claim1000 paid net', 375, contract.outUncoveredClaims[0].paid
        assertEquals 'year 2011, claim800 paid net', 360, contract.outUncoveredClaims[1].paid
        assertEquals 'year 2011, claim1000 reserved net', 375, contract.outUncoveredClaims[0].reserved
        assertEquals 'year 2011, claim800 reserved net', 240, contract.outUncoveredClaims[1].reserved
        assertEquals 'year 2011, claim1000 change in reserves net', 375, contract.outUncoveredClaims[0].changeInReserves
        assertEquals 'year 2011, claim800 change in reserves net', 360, contract.outUncoveredClaims[1].changeInReserves

        contract.reset()

        contract.inClaims << claim1000P0 << claim800P0 << claim1000P1 << claim800P1
        contract.parmContractStrategy = ReinsuranceContractType.getTrivial()
        contract.simulationScope.iterationScope.periodScope.prepareNextPeriod()
        contract.doCalculation()
        assertEquals 'year 2012 # gross covered', 0, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'year 2012 # ceded', 0, contract.outCoveredClaims.size()
        assertEquals 'year 2012 # net', 0, contract.outUncoveredClaims.size()

        contract.reset()

        contract.inClaims << claim1000P0 << claim800P0 << claim1000P1 << claim800P1  << claim1000P3 << claim800P3
        contract.parmContractStrategy = ReinsuranceContractType.getTrivial()
        contract.simulationScope.iterationScope.periodScope.prepareNextPeriod()
        contract.doCalculation()
        assertEquals 'year 2013, # gross covered', 2, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'year 2013, claim1000 gross covered', claim1000P3, contract.outClaimsGrossInCoveredPeriod[0]
        assertEquals 'year 2013, claim800 gross covered', claim800P3, contract.outClaimsGrossInCoveredPeriod[1]

        assertEquals 'year 2013, # ceded', 2, contract.outCoveredClaims.size()
        assertEquals 'year 2013, claim1000 incurred ceded', 250, contract.outCoveredClaims[0].incurred
        assertEquals 'year 2013, claim800 incurred ceded', 200, contract.outCoveredClaims[1].incurred
        assertEquals 'year 2013, claim1000 paid ceded', 125, contract.outCoveredClaims[0].paid
        assertEquals 'year 2013, claim800 paid ceded', 120, contract.outCoveredClaims[1].paid
        assertEquals 'year 2013, claim1000 reserved ceded', 125, contract.outCoveredClaims[0].reserved
        assertEquals 'year 2013, claim800 reserved ceded', 80, contract.outCoveredClaims[1].reserved
        assertEquals 'year 2013, claim1000 change in reserves ceded', 125, contract.outCoveredClaims[0].changeInReserves
        assertEquals 'year 2013, claim800 change in reserves ceded', 120, contract.outCoveredClaims[1].changeInReserves

        assertEquals 'year 2013, # net', 2, contract.outUncoveredClaims.size()
        assertEquals 'year 2013, claim1000 incurred net', 750, contract.outUncoveredClaims[0].incurred
        assertEquals 'year 2013, claim800 incurred net', 600, contract.outUncoveredClaims[1].incurred
        assertEquals 'year 2013, claim1000 paid net', 375, contract.outUncoveredClaims[0].paid
        assertEquals 'year 2013, claim800 paid net', 360, contract.outUncoveredClaims[1].paid
        assertEquals 'year 2013, claim1000 reserved net', 375, contract.outUncoveredClaims[0].reserved
        assertEquals 'year 2013, claim800 reserved net', 240, contract.outUncoveredClaims[1].reserved
        assertEquals 'year 2013, claim1000 change in reserves net', 375, contract.outUncoveredClaims[0].changeInReserves
        assertEquals 'year 2013, claim800 change in reserves net', 360, contract.outUncoveredClaims[1].changeInReserves

        contract.reset()

        contract.inClaims << claim1000P0 << claim800P0 << claim1000P1 << claim800P1 << claim1000P4 << claim800P4
        contract.parmContractStrategy = ReinsuranceContractType.getTrivial()
        contract.simulationScope.iterationScope.periodScope.prepareNextPeriod()
        contract.doCalculation()
        assertEquals 'year 2014 # gross covered', 0, contract.outClaimsGrossInCoveredPeriod.size()
        assertEquals 'year 2014 # ceded', 0, contract.outCoveredClaims.size()
        assertEquals 'year 2014 # net', 0, contract.outUncoveredClaims.size()
    }

    void testNoContractSet() {
        MultiLinesPerilsReinsuranceContract contract = getContractFullCoverAllLinesPerils()
        contract.parmContractStrategy = null

        ClaimDevelopmentPacket claim1000 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500)
        ClaimDevelopmentPacket claim800 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480)
        contract.inClaims << claim1000 << claim800

        shouldFail IllegalArgumentException, {contract.doCalculation()}
    }

    /**
     * Each contract may not have more than one nontrivial contract strategy instance over the scope of a simulation.
     */
    void testTwoContractSet() {
        // set up a contract (25% quota share) for 4 years (1.1.2010--31.12.2013)
        // with inuring priority 0 and iteration scope starting 1.1.2010
        MultiLinesPerilsReinsuranceContract contract = getAllLinesPerilsQuotaShareContract(2010, 2010, 4, 0.25, 0)

        // define some claims
        ClaimDevelopmentPacket claim1000 = new ClaimDevelopmentPacket(incurred: 1000, paid: 500, reserved: 500, changeInReserves: 500)
        ClaimDevelopmentPacket claim800 = new ClaimDevelopmentPacket(incurred: 800, paid: 480, reserved: 320, changeInReserves: 480)

        // simulate the first period of the first iteration
        contract.inClaims << claim1000 << claim800
        contract.doCalculation()

        // simulate the second period, but with another nontrivial contract strategy (instance)
        contract.reset()
        contract.parmContractStrategy = ReinsuranceContractType.getContractStrategy(
                ReinsuranceContractType.QUOTASHARE, ['quotaShare': 0.25, 'commission':0])
        contract.inClaims << claim1000 << claim800
        contract.simulationScope.iterationScope.periodScope.prepareNextPeriod()
        shouldFail IllegalArgumentException, {contract.doCalculation()}
    }

    /**
     * Make sure that filterClaimsInCoveredPeriod rejects processing all subclasses of Claim other than
     * ClaimDevelopmentPacket, i.e. (currently) it should throw a NotImplementedException if any claim
     * in inClaims is an instance of one of these classes:
     * <ul><li>ClaimDevelopmentLeanPacket</li>
     * <li>ClaimDevelopmentWithIBNRPacket</li>
     * <li>ClaimExposure</li></ul>
     */
    void testUnimplementedClaim() {
        MultiLinesPerilsReinsuranceContract contract = getAllLinesPerilsQuotaShareContract(2011, 2011, 1, 0.5, 0)

        contract.inClaims << new ClaimDevelopmentLeanPacket()
        shouldFail NotImplementedException, {contract.doCalculation()}

        contract.reset()
        contract.parmContractStrategy = ReinsuranceContractType.getTrivial()
        contract.inClaims << new ClaimDevelopmentWithIBNRPacket()
        shouldFail NotImplementedException, {contract.doCalculation()}

        contract.reset()
        contract.parmContractStrategy = ReinsuranceContractType.getTrivial()
        contract.inClaims << new ClaimWithExposure()
        shouldFail NotImplementedException, {contract.doCalculation()}
    }
}
