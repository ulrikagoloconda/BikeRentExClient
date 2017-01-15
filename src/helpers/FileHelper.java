package helpers;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class FileHelper {
  public static void saveFile(String content, String filename) throws IOException
  {
    File file = new File (filename);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write(content);
    out.close();
  };

  public static void openPDF(String fileName){
    try {

      File pdfFile = new File(fileName);
      if (pdfFile.exists()) {

        if (Desktop.isDesktopSupported()) {
          Desktop.getDesktop().open(pdfFile);
        } else {
          System.out.println("Awt Desktop is not supported!");
        }

      } else {
        System.out.println("File is not exists!");
      }

      System.out.println("Done");

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

}
