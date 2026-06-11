package pages

import pages.modules.WallpaperCardModule

class SearchResultsPage extends BasePage {

    static String atUrl = urlPattern("/find/[^?#/]+/?(\\?.*)?")

    static at = {
        dismissVignetteIfNeeded()
        browser.currentUrl.matches(atUrl)
        expectElements()
    }

    static content = {
        wallpaperCards(wait: true) { $("a:has([class*='Card_card'])").moduleList(WallpaperCardModule) }
        noResultsHeader(required: false) { $("[class*='heading']", text: "Oops, couldn’t find it") }
    }

    @Override
    boolean expectElements() {
        assert searchHeader.searchField.displayed
        true
    }

    void openFirstWallpaper(String wallpaperType) {
        WallpaperCardModule card = firstWallpaperOfType(wallpaperType)
        assert card != null: "No ${wallpaperType} wallpaper was found on the search results page"
        card.click()

        dismissVignetteIfNeeded()
        browser.at WallpaperDetailsPage
    }

    private WallpaperCardModule firstWallpaperOfType(String wallpaperType) {
        switch (wallpaperType?.toLowerCase()) {
            case 'free':
                return wallpaperCards.find { it.free }
            case 'premium':
                return wallpaperCards.find { it.premium }
            case 'premium with credits':
                return wallpaperCards.find { it.premiumWithCredits }
            default:
                throw new IllegalArgumentException("Unsupported wallpaper type: ${wallpaperType}")
        }
    }
}
