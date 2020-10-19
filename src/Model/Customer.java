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
public class Customer extends Stamp {
   public Customer(Integer id, String customerName,
           Integer addressID, Integer active,
           ZonedDateTime createDate, String createdBy, 
           ZonedDateTime lastUpdate, String lastUpdateBy) {
      
      setCustomerID(id);
      setCustomerName(customerName);
      setAddressID(addressID);
      setActive(active);
      setCreateDate(createDate);
      setCreatedBy(createdBy);
      setLastUpdate(lastUpdate);
      setLastUpdateBy(lastUpdateBy);
   }
   
   private IntegerProperty customerID = new SimpleIntegerProperty();
   private StringProperty customerName = new SimpleStringProperty();
   private IntegerProperty addressID = new SimpleIntegerProperty();
   private IntegerProperty active = new SimpleIntegerProperty();
   
   public static Map<String, Integer> getFullMap() {
      Map<String, Integer> custs = new HashMap<>();
      for(Customer c : Main.customers) {
         custs.put(c.getCustomerName(), c.getID());
      }
      return custs;
   }
   // Go through each customer and add their name to the list if they are from country id
   public static ObservableList<String> getList(Integer id) {
      ObservableList<String> cst = FXCollections.observableArrayList();
      
      for(Model.Customer a : Main.customers) {
         
         Integer countryID = a.getCountry().getID();
         if (countryID.equals(id)) {
            cst.add(a.getCustomerName());
         }
      }
      return cst;
   }
   
   public Address getAddress() {
      return Address.getAddressObj(this.getAddressID());
   }
   
   public City getCity() {
      return City.getCityObj(getAddress().getCityID());
   }
   
   public Country getCountry() {
      return Country.getCountryObj(getCity().getCountryID());
   }
   
   @Override
   public String toString() {
      return getCustomerName();
   }

   public static Customer getCustomerObj(Integer id) {
      for(Customer cust : VC.Main.customers) {
         if(cust.getID().equals(id)) {
            return cust;
         }
      }
      return null;
   }
   
   public IntegerProperty customerIDProperty() {
      return customerID;
   }
   public void setCustomerID(Integer customerID) {
      customerIDProperty().set(customerID);
   }
   public Integer getID() {
      return customerIDProperty().get();
   }
   
   public StringProperty customerNameProperty() {
      return customerName;
   }
   public void setCustomerName(String customerName) {
      customerNameProperty().set(customerName);
   }
   public String getCustomerName() {
      return customerNameProperty().get();
   }
   
   public IntegerProperty addressIDProperty() {
      return addressID;
   }
   public void setAddressID(Integer addressID) {
      addressIDProperty().set(addressID);
   }
   public Integer getAddressID() {
      return addressIDProperty().get();
   }
   
   public IntegerProperty activeProperty() {
      return active;
   }
   public void setActive(Integer active) {
      activeProperty().set(active);
   }
   public Integer getActive() {
      return activeProperty().get();
   }
}

