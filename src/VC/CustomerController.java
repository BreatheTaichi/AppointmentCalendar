/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import CRUD.Query;
import Model.City;
import Model.Country;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author G
 */

public class CustomerController implements Initializable {
   ObservableList<String> countryList = FXCollections.observableArrayList();
   ObservableList<String> cityList = FXCollections.observableArrayList();
   Map<String, Integer> allCities = new HashMap<>();
   Map<String, Integer> allCountries = new HashMap<>();
   Map cityMap, countryMap, custMap;
   Customer current;

   @FXML private TextField addressField;
   @FXML private TextField address2Field;
   @FXML private TextField zipcodeField;
   @FXML private TextField phoneField;
   @FXML private TextField nameField;
   @FXML private TextField cityField;
   @FXML private TextField countryField;
   @FXML private Label errorLabel;
   @FXML private Button exitButton;
   @FXML private ComboBox cityCombo;
   @FXML private ComboBox countryCombo;
   @FXML private ComboBox customerCombo;

   @FXML void addCityButton(ActionEvent event) throws SQLException {
      if(cityField.getText().trim().isEmpty()) {
         errorLabel.setText("Can't add empty City.");
         return;
      }
      if(countryCombo.getSelectionModel().getSelectedItem() == null) {
         errorLabel.setText("Please choose a Country first.");
         return;
      }
      
      allCities.clear();
      allCountries.clear();
      countryMap = Model.Country.getMap();
         
      Integer countryID = (Integer) countryMap.get(countryCombo.getValue());
      
      String cityName = cityField.getText();
      String userName = Model.User.getUserObj(VC.Login.currentUserID).getUserName();

      ZonedDateTime now = ZonedDateTime.now();
      LocalDateTime ldtNow = now.toLocalDateTime();
      Timestamp ts = Timestamp.valueOf(ldtNow);
      System.out.println(ts);
      
      Query.makeQuery("INSERT INTO city (city, countryId, createDate, "
              + "createdBy, lastUpdate, lastUpdateBy) "
              + "VALUES ('" + cityName + "', '" + countryID + "', '" + ts
              + "', '" + userName + "', '" + ts
              + "', '" + userName + "');");
      
      Main.fillCities();
      cityMap = Model.City.getMap(countryID);

      // Put the current country and city into the combobox so they don't reset
      cityCombo.setValue(cityName);
      countryCombo.setValue(Country.getCountryObj(countryID).getCountry());
   }

   @FXML void addCountryButton(ActionEvent event) throws SQLException {
      if(countryField.getText().trim().isEmpty()) {
         errorLabel.setText("Can't add empty Country.");
         return;
      }
      allCities = new HashMap<String, Integer>();
      allCountries = new HashMap<String, Integer>();
      allCities = Model.City.getFullMap();
      allCountries = Model.Country.getFullMap();
      String country = countryField.getText();
      String userName = Model.User.getUserObj(VC.Login.currentUserID).getUserName();
      
      ZonedDateTime now = ZonedDateTime.now();
      LocalDateTime ldtNow = now.toLocalDateTime();
      Timestamp ts = Timestamp.valueOf(ldtNow);
      System.out.println(ts);

      Query.makeQuery("INSERT INTO country (country, createDate, "
              + "createdBy, lastUpdate, lastUpdateBy) "
              + "VALUES ('" + country + "', '" + ts
              + "', '" + userName + "', '" + ts
              + "', '" + userName + "');");
      
      Main.fillCountries();
      setCountryCombo();
      countryCombo.setValue(country);
      allCities = Model.City.getFullMap();
      allCountries = Model.Country.getFullMap();
   }
   
   public Boolean nullFields() {
      if(nameField.getText().trim().isEmpty()
              || (phoneField.getText().trim().isEmpty()) 
              || (addressField.getText().trim().isEmpty())
              || (address2Field.getText().trim().isEmpty())
              || (zipcodeField.getText().trim().isEmpty())
              || (cityCombo.getSelectionModel().isEmpty())
              || (countryCombo.getSelectionModel().isEmpty())) {
         return true;
      }
      return false;
   }
   
   public Boolean checkZipPhone(String zField, String pField) {
      return !(zField.matches("[0-9]+")
              && pField.matches("[0-9]+"));
   }

