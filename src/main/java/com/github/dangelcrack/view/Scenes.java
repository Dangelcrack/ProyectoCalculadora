package com.github.dangelcrack.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    MAIN("view/main.fxml"),
    ABOUT("view/about.fxml"),
    FORMPOKEMON("view/formPokemon.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
