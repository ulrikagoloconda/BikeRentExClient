package view;


import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import model.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-15
 */

//Klass som fungerar som controller till mainView. Här finns metoder som motsvarar funktionaliteten i programmets huvudfönster.
public class MainVewController implements Initializable {
    @FXML
    private TableColumn columCykel;
    @FXML
    private TableView<Bike> tableBikeView;
    @FXML
    private TableColumn<Bike, String> year, status, color, type, model, available;
    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView mainImage;
    @FXML
    private Label messageLabel;
    @FXML
    private Button executeLoanBtn, netBtn, adminBtn, returnBtn;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private Label userNameLabel, memberLevelLabel, activeLoanLabel, numberOfLoanedBikesLabel, statLabel;

    private Map<Node, Integer> idMap;
    private int selectedFromGrid;
    private ArrayList<Bike> availableBikes;
    private List<Bike> currentListInView;
    private PopulateType currentTypeInView;
    private MainViewInformaiton mvi;
    private Map<String, Integer> searchMap;
    private Bike selectedBikeSearch;
    private ArrayList<Bike> usersCurrentBikes;
    private ServerCall serverCall;
    private PrestandaMeasurement mesaurment;
    private boolean isUserInfoPopulated;


    private String errorTitle = "Fel i huvudfönster";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverCall = new ServerCallImpl();
        Main.getSpider().setMainView(this);
        populateUserTextInGUI(Main.getSpider().getMain().getMvi().getCurrentUser());
        if (Main.getSpider().getMain().getMvi().getCurrentUser().getMemberLevel() != 10) {
            adminBtn.setVisible(false);
        }
        netBtn.setDisable(true);
        executeLoanBtn.setDisable(true);
        combobox.setEditable(true);
        idMap = new HashMap<>();
        returnBtn.setVisible(false);
        currentListInView = new ArrayList<>();

