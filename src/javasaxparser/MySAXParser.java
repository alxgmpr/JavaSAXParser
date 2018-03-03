/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 *
 * @author alex
 */
public class MySAXParser {
    
    public String parsedText = "";
    
    public MySAXParser() {
        
    }
    
    public void parseFile(File file) {
        if (file != null) {
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                DefaultHandler handler = new DefaultHandler() {
                    String currentName = null;
                    String currentValue = null;
                    boolean isHead = false;

                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                        currentName = qName;
                        currentValue = "";
                        if(!isHead) {
                            isHead = true;
                            parsedText = parsedText.concat("obj: '" + qName + "':");
                        }
                        for (int i = 0; i < attributes.getLength(); i++) {
                            String aname = attributes.getLocalName(i);
                            String value = attributes.getValue(i);
                            parsedText = parsedText.concat("\n\t->obj: '" + qName + "' attr: '" + aname + "' val: '" + value + "'");
                        }

                    }

                    @Override
                    public void endElement(String uri, String localName, String qName) throws SAXException { 
                        if(currentName.equals(qName)) {
                            parsedText = parsedText.concat("\n\t\t-> var: '" + qName + "' val: '" + currentValue + "'");
                        }
                    }

                    @Override
                    public void characters(char ch[], int start, int length) throws SAXException {
                        currentValue = new String(ch, start, length) ;
                    }
                };

                saxParser.parse(file.getAbsoluteFile(), handler);
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(MySAXParser.class.getName()).log(Level.SEVERE, null, ex);
                showFileError("Encountered an exception while parsing file");
            }
        } else {
            showFileError("Could't parse file because it was null");
        }
        
    }
    
    public String getParsedText() {
        if (this.parsedText != null) {
            return this.parsedText;
        } else {
            showFileError("Couldn't return parsed text because it was null");
            return "";
        }
    }
    
    private static void showFileError(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Houston we have a problem");
        alert.setHeaderText("File Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
