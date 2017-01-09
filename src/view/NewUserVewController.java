package view;
//no..


import ServerConnecttion.ServerCallImpl;
import helpers.EmailValidator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.BikeUser;

import java.net.URL;
import java.util.ResourceBundle;

public class NewUserVewController implements Initializable {

  public BikeUser currentUser;
  @FXML
  private TextField userNameText;
  @FXML
  private TextField fNameText;
  @FXML
  private TextField lNameText;
  @FXML
  private TextField mailText;
  @FXML
  private TextField phoneText;
  @FXML
  private TextField passwordText;
  @FXML
  private TextField passwordCheckerText;
  @FXML
  private Label uniqeTextIdLabel;
  @FXML

  private String errorTitle = "fel i lägg till användare";
  private ServerCallImpl serverCall;

  public NewUserVewController() {
    serverCall = new ServerCallImpl();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Main.getSpider().setNewUserView(this);

  }

  public void logInClick(Event event) {


    System.out.println("logInClick");

  }


  public void showLoginGui() {
    Main.getSpider().getMain().showNewUserView();
    serverCall = new ServerCallImpl();
  }


  public void newUserClick(ActionEvent actionEvent) {
    System.out.println("clicked on newUserClick");
    String userName = userNameText.getText();
    String fName = fNameText.getText();
    String lName = lNameText.getText();
    String email = mailText.getText();
    String phoneString = phoneText.getText();
    String password = passwordText.getText();
    String passwordChecker = passwordCheckerText.getText();
    phoneString.replace("-", "");
    phoneString.replace("+", "");

      if (userName.length() < 5) {
        System.out.println("username to short");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("username is to short!"));
      }
      /*else if (!dbAccess.isUserAvalible(userName)) {
        System.out.println("username not free");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("username is allready taken!"));
      */
      else if (password.length() < 1) {
        System.out.println("password is to short!");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("password is to short!"));
      } else if (!password.equals(passwordChecker)) {
        System.out.println("passw not same");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("password is to not the same!"));
      } else if (!EmailValidator.validate(email)) {
        System.out.println("email not ok format!");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("email not ok format!"));
      } else if (phoneString.length() < 2) {
        System.out.println("phone is to short!");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("phone is to short!"));
      } else if (fName.length() < 1) {
        System.out.println("fName is to short!");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("First Name is to short!"));
      } else if (lName.length() < 1) {
        System.out.println("phone is to short!");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("Last Name is to short!"));
      } else if (phoneString.length() < 2) {
        System.out.println("phone is to short!");
        ErrorView.showError(errorTitle, "fel vid uppdatering", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception("phone is to short!"));
      } else {
        int phone = Integer.parseInt(phoneString);
        System.out.println("we can now add some info");
        int in_memberlevel = 1;
        BikeUser newUser = new BikeUser(fName, lName, in_memberlevel, email, phone, userName, password);
        System.out.println(serverCall + " serverCall");
        boolean isAddUserOK = serverCall.createNewUser(newUser);
            //dbAccess.InsertNewUser(fName, lName, in_memberlevel, email, phone, userName, password);
        if (!isAddUserOK) {
          ErrorView.showError(errorTitle, "fel vid inläsning", "Kontrollera era uppgifter", currentUser.getUserID(), new Exception(" :-( kunde inte lägga till användare"));
        }
        if (isAddUserOK) {
          //boolean d = DialogView.showSimpleInfo("Ny användare upplaggd", "Lyckades", "Ny användare är nu upplagd, öppnar nu inloggningsrutan");
      /*boolean whantToSentMail = DialogView.showOK_CANCEL_Dialog("Ny användare upplaggd", "Lyckades, vill ni skicka iväg ett email", "Ny användare är nu upplagd");
      if (whantToSentMail) {
        boolean isSentMailOK = SentMail.sendNewUser(fName, email);
        if (isSentMailOK) {
          boolean d = DialogView.showSimpleInfo("Mailutskick", "mail till er är skickat, Lyckades", "Öppnar nu inloggningsrutan");
        }
      }*/
          Main.getSpider().getMain().showLoginView();
        }

//      ErrorView.showError("Inloggningsfel", "fel vid inloggning","Kontrollera era uppgifter" ,  e);
    }

  }


  public void checkUserName(KeyEvent keyEvent) {
    System.out.println("entering get username");
    String userName = userNameText.getText();
    if (userName.length() <= 5) {
      uniqeTextIdLabel.setText("Måste vara längre än 5 tecken");
      uniqeTextIdLabel.setTextFill(Color.RED);
    } else {
      //userName
 /*     try {
        if (!dbAccess.isUserAvalible(userName)){
          uniqeTextIdLabel.setText("Ej unikt..");
          uniqeTextIdLabel.setTextFill(Color.RED);
        }
        else{
          System.out.println("Ok username");
          uniqeTextIdLabel.setText("Användarnamn OK..");
          uniqeTextIdLabel.setTextFill(Color.GREEN);
        }
      } catch (SQLException e) {
        processException(e);
        uniqeTextIdLabel.setText("Ej unikt..");
        uniqeTextIdLabel.setTextFill(Color.RED);
        ErrorView.showError("Inloggningsfel", "fel vid inloggning","Kontrollera era uppgifter" ,  e);
      }
*/
    }

  }

  public void abortClick(ActionEvent actionEvent) {
    System.out.println("abort click");
    Main.getSpider().getMain().showLoginView();
   /* try {
      FXMLLoader loginLoader = Main.getSpider().getMain().getLoginViewLoader();
      Parent loginRoot = (Parent) loginLoader.load();
      Scene loginScean = new Scene(loginRoot);
      Main.getSpider().getMain().getPrimaryStage().setScene(loginScean);

    } catch (IOException e) {
      e.printStackTrace();
      ErrorView.showError(errorTitle, "fel vid Öppnining av data..", "starta om denna session..", e);
    }
    */

  }


}
