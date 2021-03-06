/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VC;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author G
 */
public class ConsultantReportController implements Initializable {
   ObservableList<String> appointmentList = FXCollections.observableArrayList();
   Map<String, Integer> consultantMap = new HashMap<>();

   @FXML private ComboBox<String> consultantCombo;

   @FXML private ListView<String> appointmentListView;

   @FXML void backButtonAction(ActionEvent event) { 

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
         
         Stage stage = (Stage) appointmentListView.getScene().getWindow();
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
   
   private void setConsultantCombo() {
      
      consultantMap = Model.User.getMap();
      consultantCombo.setItems(makeList(consultantMap));
      
      consultantCombo.getSelectionModel().selectedItemProperty().addListener(a -> {
         String chose = (String) consultantCombo.getSelectionModel().getSelectedItem();
         consultantMap = Model.User.getMap();
         appointmentList = Model.Appointment.getList(consultantMap.get(chose));
         appointmentListView.setItems(appointmentList);
      });
   }

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      setConsultantCombo();
   }   
   
}
