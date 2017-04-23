package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Goloconda on 2017-04-23.
 */
public class PopupInfoController implements Initializable{
    @FXML
    private TextArea infoTextArea;
    @FXML
    private Label headLine;
    @FXML
    private Button okButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getSpider().setPopupInfoController(this);
        infoTextArea.setEditable(false);
    }


    public void userOk(ActionEvent actionEvent) {
        Main.getSpider().getMain().closePopup();
    }

    public void setMessage(String headLineText, String message, String buttonText){
        headLine.setText(headLineText);
        infoTextArea.setText(message);
        okButton.setText(buttonText);
    }
}
