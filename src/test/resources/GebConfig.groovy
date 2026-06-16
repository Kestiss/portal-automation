import browser.DownloadDirectory
import config.EnvironmentConfig
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile

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

    firefox {
        driver = { firefoxDriver(false) }
    }

    firefoxHeadless {
        driver = { firefoxDriver(true) }
    }
}

def chromeDriver(boolean headless) {
    ChromeOptions options = new ChromeOptions()
    options.addArguments('--disable-dev-shm-usage', '--no-sandbox', '--window-size=1440,1200')
    options.setExperimentalOption('prefs', [
        'download.default_directory'                              : DownloadDirectory.absolutePath,
        'download.prompt_for_download'                            : false,
        'download.directory_upgrade'                              : true,
        'profile.default_content_setting_values.automatic_downloads': 1,
        'safebrowsing.enabled'                                    : true
    ])
    if (headless) {
        options.addArguments('--headless=new')
    }
    ChromeDriver driver = new ChromeDriver(options)
    if (headless) {
        driver.executeCdpCommand('Page.setDownloadBehavior', [
            behavior    : 'allow',
            downloadPath: DownloadDirectory.absolutePath
        ])
    }
    driver
}

def firefoxDriver(boolean headless) {
    FirefoxProfile profile = new FirefoxProfile()
    profile.setPreference('browser.download.folderList', 2)
    profile.setPreference('browser.download.dir', DownloadDirectory.absolutePath)
    profile.setPreference('browser.download.useDownloadDir', true)
    profile.setPreference('browser.helperApps.neverAsk.saveToDisk',
        'application/octet-stream,image/png,image/jpeg,image/webp')

    FirefoxOptions options = new FirefoxOptions()
    options.profile = profile
    if (headless) {
        options.addArguments('--headless')
    }
    new FirefoxDriver(options)
}
