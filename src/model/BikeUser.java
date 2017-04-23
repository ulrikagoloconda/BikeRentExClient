package model;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

/**
 * @author Ulrika Goloconda Fahlén
 * @version 1.0
 * @since 2016-09-16
 */
public class BikeUser {
    private int userID;
    private String userName;
    private String fName;
    private String lName;
    private String gender;
    private Year birthYear;
    private int memberLevel;
    private String email;
    private int phone;
    private LocalDate memberSince;
    private String passw;
    private String sessionToken;
    private ArrayList<Bike> currentBikeLoans;
    private ArrayList<Integer> totalBikeLoans;
    private PrestandaMeasurement mesaurment;

    public BikeUser() {
        this.sessionToken= "-1";
    }

    public BikeUser(String userName, String fName, String lName, String gender, Year birthYear, int memberLevel, String email, int phone, LocalDate memberSince) {
        this.userName = userName;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.memberLevel = memberLevel;
        this.email = email;
        this.phone = phone;
        this.memberSince = memberSince;
        this.sessionToken= "-1";
    }
    public BikeUser(String fName, String lName, String gender, Year birthYear, int in_memberlevel, String email, int phone, String userName, String password){
    this.fName = fName;
    this.lName = lName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.memberLevel = in_memberlevel;
    this.email = email;
    this.phone = phone;
    this.userName = userName;
    this.passw = password;
        this.sessionToken= "-1";
  }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public LocalDate getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(LocalDate memberSince) {
        this.memberSince = memberSince;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public ArrayList<Bike> getCurrentBikeLoans() {
        return currentBikeLoans;
    }

    public void setCurrentBikeLoans(ArrayList<Bike> currentBikeLoans) {
        this.currentBikeLoans = currentBikeLoans;
    }

    public ArrayList<Integer> getTotalBikeLoans() {
        return totalBikeLoans;
    }

    public void setTotalBikeLoans(ArrayList<Integer> totalBikeLoans) {
        this.totalBikeLoans = totalBikeLoans;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Year getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Year birthYear) {
        this.birthYear = birthYear;
    }

    public PrestandaMeasurement getMesaurment() {
        return mesaurment;
    }

    public void setMesaurment(PrestandaMeasurement mesaurment) {
        this.mesaurment = mesaurment;
    }
}

