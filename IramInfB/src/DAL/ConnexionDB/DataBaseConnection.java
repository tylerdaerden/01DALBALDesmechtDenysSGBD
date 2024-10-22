package DAL.ConnexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    // Informations de connexion à PostgreSQL (sans base pour démarrer)
    private static final String URL = "jdbc:postgresql://127.0.0.1/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Test1234";

    // URL pour se connecter à la base de données INFB
    private static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1/INFB";

    // Méthode pour vérifier si la base de données INFB existe, sinon la créer
    public static void initializeDatabase() {
        Connection initialConnection = null;
        Statement statement = null;

        try {
            // Connexion initiale à PostgreSQL (sans base)
            initialConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = initialConnection.createStatement();

            // Création de la base de données INFB si elle n'existe pas
            String sqlCreateDB = "CREATE DATABASE INFB";
            try {
                statement.executeUpdate(sqlCreateDB);
                System.out.println("Base de données INFB créée.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("42P04")) { // Code SQL pour "Database already exists"
                    System.out.println("La base de données INFB existe déjà.");
                } else {
                    System.out.println("Erreur lors de la création de la base de données INFB : " + e.getMessage());
                    return; // Si la base ne peut pas être créée, on arrête ici
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à PostgreSQL : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (initialConnection != null) {
                    initialConnection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    // Méthode pour obtenir une connexion à la base de données INFB
    public static Connection getConnection() throws SQLException {
        // Reconnexion explicite à la base de données INFB
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    // Méthode pour fermer la connexion
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}