/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import CRUD.Query;
import Model.Appointment;
import Model.Customer;
import Model.User;
import static VC.Main.appointments;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author G
 */
public class AppointmentsController implements Initializable {

   private List<String> calDates = new ArrayList<>();
   public LocalDate currentDate = LocalDate.now();
   private LocalDate today = LocalDate.now();

   public static Appointment activeAppt = null;
   public static Customer activeCustomer = null;
   public static ZoneId zone = ZoneId.systemDefault();

   public static DateTimeFormatter my = DateTimeFormatter.ofPattern("MMMM YYYY");
   public static DateTimeFormatter yearMonthDay = DateTimeFormatter.ofPattern("YYYY MMMM dd");
   public static DateTimeFormatter ymdhm = DateTimeFormatter.ofPattern("YYYY MMMM dd, hh:mm");
   public static DateTimeFormatter hourMinute = DateTimeFormatter.ofPattern("hh:mm");
   public static DateTimeFormatter hour = DateTimeFormatter.ofPattern("hh");
   public static DateTimeFormatter hour24 = DateTimeFormatter.ofPattern("HH");
   public static DateTimeFormatter minute = DateTimeFormatter.ofPattern("mm");
   
   
   // Key: Day of month, Value: List of appointmentIDs for that day
   Map<Integer, List<Integer>> apptListByDay = new HashMap<>();

   @FXML private GridPane calendar;
   @FXML private GridPane weekCal;
   @FXML private Label monthLabel;
   @FXML private Pane leftPane;
   @FXML private Label userName;
   @FXML private Label errorLabel;
   @FXML private Label custErrorLabel;
   @FXML private DatePicker datePicker;
   @FXML private DatePicker datePicker1;
   @FXML private TextArea descriptionField;
   @FXML private TextField countryField;
   @FXML private TextField cityField;
   @FXML private TextField addressField;
   @FXML private TextField address2Field;
   @FXML private TextField zipcodeField;
   @FXML private TextField phoneField;
   @FXML private TextField nameField;
   @FXML private TextField contactField;
   @FXML private TextField typeField;
   @FXML private ImageView weekLeftArrow;
   @FXML private ImageView weekRightArrow;
   @FXML private ImageView monthLeftArrow;
   @FXML private ImageView monthRightArrow;
   @FXML private Label typeLabel;
   @FXML private Label dateLabel;
   @FXML private Label date1Label;
   @FXML private Label contactLabel;
   @FXML private Label fromLabel;
   @FXML private Label toLabel;
   @FXML private Label secondLabel;
   @FXML private Label address1Label;
   @FXML private Label customerLabel;
   @FXML private Label descriptionLabel;
   @FXML private Label phoneLabel;
   @FXML private Label addressLabel;
   @FXML private Label zipcodeLabel;
   @FXML private Label cityLabel;
   @FXML private Label countryLabel;
   @FXML private Button updateApptButton;
   @FXML private Button deleteApptButton;
   @FXML private Button updateCustButton;
   @FXML private Button deleteCustButton;

   @FXML private ToggleButton month;
   @FXML private ToggleButton week;
   
   @FXML private ComboBox<String> customerCombo = new ComboBox<>();

   @FXML private ComboBox<String> ampmCombo = new ComboBox<>();
   @FXML private ComboBox<String> minuteCombo = new ComboBox<>();
   @FXML private ComboBox<String> hourCombo = new ComboBox<>();

   @FXML private ComboBox<String> ampmCombo1 = new ComboBox<>();
   @FXML private ComboBox<String> minuteCombo1 = new ComboBox<>();
   @FXML private ComboBox<String> hourCombo1 = new ComboBox<>();

