package assignment1.events;

import tbn.api.Event;
import assignments.util.TopologyDescriptor;

public class InitEvent implements Event {

	private TopologyDescriptor topologyDescriptor;

	public InitEvent(TopologyDescriptor topologyDescriptor) {
		this.topologyDescriptor = topologyDescriptor;
	}

	public TopologyDescriptor getTopologyDescriptor() {
		return topologyDescriptor;
	}
}
