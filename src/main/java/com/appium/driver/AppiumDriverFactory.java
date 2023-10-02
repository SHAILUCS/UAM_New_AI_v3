package com.appium.driver;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import com.config.Config;
import com.config.FrameworkConfig;
import com.json.JSONManager;
import com.listener.TestNGKeys;
import com.prop.Prop;
import com.reporting.Reporter;
import com.selenium.Custom_ExceptionHandler;
import com.selenium.webdriver.Custom_WebDriverEventListener;
import com.selenium.webdriver.DriverFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumDriverFactory {

	private static final ThreadLocal<WebDriver> APPIUM_DRIVER_LIST = new ThreadLocal<>();
	private static final ThreadLocal<AppiumDriverLocalService> APPIUM_SERVER_LIST = new ThreadLocal<>();
	private static final ThreadLocal<String> PORT_LIST = new ThreadLocal<>();
	private static final String IP = "127.0.0.1";

	private static synchronized void setAppiumDriverLocalService(AppiumDriverLocalService service) {
		APPIUM_SERVER_LIST.set(service);
	}

	public static synchronized AppiumDriverLocalService getAppiumDriverLocalService() {
		return APPIUM_SERVER_LIST.get();
	}

	private static synchronized void setPort(String port) {
		PORT_LIST.set(port);
	}

	public static synchronized String getPort() {
		return PORT_LIST.get();
	}

	private static synchronized void setAppiumDriver(WebDriver driverApp) {
		APPIUM_DRIVER_LIST.set(driverApp);
		DriverFactory.setDriver(driverApp);
	}

	public static synchronized WebDriver getAppiumDriver() {
		return APPIUM_DRIVER_LIST.get();
	}

	public static synchronized void startAppiumServer(String jsonFileName, String jsonObjectName) {
		// String JSON_FILE_PATH = Config.ROOT + jsonFileName;
		// JSONManager json = new JSONManager(JSON_FILE_PATH,jsonObjectName);
		// String ip = json.getStr("ip");
		// String port = json.getStr("port");
		String port = initAppiumServer();
		// Reporter.INFO("Appium Server is started on port " + port);
	}

	/**
	 * this method will start appium server on the given ip and port
	 * 
	 */
	@Deprecated
	public static synchronized void startAppiumServer(String configFileName) {
		// String CONFIG_FILE_PATH = Config.ROOT + configFileName;
		// String port = Prop.getValue(CONFIG_FILE_PATH, "port");
		// String ip = Prop.getValue(CONFIG_FILE_PATH, "ip");
		String port = initAppiumServer();
		// Reporter.INFO("Appium Server is started on port " + port);
	}

	private static synchronized String initAppiumServer() {

		try {
			// String appiumLogfilePath = FrameworkConfig.getResultFolderPath() +
			// "/appiumLogs.txt";
			String appiumLogfilePath = FrameworkConfig.ROOT + "/appiumLogs.txt";

			AppiumServiceBuilder builder = new AppiumServiceBuilder();
			builder.withIPAddress(IP);
			// builder.usingPort(Integer.parseInt(port));
			builder.usingAnyFreePort();
			builder.withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");
			// builder.withArgument(GeneralServerFlag.ALLOW_INSECURE,
			// "chromedriver_autodownload");
			builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");
			// builder.withLogFile(new File(appiumLogfilePath));
			AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
			service.start();
			setAppiumDriverLocalService(service);
			String port = service.getUrl().toString();
			port = port.substring(port.indexOf(":", 5) + 1, port.indexOf("/wd"));
			setPort(port);
			System.out.println("Appium Server is started on port " + port);
			// Reporter.INFO("Appium Server is started on port " + port);
			return port;
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return null;
	}

	private static DesiredCapabilities getCapabilities_Json(JSONManager json) {
		DesiredCapabilities cap = new DesiredCapabilities(json.toHashMap());
		return cap;
	}

	@Deprecated
	private static DesiredCapabilities getCapabilities_Prop(String CONFIG_FILE_PATH) {

		// String emulatorDevice = Prop.getValue(CONFIG_FILE_PATH, "emulator");
		String platformName = Prop.getValue(CONFIG_FILE_PATH, "platformName");
		String appPackage = Prop.getValue(CONFIG_FILE_PATH, "appPackage");
		String appActivity = Prop.getValue(CONFIG_FILE_PATH, "appActivity");
		String automationName = Prop.getValue(CONFIG_FILE_PATH, "automationName");
		String deviceName = Prop.getValue(CONFIG_FILE_PATH, "deviceName");
		String platformVersion = Prop.getValue(CONFIG_FILE_PATH, "platformVersion");
		String udid = Prop.getValue(CONFIG_FILE_PATH, "udid");
		String avd = Prop.getValue(CONFIG_FILE_PATH, "avd");

		String appWaitForLaunch;
		if (Prop.containsKey(CONFIG_FILE_PATH, "appWaitForLaunch")) {
			appWaitForLaunch = Prop.getValue(CONFIG_FILE_PATH, "appWaitForLaunch");
		} else {
			appWaitForLaunch = false + "";
		}

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", platformName);
		cap.setCapability("appPackage", appPackage);
		cap.setCapability("appActivity", appActivity);
		cap.setCapability("automationName", automationName);
		cap.setCapability("deviceName", deviceName);
		cap.setCapability("platformVersion", platformVersion);
		cap.setCapability("appWaitForLaunch", Boolean.parseBoolean(appWaitForLaunch));
		cap.setCapability("udid", udid);
		cap.setCapability("avd", avd);
		return cap;

	}

	public static void startAppiumDriver(String jsonFileName, String jsonObjectName) {
		try {
			String JSON_FILE_PATH = Config.ROOT + jsonFileName;

			JSONManager json = new JSONManager(JSON_FILE_PATH, jsonObjectName);
			// String ip = json.getStr("ip");
			// String port = json.getStr("port");
			String port = getPort();

			DesiredCapabilities cap = getCapabilities_Json(json);
			URL url = new URL("http://" + IP + ":" + port + "/wd/hub");
			AndroidDriver original = new AndroidDriver(url, cap);

			WebDriverListener listener = new Custom_WebDriverEventListener();
			WebDriver decorated = new EventFiringDecorator<>(listener).decorate(original);
			setAppiumDriver(decorated);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	@Deprecated
	public static void startAppiumDriver(String configFileName) {
		try {
			String CONFIG_FILE_PATH = Config.ROOT + configFileName;
			String ip = Prop.getValue(CONFIG_FILE_PATH, "ip");
			String port = Prop.getValue(CONFIG_FILE_PATH, "port");

			DesiredCapabilities cap = getCapabilities_Prop(CONFIG_FILE_PATH);
			URL url = new URL("http://" + ip + ":" + port + "/wd/hub");
			WebDriver driverApp = new AndroidDriver(url, cap);
			setAppiumDriver(driverApp);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	public static void stopAppiumDriver() {
		if (getAppiumDriver() != null) {
			getAppiumDriver().quit();
		}
	}

	public static void stopAppiumServer() {
		if (getAppiumDriverLocalService() != null) {
			getAppiumDriverLocalService().stop();
		}
	}

	public static void startAppiumServer(HashMap<TestNGKeys, String> map) {
		String jsonFileName = map.get(TestNGKeys.JSON_FILE_NAME);
		String jsonObjectName = map.get(TestNGKeys.JSON_OBJECT_NAME);
		String platform_Desktop_Mobile = map.get(TestNGKeys.platform_Desktop_Mobile);
		
		if (null!=platform_Desktop_Mobile && !"".equals(platform_Desktop_Mobile.trim()) && "mobile".equals(platform_Desktop_Mobile.toLowerCase())) {
			Reporter.INFO("Starting the appium server for json config '" + jsonFileName + "' and capability '"
					+ jsonObjectName + "'");

			startAppiumServer(jsonFileName, jsonObjectName);
		}
	}

	public static void startAppiumDriver(HashMap<TestNGKeys, String> map) {
		String jsonFileName = map.get(TestNGKeys.JSON_FILE_NAME);
		String jsonObjectName = map.get(TestNGKeys.JSON_OBJECT_NAME);
		String platform_Desktop_Mobile = map.get(TestNGKeys.platform_Desktop_Mobile);
		
		if (null!=platform_Desktop_Mobile && !"".equals(platform_Desktop_Mobile.trim()) && "mobile".equals(platform_Desktop_Mobile.toLowerCase())) {
					Reporter.INFO("Starting the appium driver for json config '" + jsonFileName + "' and capability '"
					+ jsonObjectName + "'");

			startAppiumDriver(jsonFileName, jsonObjectName);
		}

	}

	public static String getDesiredCapabilities() {
		if (getAppiumDriver() != null) {
			return "";// getAppiumDriver().getCapabilities().asMap().toString();
		}
		return "";
	}

	public static String getDeviceName_Mobile() {
		if (getAppiumDriver() != null) {
			Capabilities cap = ((HasCapabilities) getAppiumDriver()).getCapabilities();
			return (String) cap.getCapability("deviceName");
		}
		return "";
	}

}
