package pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import graph.Graph;
import graph.Node;
import graph.Edge;
import java.awt.Color;

import java.io.FileOutputStream;
import java.io.IOException;

public class GraphPdfGenerator {
    
    public static void generatePdf(Graph graph, String outputPath) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();
        
        PdfContentByte cb = writer.getDirectContent();
        
        // Dessiner les arêtes d'abord (pour qu'elles soient derrière les nœuds)
        cb.setColorStroke(Color.BLACK);
        cb.setLineWidth(2f);
        
        for (Edge edge : graph.getEdges()) {
            Node fromNode = graph.getNodeById(edge.getFrom());
            Node toNode = graph.getNodeById(edge.getTo());
            
            if (fromNode != null && toNode != null) {
                // Dessiner la ligne
                cb.moveTo(fromNode.getX(), 800 - fromNode.getY()); // Inverser Y pour PDF
                cb.lineTo(toNode.getX(), 800 - toNode.getY());
                cb.stroke();
            }
        }
        
        // Dessiner les poids des arêtes séparément pour éviter les chevauchements
        cb.setColorFill(Color.RED);
        for (Edge edge : graph.getEdges()) {
            Node fromNode = graph.getNodeById(edge.getFrom());
            Node toNode = graph.getNodeById(edge.getTo());
            
            if (fromNode != null && toNode != null) {
                // Calculer une position décalée pour éviter les chevauchements
                float midX = (fromNode.getX() + toNode.getX()) / 2;
                float midY = 800 - (fromNode.getY() + toNode.getY()) / 2;
                
                // Décaler légèrement selon l'arête pour éviter les chevauchements
                if (edge.getFrom().equals("paris") && edge.getTo().equals("lyon")) {
                    midX += 15;
                    midY -= 10;
                } else if (edge.getFrom().equals("paris") && edge.getTo().equals("bordeaux")) {
                    midX -= 20;
                    midY -= 10;
                } else if (edge.getFrom().equals("lyon") && edge.getTo().equals("bordeaux")) {
                    midY += 15;
                }
                
                cb.beginText();
                cb.setFontAndSize(com.lowagie.text.pdf.BaseFont.createFont(), 10);
                cb.setTextMatrix(midX - 10, midY);
                cb.showText(String.valueOf(edge.getWeight()) + "km");
                cb.endText();
            }
        }
        
        // Dessiner les nœuds
        for (Node node : graph.getNodes()) {
            float x = node.getX();
            float y = 800 - node.getY(); // Inverser Y pour PDF
            float radius = 25;
            
            // Dessiner le cercle avec une couleur de fond claire
            cb.setColorFill(Color.LIGHT_GRAY);
            cb.setColorStroke(Color.BLACK);
            cb.setLineWidth(2f);
            cb.circle(x, y, radius);
            cb.fillStroke();
            
            // Ajouter le nom du nœud en noir
            cb.beginText();
            cb.setColorFill(Color.BLACK);
            cb.setFontAndSize(com.lowagie.text.pdf.BaseFont.createFont(), 11);
            
            // Centrer le texte selon la longueur du nom
            String nodeName = node.getName();
            float textWidth = nodeName.length() * 6; // Approximation de la largeur
            cb.setTextMatrix(x - textWidth/2, y - 4);
            cb.showText(nodeName);
            cb.endText();
        }
        
        // Ajouter un titre
        cb.beginText();
        cb.setColorFill(Color.BLACK);
        cb.setFontAndSize(com.lowagie.text.pdf.BaseFont.createFont(), 16);
        cb.setTextMatrix(200, 750);
        cb.showText("Graphe des villes françaises");
        cb.endText();
        
        document.close();
        System.out.println("PDF généré avec succès : " + outputPath);
    }
}