package traitement;

import contrat.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

/**
 * Permet de charger les fichiers contenant les donnees des etudiants et des stages.
 * Parse et donne accès à ces données en mémoire.
 */
public final class StagesIO {

    private final Map<String, contrat.Stage> stagesMap;
    private final Map<String, contrat.Etudiant> etusMap;
    private final Map<Integer, contrat.Classe> classesMap;
    private final Map<String, contrat.Enseignant> enseignantsMap;
    /**
     * Représente le chemin du fichier des données étudiants.
     */
    private Path etuFilePath;
    /**
     * Représente le chemin du fichier des données stages.
     */
    private Path stagesFilePath;


    public StagesIO(Path etuFilePath, Path stagesFilePath) {
        this.etuFilePath = etuFilePath;
        this.stagesFilePath = stagesFilePath;
        this.stagesMap = new HashMap<>();
        this.etusMap = new HashMap<>();
        this.classesMap = new HashMap<>();
        this.enseignantsMap = new HashMap<>();
    }

    /**
     * Charge les données étudiants + stages des fichiers specifiés en paramètres au constructeur de cette classe.
     * @throws IOException
     */
    public void chargerDonnees() throws IOException {

        Scanner readEtu = new Scanner(etuFilePath);
        Scanner readStages = new Scanner(stagesFilePath);

        while(readStages.hasNextLine()){
            List<String> lineStageFile = new ArrayList<>(Arrays.asList(readStages.nextLine().split("#")));

            Entreprise entreprise = new model.Entreprise(lineStageFile.get(4));

            Stage stage = new model.Stage(lineStageFile.get(0), lineStageFile.get(1), Competence.valueOf(lineStageFile.get(2)),
                    Niveau.valueOf(lineStageFile.get(3)), entreprise);

            stage.setStatut(Statut.valueOf(lineStageFile.get(5)));
            stagesMap.put(stage.getIdentifiant(),stage);
        }

        while(readEtu.hasNextLine()){
            List<String> lineEtuFile = new ArrayList<>(Arrays.asList(readEtu.nextLine().split("#")));

            Etudiant etudiant = new model.Etudiant(lineEtuFile.get(0));
            if(etusMap.containsKey(etudiant.getNom()))
                etudiant = etusMap.get(etudiant.getNom());

            Classe classe = new model.Classe(Niveau.valueOf(lineEtuFile.get(1)),Filiere.valueOf(lineEtuFile.get(2)),lineEtuFile.get(3));
            if(classesMap.containsKey(classe.hashCode()))
                classe = classesMap.get(classe.hashCode());

            Enseignant enseignant = new model.Enseignant(lineEtuFile.get(6));
            if(enseignantsMap.containsKey(enseignant.getNom()))
                 enseignant = enseignantsMap.get(enseignant.getNom());

            for(String comp : lineEtuFile.get(4).split(","))
                etudiant.addCompetence(Competence.valueOf(comp));
            etudiant.setTuteur(enseignant);
            Stage stage = stagesMap.get(lineEtuFile.get(5));
            if(stage!=null){
                etudiant.addStage(stage);
                stage.setEtudiant(etudiant);
                stagesMap.put(stage.getIdentifiant(),stage);

            }

            etusMap.put(etudiant.getNom(),etudiant);

            enseignant.addEtudiant(etudiant);
            enseignantsMap.put(enseignant.getNom(),enseignant);

            classe.addEtudiants(etudiant);
            classesMap.put(classe.hashCode(),classe);
        }
    }

    /**
     * Renvoie la liste des classes, triées selon le niveau (L3, M1, M2)
     * @return la liste des classes
     */
    public List<contrat.Classe> getClasses() {
        return classesMap.values().stream()
                .sorted(Comparator.comparing(Classe::getNiveau)).collect(Collectors.toList());
    }

    /**
     * Renvoie la liste des enseignants, triés selon leur nom.
     * @return la liste des enseignants
     */
    public List<contrat.Enseignant> getEnseignants(){
        return enseignantsMap.values().stream()
                .sorted(Comparator.comparing(Enseignant::getNom)).collect(Collectors.toList());
    }

    public Set<contrat.Entreprise> getEntreprises(){
        return null;
    }

    /**
     * Renvoie la liste des étudiants, triés selon leur nom.
     * @return la liste des étudiants
     */
    public List<contrat.Etudiant> getEtudiants(){
        return etusMap.values().stream()
                .sorted(Comparator.comparing(Etudiant::getNom)).collect(Collectors.toList());
    }

    /**
     * Renvoie la liste des stages, triés selon le niveau (L3, M1, M2)
     * @return la liste des stages
     */
    public List<contrat.Stage> getStages(){
        return stagesMap.values().stream()
            .sorted(Comparator.comparing(Stage::getNiveau)).collect(Collectors.toList());
    }

}
