package assignments.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;


import tbn.comm.mina.NodeReference;

public class TopologyDescriptor {

    private int totalNodes;
    private HashMap<Integer, NodeReference> nodesList;
    private HashMap<Integer, LinkDescriptor> linksMap;
    private NodeReference thisNodeRef;

    public TopologyDescriptor(int totalNodes) {
        this.totalNodes = totalNodes;
        this.linksMap = new HashMap<Integer, LinkDescriptor>();
        this.nodesList = new HashMap<Integer, NodeReference>();
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public void setMyNodeRef(NodeReference ref) {
        this.thisNodeRef = ref;
        addLink(ref, ref, 0, 0.0);
    }

    public NodeReference addNode(String nodeId, String Ip, int port)
            throws UnknownHostException {

        InetAddress address = InetAddress.getByName(Ip);

        BigInteger bIntNodeId = new BigInteger(nodeId);

        NodeReference nodeReference = new NodeReference(address, port,
                bIntNodeId);

        nodesList.put(bIntNodeId.intValue(), nodeReference);

        return nodeReference;
    }

    public LinkDescriptor addLink(NodeReference sourceNodeId,
            NodeReference destNodeId, long latency, double loss_rate) {

        LinkDescriptor linkDescriptor = new LinkDescriptor(sourceNodeId,
                destNodeId, latency, loss_rate);

        linksMap.put(destNodeId.getId().intValue(), linkDescriptor);

        return linkDescriptor;
    }

    public Collection<LinkDescriptor> getAllLinks() {
        return linksMap.values();
    }

    public LinkDescriptor getLink(BigInteger nodeId) {
        return linksMap.get(nodeId.intValue());
    }

    public NodeReference getNode(BigInteger nodeId) {
        return nodesList.get(nodeId.intValue());
    }

    /**
     * Gets all the node references of the nodes specified in this Topology
     * Descriptor.
     * 
     * @return a {@link Collection} containing all the {@link NodeReference}
     *         instances
     */
    public Collection<NodeReference> getAllNodes() {
        return nodesList.values();
    }

    /**
     * Gets all the node references of the nodes specified in this Topology
     * Descriptor, except the one of the current node.
     * 
     * @return a {@link Collection} containing all the {@link NodeReference}
     *         instances
     */
    public Collection<NodeReference> getAllOtherNodes() {
        Collection<NodeReference> nodes = new HashSet<NodeReference>(nodesList.values());
        nodes.remove(thisNodeRef);
        return nodes;
    }

    public boolean isNodePresent(int nodeId) {
        return nodesList.containsKey(nodeId);
    }

    public NodeReference getMyNodeRef() {
        return thisNodeRef;
    }
}
