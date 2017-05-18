package model;

import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.ProgressBar;
import view.Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Goloconda on 2017-04-29.
 */
public class BikeReader extends Task {
    private ArrayList<Bike> bikeList;
    private ServerCall serverCall;
    private Set<Bike> bikeSet;
    private Bikes bikesObject;
    private int numberOfBikesToRead;
    private ObservableList<Bike> obserableList;
    private boolean isStillFetching;


    public BikeReader() {
        bikeList = new ArrayList<>();
        serverCall = new ServerCallImpl();
        bikeSet = new HashSet<>();
        obserableList = FXCollections.<Bike>observableArrayList();
        updateValue(obserableList);
        isStillFetching = true;
    }

    public  Set<Bike> getBikeSet() {
        return bikeSet;
    }

    public void setBikeSet(Set<Bike> bikeSet) {
        this.bikeSet = bikeSet;
    }

    public int getNumberOfBikesToRead() {
        return numberOfBikesToRead;
    }

    public void setNumberOfBikesToRead(int numberOfBikesToRead) {
        this.numberOfBikesToRead = numberOfBikesToRead;
    }


    @Override
    protected Object call() throws Exception {
        //Platform.runLater();
        //Main.getSpider().getMainView().getProgress().
      //  Main.getSpider().getMainView().getProgress().progressProperty().bind(this.progressProperty());
         int max = 100;
        //for (int i = 1; i <= max; i++) {
        //    updateProgress(i, max);
        //    Thread.sleep(500);
        //    System.out.println(i);
        //}
        double d = 0.1;

        updateProgress(d, 1);
        int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
        for (int i = 0; i < 10; i++) {
            if (bikesObject == null) {
                bikesObject = serverCall.getNextTenAvailableBikes(0, numberOfBikesToRead,userID);
                System.out.println("hämtats med id 1 gång");
                if(bikesObject.getBikes().size()<1){
                    readWithoutID();
                    break;
                }else {
                    bikeSet.addAll(bikesObject.getBikes());
                }

            }else if(i > 2) {
                bikesObject = serverCall.getNextTenAvailableBikesNotPrevious(bikesObject.getTenNextfromInt(), numberOfBikesToRead,userID);
                System.out.println("hämtat id resten av gångerna gång");
                bikeSet.addAll(bikesObject.getBikes());
                if (i==9) {
                    Main.getSpider().getMainView().setMesaurmentStop();
                }

            } else {
                bikesObject = serverCall.getNextTenAvailableBikesNotPrevious(bikesObject.getTenNextfromInt(), numberOfBikesToRead,userID);
                System.out.println("hämtats med id resten av gångerna gång");
                bikeSet.addAll(bikesObject.getBikes());
            }
            d+=0.1;
            updateProgress(d, 1);
            Thread.sleep(100);
            updateMessage((obserableList.size() / 3) + "");
            updateGui();
        }
        return null;
    }

    public void readWithoutID(){
        System.out.println("Går in i read utan id");
        for (int i = 0; i < 10; i++) {
            if (bikesObject == null) {
                bikesObject = serverCall.getNextTenAvailableBikes(0, numberOfBikesToRead);
                    bikeSet.addAll(bikesObject.getBikes());

            } else {
                bikesObject = serverCall.getNextTenAvailableBikes(bikesObject.getTenNextfromInt(), numberOfBikesToRead);
                bikeSet.addAll(bikesObject.getBikes());
                if (i==9) {
                    Main.getSpider().getMainView().setMesaurmentStop();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateMessage((obserableList.size() / 3) + "");
            updateGui();
        }
    }

   public void updateGui(){
        obserableList.clear();
        obserableList.addAll(bikeSet);
        updateValue(obserableList);
   }

    public ObservableList<Bike> getObserableList() {
        return obserableList;
    }

    public void setObserableList(ObservableList<Bike> obserableList) {
        this.obserableList = obserableList;
    }

    public boolean isStillFetching() {
        return isStillFetching;
    }

    public void setStillFetching(boolean stillFetching) {
        isStillFetching = stillFetching;
    }

    public Bikes getBikesObject() {
        return bikesObject;
    }

    public void setBikesObject(Bikes bikesObject) {
        this.bikesObject = bikesObject;
    }
}
