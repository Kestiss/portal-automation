package config

class EnvironmentConfig {

    static final String activeEnvironment = System.getProperty('environment', SelectedEnvironment.CURRENT)
    private static final Properties properties = loadProperties(activeEnvironment)

    static final String webHost = requiredProperty('web.host')

    private static Properties loadProperties(String environment) {
        Properties resolvedProperties = new Properties()
        loadFromResource("environments/${environment}.properties").each { key, value ->
            resolvedProperties.setProperty(key as String, value as String)
        }
        System.properties.each { key, value ->
            resolvedProperties.setProperty(key as String, value as String)
        }
        resolvedProperties
    }

    private static Properties loadFromResource(String resourcePath) {
        InputStream inputStream = EnvironmentConfig.class.classLoader.getResourceAsStream(resourcePath)
        if (inputStream == null) {
            return new Properties()
        }

        Properties loadedProperties = new Properties()
        inputStream.withCloseable {
            loadedProperties.load(it)
        }
        loadedProperties
    }

    private static String requiredProperty(String key) {
        String value = properties.getProperty(key)
        if (!value) {
            throw new IllegalStateException("Missing required property '${key}' in environment '${activeEnvironment}'")
        }
        value
    }
}