        netBtn.setDisable(true);
        executeLoanBtn.setDisable(true);
        combobox.setEditable(true);
        idMap = new HashMap<>();
        returnBtn.setVisible(false);
        mesaurment = new PrestandaMeasurement();
        currentListInView = new ArrayList<>();
        serverCall = new ServerCallImpl();
        Image image = new Image("img/bike.png");
        mainImage.setImage(image);
        messageLabel.setWrapText(true);

    }

    public void restartMainGui(){
        BikeUser user = Main.getSpider().getMain().getMvi().getCurrentUser();
        if(user.getMemberLevel() >= 10){
            adminBtn.setVisible(true);
        }
        populateUserTextInGUI(user);
        cleanMainGui();
        if(availableBikes!= null) {
            availableBikes.clear();
        }
    }

    public void cleanMainGui() {
        gridPane.getChildren().clear();
        messageLabel.setText("");
        combobox.getItems().clear();
    }

    public void populateUserTextInGUI(BikeUser bikeUser) {
        ArrayList<Bike> bikesInUse = Main.getSpider().getMain().getMvi().getCurrentUser().getCurrentBikeLoans();
        ArrayList<Integer> totalBikes = Main.getSpider().getMain().getMvi().getCurrentUser().getTotalBikeLoans();
        userNameLabel.setText(bikeUser.getUserName());
        memberLevelLabel.setText("* " + bikeUser.getMemberLevel() + " *");
        activeLoanLabel.setText("" + bikesInUse.size());
        numberOfLoanedBikesLabel.setText("" + totalBikes.size());
        setUserInfoPopulated(true);
        setStatLabel();

    }

    public void setStatLabel() {
        mvi = Main.getSpider().getMain().getMvi();
        float total = mvi.getTotalBikes();
        float free = mvi.getAvailableBikes();
        float poc = free / total;
        poc = poc * 100;
        statLabel.setText("" + poc + " %");
    }


    public void searchAvailableBikes(ActionEvent actionEvent) {
        cleanMainGui();
        long millisStart = Calendar.getInstance().getTimeInMillis();
        executeLoanBtn.setDisable(true);
        netBtn.setVisible(false);
        availableBikes = serverCall.getAvailableBikes();
        if (availableBikes.size() > 3) {
            currentListInView = availableBikes.subList(0, 3);
            populateGridPane(PopulateType.AVAILABLE_BIKES, currentListInView);
        } else {
            populateGridPane(PopulateType.AVAILABLE_BIKES, availableBikes);
        }
        long millisStop = Calendar.getInstance().getTimeInMillis();
        double total = (millisStop - millisStart) / 1000.0;
        double perc = (millisStop - millisStart) / 1000.0;
        ServerCallImpl.getMesaurment().setTotalTimeSec(total);
        ServerCallImpl.getMesaurment().setPerceivedTimeAvailableBikesSec(perc);
        serverCall.insertPrestandaMeasurment(mesaurment, " Mätning av getAvailableBikes. Alla bikes kommer samtidit i samma tråd");
    }

    public void showAdminView(ActionEvent actionEvent) {
        Main.getSpider().getMain().showAdeminView();
    }

    //Denna metod används till fem olika syften, och beteendet är något olika beroende på vilken lista av cyklar som ska visas.
    //Det enum, PopulateType, som skickas som argument är ett sätt att separera de olika användingsområdena.
    //I och m ed att event är kopplade till de olika användningarna så sätts variabler globalt i klassen, currnetType, currnetListInView, för att
    //anpassa beteendet när event triggas till aktuell lista och aktuellt syfte
    public boolean populateGridPane(PopulateType type, List<Bike> bikeArray) {
        gridPane.getChildren().clear();
        returnBtn.setVisible(false);
        currentTypeInView = type;
        if (type == PopulateType.AVAILABLE_BIKES) {
            if (availableBikes.size() <= 3) {
                netBtn.setVisible(false);
            } else {
                netBtn.setDisable(false);
                netBtn.setVisible(true);
            }
        }
        if (type == PopulateType.USERS_CURRENT_BIKES) {
            if (usersCurrentBikes.size() <= 3) {
                netBtn.setVisible(false);
            } else {
                netBtn.setDisable(false);
                netBtn.setVisible(true);
            }
        }
        String[] topList = {"Bild", "Årsmodell", "Färg", "Cykeltyp", "Modell", "Ledig?"};
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Label l = new Label();
            l.setText(topList[i]);
            l.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
            gridPane.add(l, i, 0);
        }
        int j = 1;
        try {
            for (Bike b : bikeArray) {
                values.clear();
                values.add("" + b.getModelYear());
                values.add(b.getColor());
                values.add(b.getType());
                values.add(b.getBrandName());
                values.add("" + b.isAvailable());
                for (int i = 0; i < 6; i++) {
                    if (i == 0) {
                        BufferedImage theImage = ImageIO.read(b.getImageStream());
                        b.getImageStream().close();
                        Image image = SwingFXUtils.toFXImage(theImage, null);
                        ImageView iv = new ImageView();
                        iv.setFitHeight(65);
                        iv.setFitWidth(95);
                        iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Node n = (Node) event.getSource();
                                onClickActions(n);
                            }
                        });

                        idMap.put(iv, b.getBikeID());
                        iv.setImage(image);
                        gridPane.add(iv, i, j);
                    } else {
                        Label k = new Label();
                        k.setOnMouseClicked(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                Node n = (Node) event.getSource();
                                onClickActions(n);
                            }
                        });
                        k.setText(values.get(i - 1));
                        Font f = new Font(16);
                        k.setFont(f);
                        idMap.put(k, b.getBikeID());
                        gridPane.add(k, i, j);
                    }
                }
                j++;
                if (j > 3) {
                    return
                            false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    //Denna metod triggas av event då någon klickar, markerar en rad i gridPane. Här används den variabel currentTYpeInView som
    //sattes när metoden populateGridPane kördes.
    public void onClickActions(Node n) {
        if (currentTypeInView == PopulateType.SEARCH_RESULTS) {
            executeLoanBtn.setVisible(true);
            selectedFromGrid = selectedBikeSearch.getBikeID();
            String available = "";
            if (selectedBikeSearch.isAvailable()) {
                available = "Ja";
                executeLoanBtn.setDisable(false);
            } else {
                available = "Nej";
                executeLoanBtn.setDisable(true);
            }

            String s = "Årsmodell: " + selectedBikeSearch.getModelYear() + " Färg: " + selectedBikeSearch.getColor() + " Cykeltyp: " +
                    selectedBikeSearch.getType() + " Ledig? " + available;
            messageLabel.setText(s);
            executeLoanBtn.setVisible(true);
        } else if (currentTypeInView == PopulateType.USERS_CURRENT_BIKES) {
            selectedFromGrid = idMap.get(n);
            for (Bike b : usersCurrentBikes) {
                if (b.getBikeID() == selectedFromGrid) {
                    String s = "Årsmodell: " + b.getModelYear() + " Färg: " + b.getColor() + " Cykeltyp: " +
                            b.getType() + " \nÅterlämningsdag: " + b.getDayOfReturn();
                    messageLabel.setText(s);
                    returnBtn.setVisible(true);
                }
            }

        } else if (currentTypeInView == PopulateType.AVAILABLE_BIKES) {
            selectedFromGrid = idMap.get(n);
            String available = "";
            for (Bike b : availableBikes) {
                if (b.isAvailable()) {
                    available = "Ja";
                    executeLoanBtn.setDisable(false);
                } else {
                    available = "Nej";
                    executeLoanBtn.setDisable(true);
                }
                if (b.getBikeID() == selectedFromGrid) {

                    String s = "Årsmodell: " + b.getModelYear() + " Färg: " + b.getColor() + " Cykeltyp: " +
                            b.getType() + " Ledig? " + available;
                    messageLabel.setText(s);
                    executeLoanBtn.setVisible(true);
                }
            }
        }
    }

    public void nextBikesOnList(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        currentListInView.clear();
        if (currentTypeInView == PopulateType.AVAILABLE_BIKES) {
            if (availableBikes.size() >= 3) {
                currentListInView = availableBikes.subList(0, 3);
            } else {
                currentListInView = availableBikes.subList(0, availableBikes.size());
            }
        } else if (currentTypeInView == PopulateType.USERS_CURRENT_BIKES) {
            if (usersCurrentBikes.size() >= 3) {
                currentListInView = usersCurrentBikes.subList(0, 3);
            } else {
                currentListInView = usersCurrentBikes.subList(0, usersCurrentBikes.size());
            }
        }
        populateGridPane(currentTypeInView, currentListInView);

    }

    public void showChangeUserView(ActionEvent actionEvent) {
        Main.getSpider().getMain().showChangeUserView();
    }

    public void executeBikeLoan(ActionEvent actionEvent) {
        Bike b = serverCall.getSingleBike(selectedFromGrid);
        if (b.isAvailable()) {
            Bike rentedBike = serverCall.executeBikeLoan(b.getBikeID());
           //messageLabel.setText("Cykeln är lånad till och med " + rentedBike.getDayOfReturn());
            System.out.println("Är det detta som tar sån tid, innan i execute " + rentedBike.getImageStream());
            Main.getSpider().getMain().showPupupInfo("Lån genomfört", "Ditt lån är nu genomomfört och \n den är tillgänglig för dig \n till och med " + rentedBike.getDayOfReturn(), "Ok, Jag fattar!");
            System.out.println("Är det detta som tar sån tid, innan i execute ");
            rentedBike.setAvailable(false);
            List<Bike> bikeList = new ArrayList<>();
            bikeList.add(rentedBike);

            populateGridPane(PopulateType.RENTED_BIKE, bikeList);
            serverCall.fetchUpdatedInfo();
            populateUserTextInGUI(Main.getSpider().getMain().getMvi().getCurrentUser());
            setStatLabel();
        } else {
            Main.getSpider().getMain().showPupupInfo("Lån inte genomfört", "Cykeln är tyvärr inte ledig",  "Ok, Jag fattar!");
            //messageLabel.setText("Cykeln är tyvärr inte ledig");
        }
    }


    public void popuateComboBox(Event event) {
        searchMap = serverCall.getBikesFromSearch(combobox.getEditor().getText());
        int count = 0;
        combobox.getItems().clear();
        for (Map.Entry<String, Integer> entry : searchMap.entrySet()) {
            if (count > 10) {
                break;
            }
            combobox.getItems().add(entry.getKey());
            count++;
        }
    }


    public void setSearchResult(ActionEvent actionEvent) {
        if (combobox.getSelectionModel().getSelectedItem() != null) {
            String selected = combobox.getSelectionModel().getSelectedItem().toString();
            if (searchMap.containsKey(selected)) {
                int bikeID = searchMap.get(selected);
                selectedBikeSearch = serverCall.getSingleBike(bikeID);
                List<Bike> bike = new ArrayList<>();
                bike.add(selectedBikeSearch);
                populateGridPane(PopulateType.SEARCH_RESULTS, bike);
            }
        }
    }

    public void showStatClick(ActionEvent actionEvent) {
        Main.getSpider().getMain().showStatView();
    }

    public void showUsersBikes(ActionEvent actionEvent) {
        usersCurrentBikes = Main.getSpider().getMain().getMvi().getCurrentUser().getCurrentBikeLoans();

        if (usersCurrentBikes.size() > 3) {
            currentListInView = usersCurrentBikes.subList(0, 3);
            populateGridPane(PopulateType.USERS_CURRENT_BIKES, currentListInView);
        } else {
            populateGridPane(PopulateType.USERS_CURRENT_BIKES, usersCurrentBikes);
        }
    }

    public void returnBike(ActionEvent actionEvent) {
        Bike returnedBike = null;
        for (Bike b : usersCurrentBikes) {
            if (b.getBikeID() == selectedFromGrid) {
                returnedBike = b;
            }
        }
        LocalDate dayOfReturn = returnedBike.getDayOfReturn();
        boolean result = serverCall.returnBike(Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID(), returnedBike.getBikeID());
        if (result == true) {
            serverCall.fetchUpdatedInfo();
            populateUserTextInGUI(Main.getSpider().getMain().getMainVI().getCurrentUser());
            setStatLabel();
            returnedBike.setAvailable(true);
            List<Bike> bike = new ArrayList<>();
            bike.add(returnedBike);
            populateGridPane(PopulateType.RETURNED_BIKE, bike);
            Main.getSpider().getMain().showPupupInfo("Cykel återlämnad", "Du har nu lämnat tillbaka cykeln. \n Återlämningsdatum: " + dayOfReturn  + " \n Dagens datum: " + LocalDate.now(), "Ok, Jag fattar!");
        }else {
            Main.getSpider().getMain().showPupupInfo("Något har gått fel", "Cykeln är inte återlämnad. \n Kontakta kontoret för mer information", "Ok, Jag fattar!");

        }
    }

    public void closeSession(ActionEvent actionEvent) {
        setUserInfoPopulated(false);
        serverCall.closeSession();
        Main.getSpider().getMain().showLoginView();
    }

    public void searchAvailableBikesThred(){

    }
    public PrestandaMeasurement getPrestandaMesaurment(){
        return mesaurment;
    }

    public boolean isUserInfoPopulated() {
        return isUserInfoPopulated;
    }

    public void setUserInfoPopulated(boolean userInfoPopulated) {
        isUserInfoPopulated = userInfoPopulated;
    }
}

