package pages


import pages.modules.DownloadPopupModule

class WallpaperDetailsPage extends BasePage {

    static String atUrl = urlPattern("/wallpapers/[0-9a-f-]+/?(\\?.*)?")

    static at = {
        browser.currentUrl.matches(atUrl)
        dismissVignetteIfNeeded()
        expectElements()
    }

    static content = {
        downloadButton(waitCondition: { it.displayed }) { $("button[class*='Button_button']", text: 'Download') }
        downloadPopup(required: false) { module DownloadPopupModule }
    }

    @Override
    boolean expectElements() {
        assert downloadButton
        true
    }

    void downloadWallpaper() {
        downloadButton.click()
        waitForDownloadInterstitialToFinish()
    }

    private void waitForDownloadInterstitialToFinish() {
        downloadPopup.waitUntilClosedIfPresent()
    }
}
