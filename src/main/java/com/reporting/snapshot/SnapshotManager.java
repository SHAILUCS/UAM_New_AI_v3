package com.reporting.snapshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.appium.driver.AppiumDriverFactory;
import com.config.FrameworkConfig;
import com.selenium.Custom_ExceptionHandler;
import com.selenium.webdriver.DriverFactory;
import com.util.Util;

public class SnapshotManager {
	static Map<Long, String> runninTestsNameMap_AsPer_Thread = new HashMap<Long, String>();
	static Map<String, String> runningMethodNamesMap_AsPer_SnapshotFolderName = new HashMap<String, String>();

	public static void initialize() {
		File file = new File(FrameworkConfig.getSnapShotsFolderPath());
		deleteDirectory(file);
	}

	public static void setRunningMethodName(String methodName) {
		String tempName = getName();
		runningMethodNamesMap_AsPer_SnapshotFolderName.put(tempName, methodName);
	}

	public static String getRunningMethodName() {
		String tempName = getName();
		return runningMethodNamesMap_AsPer_SnapshotFolderName.get(tempName);
	}

	public static void setUp(String name) {
		String tempName = runninTestsNameMap_AsPer_Thread.get((Thread.currentThread().getId()));
		if (tempName == null) {
			runninTestsNameMap_AsPer_Thread.put((Thread.currentThread().getId()), name);
		}
	}

	public static synchronized String getName() {
		return runninTestsNameMap_AsPer_Thread.get((Thread.currentThread().getId()));
	}

	public static synchronized String getSnapshotDestinationDirectoryName() {
		String tempName = getName();
		String runningMethodName = getRunningMethodName();
		if (runningMethodName == null) {
			return tempName;
		}
		return tempName + "/" + runningMethodName;
	}

	public static synchronized String getSnapshotDestinationDirectoryPath() {
		return FrameworkConfig.getSnapShotsFolderPath() + "/" + getSnapshotDestinationDirectoryName();
	}

	public static boolean deleteDirectory(File dir) {
		boolean bool = false;
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		// either file or an empty directory
		if (dir.getName().equals(FrameworkConfig.SNAPSHOTS_FOLDER_NAME)) {
			// System.out.println("Skipping file or directory : " + dir.getName());
			bool = true;
		} else {
			// System.out.println("Removing file or directory : " + dir.getName());
			bool = dir.delete();
		}
		return bool;
	}

	public static String takeSnapShot(String extraIdentifierCanAlsoBeBlank) {
		String url = " ";

		try {
			if (extraIdentifierCanAlsoBeBlank.contains("failure") || FrameworkConfig.enableCaptureSnapshots()) {
				String timeStamp = Util.getTimeStamp_InMilliSec();
				String snapShotFolder = FrameworkConfig.getSnapShotsFolderPath();

				String subFolderName = getSnapshotDestinationDirectoryName();
				if (subFolderName != null) {
					String fileName = timeStamp + "_" + extraIdentifierCanAlsoBeBlank + ".jpg";
					String path = snapShotFolder + "/" + subFolderName + "/" + fileName;

					/*
					 * In case any exception occurs when the driver window is not opened then we
					 * will avoid taking screenshot
					 */
					TakesScreenshot scrShot = null;
					if (DriverFactory.getDriver() != null) {
						scrShot = (TakesScreenshot) DriverFactory.getDriver();
					} /*
						 * else if(AppiumDriverFactory.getAppiumDriver() != null) { scrShot =
						 * (TakesScreenshot) AppiumDriverFactory.getAppiumDriver(); }
						 */
					
					if (scrShot != null) {
						// Taking SS using output type as FILE and saving it in a folder, It is NOT
						// working when taking SS of a mobile - browser
						File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
						File destFile = new File(path);
						FileUtils.copyFile(srcFile, destFile);

						if (extraIdentifierCanAlsoBeBlank.contains("failure")) {
							url = snapShotFolder + "/" + subFolderName + "/failure/" + fileName;
							destFile = new File(url);
							FileUtils.copyFile(srcFile, destFile);
							url = "../" + FrameworkConfig.SNAPSHOTS_FOLDER_NAME + "/" + subFolderName + "/failure/"
									+ fileName;
							url = url.replace("\\", "/");
						}

					}

				}
			}

		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
			//e.printStackTrace();
		}
		return url;
	}

	public static void tearDown() {
		runninTestsNameMap_AsPer_Thread.remove((Thread.currentThread().getId()));
	}

	/**
	 * @author shailendra.rajawat Use this method to take the snapshot of passed web
	 *         element object
	 */
	public static String takeSnapShot(String extraIdentifierCanAlsoBeBlank, Object element) {
		String url = "";
		try {
			WebElement webElement = (WebElement) element;

			String timeStamp = Util.getTimeStamp_InMilliSec();
			String snapShotFolder = FrameworkConfig.getSnapShotsFolderPath();
			String subFolderName = getName();
			String fileName = timeStamp + "_" + extraIdentifierCanAlsoBeBlank + ".jpg";
			String path = snapShotFolder + "/" + subFolderName + "/" + fileName;
			TakesScreenshot scrShot = (TakesScreenshot) DriverFactory.getDriver();
			File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
			int w = webElement.getSize().getWidth();
			int h = webElement.getSize().getHeight();
			BufferedImage img = ImageIO.read(srcFile);
			/*
			 * int totH=img.getHeight(); int totW=img.getWidth();
			 */
			int x = webElement.getLocation().getX();
			int y = webElement.getLocation().getY();
			BufferedImage dest = img.getSubimage(x, y, w, h);
			ImageIO.write(dest, "jpg", srcFile);
			File destFile = new File(path);
			FileUtils.copyFile(srcFile, destFile);
			url = "../" + FrameworkConfig.SNAPSHOTS_FOLDER_NAME + "/" + subFolderName + "/" + fileName;
			url = url.replace("\\", "/");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return url;
	}

}
