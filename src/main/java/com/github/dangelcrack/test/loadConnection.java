package com.github.dangelcrack.test;

import com.github.dangelcrack.model.connection.ConnectionProperties;
import com.github.dangelcrack.utils.XMLManager;
//testcommit
public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
