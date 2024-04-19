package com.github.dangelcrack.view;

import java.io.IOException;

import com.github.dangelcrack.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}