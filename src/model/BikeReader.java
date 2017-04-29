package model;

import ServerConnecttion.ServerCall;
import ServerConnecttion.ServerCallImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

/**
 * Created by Goloconda on 2017-04-29.
 */
public class BikeReader  extends Observable implements Runnable{
    private ArrayList<Bike> bikeList;
    private ServerCall serverCall;
    private Set<Bike> bikeSet;
    Bikes bikesObject;



    public BikeReader(){
        bikeList = new ArrayList<>();
        serverCall = new ServerCallImpl();
        bikeSet = new HashSet<>();

    }

    @Override
    public void run() {

          if (bikesObject == null) {
              bikesObject = serverCall.getNextTenAvailableBikes(0);
              bikeSet.addAll(bikesObject.getBikes());

          } else {
              while (bikesObject.isAllAvailableBikesSent()) {
                  bikesObject = serverCall.getNextTenAvailableBikes(bikesObject.getTenNextfromInt());
                  bikeSet.addAll(bikesObject.getBikes());
                  if (bikesObject.getBikes().size() < 10) {

                      System.out.println("Här är listan som kommer mindre än 10 ");
                  }
                  System.out.println("Setets längd " + bikeSet.size());
                  notifyObservers();
              }
          }

      }

    public Set<Bike> getBikeSet() {
        return bikeSet;
    }

    public void setBikeSet(Set<Bike> bikeSet) {
        this.bikeSet = bikeSet;
    }
}
