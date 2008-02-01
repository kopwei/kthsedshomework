package components;

import java.util.Random;

import org.apache.log4j.Logger;

import tbn.api.Component;
import tbn.comm.mina.MessageHandler;
import tbn.comm.mina.events.MessageEvent;
import assignment1.events.InitEvent;
import assignments.util.LinkDescriptor;
import assignments.util.TopologyDescriptor;

public class DropComponent {

    private static Logger log = Logger.getLogger(DropComponent.class);
    private Component component;
    private TopologyDescriptor topologyDescriptor;
    private MessageHandler messageHandler;
    private Random random;

    public DropComponent(Component component) {
        this.component = component;
        messageHandler = new MessageHandler(this.component);
        random = new Random(System.currentTimeMillis());
    }

    public void handleInitEvent(InitEvent event) {
        this.topologyDescriptor = event.getTopologyDescriptor();
    }

    public void handleMessageEvent(MessageEvent event) {
        LinkDescriptor linkDescriptor = topologyDescriptor.getLink(event.getDestination().getId());

        if (linkDescriptor == null) {
            log.info("I have no link to destination: " + event.getDestination());
            return;
        }
        log.info("Link to " + event.getDestination().getId() + " has loss rate of: " + linkDescriptor.getLossRate() + " msg=" + event);

        double randNum = random.nextDouble();
        if (randNum <= 1.0 - linkDescriptor.getLossRate()) {
            messageHandler.send(event, event.getDestination());
        } else {
            log.info("Message " + event.getClass().getSimpleName() + " to " + event.getDestination().getId() + " dropped");
        }
    }
}
