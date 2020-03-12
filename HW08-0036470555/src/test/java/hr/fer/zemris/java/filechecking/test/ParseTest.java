package hr.fer.zemris.java.filechecking.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.filechecking.FCProgramChecker.FCProgramChecker;

import org.junit.Test;

public class ParseTest {

        @Test
        public void DefParseSuccessfulTest() {
                String program = "def a \"${ivan}:ivan/pavic\"\n";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());
        }

        @Test
        public void DefParseHasErrorsTest() {
                String program = "def \"{$ivan}ivan\"";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertTrue(checker.hasErrors());
        }

        @Test
        public void ExistsParseSuccessfulTest() {
                String program = "exists dir \"ivan\"";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());
        }

        @Test
        public void ExistsParseWithFailMessageTest() {
                String program = "exists dir \"ivan\"@\"FAIL\"";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());
        }

        @Test
        public void ExistsParseInverseTest() {
                String program = "!exists dir \"ivan\"";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());
        }

        @Test
        public void MoreComplexParsingTestWithBlocksTest() {
                String program = "!exists dir \"ivan\" { \n"
                                + "def varA \"varijablaA\"\n" + "}";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());
        }

        @Test
        public void UltimateExampleOfParsingWithPunoStupicaTest() {
                String program = "format zip { \n"
                                + "exists dir \"mama\" @\"Poruka neuspjeha\" { \n"
                                + "def var \"{mamam}\" \n " + "}\n" + "}\n"
                                + "terminate";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertFalse(checker.hasErrors());
        }

        @Test
        public void MoreFailComplexParsingTestWithBlocksTest() {
                String program = "exissts dir \"file\"\n"
                                + "\"filename \"avion\"";
                FCProgramChecker checker = new FCProgramChecker(program);
                assertTrue(checker.hasErrors());
                for (String i : checker.errors()) {
                        System.out.println(i);
                }
        }

}
