module com.github.dangelcrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens com.github.dangelcrack to javafx.fxml;
    opens com.github.dangelcrack.model.connection to java.xml.bind;
    exports com.github.dangelcrack;
    exports com.github.dangelcrack.view;
    opens com.github.dangelcrack.view to javafx.fxml;
    exports com.github.dangelcrack.controller;
    opens com.github.dangelcrack.controller to javafx.fxml;
    exports com.github.dangelcrack.model.entity;
    opens com.github.dangelcrack.model.entity to javafx.base, javafx.fxml;
}
