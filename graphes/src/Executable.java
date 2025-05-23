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
        Graph<String, DefaultEdge> graph = CollaborationGraphBuilder.faireGraphe("./src/data_100.json");
        System.out.println("Nombre de personnes : " + graph.vertexSet().size());
        System.out.println("Nombre de collaborations : " + graph.edgeSet().size()+"\n");

        System.out.println("Nombre de voisins de Ellen Greene : " +CollaborationGraphBuilder.voisins(graph, "Ellen Greene").size()+ CollaborationGraphBuilder.voisins(graph, "Ellen Greene")+"\n");
        System.out.println("Nombre de voisins de 2eme personne : " +  CollaborationGraphBuilder.voisins(graph, "Ryan O'Neal").size() +CollaborationGraphBuilder.voisins(graph, "Ryan O'Neal")+"\n");
        System.out.println("Nombre de voisins communs : " +CollaborationGraphBuilder.voisinsCommuns(graph,"Ellen Greene","Ryan O'Neal").size()+ CollaborationGraphBuilder.voisinsCommuns(graph,"Ellen Greene","Ryan O'Neal")+"\n");
        
        System.out.println("voisins de Ellen Greene x 2 : " +CollaborationGraphBuilder.collaborateurs_proches(graph, "Ellen Greene", 2).size()+ CollaborationGraphBuilder.collaborateurs_proches(graph, "Ellen Greene", 2)+"\n");
        System.out.println("distance Ellen Greene  : " +CollaborationGraphBuilder.collaborateurs_distance(graph, "Ellen Greene", "Ryan O'Neal"));
        System.out.println("distance Ellen Greene  : " +CollaborationGraphBuilder.collaborateurs_distance(graph, "Ellen Greene", "Ellen Greene"));
        System.out.println("distance max Ellen Greene  : " +CollaborationGraphBuilder.centralite_personne(graph, "Ellen Greene"));
        System.out.println("distance max l'autre  : " +CollaborationGraphBuilder.centralite_personne(graph, "Bill Nunn"));

    }
}