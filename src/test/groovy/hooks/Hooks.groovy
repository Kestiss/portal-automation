package hooks

import browser.BrowserManager
import browser.DownloadDirectory
import geb.Browser
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver

class Hooks {

    @Before
    void beforeScenario() {
        DownloadDirectory.reset()
        BrowserManager.browser
    }

    @After
    void afterScenario(Scenario scenario) {
        try {
            Browser browser = BrowserManager.browser
            WebDriver driver = browser.driver
            if (scenario.failed) {
                scenario.log(driver.currentUrl)
                scenario.attach((driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES), 'image/png', 'failure-screenshot')
            }
        } finally {
            BrowserManager.quitBrowser()
            DownloadDirectory.reset()
        }
    }
}
