package ServerConnecttion;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Bike;
import model.BikeUser;
import model.Bikes;
import model.MainViewInformaiton;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import view.ErrorView;
import view.Main;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by Goloconda on 2016-12-01.
 */
public class ServerCallImpl implements ServerCall {
    @Override
    public BikeUser login(String userName, String passw) {
        //start: "try login"
        BikeUser user = new BikeUser();
        String urlString = "http://localhost:8080/text/resources";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost requsetPost = new HttpPost(urlString);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("userName", userName);
        jsonObject.addProperty("passw", passw);
        String valuePair = jsonObject.toString();

        HttpEntity entity = null;
        String json = null;
        String errorText = "";
        int httpResponseCode = 0;//404;
        try {
            entity = new StringEntity(valuePair);
            requsetPost.setEntity(entity);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            HttpResponse response = client.execute(requsetPost);
            httpResponseCode = response.getStatusLine().getStatusCode();
            System.out.println("Code " + httpResponseCode);
            json = EntityUtils.toString(response.getEntity());
            System.out.println(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            errorText = e.toString();
            httpResponseCode = 1;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            errorText = e.toString();
            httpResponseCode = 2;
        } catch (IOException e) {
            e.printStackTrace();
            errorText = e.toString();
            httpResponseCode = 3;
        }
        if (httpResponseCode != 200) { //error in request or connection
            System.out.println("Fel på path eller server..");
            System.out.println(errorText);
            ErrorView.showError("Inloggningsfel-serverCall", "fel vid inloggning", "Fail", 0, new Exception("httpResponseCode" + httpResponseCode + errorText));
            Main.getSpider().getMain().showLoginView();

        } else {
            Gson gson = new Gson();
            MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
            System.out.println("totalloanslient mm:" + mvi.getCurrentUser().getCurrentBikeLoans() + " & " + mvi.getCurrentUser().getTotalBikeLoans() + " phone : " + mvi.getCurrentUser().getPhone() );

            if (mvi.getCurrentUser().getUserID() > 0) { //login = OK!!
                user = mvi.getCurrentUser();
                System.out.println("user " + user);
                Main.getSpider().getMain().setMvi(mvi);
                //TODO kolla detta
             //  Main.getSpider().getMainView().populateUserTextInGUI(mvi.getCurrentUser());

                return user;
            } else { // wrong password
                System.out.println("Fel lösenord eller användarnam");
                ErrorView.showError("Inloggningsfel", "fel vid inloggning", "Kontrollera era uppgifter", 0, new Exception(httpResponseCode + "Wrong user information." + errorText));
                Main.getSpider().getMain().showLoginView();
            }
        }
        return null;
    }

    @Override
    public boolean createNewUser(BikeUser newUser) {
        String urlString = "http://localhost:8080/text/resources/newUser";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost requsetPost = new HttpPost(urlString);
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        String json = gson.toJson(newUser);


        HttpEntity entity = null;
        String errorText = "";
        int httpResponseCode = 0;//404;
        try {
            entity = new StringEntity(json);
            requsetPost.setEntity(entity);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            HttpResponse response = client.execute(requsetPost);
            httpResponseCode = response.getStatusLine().getStatusCode();
            System.out.println("Code " + httpResponseCode);
            //TODO borde kolla att det är status 200, annars händer nåt bäääd
            json = EntityUtils.toString(response.getEntity());
            System.out.println(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            errorText = e.toString();
            httpResponseCode = 1;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            errorText = e.toString();
            httpResponseCode = 2;
        } catch (IOException e) {
            e.printStackTrace();
            errorText = e.toString();
            httpResponseCode = 3;
        }
        if (httpResponseCode != 200) { //error in request or connection
            System.out.println("Fel på path eller server.." + errorText);
            ErrorView.showError("addUser-serverCall", "fel vid addUser", "Fail", 0, new Exception("httpResponseCode" + httpResponseCode + errorText));
        } else {
            gson = new Gson();
            boolean isAddedOk = gson.fromJson(json, boolean.class);
            System.out.println("tadded OK: " + isAddedOk);

            return isAddedOk;

            //ErrorView.showError("Fel vid added user tyvärr", "Fel vid added user tyvärr", "Kontrollera era uppgifter", 0,new Exception(httpResponseCode + "Wrong user information." + errorText));
        }

        return false;
    }

    @Override
    public boolean updateUser(BikeUser oldUser, BikeUser alteredUser) {
        Gson gson = new Gson();
        boolean isReturnOK = false;
        String url = "http://localhost:8080/text/resources/alterUser";
            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost requsetPost = new HttpPost(url);
                requsetPost.addHeader("User-Agent123", USER_AGENT);
                String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
                int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
                MainViewInformaiton mvi = new MainViewInformaiton();
                oldUser.setSessionToken(token);
                alteredUser.setSessionToken(token);
                oldUser.setUserID(userID);
                System.out.print("Servercall ex phone : " +  oldUser.getPhone());
                alteredUser.setUserID(userID);
                mvi.setOldUser(oldUser);
                mvi.setCurrentUser(oldUser);
                mvi.setAlteredUser(alteredUser);
                mvi.setCurrentUser(oldUser);
                String json = gson.toJson(mvi);
                HttpEntity entity = new StringEntity(json);
                requsetPost.setEntity(entity);
                HttpResponse response = client.execute(requsetPost);
                System.out.println("Code " + response.getStatusLine().getStatusCode());
                String code = response.getStatusLine().getStatusCode() + "";
                if (response.getStatusLine().getStatusCode() == 200) {
                    String returnedJson = EntityUtils.toString(response.getEntity());
                    System.out.println(returnedJson);
                    Gson gson1 = new Gson();
                    isReturnOK = gson1.fromJson(returnedJson, boolean.class);
                    return isReturnOK;
                } else {
                    ResponceCodeCecker.checkCode(code);
                    closeSession();
                    Main.getSpider().getMain().showLoginView();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
            return isReturnOK;
        }

    @Override
    public ArrayList<Bike> getAvailableBikes() {
        Gson gson = new Gson();
        Bikes bikes = null;
        String url = "http://localhost:8080/text/resources/availableBikes";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(url);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            JsonObject jsonObject = new JsonObject();
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            jsonObject.addProperty("sessionToken", token);
            jsonObject.addProperty("userID", userID);
            String valuePair = jsonObject.toString();
            HttpEntity entity = new StringEntity(valuePair);
            requsetPost.setEntity(entity);
            long millisStart = Calendar.getInstance().getTimeInMillis();
            System.out.println("Start: " + millisStart);


            HttpResponse response = client.execute(requsetPost);
            long millisStop = Calendar.getInstance().getTimeInMillis();
            System.out.println("execute Tidsåtgång: " + (millisStop - millisStart) + " millisekunder");

            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("i if ");
                String json = EntityUtils.toString(response.getEntity());

                long millisStart1 = Calendar.getInstance().getTimeInMillis();
                bikes = gson.fromJson(json, Bikes.class);
                long millisStop1 = Calendar.getInstance().getTimeInMillis();
                System.out.println("Tidsåtgång fromJson: " + (millisStop1 - millisStart1) + " millisekunder");
                return bikes.getBikes();
            } else {
                try {
                   // ResponceCodeCecker.checkCode(code);
                    /*
                    Läsa in 10114 cyklar = 80050 millisekunder MISSLYCKAT
                    Läsa in 5114 cyklar = 51740 millisekunder MISSLYCKAT
                    Läsa in 4114 cyklar = 44635 millisekunder MISSLYCKAT
                    Läsa in 3114 cyklar = 38240 millisekunder MISSLYCKAT
                    Läsa in 2114 cyklar = 29717 millisekunder MISSLYCKAT
                    Läsa in 1114 cyklar = 22131 millisekunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1014 cyklar = 19851 millisekunder FUNKAR! MEN för att guit ska laddas helt tar det hela 33205
                    Läsa in 1064 cyklar = 21389 milliseckunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1054 cyklar = 20075 milliseckunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1044 cyklar = 20274 milliseckunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1034 cyklar = 19079 milliseckunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1024 cyklar = 19409 milliseckunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1019 cyklar = 18668 milliseckunder får respons 200, men error uppstår vid (String json = EntityUtils.toString(response.getEntity());
                    Läsa in 1017 cyklar = 19570 millisekunder FUNKAR! MEN för att guit ska laddas helt tar det hela 35532

                    4871 millisekunder går åt till att läsa från databasen
                    1739 millisekunder går åt till att läsa från databas med index
                    15051 millisekunder går åt till att göra om från objekt till json
                    ungefär hälften av tiden går åt till att göra om från String till ojbekt
                    */
                }catch (Exception e) {
                    e.printStackTrace();
                    closeSession();
                    Main.getSpider().getMain().showLoginView();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> getBikesFromSearch(String searchString) {
        Map<String, Integer> returnMap = null;
        Gson gson = new Gson();
        String url = "http://localhost:8080/text/resources/search";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(url);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            MainViewInformaiton mvi = new MainViewInformaiton();
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            BikeUser user = new BikeUser();
            user.setSessionToken(token);
            user.setUserID(userID);
            mvi.setCurrentUser(user);
            mvi.setSearchValue(searchString);
            String json = gson.toJson(mvi);
            HttpEntity entity = new StringEntity(json);
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                String returnedJson = EntityUtils.toString(response.getEntity());
                Gson gson1 = new Gson();
                Bikes bikes = gson1.fromJson(returnedJson, Bikes.class);
                returnMap = bikes.getSearchResults();
                return returnMap;
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
        }
        return returnMap;
    }

    @Override
    public Bike addBikeToDB(Bike newBike) {
        String urlString = "http://localhost:8080/text/resources/newBike";
        Bike returnedBike = null;
        try {
            Gson gson = new Gson();
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(urlString);
            requsetPost.addHeader("Add_bike", USER_AGENT);
            MainViewInformaiton mvi = new MainViewInformaiton();
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            BikeUser user = new BikeUser();
            user.setSessionToken(token);
            user.setUserID(userID);
            mvi.setCurrentUser(user);
            mvi.setNewBike(newBike);
            String json = gson.toJson(mvi);
            HttpEntity entity = new StringEntity(json);
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                String returnedJson = EntityUtils.toString(response.getEntity());
                Gson gson1 = new Gson();
                returnedBike = gson1.fromJson(returnedJson, Bike.class);
                return returnedBike;
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
        }
        return returnedBike;
    }

    @Override
    public String removeBikeFromDB(int bikeID) {
        String urlString = "http://localhost:8080/text/resources/removeBike";
        String mess = "Något har gått fel";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            urlString += "/" + userID + "/" + token + "/" + bikeID;
            System.out.println(urlString);
            HttpGet requsetGet = new HttpGet(urlString);
            requsetGet.addHeader("remove_bike", USER_AGENT);
            HttpResponse response = client.execute(requsetGet);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                mess = EntityUtils.toString(response.getEntity());
                return mess;
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
        }
        return mess;
    }

    @Override
    public Bike executeBikeLoan(int bikeID) {
        String url = "http://localhost:8080/text/resources/executeRental";
        Gson gson = new Gson();
        Bike bike = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(url);
            requsetPost.addHeader("BikeToRent", USER_AGENT);
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            MainViewInformaiton mvi = new MainViewInformaiton();
            BikeUser user = new BikeUser();
            user.setSessionToken(token);
            user.setUserID(userID);
            mvi.setCurrentUser(user);
            mvi.setBikeToRentID(bikeID);
            String json = gson.toJson(mvi);
            HttpEntity entity = new StringEntity(json);
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                String returnedJson = EntityUtils.toString(response.getEntity());
                System.out.println(returnedJson);
                Gson gson1 = new Gson();
                bike = gson1.fromJson(returnedJson, Bike.class);
                return bike;
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
        }
        return bike;
    }

    @Override
    public boolean returnBike(int userID, int bikeID) {
        Gson gson = new Gson();
        Bikes bikes = null;
        String url = "http://localhost:8080/text/resources/returnBike";
        boolean isReturnOkID = false;
        BikeUser user = new BikeUser();
        user.setSessionToken(Main.getSpider().getMain().getMvi().getCurrentUser().getSessionToken());
        user.setUserID(userID);
        MainViewInformaiton mvi = new MainViewInformaiton();
        mvi.setCurrentUser(user);
        mvi.setBikeToReturnID(bikeID);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(url);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            String jsonOut = gson.toJson(mvi);
            HttpEntity entity = new StringEntity(jsonOut);
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            System.out.println(code + " code i return bike ");
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonIn = EntityUtils.toString(response.getEntity());
                System.out.println(jsonIn);
                isReturnOkID = gson.fromJson(jsonIn, boolean.class);
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
            return false;
        }
        return isReturnOkID;
    }

    @Override
    public Bike getSingleBike(int bikeID) {
        Gson gson = new Gson();
        String url = "http://localhost:8080/text/resources/getBike";
        Bike bike = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(url);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            MainViewInformaiton mvi = new MainViewInformaiton();
            BikeUser user = new BikeUser();
            user.setSessionToken(token);
            user.setUserID(userID);
            mvi.setCurrentUser(user);
            mvi.setSingleBikeID(bikeID);
            String json = gson.toJson(mvi);
            HttpEntity entity = new StringEntity(json);
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                String returnedJson = EntityUtils.toString(response.getEntity());
                System.out.println(returnedJson);
                Gson gson1 = new Gson();
                bike = gson1.fromJson(returnedJson, Bike.class);
                return bike;
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
        }
        return bike;
    }

