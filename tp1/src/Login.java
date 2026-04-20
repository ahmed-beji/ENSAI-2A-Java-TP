import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        // 1. Chargement de la base de données
        // J'ai mis un chemin générique, assure-toi qu'il correspond à ton fichier
        HashMap<String, String> userDatabase = loadUserDatabase("data/user_hashpwd.csv");

        // Fail-fast : Si la base est vide (fichier introuvable ou erreur), on stoppe
        // tout.
        if (userDatabase.isEmpty()) {
            System.out.println("Erreur critique : Impossible de charger la base de données. Arrêt du système.");
            return; // Le return vide dans un 'void main' arrête le programme.
        }

        System.out.println("--- Système d'Authentification ---");

        // 2. Le Try-With-Resources (CRITIQUE pour la gestion mémoire)
        // Déclarer le Scanner ici garantit qu'il sera fermé automatiquement à la fin,
        // ce qui règle l'avertissement jaune que tu avais sur ton éditeur.
        try (Scanner scanner = new Scanner(System.in)) {

            // 3. La boucle infinie contrôlée
            while (true) {
                System.out.print("Entrez votre nom d'utilisateur (ou 'exit' pour quitter) : ");
                String username = scanner.nextLine();

                if (username.equalsIgnoreCase("exit")) {
                    System.out.println("Fermeture du programme...");
                    break;
                }

                System.out.print("Entrez votre mot de passe : ");
                String password = scanner.nextLine();

                // 4. Vérification
                // test
                // test 2
                if (userDatabase.containsKey(username)) {
                    String storedHash = userDatabase.get(username);

                    // Note de sécurité : Si la BDD contient des mots de passe hashés,
                    // il faudrait appeler hashPassword(password) ici avant le .equals()
                    if (password.equals(storedHash)) {
                        System.out.println("Connexion réussie ! Accès autorisé pour : " + username);
                        break; // On casse la boucle, l'utilisateur est connecté.
                    } else {
                        System.out.println("Erreur : Identifiant ou mot de passe incorrect.\n");
                    }
                } else {
                    System.out.println("Erreur : Identifiant ou mot de passe incorrect.\n");
                }
            }
        }
    }

    /**
     * Loads a user database from a CSV file.
     * The CSV file is expected to have two columns: username and hashed password.
     * 
     * @param filename The path to the CSV file containing user data.
     * @return A HashMap where keys are usernames and values are hashed passwords.
     * 
     * @throws IOException If an error occurs while reading the file.
     */
    public static HashMap<String, String> loadUserDatabase(String filename) {
        HashMap<String, String> userDatabase = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userDatabase.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return userDatabase;
    }
}
