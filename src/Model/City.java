/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import VC.Main;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author G
 */
public class City extends Stamp {
   public City(Integer cityID, String city, Integer countryID,
           ZonedDateTime createDate, String createdBy, 
           ZonedDateTime lastUpdate, String lastUpdateBy) {
      setCity(city);
      setCityID(cityID);
      setCountryID(countryID);
      setCreateDate(createDate);
      setCreatedBy(createdBy);
      setLastUpdate(lastUpdate);
      setLastUpdateBy(lastUpdateBy);
   }
   
   private final IntegerProperty cityID = new SimpleIntegerProperty();
   private final StringProperty city = new SimpleStringProperty();
   private final IntegerProperty countryID = new SimpleIntegerProperty();
   
   public static ObservableList<String> getList(Integer country) {
      ObservableList<String> citis = FXCollections.observableArrayList();
      for(City c : Main.cities) {
         if(Objects.equals(c.getCountryID(), country)) {
            citis.add(c.getCity());
         }
      }
      return citis;
   }
   
   public static Map<String, Integer> getMap(Integer country) {
      Map<String, Integer> citis = new HashMap<>();
      for(City c : Main.cities) {
         if(Objects.equals(c.getCountryID(), country)) {
            citis.put(c.getCity(), c.getID());
         }
      }
      return citis;
   }
   
   public static Map<String, Integer> getFullMap() {
      Map<String, Integer> citis = new HashMap<>();
      for(City c : Main.cities) {
         citis.put(c.getCity(), c.getID());
      }
      return citis;
   }
   
   public static City getCityObj(Integer id) {
      for(City cty : VC.Main.cities) {
         if(cty.getID() == id) return cty;
      }
      return null;
   }   
   
   public IntegerProperty cityIDProperty() {
      return cityID;
   }
   public final void setCityID(Integer cityID) {
      this.cityIDProperty().set(cityID);
   }
   public Integer getID() {
      return cityIDProperty().get();
   }
   
   public StringProperty cityProperty() {
      return city;
   }
   public final void setCity(String city) {
      this.cityProperty().set(city);
   }
   public String getCity() {
      return cityProperty().get();
   }
   
   public IntegerProperty countryIDProperty() {
      return countryID;
   }
   public final void setCountryID(Integer countryID) {
      this.countryIDProperty().set(countryID);
   }
   public Integer getCountryID() {
      return countryIDProperty().get();
   }
}
