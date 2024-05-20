package com.github.dangelcrack.controller;
import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.entity.Obj;
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
    // Reference to the ObjectsController
    private ObjectsController controller;
    /**
     * This method is called when the view is opened.
     * @param input The ObjectsController instance passed as input.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (ObjectsController) input;
    }
    /**
     * This method is called when the view is closed.
     * @param output The output data to be passed, not used in this implementation.
     */
    @Override
    public void onClose(Object output) {
        // Not implemented
    }
    /**
     * Initializes the controller class, sets up the background image, and populates the objects combo box.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the background image for the vbox
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/img.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));

        // Retrieve the list of objects and set them in the combo box
        List<Obj> objs = ObjDAO.build().findAll();
        ObservableList<Obj> observableNames = FXCollections.observableArrayList(objs);
        objComboBox.setItems(observableNames);

        // Set an action on the combo box to initialize the view with the selected object
        objComboBox.setOnAction(event -> {
            Obj selectedObj = objComboBox.getValue();
            initializeWithObj(selectedObj);
        });
    }

    /**
     * Initializes the view with the selected object's image.
     * @param obj The Obj instance containing the object's data.
     */
    private void initializeWithObj(Obj obj) {
        // Load the object's image and set it to the imageView
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/ObjImages/" + obj.getPhotoObj());
        Image image = new Image(inputStream);
        imageView.setImage(image);
    }

    /**
     * Handles the close window event, deletes the selected object, and hides the window.
     * @param event The event that triggered the method call.
     */
    @FXML
    private void closeWindow(Event event) {
        // Get the selected object name from the combo box
        String objName = objComboBox.getValue().getNameObject();
        if (objName != null) {
            // Find the object by name and delete it if it exists
            Obj obj = ObjDAO.build().findByName(objName);
            if (obj != null) {
                this.controller.deleteObj(obj);
            }
        }
        // Hide the window
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}