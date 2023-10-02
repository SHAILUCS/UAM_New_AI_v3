package com.reporting.snapshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.config.FrameworkConfig;
import com.selenium.Custom_ExceptionHandler;

public class SnapshotsMovieMaker {
	private static String snapshotMovieTemplateFilePath=FrameworkConfig.snapshotsMovieTemplateFilePath();
	private static final String snapsArrayPlaceholder="<!-- snapsArray -->";

	public static void main(String[] args) {
	 createMovie("TC05_Admin_External_verify_SendDraftMail");
	}

	public static String createMovie(String testFolderName) {
		
		// Checking if the test name folder is not created/null then not running this method code
		if (testFolderName == null) {
			return "";
		}
		
		// Checking if the test name folder is empty then not running this method code
		String snapshotFolderPath=FrameworkConfig.getSnapShotsFolderPath()+"/"+testFolderName;
		List<String> snapsList=getSnapsNameList(snapshotFolderPath);
		if(snapsList.size()==0) {
			return "";
		}
		
		String createdMoviePath=null;

		System.out.println("===============================================================================");
		System.out.println(
				convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "	
				+ "Movie Maker STARTED for TEST: " + testFolderName + " | " + new Date());
		if (!FrameworkConfig.enableCaptureSnapshots()) {
			System.out.println(
					convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "	
					+"Movie Maker feature is STOPPED by Constant.captureSnapshots | "+ new Date());
			System.out.println("===============================================================================");
		}else{
			try {
				snapshotFolderPath=FrameworkConfig.getSnapShotsFolderPath()+"/"+testFolderName;
				snapsList=getSnapsNameList(snapshotFolderPath);
				
				String snapsArr="";
				for (String string : snapsList) {
					snapsArr=snapsArr+",\""+string+"\"";
				}
				snapsArr=snapsArr.substring(1, snapsArr.length());

				String reportIn = new String(Files.readAllBytes(Paths.get(snapshotMovieTemplateFilePath)));
				reportIn = reportIn.replaceFirst(snapsArrayPlaceholder,"["+snapsArr+"]");
				String moviePath = snapshotFolderPath + "/" + FrameworkConfig.MOVIE_TEMPLATE_NAME;
				//Util.deleteSpecificFile(moviePath);
				Files.write(Paths.get(moviePath),reportIn.getBytes(),StandardOpenOption.CREATE);
				
				createdMoviePath="../"+FrameworkConfig.SNAPSHOTS_FOLDER_NAME+"/"+testFolderName+ "/" + FrameworkConfig.MOVIE_TEMPLATE_NAME;
				createdMoviePath = createdMoviePath.replace("\\", "/");
			} catch (Exception e) {
				System.out.println("Error when writing report file:\n" + e.toString());
			}finally {
				System.out.println(
				convertToHHMMSS(new Date().getTime()) + " | " + Thread.currentThread().getId() + " | INFO | "		
				+"Movie Maker COMPLETED for TEST: "+testFolderName+" | "+ new Date());
				System.out.println("===============================================================================");
			}
		}
		
		
		return createdMoviePath;
	}


	public static List<String> getSnapsNameList(String snapshotFolderPath){

		List<String> snapshotsNameList=new ArrayList<>();
		try{
			File dir= new File(snapshotFolderPath);
			if (dir.isDirectory()) { 
				File[] children = dir.listFiles();
				for (int i = 0; i < children.length; i++) {

					String fileName=children[i].getName();
					if(fileName.contains(".jp")){
						snapshotsNameList.add(fileName);
					}
				} 
			} 		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.sort(snapshotsNameList);
		return snapshotsNameList;
	}
	
	/** Converts the passed millisecond value to hh:mm:ss.sss format */
	private static String convertToHHMMSS(long millis) {
		String hhmmss = "";
		try {
			DateFormat formatter = new SimpleDateFormat("hh:mm:ss.sss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(millis);
			hhmmss = formatter.format(calendar.getTime());
		} catch (Exception e) {
			new Custom_ExceptionHandler(e);
		}
		return hhmmss;
	}

	
}
