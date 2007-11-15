/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

//import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Kop
 */
public class TorrentFileParser {
    private String fileName;
    private int blockNumber;
    
    public TorrentFileParser(String torrentFileName) {
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            
        }
        catch(SAXException se) {
            System.err.println(se.getMessage());
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
