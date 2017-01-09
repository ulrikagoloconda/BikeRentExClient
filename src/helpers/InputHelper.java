package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputHelper {

  public static String getStringInput(String prompt) {
    BufferedReader stdin = new BufferedReader(
        new InputStreamReader(System.in));

    System.out.print(prompt);
    System.out.flush();

    try {
      return stdin.readLine();
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  public static double getDoubleInput(String prompt) throws NumberFormatException {
    String input = getStringInput(prompt);
    return Double.parseDouble(input);

  }

  public static int getIntegerInput(String prompt) throws NumberFormatException {
    String input = getStringInput(prompt);
    return Integer.parseInt(input);
  }

}