    @Override
    public void closeSession() {
        Gson gson = new Gson();
        String url = "http://localhost:8080/text/resources/closeSession";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(url);
            requsetPost.addHeader("User-Agent123", USER_AGENT);
            JsonObject jsonObject = new JsonObject();
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            if (!token.equals("-1")) {
                int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
                jsonObject.addProperty("sessionToken", token);
                jsonObject.addProperty("userID", userID);
                String valuePair = jsonObject.toString();
                HttpEntity entity = new StringEntity(valuePair);
                requsetPost.setEntity(entity);
                HttpResponse response = client.execute(requsetPost);
                System.out.println("Code " + response.getStatusLine().getStatusCode());
                String code = response.getStatusLine().getStatusCode() + "";
                if (response.getStatusLine().getStatusCode() == 200) {
                    String json = EntityUtils.toString(response.getEntity());
                    System.out.println(json);
                    BikeUser user = gson.fromJson(json, BikeUser.class);
                    Main.getSpider().getMain().getMainVI().getCurrentUser().setSessionToken(user.getSessionToken());
                } else if (code.charAt(0) == '5') {
                    ErrorView.showError("Serverfel", "Fel hos servern", "Din session kan tyfärr inte avslutas", 0, new Exception(code + "Din session kan tyvärr inte avslutas på detta sätt." +
                            "Den kommer att automatiskt avslutas inom 24 timmar om du inte under samma tid loggar in på nytt" + ""));
                    Main.getSpider().getMain().getMainVI().getCurrentUser().setSessionToken("-1");
                    Main.getSpider().getMain().showLoginView();
                } else if (code.charAt(0) == '4') {
                    ErrorView.showError("Klientens kontakt med servern felar", "Ingen endpoint funnen", "Försök igen senare", 0, new Exception(code + "Din session kan tyvärr inte avslutas på detta sätt." +
                            "Den kommer att automatiskt avslutas inom 24 timmar om du inte under samma tid loggar in på nytt" + ""));
                    Main.getSpider().getMain().getMainVI().getCurrentUser().setSessionToken("-1");
                    Main.getSpider().getMain().showLoginView();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            Main.getSpider().getMain().getMainVI().getCurrentUser().setSessionToken("-1");
            Main.getSpider().getMain().showLoginView();
        }
    }

    @Override
    public ArrayList<Bike> getAllBikes() {
        long millisStart = Calendar.getInstance().getTimeInMillis();
        String urlString = "http://localhost:8080/text/resources/getAllBikes";
        Bikes bikes = null;
        try {
            Gson gson = new Gson();
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(urlString);
            requsetPost.addHeader("getAllBikes", USER_AGENT);
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            int memberLevel = Main.getSpider().getMain().getMainVI().getCurrentUser().getMemberLevel();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("sessionToken", token);
            jsonObject.addProperty("userID", userID);
            jsonObject.addProperty("memberLevel", memberLevel);
            HttpEntity entity = new StringEntity(jsonObject.toString());
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());

            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                String returnedJson = EntityUtils.toString(response.getEntity());
                Gson gson1 = new Gson();
                bikes = gson1.fromJson(returnedJson, Bikes.class);
                long millisStop = Calendar.getInstance().getTimeInMillis();
                long sec1 = millisStop - millisStart;

                System.out.println("Tidsåtgång: " + sec1 + " millisekunder" );
                /*
                28731st = 521 millisekunder
                 */
                return bikes.getBikes();
            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
            return null;
        }

        return bikes.getBikes();
    }

