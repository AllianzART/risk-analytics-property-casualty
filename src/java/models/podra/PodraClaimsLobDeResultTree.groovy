package models.podra

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
model = PodraModel
displayName = "Sch�den nach Sparten (aufgeschl�sselt)"
language = "de"
mappings = [
        "Podra:Sch�den:bezahlt": "Podra:linesOfBusiness:outClaimsDevelopmentLeanNet:paid",
        "Podra:Sch�den:bezahlt:[%lineOfBusiness%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanNet:paid",
        "Podra:Sch�den:bezahlt:[%lineOfBusiness%]:brutto": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanGross:paid",
        "Podra:Sch�den:bezahlt:[%lineOfBusiness%]:brutto:[%claimsGenerator%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:claimsGenerators:[%claimsGenerator%]:outClaimsDevelopmentLeanGross:paid",
        "Podra:Sch�den:bezahlt:[%lineOfBusiness%]:zediert": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanCeded:paid",
        "Podra:Sch�den:bezahlt:[%lineOfBusiness%]:zediert:[%contract%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:reinsuranceContracts:[%contract%]:outClaimsDevelopmentLeanCeded:paid",

        "Podra:Sch�den:eingetreten": "Podra:linesOfBusiness:outClaimsDevelopmentLeanNet:incurred",
        "Podra:Sch�den:eingetreten:[%lineOfBusiness%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanNet:incurred",
        "Podra:Sch�den:eingetreten:[%lineOfBusiness%]:brutto": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanGross:incurred",
        "Podra:Sch�den:eingetreten:[%lineOfBusiness%]:brutto:[%claimsGenerator%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:claimsGenerators:[%claimsGenerator%]:outClaimsDevelopmentLeanGross:incurred",
        "Podra:Sch�den:eingetreten:[%lineOfBusiness%]:zediert": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanCeded:incurred",
        "Podra:Sch�den:eingetreten:[%lineOfBusiness%]:zediert:[%contract%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:reinsuranceContracts:[%contract%]:outClaimsDevelopmentLeanCeded:incurred",

        "Podra:Sch�den:reserviert": "Podra:linesOfBusiness:outClaimsDevelopmentLeanNet:reserved",
        "Podra:Sch�den:reserviert:[%lineOfBusiness%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanNet:reserved",
        "Podra:Sch�den:reserviert:[%lineOfBusiness%]:brutto": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanGross:reserved",
        "Podra:Sch�den:reserviert:[%lineOfBusiness%]:brutto:[%claimsGenerator%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:claimsGenerators:[%claimsGenerator%]:outClaimsDevelopmentLeanGross:reserved",
        "Podra:Sch�den:reserviert:[%lineOfBusiness%]:zediert": "Podra:linesOfBusiness:[%lineOfBusiness%]:outClaimsDevelopmentLeanCeded:reserved",
        "Podra:Sch�den:reserviert:[%lineOfBusiness%]:zediert:[%contract%]": "Podra:linesOfBusiness:[%lineOfBusiness%]:reinsuranceContracts:[%contract%]:outClaimsDevelopmentLeanCeded:reserved",

]