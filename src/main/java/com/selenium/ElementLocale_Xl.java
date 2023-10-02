package com.selenium;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.config.Config;
import com.reporting.Reporter;
import com.xl.ExcelManager;

/**
 * This class is created to support Multi Lingual testing.
 * 
 * Use this class to get locale/language specific By objects from the
 * .properties files
 * 
 * @param locatorXlFilePath Folder path
 * 
 * @author Shailendra
 */
public class ElementLocale_Xl {

	private String locatorXlFilePath;
	private String localeColumnName;
	private ExcelManager xl;
	private Map<String, By> loc_map;

	/**
	 * Give the path of the XlSX File which contains all of the locators for each
	 * language
	 * 
	 * @param locatorXlFilePath Folder path
	 * 
	 * @author Shailendra 29 Aug 2023
	 */
	public ElementLocale_Xl(String locatorXlFilePath) {
		loc_map = new HashMap<String, By>();
		this.locatorXlFilePath = locatorXlFilePath;
		this.localeColumnName = getColumnName();
		xl = new ExcelManager(locatorXlFilePath, 0);

		if (xl.getWorkbook() == null) {
			Assert.fail("Locators xlsx file not found, Please recheck the file path. Path = " + locatorXlFilePath);
		}

		initializeLocatorMap();
	}

	private void initializeLocatorMap() {
		for (int row = 1; row <= xl.getLastRowIndex(); row++) {
			String key_val = xl.getValue(row, "key");
			String loc_type = xl.getValue(row, "locator");
			String loc_val = xl.getValue(row, localeColumnName);
			By byObj = getByObj(loc_type, loc_val);
			if (!key_val.equals("")) {
				loc_map.put(key_val, byObj);
			}
		}
	}

	/**
	 * Gives you the language/locale specific By object of the passed key.
	 * 
	 * Make sure to save the locators in the property file in
	 * locator_type:locator_value format. Allowed values for locator_type are xpath,
	 * class, css, id, link, name, partialLink, tag
	 * 
	 * @author Shailendra Jul 2023
	 */
	public By getLocator(String key) {
		if (!loc_map.containsKey(key)) {
			Assert.fail("Passed [" + key + "] key is not found. File path= [" + locatorXlFilePath + "]");
			return null;
		}
		return loc_map.get(key);
	}

	private By getByObj(String loc_type, String loc_val) {
		switch (loc_type) {
		case "xpath":
			return By.xpath(loc_val);
		case "class":
			return By.className(loc_val);
		case "css":
			return By.cssSelector(loc_val);
		case "id":
			return By.id(loc_val);
		case "link":
			return By.linkText(loc_val);
		case "name":
			return By.name(loc_val);
		case "partialLink":
			return By.partialLinkText(loc_val);
		case "tag":
			return By.tagName(loc_val);
		}
		return null;
	}

	private String getColumnName() {
		String l = Config.getLocale();
		if (l == null || "".equals(l)) {
			Reporter.WARNING("Locale param is missing in testNg.xml file, setting [en] as default");
			l = "en";
		}
		return l + "_values";
	}

}
