package ServerConnecttion;

import model.Bike;
import model.BikeUser;
import model.MainViewInformaiton;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Goloconda on 2016-12-01.
 */

//Interface som samlar metoderna som gör rest-anrop till server
public interface ServerCall {
    //Metoden gör restanrop som loggar in och öppnar en session genom att generera ett session token
    BikeUser login(String userName, String passw);

    //Metoden skapar en ny användare i databasen men öppnar inte ett nytt session
    boolean createNewUser(BikeUser newUser);

    //Metoden uppdaterar en användares användares användaruppgifter, dock inte användar-ID, metoden påverkar inte hanteringen av session
    boolean updateUser(BikeUser oldUser, BikeUser newUser);

    //Metoden returnerar alla cyklar som är lediga i databasen
    ArrayList<Bike> getAvailableBikes();

    //Metoden tar emto en sträng som används som en wildcard sökning i databasen och returnerar en map där en ny sträng med mer info om
    //det eftersökta eobjektet finns och en int som motsvarar cykelns id
    Map<String,Integer> getBikesFromSearch(String searchString);

    //Metoden lägger till en cykel med bild i databasen och returerar ett nytt objekt av klassen Bike med bike ID som är genererati databasen.
    Bike addBikeToDB(Bike newBike);

    //Metoden raderar en cykel från databasen
    String removeBikeFromDB(int bikeID);

    //Metoden utför ett cykellån, till i restanropet finns också ett user id som hämtas från en global variabel
    Bike executeBikeLoan(int bikeID);

    //Metoden upphäver ett lån och gör återigen cykeln tillgänglig för andra användare
    boolean returnBike(int userID, int bikeID);

    //Metoden returnerar ett objekt av klassen Bike som hämtats från databasen baserat på cyeklens id
    Bike getSingleBike(int bikeID);

    //Metoden stänger en öppen session i databasen och sätter variabeln sessionToken till -1 som sträng
    void closeSession();

    //Meoden returnerar på samtliga cyklar som finn i databasen. Informationen används av admin då någon cykel ska raderas.
    ArrayList<Bike> getAllBikes();

    //Metoden hämtar data för att kunna uppdater huvudfönstrets statistk och användaruppgifter
    MainViewInformaiton fetchUpdatedInfo();

}
