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

public class Requetes {

    public static Graph<String, DefaultEdge> faireGraphe(String filePath) {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            reader.beginArray();
            while (reader.hasNext()) {
                Film film = gson.fromJson(reader, Film.class);

                Set<String> participants = new HashSet<>();
                if (film.cast != null) participants.addAll(faireNoms(film.cast));

                for (String person : participants) {
                    graph.addVertex(person);
                }

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

    private static List<String> faireNoms(List<String> rawNames) {
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

    /* O(N)*/
    public static Set<String> voisins(Graph<String, DefaultEdge> graphe, String personne){
        if(!(graphe.containsVertex(personne))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }
        Set<String> lesVoisins = new HashSet<>();
        for(DefaultEdge arete : graphe.edgesOf(personne)){
            if(!(graphe.getEdgeSource(arete).equals(personne))){
            lesVoisins.add(graphe.getEdgeSource(arete));}
            if(!(graphe.getEdgeTarget(arete).equals(personne))){
            lesVoisins.add(graphe.getEdgeTarget(arete));}
        }
        return lesVoisins;
    }

    /* O(N²) */
    public static Set<String> voisinsCommuns(Graph<String, DefaultEdge> graphe, String personne,String personne2){
        if(!(graphe.containsVertex(personne))||voisins(graphe, personne).isEmpty()){
            System.out.println(personne + " est un illustre inconnu.");
            return  null;
        }
        if(!((graphe.containsVertex(personne2)))||voisins(graphe, personne2).isEmpty()){
            System.out.println(personne2 + " est un illustre inconnu.");
            return null;
        }

        Set<String> lesVoisinsCommuns = new HashSet<>();
        for (String voisin : voisins(graphe, personne)){
            if(voisins(graphe, personne2).contains(voisin)){
                lesVoisinsCommuns.add(voisin);
            }
        }
        return lesVoisinsCommuns;
    }
    /* O(N³) */
    public static Set<String> collaborateursProches(Graph<String, DefaultEdge> graphe,String personne,int distance){
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
    /* O(N³) */
    public static int distanceDeuxPersonnes(Graph<String, DefaultEdge> graphe,String personne, String personne2){
        if(!(graphe.containsVertex(personne))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }
        if(!((graphe.containsVertex(personne2)))){
            System.out.println(personne2 + " est un illustre inconnu.");
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

    /* O(n⁴) */
    public static int centralitePersonne(Graph<String, DefaultEdge> graphe,String personne){
        if(!(graphe.containsVertex(personne))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }

        int max = 0;
        for(String quelquun : graphe.vertexSet()){
            int distance = distanceDeuxPersonnes(graphe, personne, quelquun);
            if(distance>max){
                max = distance;
            }
        }
        return max;
    }
    /* O(N⁵) */
    public static String centreGraphe(Graph<String, DefaultEdge> graphe){
        String centre = null;
        Integer min = null;
        for(String quelquun : graphe.vertexSet()){
            Integer distance = centralitePersonne(graphe, quelquun);
            if(min== null || distance<min ){
                min = distance;
                centre= quelquun;
            }
        }
        return centre;
    }

     /*O(N⁵) */
    public static Integer maxdistanceMax (Graph<String, DefaultEdge> graphe){
        Integer max = null;
        for(String quelquun : graphe.vertexSet()){
            for(String autre : graphe.vertexSet()){
                if(!(quelquun.equals(autre))){
                    Integer distance = distanceDeuxPersonnes(graphe, quelquun,autre);
                    if(max== null || max<distance){
                        max = distance;
                    }}
        }}
        return max;
    }

    /* O(N⁴) */
    public static double distanceMoyenne(Graph<String, DefaultEdge> graphe,String personne){
        if(!(graphe.containsVertex(personne))){
            System.out.println(personne + " est un illustre inconnu.");
            System.exit(0);
        }

        int somme = 0;
        for(String qqun : graphe.vertexSet()){
            if(!(personne.equals(qqun))){
            somme+= distanceDeuxPersonnes(graphe, personne, qqun);}
    }
    return somme/graphe.vertexSet().size();
    }

    /* O(N⁵) */
    public static String minDistanceMoyenne(Graph<String, DefaultEdge> graphe){
        Double min = null;
        String persMin = null;
        for(String quelquun : graphe.vertexSet()){
            Double distance = distanceMoyenne(graphe, quelquun);
            if(min== null || distance<min){
                min = distance;
                persMin = quelquun;
            }
        }
        return persMin;
    }
}