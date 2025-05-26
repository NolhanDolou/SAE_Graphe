import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.ExportException;
import java.io.FileWriter;

public class Executable{

public static void main(String[] args) {
        Graph<String, DefaultEdge> graph = CollaborationGraphBuilder.faireGraphe("./src/data_1000.json");
        System.out.println("Nombre de personnes : " + graph.vertexSet().size());
        System.out.println("Nombre de collaborations : " + graph.edgeSet().size()+"\n");

        System.out.println("Nombre de voisins de Adrian Pasdar : " +CollaborationGraphBuilder.voisins(graph, "Adrian Pasdar").size()+ CollaborationGraphBuilder.voisins(graph, "Adrian Pasdar")+"\n");
        System.out.println("Nombre de voisins de 2eme personne : " +  CollaborationGraphBuilder.voisins(graph, "Roger Bart").size() +CollaborationGraphBuilder.voisins(graph, "Roger Bart")+"\n");
        System.out.println("Nombre de voisins communs : " +CollaborationGraphBuilder.voisinsCommuns(graph,"Roger Bart","Adrian Pasdar").size()+ CollaborationGraphBuilder.voisinsCommuns(graph,"Al Pacino","Adrian Pasdar")+"\n");
        
        System.out.println("voisins de Al Pacino x 2 : " +CollaborationGraphBuilder.collaborateurs_proches(graph, "Al Pacino", 2).size()+ CollaborationGraphBuilder.collaborateurs_proches(graph, "Al Pacino", 2)+"\n");
        System.out.println("distance Al Pacino 1111 : " +CollaborationGraphBuilder.collaborateurs_distance(graph, "Al Pacino", "Adrian Pasdar"));
        System.out.println("distance Al Pacino  : " +CollaborationGraphBuilder.collaborateurs_distance(graph, "Al Pacino", "Al Pacino"));
        //System.out.println("distance max Al Pacino  : " +CollaborationGraphBuilder.centralite_personne(graph, "Al Pacino"));
        //System.out.println("distance max l'autre  : " +CollaborationGraphBuilder.centralite_personne(graph, "Adrian Pasdar"));
        System.out.println("centre graphe : " + CollaborationGraphBuilder.centre_graphe(graph));
        System.out.println("distance max 2 pers: " +CollaborationGraphBuilder.distance_max(graph, "Mădălina Diana Ghenea", "Renee Olstead"));

    }
}