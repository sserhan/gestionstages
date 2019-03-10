package traitement;

import contrat.Competence;
import contrat.Enseignant;
import contrat.Etudiant;
import contrat.Stage;
import org.testng.annotations.*;

import static org.testng.Assert.*;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class StagesRequetesTest {

    private static final String ETU_FILE = "etudiants.txt";
    private static final String STAGES_FILE = "stages.txt";
    private static final String DONNEES = "donnees";
    private StagesRequetes requetes;
    private StagesIO io;

    @DataProvider(name = "enseignantEtudiantData")
    public Object [][] createEnseigantEtudiantData() {
        return new Object[][] {
                {"Tuteur Alpha", io.getEtudiants().stream().filter(e -> e.getNom().matches("Julien PETIT")).collect(Collectors.toSet()) },
                {"Tuteur Beta", io.getEtudiants().stream().filter(e -> e.getNom().matches("Frank RITOUT|Sara MAKTOU"))
                                                        .collect(Collectors.toSet())},
                {"Tuteur Gamma", io.getEtudiants().stream().filter(e -> e.getNom().matches("Lina SOULIER")).collect(Collectors.toSet())}
        };
    }

    @DataProvider(name="competenceEnseignantData")
    public Object [][] createCompetenceEnseignantData(){
        return new Object[][] {
                {Competence.WEB, io.getEnseignants().stream().filter(e->e.getNom().matches("Tuteur Beta|Tuteur Alpha"))
                                                        .collect(Collectors.toSet())},
                {Competence.AMOA,io.getEnseignants().stream().filter(e->e.getNom().matches("Tuteur Beta"))
                                                        .collect(Collectors.toSet())},
                {Competence.AMOE,new HashSet<Enseignant>()},
                {Competence.DONNEES,new HashSet<Enseignant>()},
                {Competence.MOBILE,new HashSet<Enseignant>()},
                {Competence.SECURITE,new HashSet<Enseignant>()},
                {Competence.SYSADMIN,new HashSet<Enseignant>()}
        };
    }

/*    @DataProvider(name="etudiantMatchStageNonAffecteData")
    public Object[] createEtudiantMatchStageNonAffecteData(){
        Map<Object, Object> map = new HashMap<>();
        map.put(io.getEtudiants().get(3),io.getStages().stream().filter(s->s.getIdentifiant().matches("S267|S347|S361")).collect(Collectors.toSet()));
        map.put(io.getEtudiants().get(4),io.getStages().stream().filter(s->s.getIdentifiant().matches("S267")).collect(Collectors.toSet()));
        return new Object[]{map};
    }*/

    @BeforeClass
    public void setUp() throws IOException {
        io = new StagesIO(Paths.get(DONNEES, ETU_FILE), Paths.get(DONNEES, STAGES_FILE));
        io.chargerDonnees();
        requetes = new StagesRequetes(io);
    }

    @AfterClass
    public void tearDown() {
        io=null;
        requetes=null;
    }

    @Test(dataProvider="enseignantEtudiantData")
    public void testEtudiantsDeLEnseignant(String nom, Set<Etudiant> etudiantSet) {
        assertEquals(requetes.etudiantsDeLEnseignant(nom),etudiantSet);
    }

    @Test(dataProvider = "competenceEnseignantData")
    public void testEnseignantEncadreCompetence(Competence competence,Set<Enseignant> enseignantSet) {
        assertEquals(requetes.enseignantEncadreCompetence(competence),enseignantSet);
    }

    @Test
    public void testEtudiantsMatchStagesNonAffectes() {
        Map<Etudiant, Set<Stage>> map = new HashMap<>();
        map.put(io.getEtudiants().stream().filter(e->e.getNom().equals("Lina SOULIER")).findFirst().get(),
                io.getStages().stream().filter(s->s.getIdentifiant().matches("S267|S347|S361")).collect(Collectors.toSet()));
        /*map.put(io.getEtudiants().stream().filter(e->e.getNom().equals("Sara MAKTOU")).findFirst().get(),
              io.getStages().stream().filter(s->s.getIdentifiant().matches("S267")).collect(Collectors.toSet()));*/
        assertEquals(requetes.etudiantsMatchStagesNonAffectes(),map);
    }
}