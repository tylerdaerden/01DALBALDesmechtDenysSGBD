package DAL.Interfaces;

import java.util.List;

public interface ICoursDAO {
    boolean addCours(String nom, int idSection);
    List<String> getCours();
    boolean updateCours(int id, String newNom, int newSectionId);
    boolean deleteCours(int id);
}
