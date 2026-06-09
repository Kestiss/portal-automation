package pages.modules

import geb.Module

class DownloadPopupModule extends Module {

    static content = {
        modalPopup(required: false) { $("[class*='Modal_modal-content']") }
    }

    boolean waitUntilClosedIfPresent(int timeoutSeconds = 20) {
        modalPopupVisible() && waitFor(timeoutSeconds) { !modalPopupVisible() }
    }

    private boolean modalPopupVisible() {
        modalPopup.allElements().any { try { it.displayed } catch (ignored) { false } }
    }
}
