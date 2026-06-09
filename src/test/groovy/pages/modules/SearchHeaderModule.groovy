package pages.modules

import geb.Module
import pages.SearchResultsPage

class SearchHeaderModule extends Module {

    static content = {
        header(wait: true) { $("[class*='header']")[0] }
        searchField(wait: true) { header.$('#search') }
        searchSubmit(waitCondition: { it.displayed }, toWait: true, to: SearchResultsPage) { header.$("button[type='submit']") }
    }

    void performSearch(String query) {
        searchField.click()
        searchField.value(query)
        searchSubmit.click()
    }
}
