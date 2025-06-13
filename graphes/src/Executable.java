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
    //3.1 Echauffement
        //Création du graphe + nb sommets et arêtes
        Graph<String, DefaultEdge> graph = Requetes.faireGraphe("./src/data_100.json");
        System.out.println("Nombre de personnes dans le graphe (sommets) : " + graph.vertexSet().size()+"\n");
        System.out.println("Nombre de collaborations dans le graphe (arêtes): " + graph.edgeSet().size()+"\n");

        System.out.println("max distance deux acteurs"+ Requetes.maxdistanceMax(graph));

    //3.2 Collaborateurs en communs
        //Voisins d'une personne
        System.out.println("Nombre de voisins de Adrian Pasdar + ensemble des voisins: " +Requetes.voisins(graph, "Adrian Pasdar").size()+ Requetes.voisins(graph, "Adrian Pasdar")+"\n");
        //Voisins en communs entre deux acteurs
        System.out.println("Nombre de voisins communs + ensemble des voisins : " +Requetes.voisinsCommuns(graph,"Roger Bart","Adrian Pasdar").size()+ Requetes.voisinsCommuns(graph,"Roger Bart","Adrian Pasdar")+"\n");
        

    //3.3 Collaborateurs proches
        //Implémentation du pseudo-code 
        System.out.println("voisins de Al Pacino distance 2 : " +Requetes.collaborateursProches(graph, "Al Pacino", 2).size()+ Requetes.collaborateursProches(graph, "Al Pacino", 2)+"\n");
        //distance entre deux acteurs
        System.out.println("distance Al Pacino - Adrian Pasdar : " +Requetes.distanceDeuxPersonnes(graph, "Al Pacino", "Adrian Pasdar"));
    
    
    //3.4 Qui est au centre d'Hollywood ?
        // Centralité d'un acteur
        System.out.println("centralité Al Pacino  : " +Requetes.centralitePersonne(graph, "Al Pacino"));
        //Acteur le plus central du graphe
        System.out.println("centre graphe : " + Requetes.centreGraphe(graph));
        
    //3.5 Une petite famille
        //Distance maximum entre toute paire d'acteurs
        System.out.println("max distance deux acteurs"+ Requetes.maxdistanceMax(graph));
        //Distance moyenne entre un acteur et tous les autres
        System.out.println("distance moyenne : " +Requetes.distanceMoyenne(graph, "Al Pacino"));
        //Personne avec la plus petite distance moyenne du graphe
        System.out.println("MIN distance moyenne : " +Requetes.minDistanceMoyenne(graph));

    }
}