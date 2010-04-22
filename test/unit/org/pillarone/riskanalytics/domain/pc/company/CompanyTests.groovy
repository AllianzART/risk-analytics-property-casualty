package org.pillarone.riskanalytics.domain.pc.company

import org.pillarone.riskanalytics.domain.pc.claims.Claim
import org.pillarone.riskanalytics.domain.pc.lob.CompanyConfigurableLobWithReserves
import org.pillarone.riskanalytics.core.parameterization.ConstrainedString

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
class CompanyTests extends GroovyTestCase {
    
    void testUsage() {
        Company companyA = new Company(name: 'A')
        Company companyB = new Company(name: 'B')
        ConstrainedString companyAStr = new ConstrainedString(ICompanyMarker, 'A')
        companyAStr.selectedComponent = companyA
        ConstrainedString companyBStr = new ConstrainedString(ICompanyMarker, 'B')
        companyBStr.selectedComponent = companyB
        CompanyConfigurableLobWithReserves motorA = new CompanyConfigurableLobWithReserves(parmCompany: companyAStr)
        CompanyConfigurableLobWithReserves motorB = new CompanyConfigurableLobWithReserves(parmCompany: companyBStr)
        Claim claim100A = new Claim(ultimate: 100, lineOfBusiness: motorA)
        Claim claim500B = new Claim(ultimate: 500, lineOfBusiness: motorB)

        companyA.inClaimsGross << claim100A << claim500B
        companyA.doCalculation()
        assertEquals 'number of claims for company A', 1, companyA.outClaimsGross.size()
        assertEquals 'correct claim for company A', claim100A.ultimate, companyA.outClaimsGross[0].ultimate

        companyB.inClaimsGross << claim100A << claim500B
        companyB.doCalculation()
        assertEquals 'number of claims for company B', 1, companyB.outClaimsGross.size()
        assertEquals 'correct claim for company B', claim500B.ultimate, companyB.outClaimsGross[0].ultimate
    }
}