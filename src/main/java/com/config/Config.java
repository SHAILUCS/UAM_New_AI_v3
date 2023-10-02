package com.config;

import java.util.Date;

/**
 * <pre>
 * This class holds some values which controls specific features/flow of
 * framework and these values does not get changed unless you explicitly change
 * them here,
 * 
 * Description for each constant field is provided in the class body.
 * 
 * <b>Note</b>: 
 * All Constant fields are created static so that we can directly use
 * them, without creating object of Constant Class.
 * 
 * <b>Usage</b>: 
 * String filePath=Constant.getTestDataFilePath();
 * System.out.println(filePath); //This will print the test data file (TestData.xlsx) path
 * </pre>
 */
public class Config extends FrameworkConfig {

	/**
	 * This constant holds the EMAIL address, which will be filled during execution
	 */
	public static final String EMAIL = "dummymail@gmail.com";

	/**
	 * To Register a new User in Commercial Website We need to have a unique email
	 * 
	 * @author DRIVER BINARY 16-Apr-2020
	 */
	public static String getUnique_Email() {
		String emailParts[] = EMAIL.split("@");
		emailParts[0] = emailParts[0] + "+" + new Date().getTime();
		return emailParts[0] + "@" + emailParts[1];

	}

	/*
	 * public static final int width = 5000; public static final int height = 3000;
	 */
	/**
	 * the directory in which our project is placed
	 */
	public static final String ROOT = System.getProperty("user.dir")+"/";

	/** Test Data information */
	private static final String RESOURCE_FOLDER_NAME_TEST = "/src/test/resources/";
	
	/** These variables are used for deciding the run time behavior */
	private static String ENVIRONMENT = "NOT_SET";
	
	private static String LOCALE;

	public static String getEnvironment() {
		return ENVIRONMENT;
	}

	/** set the Name of the environment Info Sheet */
	public static void setEnvironmentInfoSheet(String env) {
		if (env != null && !env.equals("") && !env.equals("${environment}")) {
			ENVIRONMENT = env.toUpperCase();
		} else {
			String message = "Warning: TestNG parameter 'environment' value= {" + env
					+ "} is incorrectly provided, running the tests on '" + env + "' enviroment";
			System.err.println(message);
		}
	}

	public static String getTestDataFolderPath() {
		String path = ROOT + "/" + RESOURCE_FOLDER_NAME_TEST + "/" ;
		return path;
	}
	
	public static void setLocale(String l) {
		LOCALE = l;
	}
	
	public static String getLocale() {
		return LOCALE;
	}

	/**
	 * Custom method created for this application under test.
	 * */
	public static String getLocaleXlFilePath() {
		return Config.ROOT+"/src/test/java/or/localization/locators.xlsx";
	}
	

	/**
	 * Custom method created for this application under test.
	 * */
	public static String getLocalePropertiesPath() {
		return Config.ROOT+"/src/test/java/or/localization";
	}

	public static String getCredentialsFilePath() {
		String path = Config.ROOT + "/" + RESOURCE_FOLDER_NAME_TEST + "/" + "Credentials.xlsx" ;
		return path;
	}

}
