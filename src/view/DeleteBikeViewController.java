package view;

import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import model.Bike;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.MainViewInformaiton;

import java.net.URL;
import java.util.*;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-19
 */

public class DeleteBikeViewController implements Initializable {
    @FXML
    private GridPane gridDelBike;
    @FXML
    private Label deleteLabel, messLabel;
    @FXML
    private Button deleteBikeBtn, forwardBtn, backToMain;
    @FXML
    private AnchorPane deletePane;
    private Map<Node, Integer> idMap;
    private static ArrayList<Bike> allBikes;
    private Bike selected = null;
    private int bikeIdDel;
    private int lastIndex;
    private List<Bike> smallList;
    private ServerCall serverCall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getSpider().setDeleteView(this);
        serverCall = new ServerCallImpl();
        idMap = new HashMap<>();
        deleteBikeBtn.setVisible(false);
        backToMain.setVisible(true);
        initDeleteView();

    }


    public void initDeleteView() {
        System.out.println(allBikes + " " + serverCall);
        allBikes = serverCall.getAllBikes();
        if (allBikes.size() > 10) {
            smallList = allBikes.subList(0, 9);
            populateDeleteGrid(smallList);
            forwardBtn.setDisable(false);
        } else {
            populateDeleteGrid(allBikes);
            forwardBtn.setDisable(true);

        }
    }

    public void populateDeleteGrid(List<Bike> bikeList) {
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("CykelID");
        columnNames.add("Modell");
        columnNames.add("Årsmodell");
        columnNames.add("Storlek");
        columnNames.add("Färg");
        columnNames.add("Cykeltyp");
        ArrayList<String> values = new ArrayList<>();
        int j = 0;
        for (Bike b : bikeList) {
            values.clear();
            values.add("" + b.getBikeID());
            values.add(b.getBrandName());
            values.add("" + b.getModelYear());
            values.add("" + b.getSize());
            values.add(b.getColor());
            values.add(b.getType());

            for (int i = 0; i < 6; i++) {
                if (j == 0) {
                    gridDelBike.add(new Label(columnNames.get(i)), i, j);
                } else {
                    Label k = new Label(values.get(i));
                    k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Label l = (Label) event.getSource();
                            bikeIdDel = idMap.get(l);

                            for (Bike b : allBikes) {
                                if (b.getBikeID() == bikeIdDel) {
                                    selected = b;
                                    break;
                                }
                            }

                            String s = selected.getBikeID() + " " + selected.getBrandName() + " " + selected.getModelYear() +
                                    " " + selected.getSize() + " " + selected.getColor() + " " + selected.getType();
                            deleteLabel.setText(s);
                            deleteBikeBtn.setVisible(true);
                        }
                    });

                    idMap.put(k, b.getBikeID());
                    gridDelBike.add(k, i, j);
                    lastIndex = idMap.get(k);
                }
            }
            j++;
        }
    }


    public void deleteBike(Event event) {
        String mess = serverCall.removeBikeFromDB(bikeIdDel);
        messLabel.setText(mess);
        deletePane.setVisible(false);

    }

    public void nextView(ActionEvent actionEvent) {
        gridDelBike.getChildren().clear();
        smallList.clear();
        if (allBikes.size() > 10) {
            smallList = allBikes.subList(0, 9);
            populateDeleteGrid(smallList);
        } else {
            smallList = allBikes.subList(0, allBikes.size() - 1);
            populateDeleteGrid(smallList);
            forwardBtn.setDisable(true);
        }
    }


    public void showMainView(ActionEvent actionEvent) {
        MainViewInformaiton mvi = serverCall.fetchUpdatedInfo();
        Main.getSpider().getMain().getMvi().setTotalBikes(mvi.getTotalBikes());
        Main.getSpider().getMain().getMvi().setAvailableBikes(mvi.getAvailableBikes());
        Main.getSpider().getMainView().setStatLabel();
        Main.getSpider().getLoginView().showMainGui();
    }
}
