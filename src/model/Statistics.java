package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Niklas och Goloconda on 2017-01-18.
 *
 * Att göra: lägg till kön och födelseår
 * schemalägg event som sparar ner en månadsraport i databasen
 */
public class Statistics {
  private LocalDateTime dateTime;
  private String generatedBy;

  //BikeStatistics
  private int numberOfTotalBikes;
  private int availableBikesPercent;
  private int numberOfUsableBikesPercent; //3, 4 eller 5
  private int numberOfMaintenancePercent; //2
  private int numberOfDisabledBikesPercent; //1

  //Användarstatistik
  private int numberOfUsers;
  private int numberOfMenPercent;
  private int numberOfWomenPercent;
  private int numberOfOthersPercent;
  private int numberMen20to30Percent;
  private int numberMen30to40Percent;
  private int numberMen40to50Percent;
  private int numberMen5to130Percent;
  private int numberWomen20to30Percent;
  private int numberWomen30to40Percent;
  private int numberWomen40to50Percent;
  private int numberWomen50to130Percent;

  //Aktivitetsstatistk
  private int numberNewLoans30DaysBackTotal;
  private int numberNewLoans30DaysBackMenPercent;
  private int numberNewLoans30DaysBackWomenPercent;
  private int numberNewLoans30DaysBackOtherPercent;
  private BikeUser mosteActiveUserGroup; //Ev ett enum
  private BikeUser leastActiveUserGroup;

  //Cykelaktivitetsstatistk
  private ArrayList<Bike> mostPopularBikes; //utan id, men med bild
  private ArrayList<Bike> leastPopulareBIkes;


  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getGeneratedBy() {
    return generatedBy;
  }

  public void setGeneratedBy(String generatedBy) {
    this.generatedBy = generatedBy;
  }

  public int getNumberOfTotalBikes() {
    return numberOfTotalBikes;
  }

  public void setNumberOfTotalBikes(int numberOfTotalBikes) {
    this.numberOfTotalBikes = numberOfTotalBikes;
  }

  public int getAvailableBikesPercent() {
    return availableBikesPercent;
  }

  public void setAvailableBikesPercent(int availableBikesPercent) {
    this.availableBikesPercent = availableBikesPercent;
  }

  public int getNumberOfUsableBikesPercent() {
    return numberOfUsableBikesPercent;
  }

  public void setNumberOfUsableBikesPercent(int numberOfUsableBikesPercent) {
    this.numberOfUsableBikesPercent = numberOfUsableBikesPercent;
  }

  public int getNumberOfMaintenancePercent() {
    return numberOfMaintenancePercent;
  }

  public void setNumberOfMaintenancePercent(int numberOfMaintenancePercent) {
    this.numberOfMaintenancePercent = numberOfMaintenancePercent;
  }

  public int getNumberOfDisabledBikesPercent() {
    return numberOfDisabledBikesPercent;
  }

  public void setNumberOfDisabledBikesPercent(int numberOfDisabledBikesPercent) {
    this.numberOfDisabledBikesPercent = numberOfDisabledBikesPercent;
  }

  public int getNumberOfUsers() {
    return numberOfUsers;
  }

  public void setNumberOfUsers(int numberOfUsers) {
    this.numberOfUsers = numberOfUsers;
  }

  public int getNumberOfMenPercent() {
    return numberOfMenPercent;
  }

  public void setNumberOfMenPercent(int numberOfMenPercent) {
    this.numberOfMenPercent = numberOfMenPercent;
  }

  public int getNumberOfWomenPercent() {
    return numberOfWomenPercent;
  }

  public void setNumberOfWomenPercent(int numberOfWomenPercent) {
    this.numberOfWomenPercent = numberOfWomenPercent;
  }

  public int getNumberOfOthersPercent() {
    return numberOfOthersPercent;
  }

  public void setNumberOfOthersPercent(int numberOfOthersPercent) {
    this.numberOfOthersPercent = numberOfOthersPercent;
  }

  public int getNumberMen20to30Percent() {
    return numberMen20to30Percent;
  }

  public void setNumberMen20to30Percent(int numberMen20to30Percent) {
    this.numberMen20to30Percent = numberMen20to30Percent;
  }

