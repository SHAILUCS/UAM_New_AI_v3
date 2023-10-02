package com.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.config.Config;
import com.prop.Prop;
import com.reporting.Reporter;

/**
 * This class is created to support Multi Lingual testing.
 * 
 * Use this class to get locale/language specific By objects from the .properties files
 * 
 * @param configFolderPath Folder path
 * 
 * @author Shailendra
 */
public class ElementLocale_Prop {

	private String configFolderPath;
	private String configFileName;

	/**
	 * Give the path of the FOLDER which contains all of the properties file, for
	 * each language
	 * 
	 * @param configFolderPath Folder path
	 * 
	 * @author Shailendra
	 */
	public ElementLocale_Prop(String configFolderPath) {
		this.configFolderPath = configFolderPath;
		this.configFileName = getFileName();
	}

	/**
	 * Gives you the language/locale specific By object of the passed key. 
	 * 
	 * Make sure to save the locators in the property file in locator_type:locator_value format.
	 * Allowed values for locator_type are xpath, class, css, id, link, name,
	 * partialLink, tag
	 * 
	 * @author Shailendra Jul 2023
	 */
	public By getLocator(String key) {
		// String configPath = Config.root + "/src/test/java/or/localization/" +
		// localeConfigFile;
		String configPath = configFolderPath + "/" + configFileName;
		String propLoc = Prop.getValue(configPath, key);
		if(propLoc.equals("PROP_ERROR")) {
			Assert.fail("Locale properties file not found, Please recheck the file path. Path = "+configPath);
		}
		
		By obj = null;
		String splitter = ":";
		
		if (propLoc.contains(splitter)) {
			String loc_type = propLoc.split(splitter)[0].trim();
			String loc_val = propLoc.split(splitter)[1].trim();
			obj = getByObj(loc_type, loc_val);
		} else {
			Assert.fail("For key ["+key+"], Locator value is not in this format [locator_type:locator_value], for example key = xpath:text[@id='myid']. File path= [" +configPath+"]");
		}

		return obj;
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

	private String getFileName() {
		String l = Config.getLocale();
		if (l == null) {
			Reporter.WARNING("Locale param is missing in testNg.xml file, setting [en] as default");
			l = "en";
		}
		return l + ".properties";
	}

}
