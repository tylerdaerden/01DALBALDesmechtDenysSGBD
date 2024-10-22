package DAL.Personne;

import DAL.ConnexionDB.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class PersonneDAO {
    // Méthode pour créer la table Personne si elle n'existe pas
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Personne (" +
                "id SERIAL PRIMARY KEY, " +
                "id_status INT REFERENCES Status(id), " +
                "nom VARCHAR(15), " +
                "prenom VARCHAR(15))";
        try (Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table Personne vérifiée/créée.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Personne : " + e.getMessage());
        }
    }

    // Méthode pour ajouter une nouvelle personne
    public boolean addPersonne(String nom, String prenom, int idStatus) {
        String sql = "INSERT INTO Personne (nom, prenom, id_status) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setInt(3, idStatus);
            preparedStatement.executeUpdate();
            System.out.println("Personne ajoutée : " + nom + " " + prenom);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la personne : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour obtenir toutes les personnes
    public List<String> getPersonnes() {
        List<String> personnes = new ArrayList<>();
        String sql = "SELECT nom, prenom FROM Personne";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String personne = resultSet.getString("nom") + " " + resultSet.getString("prenom");
                personnes.add(personne);
            }
            System.out.println("Personnes récupérées : " + personnes);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }
        return personnes;
    }

    // Méthode pour mettre à jour une personne
    public boolean updatePersonne(int id, String newNom, String newPrenom, int newStatusId) {
        String sql = "UPDATE Personne SET nom = ?, prenom = ?, id_status = ? WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newNom);
            preparedStatement.setString(2, newPrenom);
            preparedStatement.setInt(3, newStatusId);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Personne mise à jour : " + newNom + " " + newPrenom);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la personne : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour supprimer une personne
    public boolean deletePersonne(int id) {
        String sql = "DELETE FROM Personne WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Personne supprimée avec ID : " + id);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la personne : " + e.getMessage());
            return false;
        }
    }
}
