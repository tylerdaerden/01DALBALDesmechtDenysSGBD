package DAL.CoursPersonne;

import DAL.ConnexionDB.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class CoursPersonneDAO {

    // Méthode pour créer la table Cours_Personne si elle n'existe pas
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Cours_Personne (" +
                "id_personne INT REFERENCES Personne(id), " +
                "id_cours INT REFERENCES Cours(id), " +
                "annee INT, " +
                "PRIMARY KEY (id_personne, id_cours, annee))";
        try (Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table Cours_Personne vérifiée/créée.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Cours_Personne : " + e.getMessage());
        }
    }

    // Méthode pour ajouter un cours à une personne pour une année donnée
    public boolean addCoursPersonne(int idPersonne, int idCours, int annee) {
        String sql = "INSERT INTO Cours_Personne (id_personne, id_cours, annee) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idPersonne);
            preparedStatement.setInt(2, idCours);
            preparedStatement.setInt(3, annee);
            preparedStatement.executeUpdate();
            System.out.println("Cours attribué à la personne avec ID : " + idPersonne);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'attribution du cours à la personne : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour obtenir les cours d'une personne
    public List<String> getCoursByPersonne(int idPersonne) {
        List<String> coursList = new ArrayList<>();
        String sql = "SELECT c.nom FROM Cours c JOIN Cours_Personne cp ON c.id = cp.id_cours WHERE cp.id_personne = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idPersonne);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coursList.add(resultSet.getString("nom"));
            }
            System.out.println("Cours de la personne récupérés : " + coursList);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des cours de la personne : " + e.getMessage());
        }
        return coursList;
    }
}