    @Override
    public MainViewInformaiton fetchUpdatedInfo() {
        String urlString = "http://localhost:8080/text/resources/fetchUpdate";
        MainViewInformaiton mvi = null;
        try {
            Gson gson = new Gson();
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost requsetPost = new HttpPost(urlString);
            requsetPost.addHeader("fetch_update", USER_AGENT);
            String token = Main.getSpider().getMain().getMainVI().getCurrentUser().getSessionToken();
            int userID = Main.getSpider().getMain().getMainVI().getCurrentUser().getUserID();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("sessionToken", token);
            jsonObject.addProperty("userID", userID);
            HttpEntity entity = new StringEntity(jsonObject.toString());
            requsetPost.setEntity(entity);
            HttpResponse response = client.execute(requsetPost);
            System.out.println("Code " + response.getStatusLine().getStatusCode());
            String code = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                String returnedJson = EntityUtils.toString(response.getEntity());
                Gson gson1 = new Gson();
                mvi = gson1.fromJson(returnedJson, MainViewInformaiton.class);
                ArrayList<Bike> usersCurrentBikes = mvi.getCurrentUser().getCurrentBikeLoans();
                ArrayList<Integer> usersTotalLoan = mvi.getCurrentUser().getTotalBikeLoans();
                System.out.println("userTotalLoan serverImpl " + usersTotalLoan);
                int total = mvi.getTotalBikes();
                int available = mvi.getAvailableBikes();

                Main.getSpider().getMain().getMvi().getCurrentUser().setCurrentBikeLoans(usersCurrentBikes);
                Main.getSpider().getMain().getMvi().getCurrentUser().setTotalBikeLoans(usersTotalLoan);
                Main.getSpider().getMain().getMvi().setTotalBikes(total);
                Main.getSpider().getMain().getMvi().setAvailableBikes(available);

            } else {
                ResponceCodeCecker.checkCode(code);
                closeSession();
                Main.getSpider().getMain().showLoginView();
            }

        } catch (Exception e) {
            ErrorView.showError("Serverfel", "Fel hos servern", "Försök igen senare", 0, new Exception(500 + "Fel hos server." + ""));
            closeSession();
            Main.getSpider().getMain().showLoginView();
            e.printStackTrace();
        }
        return mvi;
    }
}

