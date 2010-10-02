package models.podra

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
model = PodraModel
displayName = "Sparten"
language = "de"

mappings = [
        "Podra:Sch�den:netto:eingetreten": "Podra:linesOfBusiness:outClaimsDevelopmentLeanNet:incurred",
        "Podra:Sch�den:netto:eingetreten:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanNet:incurred",
        "Podra:Sch�den:netto:bezahlt": "Podra:linesOfBusiness:outClaimsDevelopmentLeanNet:paid",
        "Podra:Sch�den:netto:bezahlt:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanNet:paid",
        "Podra:Sch�den:netto:reserviert": "Podra:linesOfBusiness:outClaimsDevelopmentLeanNet:reserved",
        "Podra:Sch�den:netto:reserviert:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanNet:reserved",
        "Podra:Sch�den:brutto:eingetreten": "Podra:linesOfBusiness:outClaimsDevelopmentLeanGross:incurred",
        "Podra:Sch�den:brutto:eingetreten:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanGross:incurred",
        "Podra:Sch�den:brutto:bezahlt": "Podra:linesOfBusiness:outClaimsDevelopmentLeanGross:paid",
        "Podra:Sch�den:brutto:bezahlt:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanGross:paid",
        "Podra:Sch�den:brutto:reserviert": "Podra:linesOfBusiness:outClaimsDevelopmentLeanGross:reserved",
        "Podra:Sch�den:brutto:reserviert:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanGross:reserved",
        "Podra:Sch�den:zediert:eingetreten": "Podra:linesOfBusiness:outClaimsDevelopmentLeanCeded:incurred",
        "Podra:Sch�den:zediert:eingetreten:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanCeded:incurred",
        "Podra:Sch�den:zediert:bezahlt": "Podra:linesOfBusiness:outClaimsDevelopmentLeanCeded:paid",
        "Podra:Sch�den:zediert:bezahlt:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanCeded:paid",
        "Podra:Sch�den:zediert:reserviert": "Podra:linesOfBusiness:outClaimsDevelopmentLeanCeded:reserved",
        "Podra:Sch�den:zediert:reserviert:[%subcomponents%]": "Podra:linesOfBusiness:[%subcomponents%]:outClaimsDevelopmentLeanCeded:reserved",

        "Podra:Zeichnungsinformation:Pr�mie:netto": "Podra.linesOfBusiness:outUnderwritingInfoNet.premiumWritten",
        "Podra:Zeichnungsinformation:Pr�mie:netto:[%subcomponents%]": "Podra.linesOfBusiness:[%subcomponents%]:outUnderwritingInfoNet.commission",
        "Podra:Zeichnungsinformation:Provision:netto": "Podra.linesOfBusiness:outUnderwritingInfoNet.premiumWritten",
        "Podra:Zeichnungsinformation:Provision:netto:[%subcomponents%]": "Podra.linesOfBusiness:[%subcomponents%]:outUnderwritingInfoNet.commission",
        "Podra:Zeichnungsinformation:Pr�mie:brutto": "Podra.linesOfBusiness:outUnderwritingInfoGross.premiumWritten",
        "Podra:Zeichnungsinformation:Pr�mie:brutto:[%subcomponents%]": "Podra.linesOfBusiness:[%subcomponents%]:outUnderwritingInfoGross.commission",
        "Podra:Zeichnungsinformation:Provision:brutto": "Podra.linesOfBusiness:outUnderwritingInfoGross.premiumWritten",
        "Podra:Zeichnungsinformation:Provision:brutto:[%subcomponents%]": "Podra.linesOfBusiness:[%subcomponents%]:outUnderwritingInfoGross.commission",
        "Podra:Zeichnungsinformation:Pr�mie:zediert": "Podra.linesOfBusiness:outUnderwritingInfoCeded.premiumWritten",
        "Podra:Zeichnungsinformation:Pr�mie:zediert:[%subcomponents%]": "Podra.linesOfBusiness:[%subcomponents%]:outUnderwritingInfoCeded.commission",
        "Podra:Zeichnungsinformation:Provision:zediert": "Podra.linesOfBusiness:outUnderwritingInfoCeded.premiumWritten",
        "Podra:Zeichnungsinformation:Provision:zediert:[%subcomponents%]": "Podra.linesOfBusiness:[%subcomponents%]:outUnderwritingInfoCeded.commission",
]