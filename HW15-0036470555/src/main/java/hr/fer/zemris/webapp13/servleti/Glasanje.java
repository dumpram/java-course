package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.model.BandInfo;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

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
	/**
	 * Reads options from definition file. Path is given as argument.
	 * 
	 * @param defFileName
	 *            definition file path
	 * @return list of options or null if error in file format
	 * @throws IOException
	 *             if error while reading occurs
	 */
	public static List<PollOption> ucitajOpcijeIzDatoteke(String defFileName) throws IOException {
		List<PollOption> forExport = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(defFileName), StandardCharsets.UTF_8);
		for (String line : lines) {
			try {
				String[] parts = line.split("\t");
				if (parts.length != 4) {
					throw new IllegalArgumentException();
				}
				PollOption unos = new PollOption();
				unos.setId(Long.parseLong(parts[0]));
				unos.setOptionTitle(parts[1]);
				unos.setOptionLink(parts[2]);
				unos.setPollID(Long.parseLong(parts[3]));
				unos.setVotesCount(0);
				forExport.add(unos);
			} catch (IllegalArgumentException e) {
				throw new IOException();
			}
		}
		return forExport;
	}
	/**
	 * Reads polls from definition file. Path to definition file is provided as
	 * argument.
	 * 
	 * @param pollsFileName
	 *            path to definition file
	 * @return list of polls
	 * @throws IOException
	 *             if error while reading occurs
	 */
	public static List<Poll> ucitajAnketeIzDatoteke(String pollsFileName) throws IOException {
		List<Poll> forExport = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(pollsFileName), StandardCharsets.UTF_8);
		for (String line : lines) {
			String[] parts = line.split("\t");
			if (parts.length != 3) {
				throw new IllegalArgumentException();
			}
			Poll poll = new Poll();
			poll.setId(Long.parseLong(parts[0]));
			poll.setPollTitle(parts[1]);
			poll.setPollMessage(parts[2]);
			forExport.add(poll);
		}
		return forExport;
	}
}
