package com.selenium;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.config.Config;
import com.config.FrameworkConfig;
import com.reporting.Reporter;
import com.selenium.webdriver.DriverFactory;

class WebDriver_Interactions extends WebUtils {

	/**
	 * Overloaded method of getDynamicElement(By byObj,boolean trueForSnapshot),
	 * Will not take snapshot
	 *
	 * @param byObj The By Object, The locating mechanism
	 * @return The first matching element on the current page
	 * 
	 */
	public WebElement findElement(Object parentElem, By childByObj) {
		return findElement(parentElem, childByObj, "");
	}

	/**
	 * Find the child element of the passed Web Element, Also Takes snapshot if
	 * parameter is set true
	 * 
	 * @param elem  The WebElement Object, The WebElement whose child is required
	 * @param byObj The By Object, The locating mechanism of child Object
	 * @param desc  element description to be display in report
	 * @return The first matching child element of the passed parent WebElement
	 * @throws NoSuchElementException If no matching elements are found
	 */
	public WebElement findElement(Object parentElem, By childByObj, String desc) {
		boolean reportFlag = false;
		WebElement temp = null;
		// StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		// String methodName = ste[1].getMethodName();
		try {
			WebElement parentWebElem = getWebElement(parentElem);
			temp = parentWebElem.findElement(childByObj);
			reportFlag = true;

		} catch (Exception e) {
			new Custom_ExceptionHandler(e, "Element with locator [" + childByObj + "] is Not found");
		} finally {
			if (!desc.equals("")) {
				if (reportFlag) {
					Reporter.PASS( desc + " element is found");
				} else {
					Reporter.FAIL( desc + " element is NOT found");
				}
			}
		}
		return temp;
	}

	/**
	 * Find the first matching element on the current page, Also Takes snapshot if
	 * parameter is set true This method is affected by the '{@link Config}.wait'
	 * times in force at the time of execution. It will return a matching element,
	 * or try again repeatedly until the configured timeout is reached.
	 *
	 * @param byObj           The By Object, The locating mechanism
	 * @param trueForSnapshot pass true if you want to take snapshot
	 * @return The first matching element on the current page
	 * @throws NoSuchElementException If no matching elements are found
	 */
	public WebElement findElement(By byObj) {
		return findElement(byObj, "");
	}

	/**
	 * Find the first matching element on the current page, Also Takes snapshot if
	 * parameter is set true This method is affected by the '{@link Config}.wait'
	 * times in force at the time of execution. It will return a matching element,
	 * or try again repeatedly until the configured timeout is reached.
	 *
	 * @param byObj           The By Object, The locating mechanism
	 * @param desc            element description to be display in report
	 * @param trueForSnapshot pass true if you want to take snapshot
	 * @return The first matching element on the current page
	 * @throws NoSuchElementException If no matching elements are found
	 */
	public WebElement findElement(By byObj, String desc) {
		WebElement temp = null;
		boolean reportFlag = false;
		// StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		// String methodName = ste[1].getMethodName();
		try {
			WebDriver driver = DriverFactory.getDriver();
			temp = driver.findElement(byObj);
			reportFlag = true;

		} catch (Exception e) {
			new Custom_ExceptionHandler(e, "Element with locator [" + byObj + "] is Not found");
		} finally {
			if (!desc.equals("")) {
				if (reportFlag) {
					Reporter.PASS( desc + " is found");
				} else {
					Reporter.FAIL( desc + " is NOT found");
				}
			}
		}
		return temp;
	}

