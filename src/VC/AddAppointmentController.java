/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author G
 */
public class AddAppointmentController implements Initializable {
   
   private Map allCustomers = Customer.getFullMap();

   public ObservableList<String> ampmList = FXCollections.observableArrayList(
           "AM", "PM");
   public ObservableList<String> minuteList = FXCollections.observableArrayList(
           "00", "15", "30", "45");
   public ObservableList<String> hourList = FXCollections.observableArrayList(
           "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
   public ObservableList<String> nameList = FXCollections.observableArrayList();

    @FXML private TextArea descriptionField;
    @FXML private TextField contactField;
    @FXML private TextField typeField;
    @FXML private ComboBox<String> ampmCombo;
    @FXML private ComboBox<String> minuteCombo;
    @FXML private ComboBox<String> hourCombo;
    @FXML private ComboBox<String> ampmCombo1;
    @FXML private ComboBox<String> minuteCombo1;
    @FXML private ComboBox<String> hourCombo1;
    @FXML private DatePicker datePicker;
    @FXML private DatePicker datePicker1;
    @FXML private ComboBox<String> customerCombo;
    @FXML private Label errorLabel;

    @FXML
    void exitAddAppointment(ActionEvent event) {
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
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }
    }

   @FXML
   void saveAppointment(ActionEvent event) throws SQLException, IOException {
      ZonedDateTime timeStart = getStartTime();
      ZonedDateTime timeEnd = getEndTime();
      
      if(emptyField()) {
         errorLabel.setText("Please fill all Fields");
      } else if(businessHours()) {
         errorLabel.setText("Not within business hours");
      } else if(Model.Appointment.apptCollisionNew(getStartTime(), getEndTime())) {
         errorLabel.setText("Not updated.  You already have\nan appointment at that time.");
      } else if(Model.Appointment.startIsBeforeEnd(getStartTime(), getEndTime())) {
         errorLabel.setText("Can't update.  Start must be\nbefore end of appointment.");
      } else if((!"".equals(typeField.getText()))
               && (!"".equals(contactField.getText()))
               && (!"".equals(descriptionField.getText()))
               && (datePicker.getValue() != null)
               && (datePicker1.getValue() != null)) {
         Integer custID = (Integer) allCustomers.get(customerCombo.getValue());
         
         ZoneId zid = ZoneId.of("UTC");
         ZonedDateTime toLocTZ = timeStart
                 .withZoneSameInstant(zid);
         String start = toLocTZ.toLocalDateTime().toString();

         toLocTZ = timeEnd
                 .withZoneSameInstant(zid);
         String end = toLocTZ.toLocalDateTime().toString();
         String userName = Model.User.getUserObj(VC.Login.currentUserID).getUserName();

         ZonedDateTime now = ZonedDateTime.now();
         LocalDateTime ldtNow = now.toLocalDateTime();
         Timestamp ts = Timestamp.valueOf(ldtNow);
         
         CRUD.Query.makeQuery("INSERT INTO appointment (customerId, title, "
                  + "description, location, contact, url, start, end, "
                  + "createDate, createdBy, lastUpdate, lastUpdateBy) "
                  + "VALUES ('" + custID + "', '" + typeField.getText() + "', '"
                  + descriptionField.getText() + "', '" + Locale.getDefault().toString() 
                  + "', '" + contactField.getText() + "', 'url', '" 
                  + start + "', '" + end + "', '" + ts
                  + "', '" + userName + "', '" + ts
                  + "', '" + userName + "');");

         Main.fillAppointments();

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
      } else {
         errorLabel.setText("Not updated,\nplease fill all fields.");
      }
      
   }
   
   private Boolean emptyField() {
      return (ampmCombo.getSelectionModel().isEmpty()
      || hourCombo.getSelectionModel().isEmpty()
      || minuteCombo.getSelectionModel().isEmpty()
      || ampmCombo1.getSelectionModel().isEmpty()
      || hourCombo1.getSelectionModel().isEmpty()
      || minuteCombo1.getSelectionModel().isEmpty()
      || datePicker.getValue() == null
      || datePicker1.getValue() == null
      || typeField.getText().trim().isEmpty()
      || contactField.getText().trim().isEmpty()
      || descriptionField.getText().trim().isEmpty()
      || customerCombo.getSelectionModel().isEmpty());
   }
   
