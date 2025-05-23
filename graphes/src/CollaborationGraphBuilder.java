//javac -d bin -cp "lib/*:src" src/*.java
//java -cp "bin:lib/*" Executable 


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CollaborationGraphBuilder {

    public static Graph<String, DefaultEdge> faireGraphe(String filePath) {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            reader.beginArray();
            while (reader.hasNext()) {
                Film film = gson.fromJson(reader, Film.class);

                Set<String> participants = new HashSet<>();
                if (film.cast != null) participants.addAll(film.cast);
                ////if (film.directors != null) participants.addAll(cleanNames(film.directors));
                ////if (film.producers != null) participants.addAll(cleanNames(film.producers));

                for (String person : participants) {
                    graph.addVertex(person);
                }

                // Connect all participants to each other (clique)
                List<String> participantList = new ArrayList<>(participants);
                for (int i = 0; i < participantList.size(); i++) {
                    for (int j = i + 1; j < participantList.size(); j++) {
                        graph.addEdge(participantList.get(i), participantList.get(j));
                    }
                }
            }
            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(graph.vertexSet());
        return graph;
    }


    public static Set<String> voisins(Graph<String, DefaultEdge> graphe, String personne){
        Set<String> lesVoisins = new HashSet<>();
        for(DefaultEdge arete : graphe.edgesOf(personne)){
            if(!(graphe.getEdgeSource(arete).equals(personne))){
            lesVoisins.add(graphe.getEdgeSource(arete));}
            if(!(graphe.getEdgeTarget(arete).equals(personne))){
            lesVoisins.add(graphe.getEdgeTarget(arete));}
        }
        return lesVoisins;
    }

    public static Set<String> voisinsCommuns(Graph<String, DefaultEdge> graphe, String personne,String personne2){
        Set<String> lesVoisinsCommuns = new HashSet<>();
        lesVoisinsCommuns.addAll(voisins(graphe, personne));
        lesVoisinsCommuns.addAll(voisins(graphe, personne2));
        return lesVoisinsCommuns;
    }

    /*  SAE Exploration algorithmique d'un problème

    Voici l'algorithme permettant d'obtenir l'ensemble des collaborateurs à distance k d'un acteur ou d'une actrice. A vous de l'implémenter en JAVA

    Algo collaborateurs_proches(G,u,k):
        """Algorithme renvoyant l'ensemble des acteurs à distance au plus k de l'acteur u dans le graphe G. La fonction renvoie None si u est absent du graphe.
        
        Parametres:
        G: le graphe
        u: le sommet de départ
        k: la distance depuis u
    """
    si u n'est pas un sommet de G:
        afficher u+"est un illustre inconnu"
        fin de l'algorithme

    collaborateurs = Ensemble vide
    Ajouter u à l'ensemble des collaborateurs
    pour tout i allant de 1 à k:
        collaborateurs_directs = Ensemble Vide
        Pour tout collaborateur c dans l'ensemble des collaborateurs
            Pour tout voisin v de c:
                si v n'est pas dans l'ensemble des collaborateurs:
                    Ajouter v à l'ensemble des collaborateurs_directs

        Remplacer collaborateurs par l'union des collaborateurs et collaborateurs_directs
    Renvoyer l'ensemble collaborateur*/

    public static Set<String> collaborateurs_proches(Graph<String, DefaultEdge> graphe,String personne,int distance){
        if(!(graphe.containsVertex(personne))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }
        Set<String> collaborateurs = new HashSet<>();
        collaborateurs.add(personne);
        for(int i=1;i<distance;i++){
            Set<String> collaborateurs_directs = new HashSet<>();
            for(String collabo : collaborateurs){
                for(String voisin : voisins(graphe, collabo)){
                    if(!(collaborateurs.contains(voisin))){
                        collaborateurs_directs.add(voisin);
                    }
                }
            }
            collaborateurs.addAll((collaborateurs_directs));
        }
        return collaborateurs;
    }
}