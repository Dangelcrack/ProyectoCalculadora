package com.github.dangelcrack.view;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ObjectsController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private TableView<Obj> tableView;
    @FXML
    private TableColumn<Obj, Image> imageViewTableColumn;
    @FXML
    private TableColumn<Obj, String> columnNameObj;
    @FXML
    private TableColumn<Obj, String> columnDescription;
    @FXML
    private TableColumn<Obj, Image> columnCategoryBoost;
    @FXML
    private TableColumn<Obj, Image> columnMovesBoost;

    public ObservableList<Obj> objs;

    @Override
    public void onOpen(Object input) {
        List<Obj> objs = ObjDAO.build().findAll();
        this.objs = FXCollections.observableArrayList(objs);
        tableView.setItems(this.objs);
    }

    @Override
    public void onClose(Object output) {

    }
    public void deleteOldObj(Obj oldObj){
        this.objs.remove(oldObj);
    }
    public void saveObj(Obj newObj) {
        ObjDAO.build().save(newObj);
        this.objs.add(newObj);

    }
    public void deleteObj(Obj deleteObj) {
        ObjDAO.build().delete(deleteObj);
        this.objs.remove(deleteObj);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.refresh();
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/fondonegro.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));
        imageViewTableColumn.setCellValueFactory(obj -> {
            String imageExtension = obj.getValue().getPhotoObj();
            String imagePath = "/com/github/dangelcrack/media/ObjImages/" + imageExtension;
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            Image image = new Image(inputStream);
            return new SimpleObjectProperty<>(image);
        });
        imageViewTableColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    setGraphic(imageView);
                }
            }
        });
        columnNameObj.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getNameObject()));
        columnNameObj.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }

            if (event.getNewValue().length() <= 20) {
                Obj obj = event.getRowValue();
                ObjDAO.build().delete(obj);
                obj.setNameObject(event.getNewValue());
                ObjDAO.build().save(obj);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("¡Te has pasado del límite de caracteres!");
                alert.show();
            }
        });
        columnDescription.setCellValueFactory(obj -> new SimpleStringProperty(obj.getValue().getDescription()));
        columnCategoryBoost.setCellValueFactory(obj -> {
            SimpleObjectProperty result;
            Category boostCategory = obj.getValue().getBoostCategory();
            if (boostCategory == null) {
                result = new SimpleObjectProperty<>();
            } else {
                Image secondTypeImage = new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/ModalImageUtils/" + boostCategory + ".png"));
                result = new SimpleObjectProperty<>(secondTypeImage);
            }
            return result;
        });
        columnCategoryBoost.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    setGraphic(imageView);
                }
            }
        });
        columnMovesBoost.setCellValueFactory(obj -> {
            SimpleObjectProperty result;
            Types movesTypeBoost = obj.getValue().getBoostType();
            if (movesTypeBoost == null) {
                result = new SimpleObjectProperty<>();
            } else {
                Image secondTypeImage = new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/TypesImage/" + movesTypeBoost + ".png"));
                result = new SimpleObjectProperty<>(secondTypeImage);
            }
            return result;
        });
        columnMovesBoost.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    setGraphic(imageView);
                }
            }
        });

    }
    @FXML
    private void addObjet() throws IOException {
        App.currentController.openModal(Scenes.ADDOBJECT, "Agregando un Objeto...", this, null);
    }
    @FXML
    private void deleteObjet() throws IOException {
        App.currentController.openModal(Scenes.DELETEOBJECT, "Borrando un Objeto...", this, null);
    }
    @FXML
    private void editObjet() throws IOException {
        App.currentController.openModal(Scenes.EDITOBJECT, "Editando un Objeto...", this, null);
    }
}
