module com.example.gestoreventos {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
                        
    opens com.example.gestoreventos to javafx.fxml;
    exports com.example.gestoreventos;
}