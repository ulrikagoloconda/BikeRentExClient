package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by NIK1114 on 2017-01-16.
 */
public class ChoiceDialogView {

  public static boolean showChoiceDialogYESorNO(String textContent){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, textContent, ButtonType.YES, ButtonType.NO);
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES){
      return true;
    }
    return false;
  }



}
