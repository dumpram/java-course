package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.comparators.CompositeComparator;
import hr.fer.zemris.java.tecaj.hw5.comparators.DateModifiedComparator;
import hr.fer.zemris.java.tecaj.hw5.comparators.ExecutableComparator;
import hr.fer.zemris.java.tecaj.hw5.comparators.NameComparator;
import hr.fer.zemris.java.tecaj.hw5.comparators.NameLengthComparator;
import hr.fer.zemris.java.tecaj.hw5.comparators.SizeComparator;
import hr.fer.zemris.java.tecaj.hw5.comparators.TypeComparator;
import hr.fer.zemris.java.tecaj.hw5.filters.EFilter;
import hr.fer.zemris.java.tecaj.hw5.filters.FFilter;
import hr.fer.zemris.java.tecaj.hw5.filters.Size1Filter;
import hr.fer.zemris.java.tecaj.hw5.filters.SizeSFilter;

import java.io.File;
import java.io.FileFilter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class FileWork is used for sorting, filtering and printing content of the
 * given directory. It can be easily modified by adding regular expression of
 * new modifier. Also you have to implement suitable comparator or filter.
 *
 * @author Ivan Pavić
 *
 */
public class FileWork {

	/* START OF REGEX */
	/**
	 * Regular expression for {@link SizeSFilter} specifier ( -f:(-)s ).
	 */
	private final String SizeSRegex = "\\s*-f:-?s\\d+\\s*";
	/**
	 * Regular expression for {@link Size1Filter} specifier ( -f:(-)%d ).
	 */
	private final String Size1Regex = "\\s*-f:-?\\d+\\s*";
	/**
	 * Regular expression for {@link EFilter} specifier ( -f:(-)e ).
	 */
	private final String EFilterRegex = "\\s*-f:-?e\\s*";
	/**
	 * Regular expression for {@link FFilter} specifier ( -f:(-)f ).
	 */
	private final String FFilterRegex = "\\s*-f:-?f\\s*";
	/**
	 * Regular expression for SizeSort specifier ( -s:(-)s ).
	 */
	private final String SizeSortRegex = "\\s*-s:-?s\\s*";
	/**
	 * Regular expression for NameSort specifier ( -s:(-)n ).
	 */
	private final String NameSortRegex = "\\s*-s:-?n\\s*";
	/**
	 * Regular expression for DateModified sort specifier ( -s:(-)m ).
	 */
	private final String ModSortRegex = "\\s*-s:-?m\\s*";
	/**
	 * Regular expression for TypeSort specifier ( -s:(-)t ).
	 */
	private final String TypeSortRegex = "\\s*-s:-?t\\s*";
	/**
	 * Regular expression for LengthSort specifier ( -s:(-)l ).
	 */
	private final String LengthSortRegex = "\\s*-s:-?l\\s*";
	/**
	 * Regular expression for ExeSort specifier ( -s:(-)e ).
	 */
	private final String ExeSortRegex = "\\s*-s:-?e\\s*";
	/**
	 * Regular expression for NamePrint specifier ( -w:n ).
	 */
	private final String NamePrintRegex = "\\s*-w:n\\s*";
	/**
	 * Regular expression for TypePrint specifier ( -w:t ).
	 */
	private final String TypePrintRegex = "\\s*-w:t\\s*";
	/**
	 * Regular expression for SizePrint specifier ( -w:s ).
	 */
	private final String SizePrintRegex = "\\s*-w:s\\s*";
	/**
	 * Regular expression for ModifiedPrint specifier ( -w:m ).
	 */
	private final String ModifiedPrintRegex = "\\s*-w:m\\s*";
	/**
	 * Regular expression for HiddenPrint specifier ( -w:h ).
	 */
	private final String HiddenPrintRegex = "\\s*-w:h\\s*";
	/* END OF REGEX */

