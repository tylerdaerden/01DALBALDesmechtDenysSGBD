package DAL.Interfaces;

import java.util.List;

public interface ISectionDAO {
    boolean addSection(String nom);
    List<String> getSections();
    boolean updateSection(int id, String newNom);
    boolean deleteSection(int id);
}
