package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class Bike{
  private int bikeID;
  private String brandName;
  private int modelYear;
  private String color;
  private String imagePath;
  private int size;
  private String type;
  private boolean available;
  private int createdByUserID;
  private String imageFileName;
  private LocalDate dayOfReturn;
  private LocalDate dayOfRent;
    private byte[] arrayImage;

 private ByteArrayInputStream imageStream;




  public Bike() {
  }

  public Bike(String brandName, int modelYear, String color, int size, String type) {
    this.brandName = brandName;
    this.modelYear = modelYear;
    this.color = color;
    this.size = size;
    this.type = type;
  }

  public Bike(String brandName, int modelYear, String color, int size, String type, int bikeID, String imagePath) {

    this.brandName = brandName;
    this.modelYear = modelYear;
    this.color = color;
    this.size = size;
    this.type = type;
    this.bikeID = bikeID;
    this.imagePath = imagePath;

  }

  public int getBikeID() {
    return bikeID;
  }

  public void setBikeID(int bikeID) {
    this.bikeID = bikeID;
  }

  public int getModelYear() {
    return modelYear;
  }

  public void setModelYear(int modelYear) {
    this.modelYear = modelYear;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getImageFileName() {
    return imageFileName;
  }

  public void setImageFileName(String imageFileName) {
    this.imageFileName = imageFileName;
  }

  public LocalDate getDayOfReturn() {
    return dayOfReturn;
  }

  public void setDayOfReturn(LocalDate dayOfReturn) {
    this.dayOfReturn = dayOfReturn;
  }

  public LocalDate getDayOfRent() {
    return dayOfRent;
  }

  public void setDayOfRent(LocalDate dayOfRent) {
    this.dayOfRent = dayOfRent;
  }

  public int getCreatedByUserID() {
    return createdByUserID;
  }

  public void setCreatedByUserID(int createdByUserID) {
    this.createdByUserID = createdByUserID;
  }

  public ByteArrayInputStream getImageStream() {
      imageStream.reset();
    return imageStream;
  }

  public void setImageStream(ByteArrayInputStream imageStream) {
    this.imageStream = imageStream;
  }

  @Override
  public String toString() {
    return "Bike Object: \n**********" + "\n" +
            getBrandName() + "\n" +
            //getImagePath() +"\n" +
            getType() + "\n" +
            getColor() + "\n" +
            getModelYear() + "\n" +
            getSize() + "\n ************";
  }

}