   public ObservableList<String> ampmList = FXCollections.observableArrayList(
           "AM", "PM");
   public ObservableList<String> minuteList = FXCollections.observableArrayList(
           "00", "15", "30", "45");
   public ObservableList<String> hourList = FXCollections.observableArrayList(
           "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
   public ObservableList<String> nameList = FXCollections.observableArrayList();
   
   @FXML void addAppt(ActionEvent event) throws IOException {
            
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/AddAppointment.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Add Appointment");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }
   
   @FXML void consultantReportButtonAction(ActionEvent event) throws IOException {
            
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/ConsultantReport.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Consultant Schedule Report");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }
   
   @FXML void monthReportButtonAction(ActionEvent event) throws IOException {
            
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/TypeByMonth.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Appointment Type By Month");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();
      
   }
   
   @FXML void perCountryButtonAction(ActionEvent event) throws IOException {
            
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/PerCountry.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Reports");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();      
   }

   @FXML void toMonth(ActionEvent event) {
      resetAll();
      formatCalendar(currentDate);
   }

   @FXML void toWeek(ActionEvent event) {
      resetAll();
      formatWeek(currentDate);
   }

   @FXML void addCustomer(ActionEvent event) throws IOException {
            
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/AddCustomer.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Add Customer");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }

   @FXML void deleteCustomer(ActionEvent event) throws SQLException, IOException {
            
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/Customer.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Update/Delete Customer");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }

   @FXML void deleteAppt(ActionEvent event) throws SQLException {
      if(activeAppt == null) {
         custErrorLabel.setText("");
         errorLabel.setText("No appointment selected.");
         return;
      }
      ZonedDateTime date = ZonedDateTime.parse(activeAppt.getStart());
      LocalDate date2 = date.toLocalDate();
      
      Query.makeQuery("DELETE FROM appointment WHERE appointmentId = " 
              + activeAppt.getID() + ";");
      
      Main.fillAppointments();
      
      activeAppt = null;
      activeCustomer = null;
      resetApptFields();
      resetCustomerFields();
      setVisible(false);
      if(week.isSelected()) {
         formatWeek(date2);
      } else {
         formatCalendar(date2);
      }
   }

   @FXML void monthDown(MouseEvent event) {
      currentDate = currentDate.minusMonths(1).withDayOfMonth(1);
      activeAppt = null;
      activeCustomer = null;
      resetCustomerFields();
      resetApptFields();
      formatCalendar(currentDate);
   }

   @FXML void monthUp(MouseEvent event) {
      currentDate = currentDate.plusMonths(1).withDayOfMonth(1);
      activeAppt = null;
      activeCustomer = null;
      resetCustomerFields();
      resetApptFields();
      formatCalendar(currentDate);
   }

   @FXML void weekDown(MouseEvent event) {
      currentDate = currentDate.minusWeeks(1);
      activeAppt = null;
      activeCustomer = null;
      resetCustomerFields();
      resetApptFields();
      formatWeek(currentDate);
   }

   @FXML void weekUp(MouseEvent event) {
      currentDate = currentDate.plusWeeks(1);
      activeAppt = null;
      activeCustomer = null;
      resetCustomerFields();
      resetApptFields();
      formatWeek(currentDate);
   }

   @FXML void updateCustomer(ActionEvent event) throws IOException {

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(AppointmentsController.class.getResource("/VC/Customer.fxml"));
      AnchorPane ap = (AnchorPane) loader.load();

      Stage cStage = new Stage();
      cStage.setTitle("Customer Information");
      Scene scene = new Scene(ap);

      cStage.hide();
      cStage.setScene(scene);
      cStage.isAlwaysOnTop();

      Stage stage = (Stage) monthLabel.getScene().getWindow();
      stage.close();

      cStage.show();
   }
   
   private void setVisible(Boolean v) {
      ampmCombo.setVisible(v);
      minuteCombo.setVisible(v);
      hourCombo.setVisible(v);
      ampmCombo1.setVisible(v);
      minuteCombo1.setVisible(v);
      hourCombo1.setVisible(v);
      customerCombo.setVisible(v);
      typeField.setVisible(v);
      contactField.setVisible(v);
      address2Field.setVisible(v);
      nameField.setVisible(v);
      descriptionField.setVisible(v);
      phoneField.setVisible(v);
      addressField.setVisible(v);
      zipcodeField.setVisible(v);
      cityField.setVisible(v);
      countryField.setVisible(v);
      datePicker.setVisible(v);
      datePicker1.setVisible(v);
      typeLabel.setVisible(v);
      contactLabel.setVisible(v);
      fromLabel.setVisible(v);
      toLabel.setVisible(v);
      secondLabel.setVisible(v);
      address1Label.setVisible(v);
      customerLabel.setVisible(v);
      descriptionLabel.setVisible(v);
      phoneLabel.setVisible(v);
      addressLabel.setVisible(v);
      zipcodeLabel.setVisible(v);
      cityLabel.setVisible(v);
      countryLabel.setVisible(v);
      updateApptButton.setVisible(v);
      deleteApptButton.setVisible(v);
//      updateCustButton.setVisible(v);
//      deleteCustButton.setVisible(v);
      dateLabel.setVisible(v);
      date1Label.setVisible(v);
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

   public void resetAll() {
      resetCustomerFields();
      resetApptFields();
      activeAppt = null;
      activeCustomer = null;
      setVisible(false);
   }

   public void fillCustomerFields(Customer cust) {
      setVisible(true);
      nameField.setText(cust.getCustomerName());
      addressField.setText(cust.getAddress().getAddress());
      address2Field.setText(cust.getAddress().getAddress2());
      cityField.setText(cust.getCity().getCity());
      zipcodeField.setText(cust.getAddress().getPostalCode());
      countryField.setText(cust.getCountry().getCountry());
      phoneField.setText(cust.getAddress().getPhone());
      
      custErrorLabel.setText("");
   }

   public void resetCustomerFields() {

      nameField.setText("");
      addressField.setText("");
      address2Field.setText("");
      cityField.setText("");
      zipcodeField.setText("");
      countryField.setText("");
      phoneField.setText("");
      custErrorLabel.setText("");
   }

   public void fillApptFields(Appointment appt) {
      typeField.setText(appt.getType());
      contactField.setText(appt.getContact());

      ZonedDateTime time = ZonedDateTime.parse(appt.getStart());
      String startTimeHour = time.format(hour);
      String startTimeMinute = time.format(minute);
      String startTimeAMPM = time.format(hour24);

      ZonedDateTime time1 = ZonedDateTime.parse(appt.getEnd());
      String endTimeHour1 = time1.format(hour);
      String endTimeMinute1 = time1.format(minute);
      String endTimeAMPM1 = time1.format(hour24);

      int hr = Integer.parseInt(startTimeAMPM);
      if(hr >= 12) {
         ampmCombo.setValue(ampmList.get(1));
      } else { ampmCombo.setValue(ampmList.get(0)); }

      int hr1 = Integer.parseInt(endTimeAMPM1);
      if(hr1 >= 12) {
         ampmCombo1.setValue(ampmList.get(1));
      } else { ampmCombo1.setValue(ampmList.get(0)); }

      ZonedDateTime date = ZonedDateTime.parse(appt.getStart());
      LocalDate date2 = date.toLocalDate();
      datePicker.setValue(date2);
      ZonedDateTime date3 = ZonedDateTime.parse(appt.getEnd());
      LocalDate date4 = date3.toLocalDate();
      datePicker1.setValue(date4);
      hourCombo.setValue(startTimeHour);
      minuteCombo.setValue(startTimeMinute);
      hourCombo1.setValue(endTimeHour1);
      minuteCombo1.setValue(endTimeMinute1);
      descriptionField.setText(appt.getDescription());
      errorLabel.setText("");
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

   @FXML void updateAppt(ActionEvent event) throws SQLException {
      if(activeAppt == null) {
         errorLabel.setText("No appointment selected.");
         custErrorLabel.setText("");
      } else if(businessHours()) {
         errorLabel.setText("Not within business hours");
      } else if(Model.Appointment.apptCollision(getStartTime(), getEndTime())) {
         errorLabel.setText("Not updated.  You already have\nan appointment at that time.");
      } else if(Model.Appointment.startIsBeforeEnd(getStartTime(), getEndTime())) {
         errorLabel.setText("Can't update.  Start must be\nbefore end of appointment.");
      } else if((activeAppt != null)
               && (!"".equals(typeField.getText()))
               && (!"".equals(contactField.getText()))
               && (!"".equals(descriptionField.getText()))) {
         
         Integer theActiveID = activeAppt.getID();
         
         ZoneId zid = ZoneId.of("UTC");
         ZonedDateTime toLocTZ = getStartTime()
                 .withZoneSameInstant(zid);
         String start = toLocTZ.toLocalDateTime().toString();

         toLocTZ = getEndTime()
                 .withZoneSameInstant(zid);
         String end = toLocTZ.toLocalDateTime().toString();
         
         Query.makeQuery("UPDATE appointment SET title = '" + typeField.getText() + "', "
                  + "description = '" + descriptionField.getText() + "', "
                  + "contact = '" + contactField.getText() + "', "
                  + "start = '" + start + "', "
                  + "end = '" + end 
                  + "' WHERE appointmentId = " + activeAppt.getID() + ";");
         
         Main.fillAppointments();
         
         resetApptFields();
         if(month.isSelected()) {
            formatCalendar(activeAppt.getLocalDate());
         } else if(week.isSelected()) {
            formatWeek(activeAppt.getLocalDate());
         }
         activeAppt = Appointment.getAppointmentObj(theActiveID);
         fillApptFields(activeAppt);
         errorLabel.setText("Appointment updated");
      } else {
         errorLabel.setText("Not updated,\nplease fill all fields.");
      }
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

   private void fillApptList(LocalDate start, LocalDate end) {
   // Take the string of a date formatted in (Month Year) my, and int last day of month.
      // Fill apptListByDay with a key for each day.
      for(int i = 1; i <= end.getDayOfMonth(); i++) {
         List<Integer> apptList = new ArrayList<>();
         apptListByDay.put(i, apptList);
      }
      
      int firstDay = start.getDayOfWeek().getValue() - 1;
      
      // For each appointment get the day, and use that as a key to add the appointment ID
      for(Appointment a : appointments) {
         if(a.getLocalDate().isAfter(start.minusDays(1)) 
                 && a.getLocalDate().isBefore(end.plusDays(1))) {
            int dayKey = a.getLocalDate().getDayOfMonth() + firstDay;
            
            apptListByDay.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(a.getID());
         }
      }
   }

   private void fillApptListWeek(LocalDate start, LocalDate end) {
   // Take the string of a date formatted in (Month Year) my, and int last day of month.
      // Fill apptListByDay with a key for each day.
      for(int i = 0; i < 7; i++) {
         List<Integer> apptList = new ArrayList<>();
         apptListByDay.put(i, apptList);
      }
      // For each appointment get the day, and use that as a key to add the appointment ID
      // if that appt is within the current week.
      for(Appointment a : appointments) {
         if(a.getLocalDate().isAfter(start.minusDays(1)) 
                 && a.getLocalDate().isBefore(end.plusDays(1))) {
            int dayKey = a.getLocalDate().getDayOfWeek().getValue() - 1; // get day of week
            apptListByDay.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(a.getID());
         }
      }
   }
   
   private void makeDayButton(int day, int i, int l) {
      int p = (i * 7) + l;
      Button button = new Button();
      button.setId("calendar-button");
      button.setMinSize(128, 147);
      button.setOnAction((ActionEvent event) -> {
         resetApptFields();
         resetCustomerFields();
         activeAppt = null;
         activeCustomer = null;
         currentDate = currentDate.withDayOfMonth(day);
         setVisible(false);
      });
      calendar.add(button, l, i);
      GridPane.setValignment(button, VPos.TOP);
      GridPane.setHalignment(button, HPos.LEFT);

      // Add day number to cell.
      Label label = new Label("" + day);
      label.setFont(new Font("Arial", 30));

      calendar.add(label, l, i);
      GridPane.setHalignment(label, HPos.RIGHT);
      GridPane.setValignment(label, VPos.TOP);
   }

   private void addAppointments(List<Integer> apptsToday, int i, int l) {
      double offsetY = -54;
      double offsetX = 5;
      if(apptsToday == null || apptsToday.isEmpty()) {
         return;
      }
      for(Integer m : apptsToday) {

         offsetY += 25;

         if(!Main.appointments.contains(Model.Appointment.getAppointmentObj(m))) return;

         String nLabel =
            (Customer.getCustomerObj
               (Model.Appointment.getAppointmentObj(m)
               .getCustomerID()))
            .getCustomerName();

         // Create apptTime by getting the appointments object's appointment time
         ZonedDateTime startTime = ZonedDateTime.parse(
                 Model.Appointment.getAppointmentObj(m).getStart());
         String sTime = startTime.format(hourMinute);
         ZonedDateTime endTime = ZonedDateTime.parse(
                 Model.Appointment.getAppointmentObj(m).getEnd());
         String eTime = endTime.format(hourMinute);
         Button apptBtn = new Button(sTime + "-" + eTime + "\n" + nLabel);

         apptBtn.setTranslateX(offsetX);
         offsetY += 15;
         apptBtn.setTranslateY(offsetY);

         apptBtn.setId("appt-label");

         apptBtn.setOnAction(  (ActionEvent event) -> {
            fillApptFields(Model.Appointment.getAppointmentObj(m));
            fillCustomerFields(Customer.getCustomerObj(
                    Model.Appointment.getAppointmentObj(m).getCustomerID()));
            activeAppt = Model.Appointment.getAppointmentObj(m);
            activeCustomer = Model.Customer.getCustomerObj(activeAppt.getCustomerID());
            currentDate = Model.Appointment.getAppointmentObj(m).getLocalDate();
            setVisible(true);
         });

         if(offsetY < 40) {
            calendar.add(apptBtn, l, i);
         } else {
            Button ellipses = new Button("More...  ");
            ellipses.setTranslateX(offsetX);
            ellipses.setTranslateY(59);
            ellipses.setId("appt-label-ellipses");
            calendar.add(ellipses, l, i);
            ellipses.setOnAction((ActionEvent event) -> {
               formatWeek(currentDate);
            });
         }
      }
   }

   private void addAppointmentsWeek(List<Integer> apptsToday, int i) {
      if(apptsToday.isEmpty()) return;
      double offsetY = -430;
      double offsetX = 5;
      if(apptsToday == null || apptsToday.isEmpty()) {
         return;
      }

      for(Integer m : apptsToday) {
         offsetY += 25;
         
         if(!Main.appointments.contains(Model.Appointment.getAppointmentObj(m))) return;

         String nLabel =
            Customer.getCustomerObj(
               Model.Appointment.getAppointmentObj(m)
               .getCustomerID())
            .getCustomerName();

         // Create apptTime by getting the appointments object's appointment time
         ZonedDateTime startTime = ZonedDateTime.parse(
                 Model.Appointment.getAppointmentObj(m).getStart());
         String sTime = startTime.format(hourMinute);
         ZonedDateTime endTime = ZonedDateTime.parse(
                 Model.Appointment.getAppointmentObj(m).getEnd());
         String eTime = endTime.format(hourMinute);
         Button apptBtn = new Button(sTime + "-" + eTime + "\n" + nLabel);

         apptBtn.setTranslateX(offsetX);
         offsetY += 18;
         apptBtn.setTranslateY(offsetY);

         apptBtn.setId("appt-label");

         apptBtn.setOnAction((ActionEvent event) -> {
            fillApptFields(Model.Appointment.getAppointmentObj(m));
            fillCustomerFields(Customer.getCustomerObj(
                    Model.Appointment.getAppointmentObj(m).getCustomerID()));
            activeAppt = Model.Appointment.getAppointmentObj(m);
            activeCustomer = Model.Customer.getCustomerObj(activeAppt.getCustomerID());
            currentDate = Model.Appointment.getAppointmentObj(m).getLocalDate();
            formatWeek(currentDate);
            setVisible(true);
         });

         weekCal.add(apptBtn, i, 0);
         
      }
   }

   private void formatWeek(LocalDate date) {
      week.setSelected(true);
      apptListByDay.clear();
      calendar.setVisible(false);
      weekCal.setVisible(true);
      monthLeftArrow.setVisible(false);
      monthRightArrow.setVisible(false);
      weekLeftArrow.setVisible(true);
      weekRightArrow.setVisible(true);

      // Clear appointments from cal, and keep gridlines
      Node gridLines = weekCal.getChildren().get(0);
      weekCal.getChildren().clear();
      weekCal.getChildren().add(0, gridLines);

      // Get first and last day of week
      LocalDate firstDay = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
      LocalDate lastDay = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
      monthLabel.setFont(Font.font("Arial", 34));
      monthLabel.setText(firstDay + " - " + lastDay);

      fillApptListWeek(firstDay, lastDay);

      for(int i = 0; i < 7; i++) {
         Button button = new Button();
         button.setId("calendar-button");
         button.setMinSize(128, 891);
         final int tDay = firstDay.getDayOfMonth();
         button.setOnAction((ActionEvent event) -> {
            resetApptFields();
            resetCustomerFields();
            activeAppt = null;
            activeCustomer = null;
            currentDate = currentDate.withDayOfMonth(tDay);
         });
         weekCal.add(button, i, 0);
         GridPane.setValignment(button, VPos.TOP);
         GridPane.setHalignment(button, HPos.LEFT);

         // Add day number to cell.
         Label label = new Label(Integer.toString(firstDay.getDayOfMonth()));
         label.setFont(new Font("Arial", 30));

         firstDay = firstDay.plusDays(1);

         weekCal.add(label, i, 0);
         GridPane.setHalignment(label, HPos.RIGHT);
         GridPane.setValignment(label, VPos.TOP);

         // Get list of appointments, if exists add it to the calendar
         List<Integer> apptsToday = apptListByDay.get(i);
         
         addAppointmentsWeek(apptsToday, i);
      }
   }
   
   private void appointmentCheck() {
      ZonedDateTime rightNow = ZonedDateTime.now();
      ZonedDateTime nowAndFifteen = rightNow.plusMinutes(15);
      
      Main.appointments.forEach((a) -> {
         ZonedDateTime st = ZonedDateTime.parse(a.getStart());
         if (!(st.isBefore(rightNow) || st.isAfter(nowAndFifteen)) ) {
            activeAppt = a;
            activeCustomer = Customer.getCustomerObj(a.getCustomerID());
            fillApptFields(activeAppt);
            fillCustomerFields(activeCustomer);
            errorLabel.setText("This appointment is about to start");
         }
      });
   }

   private void formatCalendar(LocalDate date) {
      calendar.setVisible(true);
      weekCal.setVisible(false);
      monthLeftArrow.setVisible(true);
      monthRightArrow.setVisible(true);
      weekLeftArrow.setVisible(false);
      weekRightArrow.setVisible(false);
      apptListByDay.clear();
      calDates.clear();

      // Keep gridlines on the grid
      Node gridLines = calendar.getChildren().get(0);
      calendar.getChildren().clear();
      calendar.getChildren().add(0, gridLines);

      // Format month label
      String thisMonth = date.format(my);
      monthLabel.setFont(Font.font("Arial", 50));
      monthLabel.setText(thisMonth);

      // Get first and last days of month.
      LocalDate startMonth = date.withDayOfMonth(1);
      LocalDate endMonth = date.withDayOfMonth(date.lengthOfMonth());

      int firstDay = startMonth.getDayOfWeek().getValue();
      int lastDay = endMonth.getDayOfMonth();
      fillApptList(startMonth, endMonth);

      // Collect strings to fill in calendar dates
      for(int i = 0; i < firstDay; i++) {
         calDates.add("");
      }

      for(int i = 1; i <= lastDay; i++) {
         calDates.add("" + i);
      }

      for(int i = lastDay; i <= 42; i++) {
         calDates.add("");
      }
      
      // First loop with i runs 6 times, one for each week row 0-5
      // Second loop l runs 7 times, one for each day column 0-6
      for(int i = 0; i < 6; i++) {
         for(int l = 0; l < 7; l++) {
            int p = (i * 7) + l + 1; // p iterates from 1 to 42, each cell in the grid.

            List<Integer> apptsToday = apptListByDay.get(p);
            if (!"".equals(calDates.get(p))) {
               makeDayButton(Integer.parseInt(calDates.get(p)), i, l);
               addAppointments(apptsToday, i, l);
            }
         }
      }
   }

   @Override public void initialize(URL url, ResourceBundle rb) {
      setVisible(false);
      setCombos();
      month.setSelected(true);
      userName.setText(User.getUserObj(VC.Login.currentUserID).getUserName());
      descriptionField.setWrapText(true);

      // Initially fill calendar with current date
      formatCalendar(today);
      
      appointmentCheck();
   }
}