  public int getNumberMen30to40Percent() {
    return numberMen30to40Percent;
  }

  public void setNumberMen30to40Percent(int numberMen30to40Percent) {
    this.numberMen30to40Percent = numberMen30to40Percent;
  }

  public int getNumberMen40to50Percent() {
    return numberMen40to50Percent;
  }

  public void setNumberMen40to50Percent(int numberMen40to50Percent) {
    this.numberMen40to50Percent = numberMen40to50Percent;
  }

  public int getNumberMen5to130Percent() {
    return numberMen5to130Percent;
  }

  public void setNumberMen5to130Percent(int numberMen5to130Percent) {
    this.numberMen5to130Percent = numberMen5to130Percent;
  }

  public int getNumberWomen20to30Percent() {
    return numberWomen20to30Percent;
  }

  public void setNumberWomen20to30Percent(int numberWomen20to30Percent) {
    this.numberWomen20to30Percent = numberWomen20to30Percent;
  }

  public int getNumberWomen30to40Percent() {
    return numberWomen30to40Percent;
  }

  public void setNumberWomen30to40Percent(int numberWomen30to40Percent) {
    this.numberWomen30to40Percent = numberWomen30to40Percent;
  }

  public int getNumberWomen40to50Percent() {
    return numberWomen40to50Percent;
  }

  public void setNumberWomen40to50Percent(int numberWomen40to50Percent) {
    this.numberWomen40to50Percent = numberWomen40to50Percent;
  }

  public int getNumberWomen50to130Percent() {
    return numberWomen50to130Percent;
  }

  public void setNumberWomen50to130Percent(int numberWomen50to130Percent) {
    this.numberWomen50to130Percent = numberWomen50to130Percent;
  }

  public int getNumberNewLoans30DaysBackTotal() {
    return numberNewLoans30DaysBackTotal;
  }

  public void setNumberNewLoans30DaysBackTotal(int numberNewLoans30DaysBackTotal) {
    this.numberNewLoans30DaysBackTotal = numberNewLoans30DaysBackTotal;
  }

  public int getNumberNewLoans30DaysBackMenPercent() {
    return numberNewLoans30DaysBackMenPercent;
  }

  public void setNumberNewLoans30DaysBackMenPercent(int numberNewLoans30DaysBackMenPercent) {
    this.numberNewLoans30DaysBackMenPercent = numberNewLoans30DaysBackMenPercent;
  }

  public int getNumberNewLoans30DaysBackWomenPercent() {
    return numberNewLoans30DaysBackWomenPercent;
  }

  public void setNumberNewLoans30DaysBackWomenPercent(int numberNewLoans30DaysBackWomenPercent) {
    this.numberNewLoans30DaysBackWomenPercent = numberNewLoans30DaysBackWomenPercent;
  }

  public int getNumberNewLoans30DaysBackOtherPercent() {
    return numberNewLoans30DaysBackOtherPercent;
  }

  public void setNumberNewLoans30DaysBackOtherPercent(int numberNewLoans30DaysBackOtherPercent) {
    this.numberNewLoans30DaysBackOtherPercent = numberNewLoans30DaysBackOtherPercent;
  }

  public BikeUser getMosteActiveUserGroup() {
    return mosteActiveUserGroup;
  }

  public void setMosteActiveUserGroup(BikeUser mosteActiveUserGroup) {
    this.mosteActiveUserGroup = mosteActiveUserGroup;
  }

  public BikeUser getLeastActiveUserGroup() {
    return leastActiveUserGroup;
  }

  public void setLeastActiveUserGroup(BikeUser leastActiveUserGroup) {
    this.leastActiveUserGroup = leastActiveUserGroup;
  }

  public ArrayList<Bike> getMostPopularBikes() {
    return mostPopularBikes;
  }

  public void setMostPopularBikes(ArrayList<Bike> mostPopularBikes) {
    this.mostPopularBikes = mostPopularBikes;
  }

  public ArrayList<Bike> getLeastPopulareBIkes() {
    return leastPopulareBIkes;
  }

  public void setLeastPopulareBIkes(ArrayList<Bike> leastPopulareBIkes) {
    this.leastPopulareBIkes = leastPopulareBIkes;
  }
}
