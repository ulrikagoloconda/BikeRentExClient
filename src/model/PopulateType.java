package model;

/**
 * Created by Goloconda on 2016-12-02.
 */

//Enum används som argument i metoden populateGrid i MainViewController för att hålla isär de olika ändamålen som den metoden används till
public enum PopulateType {
    AVAILABLE_BIKES, USERS_CURRENT_BIKES, SEARCH_RESULTS, RETURNED_BIKE, RENTED_BIKE;
}