	/**
	 * Directory in question.
	 */
	private final File dir;
	/**
	 * Directory content.
	 */
	private List<File> content;
	/**
	 * Array of specifiers.
	 */
	private final String[] orders;
	/**
	 * Flag; if true orders contain print specifier.
	 */
	private boolean printSpecFlag = false;

	/**
	 * Constructs FileWork class from input arguments in main program.
	 *
	 * @param args
	 *            given arguments.
	 */
	public FileWork(String[] args) {
		dir = new File(args[0]);
		content = Arrays.asList(dir.listFiles());
		orders = Arrays.copyOfRange(args, 1, args.length);
		go();
	}

	/**
	 * Private method which delegates work to other methods(sorting and
	 * filtering) based on specifiers given.
	 */
	private void go() {
		List<Comparator<File>> fileComparators = new ArrayList<Comparator<File>>();
		for (String i : orders) {
			if (i.matches(FFilterRegex)) {
				content = doFiltering(new FFilter(controlValue(i)));
			} else if (i.matches(EFilterRegex)) {
				content = doFiltering(new EFilter(controlValue(i)));
			} else if (i.matches(Size1Regex)) {
				content = doFiltering(new Size1Filter(sizeArg(i),
						controlValue(i)));
			} else if (i.matches(SizeSRegex)) {
				content = doFiltering(new SizeSFilter(sizeArg(i),
						controlValue(i)));
			} else if (i.matches(SizeSortRegex)) {
				addToList(fileComparators, new SizeComparator(),
						controlValue(i));
			} else if (i.matches(NameSortRegex)) {
				addToList(fileComparators, new NameComparator(),
						controlValue(i));
			} else if (i.matches(ModSortRegex)) {
				addToList(fileComparators, new DateModifiedComparator(),
						controlValue(i));
			} else if (i.matches(TypeSortRegex)) {
				addToList(fileComparators, new TypeComparator(),
						controlValue(i));
			} else if (i.matches(LengthSortRegex)) {
				addToList(fileComparators, new NameLengthComparator(),
						controlValue(i));
			} else if (i.matches(ExeSortRegex)) {
				addToList(fileComparators, new ExecutableComparator(),
						controlValue(i));
			} else if (isPrintSpecifier(i)) {
				printSpecFlag = true;
			} else {
				throw new IllegalArgumentException("Invalid specifier: " + i);
			}
		}
		doSorting(fileComparators);
	}

	/**
	 * Method finds integer value in specifier.
	 *
	 * @param i
	 *            given specifier
	 * @return value of integer value in size specifier.
	 */
	private int sizeArg(String i) {
		String SizeRegex = "\\s*-f:-?s?(\\d+)\\s*";
		Pattern pattern = Pattern.compile(SizeRegex);
		Matcher matcher = pattern.matcher(i);
		matcher.matches();
		return Integer.parseInt(matcher.group(1));
	}

	/**
	 * Returns false if specifier is inverted by '-'.
	 *
	 * @param i
	 *            given specifier.
	 * @return false if specifier is inverted, otherwise true.
	 */
	private boolean controlValue(String i) {
		return !i.trim().substring(1).contains("-");
	}

	/**
	 * Method filters {@link content} depending on input filter.
	 *
	 * @param filter
	 *            input filter
	 * @return new list which is basically filtered directory content.
	 */
	private List<File> doFiltering(FileFilter filter) {
		List<File> forExport = new ArrayList<>();
		for (File i : content) {
			if (filter.accept(i)) {
				forExport.add(i);
			}
		}
		return forExport;
	}

	/**
	 * Method sorts based on given comparator, and control variable which
	 * selects type of sorting.
	 *
	 * @param comparator
	 *            given comparator.
	 * @param control
	 *            control variable(if false reverseCompartor is invoked).
	 */
	private void doSorting(List<Comparator<File>> fileComparators) {
		CompositeComparator allComparators = new CompositeComparator(
				fileComparators);
		Collections.sort(content, allComparators);
	}

