package models.podra


import org.pillarone.riskanalytics.core.model.StochasticModel
import org.pillarone.riskanalytics.domain.pc.underwriting.DynamicUnderwritingSegments
import org.pillarone.riskanalytics.domain.pc.generators.claims.DynamicClaimsGenerators
import org.pillarone.riskanalytics.domain.pc.generators.copulas.DynamicDependencies
import org.pillarone.riskanalytics.domain.pc.generators.copulas.DynamicMultipleDependencies
import org.pillarone.riskanalytics.domain.pc.lob.DynamicConfigurableLobs
import org.pillarone.riskanalytics.domain.pc.reinsurance.programs.MultiLineDynamicReinsuranceProgram

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
class PodraModel extends StochasticModel {

// Anlegen der Komponenten des Podra Modells
// als "Dynamische Komponenten" (Elemente k�nnen im UI hinzugef�gt und gel�scht werden
// Underwriting Kennzahlen: Risikob�nder
    DynamicUnderwritingSegments underwritingSegments
// Schadengeneratoren: Typen Attritional Loss, Frequency Severity
    DynamicClaimsGenerators claimsGenerators
// Abh�ngigkeitsstrukturen: Copulae auf Attritional Loss Verteilungen
    DynamicDependencies dependencies
// Ereignisgeneratoren f�r Frequency Severity Generatoren
    DynamicMultipleDependencies eventGenerators
// Zusammenfassung der Schadengeneratoren zu Branchen
    DynamicConfigurableLobs linesOfBusiness
// R�ckversicherung auf Branchen oder SG-Level
    MultiLineDynamicReinsuranceProgram reinsurance

    void initComponents() {
// Komponenten anlegen
        underwritingSegments = new DynamicUnderwritingSegments()
        claimsGenerators = new DynamicClaimsGenerators()
        dependencies = new DynamicDependencies()
        eventGenerators = new DynamicMultipleDependencies()
        linesOfBusiness = new DynamicConfigurableLobs()
        reinsurance = new MultiLineDynamicReinsuranceProgram()
// Aufbau des Modells als Baumstruktur: Wurzeln festlegen
        addStartComponent underwritingSegments
        addStartComponent dependencies
        addStartComponent eventGenerators
    }

    void wireComponents() {
// Zusammenbinden der Komponenten: �bergabe von Ausgabegr��en an Eingabekan�le
        claimsGenerators.inUnderwritingInfo = underwritingSegments.outUnderwritingInfo
        claimsGenerators.inProbabilities = dependencies.outProbabilities
        claimsGenerators.inEventSeverities = eventGenerators.outEventSeverities
        linesOfBusiness.inUnderwritingInfoGross = underwritingSegments.outUnderwritingInfo
        linesOfBusiness.inClaimsGross = claimsGenerators.outClaims
        reinsurance.inUnderwritingInfo = linesOfBusiness.outUnderwritingInfoGross
        reinsurance.inClaims = linesOfBusiness.outClaimsGross
        linesOfBusiness.inUnderwritingInfoCeded = reinsurance.outCoverUnderwritingInfo
        linesOfBusiness.inClaimsCeded = reinsurance.outClaimsCeded
    }
}