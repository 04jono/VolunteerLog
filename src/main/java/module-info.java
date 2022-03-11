module com.volunteerlog.volunteerlog2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.json;
    requires kernel;
    requires forms;
    requires layout;

    opens com.volunteerlog.volunteerlog2 to javafx.fxml;
    exports com.volunteerlog.volunteerlog2;
}