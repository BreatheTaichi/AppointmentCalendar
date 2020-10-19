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
public class Country extends Stamp {
   public Country(Integer id, String country,
           ZonedDateTime createDate, String createdBy, 
           ZonedDateTime lastUpdate, String lastUpdateBy) {
      
      setCountryID(id);
      setCountry(country);
      setCreateDate(createDate);
      setCreatedBy(createdBy);
      setLastUpdate(lastUpdate);
      setLastUpdateBy(lastUpdateBy);
   }
   
   public IntegerProperty countryID = new SimpleIntegerProperty();
   public StringProperty country = new SimpleStringProperty();
   
   @Override public String toString() {
      return getCountry();
   }
   
   public static ObservableList getList() {
      ObservableList<String> countries = FXCollections.observableArrayList();
      for(Country c : VC.Main.countries) {
         countries.add(c.getCountry());
      }
      return countries;
   }
   
   public static Map<String, Integer> getMap() {
      Map<String, Integer> countries = new HashMap<>();
      for(Country c : Main.countries) {
         countries.put(c.getCountry(), c.getID());
      }
      return countries;
   }
   
   public static Map<String, Integer> getFullMap() {
      Map<String, Integer> countries = new HashMap<>();
      for(Country c : Main.countries) {
         countries.put(c.getCountry(), c.getID());
      }
      return countries;
   }
   
   public static Country getCountryFromString(String c) {
      for(Country cntry : VC.Main.countries) {
         if(cntry.getCountry().equals(cntry)) {
            return cntry;
         }
      }
      return null;
   }
   
   public static Country getCountryObj(Integer id) {
      for(Country cntry : VC.Main.countries) {
         if(Objects.equals(cntry.getID(), id)) return cntry;
      }
      return null;
   }
   
   public IntegerProperty countryIDProperty() {
      return countryID;
   }
   public Integer getID() {
      return countryIDProperty().get();
   }
   public final void setCountryID(Integer countryID) {
      this.countryIDProperty().set(countryID);
   }
   
   public StringProperty countryProperty() {
      return country;
   }
   public String getCountry() {
      return countryProperty().get();
   }
   public final void setCountry(String country) {
      this.countryProperty().set(country);
   }
}