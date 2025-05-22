package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import graph.Graph;
import graph.Node;
import graph.Edge;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    
    public static Graph readGraphFromJson(String filePath) throws IOException {
        Graph graph = new Graph();
        Gson gson = new Gson();
        
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            // Lecture des nœuds
            JsonArray nodesArray = jsonObject.getAsJsonArray("nodes");
            List<Node> nodes = new ArrayList<>();
            
            for (JsonElement nodeElement : nodesArray) {
                JsonObject nodeObj = nodeElement.getAsJsonObject();
                Node node = new Node(
                    nodeObj.get("id").getAsString(),
                    nodeObj.get("name").getAsString(),
                    nodeObj.get("x").getAsInt(),
                    nodeObj.get("y").getAsInt()
                );
                nodes.add(node);
            }
            
            // Lecture des arêtes
            JsonArray edgesArray = jsonObject.getAsJsonArray("edges");
            List<Edge> edges = new ArrayList<>();
            
            for (JsonElement edgeElement : edgesArray) {
                JsonObject edgeObj = edgeElement.getAsJsonObject();
                Edge edge = new Edge(
                    edgeObj.get("from").getAsString(),
                    edgeObj.get("to").getAsString(),
                    edgeObj.get("weight").getAsInt()
                );
                edges.add(edge);
            }
            
            graph.setNodes(nodes);
            graph.setEdges(edges);
        }
        
        return graph;
    }
}