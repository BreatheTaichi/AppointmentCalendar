/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.ZonedDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author G
 */
public abstract class Stamp {
   protected ZonedDateTime createDate;
   protected StringProperty createdBy = new SimpleStringProperty();
   protected ZonedDateTime lastUpdate;
   protected StringProperty lastUpdateBy = new SimpleStringProperty();
   
   public final void setCreateDate(ZonedDateTime createDate) {
      this.createDate = createDate;
   }
   public final ZonedDateTime getCreateDate() {
      return createDate;
   }
      
   public StringProperty createdByProperty() {
      return createdBy;
   }
   public final void setCreatedBy(String createdBy) {
      this.createdByProperty().set(createdBy);
   }
   public final String getCreatedBy() {
      return createdByProperty().get();
   }
   
   public final void setLastUpdate(ZonedDateTime lastUpdate) {
      this.lastUpdate = lastUpdate;
   }
   public final ZonedDateTime getLastUpdate() {
      return lastUpdate;
   }
   
   public StringProperty lastUpdateByProperty() {
      return lastUpdateBy;
   }
   public final void setLastUpdateBy(String lastUpdateBy) {
      this.lastUpdateByProperty().set(lastUpdateBy);
   }
   public final String getLastUpdateBy() {
      return lastUpdateByProperty().get();
   }
}
