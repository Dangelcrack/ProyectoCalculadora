package com.github.dangelcrack.test;


import com.github.dangelcrack.model.connection.ConnectionProperties;
import com.github.dangelcrack.utils.XMLManager;

public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3307","pokedex","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
