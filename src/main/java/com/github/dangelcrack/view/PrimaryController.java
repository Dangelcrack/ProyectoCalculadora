package com.github.dangelcrack.view;

import java.io.IOException;

import com.github.dangelcrack.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
