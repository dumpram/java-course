package hr.fer.zemris.java.filechecking.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.filechecking.FCFileVerifier.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker.FCProgramChecker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CompleteFunctionalityTest {

        @Test
        public void OfficialDemoTest() {
                String program = HelpClass.ucitaj("provjere/provjere.txt"); // učitajprogram
                                                                            // iz
                // datoteke
                File file = new File("zipovi/0012345678-DZ1.zip");
                String fileName = "0012345678-DZ1.zip"; // definiraj stvarno ime
                                                        // arhive
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);

                assertFalse(verifier.hasErrors());
        }

        @Test
        public void TestSomeHomeworkTest() {
                String program = HelpClass.ucitaj("provjere/provjere2.txt");
                File file = new File("zipovi/HW07-0036470555.zip");
                String fileName = "0036470555-DZ1.zip"; // definiraj stvarno ime
                // arhive
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);

                assertFalse(verifier.hasErrors());
        }

        @Test
        public void TestSomeHomeworkAndFailTest() {
                String program = HelpClass.ucitaj("provjere/provjere3.txt");
                File file = new File("zipovi/HW07-0036470555.zip");
                String fileName = "0036470555-DZ1.zip"; // definiraj stvarno ime
                // arhive
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);

                assertTrue(verifier.hasErrors());
                /* error jer postoji src direktorij, pogledaj provjere3.txt */
        }

        @Test
        public void TestSomeHomeworkAndFailAgainTest() {
                String program = HelpClass.ucitaj("provjere/provjere4.txt");
                File file = new File("zipovi/HW07-0036470555.zip");
                String fileName = "0036470555-DZ1.zip"; // definiraj stvarno ime
                // arhive
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);

                assertFalse(verifier.hasErrors());
                /* pogledati provjere4.txt */
        }

        @Test
        public void Homework07FailonNameTest() {
                String program = "filename \"HW07-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("0012345678-DZ1.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07CorrectNameTest() {
                String program = "filename \"HW07-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07CorrectNameCaseInsensitiveTest() {
                String program = "filename i\"hw07-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07FailNameCaseInsensitiveTest() {
                String program = "filename i\"hw08-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07InvertFailonNameTest() {
                String program = "!filename \"HW07-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("0012345678-DZ1.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07InvertCorrectNameTest() {
                String program = "!filename \"HW07-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07InvertCorrectNameCaseInsensitiveTest() {
                String program = "!filename i\"hw07-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void Homework07InvertFailNameCaseInsensitiveTest() {
                String program = "!filename i\"hw08-0036470555.zip\" {\n"
                                + "exists dir \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void ExistsInvalidFileSpecifierTest() {
                String program = "filename i\"hw07-0036470555.zip\" {\n"
                                + "exists dire \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void InvalidFileFormatNoFormatTest() {
                String program = "format zip \nfilename i\"hw07-0036470555\" {\n"
                                + "exists dire \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void InvalidFileFormatSomeOtherFormatTest() {
                String program = "format zip \nfilename i\"hw07-0036470555.koc\" {\n"
                                + "exists dire \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.koc");
                String fileName = "0012345678-DZ1";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void InvertedInvalidFileFormatNoFormatTest() {
                String program = "!format zip \nfilename i\"hw07-0036470555\" {\n"
                                + "exists dire \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555");
                String fileName = "0012345678-DZ1.zip";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void InvertedInvalidFileFormatSomeOtherFormatTest() {
                String program = "!format zip \nfilename i\"hw07-0036470555.koc\" {\n"
                                + "exists dire \"HW07-0036470555\" \n"
                                + "exists file \"build.xml\"\n" + "}";

                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
        }

        @Test
        public void FailExecutionTest() {
                String program = "fail @\"Joj nekava greska\"";
                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertTrue(verifier.hasErrors());
                /* jedna greska i to ona koju plasira fail */
        }

        @Test
        public void FailInvertedWithBlockExecutionTest() {
                String program = "!fail @\"Joj nekava greska\" {\n"
                                + "def var \"kepeci\"\n" + "}";
                File file = new File("HW07-0036470555.zip");
                String fileName = "0012345678-DZ1";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                assertFalse(verifier.hasErrors());
                /* nema gresaka fail je invertiran */
        }
}
