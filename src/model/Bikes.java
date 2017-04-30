package model;

import java.util.ArrayList;
import java.util.Map;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class Bikes {

    private ArrayList<Bike> bikes;
    private Map<String,Integer> searchResults;
    private Integer tenNextfromInt;
    private Integer lasID;
    private Integer numberOfBikesRead;
    private PrestandaMeasurement prestandaMeasurement;
    public Bikes(){
        bikes = new ArrayList<>();
    }

    public ArrayList<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(ArrayList<Bike> bikes) {
        this.bikes = bikes;
    }

    public Map<String, Integer> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(Map<String, Integer> searchResults) {
        this.searchResults = searchResults;
    }

    public PrestandaMeasurement getPrestandaMeasurement() {
        return prestandaMeasurement;
    }

    public void setPrestandaMeasurement(PrestandaMeasurement prestandaMeasurement) {
        this.prestandaMeasurement = prestandaMeasurement;
    }

    public Integer getTenNextfromInt() {
        return tenNextfromInt;
    }

    public void setTenNextfromInt(Integer tenNextfromInt) {
        this.tenNextfromInt = tenNextfromInt;
    }

    public Integer getLasID() {
        return lasID;
    }

    public void setLasID(Integer lasID) {
        this.lasID = lasID;
    }

    public Integer getNumberOfBikesRead() {
        return numberOfBikesRead;
    }

    public void setNumberOfBikesRead(Integer numberOfBikesRead) {
        this.numberOfBikesRead = numberOfBikesRead;
    }
}
