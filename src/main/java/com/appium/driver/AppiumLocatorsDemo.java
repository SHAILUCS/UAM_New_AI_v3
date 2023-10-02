package com.appium.driver;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AppiumLocatorsDemo {

	public static void main(String[] args) {
		try {
			AppiumDriverFactory.startAppiumDriver("app-config-device1.properties");

			AndroidDriver driver = (AndroidDriver) AppiumDriverFactory.getAppiumDriver();

			WebElement el = null;

			/*
			 * XPATH - not recommended, due to its slowness. Use other platform-specific
			 * locator strategies such as UIAutomator(UiAutomator2), DataMatcher(Espresso),
			 * Predicate(iOs)
			 * 
			 * Android Page source <android.widget.ImageButton content-desc="key pad" />
			 * 
			 * iOS Page source --
			 */
			el = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc='key pad']"));
			System.out.println(el.getTagName());
			Thread.sleep(1000);
			el.click();
			Thread.sleep(1000);
			driver.navigate().back();

			/*
			 * ID - most recommended
			 * 
			 * Android Page source <android.widget.TextView
			 * resource-id="com.android.dialer:id/bottom_nav_item_text" />
			 * 
			 * iOS Page source <XCUIElementTypeOther name="bottom_nav_item_text">
			 */
			el = driver.findElement(AppiumBy.id("com.android.dialer:id/bottom_nav_item_text"));
			System.out.println(el.getText());

			/*
			 * ID - most recommended
			 * 
			 * Android Page source <android.widget.TextView
			 * resource-id="com.android.dialer:id/bottom_nav_item_text" />
			 * 
			 * iOS Page source <XCUIElementTypeOther name="bottom_nav_item_text">
			 */
			el = driver.findElement(AppiumBy.id("bottom_nav_item_text"));
			System.out.println(el.getText());

			/*
			 * ACCESSIBILITY ID - most recommended
			 * 
			 * Android Page source <android.widget.ImageButton content-desc="key pad" />
			 * 
			 * iOS Page source <XCUIElementTypeOther accessibility-id="key pad">
			 */
			el = driver.findElement(AppiumBy.accessibilityId("key pad"));
			el.click();
			Thread.sleep(1000);
			driver.navigate().back();

			/*
			 * CLASS NAME - recommended only when the element is dynamic and it is the only
			 * element on the page
			 * 
			 * Android Page source <android.widget.TextView class="android.widget.TextView"
			 * text="Search contacts" />
			 * 
			 * iOS Page source <XCUIElementTypeNavigationBar >
			 */
			el = driver.findElement(AppiumBy.className("android.widget.TextView"));
			el.click();
			Thread.sleep(1000);
			driver.navigate().back();
			Thread.sleep(1000);
			driver.navigate().back();
			Thread.sleep(1000);
			
			/*
			 * androidUIAutomator - Recommended, Android UIAutomator2 platform-specific locator strategy
			 * 
			 * Android Page source <android.widget.ImageButton content-desc="key pad" />
			 * 
			 * iOS Page source --
			 */
			el = driver.findElement(AppiumBy.androidUIAutomator(""));
			el.click();
			Thread.sleep(1000);
			driver.navigate().back();
			Thread.sleep(1000);
			driver.navigate().back();
			Thread.sleep(1000);

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AppiumDriverFactory.stopAppiumDriver();
		}

	}

}
