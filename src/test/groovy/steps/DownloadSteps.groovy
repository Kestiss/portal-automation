package steps

import browser.BrowserManager
import browser.DownloadDirectory
import geb.Browser
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import pages.WallpaperDetailsPage

import java.nio.file.Files
import java.nio.file.Path

class DownloadSteps {

    private Set<String> downloadsBefore = [] as Set
    private Path downloadedWallpaper

    private Browser getBrowser() {
        BrowserManager.browser
    }

    @When('guest downloads the wallpaper')
    void guestDownloadsTheWallpaper() {
        downloadsBefore = DownloadDirectory.snapshot()
        (browser.page as WallpaperDetailsPage).downloadWallpaper()
    }

    @Then('the wallpaper should be downloaded successfully')
    void theWallpaperShouldBeDownloadedSuccessfully() {
        downloadedWallpaper = DownloadDirectory.waitForNewDownload(downloadsBefore)
        assert Files.exists(downloadedWallpaper): "Downloaded wallpaper was not found at ${downloadedWallpaper}"
        assert Files.size(downloadedWallpaper) > 0L: "Downloaded wallpaper at ${downloadedWallpaper} is empty"
    }
}