   private ObservableList<String> makeList(Map map) {
      ObservableList<String> list = FXCollections.observableArrayList();
      map.forEach((k, v) -> {
         list.add(k.toString());
      });
      return list;
   }

   public void setCombos() {
      ampmCombo.setItems(ampmList);
      minuteCombo.setItems(minuteList);
      hourCombo.setItems(hourList);
      ampmCombo1.setItems(ampmList);
      minuteCombo1.setItems(minuteList);
      hourCombo1.setItems(hourList);
      customerCombo.setItems(nameList);
   }

   public void resetApptFields() {
      ampmCombo.setValue(null);
      hourCombo.setValue(null);
      minuteCombo.setValue(null);
      ampmCombo1.setValue(null);
      hourCombo1.setValue(null);
      minuteCombo1.setValue(null);
      datePicker.setValue(null);
      datePicker1.setValue(null);
      typeField.setText("");
      contactField.setText("");
      descriptionField.setText("");
      errorLabel.setText("");
   }

   public ZonedDateTime getStartTime() {
      if(ampmCombo.getSelectionModel().isEmpty()
              || hourCombo.getSelectionModel().isEmpty()
              || minuteCombo.getSelectionModel().isEmpty()
              || datePicker.getValue() == null
              ) {
         return null;
      }
      
      int hourInt = Integer.parseInt(hourCombo.getValue());
      String minuteStr = minuteCombo.getValue();
      LocalDate date = datePicker.getValue();
      LocalTime time = null;
      ZonedDateTime zdt;
      String ampm = ampmCombo.getValue();
      if("PM".equals(ampm) && (hourInt < 12)) {
         hourInt += 12;
         time = LocalTime.parse(hourInt + ":" + minuteStr);
      } else if(("PM".equals(ampm)) && (hourInt == 12)) {
         time = LocalTime.parse("12:" + minuteStr);
      } else if(("AM".equals(ampm)) && (hourInt == 12)) {
         time = LocalTime.parse("00:" + minuteStr);
      } else if("AM".equals(ampm) && (hourInt < 12)) {
         time = LocalTime.parse(hourCombo.getValue() + ":" + minuteStr);
      }
      zdt = ZonedDateTime.of(date, time, ZoneId.systemDefault());
      return zdt;
   }

   public ZonedDateTime getEndTime() {
      if(ampmCombo1.getSelectionModel().isEmpty()
              || hourCombo1.getSelectionModel().isEmpty()
              || minuteCombo1.getSelectionModel().isEmpty()
              || datePicker1.getValue() == null
              ) {
         return null;
      }
         int hourInt = Integer.parseInt(hourCombo1.getValue());
         String minuteStr = minuteCombo1.getValue();
         LocalDate date = datePicker1.getValue();
         LocalTime time = null;
         ZonedDateTime zdt;
         String ampm = ampmCombo1.getValue();
         if("PM".equals(ampm) && (hourInt < 12)) {
            hourInt += 12;
            time = LocalTime.parse(hourInt + ":" + minuteStr);
         } else if("PM".equals(ampm) && (hourInt == 12)) {
            time = LocalTime.parse("12:" + minuteStr);
         } else if("AM".equals(ampm) && (hourInt == 12)) {
            time = LocalTime.parse("00:" + minuteStr);
         } else if("AM".equals(ampm) && (hourInt < 12)) {
            time = LocalTime.parse(hourCombo1.getValue() + ":" + minuteStr);
         }
         zdt = ZonedDateTime.of(date, time, ZoneId.systemDefault());
         return zdt;
   }
   
   private boolean businessHours() {
      
      ZoneId zid = ZoneId.of("America/Los_Angeles");
      
      ZonedDateTime start = getStartTime().withZoneSameInstant(zid);
      LocalTime openTime = LocalTime.of(8, 00);
      ZonedDateTime open = start.with(openTime);
      
      ZonedDateTime end = getEndTime().withZoneSameInstant(zid);
      LocalTime closeTime = LocalTime.of(18, 00);
      ZonedDateTime close = end.with(closeTime);
      
      return (start.toLocalTime().isBefore(open.toLocalTime())
              || end.toLocalTime().isAfter(close.toLocalTime())
              || start.getDayOfWeek() == DayOfWeek.SUNDAY
              || start.getDayOfWeek() == DayOfWeek.SATURDAY);
   }

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      setCombos();
      customerCombo.setItems(makeList(Customer.getFullMap()));
   }   
   
}
