package hr.fer.zemris.java.hw13.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Class contains methods for creating sorted or unsorted lists of bands. Also
 * it can create file for results.
 * 
 * @author Ivan Pavić
 * 
 */
public class Glasanje {
	/**
	 * Create files for results tracing.
	 * 
	 * @param defFileName
	 *            definition file name
	 * @param rezFileName
	 *            results file name
	 * @throws IOException
	 *             when error in reading file occurs
	 */
	public static void napraviDatoteku(String defFileName, String rezFileName) throws IOException {
		if (Files.exists(Paths.get(rezFileName))) {
			return;
		}
		List<String> lines = Files.readAllLines(Paths.get(defFileName), StandardCharsets.UTF_8);
		int count = lines.size();
		FileOutputStream out = new FileOutputStream(rezFileName);
		for (int i = 0; i < count; i++) {
			out.write(new String(i + 1 + "\t" + 0).getBytes(StandardCharsets.UTF_8));
			out.write(new String("\r\n").getBytes(StandardCharsets.UTF_8));
		}
		out.close();
	}
	/**
	 * Creates list of bands. Sorted or unsorted depending on flag isSorted.
	 * 
	 * @param isSorted
	 *            flag for sorting
	 * @param defFileName
	 *            definition file name
	 * @param rezFileName
	 *            results file name
	 * @return list of {@link BandInfo}
	 * @throws IOException
	 *             if error while reading or writing occurs
	 */
	public static List<BandInfo> ucitajIzDatoteke(boolean isSorted, String defFileName, String rezFileName)
			throws IOException {
		Map<Integer, Integer> results = new HashMap<>();
		Map<Integer, String> bands = new LinkedHashMap<>();
		List<String> lines = Files.readAllLines(Paths.get(rezFileName), StandardCharsets.UTF_8);
		for (String line : lines) {
			String[] parts = line.split("\t");
			results.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		}
		lines = Files.readAllLines(Paths.get(defFileName), StandardCharsets.UTF_8);
		for (String line : lines) {
			String[] parts = line.split("\t");
			bands.put(Integer.parseInt(parts[0]), parts[1]);
		}
		List<BandInfo> forExport = new ArrayList<>();
		for (Integer key : bands.keySet()) {
			BandInfo current = new BandInfo(key, bands.get(key), results.get(key), lines.get(key - 1).split("\t")[2]);
			forExport.add(current);
		}
		if (isSorted) {
			Collections.sort(forExport);
		}
		return forExport;
	}
}
