package locationinRAID;

import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import org.apache.commons.lang3.StringUtils;

/*
 * This class for finding true location of image within file system 
 * and then providing a "ls" statement to verify that it exists. 
 * Input is the output of a query from IMAGES table
 * 
 * Things still left to to do:
 * . make it so that the output writes out the entire file name, not just the date/time identifiers
 * . Move some functions in to their own classes. 
 * . create GUI
 * 
 */

public class LocationInRaid {

private static String QueryOutput;
private static String[] fields;
private String midLabel;
private String midInst;
private String IIDdt;
private String IIDtm;
private String MM;
private String YYYY;
private String DD;
private String IIDgrp;
private String midSec;
private boolean ValidMidsector = false;
	
	public static void main(String[] args) {
		
		LocationInRaid LocationinRaid = new LocationInRaid();
		
		if (!LocationinRaid.findDicom()){
			System.out.println("Sorry, have not yet written for non DICOM");
		}
		else{
		
		QueryOutput = LocationinRaid.fillInitString();
		String[] fields = LocationinRaid.startparse(QueryOutput);
		
		LocationinRaid.FileSystemLoader(fields);
		System.out.println(LocationinRaid.DicomPathBuild());}
	}	
		
	public boolean findDicom() {
		
		boolean dicInd = false;		
		boolean AnsQ = false;
		String DicomQreturn;
		
		Scanner user_input = new Scanner (System.in);	
		
		do {			
			System.out.print("Is this Dicom [Y,N]");
			DicomQreturn = user_input.next();
			DicomQreturn = DicomQreturn.trim();	
			
				if (DicomQreturn.equals("Y")){
					AnsQ = true;
					dicInd = true;}
				else if(DicomQreturn.equals("N")){
					AnsQ = true;
					dicInd = false;}
				else { 
					System.out.println("Please answer with either 'Y' or 'N'");
					AnsQ = false;}
		} while (AnsQ == false);
		
		if (dicInd == false){
			return false;}
		else 
			return true;
		
		
	}

	public String fillInitString () {
		
		System.out.print("Please paste in return of query:");	
		
		String input = " ";
		
		//Scanner user_input2 = new Scanner (new BufferedInputStream(System.in));	
		//return user_input2.nextLine();
		
		//while (user_input2.hasNext()){
			//String p = user_input2.nextLine();
			//if (p.trim().isEmpty()) break;
			//else 
				//input = input + p;}

		Scanner user_input2 = new Scanner(System.in);
		
		while (user_input2.hasNext()){
			input = input + " " + user_input2.nextLine();
		}
		return input;
		
	}
	

	public String[] startparse(String row) {
		
		String delims = "[ ]+";
		if (QueryOutput != null){
			String[] a = QueryOutput.split(delims);	
			return a;}
		else { 
			System.out.println("I don't think you typed anything");
			String[] b = null;
			return b;}
		

		
	}
	
	public void FileSystemLoader(String[] QueryFields) {
		
		
		int t = 0;
		boolean cmP = false;
		int IIDgrpCount = 0;
		while (cmP == false){
				int count = StringUtils.countMatches(QueryFields[t], ".");
				//finding IIDgroup first for reference
				
				if (count > 5 && t > 2 && t < (QueryFields.length -2)){
					IIDgrp = QueryFields[t];
					IIDgrpCount = t;
					System.out.println("IIDGroup is:" + IIDgrp);}
				
				if (t == QueryFields.length - 2){
					if (QueryFields[t].contains("invalid")){
						ValidMidsector = false;}
						else{ 
						ValidMidsector = true;
						midSec = QueryFields[t];
						System.out.println("Midsector is:" + midSec);}
				}
				
				if (t == QueryFields.length - 3){
					midLabel = QueryFields[t];
					System.out.println("Midlabel is:" + midLabel);}
				
				if (t == QueryFields.length -4){
					midInst = QueryFields[t];
					System.out.println("Midinstitution is:" + midInst);}
				
				if (IIDgrp != null && t == IIDgrpCount - 4){
					IIDdt = QueryFields[t];
					IIDdt = IIDdt.replaceAll("-", ".");
					System.out.println("IIDdate is:" + IIDdt);
					String gdelims = "[.]+";
					String[] c = IIDdt.split(gdelims);
					YYYY = c[0];
					MM = c[1];
					DD = c[2];}
				
				if (IIDdt != null && t == IIDgrpCount - 3){
					IIDtm = QueryFields[t];
					IIDtm = IIDtm.replaceAll(":", ".");
					System.out.println("IIDtime is:" + IIDtm);
					cmP = true;}
				
				if (t < QueryFields.length - 1){
					t++;}
				else{
					t = 1;}
		}
	}

	public String DicomPathBuild(){
		String m = null;
		String j = null;
		
		if (ValidMidsector = true){
			m = "ls /MOUNT/" + midLabel + "/" + midInst + "/" + YYYY + MM + "/" + DD + "/" + midSec + "/" + IIDgrp + "/" + IIDtm + "--" + IIDdt + "*";
			return m;}
		else 
			j = "ls /MOUNT/" + midLabel + "/" + midInst + "/" + YYYY + MM + "/" + DD + "/" + IIDgrp + "/" + IIDtm + "--" + IIDdt + "*";
			return j;
	}	
}	

