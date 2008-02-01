package assignments.util;

import tbn.comm.mina.NodeReference;

public class LinkDescriptor {

    private NodeReference sourceNode;
    private NodeReference destNode;
    private long latency;
    private double lossRate;

    public LinkDescriptor(NodeReference source, NodeReference dest,
            long latency, double lossRate) {
        this.sourceNode = source;
        this.destNode = dest;
        this.latency = latency;
        this.lossRate = lossRate;
    }

    public long getLatency() {
        return latency;
    }

    public double getLossRate() {
        return lossRate;
    }

    public NodeReference getSourceNodeId() {
        return sourceNode;
    }

    public NodeReference getDestNode() {
        return destNode;
    }

    @Override
    public String toString() {

        return "Link: SRC=" + sourceNode + ", DST= " + destNode + " ,LATENCY=" + latency + " .LOSS_RATE=" + lossRate;
    }
}