	/**
	 * Public method enables formatted printing filtered and sorted directory
	 * content.
	 */
	public void print() {
		if (content.size() == 0) {
			System.out.println("No elements match your request!");
			return;
		}
		int maxName = Collections.max(content, new NameLengthComparator())
				.getName().length();
		int maxSize = Long.toString(
				Collections.max(content, new SizeComparator()).length())
				.length();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatName = "| %-" + maxName + "s ";
		String formatSize = "| %" + maxSize + "s ";
		String border = "+";
		String[] orders = new String[] { "-w:n" };
		if (printSpecFlag) {
			orders = this.orders;
		}
		for (String i : orders) {
			if (i.matches(NamePrintRegex)) {
				border += fillBorder(maxName);
			} else if (i.matches(SizePrintRegex)) {
				border += fillBorder(maxSize);
			} else if (i.matches(HiddenPrintRegex)) {
				border += "---+";
			} else if (i.matches(ModifiedPrintRegex)) {
				border += "---------------------+";
			} else if (i.matches(TypePrintRegex)) {
				border += "---+";
			}
		}
		System.out.println(border);
		for (File file : content) {
			for (String i : orders) {
				if (i.matches(NamePrintRegex)) {
					System.out.format(formatName, file.getName());
				} else if (i.matches(SizePrintRegex)) {
					System.out.format(formatSize, file.length());
				} else if (i.matches(HiddenPrintRegex)) {
					System.out.format("| %s ", printHidden(file.isHidden()));
				} else if (i.matches(ModifiedPrintRegex)) {
					System.out.print("| "
							+ sdf.format(new Date(file.lastModified())) + " ");
				} else if (i.matches(TypePrintRegex)) {
					System.out.format("| %s ", printFormat(file.isFile()));
				}
			}
			System.out.println("|");
		}
		System.out.println(border);
	}

	/**
	 * Method creates string border used in formatted printing.
	 *
	 * @param maxName
	 *            input parameter that sets max size of column.
	 * @return string representing border
	 */
	private String fillBorder(int maxName) {
		String forExport = "";
		maxName += 2;
		while (maxName != 0) {
			maxName--;
			forExport += "-";
		}
		forExport += "+";
		return forExport;
	}

	/**
	 * Returns string representing file format.
	 *
	 * @param file
	 *            true if file, otherwise false
	 * @return string "f" if file, otherwise "d".
	 */
	private String printFormat(boolean file) {
		if (file) {
			return "f";
		}
		return "d";
	}

	/**
	 * Returns string if file is hidden.
	 *
	 * @param hidden
	 *            if true file is hidden, false otherwise.
	 * @return string "h" if file is hidden, "" otherwise.
	 */
	private String printHidden(boolean hidden) {
		if (hidden) {
			return "h";
		}
		return " ";
	}

	/**
	 * Checks if String i is print specifier.
	 *
	 * @param i
	 *            given string
	 * @return true if specifier is print specifier, false otherwise.
	 */
	private boolean isPrintSpecifier(String i) {
		return i.matches(NamePrintRegex) || i.matches(HiddenPrintRegex)
				|| i.matches(TypePrintRegex) || i.matches(ModifiedPrintRegex)
				|| i.matches(SizePrintRegex);
	}

	/**
	 * Adds comparator to fileComparators list of comparators. If control is set
	 * then original comparator will be added, reversed comparator otherwise.
	 *
	 * @param fileComparators
	 *            list of comparators
	 * @param comparator
	 *            comparator in question
	 * @param control
	 *            flag for indicating regular or reverse order
	 */
	private void addToList(List<Comparator<File>> fileComparators,
			Comparator<File> comparator, boolean control) {
		if (control) {
			fileComparators.add(comparator);
		} else {
			fileComparators.add(Collections.reverseOrder(comparator));
		}
	}
}
