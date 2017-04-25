package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Goloconda on 2017-04-23.
 */
public class PopupInfoController implements Initializable{
    @FXML
    private TextArea infoTextArea;
    @FXML
    private Label headLine, messageLable;
    @FXML
    private Button okButton;
    @FXML



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getSpider().setPopupInfoController(this);
    }


    public void userOk(ActionEvent actionEvent) {
        Main.getSpider().getMain().closePopup();
        Main.getSpider().getMainView().cleanMainGui();
        messageLable.setWrapText(true);

    }

    public void setMessage(String headLineText, String message1, String buttonText){
        headLine.setText(headLineText);
        if(message1 != null){
            messageLable.setText(message1);
        }
        okButton.setText(buttonText);
    }
}
