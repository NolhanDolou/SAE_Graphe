import graph.Graph;
import json.JsonReader;
import pdf.GraphPdfGenerator;

public class Main {
    public static void main(String[] args) {
        try {
            // Chemin vers le fichier JSON
            String jsonFilePath = "data/graph.json";
            
            // Lire le graphe depuis le fichier JSON
            System.out.println("Lecture du fichier JSON...");
            Graph graph = JsonReader.readGraphFromJson(jsonFilePath);
            
            System.out.println("Graphe chargé :");
            System.out.println(graph);
            
            // Générer le PDF
            String outputPath = "output/graph.pdf";
            System.out.println("Génération du PDF...");
            GraphPdfGenerator.generatePdf(graph, outputPath);
            
            System.out.println("Processus terminé avec succès!");
            
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}