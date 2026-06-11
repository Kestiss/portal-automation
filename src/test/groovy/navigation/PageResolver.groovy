package navigation

import geb.Page

class PageResolver {

    private static final String PAGE_PACKAGE = 'pages'

    static Class<? extends Page> getPageByName(String pageName) {
        String normalizedPageName = pageName?.trim()
        if (!normalizedPageName) {
            throw new IllegalArgumentException('Page name must not be blank')
        }

        String className = "${PAGE_PACKAGE}.${toUpperCamelCase(normalizedPageName)}Page"
        try {
            Class.forName(className).asSubclass(Page)
        } catch (ClassNotFoundException exception) {
            throw new IllegalArgumentException("Unknown page: ${pageName}", exception)
        }
    }

    static boolean isDirectlyOpenable(Class<? extends Page> pageClass) {
        String url = pageClass.metaClass.getMetaProperty('url')?.getProperty(null)
        url && !url.startsWith('^')
    }

    private static String toUpperCamelCase(String text) {
        text.split(/[^A-Za-z0-9]+/)
                .findAll { it }
                .collect { it[0].toUpperCase() + it.substring(1) }
                .join('')
    }
}
