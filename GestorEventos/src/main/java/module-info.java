module com.gestoreventostest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.gestoreventostest to javafx.fxml;
    exports com.gestoreventostest;
    exports com.gestoreventostest.controllers;
    opens com.gestoreventostest.controllers to javafx.fxml;
}