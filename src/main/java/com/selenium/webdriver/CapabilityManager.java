package com.selenium.webdriver;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.config.Config;
import com.config.FrameworkConfig;
import com.json.JSONManager;
import com.reporting.Reporter;
import com.selenium.Custom_ExceptionHandler;


public enum CapabilityManager implements CapabilityManagerInterface {

	FIREFOX {
		
		public WebDriver getWebDriver(String capabilityJsonFilePath, String capabilityJsonObject, String remoteURL) {
			
			WebDriver driver = null;
			
			try {
				JSONManager json = new JSONManager(capabilityJsonFilePath, capabilityJsonObject);

				DesiredCapabilities otherCapabilities = new DesiredCapabilities(json.toHashMap());

				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("browser.download.folderList", 2);
				options.addPreference("browser.download.dir", Config.getDownloadsPath());
				options.addPreference("browser.download.useDownloadDir", true);
				options.addPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/pdf,application/x-pdf,application/octet-stream,text/csv");
				options.addPreference("pdfjs.disabled", true); // disable the built-in PDF viewer

				if (null == remoteURL || remoteURL.equals("")) {
					driver =  new FirefoxDriver(options);
				} else {
					options = options.merge(otherCapabilities);
					driver = new RemoteWebDriver(new URL(remoteURL), options);
				}
			} catch (Exception e) {
				new Custom_ExceptionHandler(e);
			}
			return driver;
		}

	},
	
	CHROME {
		public WebDriver getWebDriver(String capabilityJsonFilePath, String capabilityJsonObject, String remoteURL) {
			WebDriver driver = null;
			try {
				JSONManager json = new JSONManager(capabilityJsonFilePath, capabilityJsonObject);

				Capabilities otherCapabilities = new MutableCapabilities(json.toHashMap());
				ChromeOptions options = new ChromeOptions();

				//*****************************
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", FrameworkConfig.getDownloadsPath());
				chromePrefs.put("ssl.error_override_allowed", true);
				chromePrefs.put("safebrowsing.enabled", "false"); // to disable security check eg. Keep or
				options.setExperimentalOption("prefs", chromePrefs);
				
				options.addArguments("--disable-extensions"); // to disable browser extension popup
				options.addArguments("--use-fake-device-for-media-stream");
				options.addArguments("--use-fake-ui-for-media-stream");
				// Putting down a logic to render diff videos when thread id is even or odd
				String mediafile = "bus_cif_15fps.y4m";
				/*
				 * options.addArguments("--use-file-for-fake-video-capture=" +
				 * FrameworkConfig.getResourcesFolderPath() + "/" + mediafile);
				 */
				/*
				 * https://www.programcreek.com/java-api-examples/?class=org.openqa.selenium.
				 * chrome.ChromeOptions&method=addArguments
				 * https://www.programcreek.com/java-api-examples/?code=OpenVidu%2Fopenvidu%
				 * 2Fopenvidu-master%2Fopenvidu-test-browsers%2Fsrc%2Fmain%2Fjava%2Fio%
				 * 2Fopenvidu%2Ftest%2Fbrowsers%2FChromeUser.java
				 * prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
				 * prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
				 */
				//*****************************
				options.merge(otherCapabilities);
				
				if (null == remoteURL || remoteURL.equals("")) {
					driver = new ChromeDriver(options);
				} else {
					options = options.merge(otherCapabilities);
					driver = new RemoteWebDriver(new URL(remoteURL), options);
				}
			} catch (Exception e) {
				new Custom_ExceptionHandler(e);
			}
			return driver;
		}

	},
	
	CHROME_HEADLESS {
		public WebDriver getWebDriver(String capabilityJsonFilePath, String capabilityJsonObject, String remoteURL) {
			WebDriver driver = null;
			try {
				JSONManager json = new JSONManager(capabilityJsonFilePath, capabilityJsonObject);

				DesiredCapabilities otherCapabilities = new DesiredCapabilities(json.toHashMap());
				ChromeOptions options = new ChromeOptions();
				//*****************************
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", FrameworkConfig.getDownloadsPath());
				chromePrefs.put("ssl.error_override_allowed", true);
				chromePrefs.put("safebrowsing.enabled", "false"); // to disable security check eg. Keep or
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors",
						"--log-path=chromedriver.log", "--verbose", "--user-agent=\"valid user agent :)\"");

				// TODO Adding this line in attempt to solve the browser spawning issue on
				// jenkins
				options.addArguments("--no-sandbox");
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("--disable-extensions"); // to disable browser extension popup
				options.addArguments("--use-fake-device-for-media-stream");
				options.addArguments("--use-fake-ui-for-media-stream");
				// Putting down a logic to render diff videos when thread id is even or odd
				String mediafile = "bus_cif_15fps.y4m";
				/*
				 * options.addArguments("--use-file-for-fake-video-capture=" +
				 * FrameworkConfig.getResourcesFolderPath() + "/" + mediafile);
				 */
				/*
				 * https://www.programcreek.com/java-api-examples/?class=org.openqa.selenium.
				 * chrome.ChromeOptions&method=addArguments
				 * https://www.programcreek.com/java-api-examples/?code=OpenVidu%2Fopenvidu%
				 * 2Fopenvidu-master%2Fopenvidu-test-browsers%2Fsrc%2Fmain%2Fjava%2Fio%
				 * 2Fopenvidu%2Ftest%2Fbrowsers%2FChromeUser.java
				 * prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
				 * prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
				 */
				//*****************************
				

				if (null == remoteURL || remoteURL.equals("")) {
					driver = new ChromeDriver(options);
				} else {
					options = options.merge(otherCapabilities);
					driver = new RemoteWebDriver(new URL(remoteURL), options);
				}
			} catch (Exception e) {
				new Custom_ExceptionHandler(e);
			}
			return driver;
		}

	},
	
	EDGE {
		public WebDriver getWebDriver(String capabilityJsonFilePath, String capabilityJsonObject, String remoteURL) {
			WebDriver driver = null;
			try {
				JSONManager json = new JSONManager(capabilityJsonFilePath, capabilityJsonObject);
				DesiredCapabilities otherCapabilities = new DesiredCapabilities(json.toHashMap());

				//EdgeOptions options = new EdgeOptions();
				//*****************************
				
				//*****************************
				//options.merge(otherCapabilities);

				if (null == remoteURL || remoteURL.equals("")) {
					driver = new EdgeDriver();
				} else {
					driver = new RemoteWebDriver(new URL(remoteURL), otherCapabilities);
				}
			} catch (Exception e) {
				new Custom_ExceptionHandler(e);
			}
			return driver;
		}

	},
	
	SAFARI {
		public WebDriver getWebDriver(String capabilityJsonFilePath, String capabilityJsonObject, String remoteURL) {
			WebDriver driver = null;
			try {
				JSONManager json = new JSONManager(capabilityJsonFilePath, capabilityJsonObject);
				DesiredCapabilities otherCapabilities = new DesiredCapabilities(json.toHashMap());

				SafariOptions options = new SafariOptions(otherCapabilities);
				//*****************************
				
				//*****************************
				//options.merge(otherCapabilities);

				if (null == remoteURL || remoteURL.equals("")) {
					driver = new SafariDriver(options);
				} else {
					driver = new RemoteWebDriver(new URL(remoteURL), options);
				}
			} catch (Exception e) {
				new Custom_ExceptionHandler(e);
			}
			return driver;
		}

	}
	
	

}
