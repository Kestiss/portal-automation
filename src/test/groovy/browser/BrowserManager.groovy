package browser

import geb.Browser

class BrowserManager {

    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>()

    static Browser getBrowser() {
        if (browserThreadLocal.get() == null) {
            browserThreadLocal.set(new Browser())
        }
        browserThreadLocal.get()
    }

    static void quitBrowser() {
        Browser browser = browserThreadLocal.get()
        if (browser != null) {
            browser.quit()
            browserThreadLocal.remove()
        }
    }
}
