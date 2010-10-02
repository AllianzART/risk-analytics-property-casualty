package models.podra

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
model = PodraModel
displayName = "Vertragsdaten aufgeschl�sselt"
language = "de"
mappings = [
        "Podra:R�ckversicherungsvertrag:�bersicht:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outContractFinancials:result",
        "Podra:R�ckversicherungsvertrag:�bersicht:[%contract%]:Pr�mie": "Podra:reinsurance:subContracts:[%contract%]:outContractFinancials:premium",
        "Podra:R�ckversicherungsvertrag:�bersicht:[%contract%]:Provision": "Podra:reinsurance:subContracts:[%contract%]:outContractFinancials:commission",
        "Podra:R�ckversicherungsvertrag:�bersicht:[%contract%]:Schaden": "Podra:reinsurance:subContracts:[%contract%]:outContractFinancials:claim",
        "Podra:R�ckversicherungsvertrag:Schadenquote:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outContractFinancials:cededLossRatio",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanNet:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:zediert:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanCeded:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:zediert:[%contract%]:pro Segment:[%lineOfBusiness%]": "Podra:reinsurance:subContracts:[%contract%]:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanCeded:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:zediert:[%contract%]:pro Risiko:[%peril%]": "Podra:reinsurance:subContracts:[%contract%]:claimsGenerators:[%peril%]:outClaimsDevelopmentLeanCeded:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:brutto:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanGross:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:brutto:[%contract%]:pro Segment:[%lineOfBusiness%]": "Podra:reinsurance:subContracts:[%contract%]:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanGross:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:eingetreten:brutto:[%contract%]:pro Risiko:[%peril%]": "Podra:reinsurance:subContracts:[%contract%]:claimsGenerators:[%peril%]:outClaimsDevelopmentLeanGross:incurred",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanNet:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:zediert:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanCeded:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:zediert:[%contract%]:pro Segment:[%lineOfBusiness%]": "Podra:reinsurance:subContracts:[%contract%]:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanCeded:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:zediert:[%contract%]:pro Risiko:[%peril%]": "Podra:reinsurance:subContracts:[%contract%]:claimsGenerators:[%peril%]:outClaimsDevelopmentLeanCeded:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:brutto:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanGross:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:brutto:[%contract%]:pro Segment:[%lineOfBusiness%]": "Podra:reinsurance:subContracts:[%contract%]:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanGross:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:bezahlt:brutto:[%contract%]:pro Risiko:[%peril%]": "Podra:reinsurance:subContracts:[%contract%]:claimsGenerators:[%peril%]:outClaimsDevelopmentLeanGross:paid",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanNet:reserved",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:zediert:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanCeded:reserved",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:zediert:[%contract%]:pro Segment:[%lineOfBusiness%]": "Podra:reinsurance:subContracts:[%contract%]:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanCeded:reserved",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:zediert:[%contract%]:pro Risiko:[%peril%]": "Podra:reinsurance:subContracts:[%contract%]:claimsGenerators:[%peril%]:outClaimsDevelopmentLeanCeded:reserved",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:brutto:[%contract%]": "Podra:reinsurance:subContracts:[%contract%]:outClaimsDevelopmentLeanGross:reserved",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:brutto:[%contract%]:pro Segment:[%lineOfBusiness%]": "Podra:reinsurance:subContracts:[%contract%]:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanGross:reserved",
        "Podra:R�ckversicherungsvertrag:Schaden:reserviert:brutto:[%contract%]:pro Risiko:[%peril%]": "Podra:reinsurance:subContracts:[%contract%]:claimsGenerators:[%peril%]:outClaimsDevelopmentLeanGross:reserved",
]