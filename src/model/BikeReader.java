package model;

import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
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


    public BikeReader() {


        bikeList = new ArrayList<>();
        serverCall = new ServerCallImpl();
        bikeSet = new HashSet<>();
        obserableList = FXCollections.<Bike>observableArrayList();
        updateValue(obserableList);
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
        System.out.println("Trådid i run " + Thread.currentThread().getId());
        boolean check = false;
        while (!check) {
            if (bikesObject == null) {
                bikesObject = serverCall.getNextTenAvailableBikes(0, numberOfBikesToRead);
                bikeSet.addAll(bikesObject.getBikes());

            } else {

                bikesObject = serverCall.getNextTenAvailableBikes(bikesObject.getTenNextfromInt(), numberOfBikesToRead);
                bikeSet.addAll(bikesObject.getBikes());
                if (bikesObject.getBikes().size() < numberOfBikesToRead) {
                    check = true;
                }
            }
            Thread.sleep(100);
            updateMessage(obserableList.size() + " längden på listan");
            updateGui();
        }
        return null;
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
}
