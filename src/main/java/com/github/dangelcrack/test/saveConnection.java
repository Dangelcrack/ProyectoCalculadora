package com.github.dangelcrack.test;

import com.github.dangelcrack.model.connection.ConnectionProperties;
import com.github.dangelcrack.utils.XMLManager;

/**
 * This class demonstrates saving connection properties to an XML file.
 * A ConnectionProperties object is created with specific values
 * and then written to the "connection.xml" file using the XMLManager utility class.
 */
public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost", "3307", "pokedex", "root", "root");
        XMLManager.writeXML(c, "connection.xml");
    }
}
