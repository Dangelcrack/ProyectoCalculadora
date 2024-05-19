package com.github.dangelcrack.view;

/**
 * Enum representing various scenes in the application.
 * Each scene is associated with a specific FXML file.
 */
public enum Scenes {
    /** The root layout of the application. */
    ROOT("view/layout.fxml"),
    /** Scene for displaying the list of Pokemon. */
    PokemonList("view/PokemonList.fxml"),
    /** Scene for displaying the list of moves. */
    MOVESLIST("view/MovesList.fxml"),
    /** Scene for displaying the list of objects. */
    OBJECTSLIST("view/ObjectsList.fxml"),
    /** Scene for the 'About' information. */
    ABOUT("view/about.fxml"),
    /** Scene for adding a new Pokemon. */
    ADDPOKEMON("view/addPokemon.fxml"),
    /** Scene for adding a new move. */
    ADDMOVE("view/addMove.fxml"),
    /** Scene for adding a new object. */
    ADDOBJECT("view/addObject.fxml"),
    /** Scene for deleting a Pokemon. */
    DELETEPOKEMON("view/deletePokemon.fxml"),
    /** Scene for deleting a move. */
    DELETEMOVE("view/deleteMove.fxml"),
    /** Scene for deleting an object. */
    DELETEOBJECT("view/deleteObject.fxml"),
    /** Scene for editing a Pokemon. */
    EDITPOKEMON("view/editPokemon.fxml"),
    /** Scene for editing a move. */
    EDITMOVE("view/editMove.fxml"),
    /** Scene for editing an object. */
    EDITOBJECT("view/editObject.fxml"),
    /** Scene for combat-related actions. */
    COMBATS("view/combats.fxml");
    /** The URL of the FXML file associated with the scene. */
    private String url;
    /**
     * Constructor for the enum constant.
     * @param url The URL of the FXML file.
     */
    Scenes(String url){
        this.url = url;
    }
    /**
     * Retrieves the URL of the FXML file.
     * @return The URL as a String.
     */
    public String getURL(){
        return url;
    }
}
