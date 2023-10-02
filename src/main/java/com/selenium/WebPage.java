package com.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.config.FrameworkConfig;
import com.reporting.Reporter;
import com.selenium.webdriver.DriverFactory;

/**
 * This class is holding all the methods that will perform operation on WebPage
 * using Selenium API. Also this class provide following additional
 * functionalities - Exception handling - Snapshots capturing - Reporting
 * 
 * Feel free to add new methods if not found here. Please follow the similar
 * structure of existing methods.
 * 
 * @author shailendra.rajawat
 * 
 */
public class WebPage extends WebDriverWait_Interactions {

	/**
	 * Clicks on the checkbox to select it only if it is not already checked
	 * 
	 * @param element A WebElement/By Object
	 * 
	 * @return True if the element is successfully selected
	 */
	public boolean check_Checkbox(Object element) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			if (!isSelected(elem)) {
				javaScript_Click(elem);
			}
			bool = true;
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return bool;
	}

	/**
	 * Clicks on the checkbox to deselect/uncheck it only if it is already checked
	 * 
	 * @param element A WebElement/By Object
	 * 
	 * @return True if the element is successfully selected
	 */
	public boolean uncheck_Checkbox(Object element) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			if (isSelected(elem)) {
				javaScript_Click(elem);
			}
			bool = true;
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return bool;
	}

	/**
	 * Clicks on the checkbox to select it, then check isSelected for verification,
	 * Runs Report
	 * 
	 * @param element A WebElement/By Object
	 * @param objName Name of the object to be display in report
	 * @return True if the element is successfully selected
	 */
	public boolean check_CheckboxAndConfirm(Object element, String objName) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			// elem.click();
			javaScript_Click(elem);
			if (elem.isSelected()) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (bool) {
				Reporter.PASS( "'" + objName + "' checkbox is successfully checked");
			} else {
				Reporter.FAIL( "'" + objName + "' checkbox is NOT checked");
			}
		}

		return bool;
	}

	/**
	 * checks whether some value is filled in textbox or not, Also run reporter.
	 * 
	 * @param element A WebElement/By Object
	 * @param objName Name of the Object, this will get printed in the report
	 * @return true if value attribute of textbox is not empty.
	 */
	public boolean checkBlank(Object element, String objName) {
		boolean bool = false;
		// String methodName = "checkBlank method";
		String val = "";
		try {
			WebElement elem = getWebElement(element);

			val = elem.getAttribute("value").trim();
			if (!"".equalsIgnoreCase(val)) {
				bool = true;
			}
			// //SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (bool) {
				Reporter.PASS( "Value: '" + val + "' is filled in \"" + objName + "\" field");
			} else {
				Reporter.FAIL( "Value: '" + val + "' is Not filled in \"" + objName + "\" field");
			}
		}
		return bool;
	}

	/**
	 * Click this element. If this causes a new page to load, you should discard all
	 * references to this element and any further operations performed on this
	 * element will throw a StaleElementReferenceException.
	 *
	 * Note that if click() is done by sending a native event (which is the default
	 * on most browsers/platforms) then the method will _not_ wait for the next page
	 * to load and the caller should verify that themselves.
	 *
	 * There are some preconditions for an element to be clicked. The element must
	 * be visible and it must have a height and width greater then 0.
	 *
	 * @param element The By/WebElement Object
	 * @return true if operation is successful
	 * @throws StaleElementReferenceException If the element no longer exists as
	 *                                        initially defined
	 */
	public boolean click(Object element) {
		return click(element, "");
	}

	/**
	 * Click this element. If this causes a new page to load, you should discard all
	 * references to this element and any further operations performed on this
	 * element will throw a StaleElementReferenceException.
	 *
	 * Note that if click() is done by sending a native event (which is the default
	 * on most browsers/platforms) then the method will _not_ wait for the next page
	 * to load and the caller should verify that themselves.
	 *
	 * There are some preconditions for an element to be clicked. The element must
	 * be visible and it must have a height and width greater then 0.
	 *
	 * @param element The By/WebElement Object
	 * @param desc    element description to be display in report
	 * @return true if operation is successful
	 * @throws StaleElementReferenceException If the element no longer exists as
	 *                                        initially defined
	 */
	public boolean click(Object element, String desc) {
		
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			//javaScript_ScrollIntoMIDDLEView_AndHighlight(elem);
			//SnapshotManager.takeSnapShot(methodName);
			elem.click();
			bool = true;
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( "'" + desc + "' is clicked");
				} else {
					Reporter.FAIL( "'" + desc + "' object is NOT clicked, due to some exception");
				}
			}
		}
		return bool;
	}

	/**
	 * Get the value of the given attribute of the element. Will return the current
	 * value, even if this has been modified after the page has been loaded.
	 *
	 * <p>
	 * More exactly, this method will return the value of the property with the
	 * given name, if it exists. If it does not, then the value of the attribute
	 * with the given name is returned. If neither exists, null is returned.
	 *
	 * <p>
	 * The "style" attribute is converted as best can be to a text representation
	 * with a trailing semi-colon.
	 *
	 * <p>
	 * The following are deemed to be "boolean" attributes, and will return either
	 * "true" or null:
	 *
	 * <p>
	 * async, autofocus, autoplay, checked, compact, complete, controls, declare,
	 * defaultchecked, defaultselected, defer, disabled, draggable, ended,
	 * formnovalidate, hidden, indeterminate, iscontenteditable, ismap, itemscope,
	 * loop, multiple, muted, nohref, noresize, noshade, novalidate, nowrap, open,
	 * paused, pubdate, readonly, required, reversed, scoped, seamless, seeking,
	 * selected, truespeed, willvalidate
	 *
	 * <p>
	 * Finally, the following commonly mis-capitalized attribute/property names are
	 * evaluated as expected:
	 *
	 * <ul>
	 * <li>If the given name is "class", the "className" property is returned.
	 * <li>If the given name is "readonly", the "readOnly" property is returned.
	 * </ul>
	 *
	 * <i>Note:</i> The reason for this behavior is that users frequently confuse
	 * attributes and properties. If you need to do something more precise, e.g.,
	 * refer to an attribute even when a property of the same name exists, then you
	 * should evaluate Javascript to obtain the result you desire.
	 *
	 * @param element The By/WebElement Object
	 * @param name    The name of the attribute.
	 * @return The attribute/property's current value or null if the value is not
	 *         set.
	 */
	public String getAttribute(Object element, String name) {
		String val = null;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		if (name.toLowerCase().contains("innertext")) {
			val = getText(element);
		} else {
			try {
				WebElement elem = getWebElement(element);
				val = elem.getAttribute(name);
				//SnapshotManager.takeSnapShot(methodName);
			} catch (Exception e) {
				new Custom_ExceptionHandler(e, element.toString() + " Attribute " + name);
			}
		}
		return val;
	}

	/**
	 * @param element
	 * @param txt
	 */
	public void verifyTextFieldContainsValue(Object element, String txt) {
		String val = null;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			//SnapshotManager.takeSnapShot(methodName);
			val = elem.getAttribute("outerHTML");
			if (val.toLowerCase().contains(txt.toLowerCase())) {
				Reporter.PASS( txt + " is present in textfield");
			} else {
				Reporter.FAIL( txt + " is NOT present in textfield");
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e, element.toString() + " : " + txt);
		}
	}

	/**
	 * Get the visible (i.e. not hidden by CSS) innerText of this element, including
	 * sub-elements, without any leading or trailing whitespace.
	 * 
	 * @param element The By/WebElement Object
	 * @return The innerText of this element.
	 */
	public String getText(Object element) {
		String str = "";
		/*
		 * //StackTraceElement[] ste =Thread.currentThread().getStackTrace(); String
		 * methodName=ste[1].getMethodName();
		 */
		try {
			WebElement elem = getWebElement(element);
			str = elem.getText().trim();
			// javaScriptScrollIntoViewAndHighlight(element);
			// //SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {

			new Custom_ExceptionHandler(e);
		}
		return str;
	}

	/**
	 * Is the element currently enabled or not? This will generally return true for
	 * everything but disabled input elements.
	 * 
	 * @param element A WebElement/By Object
	 * @return True if the element is enabled, false otherwise.
	 */
	public boolean isDisplayed(Object element) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			if (elem.isDisplayed()) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return bool;
	}

	/**
	 * Overloaded method of isElementPresent(Object webelement_obj, String desc)
	 * Will NOT take Snapshot, Will NOT run Reporter
	 * 
	 * @param element The By/WebElement Object
	 * @return True if the element is enabled or is displayed.
	 * 
	 */
	public boolean isElementPresent(Object element) {
		return isElementPresent(element, "");

	}

	/**
	 * Is this element (Displayed || Enabled) or not? This method avoids the problem
	 * of having to parse an element's "style" attribute. Or Is the element
	 * currently enabled or not? This will generally return true for everything but
	 * disabled input elements. Will NOT take Snapshot, Will run Reporter
	 *
	 * @param element The By/WebElement Object
	 * @param desc    element description to be display in report
	 * @return True if the element is enabled or is displayed.
	 * 
	 */
	public boolean isElementPresent(Object element, String desc) {
		boolean bool = false;
		boolean reportFlag = false;
		WebElement elem = null;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			elem = getWebElement(element);
			if (elem.isDisplayed() || elem.isEnabled()) {
				bool = true;
				reportFlag = true;
			}
			javaScript_ScrollByPercent_AndHighlight(element, 50);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			bool = false;
			reportFlag = false;
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (reportFlag) {
					Reporter.PASS( "'" + desc + "' is displayed");
				} else {
					Reporter.FAIL( "'" + desc + "' is NOT displayed");
				}
			}
		}

		return bool;
	}

	/**
	 * Is the element currently enabled or not? This will generally return true for
	 * everything but disabled input elements.
	 * 
	 * @param element A WebElement/By Object
	 * @return True if the element is enabled, false otherwise.
	 */
	public boolean isEnabled(Object element) {
		return isEnabled(element, "");
	}

	/**
	 * Is the element currently enabled or not? This will generally return true for
	 * everything but disabled input elements.
	 * 
	 * @param element A WebElement/By Object
	 * @return True if the element is enabled, false otherwise.
	 */
	public boolean isEnabled(Object element, String desc) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			if (elem.isEnabled()) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( "'" + desc + "' is displayed as enabled");
				} else {
					Reporter.FAIL( "'" + desc + "' is NOT displayed as enabled");
				}
			}
		}
		return bool;
	}

	/**
	 * Determine whether or not this element is selected or not. This operation only
	 * applies to input elements such as checkboxes, options in a select and radio
	 * buttons.
	 * 
	 * @param element A WebElement/By Object
	 * @return True if the element is currently selected or checked, false
	 *         otherwise.
	 */
	public boolean isSelected(Object element) {
		return isSelected(element, "");
	}

	/**
	 * Determine whether or not this element is selected or not. This operation only
	 * applies to input elements such as checkboxes, options in a select and radio
	 * buttons.
	 * 
	 * @param element A WebElement/By Object
	 * @return True if the element is currently selected or checked, false
	 *         otherwise.
	 */
	public boolean isSelected(Object element, String desc) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			if (elem.isSelected()) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( "'" + desc + "' is displayed as selected");
				} else {
					Reporter.FAIL( "'" + desc + "' is NOT displayed as selected");
				}
			}
		}
		return bool;
	}

	/** Refer to isClickable(Object element, long timeOutInSeconds, String desc) */
	public boolean isClickable(Object element) {
		return isClickable(element, FrameworkConfig.WAIT_EXPLICIT, "");
	}

	/** Refer to isClickable(Object element, long timeOutInSeconds, String desc) */
	public boolean isClickable(Object element, long timeOutInSeconds) {
		return isClickable(element, timeOutInSeconds, "");
	}

	/** Refer to isClickable(Object element, long timeOutInSeconds, String desc) */
	public boolean isClickable(Object element, String desc) {
		return isClickable(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}

	/**
	 * 
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Clickable</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * 
	 * An expectation for checking that the element is clickable, and it will wait
	 * for passed seconds.
	 * 
	 * <b>Will not throw/report exception[will return only true/false]</b>
	 * 
	 * Will take Snapshot, Will run the Reporter
	 *
	 * @param element          The By/WebElement Object
	 * @param timeOutInSeconds Seconds to wait before returning false
	 * @param desc             element description to be display in report
	 * @return true - element got deleted within timeout, false - element is still
	 *         visible after timeout
	 */
	public boolean isClickable(Object element, long timeOutInSeconds, String desc) {

		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver driver = DriverFactory.getDriver();
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			if (element instanceof By) {
				wait.until(ExpectedConditions.elementToBeClickable((By) element));
			} else if (element instanceof WebElement) {
				wait.until(ExpectedConditions.elementToBeClickable((WebElement) element));
			}
			bool = true;
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
		} finally {
			//SnapshotManager.takeSnapShot(methodName);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConfig.WAIT_IMPLICIT));
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( "Element " + desc + " is clickable");
				} else {
					Reporter.FAIL( "Element " + desc + " is NOT Clickable even after waiting for '"
							+ timeOutInSeconds + "' seconds");
				}
			}
		}
		return bool;
	}
	/**
	 * Will click on the passed object then verify the title of the page, Will Run
	 * the reporter.
	 * 
	 * @param element A WebElement/By object
	 * @param title   Expected page title string(pass exact value)
	 * @return true if title matched with the expected value
	 */
	public boolean navigateToAndVerifyPageTitle(Object element, String title) {
		Reporter.NODE("Navigating to [" + title + "] page");
		boolean bool = false;
		// if(isElementPresent(obj,"link for '"+desc+"' Page ")){
		click(element);
		if (verifyPageTitle(title, true)) {
			bool = true;
		}
		// }
		return bool;
	}

	/**
	 * Will click on the passed object then verify the URL of the page, Will Run the
	 * reporter.
	 * 
	 * @param element A WebElement/By object
	 * @param url     Expected page title string(pass exact value)
	 * @return true if title matched with the expected value
	 */
	public boolean navigateToAndVerifyPageUrl(Object element, String url) {
		Reporter.NODE("Navigating to [" + url + "] page");
		boolean bool = false;
		// if(isElementPresent(obj,"link for '"+desc+"' Page ")){
		click(element);
		if (verifyPageUrl(url, true)) {
			bool = true;
		}
		// }
		return bool;
	}

	/**
	 * Use this method to simulate typing into an element, which may set its value.
	 *
	 * @param element    The By/WebElement Object
	 * @param desc       element description to be display in report
	 * @param keysToSend character sequence to send to the element you can send
	 *                   multiple comma separated values for e.g.
	 *                   sendKeys(webElementobj,"HelloWorld", {@link Keys}.DOWN,
	 *                   {@link Keys}.ENTER)
	 */
	public boolean sendKeys(Object element, CharSequence... keysToSend) {
		return sendKeys("", element, keysToSend);
	}

	/**
	 * Use this method to simulate typing into an element, which may set its value.
	 *
	 * @param element    The By/WebElement Object
	 * @param desc       element description to be display in report
	 * @param keysToSend character sequence to send to the element you can send
	 *                   multiple comma separated values for e.g.
	 *                   sendKeys(webElementobj,"HelloWorld", {@link Keys}.DOWN,
	 *                   {@link Keys}.ENTER)
	 */
	public boolean sendKeys(String desc, Object element, CharSequence... keysToSend) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			elem.sendKeys(keysToSend);
			bool = true;
			//SnapshotManager.takeSnapShot(methodName);

		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				checkBlank(element, desc);
			}
		}
		return bool;
	}

	/**
	 * If this element is a text entry element, this will clear the value. Has no
	 * effect on other elements. Text entry elements are INPUT and TEXTAREA
	 * elements. Note that the events fired by this event may not be as you'd
	 * expect. In particular, we don't fire any keyboard or mouse events. If you
	 * want to ensure keyboard events are fired, consider using something like
	 * sendKeys(CharSequence) with the backspace key. To ensure you get a change
	 * event, consider following with a call to sendKeys(CharSequence) with the tab
	 * key.
	 * 
	 */
	public void clear(Object element) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			elem.clear();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Clicks on the checkbox to de-select it, then check isSelected for
	 * verification, Runs Report
	 * 
	 * @param element A WebElement/By Object
	 * @param objName Name of the object to be display in report
	 * @return True if the element is successfully de-selected
	 */
	public boolean uncheck_CheckboxAndConfirm(Object element, String objName) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			elem.click();
			if (!elem.isSelected()) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (bool) {
				Reporter.PASS( "'" + objName + "' checkbox is successfully un-checked");
			} else {
				Reporter.FAIL( "'" + objName + "' checkbox is NOT un-checked");
			}
		}

		return bool;
	}

	/**
	 * It will verify that the passed element is enabled or not
	 * 
	 * @param element A WebElement/By Object
	 * @param desc    the description of element
	 */
	public void verifyObjectIsEnabled(Object element, String desc) {
		if (isEnabled(element)) {
			Reporter.PASS( desc + " is Enabled");
		} else {
			Reporter.FAIL( desc + " is Not Enabled");
		}
	}

	/**
	 * It will verify that the passed element is disabled or not
	 * 
	 * @param element A WebElement/By Object
	 * @param desc    the description of element
	 */
	public void verifyObjectIsDisabled(Object element, String desc) {
		if (isEnabled(element)) {
			Reporter.FAIL( desc + " is Not Disabled");
		} else {
			Reporter.PASS( desc + " is Disabled");
		}
	}

}
