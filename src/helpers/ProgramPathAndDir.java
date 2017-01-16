package helpers;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgramPathAndDir {

	public static String getPathfromfile(){
	String filePath;
		try {
			filePath = readFile("path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return filePath + "/";

	
}

	public static String readFile(String filename) throws IOException {
	 BufferedReader br = new BufferedReader(
	 new InputStreamReader(new FileInputStream(filename), "UTF-8"));
	 String nextLine = "";
	   StringBuffer sb = new StringBuffer();
	   while ((nextLine = br.readLine()) != null) {
	     sb.append(nextLine);
	   }
	   return sb.toString();
	}
	

	 public static void makeDir(String folder) {


	File theDir = new File(folder);

	// if the directory does not exist, create it
	if (!theDir.exists()) {
	    System.out.println("creating directory: " + theDir);
	    boolean result = false;

	    try{
	        theDir.mkdir();
	        result = true;
	    } 
	    catch(SecurityException se){
	        //handle it
	    }        
	    if(result) {    
	        System.out.println("DIR created");  
	    }
	}


	}

	 public static void dumptempfiles(String tempFile) {
		 try{
		      
		        //Delete if tempFile exists
		        File fileTemp = new File(tempFile);
		          if (fileTemp.exists()){
		             fileTemp.delete();
		          }   
		      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }

		
	}

	 public static String makeFoldertext(String textString){
	String old = textString;
	try {	
		textString = textString.replaceAll("&", " o ");		
		textString = textString.replaceAll("¤" , " o ");
		textString = textString.replaceAll("Ñ" , " n ");
		textString = textString.replaceAll("Ü" , " u ");
		textString = textString.replaceAll("ß" , " SS ");
		textString = textString.replaceAll("€" , " Euro");
		textString = textString.replaceAll("Æ" , "AE");
		textString = textString.replaceAll("Æ" , "AE");
		textString = textString.replaceAll("À" , "A");
		textString = textString.replaceAll("È" , "E");
		textString = textString.replaceAll("É" , "E");
		textString = textString.replaceAll("§" , "paragraf");
		textString = textString.replaceAll("°" , "GRADER");
		textString = textString.replaceAll("£" , "Pound");
		textString = textString.replaceAll("´" , " ");
		textString = textString.replaceAll(">" , "-");
		textString = textString.replaceAll("<" , "-");
		textString = textString.replaceAll("\"" , "-");
		textString = textString.replaceAll("&" , "o");
		textString = textString.replaceAll("~" , "-");
		textString = textString.replaceAll("á" , "a");
		textString = textString.replaceAll("à" , "a");
		textString = textString.replaceAll("Á" , "A");
		textString = textString.replaceAll("À" , "A");
		textString = textString.replaceAll("é" , "e");
		textString = textString.replaceAll("è" , "e");
		textString = textString.replaceAll("È" , "E");
		textString = textString.replaceAll("É" , "E");
		textString = textString.replaceAll("/" , "-");
		textString = textString.replaceAll(":" , "-");
		textString = textString.replaceAll(";" , "-");
		textString = textString.replaceAll("," , "-");
		textString = textString.replaceAll("@" , "-");
		textString = textString.replaceAll("#" , "-");
		textString = textString.replaceAll("/+" , " och ");
		return textString;
	} catch (Exception e) {
		e.printStackTrace();
		 old = old.replaceAll("[^\\p{Alpha}\\p{Digit}]+","");
		return old;
	}	

}

	 public static void savefile(String content, String filename) throws IOException
		{
			  File file = new File (filename);
			  BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
			  out.write(content);
			  out.close();
			};


}
