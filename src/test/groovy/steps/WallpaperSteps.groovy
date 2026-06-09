package steps

import browser.BrowserManager
import geb.Browser
import io.cucumber.java.en.When
import pages.SearchResultsPage

class WallpaperSteps {

    private Browser getBrowser() {
        BrowserManager.browser
    }

    @When('^guest opens a (free|premium with credits|premium) wallpaper$')
    void guestOpensWallpaperType(String wallpaperType) {
        (browser.page as SearchResultsPage).openFirstWallpaper(wallpaperType)
    }
}
