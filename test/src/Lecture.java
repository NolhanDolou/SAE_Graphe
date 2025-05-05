import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lecture {
    // Méthode pour lire chaque film depuis le fichier data_100.txt (format JSONL)
    public static List<Film> lireFilmsDepuisJSONLigneParLigne(String nomFichier) {
        List<Film> films = new ArrayList<>();
        Gson gson = new Gson();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                // Chaque ligne est un objet JSON à convertir en Film
                Film film = gson.fromJson(ligne, Film.class);
                films.add(film);
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier JSON.");
        }

        return films;
    }
}
