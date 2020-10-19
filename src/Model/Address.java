/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.ZonedDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import VC.AppointmentsController;
import VC.Main;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author G
 */
public class Address extends Stamp {
   public Address(Integer addressID, String address,
           String address2, Integer cityID,
           String postalCode, String phone,
           ZonedDateTime createDate, String createdBy, 
           ZonedDateTime lastUpdate, String lastUpdateBy) {

      setAddressID(addressID);
      setAddress(address);
      setAddress2(address2);
      setCityID(cityID);
      setPostalCode(postalCode);
      setPhone(phone);
      setCreateDate(createDate);
      setCreatedBy(createdBy);
      setLastUpdate(lastUpdate);
      setLastUpdateBy(lastUpdateBy);
   }
   
   private IntegerProperty addressID = new SimpleIntegerProperty();
   private StringProperty address = new SimpleStringProperty();
   private StringProperty address2 = new SimpleStringProperty();
   private IntegerProperty cityID = new SimpleIntegerProperty();
   private StringProperty postalCode = new SimpleStringProperty();
   private StringProperty phone = new SimpleStringProperty();
   
   public static Map<String, Integer> getFullMap() {
      Map<String, Integer> adds = new HashMap<>();
      for(Address c : Main.addresses) {
         adds.put(c.getAddress(), c.getID());
      }
      return adds;
   }
   
   public static Address getAddressObj(Integer id) {
      for(Address addr : VC.Main.addresses) {
         if(addr.getID() == id) return addr;
      }
      return null;
   }
   
   public IntegerProperty addressIDProperty() {
      return addressID;
   }
   public void setAddressID(Integer addressID) {
      addressIDProperty().set(addressID);
   }
   public Integer getID() {
      return addressIDProperty().get();
   }
   
   public StringProperty addressProperty() {
      return address;
   }
   public void setAddress(String address) {
      addressProperty().set(address);
   }
   public String getAddress() {
      return addressProperty().get();
   }
   
   public StringProperty address2Property() {
      return address2;
   }
   public void setAddress2(String address2) {
      address2Property().set(address2);
   }
   public String getAddress2() {
      return address2Property().get();
   }
   
   public IntegerProperty cityIDProperty() {
      return cityID;
   }
   public void setCityID(Integer cityID) {
      cityIDProperty().set(cityID);
   }
   public Integer getCityID() {
      return cityIDProperty().get();
   }
   
   public StringProperty postalCodeProperty() {
      return postalCode;
   }
   public void setPostalCode(String postalCode) {
      postalCodeProperty().set(postalCode);
   }
   public String getPostalCode() {
      return postalCodeProperty().get();
   }
   
   public StringProperty phoneProperty() {
      return phone;
   }
   public void setPhone(String phone) {
      phoneProperty().set(phone);
   }
   public String getPhone() {
      return phoneProperty().get();
   }
}
