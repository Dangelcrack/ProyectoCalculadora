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
        // Create a new ConnectionProperties object with specified values
        ConnectionProperties c = new ConnectionProperties("localhost", "3307", "pokedex", "root", "root");

        // Write the connection properties to the "connection.xml" file
        XMLManager.writeXML(c, "connection.xml");
    }
}
