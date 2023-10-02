package com.appium.driver;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reporting.Reporter;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AppiumDriverFactoryTester {
	
	//@Test
	public void OpenChromeAndLoadSomeUrlOnPhysicalDevice() {
		Reporter.INFO("Test5 started - Opening Chrome and running ");
		String jsonFileName = "app-config-devices.json";
		String jsonObjName = "physicalDeviceChromeCapabilities";
		AppiumDriverFactory.startAppiumServer(jsonFileName,jsonObjName);
		AppiumDriverFactory.startAppiumDriver(jsonFileName,jsonObjName);
		AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();
		try {
			Thread.sleep(10000);
			driver.get("https://www.google.com");
			System.out.println(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
			AppiumDriverFactory.stopAppiumServer();
		}
	}
	
	//@Test
	public void OpenChromeAndLoadSomeUrlOnEmulator() {
		Reporter.INFO("Test5 started - Opening Chrome and running ");
		String jsonFileName = "app-config-devices.json";
		String jsonObjName = "emulatorChromeCapabilities";
		AppiumDriverFactory.startAppiumServer(jsonFileName,jsonObjName);
		AppiumDriverFactory.startAppiumDriver(jsonFileName,jsonObjName);
		AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();
		try {
			Thread.sleep(10000);
			driver.get("https://www.google.com");
			System.out.println(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
			AppiumDriverFactory.stopAppiumServer();
		}
	}

	@Test
	public void test3_Json() {
		Reporter.INFO("Test3 started");
		String jsonFileName = "app-config-devices.json";
		String jsonObjName = "device1";
		AppiumDriverFactory.startAppiumServer(jsonFileName,jsonObjName);
		AppiumDriverFactory.startAppiumDriver(jsonFileName,jsonObjName);
		AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();
		try {
			Thread.sleep(10000);
			WebElement el = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc='key pad']"));
			el.click();
			System.out.println(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
			AppiumDriverFactory.stopAppiumServer();
		}
	}
	
	//@Test
	public void test4_Json() {
		Reporter.INFO("Test4 started - poco x2");
		String jsonFileName = "app-config-devices.json";
		String jsonObjName = "device2";
		AppiumDriverFactory.startAppiumServer(jsonFileName,jsonObjName);
		AppiumDriverFactory.startAppiumDriver(jsonFileName,jsonObjName);
		AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();
		try {
			Thread.sleep(10000);
			driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_1_s")).click();
			driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_plus_s")).click();
			driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_2_s")).click();
			driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_equal_s")).click();
			String op = driver.findElement(AppiumBy.id("com.miui.calculator:id/result")).getText();
			
			if(!op.contains("3")) {
				Assert.fail("FAILED RESULT NOT MATCH");
			}
			
			
			System.out.println(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
			AppiumDriverFactory.stopAppiumServer();
		}
	}
	
	//@Test
	public void test1() {
		Reporter.INFO("Test1 started");
		String configFileName = "app-config-device1.properties";
		AppiumDriverFactory.startAppiumServer(configFileName);
		AppiumDriverFactory.startAppiumDriver(configFileName);
		AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();
		try {
			Thread.sleep(10000);
			WebElement el = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc='key pad']"));
			el.click();
			System.out.println(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
			AppiumDriverFactory.stopAppiumServer();
		}
	}

	//@Test
	public void test2() {
		Reporter.INFO("Test2 started");
		String configFileName = "app-config-device2.properties";
		AppiumDriverFactory.startAppiumServer(configFileName);
		AppiumDriverFactory.startAppiumDriver(configFileName);
		AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();
		try {

			Thread.sleep(10000);
			WebElement el = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc='key pad']"));
			el.click();
			System.out.println(driver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
			AppiumDriverFactory.stopAppiumServer();
		}

	}

}
