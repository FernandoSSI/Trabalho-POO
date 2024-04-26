module com.example.gestaodeeventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                        
    opens com.example.gestaodeeventos to javafx.fxml;
    exports com.example.gestaodeeventos;
    exports com.example.gestaodeeventos.controllers;
    opens com.example.gestaodeeventos.controllers to javafx.fxml;
}