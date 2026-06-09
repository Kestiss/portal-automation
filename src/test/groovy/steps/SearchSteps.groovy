package steps

import browser.BrowserManager
import geb.Browser
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import pages.BasePage
import pages.SearchResultsPage

class SearchSteps {

    private Browser getBrowser() {
        BrowserManager.browser
    }

    @When('^guest searches for phrase (.+)$')
    void guestSearchesFor(String query) {
        BasePage page = browser.page as BasePage
        page.dismissVignetteIfNeeded()
        page.searchHeader.performSearch(query)
    }

    @Then('search returns no results')
    void searchReturnsNoResults() {
        assert (browser.page as SearchResultsPage).noResultsHeader.displayed
    }
}
