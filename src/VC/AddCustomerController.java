/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import CRUD.Query;
import Model.Country;
import Model.Customer;
import static VC.Main.customers;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
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

public class AddCustomerController implements Initializable {
   ObservableList<String> countryList = FXCollections.observableArrayList();
   ObservableList<String> cityList = FXCollections.observableArrayList();
   Map<String, Integer> allCities = new HashMap<>();
   Map<String, Integer> allCountries = new HashMap<>();
   Map cityMap, countryMap;
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
      allCities.clear();
      allCountries.clear();
      allCities = Model.City.getFullMap();
      allCountries = Model.Country.getFullMap();
      String country = countryField.getText();
      String userName = Model.User.getUserObj(VC.Login.currentUserID).getUserName();
      
      ZonedDateTime now = ZonedDateTime.now();
      LocalDateTime ldtNow = now.toLocalDateTime();
      Timestamp ts = Timestamp.valueOf(ldtNow);

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
      return nameField.getText().trim().isEmpty()
              || (phoneField.getText().trim().isEmpty()) 
              || (addressField.getText().trim().isEmpty())
              || (address2Field.getText().trim().isEmpty())
              || (zipcodeField.getText().trim().isEmpty())
              || (cityCombo.getValue() == null)
              || (countryCombo.getValue() == null);
   }
   
   public Boolean checkZipPhone() {
      return !(zipcodeField.getText().matches("[0-9]+")
              || phoneField.getText().matches("[0-9]+"));
   }

   @FXML void saveCustomer(ActionEvent event) throws SQLException, IOException {
      if(nullFields()) {
         errorLabel.setText("Please fill all fields");
         return;
      } else if(checkZipPhone()) {
         errorLabel.setText("Can't save. Non numeric data in\nZipcode or Phone field.");
         return;
      }
      
      String userName = Model.User.getUserObj(VC.Login.currentUserID).getUserName();
      
      allCities = Model.City.getFullMap();
      Integer cityID = (Integer) allCities.get(cityCombo.getValue().toString());
      
      ZonedDateTime now = ZonedDateTime.now();
      LocalDateTime ldtNow = now.toLocalDateTime();
      Timestamp ts = Timestamp.valueOf(ldtNow);

      Query.makeQuery("INSERT INTO address (address, address2, "
              + "cityId, postalCode, phone, "
              + "createDate, createdBy, lastUpdate, lastUpdateBy) "
              + "VALUES ('" + addressField.getText() 
              + "', '" + address2Field.getText()
              + "', '" + cityID + "', '" + zipcodeField.getText() + "', '" 
              + phoneField.getText()
              + "', '" + ts + "', '" + userName 
              + "', '" + ts + "', '" + userName + "');");
      
      Main.fillAddresses();
      
      Query.makeQuery("SELECT MAX(addressId) FROM address;");
      ResultSet result = Query.getResult();
      result.next();
      Integer addID = result.getInt("MAX(addressId)");
      
      Query.makeQuery("INSERT INTO customer (customerName, addressId, active, "
              + "createDate, createdBy, lastUpdate, lastUpdateBy) " 
              + "VALUES ('" + nameField.getText() + "', '" + addID + "', '1', '"
              + ts + "', '" + userName + "', '" 
              + ts + "', '" + userName + "');");
   
      Main.fillCustomers();
      
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

   @FXML void exitCustomer(ActionEvent event) { 

      try {
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
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }
   
   private ObservableList<String> makeList(Map map) {
      ObservableList<String> list = FXCollections.observableArrayList();
      map.forEach((k, v) -> {
         list.add(k.toString());
      });
      return list;
   }
   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      setCountryCombo();
      // TODO move into setCountryCombo() and test
      countryCombo.getSelectionModel().selectedItemProperty().addListener(a -> {
         String chose = (String) countryCombo.getSelectionModel().getSelectedItem();
         countryMap = Country.getMap();
         cityCombo.valueProperty().set(null);
         cityCombo.setItems(Model.City.getList((Integer)countryMap.get(chose)));
      });
      allCountries = Model.Country.getFullMap();
      
   }
   
   private void setCountryCombo() {
      
      countryMap = Model.Country.getMap();
      countryCombo.setItems(makeList(countryMap));
   }
}
