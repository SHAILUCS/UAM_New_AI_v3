package com.selenium.webdriver;

import java.util.Collection;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.WebDriverListener;

import com.reporting.snapshot.SnapshotManager;

public class Custom_WebDriverEventListener implements WebDriverListener {
	@Override
	public void beforeAccept(Alert driver) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-BSnapshotManager.takeSnapShot(methodName);

	}

	@Override
	public void afterAccept(Alert driver) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		/*// -SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void afterDismiss(Alert driver) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		/*// -SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void beforeDismiss(Alert driver) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-BSnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void beforeTo(WebDriver.Navigation navigation, String url) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-BSnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void afterTo(WebDriver.Navigation navigation, String url) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		/*// -SNAP*/ SnapshotManager.takeSnapShot(methodName);

	}

	@Override
	public void beforeBack(WebDriver.Navigation navigation) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-BSnapshotManager.takeSnapShot(methodName);

	}

	@Override
	public void afterBack(WebDriver.Navigation navigation) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		/*// -SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void beforeForward(WebDriver.Navigation navigation) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-BSnapshotManager.takeSnapShot(methodName);

	}

	@Override
	public void afterForward(WebDriver.Navigation navigation) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		/*// -SNAP*/ SnapshotManager.takeSnapShot(methodName);

	}

	@Override
	public void beforeRefresh(WebDriver.Navigation navigation) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-BSnapshotManager.takeSnapShot(methodName);

	}

	@Override
	public void afterRefresh(WebDriver.Navigation navigation) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		/*// -SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void beforeFindElement(WebDriver driver, By by) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-B SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void afterFindElement(WebDriver driver, By by, WebElement result) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		 //-SYS  System.out.println(methodName);
		// -SNAP-B SnapshotManager.takeSnapShot(methodName);
	}
	
	@Override
	public void beforeClick(WebElement element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		//-SNAP-BSnapshotManager.takeSnapShot(methodName);
	}
	

	@Override
	public void afterClick(WebElement element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}
	
	@Override
	public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		//-SNAP-BSnapshotManager.takeSnapShot(methodName);
	}
	
	
	@Override
	public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP-B*/SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP-B*/SnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void beforeGetWindowHandles(WebDriver driver) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		//-SNAP-BSnapshotManager.takeSnapShot(methodName);
	}
	
	@Override
	public void afterGetWindowHandles(WebDriver driver, Set<String> result) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}
	
	@Override
	public void beforeGetText(WebElement element) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		//-SNAP-BSnapshotManager.takeSnapShot(methodName);
	}

	@Override
	public void afterGetText(WebElement element, String result) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP*/ SnapshotManager.takeSnapShot(methodName);
	}
	
	@Override
	public void beforePerform(WebDriver driver, Collection<Sequence> actions) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP*/ SnapshotManager.takeSnapShot(methodName);
		WebDriverListener.super.beforePerform(driver, actions);
	}
	
	@Override
	public void afterPerform(WebDriver driver, Collection<Sequence> actions) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		String methodName = ste[1].getMethodName();
		//-SYS System.out.println(methodName);
		/*//-SNAP*/ SnapshotManager.takeSnapShot(methodName);
		WebDriverListener.super.afterPerform(driver, actions);
	}
	

}
