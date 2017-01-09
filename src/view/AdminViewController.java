package view;


import model.Bike;
import model.BikeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-17
 */
public class AdminViewController implements Initializable {
  private Bike newBike;
  private BikeUser currentUser;
  private static AdminViewController _adminViewController;
  //private loginVewController loginView;
  @FXML
  private Label urlLabel;
  @FXML
  private TextField brandText, modelYearText, colorText, typeText, sizeText;
  @FXML
  private GridPane gridDelBike;
  @FXML
  private AnchorPane deletePane, addBikePane;
  @FXML
  private Pane editPane;
  @FXML
  private Button returnBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
      _adminViewController = this;
    Main.getSpider().setAdminView(this);
    returnBtn.setDisable(true);
  }

  public void showDeleteView(ActionEvent actionEvent) {
    Main.getSpider().getMain().showDeleteView();
  }

  public void showAddView(ActionEvent actionEvent) {
    Main.getSpider().getMain().showAddBikeView();
  }

  public void showMainGui(ActionEvent actionEvent) {
    Main.getSpider().getLoginView().showMainGui();

  }

    public static AdminViewController getAdminViewController(){
        return _adminViewController;
    }

  public void returnBike(ActionEvent actionEvent) {
   // AccessBike.returnBike(18, 1);
      //Användes för test av databasfunktion.
  }
}
