package DAL.Interfaces;

import java.util.List;

public interface IStatusDAO {
    boolean addStatus(String status);
    List<String> getStatuses();
    boolean updateStatus(int id, String newStatus);
    boolean deleteStatus(int id);
}
