/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontologies;

//import jade.content.onto.BasicOntology;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.ReflectiveIntrospector;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kop
 */
public class AuctionOntology extends Ontology{

    public static final String ONTOLOGY_NAME = "auction-ontology";    
    public static final String ITEM = "Item";
    public static final String ITEMNAME = "Name";
    public static final String MOBILE_PHONE = "MobilePhone";
    public static final String PHONETYPE = "PhoneType";
    public static final String CRY = "Cry";
    public static final String INITPRICE = "InitialPrice";
    public static final String CRYPRICE = "CryPrice";
    
    private static AuctionOntology ontology;
    
    
    private AuctionOntology(Ontology base) {
        super(ONTOLOGY_NAME, base, new ReflectiveIntrospector());
        try {
            //add(new )
            PrimitiveSchema stringSchema = (PrimitiveSchema) getSchema(BasicOntology.STRING);
            PrimitiveSchema floatSchema = (PrimitiveSchema) getSchema(BasicOntology.FLOAT);
            //PrimitiveSchema floatSchema = (PrimitiveSchema) getSchema(BasicOntology.FLOAT);
            
            ConceptSchema itemSchema = new ConceptSchema(ITEM);         
            itemSchema.add(ITEMNAME, stringSchema);
            itemSchema.add(INITPRICE, floatSchema);            
            
            ConceptSchema phoneSchema = new ConceptSchema(MOBILE_PHONE);
            phoneSchema.addSuperSchema(itemSchema);
            
            add(itemSchema, Item.class);
            add(phoneSchema, MobilePhone.class);
            
            PredicateSchema crySchema = new PredicateSchema(CRY);
            crySchema.add(MOBILE_PHONE, phoneSchema);
            crySchema.add(CRYPRICE, floatSchema);
            
            
            
            
        } catch (OntologyException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
    public static AuctionOntology getInstance() {
        // Here I use the double check locking to meet the thread safety
        synchronized (AuctionOntology.class) {
            if (ontology == null) {
                ontology = new AuctionOntology(BasicOntology.getInstance());
            }
        }
        return ontology;
    }
}
