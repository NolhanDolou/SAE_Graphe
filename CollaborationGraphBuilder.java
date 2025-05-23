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
                if (film.cast != null) participants.addAll(cleanNames(film.cast));

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

    private static List<String> cleanNames(List<String> rawNames) {
        List<String> result = new ArrayList<>();
        for (String raw : rawNames) {
            if (raw.contains("[[")) {
                String clean = raw.replaceAll("\\[\\[([^\\]|]+)(\\|([^\\]]+))?\\]\\]", "$3").trim();
                if (clean.isEmpty()) {
                    clean = raw.replaceAll("\\[\\[([^\\]|]+)\\]\\]", "$1");
                }
                result.add(clean);
            } else {
                result.add(raw.trim());
            }
        }
        return result;
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

    public static int collaborateurs_distance(Graph<String, DefaultEdge> graphe,String personne, String personne2){
        if(!(graphe.containsVertex(personne))||(!(graphe.containsVertex(personne2)))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }
        Set<String> collaborateurs = new HashSet<>();
        int distance = 0;
        collaborateurs.add(personne);
        while(!(collaborateurs.contains(personne2))){
            Set<String> collaborateurs_directs = new HashSet<>();
            for(String collabo : collaborateurs){
                for(String voisin : voisins(graphe, collabo)){
                    if(!(collaborateurs.contains(voisin))){
                        collaborateurs_directs.add(voisin);
                    }
                }
            }
            collaborateurs.addAll((collaborateurs_directs));
            distance++;
        }
        return distance;
    }

    public static int centralite_personne(Graph<String, DefaultEdge> graphe,String personne){
        if(!(graphe.containsVertex(personne))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }
        int max = 0;
        for(String quelquun : graphe.vertexSet()){
            int distance = collaborateurs_distance(graphe, personne, quelquun);
            if(distance>max){
                max = distance;
            }
        }
        return max;

    }
}