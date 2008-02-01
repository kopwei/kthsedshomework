package assignment1.events;

import tbn.comm.mina.events.MessageEvent;
import tbn.timer.events.TimerExpiredEvent;

public class DelayTimerExpiredEvent extends TimerExpiredEvent {

    private MessageEvent message;

    public DelayTimerExpiredEvent(MessageEvent message) {
        super();
        this.message = message;
    }

    public MessageEvent getMessage() {
        return message;
    }
}
