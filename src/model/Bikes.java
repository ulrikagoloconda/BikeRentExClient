package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class Bikes {
    //Lite tillfällig klass, vi kan behöva bygga ut denna för att anpassa till det vi behöver
    ArrayList<Bike> bikes;
    private Map<String,Integer> searchResults;
    private PrestandaMeasurement prestandaMeasurement;



    public Bikes(){
        bikes = new ArrayList<>();
        searchResults = new HashMap<>();
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
}
