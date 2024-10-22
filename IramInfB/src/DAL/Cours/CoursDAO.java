package DAL.Cours;

import DAL.ConnexionDB.DataBaseConnection;
import DAL.Interfaces.ICoursDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class CoursDAO implements ICoursDAO {

    // Méthode pour créer la table Cours si elle n'existe pas
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Cours (" +
                "id SERIAL PRIMARY KEY, " +
                "id_section INT REFERENCES Section(id), " +
                "nom VARCHAR(30) UNIQUE)";
        try (Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table Cours vérifiée/créée.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Cours : " + e.getMessage());
        }
    }

    @Override
    public boolean addCours(String nom, int idSection) {
        String sql = "INSERT INTO Cours (nom, id_section) VALUES (?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setInt(2, idSection);
            preparedStatement.executeUpdate();
            System.out.println("Cours ajouté : " + nom);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du cours : " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getCours() {
        List<String> coursList = new ArrayList<>();
        String sql = "SELECT nom FROM Cours";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                coursList.add(resultSet.getString("nom"));
            }
            System.out.println("Cours récupérés : " + coursList);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des cours : " + e.getMessage());
        }
        return coursList;
    }

    @Override
    public boolean updateCours(int id, String newNom, int newSectionId) {
        String sql = "UPDATE Cours SET nom = ?, id_section = ? WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newNom);
            preparedStatement.setInt(2, newSectionId);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Cours mis à jour : " + newNom);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du cours : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCours(int id) {
        String sql = "DELETE FROM Cours WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Cours supprimé avec ID : " + id);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du cours : " + e.getMessage());
            return false;
        }
    }
}
