package pages

class WallpapersPage extends BasePage {

    static String url = "${webHost}/ringtones-and-wallpapers"

    static at = {
        dismissVignetteIfNeeded()
        browser.currentUrl == url
        expectElements()
    }

    @Override
    boolean expectElements() {
        assert searchHeader.searchField.displayed
        true
    }
}
