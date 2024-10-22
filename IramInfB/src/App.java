import DAL.ConnexionDB.DataBaseConnection;
import DAL.Cours.CoursDAO;
import java.sql.Connection;
import java.sql.SQLException;
import DAL.Section.SectionDAO;
import DAL.Status.StatusDAO;
import DAL.Personne.PersonneDAO;
import DAL.CoursPersonne.CoursPersonneDAO;

public class App {
    public static void main(String[] args) {

        // Initialiser la base de données INFB
        DataBaseConnection.initializeDatabase();

        // Créer les DAO
        SectionDAO sectionDAO = new SectionDAO();
        StatusDAO statusDAO = new StatusDAO();
        CoursDAO coursDAO = new CoursDAO();
        PersonneDAO personneDAO = new PersonneDAO();
        CoursPersonneDAO coursPersonneDAO = new CoursPersonneDAO();

        try {
            // Créer les tables si elles n'existent pas
            sectionDAO.createTableIfNotExists();
            statusDAO.createTableIfNotExists();
            coursDAO.createTableIfNotExists();
            personneDAO.createTableIfNotExists();
            coursPersonneDAO.createTableIfNotExists();

            // Insertion des données dans les tables Sections, Status, et Cours
            // Sections
            sectionDAO.addSection("Informatique de gestion");
            sectionDAO.addSection("Droit");

            // Status
            statusDAO.addStatus("Etudiant");
            statusDAO.addStatus("Charge de cours");
            statusDAO.addStatus("Employe administratif");

            // Cours pour "Informatique de gestion" (ID section = 1)
            coursDAO.addCours("Base des réseaux", 1);
            coursDAO.addCours("Systèmes d’exploitation", 1);
            coursDAO.addCours("Programmation orientée objet", 1);

            // Cours pour "Droit" (ID section = 2)
            coursDAO.addCours("Droit civil", 2);
            coursDAO.addCours("Droit commercial", 2);

            // Insertion des données dans la table Personne
            personneDAO.addPersonne("Gilles", "Poulet", 2);  // Charge de cours
            personneDAO.addPersonne("Emmanuel", "Godissart", 2);  // Charge de cours
            personneDAO.addPersonne("Valeria", "Lai", 1);  // Etudiant
            personneDAO.addPersonne("David", "Mairesse", 3);  // Employe administratif
            personneDAO.addPersonne("Richard", "Durant", 1);  // Etudiant
            personneDAO.addPersonne("Valerie", "Ortiz", 1);  // Etudiant

            // Insertion des données dans Cours_Personne
            // Gilles Poulet (ID personne = 1), Systèmes d’exploitation (ID cours = 2)
            coursPersonneDAO.addCoursPersonne(1, 2, 2024);

            // Emmanuel Godissart (ID personne = 2), Base des réseaux (ID cours = 1)
            coursPersonneDAO.addCoursPersonne(2, 1, 2024);

            // Richard Durant (ID personne = 5), Systèmes d’exploitation (ID cours = 2)
            coursPersonneDAO.addCoursPersonne(5, 2, 2024);

            // Richard Durant (ID personne = 5), Base des réseaux (ID cours = 1)
            coursPersonneDAO.addCoursPersonne(5, 1, 2024);

            // Affichage des résultats (si nécessaire pour vérifier)
            System.out.println("Sections : " + sectionDAO.getSections());
            System.out.println("Statuts : " + statusDAO.getStatuses());
            System.out.println("Cours : " + coursDAO.getCours());
            System.out.println("Personnes : " + personneDAO.getPersonnes());

        } catch (Exception e) {
            System.out.println("Erreur lors de l'insertion des données : " + e.getMessage());
            e.printStackTrace();  // Afficher la pile d'erreurs pour diagnostic
        }
    }
}