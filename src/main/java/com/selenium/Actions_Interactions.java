package com.selenium;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.reporting.Reporter;
import com.selenium.webdriver.DriverFactory;

class Actions_Interactions extends WebDriver_Interactions {


	public void doubleClick(WebElement element) {
		doubleClick(element, "");
	}

	public void doubleClick(WebElement element, String desc) {
		boolean bool = false;
		// StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		// String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			javaScript_ScrollIntoMIDDLEView_AndHighlight(elem);
			// SnapshotManager.takeSnapShot(methodName);
			Actions build = new Actions(DriverFactory.getDriver());
			build.moveToElement(elem).doubleClick().build().perform();
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
	}

	/**
	 * Click this element using Actions class method. If this causes a new page to load, you should discard all
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
	 * @return void
	 * @throws StaleElementReferenceException If the element no longer exists as
	 *                                        initially defined
	 */
	public void click_UsingAction(Object element, String desc) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		boolean bool = false;
		try {
			WebElement elem = getWebElement(element);
			//javaScript_ScrollIntoMIDDLEView_AndHighlight(elem);
			//SnapshotManager.takeSnapShot(methodName);
			Actions build = new Actions(DriverFactory.getDriver());
			build.moveToElement(elem).build().perform();
			wait(0.5);
			build.click(elem).build().perform();
			// build.moveToElement(elem).click().build().perform();
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
	}

	public void contextClick_UsingAction(Object element) {
		contextClick_UsingAction(element, "");
	}

	public void contextClick_UsingAction(Object element, String desc) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		boolean bool = false;
		try {
			WebElement elem = getWebElement(element);
			javaScript_ScrollIntoMIDDLEView_AndHighlight(elem);
			//SnapshotManager.takeSnapShot(methodName);
			Actions build = new Actions(DriverFactory.getDriver());
			build.contextClick(elem).build().perform();
			// build.moveToElement(elem).click().build().perform();
			bool = true;
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( "'" + desc + "' is context clicked");
				} else {
					Reporter.FAIL( "'" + desc + "' object is NOT context clicked, due to some exception");
				}
			}
		}
	}

	/**
	 * Click this element using Actions class method. If this causes a new page to load, you should discard all
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
	 * @return void
	 * @throws StaleElementReferenceException If the element no longer exists as
	 *                                        initially defined
	 */
	public void click_UsingAction(Object element) {
		click_UsingAction(element, "");
	}
	

	/**
	 * Call this method for performing the built sequence of steps.
	 */
	public void performAction(Action builtAction) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();

		try {
			builtAction.perform();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

}
