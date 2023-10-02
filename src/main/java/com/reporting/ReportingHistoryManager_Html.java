package com.reporting;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.config.FrameworkConfig;
import com.util.Util;

/**
 * @author shailendra.rajawat
 * created on 5 Jan 2018
 * */
public class ReportingHistoryManager_Html {

	private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMMdd_HHmmss");
	private static String reportingHistoryFolderPath=FrameworkConfig.getReportingHistoryFolderPath();
	private static int limit=FrameworkConfig.reportingHistoryFolderLimit();
	
	
	
	private static void removeOlderFolders() {
		try{
			List<Date> allFoldersNameList=getFoldersNameList();
			int foldersCount=allFoldersNameList.size();
			if(foldersCount>0){


				if(foldersCount>=limit){
					Collections.sort(allFoldersNameList);
					//System.out.println(allFoldersNameList);
					int counter=foldersCount-limit+1;
					for (int i = 0; i < counter; i++) {
						String deleteThisFolderName=dateFormat.format(allFoldersNameList.get(i));
						String deleteThisFolderPath=reportingHistoryFolderPath + "/" + deleteThisFolderName;
						File file= new File(deleteThisFolderPath);
						FileUtils.deleteDirectory(file);
						if(!file.exists()){
							System.out.println("DELETED: "+deleteThisFolderPath);
						}else{
							System.out.println("NOT DELETED: "+deleteThisFolderPath);
						}
					}

					//System.out.println(getFoldersNameList());
				}else{
					System.out.println("NOTHING IS DELETED FROM "+FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME+" current folder count: "+foldersCount+" and set limit: "+limit);
				}

			}else{
				System.out.println("NO FILES PRESENT IN "+FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	static List<Date> getFoldersNameList(){

		List<Date> foldersNameList=new ArrayList<>();
		try{
			File dir= new File(reportingHistoryFolderPath);
			if (dir.isDirectory()) { 
				File[] children = dir.listFiles();
				for (int i = 0; i < children.length; i++) {
					if(children[i].isDirectory()){
						String folderName=children[i].getName();
						if(!folderName.equals(FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME_HTML)){
							foldersNameList.add(dateFormat.parse(folderName));
						}
					}
				} 
			} 		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return foldersNameList;
	}

	private static String createNewFolder() {
		String newFolderName=dateFormat.format(new Date());
		String newFolderPath=reportingHistoryFolderPath+ "/" +newFolderName;
		File fileObj= new File(newFolderPath);
		if(!fileObj.exists()) {
			fileObj.mkdir();
			fileObj= new File(newFolderPath+ "/" +FrameworkConfig.SNAPSHOTS_FOLDER_NAME);
			fileObj.mkdir();
			fileObj= new File(newFolderPath+ "/" +FrameworkConfig.HTML_REPORT_FOLDER_NAME);
			fileObj.mkdir();
		}
		return newFolderPath;
	}

	public static void maintainReportingHistory() {
		try {
		if(limit>0){
			System.out.println("===========================MANAGING REPORTING HISTORY===================");
			System.out.println("========================================================================");
			removeOlderFolders();
			String newFolderPath=createNewFolder();
			copyReportContents(newFolderPath);
			ReportingHistoryHTML.manageHTML();

		}else{
			System.out.println("\n========================================================================");
			System.out.println("TO START SET POSITIVE NUMBER IN Constant.reportingHistoryFolderLimit");
		}
		}catch (Exception e) {
			System.out.println("\n========================================================================");
			e.printStackTrace();
		}
		System.out.println("========================================================================");
	}




	private static void copyReportContents(String newFolderPath) {
		try {
			File srcDir = new File(FrameworkConfig.getResultFolderPath());
			if(srcDir.exists()){
				File destDir = new File(newFolderPath+ "/" +FrameworkConfig.HTML_REPORT_FOLDER_NAME);
				FileUtils.copyDirectory(srcDir, destDir);
			}
			srcDir = new File(FrameworkConfig.getSnapShotsFolderPath());
			if(srcDir.exists()){
				File destDir = new File(newFolderPath+ "/" +FrameworkConfig.SNAPSHOTS_FOLDER_NAME);
				FileUtils.copyDirectory(srcDir, destDir);
			}
			
			/** 26 Sept 2023 - trying to copy the karate-api execution reports in history folder */
			srcDir = new File(FrameworkConfig.getKarateReportFolderPath());
			if(srcDir.exists()){
				File destDir = new File(newFolderPath+ "/karate-reports");
				FileUtils.copyDirectory(srcDir, destDir);
				File renameSrcDir = new File(FrameworkConfig.getKarateReportFolderPath()+"_"+Util.getTimeStamp_InMilliSec());
				Reporter.deleteFolderContentRecursively(srcDir, null);
				if(srcDir.renameTo(renameSrcDir)) {
					Reporter.PASS("'karate-reports' folder rename operation succeed");
				}else {
					Reporter.WARNING("'karate-reports' folder rename operation failed");
				}
			}
			
			System.out.println("Copied the current run report in {"+newFolderPath+"} folder");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ParseException {
		maintainReportingHistory();
		/*ReportingHistoryHTML.manageHTML();*/

	}

	/**
	 * This method will print the report path in the console so that user can directly open the report
	 * @author Shailendra Jul 24 2023
	 * */
	public static void printReportPathInConsole() {
		System.out.println("==================================================================================");
		
		System.out.println("HTML report: (paste into browser to view)");
		System.out.println(); 
		System.out.println("file:///" + reportingHistoryFolderPath + "/"
				+ FrameworkConfig.REPORTING_HISTORY_FOLDER_NAME_HTML + "/" + "page1.html");
		System.out.println("==================================================================================");
	}
}
