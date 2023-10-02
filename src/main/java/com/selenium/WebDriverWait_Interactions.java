package com.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.config.Config;
import com.config.FrameworkConfig;
import com.reporting.Reporter;
import com.selenium.webdriver.DriverFactory;

class WebDriverWait_Interactions extends SelectCustom {


	
	/**
	 * if (element instanceof By) {
	 * wait.until(ExpectedConditions.visibilityOfElementLocated((By) element)); }
	 * else if (element instanceof WebElement) {
	 * wait.until(ExpectedConditions.visibilityOf((WebElement) element)); } An
	 * expectation for checking that an element is present on the DOM of a page and
	 * visible. Visibility means that the element is not only displayed but also has
	 * a height and width that is greater than 0. Also takes timeout as a parameter
	 * for applying the wait for element to get visible Will Take Snapshot
	 * 
	 * @param element        The By/WebElement Object
	 * @param timeoutSeconds used to wait for element visibility
	 * @param desc           element description to be display in report
	 * @return true - element is located and visible, false - element is not visible
	 *         within the passed timeout value
	 */
	public boolean waitForElementTobe_NotStaleAndPresent(By locator, long timeoutSeconds, String desc) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		boolean bool = false;
		try {
			WebDriver driver = DriverFactory.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
			bool = true;
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " is displayed");
				} else {
					Reporter.FAIL(
							desc + " is NOT displayed after waiting for '" + timeoutSeconds + "' seconds");
				}
			}
		}
		return bool;
	}

	/**
	 * see public boolean waitForElementTobeNotStaleAndPresent(Object element, long
	 * timeoutSeconds, String desc)
	 */
	public boolean waitForElementTobe_NotStaleAndPresent(By element, String desc) {
		return waitForElementTobe_NotStaleAndPresent(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}

	/**
	 * 
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * An expectation for checking that an element is NOT available on the DOM of a
	 * page it will wait for {@link Config}.wait seconds. Will take Snapshot, Will
	 * not run the Reporter
	 *
	 * @param element          The By/WebElement Object
	 * @param timeOutInSeconds Seconds to wait before returning false
	 * @return true - element got deleted within time, false - element is still
	 *         visible after timeout
	 */
	public boolean waitForElementTobe_NotVisible(Object element) {
		return waitForElementTobe_NotVisible(element, FrameworkConfig.WAIT_EXPLICIT, "");
	}

	/**
	 * 
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * An expectation for checking that an element is NOT available on the DOM of a
	 * page. Will take Snapshot
	 *
	 * @param element          The By/WebElement Object
	 * @param timeOutInSeconds Seconds to wait before returning false
	 * @return true - element got deleted within time, false - element is still
	 *         visible after timeout
	 */
	public boolean waitForElementTobe_NotVisible(Object element, long timeOutInSeconds) {
		return waitForElementTobe_NotVisible(element, timeOutInSeconds, "");
	}

	/**
	 * 
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * An expectation for checking that an element is NOT available on the DOM of a
	 * page it will wait for passed seconds. Will take Snapshot, Will run the
	 * Reporter
	 *
	 * <b>Will not throw/report exception[will return only true/false]</b>
	 *
	 * @param element          The By/WebElement Object
	 * @param timeOutInSeconds Seconds to wait before returning false
	 * @param desc             element description to be display in report
	 * @return true - element got deleted within time, false - element is still
	 *         visible after timeout
	 */
	public boolean waitForElementTobe_NotVisible(Object element, long timeOutInSeconds, String desc) {

		// Adding this wait for spinner Loading item to load
		wait(2);

		boolean bool = true;
		boolean runner = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();

		if (timeOutInSeconds == 0)
			timeOutInSeconds = 1;

		long counter = timeOutInSeconds;
		try {

			DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

			// In case element got deleted even before this line execution then
			// exception will trigger
			WebElement webObj = getWebElement(element);
			do {
				// In case element got deleted after few iteration, then on this
				// line exception will trigger
				if (webObj.isDisplayed()) {
					runner = true;
					wait(1);
					counter--;
				} else {
					runner = false;
				}

				if (counter < 1) {
					bool = false;
					runner = false;
				}
			} while (runner);

		} catch (Exception e) {
			bool = true;
			// new CustomExceptionHandler(e);
		} finally {
			//SnapshotManager.takeSnapShot(methodName);
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " is removed from the page");
				} else {
					Reporter.FAIL(
							desc + " is NOT removed after waiting for '" + timeOutInSeconds + "' seconds");
				}
			}
			DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConfig.WAIT_IMPLICIT));
		}
		return bool;
	}

	/**
	 * 
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * 
	 * An expectation for checking that all the elements matching the provided By
	 * Object are NOT available on the DOM of a page it will wait for passed
	 * seconds.
	 * 
	 * <b>Will not throw/report exception[will return only true/false]</b>
	 * 
	 * Will take Snapshot, Will run the Reporter
	 *
	 * @param element          The By Object
	 * @param timeOutInSeconds Seconds to wait before returning false
	 * @param desc             element description to be display in report
	 * @return true - element got deleted within timeout, false - element is still
	 *         visible after timeout
	 */
	public boolean waitForElementsTobe_NotVisible(Object element, long timeOutInSeconds, String desc) {

		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver driver = DriverFactory.getDriver();
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			By byObj = getByObjectFromWebElement(element);
			//wait.until(ExpectedConditions.numberOfElementsToBe(byObj, 0));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(byObj));
			bool = true;
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
		} finally {
			//SnapshotManager.takeSnapShot(methodName);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConfig.WAIT_IMPLICIT));
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( "All Elements of " + desc + " are removed from the page");
				} else {
					Reporter.FAIL( "All Elements of " + desc + " are NOT removed after waiting for '"
							+ timeOutInSeconds + "' seconds");
				}
			}
		}
		return bool;
	}

	/**
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	public boolean waitForElementsTobe_NotVisible(Object element, String desc) {
		return waitForElementsTobe_NotVisible(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}

	/**
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	public boolean waitForElementsTobe_NotVisible(Object element, long timeOutInSeconds) {
		return waitForElementsTobe_NotVisible(element, timeOutInSeconds, "");
	}

	/**
	 * <table border="1">
	 * <tbody>
	 * <tr>
	 * <th>Element Visible</th>
	 * <th>Timeout</th>
	 * <th>Return</th>
	 * </tr>
	 * <tr>
	 * <td>Yes</td>
	 * <td>Yes</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>No</td>
	 * <td>No</td>
	 * <td>True</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	public boolean waitForElementsTobe_NotVisible(Object element) {
		return waitForElementsTobe_NotVisible(element, FrameworkConfig.WAIT_EXPLICIT, "");
	}

	/**
	 * An expectation for checking that an element is NOT available on the DOM of a
	 * page it will wait for {@link Config}.wait seconds. Will take Snapshot, Will
	 * run the Reporter
	 *
	 * @param element The By/WebElement Object
	 * @return true - element got deleted within time, false - element is still
	 *         visible after timeout
	 */
	public boolean waitForElementTobe_NotVisible(Object element, String desc) {
		return waitForElementTobe_NotVisible(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}

	/**
	 * see public boolean waitForElementTobeVisible(Object element, long
	 * timeoutSeconds, String desc)
	 */
	public boolean waitForElementTobe_Visible(Object element) {
		return waitForElementTobe_Visible(element, FrameworkConfig.WAIT_EXPLICIT, "");
	}

	/**
	 * see public boolean waitForElementTobeVisible(Object element, long
	 * timeoutSeconds, String desc)
	 */
	public boolean waitForElementTobe_Visible(Object element, long timeoutSeconds) {
		return waitForElementTobe_Visible(element, timeoutSeconds, "");
	}

	/**
	 * <pre>
	 * if (element instanceof By) {
	 * 	wait.until(ExpectedConditions.visibilityOfElementLocated((By) element));
	 * } else if (element instanceof WebElement) {
	 * 	wait.until(ExpectedConditions.visibilityOf((WebElement) element));
	 * }
	 * </pre>
	 * 
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0. Also takes timeout as a
	 * parameter for applying the wait for element to get visible Will Take Snapshot
	 * 
	 * @param element        The By/WebElement Object
	 * @param timeoutSeconds used to wait for element visibility
	 * @param desc           element description to be display in report
	 * @return true - element is located and visible, false - element is not visible
	 *         within the passed timeout value
	 * 
	 * @throws TimeoutException
	 */
	public boolean waitForElementTobe_Visible(Object element, long timeoutSeconds, String desc) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebDriver driver = DriverFactory.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			if (element instanceof By) {
				wait.until(ExpectedConditions.visibilityOfElementLocated((By) element));
			} else if (element instanceof WebElement) {
				wait.until(ExpectedConditions.visibilityOf((WebElement) element));
			}
			bool = true;
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			//SnapshotManager.takeSnapShot(methodName);
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " is displayed");
				} else {
					Reporter.FAIL(
							desc + " is NOT displayed after waiting for '" + timeoutSeconds + "' seconds");
				}
			}
		}
		return bool;
	}
	

	/**
	 * Refer to waitForElementsTobe_Present(Object element, long timeoutSeconds,
	 * String desc)
	 */
	public boolean waitForElementsTobe_Present(Object element, long timeoutSeconds) {
		return waitForElementsTobe_Present(element, timeoutSeconds, "");
	}

	/**
	 * Refer to waitForElementsTobe_Present(Object element, long timeoutSeconds,
	 * String desc)
	 */
	public boolean waitForElementsTobe_Present(Object element, String desc) {
		return waitForElementsTobe_Present(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}

	/**
	 * Refer to waitForElementsTobe_Present(Object element, long timeoutSeconds,
	 * String desc)
	 */
	public boolean waitForElementsTobe_Present(Object element) {
		return waitForElementsTobe_Present(element, FrameworkConfig.WAIT_EXPLICIT, "");
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page. <code>
	 * 	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObj));
	 * </code> Will Take Snapshot Will print exception
	 * 
	 * @param element        : WebElement/By object
	 * @param timeoutSeconds : timeout to wait in case element is not found
	 * @param desc           : Description to be print in HTML report
	 * @return true if element is present
	 * @author shailendra.rajawat
	 */
	public boolean waitForElementsTobe_Present(Object element, long timeoutSeconds, String desc) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebDriver driver = DriverFactory.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			By byObj = null;
			if (element instanceof WebElement) {
				wait.until(ExpectedConditions.visibilityOf((WebElement) element));
			} else if (element instanceof By) {
				byObj = (By) element;
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObj));
			}

			bool = true;
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			//SnapshotManager.takeSnapShot(methodName);
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " is displayed");
				} else {
					Reporter.FAIL(
							desc + " is NOT displayed after waiting for '" + timeoutSeconds + "' seconds");
				}
			}
		}
		return bool;
	}


	public boolean waitFor_TitleContains(String string) {
		return waitFor_TitleContains(string, FrameworkConfig.WAIT_EXPLICIT,"");
	}

	public boolean waitFor_TitleContains(String title, long waitexplicit, String desc) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebDriver driver = DriverFactory.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitexplicit));
			wait.until(ExpectedConditions.titleContains(title));
			bool = true;
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " title is displayed");
				} else {
					Reporter.FAIL(
							desc + " title is NOT displayed after waiting for '" + waitexplicit + "' seconds");
				}
			}
		}
		return bool;
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * Gives TimeOutException and add exception details in the report
	 * 
	 * @Param : locator or WebElement
	 * @Return : the WebElement once it is located and clickable (visible and
	 *         enabled)
	 */
	public WebElement waitForElementTobe_Clickable(Object element, long timeoutSeconds, String desc) {
		WebElement webElem = null;
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			WebDriver driver = DriverFactory.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			if (element instanceof By) {
				webElem = wait.until(ExpectedConditions.elementToBeClickable((By) element));
			} else if (element instanceof WebElement) {
				webElem = wait.until(ExpectedConditions.elementToBeClickable((WebElement) element));
			}
			bool = true;
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " is Clickable");
				} else {
					Reporter.FAIL(
							desc + " is NOT Clickable after waiting for '" + timeoutSeconds + "' seconds");
				}
			}
		}
		return webElem;
	}

	/**
	 * Refer to waitForElementTobe_Clickable(Object element, long timeoutSeconds,
	 * String desc)
	 */
	public WebElement waitForElementTobe_Clickable(Object element, String desc) {
		return waitForElementTobe_Clickable(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}

	/**
	 * Refer to waitForElementTobe_Clickable(Object element, long timeoutSeconds,
	 * String desc)
	 */
	public WebElement waitForElementTobe_Clickable(Object element, long timeoutSeconds) {
		return waitForElementTobe_Clickable(element, timeoutSeconds, "");
	}

	/**
	 * Refer to waitForElementTobe_Clickable(Object element, long timeoutSeconds,
	 * String desc)
	 */
	public WebElement waitForElementTobe_Clickable(Object element) {
		return waitForElementTobe_Clickable(element, FrameworkConfig.WAIT_EXPLICIT, "");
	}

	/**
	 * see public boolean waitForElementTobeVisible(Object element, long
	 * timeoutSeconds, String desc)
	 */
	public boolean waitForElementTobe_Visible(Object element, String desc) {
		return waitForElementTobe_Visible(element, FrameworkConfig.WAIT_EXPLICIT, desc);
	}
	
}
