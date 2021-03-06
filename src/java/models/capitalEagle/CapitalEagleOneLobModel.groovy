package models.capitalEagle

import org.pillarone.riskanalytics.core.model.StochasticModel
import org.pillarone.riskanalytics.domain.pc.aggregators.ClaimsAggregator
import org.pillarone.riskanalytics.domain.pc.lob.ExampleLob
import org.pillarone.riskanalytics.domain.pc.global.GlobalParameters

class CapitalEagleOneLobModel extends StochasticModel {

    GlobalParameters globalParameters
    ExampleLob mtpl
    ClaimsAggregator claimsAggregator


    public void initComponents() {
        globalParameters = new GlobalParameters()
        mtpl = new ExampleLob()
        claimsAggregator = new ClaimsAggregator()

        addStartComponent mtpl
    }

    public void wireComponents() {
        claimsAggregator.inClaimsGross = mtpl.subRiProgram.outClaimsGross
        claimsAggregator.inClaimsCeded = mtpl.subRiProgram.outClaimsCeded
    }
}