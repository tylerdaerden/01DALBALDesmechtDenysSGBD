package DAL.Section;
import DAL.ConnexionDB.DataBaseConnection;
import DAL.Interfaces.ISectionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO implements ISectionDAO {
    // Méthode pour créer la table Section si elle n'existe pas
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Section (" +
                "id SERIAL PRIMARY KEY, " +
                "nom VARCHAR(30) UNIQUE)";
        try (Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table Section vérifiée/créée.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Section : " + e.getMessage());
        }
    }

    @Override
    public boolean addSection(String nom) {
        String sql = "INSERT INTO Section (nom) VALUES (?)";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nom);
            preparedStatement.executeUpdate();
            System.out.println("Section ajoutée : " + nom);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la section : " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getSections() {
        List<String> sections = new ArrayList<>();
        String sql = "SELECT nom FROM Section";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                sections.add(resultSet.getString("nom"));
            }
            System.out.println("Sections récupérées : " + sections);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des sections : " + e.getMessage());
        }
        return sections;
    }

    @Override
    public boolean updateSection(int id, String newNom) {
        String sql = "UPDATE Section SET nom = ? WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newNom);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Section mise à jour : " + newNom);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la section : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSection(int id) {
        String sql = "DELETE FROM Section WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Section supprimée avec ID : " + id);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la section : " + e.getMessage());
            return false;
        }
    }
}
