package pages.modules

import geb.Module

class WallpaperCardModule extends Module {

    static content = {
        premiumHeader(required: false) { $("[class*='Card_card-header']") }
        creditsFooter(required: false) { $("[class*='Card_card-footer']") }
    }

    boolean isFree() {
        premiumHeader.empty
    }

    boolean isPremium() {
        !premiumHeader.empty && creditsFooter.empty
    }

    boolean isPremiumWithCredits() {
        !creditsFooter.empty
    }
}