   @FXML void saveCustomer(ActionEvent event) throws SQLException, IOException {
      String zField = zipcodeField.getText();
      String pField = phoneField.getText();
      if(nullFields()) {
         errorLabel.setText("Please fill all fields");
         return;
      } else if(checkZipPhone(zField, pField)) {
         errorLabel.setText("Can't save. Non numeric data in Zipcode or Phone field.");
         return;
      }
      
      String userName = Model.User.getUserObj(VC.Login.currentUserID).getUserName();
      
      allCities = Model.City.getFullMap();
      Integer cityID = allCities.get(cityCombo.getValue().toString());
      
      ZonedDateTime now = ZonedDateTime.now();
      LocalDateTime ldtNow = now.toLocalDateTime();
      Timestamp ts = Timestamp.valueOf(ldtNow);

      Query.makeQuery("UPDATE address SET address = '" + addressField.getText() 
               + "', address2 = '" + address2Field.getText() + "', cityId = '" 
               + cityID + "', postalCode = '" + zipcodeField.getText() 
               + "', phone = '" + phoneField.getText() + "', lastUpdate = " 
               + "CURRENT_TIMESTAMP, lastUpdateBy = '" + userName + "' WHERE addressId = " 
               + VC.AppointmentsController.activeCustomer.getAddressID() + ";");
      
      Main.fillAddresses();
      
      Query.makeQuery("SELECT MAX(addressId) FROM address;");
      ResultSet result = Query.getResult();
      result.next();
      Integer addID = result.getInt("MAX(addressId)");
      
      Query.makeQuery("UPDATE customer SET customerName = '" + nameField.getText()
               + "', addressId = '" + addID + "', active = 1, lastUpdate = " 
               + "CURRENT_TIMESTAMP, lastUpdateBy = '" + userName + "' WHERE addressId = " 
               + VC.AppointmentsController.activeCustomer.getAddressID() + ";");
      
      Main.fillCustomers();
      
      // Exit and load appointmenst screen
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/Appointments.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Customer Information");
      Scene scene = new Scene(ap);

      scene.getStylesheets().add("Model/calendar.css");

      cStage.hide();
      cStage.setScene(scene);

      Stage stage = (Stage) errorLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }

   @FXML void deleteCustomer(ActionEvent event) throws SQLException, IOException {
      
      Integer id = AppointmentsController.activeCustomer.getID();

      Main.appointments.stream().filter((ap) -> 
              (Objects.equals(ap.getCustomerID(), id))).forEachOrdered((ap) -> {
         CRUD.Query.makeQuery("DELETE FROM appointment WHERE customerId = " + id);
      });
      
      CRUD.Query.makeQuery("DELETE FROM customer WHERE customerId = " + id);
      
      Main.fillCustomers();
      Main.fillAppointments();
      
      AppointmentsController.activeCustomer = null;
      AppointmentsController.activeAppt = null;
      
      // Exit and load appointmenst screen
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/Appointments.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Customer Information");
      Scene scene = new Scene(ap);

      scene.getStylesheets().add("Model/calendar.css");

      cStage.hide();
      cStage.setScene(scene);

      Stage stage = (Stage) errorLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }

   @FXML void exitCustomer(ActionEvent event) throws IOException { 

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/Appointments.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Customer Information");
      Scene scene = new Scene(ap);
      scene.getStylesheets().add("Model/calendar.css");

      cStage.hide();
      cStage.setScene(scene);

      Stage stage = (Stage) errorLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }
   
   private Map makeMap(List list) {
      Map<String, Integer> map = new HashMap<>();
      for(Integer i = 0; i < list.size(); i++) {
         map.put(list.get(i).toString(), i);
      }
      return map;
   }
   private ObservableList<String> makeList(Map map) {
      ObservableList<String> list = FXCollections.observableArrayList();
      map.forEach((k, v) -> {
         list.add(k.toString());
      });
      return list;
   }
   
   public void populateProduct() {
      
   }
   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      
      custMap = Model.Customer.getFullMap();
      customerCombo.setItems(makeList(custMap));
      
      if (AppointmentsController.activeCustomer != null) {
         setFields(AppointmentsController.activeCustomer);
      }
      
      countryCombo.getSelectionModel().selectedItemProperty().addListener(a -> {
         String chose = (String) countryCombo.getSelectionModel().getSelectedItem();
         countryMap = Country.getMap();
         cityCombo.setItems(Model.City.getList((Integer)countryMap.get(chose)));
      });
      
      customerCombo.getSelectionModel().selectedItemProperty().addListener(a -> {
         String chose = (String) customerCombo.getSelectionModel().getSelectedItem();
         setFields(Model.Customer.getCustomerObj((Integer)custMap.get(chose)));
         AppointmentsController.activeCustomer = 
                 (Model.Customer.getCustomerObj((Integer)custMap.get(chose)));
      });
      
      allCountries = Model.Country.getFullMap();
   }   
   
   private void setFields(Customer c) {
      
      Model.City city = c.getCity();
      Model.Country country = c.getCountry();
      Model.Address address = c.getAddress();
      
      // If cityCombo is empty fill it and all fields, otherwise skip filling fields
      if(!cityCombo.getSelectionModel().isEmpty()) {
         cityMap = City.getFullMap();
         String cityString = (String) cityCombo.getSelectionModel().getSelectedItem();
         city = City.getCityObj((Integer) cityMap.get(cityString));
         country = Country.getCountryObj(city.getCountryID());
      } else {
      
      nameField.setText(c.getCustomerName());
      phoneField.setText(address.getPhone());
      addressField.setText(address.getAddress());
      address2Field.setText(address.getAddress2());
      zipcodeField.setText(address.getPostalCode());
      }
      cityMap = Model.City.getMap(country.getID());
      cityCombo.setItems(makeList(cityMap));
      cityCombo.setValue(city.getCity());

      countryMap = Model.Country.getMap();
      countryCombo.setItems(makeList(countryMap));
      countryCombo.setValue(country.getCountry());
      
      
      errorLabel.setText("");
      countryField.setText("");
      cityField.setText("");
   } 
   
   private void setCountryCombo() {
      
      countryMap = Model.Country.getMap();
      countryCombo.setItems(makeList(countryMap));
   }
   
}
