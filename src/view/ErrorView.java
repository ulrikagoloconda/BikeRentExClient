package view;

//import Model.AccessErrorLog;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class ErrorView {

  public static void showError(String titleText, String headerText, String contentText, Integer userId, Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titleText);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    Exception ex = e;

// Create expandable Exception.
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("The exception stacktrace was:");
    System.out.println("**************  userID to errorlog: " + userId + " ***********");
    //SELECT insert_new_ErrorEvent('cykeltur', 'errortest...1234ÅÖÄ');
    if (userId == null) {
      userId = 0;
    }
    if (exceptionText.length() > 9999) {
      exceptionText = exceptionText.substring(1, 9000);
    }
    //System.out.println("*  added error to log: " + AccessErrorLog.insertNewError(userId, exceptionText) + "           *");
    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
    alert.getDialogPane().setExpandableContent(expContent);

    alert.showAndWait();
  }
}
