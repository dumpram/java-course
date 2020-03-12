package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.FCFileVerifier.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker.FCProgramChecker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class for file checking.
 * 
 * @author Ivan Pavić
 * 
 */
public class FCDemo {
        /**
         * 3 arguments are accepted.
         * 
         * @param args
         *                as described in homework
         */
        public static void main(final String[] args) {
                if (args.length != 3) {
                        System.err.println("You have to provide 3 arguments!");
                        System.exit(0);
                }
                String program = ucitaj(args[2]); // učitajprogram iz datoteke
                File file = new File(args[0]);
                String fileName = args[1]; // definiraj stvarno ime arhive
                FCProgramChecker checker = new FCProgramChecker(program);
                if (checker.hasErrors()) {
                        System.out.println("Predani program sadrži pogreške!");
                        for (String error : checker.errors()) {
                                System.out.println(error);
                        }
                        System.out.println("Odustajem od daljnjeg rada.");
                        System.exit(0);
                }

                Map<String, Object> initialData = new HashMap<>();
                initialData.put("jmbag", "0012345678");
                initialData.put("lastName", "Perić");
                initialData.put("firstName", "Pero");
                FCFileVerifier verifier = new FCFileVerifier(file, fileName,
                                program, initialData);
                if (!verifier.hasErrors()) {
                        System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
                } else {
                        System.out.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
                        for (String error : verifier.errors()) {
                                System.out.println(error);
                        }
                }
        }

        private static String ucitaj(final String string) {
                StringBuffer sb = new StringBuffer();
                List<String> lines = null;
                File file = new File(string);
                if (!file.isFile()) {
                        throw new IllegalArgumentException(
                                        "Given file object isn't file!");
                }
                try {
                        lines = Files.readAllLines(Paths.get(string),
                                        StandardCharsets.UTF_8);
                } catch (IOException e) {
                        System.out.println("Unsuccessful reading!");
                        e.printStackTrace();
                }
                for (String i : lines) {
                        if (!i.trim().startsWith("#")) {
                                sb.append(i.trim());
                                sb.append("\n");
                        }
                }
                return sb.toString();
        }
}
