package com.selenium.webdriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.config.Config;
import com.json.JSONManager;
import com.listener.TestNGKeys;
import com.reporting.Reporter;

public class DriverFactoryTester {

	public static final String AUTOMATE_USERNAME = "shailendrarajawa_VdY2Ec";
	public static final String AUTOMATE_KEY = "B1eyFDQA4pfxXESH3g1D";
	public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_KEY
			+ "@hub-cloud.browserstack.com/wd/hub";

	public static void main(String[] args) {

		String jsonFilePath = Config.ROOT + "/config-web-bs-grid.json";

		driverFactoryTester_FromJson(jsonFilePath, "mac-big-sur-safari",URL); 
		driverFactoryTester_FromJson(jsonFilePath, "win-10-chrome",null);
		driverFactoryTester_FromJson(jsonFilePath, "win-11-chrome", URL);
		driverFactoryTester_FromJson(jsonFilePath, "win-10-firefox", null);
		driverFactoryTester_FromJson(jsonFilePath, "win-11-firefox", URL);
		driverFactoryTester_FromJson(jsonFilePath, "win-10-edge", null);
		driverFactoryTester_FromJson(jsonFilePath, "win-11-edge", URL);
		driverFactoryTester_FromJson(jsonFilePath, "win-10-chrome-headless", null);
		driverFactoryTester_FromJson(jsonFilePath, "win-11-chrome-headless", URL);
		driverFactoryTester_FromJson(jsonFilePath, "iphone-14-safari", URL);
		driverFactoryTester_FromJson(jsonFilePath, "samsung-s20-chrome", URL);
		
		//browserStack_FromJson(jsonFilePath, "iPhone-14-Safari");
		//browserStack_FromJson(jsonFilePath, "Samsung-Galaxy-S20-Chrome");
		//browserStack_FromJson(jsonFilePath, "Mac-Big-Sur-Safari");
		//browserStack_FromJson(jsonFilePath, "win-11-chrome");
		
		//driverFactoryTesterMethod();
		// browserStack();

	}
	
	private static void driverFactoryTester_FromJson(String jsonFilePath, String jsonObjHeirarchy, String remoteURL) {
		WebDriver driver = null;
		try {
			//String URL = "https://shailendrarajawa_VdY2Ec:B1eyFDQA4pfxXESH3g1D@hub-cloud.browserstack.com/wd/hub";

			if (jsonObjHeirarchy.toLowerCase().contains("fire")) {
				driver = CapabilityManager.FIREFOX.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
			} else if (jsonObjHeirarchy.toLowerCase().contains("edge")) {
				driver = CapabilityManager.EDGE.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
			}else if (jsonObjHeirarchy.toLowerCase().contains("safari")) {
				driver = CapabilityManager.SAFARI.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
			}else if (jsonObjHeirarchy.toLowerCase().contains("ch") && jsonObjHeirarchy.toLowerCase().contains("headless")) {
				driver = CapabilityManager.CHROME_HEADLESS.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
			}else{
				driver = CapabilityManager.CHROME.getWebDriver(jsonFilePath, jsonObjHeirarchy, remoteURL);
			}
			
			
			driver.get("https://www.google.com ");
			
			Reporter.PASS(jsonObjHeirarchy +"---"+ driver.getTitle());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(driver != null)
			driver.quit();
		}
	}

	private static void driverFactoryTesterMethod() {
		try {
			Map<TestNGKeys, String> m = new HashMap<TestNGKeys, String>();
			m.put(TestNGKeys.browserName, "chrome-rtc");
			DriverFactory.setUp(m);
			WebDriver driver = DriverFactory.getDriver();

			driver.get("https://www.google.com");
			driver.navigate().to("https://www.fb.com");
			driver.navigate().back();
			driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DriverFactory.tearDown();
		}
	}
	


	private static void browserStack_FromJson(String jsonFilePath, String jsonObjHeirarchy) {
		WebDriver driver = null;
		try {
			String URL = "https://shailendrarajawa_VdY2Ec:B1eyFDQA4pfxXESH3g1D@hub-cloud.browserstack.com/wd/hub";

			JSONManager json = new JSONManager(jsonFilePath, jsonObjHeirarchy);

			DesiredCapabilities cap = new DesiredCapabilities(json.toHashMap());

			driver = new RemoteWebDriver(new URL(URL), cap);

			driver.get("https://www.google.com");
			driver.getTitle();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			driver.quit();
		}
	}

	private static void browserStack() {
		try {
			String URL = "https://shailendrarajawa_VdY2Ec:B1eyFDQA4pfxXESH3g1D@hub-cloud.browserstack.com/wd/hub";

			MutableCapabilities capabilities = new MutableCapabilities();
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
			browserstackOptions.put("osVersion", "16");
			browserstackOptions.put("deviceName", "iPhone 14");
			browserstackOptions.put("projectName", "BlumDemo");
			browserstackOptions.put("buildName", "PocBuild");
			browserstackOptions.put("sessionName", "LocalizationTest");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("networkLogs", "true");

			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("bstack:options", browserstackOptions);

			WebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);

			driver.get("https://www.google.com");
			driver.getTitle();
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
