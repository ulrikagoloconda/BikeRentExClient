package view;


import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
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
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-15
 */

//Klass som fungerar som controller till mainView. Här finns metoder som motsvarar funktionaliteten i programmets huvudfönster.
public class MainVewController implements Initializable{
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
    private Label messageLabel, counter, counter1;
    @FXML
    private Button executeLoanBtn, netBtn, adminBtn, returnBtn;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private Label userNameLabel, memberLevelLabel, activeLoanLabel, numberOfLoanedBikesLabel, statLabel;
    @FXML
    private ProgressIndicator progress;

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
    private  BikeReader bikeReader;
   private ObservableList<Node> obserableGrid;
    private ObservableList<Bike> obserableBikeList;
    private int slideNumber;
    private boolean progressStopper;
    //Mätning av prestanda
    private  long millisStop;
    private long milliStopFinnish;
   private long millisStart;




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
        obserableGrid = gridPane.getChildren();
        slideNumber = 0;
        progress.setVisible(false);

    }

    public void restartMainGui(){
        closeThred();
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
        currentListInView.clear();
        netBtn.setDisable(true);
        counter.setVisible(false);
        counter1.setText("");
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
           stopProgress();
            if (availableBikes.size() <= 3) {
                netBtn.setVisible(false);
            } else {
                netBtn.setDisable(false);
                netBtn.setVisible(true);
            }
        }
        if (type == PopulateType.USERS_CURRENT_BIKES) {
            executeLoanBtn.setVisible(false);
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
            slideNumber++;
            counter1.setText("Sidan " + slideNumber + "  /");
            availableBikes.addAll(bikeReader.getBikeSet());
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
        if(currentTypeInView == PopulateType.AVAILABLE_BIKES){
            String slideString = slideNumber+"";
            if(bikeReader.getMessage().equals(slideString)){
                netBtn.setDisable(true);
                closeThred();

            }
        }
    }

    public void showChangeUserView(ActionEvent actionEvent) {
        Main.getSpider().getMain().showChangeUserView();
    }

    public void executeBikeLoan(ActionEvent actionEvent) {
        closeThred();
        Bike b = serverCall.getSingleBike(selectedFromGrid);
        if (b.isAvailable()) {
            Bike rentedBike = serverCall.executeBikeLoan(b.getBikeID());
            Main.getSpider().getMain().showPupupInfo("Lån genomfört", "Ditt lån är nu genomomfört och \n den är tillgänglig för dig \n till och med " + rentedBike.getDayOfReturn(), "Ok, Jag fattar!");
            rentedBike.setAvailable(false);
            List<Bike> bikeList = new ArrayList<>();
            bikeList.add(rentedBike);

            populateGridPane(PopulateType.RENTED_BIKE, bikeList);
            serverCall.fetchUpdatedInfo();
            populateUserTextInGUI(Main.getSpider().getMain().getMvi().getCurrentUser());
            setStatLabel();
        } else {
            Main.getSpider().getMain().showPupupInfo("Lån inte genomfört", "Cykeln är tyvärr inte ledig",  "Ok, Jag fattar!");
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
        closeThred();
        cleanMainGui();
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


    public PrestandaMeasurement getPrestandaMesaurment(){
        return mesaurment;
    }

    public boolean isUserInfoPopulated() {
        return isUserInfoPopulated;
    }

    public void setUserInfoPopulated(boolean userInfoPopulated) {
        isUserInfoPopulated = userInfoPopulated;
    }


    public void searchAvailableBikesThread(ActionEvent actionEvent) {
        startProgress();
        progress.setProgress(0.1);
        closeThred();
        cleanMainGui();
        counter.setVisible(true);
        millisStart = Calendar.getInstance().getTimeInMillis();
        int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
        progress.setProgress(0.2);
        Bikes bikes = null;
        Task<Bikes> task = new Task<Bikes>() {
            @Override
            public Bikes call() throws Exception {
                Bikes bikes = serverCall.getNextTenAvailableBikes(0, 4, userID);
                return bikes;
            }
        };
        new Thread(task).start();

        progress.setProgress(0.3);

        try {
            bikes = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        progress.setProgress(0.4);
        //Bikes bikes = serverCall.getNextTenAvailableBikes(0, 4,userID);
        if(bikes.getBikes().size()<3) {
             readBikesNoID();
        } else {
             availableBikes = bikes.getBikes();
             Collections.shuffle(availableBikes);
             populateGridPane(PopulateType.AVAILABLE_BIKES, availableBikes);
             millisStop = Calendar.getInstance().getTimeInMillis();
             progress.setProgress(0.4);
             bikeReader = new BikeReader();
             progress.progressProperty().bind(bikeReader.progressProperty());
             bikeReader.setNumberOfBikesToRead(5);
             counter.textProperty().bind(bikeReader.messageProperty());
             slideNumber = 1;
             counter1.setText("Sida " + slideNumber + "  /");
             obserableBikeList = FXCollections.<Bike>observableArrayList();

             Thread bikeGrabberThread = new Thread(bikeReader);
             bikeGrabberThread.setDaemon(true);
             bikeGrabberThread.start();
         }
    }

    public void startProgress() {
        progress.setProgress(56);
       // progress.setVisible(true);

    }

    public void stopProgress(){
       // progress.setProgress(0.7);
       // progress.setVisible(false);
    }

    public void readBikesNoID(){
        Bikes bikesNoID = serverCall.getNextTenAvailableBikes(0, 4);
        availableBikes = bikesNoID.getBikes();
        Collections.shuffle(availableBikes);
        populateGridPane(PopulateType.AVAILABLE_BIKES, availableBikes);
        millisStop = Calendar.getInstance().getTimeInMillis();
        bikeReader = new BikeReader();
        bikeReader.setNumberOfBikesToRead(5);
        counter.textProperty().bind(bikeReader.messageProperty());
        slideNumber = 1;
        counter1.setText("Sida "+slideNumber+"  /");
        obserableBikeList = FXCollections.<Bike>observableArrayList();
        Thread bikeGrabberThread = new Thread(bikeReader);
        bikeGrabberThread.setDaemon(true);
        bikeGrabberThread.start();
    }

    public ObservableList<Node> getObserableGrid() {
        return obserableGrid;
    }

    public void setObserableGrid(ObservableList<Node> obserableGrid) {
        this.obserableGrid = obserableGrid;
    }

    public ObservableList<Bike> getObserableBikeList() {
        return obserableBikeList;
    }

    public void setObserableBikeList(ObservableList<Bike> obserableBikeList) {
        this.obserableBikeList = obserableBikeList;
    }

    public void setMesaurmentStop() {
        System.out.println("Här körs setMesaurmentStop");
        PrestandaMeasurement pm = new PrestandaMeasurement();
        double perc = (millisStop - millisStart) / 1000.0;
        milliStopFinnish =  Calendar.getInstance().getTimeInMillis();
        double total = (milliStopFinnish -millisStart) / 1000.0;
        System.out.println();
        pm.setPerceivedTimeAvailableBikesSec(perc);
        pm.setTotalTimeSec(total);
        mesaurment = pm;
        PrestandaMeasurement mpFromServer = bikeReader.getBikesObject().getPrestandaMeasurement();
        mesaurment.setExecuteSec(mpFromServer.getExecuteSec());
        mesaurment.setReadFromDbJdbcSec(mpFromServer.getReadFromDbJdbcSec());
        mesaurment.setDbProcedureSec(mpFromServer.getDbProcedureSec());
        serverCall.insertPrestandaMeasurment(mesaurment, " Mätning av getAvailableBikes. Första tre läses först, därefter laddas cyklar i en annan tråd");

    }

    public void closeThred(){


        if(bikeReader!=null) {
            bikeReader.cancel();
            counter.setVisible(false);
        }
    }

    public ProgressIndicator getProgress() {
        return progress;
    }

    public void setProgress(ProgressIndicator progress) {
        this.progress = progress;
    }
}



/*

TODO
kolla alla rubriker och nivåer så att de överrensstämmer

•
•	Se till så att något händer omedelbart då en knapp trycks in.


 */