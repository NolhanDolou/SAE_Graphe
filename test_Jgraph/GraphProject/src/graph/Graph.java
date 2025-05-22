package graph;

import java.util.List;
import java.util.ArrayList;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;
    
    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
    
    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
    
    // Getters et Setters
    public List<Node> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public List<Edge> getEdges() {
        return edges;
    }
    
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
    
    // Méthodes utilitaires
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    
    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }
    
    public Node getNodeById(String id) {
        for (Node node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }
}