package model;

import contrat.Entreprise;
import contrat.Etudiant;
import contrat.*;

public final class Stage implements contrat.Stage {
    private final String identifiant;
    private final String titre;
    private final Competence competence;
    private final Niveau niveau;
    private final contrat.Entreprise entreprise;
    private contrat.Etudiant etudiant;
    private Statut statut;

    public Stage(String identifiant, String titre, Competence competence, Niveau niveau,
                 contrat.Entreprise entreprise) {
        this.identifiant = identifiant;
        this.titre = titre;
        this.competence = competence;
        this.niveau = niveau;
        this.entreprise = entreprise;
    }


    @Override
    public String getIdentifiant() {
        return identifiant;
    }

    @Override
    public Etudiant getEtudiant() {
        return etudiant;
    }

    @Override
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public String getTitre() {
        return titre;
    }

    @Override
    public Competence getCompetence() {
        return competence;
    }

    @Override
    public Niveau getNiveau() {
        return niveau;
    }

    @Override
    public Entreprise getEntreprise() {
        return entreprise;
    }

    @Override
    public Statut getStatut() {
        return statut;
    }

    @Override
    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "identifiant='" + identifiant + '\'' +
                ", titre='" + titre + '\'' +
                ", competence=" + competence +
                ", niveau=" + niveau +
                ", entreprise=" + entreprise +
                ", etudiant=" + etudiant +
                ", statut=" + statut +
                "}\n";
    }
}
