import browser.DownloadDirectory
import config.EnvironmentConfig
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

baseUrl = EnvironmentConfig.webHost

waiting {
    timeout = 10
    includeCauseInMessage = true
}

baseNavigatorWaiting = true
atCheckWaiting = true
cacheDriver = false

driver = { chromeDriver(false) }

environments {
    chrome {
        driver = { chromeDriver(false) }
    }

    chromeHeadless {
        driver = { chromeDriver(true) }
    }
}

def chromeDriver(boolean headless) {
    ChromeOptions chromeOptions = new ChromeOptions()
    chromeOptions.addArguments('--disable-dev-shm-usage', '--no-sandbox', '--window-size=1440,1200')
    chromeOptions.setExperimentalOption('prefs', [
        'download.default_directory': DownloadDirectory.absolutePath,
        'download.prompt_for_download': false,
        'download.directory_upgrade': true,
        'profile.default_content_setting_values.automatic_downloads': 1,
        'safebrowsing.enabled': true
    ])

    if (headless) {
        chromeOptions.addArguments('--headless=new')
    }
    new ChromeDriver(chromeOptions)
}
