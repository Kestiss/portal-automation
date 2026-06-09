package pages

class HomePage extends BasePage {

    static String url = webHost

    static at = {
        browser.currentUrl.matches(urlPattern("/?(\\?.*)?"))
        expectElements()
    }

    static content = {
        browseNowLink(waitCondition: { it.displayed }, toWait: true, to: WallpapersPage) { $("[data-appearance='primary']", text: 'Browse Now') }
    }

    @Override
    boolean expectElements() {
        assert browseNowLink
        true
    }
}
