package com.github.dangelcrack.controller;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Category;
import com.github.dangelcrack.model.entity.Scenes;
import com.github.dangelcrack.model.entity.Types;
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
    /**
     * Called when the controller is opened.
     * Retrieves all objects from the database, updates the observable list, and sets it to the table view.
     *
     * @param input The input object, not used in this method.
     */
    @Override
    public void onOpen(Object input) {
        List<Obj> objs = ObjDAO.build().findAll();
        this.objs = FXCollections.observableArrayList(objs);
        tableView.setItems(this.objs);
    }

    /**
     * Called when the controller is closed.
     *
     * @param output The output object, not used in this method.
     */
    @Override
    public void onClose(Object output) {
        // No action needed on close
    }

    /**
     * Deletes the specified old object from the observable list.
     *
     * @param oldObj The object to be deleted.
     */
    public void deleteOldObj(Obj oldObj) {
        this.objs.remove(oldObj);
    }

    /**
     * Saves the new object to the database and adds it to the observable list.
     *
     * @param newObj The object to be saved.
     */
    public void saveObj(Obj newObj) {
        ObjDAO.build().save(newObj);
        this.objs.add(newObj);
    }

    /**
     * Deletes the specified object from the database and removes it from the observable list.
     *
     * @param deleteObj The object to be deleted.
     */
    public void deleteObj(Obj deleteObj) {
        ObjDAO.build().delete(deleteObj);
        this.objs.remove(deleteObj);
    }

    /**
     * Initializes the controller when the corresponding view is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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
    /**
     * Handles the event when the user wants to add an object.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void addObjet() throws IOException {
        App.currentController.openModal(Scenes.ADDOBJECT, "Adding an Object...", this, null);
    }

    /**
     * Handles the event when the user wants to delete an object.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void deleteObjet() throws IOException {
        App.currentController.openModal(Scenes.DELETEOBJECT, "Deleting an Object...", this, null);
    }

    /**
     * Handles the event when the user wants to edit an object.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void editObjet() throws IOException {
        App.currentController.openModal(Scenes.EDITOBJECT, "Editing an Object...", this, null);
    }
}
