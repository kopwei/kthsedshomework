package components;

import org.apache.log4j.Logger;

import tbn.api.Component;
import tbn.api.HandlerNotSubscribedException;
import tbn.api.NoSuchMethodException;
import tbn.comm.mina.MessageHandler;
import tbn.comm.mina.events.MessageEvent;
import tbn.timer.TimerHandler;
import assignment1.events.DelayTimerExpiredEvent;
import assignment1.events.InitEvent;
import assignments.util.LinkDescriptor;
import assignments.util.TopologyDescriptor;

public class DelayComponent {

    private static Logger log = Logger.getLogger(DelayComponent.class);
    private TopologyDescriptor topologyDescriptor;
    private TimerHandler timerHandler;
    private MessageHandler messageHandler;

    public DelayComponent(Component component) {
        timerHandler = new TimerHandler(component);
        messageHandler = new MessageHandler(component);
    }

    public void handleInitEvent(InitEvent event) {
        log.debug("INIT Delay Component");
        this.topologyDescriptor = event.getTopologyDescriptor();
    }

    public void handleMessageEvent(MessageEvent event) {

        LinkDescriptor linkDescriptor = topologyDescriptor.getLink(event.getDestination().getId());

        try {

            log.info("Delaying message: " + event//.getClass().getSimpleName()
                    + " with dest: " + event.getDestination().getId() + " of " + linkDescriptor.getLatency() + " secs");

            timerHandler.startTimer(new DelayTimerExpiredEvent(event),
                    "handleDelayTimerExpiredEvent", linkDescriptor.getLatency());

        } catch (HandlerNotSubscribedException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void handleDelayTimerExpiredEvent(DelayTimerExpiredEvent event) {

        MessageEvent message = event.getMessage();

        log.info("Sending message: " + message//.getClass().getSimpleName()
                + " to dest: " + message.getDestination().getId());

        messageHandler.send(message, message.getDestination());
    }
}
