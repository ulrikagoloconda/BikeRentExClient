package view;

import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.BikeUser;
import model.MainViewInformaiton;

public class Main extends Application {
  private static SpiderView spider;
  public BikeUser currentUser;
  private Stage primaryStage;
  private FXMLLoader loginLoader;
  private FXMLLoader mainViewLoader;
  private FXMLLoader newUserLoader;
  private FXMLLoader deleteBikeLoader;
  private FXMLLoader adminLoader;
  private FXMLLoader changeUserViewLoader;
  private FXMLLoader changeTryLoader;
  private FXMLLoader addBikeLoader;
  private Scene loginScene;
  private Scene mainScene;
  private Scene adminScene;
  private Scene addBikeScene;
  private Scene deleteBikeScene;
  private Scene changeUserScene;
  private Scene newUserScene;
  private Scene statViewScean;
  private BikeUser user;
  private MainViewInformaiton mvi;
  private ServerCall serverCall ;

  @Override
  public void start(Stage primaryStage) throws Exception {
    spider = new SpiderView();
    spider.setMain(this);
    serverCall = new ServerCallImpl();
    this.primaryStage = primaryStage;
    loginLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/loginView.fxml"));
    Parent root = loginLoader.load();
    this.primaryStage.setTitle("Bike Rent");
    loginScene = new Scene(root, 600, 600);
    this.primaryStage.setScene(loginScene);
    this.primaryStage.show();

    this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {
        if(mvi != null) {
          serverCall.closeSession();
        }
      }
    });
  }

  public MainViewInformaiton getMainVI(){
    return mvi;
  }

  public static SpiderView getSpider() {
    return spider;
  }
  public void showLoginView() {
    try {
      primaryStage.setScene(loginScene);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showNewUserView() {
    if (newUserScene == null) {
      try {
        newUserLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/newUserView.fxml"));
        Parent newUserRoot = newUserLoader.load();
        newUserScene = new Scene(newUserRoot);
        primaryStage.setScene(newUserScene);
      } catch (Exception e) {
        e.printStackTrace();
      }

    } else {
      primaryStage.setScene(newUserScene);
    }
  }

  public void showMainView() {
    if (mainScene == null) {
      try {
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/mainView.fxml"));
        Parent mainRoot = mainViewLoader.load();
        mainScene = new Scene(mainRoot);
        primaryStage.setScene(mainScene);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      primaryStage.setScene(mainScene);
    }
  }

  public void showChangeUserView() {
    if (changeUserScene == null) {
      try {
        FXMLLoader changeTryLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/changeUserView1.fxml"));
        Parent changeRoot = changeTryLoader.load();
        changeUserScene = new Scene(changeRoot);
        primaryStage.setScene(changeUserScene);
      } catch (Exception e) {
        e.printStackTrace();

      }
    } else {
      primaryStage.setScene(changeUserScene);
    }
  }

  public void showStatView() {
    if (statViewScean == null) {
      try {
        FXMLLoader statLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/statView.fxml"));
        Parent statRoot = statLoader.load();
        statViewScean = new Scene(statRoot);
        primaryStage.setScene(statViewScean);
      } catch (Exception e) {
        e.printStackTrace();
      }

    } else {
      primaryStage.setScene(statViewScean);
    }
  }


  public void showDeleteView() {
    if (deleteBikeScene == null) {
      try {
        deleteBikeLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/deleteBikeView.fxml"));
        Parent deleteRoot = deleteBikeLoader.load();
        deleteBikeScene = new Scene(deleteRoot);
        primaryStage.setScene(deleteBikeScene);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      primaryStage.setScene(deleteBikeScene);
    }
  }

  public void showAdeminView() {
    if (adminScene == null) {
      try {
        System.out.println(primaryStage);
        adminLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/adminView.fxml"));
        Parent adminRoot = adminLoader.load();
        adminScene = new Scene(adminRoot);
        primaryStage.setScene(adminScene);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      primaryStage.setScene(adminScene);
    }
  }

  public void showAddBikeView() {
    if (addBikeScene == null) {
      try {
        addBikeLoader = new FXMLLoader(getClass().getResource("../view/viewfxml/addBikeView.fxml"));
        Parent addRoot = addBikeLoader.load();
        addBikeScene = new Scene(addRoot);
        primaryStage.setScene(addBikeScene);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      primaryStage.setScene(addBikeScene);
    }
  }
  public MainViewInformaiton getMvi() {
    return mvi;
  }

  public void setMvi(MainViewInformaiton mvi) {
    this.mvi = mvi;
  }

  public static void main(String[] args) {
    launch(args);
  }
}

