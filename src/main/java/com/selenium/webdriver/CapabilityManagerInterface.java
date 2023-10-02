package com.selenium.webdriver;

import org.openqa.selenium.WebDriver;

public interface CapabilityManagerInterface {
	public WebDriver getWebDriver(String capabilityJsonFilePath, String capabilityJsonObject, String remoteURL) ;
}