	/**
	 * Find all elements within the current page using the given mechanism based on
	 * count of with given locator. This method is affected by the
	 * '{@link Config}.wait' times in force at the time of execution. When waiting,
	 * this method will return as soon as there are more than 0 items in the found
	 * collection, or will return an empty list if the timeout is reached.
	 *
	 * @param byObj The locating mechanism to use
	 * @return A list of all {@link WebElement}s, or an empty list if nothing
	 *         matches
	 * @see org.openqa.selenium.By
	 * @see org.openqa.selenium.support.ui.WebDriverWait
	 */
	public List<WebElement> findElements(By byObj) {
		List<WebElement> temp = new ArrayList<WebElement>();
		// StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		// String methodName = ste[1].getMethodName();
		try {
			WebDriver driver = DriverFactory.getDriver();
			temp = driver.findElements(byObj);
			// SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return temp;
	}
	
	/**
	 * Will execute whatever java script you pass in script param, args are not
	 * mandatory if the script do not use them. When calling this method please
	 * consider If the script is returning a single WebElement then put a type cast
	 * like this (WebElement)executeScript(). And if the script is returning a list
	 * of WebElements then put a type cast like this
	 * (List&#60;WebElement&#62;)executeScript()
	 * 
	 * Executes JavaScript in the context of the currently selected frame or window.
	 * The script fragment provided will be executed as the body of an anonymous
	 * function.
	 * 
	 * Within the script, use document to refer to the current document. Note that
	 * local variables will not be available once the script has finished executing,
	 * though global variables will persist.
	 * 
	 * 
	 * @param script Java script may or may not return any value
	 * @param args   Arguments must be a number, a boolean, a String, WebElement, or
	 *               a List of any combination of the above. An exception will be
	 *               thrown if the arguments do not meet these criteria. The
	 *               arguments will be made available to the JavaScript via the
	 *               "arguments" magic variable, as if the function were called via
	 *               "Function.apply"
	 * 
	 * @return Object, must be type cast to the desired object type. If the script
	 *         has a return value (i.e. if the script contains a return
	 *         statement),then the following steps will be taken:
	 * 
	 *         <pre>
	 * •For an HTML element, this method returns a WebElement 
	 * •For a decimal, a Double is returned 
	 * •For a non-decimal number, a Long is returned 
	 * •For a boolean, a Boolean is returned
	 * •For all other cases, a String is returned. 
	 * •For an array, return a List&#60;Object&#62; with each object following the rules above. We support nested lists. 
	 * •For a map, return a Map<String, Object> with values following the rules above. 
	 * •Unless the value is null or there is no return value, in which null is returned
	 *         </pre>
	 * 
	 * 
	 */
	public Object executeScript(String script, Object... args) {
		// StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		// String methodName = ste[1].getMethodName();
		try {
			return ((JavascriptExecutor) DriverFactory.getDriver()).executeScript(script, args);
			// SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return null;
	}

	/**
	 * Will perform java script click on the passed element Object
	 * 
	 * @param element A WebElement/By object
	 * 
	 */
	public void javaScript_Click(Object element) {
		javaScript_Click(element, "");
	}

	public void javaScript_Click(Object element, String desc) {
		boolean bool = false;
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", elem);
			// SnapshotManager.takeSnapShot(methodName);
			bool = true;
		} catch (Exception e) {
			//new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		} finally {
			if (!desc.equals("")) {
				if (bool) {
					Reporter.PASS( desc + " is clicked");
				} else {
					Reporter.FAIL( desc + " is NOT clicked");
				}

			}
		}

	}

	/**
	 * Bring the Element into Screen BOTTOM view only.
	 * 
	 * @param element A WebElement/By object
	 */
	public void javaScript_ScrollIntoBOTTOMView(Object element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(false);", elem);
			// SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * javaScript_ScrollIntoBOTTOMView_AndHighlight Bring the Element into Screen
	 * MIDDLE view, and also changes its background to Yellow
	 * 
	 * @param element A WebElement/By object
	 */

	public void javaScript_ScrollIntoMIDDLEView_AndHighlight(Object element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(false);", elem);// Scroll
																														// to
																														// bottom
																														// visible
																														// area
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollTo(0,0);");// Scroll window to
																									// top
			// top
			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("" + "var ele=arguments[0].getBoundingClientRect().top;"
							+ "var win=window.innerHeight;" + "window.scrollTo(0,((ele-win/3)/1.2));" // Scroll window
							// to Object so
							// that it comes
							// just above
							// from bottom
							// part
							+ "", elem);
			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("var st = arguments[0].style;" + "st.color = 'red';" + "st.background = 'yellow';"
							+ "st.borderBottom = '1px solid red';" + "st.borderTop = '1px solid red';", elem);
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * javaScript_ScrollIntoBOTTOMView_AndHighlight Bring the Element into Screen
	 * MIDDLE view, and also changes its background to Yellow
	 * 
	 * @param element A WebElement/By object
	 */

	public void javaScript_ScrollIntoMIDDLEView(Object element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			// ((JavascriptExecutor)
			// DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(false);",elem);//Scroll
			// to bottom visible area
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollTo(0,0);");// Scroll window to
																									// top
			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("" + "var ele=arguments[0].getBoundingClientRect().top;"
							+ "var win=window.innerHeight;" + "window.scrollTo(0,((ele-win/3)/1.2));" // Scroll window
																										// to Object so
																										// that it comes
																										// just above
																										// from bottom
																										// part
							+ "", elem);
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * Bring the Element into Screen view port by percent
	 * 
	 * @param element      A WebElement/By object
	 * @param percentValue define the percent value to scroll, if it is set to 20%
	 *                     then element will be scrolled to 20% from top
	 */
	public void javaScript_ScrollByPercent_AndHighlight(Object element, long percent) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			// Scroll window to top
			/*
			 * ((JavascriptExecutor)
			 * DriverFactory.getDriver()).executeScript("window.scrollTo(0,0);");
			 * 
			 * ((JavascriptExecutor) DriverFactory.getDriver())
			 * .executeScript("var ele=arguments[0].getBoundingClientRect().top;" +
			 * "var win=window.innerHeight;" +
			 * "var percentInPx = win/100 * parseInt(arguments[1]);" +
			 * "window.scrollTo(0,(ele-percentInPx));", elem, percent);
			 * 
			 * ((JavascriptExecutor) DriverFactory.getDriver())
			 * .executeScript("var st = arguments[0].style;" + "st.color = 'red';" +
			 * "st.background = 'yellow';" + "st.borderBottom = '1px solid red';" +
			 * "st.borderTop = '1px solid red';", elem);
			 */

			String script = 
			"window.scrollTo(0,0);"
			+ "var ele=arguments[0].getBoundingClientRect().top;"
			+ "var win=window.innerHeight;"
			+ "var percentInPx = win/100 * parseInt(arguments[1]);"
			+ "window.scrollTo(0,(ele-percentInPx));"
			+ "var st = arguments[0].style;"
			+ "st.color = 'red';"
			+ "st.background = 'yellow';"
			+ "st.borderBottom = '1px solid red';"
			+ "st.borderTop = '1px solid red';";
			
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript(script,elem,percent);

		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * Bring the Element into Screen view port by percent
	 * 
	 * @param element      A WebElement/By object
	 * @param percentValue define the percent value to scroll, if it is set to 20%
	 *                     then element will be scrolled to 20% from top
	 */
	public void javaScript_ScrollByPercent(Object element, long percent) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			// Scroll window to top
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollTo(0,0);");

			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("var ele=arguments[0].getBoundingClientRect().top;" + "var win=window.innerHeight;"
							+ "var percentInPx = win/100 * parseInt(arguments[1]);"
							+ "window.scrollTo(0,(ele-percentInPx));", elem, percent);

		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * Bring the Element into Screen BOTTOM view, and also changes its background to
	 * Yellow
	 * 
	 * @param element A WebElement/By object
	 */
	public void javaScript_ScrollIntoBOTTOMView_AndHighlight_ThenClick(Object element) {
		javaScript_ScrollIntoBOTTOMView_AndHighlight_ThenClick(element, null);
	}

	public void javaScript_ScrollIntoBOTTOMView_AndHighlight_ThenClick(Object element, String desc) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {

			if (desc != null && !desc.equals("")) {
				Reporter.INFO( "Clicking on " + desc);
			}

			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(false);", elem);
			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("var st = arguments[0].style;" + "st.color = 'red';" + "st.background = 'yellow';"
							+ "st.borderBottom = '1px solid red';" + "st.borderTop = '1px solid red';", elem);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click()", elem);
			// SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * Bring the Element into Screen TOP view, and also changes its background to
	 * Yellow
	 * 
	 * @param element A WebElement/By object
	 */
	public void javaScript_ScrollIntoTOPView_AndHighlight(Object element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elem);
			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("var st = arguments[0].style;" + "st.color = 'red';" + "st.background = 'yellow';"
							+ "st.borderBottom = '1px solid red';" + "st.borderTop = '1px solid red';", elem);
		} catch (Exception e) {
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
			// new CustomExceptionHandler(e);
		}
	}

	/**
	 * Bring the Element into Screen TOP view only.
	 * 
	 * @param element A WebElement/By object
	 */
	public void javaScript_ScrollIntoTOPView(Object element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elem);
			// SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}

	/**
	 * Bring the Element into Screen TOP view, and also changes its background to
	 * Yellow
	 * 
	 * @param element A WebElement/By object
	 */
	public void javaScript_ScrollIntoTOPView_AndHighlight_ThenClick(Object element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		try {
			WebElement elem = getWebElement(element);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elem);
			((JavascriptExecutor) DriverFactory.getDriver())
					.executeScript("var st = arguments[0].style;" + "st.color = 'red';" + "st.background = 'yellow';"
							+ "st.borderBottom = '1px solid red';" + "st.borderTop = '1px solid red';", elem);
			((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click()", elem);
			// SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			// new CustomExceptionHandler(e);
			Reporter.WARNING(methodName + " Failed for the element " + getByObjectFromWebElement(element));
		}
	}
	

	/**
	 * Close the current Thread's "BaseTest.getDriver()" Driver window, quitting the
	 * browser if it's the last window currently open.
	 */
	public void close() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			//SnapshotManager.takeSnapShot(methodName);
			DriverFactory.getDriver().close();
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Switches to the currently active modal dialog for this particular driver
	 * instance, accept/reject it and returns the text displayed on the alert.
	 *
	 * @param accept pass true for clicking Ok/Yes on the alert
	 * @return String displayed on the alert dialog.
	 * @throws NoAlertPresentException If the dialog cannot be found
	 */
	public String closeAlertAndGetItsText(boolean accept) {
		String alertText = null;
		try {
			WebDriver driver = DriverFactory.getDriver();
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
			if (accept) {
				alert.accept();
			} else {
				alert.dismiss();
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return alertText;
	}

	/**
	 * Switches to the currently active modal dialog for this particular driver
	 * instance, Put data in it and accept/reject it and returns the text displayed
	 * on the alert.
	 *
	 * @param accept pass true for clicking Ok/Yes on the alert
	 * @return String displayed on the alert dialog.
	 * @throws NoAlertPresentException If the dialog cannot be found
	 */
	public String closeAlertAndGetItsText(String data, boolean accept) {
		String alertText = null;
		try {
			WebDriver driver = DriverFactory.getDriver();
			Alert alert = driver.switchTo().alert();
			alert.sendKeys(data);
			alertText = alert.getText();
			if (accept) {
				alert.accept();
			} else {
				alert.dismiss();
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return alertText;
	}

	/**
	 * Load a new web page in the current browser window. This is done using an HTTP
	 * GET operation, and the method will block until the load is complete. This
	 * will follow redirects issued either by the server or as a meta-redirect from
	 * within the returned HTML. Should a meta-redirect "rest" for any duration of
	 * time, it is best to wait until this timeout is over, since should the
	 * underlying page change whilst your test is executing the results of future
	 * calls against this interface will be against the freshly loaded page. Synonym
	 * for {@link org.openqa.selenium.WebDriver.Navigation#to(String)}.
	 *
	 * @param url The String URL to load. It is best to use a fully qualified URL
	 */
	public void get(String url) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			DriverFactory.getDriver().get(url);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}
	
	/**
	 * Get a string representing the current URL that the browser is looking at.
	 *
	 * @return The String URL of the page currently loaded in the browser
	 */
	public String getCurrentUrl() {
		String currentURL = "";
		// //StackTraceElement[] ste =Thread.currentThread().getStackTrace();
		// String methodName=ste[1].getMethodName();
		try {
			currentURL = DriverFactory.getDriver().getCurrentUrl();
			// //SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return currentURL;
	}
	
	/**
	 * Get the source of the last loaded page. If the page has been modified after
	 * loading (for example, by Javascript) there is no guarantee that the returned
	 * text is that of the modified page. Please consult the documentation of the
	 * particular driver being used to determine whether the returned text reflects
	 * the current state of the page or the text last sent by the web server. The
	 * page source returned is a representation of the underlying DOM: do not expect
	 * it to be formatted or escaped in the same way as the response sent from the
	 * web server. Think of it as an artist's impression.
	 *
	 * @return The source of the current Threads "BaseTest.getDriver()" browser page
	 */
	public String getPageSource() {
		String pageSource = "";
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			pageSource = DriverFactory.getDriver().getPageSource();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return pageSource;
	}
	
	/**
	 * Returns the title of the current page.
	 *
	 * @return The title of the current page, with leading and trailing whitespace
	 *         stripped, or null if one is not already set
	 */
	public String getTitle() {
		String title = null;
		// String methodName = "getTitle method";
		try {
			WebDriver driver = DriverFactory.getDriver();
			title = driver.getTitle();
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return title;
	}

	/**
	 * Return an opaque handle to this window that uniquely identifies it within
	 * this driver instance. This can be used to switch to this window at a later
	 * date
	 *
	 * @return the current window handle
	 */
	public String getWindowHandle() {
		String currentWinHandle = "";
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			currentWinHandle = DriverFactory.getDriver().getWindowHandle();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return currentWinHandle;
	}

	/**
	 * Return a set of window handles which can be used to iterate over all open
	 * windows of this WebDriver instance by passing them to
	 * {@link #switchTo()}.{@link Options#window()}
	 *
	 * @return A set of window handles which can be used to iterate over all open
	 *         windows.
	 */
	public Set<String> getWindowHandles() {
		Set<String> winHandles = null;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			winHandles = DriverFactory.getDriver().getWindowHandles();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return winHandles;
	}

	/**
	 * Check for this particular driver instance if currently active modal dialog
	 * present.
	 *
	 * @return true if alert dialog is present
	 * @throws NoAlertPresentException If the dialog cannot be found
	 */
	public boolean isAlertPresent() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		boolean bool = false;
		try {
			WebDriver driver = DriverFactory.getDriver();
			driver.switchTo().alert();
			//SnapshotManager.takeSnapShot(methodName);
			bool = true;
		} catch (Exception e) {
		}
		return bool;

	}
	

	/**
	 * Move back a single "item" in the browser's history.
	 */
	public void navigateBack() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			//SnapshotManager.takeSnapShot(methodName);
			DriverFactory.getDriver().navigate().back();
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Move a single "item" forward in the browser's history. Does nothing if we are
	 * on the latest page viewed.
	 */
	public void navigateForward() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			//SnapshotManager.takeSnapShot(methodName);
			DriverFactory.getDriver().navigate().forward();
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Load a new web page in the current browser window. This is done using an HTTP
	 * GET operation, and the method will block until the load is complete. This
	 * will follow redirects issued either by the server or as a meta-redirect from
	 * within the returned HTML. Should a meta-redirect "rest" for any duration of
	 * time, it is best to wait until this timeout is over, since should the
	 * underlying page change whilst your test is executing the results of future
	 * calls against this interface will be against the freshly loaded page.
	 *
	 * @param url The URL to load. It is best to use a fully qualified URL
	 */
	public void navigateTo(String url) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			DriverFactory.getDriver().navigate().to(url);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Overloaded version of {@link #to(String)} that makes it easy to pass in a
	 * URL.
	 *
	 * @param url java.net.URL to load.
	 */
	public void navigateTo(URL url) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			DriverFactory.getDriver().navigate().to(url);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}


	/**
	 * Refresh the current page
	 */
	public void refresh() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		try {
			//SnapshotManager.takeSnapShot(methodName);
			DriverFactory.getDriver().navigate().refresh();
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Switches to the element that currently has focus within the document
	 * currently "switched to", or the body element if this cannot be detected. This
	 * matches the semantics of calling "document.activeElement" in Javascript.
	 * 
	 * @return The WebElement with focus, or the body element if no element with
	 *         focus can be detected.
	 */
	public WebElement switchTo_ActiveElement() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebElement obj = null;
		try {
			obj = DriverFactory.getDriver().switchTo().activeElement();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return obj;
	}

	/**
	 * Selects either the first frame on the page, or the main document when a page
	 * contains iframes.
	 *
	 * @return This driver focused on the top window/first frame.
	 */
	public WebDriver switchTo_DefaultContent() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver frame = null;
		try {
			frame = DriverFactory.getDriver().switchTo().defaultContent();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return frame;
	}

	/**
	 * Select a frame by its (zero-based) index. Selecting a frame by index is
	 * equivalent to the JS expression window.frames[index] where "window" is the
	 * DOM window represented by the current context. Once the frame has been
	 * selected, all subsequent calls on the WebDriver interface are made to that
	 * frame.
	 *
	 * @param index (zero-based) index
	 * @return This driver focused on the given frame
	 * @throws NoSuchFrameException If the frame cannot be found
	 */
	public WebDriver switchTo_Frame(int index) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver frame = null;
		try {
			frame = DriverFactory.getDriver().switchTo().frame(index);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return frame;
	}

	/**
	 * Select a frame using its previously located {@link WebElement}.
	 * 
	 * @param element The By/WebElement Object The frame element to switch to.
	 * @return This driver focused on the given frame.
	 * @throws NoSuchFrameException           If the given element is neither an
	 *                                        IFRAME nor a FRAME element.
	 * @throws StaleElementReferenceException If the WebElement has gone stale.
	 * @see WebDriver#findElement(By)
	 */
	public WebDriver switchTo_Frame(Object element) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver frame = null;
		try {
			if (element instanceof By) {
				frame = DriverFactory.getDriver().switchTo().frame(DriverFactory.getDriver().findElement((By) element));
			} else if (element instanceof WebElement) {
				frame = DriverFactory.getDriver().switchTo().frame((WebElement) element);
			}
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return frame;
	}

	/**
	 * Select a frame by its name or ID. Frames located by matching name attributes
	 * are always given precedence over those matched by ID.
	 *
	 * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or
	 *                 &lt;iframe&gt; element, or the (zero-based) index
	 * @return This driver focused on the given frame
	 * @throws NoSuchFrameException If the frame cannot be found
	 */
	public WebDriver switchTo_Frame(String nameOrId) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver frame = null;
		try {
			frame = DriverFactory.getDriver().switchTo().frame(nameOrId);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return frame;
	}

	/**
	 * Change focus to the parent context. If the current context is the top level
	 * browsing context, the context remains unchanged.
	 *
	 * @return This driver focused on the parent frame
	 */
	public WebDriver switchTo_ParentFrame() {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver frame = null;
		try {
			frame = DriverFactory.getDriver().switchTo().parentFrame();
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return frame;
	}

	/**
	 * Switch the focus of future commands for this driver to Other Available
	 * window. Use this method when you have only two tabs, and in case you want to
	 * alternate between them
	 *
	 * @return the window handle of old tab
	 * @throws NoSuchWindowException If the window cannot be found
	 */
	public void switchTo_Tab_TitleContains(String containsTitle) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver driver = DriverFactory.getDriver();
		try {
			for (String currentTab : driver.getWindowHandles()) {
				driver.switchTo().window(currentTab);
				if (driver.getTitle().toLowerCase().contains(containsTitle.toLowerCase())) {
					//SnapshotManager.takeSnapShot(methodName);
					break;
				}
			}

		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Switch the focus of future commands for this driver to Other Available
	 * window. Use this method when you have only two tabs, and in case you want to
	 * alternate between them
	 *
	 * @return the window handle of old tab
	 * @throws NoSuchWindowException If the window cannot be found
	 */
	public void switchTo_Tab_UrlContains(String containsUrl) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver driver = DriverFactory.getDriver();
		try {
			for (String currentTab : driver.getWindowHandles()) {
				driver.switchTo().window(currentTab);
				if (driver.getCurrentUrl().toLowerCase().contains(containsUrl.toLowerCase())) {
					//SnapshotManager.takeSnapShot(methodName);
					break;
				}
			}

		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
	}

	/**
	 * Switch the focus of future commands for this driver to the window with the
	 * given name/handle.
	 *
	 * @param nameOrHandle The name of the window or the handle as returned by
	 *                     {@link WebDriver#getWindowHandle()}
	 * @return This driver focused on the given window
	 * @throws NoSuchWindowException If the window cannot be found
	 */
	public WebDriver switchTo_Window(String nameOrHandle) {
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();
		WebDriver win = null;
		try {
			win = DriverFactory.getDriver().switchTo().window(nameOrHandle);
			//SnapshotManager.takeSnapShot(methodName);
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return win;
	}
	
	/**
	 * Verify the page url with the parameter value, Reporter will not run.
	 *
	 * @param url expected url
	 * @return true if page title is matched with the parameter value
	 */
	public boolean verifyPageUrl(String url) {
		return verifyPageUrl(url, false);
	}

	/**
	 * Verify the page url with the parameter value, Run the reporter.
	 *
	 * @param url       expected url
	 * @param runReport true - reporter will run, false - reporter will not run
	 * @return true if page title is matched with the parameter value
	 */
	public boolean verifyPageUrl(String url, boolean runReport) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();

		try {
			WebDriver driver = DriverFactory.getDriver();
			if (driver.getCurrentUrl().toLowerCase().contains(url.toLowerCase())) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}

			if (runReport) {
				if (bool) {
					Reporter.PASS( "'<b>" + url + "</b>' page is displayed <br/>" + getCurrentUrl());
				} else {
					Reporter.FAIL( "'<b>" + url + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
					Assert.fail("'<b>" + url + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
				}
			} else {
				if (!bool) {
					Reporter.FAIL( "'<b>" + url + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
					Assert.fail("'<b>" + url + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
				}
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return bool;
	}
	

	/**
	 * Verify the page url with the parameter value, Reporter will not run.
	 *
	 * @param title expected title name
	 * @return true if page title is matched with the parameter value
	 */
	public boolean verifyPageTitle(String title) {
		return verifyPageTitle(title, false);
	}

	/**
	 * Verify the page url with the parameter value, Run the reporter.
	 *
	 * @param title     expected title name
	 * @param runReport true - reporter will run, false - reporter will not run
	 * @return true if page title is matched with the parameter value
	 */
	public boolean verifyPageTitle(String title, boolean runReport) {
		boolean bool = false;
		//StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		//String methodName = ste[1].getMethodName();

		try {
			WebDriver driver = DriverFactory.getDriver();
			if (driver.getTitle().toLowerCase().contains(title.toLowerCase())) {
				bool = true;
				//SnapshotManager.takeSnapShot(methodName);
			}

			if (runReport) {
				if (bool) {
					Reporter.PASS( "'<b>" + title + "</b>' page is displayed <br/>" + getCurrentUrl());
				} else {
					Reporter.FAIL(
							"'<b>" + title + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
					Assert.fail("'<b>" + title + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
				}
			} else {
				if (!bool) {
					Reporter.FAIL(
							"'<b>" + title + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
					Assert.fail("'<b>" + title + "</b>' page is NOT displayed <br/>" + getCurrentUrl());
				}
			}
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return bool;
	}
	

	/**
	 * Returns the integer height of the passed By/WebElement Object Provided that
	 * element is present. Sometimes the element is present but hidden so its height
	 * will be 0. This method will be useful to make decisions
	 * 
	 * @return height of the passed WebElement/By Object
	 * @param elem     the By/WebElement Object
	 * @param waitTime the time to wait in case element is not present
	 * @throws does not throws any exception
	 */
	public int getHeight(Object elem, long waitTime) {
		int height = 0;
		try {
			DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
			WebElement element = getWebElement(elem);
			height = element.getRect().getHeight();
		} catch (Exception e) {
		} finally {
			DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConfig.WAIT_IMPLICIT));
		}
		return height;
	}

	/** refer getHeight(elem, waitTime) */
	public int getHeight(Object elem) {
		return getHeight(elem, FrameworkConfig.WAIT_IMPLICIT);
	}
	
}
