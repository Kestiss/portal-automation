package browser

import geb.Browser
import org.openqa.selenium.chrome.ChromeDriver

class BrowserManager {

    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>()

    static Browser getBrowser() {
        if (browserThreadLocal.get() == null) {
            DownloadDirectory.reset()
            Browser browser = new Browser()
            enableChromeDownloads(browser)
            browserThreadLocal.set(browser)
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

    private static void enableChromeDownloads(Browser browser) {
        if (browser.driver instanceof ChromeDriver) {
            (browser.driver as ChromeDriver).executeCdpCommand('Page.setDownloadBehavior', [
                    behavior    : 'allow',
                    downloadPath: DownloadDirectory.absolutePath
            ])
        }
    }
}
