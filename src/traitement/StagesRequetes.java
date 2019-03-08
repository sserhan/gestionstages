package traitement;

import contrat.Competence;
import contrat.Enseignant;
import contrat.Etudiant;
import contrat.Stage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public final class StagesRequetes {

    private final StagesIO io;

    public StagesRequetes(StagesIO io) {
        this.io = io;
    }

    /**
     * Renvoie l'ensemble des étudiants de l'enseignant dont le nom est donné en paramètre.
     *
     * @param nom le nom de l'enseignant
     * @return l'ensemble de ses étudiants
     */
    public Set<contrat.Etudiant> etudiantsDeLEnseignant(String nom) {
        Set<Etudiant> etudiantSet = (Set<Etudiant>) io.getEtudiants();
        etudiantSet.stream().filter(e -> e.getTuteur().getNom().equals(nom));
        return etudiantSet;
    }

    /**
     * Renvoie les enseignants qui encadrent des stages d'une compétence donnée en paramètre.
     *
     * @param comp la compétence des stages dont on cherche l'encadrant
     * @return l'ensemble des enseignants qui encadrent des stages de cette compétence
     */
    public Set<contrat.Enseignant> enseignantEncadreCompetence(Competence comp) {
        Set<Enseignant> enseignantSet = (Set<Enseignant>) io.getEnseignants();
        return (Set<Enseignant>) enseignantSet.stream().filter(enseignant -> enseignant.getEtudiants()
                .equals(io.getEtudiants().stream().filter(etudiant -> etudiant.getStages()
                .equals(io.getStages().stream().filter(stage -> stage.getCompetence().equals(comp))))));
    }

    /**
     * Qui sont les étudiants n'ayant pas stage pouvant avoir au moins un stage selon leurs compétences
     * et celle du stage ?
     *
     * @return le mapping entre étudiant n'ayant pas de stage et les stages qu'on peut lui proposer,
     * selon ses compétence
     */
    public Map<Etudiant, Set<Stage>> etudiantsMatchStagesNonAffectes() {
        return null;
    }
}
