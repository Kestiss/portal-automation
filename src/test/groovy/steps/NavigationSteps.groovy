package steps

import browser.BrowserManager
import geb.Browser
import io.cucumber.java.en.Given

import static navigation.PageResolver.getPageByName
import static navigation.PageResolver.isDirectlyOpenable

class NavigationSteps {

    private Browser getBrowser() {
        BrowserManager.browser
    }

    @Given('^guest is on (.+) page$')
    void guestIsOnPage(String pageName) {
        Class pageClass = getPageByName(pageName)
        assert isDirectlyOpenable(pageClass): "Page '${pageName}' cannot be opened directly because it does not have a fixed URL"
        browser.to pageClass
    }
}
