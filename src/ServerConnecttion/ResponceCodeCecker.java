package ServerConnecttion;

import view.ErrorView;

/**
 * Created by NIK1114 on 2016-12-13.
 */
public class ResponceCodeCecker {
  public static void checkCode(String code) {
    if (code.charAt(0) == '5') {
      System.out.println("Är det detta error som körs? i responseCodeChecker");
      Exception e = new Exception(code + "Fel hos server." + "");
      e.printStackTrace();
      ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0,e);
    } else if (code.charAt(0) == '4') {
      ErrorView.showError("Klientens kontakt med servern felar", "Ingen endpoint funnen", "Försök igen senare", 0, new Exception(code + "Ingen endpoint funnen" + ""));
    }
  }
}
