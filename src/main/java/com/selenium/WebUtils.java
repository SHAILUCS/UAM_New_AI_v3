package com.selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.reporting.Reporter;
import com.selenium.webdriver.DriverFactory;

class WebUtils {

	/**
	 * @author shailendra.rajawat
	 * @return webelement object
	 * @param element WebElement or By object This method takes WebElement or By
	 *                object as an input and then returns a WebElement object
	 */
	WebElement getWebElement(Object element) {
		WebElement elem = null;
		WebDriver driver = DriverFactory.getDriver();
		if (element instanceof By) {
			By byObj = (By) element;
			elem = driver.findElement(byObj);
		} else if (element instanceof WebElement) {
			elem = (WebElement) element;
		} else {
			Reporter.ERROR("Passed object must be of type By or WebElement. passed value : {type = "
					+ element.getClass().getSimpleName() + ", value=" + element + "}");
		}
		return elem;
	}

	/**
	 * From multiple list of WebElement, when only one is Clickable/visible. This
	 * method will return that element.
	 * 
	 * Lets say from the list first few are not Clickable/visible, then this method
	 * will wait for 0 second for each non-interactable element. and once the one
	 * element is found which is clickable/visible. It will return that
	 * 
	 * Note: This method will return the "first" interactable element.
	 * 
	 * @param element Can be WebElement/By Object, whose locator is finding multiple
	 *                elements on the web page. and only one is displayed at a time
	 * 
	 * @return WebElement which are clickable/visible and with which Interaction is
	 *         possible
	 *
	 */
	public WebElement getInteractableWebElementFromList(Object element) {
		WebElement elem = null;
		try {
			// Getting byObject from the passed element object, so that we can
			// apply findElements method on it
			By byObj = null;
			WebDriver driver = DriverFactory.getDriver();
			if (element instanceof By) {
				byObj = (By) element;
			} else if (element instanceof WebElement) {
				byObj = getByObjectFromWebElement((WebElement) element);
			}

			/*
			 * Applying the findElements method, to get all the web elements who have the
			 * same By Object
			 */
			List<WebElement> list = driver.findElements(byObj);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(0));

			// Looping through all web element objects to get the first
			// interactive element, by applying the checks
			for (WebElement webElement : list) {

				// Checking WebElement is clickable or not
				try {
					elem = wait.until(ExpectedConditions.elementToBeClickable(webElement));
					break;
				} catch (Exception e) {
				}

				// Checking that an element, known to be present on the
				// DOM of a page, isvisible. Visibility means that the element
				// is not only displayed but also has a height and width that is
				// greater than 0.
				try {
					elem = wait.until(ExpectedConditions.visibilityOf(webElement));
					break;
				} catch (Exception e) {
				}

				// Checking that an element has a height and width that is
				// greater than 0.
				/*
				 * try{ int height = webElement.getSize().getHeight(); int width =
				 * webElement.getSize().getWidth(); if(height > 0 && width > 0){ elem =
				 * webElement; break; } }catch (Exception e) {}
				 */
			}

			// Even after performing the above clickability and visibility
			// checks, due to some issue elem can be null, so in that case we
			// are throwing exception with all relevant details, this will
			// enhance the traceability of the actual issue
			if (elem == null) {
				new Custom_ExceptionHandler(new NullPointerException(),
						"getInteractableWebElementFromList() method: Element is null, By Object [" + byObj
								+ "] and size of webElements list [" + list.size() + "]");
			}

		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return elem;
	}

	/**
	 * When the WebElement Object is located, use this method to get the underlying
	 * By Object.
	 * 
	 * @param element Can be WebElement/By Object
	 * 
	 * @return By Object
	 *
	 */
	public By getByObjectFromWebElement(Object element) {

		if (element instanceof By) {
			return (By) element;
		}

		By byObj = null;
		try {
			String webElementInString = element.toString();
			int locatorStartIndex = -1, locatorEndIndex = -1, expressionStartIndex = -1, expressionEndIndex = -1;
			if (webElementInString.contains("Proxy element for")) {
				// Proxy element for: DefaultElementLocator 'By.cssSelector:
				// button[title='Close'][class*='fade-button']'
				locatorStartIndex = webElementInString.indexOf("By") + 3;
				locatorEndIndex = webElementInString.indexOf(":", locatorStartIndex);
				expressionStartIndex = locatorEndIndex + 1;
				expressionEndIndex = webElementInString.length() - 1;
			} else {
				// [[ChromeDriver: chrome on XP (d20ae2a31239671c5aad2f7789dc343e)] -> id:
				// P22_USERNAME]
				// [[ChromeDriver: chrome on XP (d20ae2a31239671c5aad2f7789dc343e)] -> xpath:
				// //a[.='Login'] | //button[.='Login']]
				// [[ChromeDriver: chrome on XP (900e5a8b2929a57615ca5873a347116a)] -> class
				// name: container]
				// [[ChromeDriver: chrome on XP (900e5a8b2929a57615ca5873a347116a)] -> css
				// selector: button]
				// [[ChromeDriver: chrome on XP (900e5a8b2929a57615ca5873a347116a)] -> link
				// text: Some Text In A Link]
				// [[ChromeDriver: chrome on XP (900e5a8b2929a57615ca5873a347116a)] -> name:
				// P22_USERNAME]
				// [[ChromeDriver: chrome on XP (900e5a8b2929a57615ca5873a347116a)] -> partial
				// link text: link]
				// [[ChromeDriver: chrome on XP (900e5a8b2929a57615ca5873a347116a)] -> tag name:
				// input]

				locatorStartIndex = webElementInString.indexOf("->") + 2;
				locatorEndIndex = webElementInString.indexOf(":", locatorStartIndex);
				expressionStartIndex = locatorEndIndex + 1;
				expressionEndIndex = webElementInString.length() - 1;
			}
			String LOCATOR = webElementInString.substring(locatorStartIndex, locatorEndIndex).trim().toLowerCase()
					.replaceAll(" ", "");
			String EXPRESSION = webElementInString.substring(expressionStartIndex, expressionEndIndex).trim();

			switch (LOCATOR) {
			case "xpath":
				byObj = By.xpath(EXPRESSION);
				break;
			case "id":
				byObj = By.id(EXPRESSION);
				break;
			case "classname":
				byObj = By.className(EXPRESSION);
				break;
			case "cssselector":
				byObj = By.cssSelector(EXPRESSION);
				break;
			case "linktext":
				byObj = By.linkText(EXPRESSION);
				break;
			case "name":
				byObj = By.name(EXPRESSION);
				break;
			case "partiallinktext":
				byObj = By.partialLinkText(EXPRESSION);
				break;
			case "tagname":
				byObj = By.tagName(EXPRESSION);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return byObj;
	}

	/**
	 * Pause the current Thread execution.
	 * 
	 * @param seconds The double seconds to pause the thread for fraction values
	 *                like 0.5 or 0.75 seconds.
	 */
	public void wait(double seconds) {
		int val = (int) (seconds * 1000);
		ThreadSleep(val);
	}

	/**
	 * Pause the current Thread execution.
	 * 
	 * @param seconds The int seconds to pause the thread for whole number values
	 *                like 1 or 5 seconds.
	 */
	public void wait(int seconds) {
		int val = seconds * 1000;
		ThreadSleep(val);
	}

	private void ThreadSleep(int microseconds) {
		try {
			Thread.sleep(microseconds);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

}
