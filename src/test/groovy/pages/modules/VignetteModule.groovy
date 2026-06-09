package pages.modules

import geb.Module
import geb.waiting.WaitTimeoutException

class VignetteModule extends Module {

    private static final String URL_FRAGMENT = '#google_vignette'
    private static final String DISMISS_BUTTON = '#dismiss-button-element'

    static content = {
        vignetteFrame(wait: 3, required: false) { $("ins[data-vignette-loaded='true'] iframe").allElements().find { it.displayed } }
    }

    boolean dismissIfPresent(int timeoutSeconds = 0) {
        try {
            (timeoutSeconds > 0 ? waitFor(timeoutSeconds) { isActive() } : isActive()) && clickDismiss()
        } catch (WaitTimeoutException ignored) {
            false
        }
    }

    private boolean isActive() {
        browser.currentUrl.contains(URL_FRAGMENT)
    }

    private boolean clickDismiss() {
        def frame = vignetteFrame
        if (!frame) return false
        try {
            withFrame(frame) {
                waitFor(10) { $(DISMISS_BUTTON).allElements().find { it.displayed && it.enabled } }.click()
            }
            true
        } catch (WaitTimeoutException ignored) {
            false
        }
    }
}
