package locationinRAID;

import org.apache.commons.lang3.StringUtils;

public class LocationinRaidfinder {

	/**
	 * @param args
	 */
	
	
String initialPath;	
String[] fields;

//fields for fileSystemLoader and dicomPathBuild
String IIDGroup;
boolean ValidMidsector;
String MidSector;
String MidLabel;
String MidInstitution;
String IIDdt;
String IID_tm;
String YYYY;
String MM;
String DD;

	
public String pathFinder(String path, boolean gDcm) {
	
	String finalPath;
	
	if (gDcm = false){
		finalPath = "Sorry, have not yet written for non DICOM";
	}
	else{
	
		initialPath = path;	
		fields = startparse(initialPath);
		fileSystemLoader(fields);
		finalPath = dicomPathBuild();
	}
		
return finalPath;	

}

public String[] startparse(String row){
	
	String delims = "[ ]+";
	String [] a = row.split(delims);
	return a;
	
}

public void fileSystemLoader(String[] qLoad) {
	
	int t = 0;
	boolean cmp = false;
	int IIDGroupCount = 0;
	while (cmp == false){
		int count = StringUtils.countMatches(qLoad[t], ".");
		//finding IIDgroup first for reference
		
		if (count > 5 && t > 2 && t < (qLoad.length -2)){
			IIDGroup = qLoad[t];
			IIDGroupCount = t;
			System.out.println("IIDGroup is:" + IIDGroup);}
		
		if (t == qLoad.length - 2){
			if (qLoad[t].contains("invalid")){
				ValidMidsector = false;}
				else{ 
				ValidMidsector = true;
				MidSector = qLoad[t];}}
		
		if (t == qLoad.length - 3){
			MidLabel = qLoad[t];}
		
		if (t == qLoad.length -4){
			MidInstitution = qLoad[t];}
		
		if (IIDGroup != null && t == IIDGroupCount - 4){
			IIDdt = qLoad[t];
			IIDdt = IIDdt.replaceAll("-", ".");
			String gdelims = "[.]+";
			String[] c = IIDdt.split(gdelims);
			YYYY = c[0];
			MM = c[1];
			DD = c[2];}
		
		if (IIDdt != null && t == IIDGroupCount - 3){
			IID_tm = qLoad[t];
			IID_tm = IID_tm.replaceAll(":", ".");
			cmp = true;}
		
		if (t < qLoad.length - 1){
			t++;}
		else{
			t = 1;}
	}
	
	
	
}

public String dicomPathBuild() {
	
	String m = null;
	String j = null;
	
	if (ValidMidsector = true){
		m = "ls /MOUNT/" + MidLabel + "/" + MidInstitution + "/" + YYYY + MM + "/" + DD + "/" + MidSector + "/" + IIDGroup + "/" + IID_tm + "--" + IIDdt + "*";
		return m;}
	else 
		j = "ls /MOUNT/" + MidLabel + "/" + MidInstitution + "/" + YYYY + MM + "/" + DD + "/" + IIDGroup + "/" + IID_tm + "--" + IIDdt + "*";
		return j;
	
}



}
