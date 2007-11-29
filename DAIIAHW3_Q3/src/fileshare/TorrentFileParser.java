/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 *
 * @author Kop
 */
public class TorrentFileParser {
    private String fileName = null;
    private int blockNumber = 0;
    
    public TorrentFileParser(String torrentFileName) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(torrentFileName));
            // Normalize text representation
            doc.getDocumentElement().normalize();
            NodeList listOfFileName = doc.getElementsByTagName("filename");
            int length = listOfFileName.getLength();
            if (length == 1) {
                Node node = listOfFileName.item(0);
                fileName = node.getTextContent();
            }
            NodeList listOfBlockTypes = doc.getElementsByTagName("blocknumber");
            length = listOfBlockTypes.getLength();
            if (length == 1) {
                Node node = listOfBlockTypes.item(0);
                blockNumber = Integer.parseInt(node.getTextContent());
            }
        }
        catch(IOException ie) {
            System.err.println(ie.getMessage());
            System.exit(1);
        }
        catch (ParserConfigurationException pe) {
            System.err.println(pe.getMessage());
            System.exit(1);
        }
        catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        }
        
         
    }
    
    /**
     * 
     * @return the shared file name
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * 
     * @return the shared file block number
     */
    public int getBlockNumber() {
        return blockNumber;
    }

}
