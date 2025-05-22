package jgrapht;

import graph.Graph;
import graph.Node;
import graph.Edge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Set;

public class JGraphTManager {
    
    private SimpleWeightedGraph<String, DefaultWeightedEdge> jgraph;
    
    public JGraphTManager() {
        // Créer un graphe pondéré non-orienté
        this.jgraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    }
    
    /**
     * Convertit notre graphe personnalisé vers JGraphT
     */
    public void convertFromCustomGraph(Graph customGraph) {
        // Ajouter tous les nœuds
        for (Node node : customGraph.getNodes()) {
            jgraph.addVertex(node.getName());
        }
        
        // Ajouter toutes les arêtes avec leurs poids
        for (Edge edge : customGraph.getEdges()) {
            Node fromNode = customGraph.getNodeById(edge.getFrom());
            Node toNode = customGraph.getNodeById(edge.getTo());
            
            if (fromNode != null && toNode != null) {
                DefaultWeightedEdge graphEdge = jgraph.addEdge(
                    fromNode.getName(), 
                    toNode.getName()
                );
                if (graphEdge != null) {
                    jgraph.setEdgeWeight(graphEdge, edge.getWeight());
                }
            }
        }
        
        System.out.println("Graphe JGraphT créé avec succès!");
        System.out.println("Nombre de vertices: " + jgraph.vertexSet().size());
        System.out.println("Nombre d'arêtes: " + jgraph.edgeSet().size());
    }
    
    /**
     * Affiche les informations du graphe
     */
    public void displayGraphInfo() {
        System.out.println("\n=== INFORMATIONS DU GRAPHE JGRAPHT ===");
        System.out.println("Vertices: " + jgraph.vertexSet());
        
        System.out.println("\nArêtes et poids:");
        for (DefaultWeightedEdge edge : jgraph.edgeSet()) {
            String source = jgraph.getEdgeSource(edge);
            String target = jgraph.getEdgeTarget(edge);
            double weight = jgraph.getEdgeWeight(edge);
            System.out.println(source + " <-> " + target + " (poids: " + weight + " km)");
        }
    }
    
    /**
     * Analyse de connectivité basique
     */
    public void analyzeConnectivity() {
        System.out.println("\n=== ANALYSE DE CONNECTIVITÉ ===");
        
        int numVertices = jgraph.vertexSet().size();
        int numEdges = jgraph.edgeSet().size();
        
        System.out.println("Nombre de vertices: " + numVertices);
        System.out.println("Nombre d'arêtes: " + numEdges);
        
        // Calculer la densité du graphe
        if (numVertices > 1) {
            double maxPossibleEdges = (numVertices * (numVertices - 1)) / 2.0;
            double density = numEdges / maxPossibleEdges;
            System.out.println("Densité du graphe: " + String.format("%.2f", density));
        }
        
        // Afficher le degré de chaque vertex
        System.out.println("\nDegré de chaque ville:");
        for (String vertex : jgraph.vertexSet()) {
            Set<DefaultWeightedEdge> edges = jgraph.edgesOf(vertex);
            System.out.println(vertex + ": " + edges.size() + " connexions");
        }
    }
    
    /**
     * Trouve les voisins directs d'une ville
     */
    public void findNeighbors(String cityName) {
        System.out.println("\n=== VOISINS DE " + cityName.toUpperCase() + " ===");
        
        if (!jgraph.containsVertex(cityName)) {
            System.out.println("La ville " + cityName + " n'existe pas dans le graphe!");
            return;
        }
        
        Set<DefaultWeightedEdge> edges = jgraph.edgesOf(cityName);
        System.out.println("Villes directement connectées à " + cityName + ":");
        
        for (DefaultWeightedEdge edge : edges) {
            String source = jgraph.getEdgeSource(edge);
            String target = jgraph.getEdgeTarget(edge);
            double weight = jgraph.getEdgeWeight(edge);
            
            String neighbor = source.equals(cityName) ? target : source;
            System.out.println("  -> " + neighbor + " (distance: " + weight + " km)");
        }
    }
    
    /**
     * Trouve l'arête la plus courte et la plus longue
     */
    public void findExtremePaths() {
        System.out.println("\n=== DISTANCES EXTRÊMES ===");
        
        if (jgraph.edgeSet().isEmpty()) {
            System.out.println("Aucune arête dans le graphe!");
            return;
        }
        
        DefaultWeightedEdge shortestEdge = null;
        DefaultWeightedEdge longestEdge = null;
        double minWeight = Double.MAX_VALUE;
        double maxWeight = Double.MIN_VALUE;
        
        for (DefaultWeightedEdge edge : jgraph.edgeSet()) {
            double weight = jgraph.getEdgeWeight(edge);
            
            if (weight < minWeight) {
                minWeight = weight;
                shortestEdge = edge;
            }
            
            if (weight > maxWeight) {
                maxWeight = weight;
                longestEdge = edge;
            }
        }
        
        if (shortestEdge != null) {
            String source = jgraph.getEdgeSource(shortestEdge);
            String target = jgraph.getEdgeTarget(shortestEdge);
            System.out.println("Distance la plus courte: " + source + " <-> " + target + 
                             " (" + minWeight + " km)");
        }
        
        if (longestEdge != null) {
            String source = jgraph.getEdgeSource(longestEdge);
            String target = jgraph.getEdgeTarget(longestEdge);
            System.out.println("Distance la plus longue: " + source + " <-> " + target + 
                             " (" + maxWeight + " km)");
        }
    }
    
    /**
     * Calcule la distance totale de toutes les arêtes
     */
    public void calculateTotalDistance() {
        System.out.println("\n=== DISTANCE TOTALE ===");
        
        double totalDistance = 0;
        for (DefaultWeightedEdge edge : jgraph.edgeSet()) {
            totalDistance += jgraph.getEdgeWeight(edge);
        }
        
        System.out.println("Distance totale de toutes les routes: " + totalDistance + " km");
        System.out.println("Distance moyenne par route: " + 
                          String.format("%.1f", totalDistance / jgraph.edgeSet().size()) + " km");
    }
    
    /**
     * Vérifie si deux villes sont directement connectées
     */
    public void checkDirectConnection(String city1, String city2) {
        System.out.println("\n=== CONNEXION DIRECTE ===");
        
        if (!jgraph.containsVertex(city1) || !jgraph.containsVertex(city2)) {
            System.out.println("Une des villes n'existe pas dans le graphe!");
            return;
        }
        
        DefaultWeightedEdge edge = jgraph.getEdge(city1, city2);
        if (edge != null) {
            double weight = jgraph.getEdgeWeight(edge);
            System.out.println(city1 + " et " + city2 + " sont directement connectées.");
            System.out.println("Distance: " + weight + " km");
        } else {
            System.out.println(city1 + " et " + city2 + " ne sont PAS directement connectées.");
        }
    }
    
    // Getter pour accéder au graphe JGraphT si nécessaire
    public SimpleWeightedGraph<String, DefaultWeightedEdge> getJGraph() {
        return jgraph;
    }
}