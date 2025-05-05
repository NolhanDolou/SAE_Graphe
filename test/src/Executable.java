import java.util.List;

public class Executable {
    public static void main(String[] args) {
        // Charger les films depuis le fichier data_100.txt
        List<Film> films = Lecture.lireFilmsDepuisJSONLigneParLigne("data_100.txt");

        // Afficher tous les films avec leur titre et leur casting
        for (Film film : films) {
            System.out.println("Titre : " + film.title);
            System.out.println("Casting : " + film.cast);
            System.out.println("----------------------");
        }
    }
}
