import org.pillarone.riskanalytics.core.util.ResourceBundleRegistry

class RiskAnalyticsPropertyCasualtyGrailsPlugin {
    // the plugin version
    def version = "2.0-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Intuitive Collaboration AG"
    def authorEmail = "info (at) intuitive-collaboration (dot) com"
    def title = "Property Casualty Library and Example Models"
    def description = '''\\

'''

    // URL to the plugin's documentation
    def documentation = "http://www.pillarone.org"

    def groupId = "org.pillarone"

    def doWithWebDescriptor = {xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = {ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = {applicationContext ->
        // add resource bundle for exceptions
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.RESOURCE, "org.pillarone.riskanalytics.exceptionResources")

        //add resource bundle for validation
        //new
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.validation.commissionStrategyType")
        // note: distributionType.properties is outdated, new validation i18n is distributionTypeValidatorPC
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.utils.validation.distributionTypeValidatorPC")
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.validation.unityDouble")
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.generators.claims.validation.claimsGeneratorStrategyValidator")
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.filter.segmentFilterValidator")
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.validation.xlStrategyValidator")
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.underwriting.validation.riskBands")
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.VALIDATION, "org.pillarone.riskanalytics.domain.pc.generators.claims.validation.typableClaimsGeneratorValidator")

        //"org/pillarone/riskanalytics/application/help/ComponentHelp"
        ResourceBundleRegistry.addBundle(ResourceBundleRegistry.HELP, "org/pillarone/riskanalytics/help/ComponentHelp")
    }

    def onChange = {event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = {event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
