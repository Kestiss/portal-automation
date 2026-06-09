package pages

import config.EnvironmentConfig
import geb.Page
import org.openqa.selenium.WebElement
import pages.modules.CookiesModule
import pages.modules.LoginModalModule
import pages.modules.SearchHeaderModule
import pages.modules.VignetteModule

import java.util.regex.Pattern

class BasePage extends Page {

    protected static final String webHost = EnvironmentConfig.webHost

    static content = {
        cookiesPopup(required: false) { module CookiesModule }
        loginModal(required: false) { module LoginModalModule }
        searchHeader(required: false) { module SearchHeaderModule }
        vignette(required: false) { module VignetteModule }
    }

    @Override
    void onLoad(Page previousPage) {
        super.onLoad(previousPage)
        dismissCookiesIfPresent()
    }

    boolean expectElements() {
        dismissVignetteIfNeeded()
        true
    }

    void dismissCookiesIfPresent() {
        cookiesPopup.dismissIfPresent()
    }

    void dismissVignetteIfNeeded(int timeoutSeconds = 0) {
        vignette.dismissIfPresent(timeoutSeconds)
    }

    boolean isPresent(String name) {
        readProperty(toLowerCamelCase(name))?.isPresent() ?: false
    }

    void clickButton(String buttonName) {
        def button = $("button", text: buttonName?.trim())
        assert visibleElement(button) != null: "Button '${buttonName}' is not visible on ${this.class.simpleName}"
        button.click()
    }

    void clickLink(String linkName) {
        String normalizedLinkName = linkName?.trim()
        if (!normalizedLinkName) {
            throw new IllegalArgumentException('Link name must not be blank')
        }

        String propertyName = toLowerCamelCase(normalizedLinkName) + 'Link'
        Object target = readProperty(propertyName)
        if (target == null) {
            throw new UnsupportedOperationException("Link '${linkName}' is not supported on ${this.class.simpleName}")
        }

        WebElement visibleElement = visibleElement(target)
        assert visibleElement != null: "Link '${linkName}' is not visible on ${this.class.simpleName}"
        target.click()
    }

    protected static String urlPattern(String pathPattern) {
        '^' + Pattern.quote(webHost) + pathPattern + '\$'
    }

    private Object readProperty(String propertyName) {
        try {
            this."$propertyName"
        } catch (MissingPropertyException ignored) {
            null
        }
    }

    private static WebElement visibleElement(Object target) {
        if (target == null) {
            return null
        }

        try {
            target.allElements().find { WebElement element ->
                try {
                    element.displayed
                } catch (ignored) {
                    false
                }
            }
        } catch (MissingMethodException ignored) {
            null
        }
    }

    private static String toLowerCamelCase(String text) {
        List<String> parts = text.split(/[^A-Za-z0-9]+/).findAll { it }
        if (!parts) {
            return ''
        }

        String first = parts.first().toLowerCase()
        String rest = parts.drop(1).collect { it[0].toUpperCase() + it.substring(1).toLowerCase() }.join('')
        first + rest
    }
}
