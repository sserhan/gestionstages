package traitement;

import contrat.Enseignant;
import contrat.Etudiant;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StagesRequetesTest {

    private static final String ETU_FILE = "etudiants.txt";
    private static final String STAGES_FILE = "stages.txt";
    private static final String DONNEES = "donnees";
    private StagesRequetes requetes;
    private StagesIO io;

    @DataProvider(name = "enseignantEtudiantData")
    public Object [][] createEnseigantEtudiantData() throws IOException {
        return new Object[][] {
                {"Tuteur Alpha", io.getEtudiants().stream().filter(e -> e.getNom().equals("Julien PETIT")).collect(Collectors.toSet()) },
                {"Tuteur Beta", io.getEtudiants().stream().filter(e -> e.getNom().equals("Frank RITOUT") || e.getNom().equals("Sara MAKTOU"))
                                                        .collect(Collectors.toSet())},
                {"Tuteur Gamma", io.getEtudiants().stream().filter(e -> e.getNom().equals("Lina SOULIER")).collect(Collectors.toSet())}
        };
    }

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

   /* @Test
    public void testEnseignantEncadreCompetence() {
    }

    @Test
    public void testEtudiantsMatchStagesNonAffectes() {
    }*/
}