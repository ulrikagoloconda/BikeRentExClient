package view;


import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.BikeUser;
import helpers.Sound;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.MainViewInformaiton;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.apache.http.HttpHeaders.USER_AGENT;


/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class LoginVewController implements Initializable {
    @FXML
    private TextField userNameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private AnchorPane loginPane;
    private BikeUser currentUser;

    private ServerCall serverCall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getSpider().setLoginView(this);
        serverCall = new ServerCallImpl();
  }

    public void logInClick(Event event) {
        String userName = userNameText.getText();
        String passw = passwordText.getText();
        currentUser = serverCall.login( userName,  passw);
        System.out.println("phone from loging " + currentUser.getPhone());
        if (currentUser != null) {
            if (currentUser.getUserID() > 0) { //login = OK!!
                showMainGui();
            } else { // wrong ...
                System.out.println("Fel ...");
                ErrorView.showError("Inloggningsfel-logInClick", "fel vid inloggning", "Fail", 0, new Exception("Returned something but was unexpected.."));
            }
        }else { // wrong ...
            System.out.println("Fel ...");
            ErrorView.showError("Inloggningsfel-logInClick", "fel vid inloggning", "Fail", 0, new Exception("error..."));
        }

    }

    public void showMainGui() {
        if (currentUser == null) {
            currentUser = new BikeUser();
            currentUser.setlName("Override");
            currentUser.setfName("Override");
          currentUser.setUserName("Override");
            currentUser.setMemberLevel(1010);
            currentUser.setPhone(101010);
            currentUser.setEmail("Override@Override.com");
        }
        Main.getSpider().getMain().showMainView();
    }

    public void newUserClick(ActionEvent actionEvent) {
        Main.getSpider().getMain().showNewUserView();
    }

    public BikeUser getCurrentUser() {
        return currentUser;
    }
}
