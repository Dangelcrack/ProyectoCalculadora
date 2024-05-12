package com.github.dangelcrack.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    PokemonList("view/PokemonList.fxml"),
    MOVESLIST("view/MovesList.fxml"),
    OBJECTSLIST("view/ObjectsList.fxml"),
    ABOUT("view/about.fxml"),
    ADDPOKEMON("view/addPokemon.fxml"),
    ADDMOVE("view/addMove.fxml"),
    ADDOBJECT("view/addObject.fxml"),
    DELETEPOKEMON("view/deletePokemon.fxml"),
    DELETEMOVE("view/deleteMove.fxml"),
    DELETEOBJECT("view/deleteObject.fxml"),
    EDITPOKEMON("view/editPokemon.fxml"),
    EDITMOVE("view/editMove.fxml"),
    EDITOBJECT("view/editObject.fxml"),
    COMBATS("view/combats.fxml");
    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
