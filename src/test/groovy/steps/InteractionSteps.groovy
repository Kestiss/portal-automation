package steps

import browser.BrowserManager
import geb.Browser
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import pages.BasePage

class InteractionSteps {

    private Browser getBrowser() {
        BrowserManager.browser
    }

    @When('^guest clicks on (.+) link$')
    void guestClicksOnLink(String linkName) {
        (browser.page as BasePage).clickLink(linkName)
    }

    @When('^guest clicks on (.+) button$')
    void guestClicksOnButton(String buttonName) {
        (browser.page as BasePage).clickButton(buttonName)
    }

    @Then('^guest sees (.+)$')
    void guestSees(String elementName) {
        assert (browser.page as BasePage).isPresent(elementName): "'${elementName}' is not visible"
    }
}
