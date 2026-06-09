package web

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines('cucumber')
@SelectPackages('features')
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = 'steps,hooks')
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = '@all')
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = 'true')
class CukesRunner {
}
