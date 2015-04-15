//Use a custom plugins dir, because different branches use different plugin versions
grails.project.plugins.dir = "../local-plugins/RiskAnalyticsPropertyCasualty-AR-111"

grails.project.dependency.resolver = "maven"

grails.project.dependency.resolution = {
    inherits "global" // inherit Grails' default dependencies
    log "warn"

    repositories {
        grailsHome()
        mavenLocal()

        mavenRepo (name:"zh-artisan-test" , url:"http://zh-artisan-test.art-allianz.com:8085/nexus/content/groups/public/") {
            updatePolicy System.getProperty('snapshotUpdatePolicy') ?: 'daily'
        }
    }

    plugins {
        runtime ":background-thread:1.3"
        runtime ":hibernate:3.6.10.3"
        runtime ":joda-time:0.5"
        runtime ":release:3.0.1", {
            excludes "groovy"
        }
        runtime ":quartz:1.0.1"
        runtime ":spring-security-core:2.0-RC2"
        runtime ":tomcat:7.0.42"

        test ":code-coverage:1.2.7"
        compile ":excel-import:1.0.0"

        if (appName == "RiskAnalyticsPropertyCasualty") {
            runtime "org.pillarone:risk-analytics-core:AR-111-SNAPSHOT"
            runtime("org.pillarone:risk-analytics-commons:1.9.4") { transitive = false }
        }
    }

    dependencies {
        test 'hsqldb:hsqldb:1.8.0.10'
        compile(group: 'org.apache.poi', name: 'poi', version: '3.9');
        compile(group: 'org.apache.poi', name: 'poi-ooxml', version: '3.9') {
            excludes 'xmlbeans'
        }
    }
}

grails.project.repos.default = "pillarone"
grails.project.dependency.distribution = {
    String password = ""
    String user = ""
    String scpUrl = ""
    try {
        Properties properties = new Properties()
        String version = new GroovyClassLoader().loadClass('RiskAnalyticsPropertyCasualtyGrailsPlugin').newInstance().version
        properties.load(new File("${userHome}/deployInfo.properties").newInputStream())
        user = properties.get("user")
        password = properties.get("password")

        if (version?.endsWith('-SNAPSHOT')) {
            scpUrl = properties.get("urlSnapshot")
        } else {
            scpUrl = properties.get("url")
        }
        remoteRepository(id: "pillarone", url: scpUrl) {
            authentication username: user, password: password
        }
    } catch (Throwable t) {
    }
}

coverage {
    enabledByDefault = false
    exclusions = [
            'models/**',
            '**/*Test*',
            '**/com/energizedwork/grails/plugins/jodatime/**',
            '**/grails/util/**',
            '**/org/codehaus/**',
            '**/org/grails/**',
            '**GrailsPlugin**',
            '**TagLib**'
    ]

}
