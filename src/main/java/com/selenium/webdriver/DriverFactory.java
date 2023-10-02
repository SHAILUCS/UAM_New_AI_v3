package com.selenium.webdriver;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.config.FrameworkConfig;
import com.listener.TestNGKeys;
import com.reporting.Reporter;
import com.selenium.Custom_ExceptionHandler;

public class DriverFactory {

	static Map<Integer, WebDriver> driverMap = new HashMap<Integer, WebDriver>();
	static Map<Integer, WebDriverWait> waitMap = new HashMap<Integer, WebDriverWait>();

	public static WebDriverWait getWait() {

		WebDriverWait wait = waitMap.get((int) (long) (Thread.currentThread().getId()));

		if (wait == null) {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(FrameworkConfig.WAIT_EXPLICIT));
			waitMap.put((int) (long) (Thread.currentThread().getId()), wait);
		}

		return wait;
	}
	
	/**
	 * Creating this method so that at run time we can set the WebDriver object
	 * This method is intended to be used from Appium Driver Factory class 
	 * */
	public static void setDriver(WebDriver driver) {
		driverMap.put((int) (long) (Thread.currentThread().getId()),driver);
	}

	public static WebDriver getDriver() {
		return driverMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void setUp(Map<TestNGKeys, String> testData) {

		String appType = testData.get(TestNGKeys.appType_Web_App);
		String platform = testData.get(TestNGKeys.platform_Desktop_Mobile);
		String jsonFilePath= testData.get(TestNGKeys.JSON_FILE_NAME);
		String jsonObjHeirarchy= testData.get(TestNGKeys.JSON_OBJECT_NAME);
		String remoteURL= testData.get(TestNGKeys.remoteURL);

		if (appType == null || appType.equals("") || appType.toLowerCase().contains("web")) {
			Reporter.INFO("Initializing the Web driver instance");

			//initiateWebDriver(testData);
			
			initiateWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
			WebDriver driver = getDriver();
			if (platform == null || platform.equals("") || platform.toLowerCase().contains("desktop")) {
					driver.manage().window().setSize(new Dimension(FrameworkConfig.WIDTH, FrameworkConfig.HEIGHT));
			}
			
			if (FrameworkConfig.WAIT_IMPLICIT >= 0) {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConfig.WAIT_IMPLICIT));
			}
			driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(FrameworkConfig.WAIT_EXPLICIT));			
		} 
	}

	public static String getCapabilities() {
		if (getDriver() == null) {
			return "";
		}

		return ((HasCapabilities) getDriver()).getCapabilities().asMap().toString();
	}

	public static void tearDown() {
		// System.out.println("Driver quit for Thread: "+Thread.currentThread().getId()
		// + getDriver().getTitle());
		WebDriver driver = getDriver();
		if (driver != null) {
			if(!driver.toString().contains("null")) {
				driver.quit();	
			}
		}
		driverMap.remove((int) (long) (Thread.currentThread().getId()));

	}
	
	
	private static synchronized void initiateWebDriver(String jsonFilePath, String jsonObjHeirarchy, String remoteURL) {
		WebDriver original = null;
		try {
		if (jsonObjHeirarchy.toLowerCase().contains("fire")) {
			original = CapabilityManager.FIREFOX.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
		} else if (jsonObjHeirarchy.toLowerCase().contains("edge")) {
			original = CapabilityManager.EDGE.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
		}else if (jsonObjHeirarchy.toLowerCase().contains("safari")) {
			original = CapabilityManager.SAFARI.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
		}else if (jsonObjHeirarchy.toLowerCase().contains("ch") && jsonObjHeirarchy.toLowerCase().contains("headless")) {
			original = CapabilityManager.CHROME_HEADLESS.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
		}else{
			original = CapabilityManager.CHROME.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
		}
		WebDriverListener listener = new Custom_WebDriverEventListener();
		WebDriver decorated = new EventFiringDecorator<>(listener).decorate(original);
		driverMap.put((int) (long) (Thread.currentThread().getId()), decorated);
		Reporter.INFO("BROWSER DETAILS: " + "" + getCapabilities() + "");
		}catch (Exception e) {
			new Custom_ExceptionHandler(e);
			Assert.fail("Error while creating web driver");
		}
	}

	

	public static String getBrowserVersion() {

		WebDriver d = getDriver();
		if (d != null) {
			Capabilities cap = ((HasCapabilities) d).getCapabilities();
			return cap.getBrowserVersion();
		}
		return "";
	}

	public static String getBrowserName() {
		WebDriver d = getDriver();

		if (d != null) {
			Capabilities cap = ((HasCapabilities) d).getCapabilities();
			return cap.getBrowserName();
		}
		return "";
	}

	public static String getOsName() {
		WebDriver d = getDriver();
		if (d != null) {
			Capabilities cap = ((HasCapabilities) d).getCapabilities();
			return cap.getPlatformName().name().toString();
		}
		return "";
	}

	public static String getOsVersion() {
		String v = "";
		WebDriver d = getDriver();
		if (d != null) {
			Capabilities cap = ((HasCapabilities) d).getCapabilities();
			v = (String) cap.getCapability("osVersion");
			/*
			 * if (cap.getPlatformName().getMajorVersion() != 0) { v =
			 * cap.getPlatformName().getMajorVersion() + ""; }
			 */
		}
		return v;
	}

	public static String getDeviceName_Mobile() {
		WebDriver d = getDriver();
		if (d != null) {
			Capabilities cap = ((HasCapabilities) d).getCapabilities();
			return (String) cap.getCapability("deviceName");
		}
		return "";
	}
	
	
	// Driver Object Method
		@Deprecated
		private static synchronized void initiateWebDriver(Map<TestNGKeys, String> map) {

			String platform = map.get(TestNGKeys.platform_Desktop_Mobile);
			String remoteURL = map.get(TestNGKeys.remoteURL);
			String browserName = map.get(TestNGKeys.browserName);
			String os_Desktop = map.get(TestNGKeys.os_Desktop);
			String deviceName_Mobile = map.get(TestNGKeys.deviceName_Mobile);
			String browserVersion = map.get(TestNGKeys.browserVersion);
			String osVersion = map.get(TestNGKeys.osVersion);
			// System.out.println("Driver fired for Thread:
			// "+Thread.currentThread().getId());

			WebDriver original = driverMap.get((int) (long) (Thread.currentThread().getId()));

			if (original == null) {
				try {
					if (null == remoteURL || remoteURL.equals("")) {

						// Setting the default browser value
						if (browserName == null || "".equals(browserName)) {
							browserName = "Chrome_rtc";
							Reporter.WARNING("Please provide proper browser value. Running the tests on default " + browserName);
						}

						// Running the tests on local machine
						if (browserName.toLowerCase().contains("fire") && browserName.toLowerCase().contains("rtc")) {
							FirefoxProfile profile = new FirefoxProfile();
							profile.setPreference("media.navigator.permission.disabled", true);
							profile.setPreference("media.autoplay.enabled.user-gestures-needed", false);
							profile.setPreference("media.navigator.streams.fake", true);
							
							FirefoxOptions options = new FirefoxOptions();
							options.setProfile(profile);
							original = new FirefoxDriver(options);
						}else if (browserName.toLowerCase().contains("fire")) {
							original = new FirefoxDriver();
						} else if (browserName.toLowerCase().contains("edge")) {
							original = new EdgeDriver();
						} else if (browserName.toLowerCase().contains("ch") && browserName.toLowerCase().contains("headless")) {
							HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
							chromePrefs.put("profile.default_content_settings.popups", 0);
							chromePrefs.put("download.default_directory", FrameworkConfig.getDownloadsPath());
							chromePrefs.put("ssl.error_override_allowed", true);
							ChromeOptions options = new ChromeOptions();
							// Latest code for headless chrome browser 21/10/2019 @author Shailendra
							options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors",
									"--log-path=chromedriver.log", "--verbose", "--user-agent=\"valid user agent :)\"");

							// TODO Adding this line in attempt to solve the browser spawning issue on
							// jenkins
							options.addArguments("--no-sandbox");

							options.setExperimentalOption("useAutomationExtension", false);
							options.setExperimentalOption("prefs", chromePrefs);
							DesiredCapabilities cap = new DesiredCapabilities();
							// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
							cap.setCapability(ChromeOptions.CAPABILITY, options);
							original = new ChromeDriver(options);

						} else if (browserName.toLowerCase().contains("ch") && browserName.toLowerCase().contains("rtc")) {

							HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
							chromePrefs.put("profile.default_content_settings.popups", 0);
							chromePrefs.put("download.default_directory", FrameworkConfig.getDownloadsPath());
							chromePrefs.put("ssl.error_override_allowed", true);

							chromePrefs.put("safebrowsing.enabled", "false"); // to disable security check eg. Keep or
																				// cancel button
							ChromeOptions options = new ChromeOptions();
							options.setExperimentalOption("prefs", chromePrefs);
							options.addArguments("--disable-extensions"); // to disable browser extension popup
							options.addArguments("--use-fake-device-for-media-stream");
							options.addArguments("--use-fake-ui-for-media-stream");
							// Putting down a logic to render diff videos when thread id is even or odd
							String mediafile = "bus_cif_15fps.y4m";
							/*
							 * if (Thread.currentThread().getId() % 2 == 0) { mediafile = "stefan_cif.y4m";
							 * }
							 

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

							DesiredCapabilities cap = new DesiredCapabilities();
							// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
							cap.setCapability(ChromeOptions.CAPABILITY, options);
							original = new ChromeDriver(options);

						} else if (browserName.toLowerCase().contains("ch")) {
							HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
							chromePrefs.put("profile.default_content_settings.popups", 0);
							chromePrefs.put("download.default_directory", FrameworkConfig.getDownloadsPath());
							chromePrefs.put("ssl.error_override_allowed", true);
							chromePrefs.put("safebrowsing.enabled", "false"); // to disable security check eg. Keep or
																				// cancel button
							ChromeOptions options = new ChromeOptions();
							options.setExperimentalOption("prefs", chromePrefs);
							options.addArguments("--disable-extensions"); // to disable browser extension popup
							DesiredCapabilities cap = new DesiredCapabilities();
							// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
							cap.setCapability(ChromeOptions.CAPABILITY, options);
							original = new ChromeDriver(options);
						}

					} else {

						// Running the tests on GRID/BROWSERSTACK/LAMBDATEST
						if (platform.equals("") || platform.toLowerCase().contains("desktop")) {

							platform = "desktop";

							if (os_Desktop.equals("") || osVersion.equals("") || browserName.equals("")
									|| browserVersion.equals("")) {
								browserName = "Safari";
								os_Desktop = "OS X";
								osVersion = "Big Sur";
								browserVersion = "latest";
							}

							Reporter.INFO("Running the tests on platform[" + platform + "] os[" + os_Desktop
									+ "] osVersion[" + osVersion + "] browserName[" + browserName + "] browserVersion["
									+ browserVersion + "]");
							MutableCapabilities capabilities = new MutableCapabilities();
							capabilities.setCapability("browserName", browserName);
							HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
							browserstackOptions.put("os", os_Desktop);
							browserstackOptions.put("osVersion", osVersion);
							browserstackOptions.put("browserVersion", browserVersion);
							browserstackOptions.put("local", "false");
							capabilities.setCapability("bstack:options", browserstackOptions);
							original = new RemoteWebDriver(new URL(remoteURL), capabilities);

						} else {
							platform = "mobile";

							if (deviceName_Mobile.equals("") || osVersion.equals("") || browserName.equals("")) {
								browserName = "Safari";
								deviceName_Mobile = "iPhone 14";
								osVersion = "16";
							}

							Reporter.INFO("Running the tests on platform[" + platform + "] deviceName_Mobile[" + deviceName_Mobile
									+ "] osVersion[" + osVersion + "] browserName[" + browserName + "]");
							MutableCapabilities capabilities = new MutableCapabilities();
							capabilities.setCapability("browserName", browserName);
							HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
							browserstackOptions.put("deviceName", deviceName_Mobile);
							browserstackOptions.put("osVersion", osVersion);
							browserstackOptions.put("local", "false");
							capabilities.setCapability("bstack:options", browserstackOptions);
							original = new RemoteWebDriver(new URL(remoteURL), capabilities);
						}

					}

					// System.out.println("Driver added in HashMap for Thread:
					// "+Thread.currentThread().getId());

					WebDriverListener listener = new Custom_WebDriverEventListener();
					WebDriver decorated = new EventFiringDecorator<>(listener).decorate(original);
					driverMap.put((int) (long) (Thread.currentThread().getId()), decorated);
					Reporter.INFO("BROWSER DETAILS: " + "" + getCapabilities() + "");

				} catch (Exception e) {
					new Custom_ExceptionHandler(e);
					Assert.fail("Error while creating web driver");
				}
			}
		}

}
