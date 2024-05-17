package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteObjectController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Obj> objComboBox;
    @FXML
    private ImageView imageView;
    private ObjectsController controller;
    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (ObjectsController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/img.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));
        List<Obj> objs;
        objs = ObjDAO.build().findAll();
        ObservableList<Obj> observableNames = FXCollections.observableArrayList(objs);
        objComboBox.setItems(observableNames);
        objComboBox.setOnAction(event -> {
            Obj selectedObj = objComboBox.getValue();
            initializeWithObj(selectedObj);
        });
    }
    private void initializeWithObj(Obj obj) {
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/ObjImages/" + obj.getPhotoObj());
        Image image = new Image(inputStream);
        imageView.setImage(image);
    }
    @FXML
    private void closeWindow(Event event) {
        String objName = objComboBox.getValue().getNameObject();
        if (objName != null) {
            Obj obj = ObjDAO.build().findByName(objName);
            if (obj != null) {
                this.controller.deleteObj(obj);
            }
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
