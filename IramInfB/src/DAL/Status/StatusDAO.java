package DAL.Status;
import DAL.ConnexionDB.DataBaseConnection;
import DAL.Interfaces.IStatusDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class StatusDAO implements IStatusDAO {

    // Méthode pour créer la table Status si elle n'existe pas
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Status (" +
                "id SERIAL PRIMARY KEY, " +
                "status VARCHAR(20) UNIQUE)";
        try (Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table Status vérifiée/créée.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Status : " + e.getMessage());
        }
    }

    @Override
    public boolean addStatus(String status) {
        String sql = "INSERT INTO Status (status) VALUES (?)";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.executeUpdate();
            System.out.println("Status ajouté : " + status);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du statut : " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getStatuses() {
        List<String> statuses = new ArrayList<>();
        String sql = "SELECT status FROM Status";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                statuses.add(resultSet.getString("status"));
            }
            System.out.println("Statuts récupérés : " + statuses);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des statuts : " + e.getMessage());
        }
        return statuses;
    }

    @Override
    public boolean updateStatus(int id, String newStatus) {
        String sql = "UPDATE Status SET status = ? WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Statut mis à jour : " + newStatus);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteStatus(int id) {
        String sql = "DELETE FROM Status WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Statut supprimé avec ID : " + id);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du statut : " + e.getMessage());
            return false;
        }
    }
}
