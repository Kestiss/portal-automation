package pages.modules

import geb.Module

class CookiesModule extends Module {

    static content = {
        cookiesPopup(wait: 3, required: false) { $("[class*='didomi-exterior-border']") }
        acceptAllButton(waitCondition: { it.displayed }, required: false) { cookiesPopup.$('#didomi-notice-agree-button') }
        rejectOptionlButton(waitCondition: { it.displayed }, required: false) { cookiesPopup.$('#didomi-notice-disagree-button') }
        cookieSettingsButton(waitCondition: { it.displayed }, required: false) { cookiesPopup.$('#didomi-notice-learn-more-button') }
    }

    boolean isPresent() {
        cookiesPopup.displayed
    }

    void dismissIfPresent() {
        if (present) {
            acceptAllButton.click()
        }
    }
}
