import graph.Graph;
import json.JsonReader;
import pdf.GraphPdfGenerator;
import jgrapht.JGraphTManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Chemin vers le fichier JSON
            String jsonFilePath = "data/graph.json";
            
            // Lire le graphe depuis le fichier JSON
            System.out.println("=== LECTURE DU FICHIER JSON ===");
            Graph graph = JsonReader.readGraphFromJson(jsonFilePath);
            
            System.out.println("Graphe personnalisé chargé :");
            System.out.println(graph);
            
            // Convertir vers JGraphT et analyser
            System.out.println("\n=== CONVERSION VERS JGRAPHT ===");
            JGraphTManager jgraphManager = new JGraphTManager();
            jgraphManager.convertFromCustomGraph(graph);
            
            // Afficher les informations du graphe JGraphT
            jgraphManager.displayGraphInfo();
            
            // Analyses avec JGraphT (version simplifiée)
            jgraphManager.analyzeConnectivity();
            jgraphManager.findExtremePaths();
            jgraphManager.calculateTotalDistance();
            
            // Analyses spécifiques par ville
            jgraphManager.findNeighbors("Paris");
            jgraphManager.findNeighbors("Lyon");
            jgraphManager.findNeighbors("Bordeaux");
            
            // Vérifier les connexions directes
            jgraphManager.checkDirectConnection("Paris", "Lyon");
            jgraphManager.checkDirectConnection("Paris", "Bordeaux");
            jgraphManager.checkDirectConnection("Lyon", "Bordeaux");
            
            // Générer le PDF (comme avant)
            System.out.println("\n=== GÉNÉRATION DU PDF ===");
            String outputPath = "output/graph.pdf";
            GraphPdfGenerator.generatePdf(graph, outputPath);
            
            System.out.println("\n=== PROCESSUS TERMINÉ AVEC SUCCÈS! ===");
            
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}