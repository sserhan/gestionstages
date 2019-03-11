package traitement;

import contrat.*;

import java.util.*;
import java.util.stream.Collectors;
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
        return io.getEtudiants().stream()
                .filter(etudiant -> etudiant.getTuteur().getNom().equals(nom))
                .collect(Collectors.toSet());
    }

    /**
     * Renvoie les enseignants qui encadrent des stages d'une compétence donnée en paramètre.
     *
     * @param comp la compétence des stages dont on cherche l'encadrant
     * @return l'ensemble des enseignants qui encadrent des stages de cette compétence
     */
    public Set<contrat.Enseignant> enseignantEncadreCompetence(Competence comp) {

        return io.getEnseignants().stream().filter(enseignant -> !Collections.disjoint(enseignant.getEtudiants(),
                io.getEtudiants().stream().filter(etudiant -> !Collections.disjoint(etudiant.getStages(),
                        io.getStages().stream()
                                .filter(stage -> stage.getCompetence().equals(comp))
                                 .collect(Collectors.toSet())))
                        .collect(Collectors.toSet())))
                .collect(Collectors.toSet());
    }

    /**
     * Qui sont les étudiants n'ayant pas stage pouvant avoir au moins un stage selon leurs compétences
     * et celle du stage ?
     *
     * @return le mapping entre étudiant n'ayant pas de stage et les stages qu'on peut lui proposer,
     * selon ses compétence
     */
    public Map<Etudiant, Set<Stage>> etudiantsMatchStagesNonAffectes() {
        Set<Etudiant> etudiantSansStage = io.getEtudiants().stream().filter(e -> e.getStages().stream().filter(stage -> stage.getStatut().equals(Statut.EN_COURS)).collect(Collectors.toSet()).isEmpty())
                .collect(Collectors.toSet());
        Set<Stage> stageNonAffecte = io.getStages().stream().filter(s -> s.getStatut().equals(Statut.NON_AFFECTE)).collect(Collectors.toSet());
        Map<Etudiant,Set<Stage>> mapEtudiantStage = new HashMap<>();
        etudiantSansStage.forEach(e->mapEtudiantStage.put(e,stageNonAffecte.stream().filter(stage -> e.getCompetences().contains(stage.getCompetence())).collect(Collectors.toSet())));
        return mapEtudiantStage;
    }
}
