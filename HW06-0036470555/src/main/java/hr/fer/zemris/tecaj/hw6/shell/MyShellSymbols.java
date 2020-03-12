package hr.fer.zemris.tecaj.hw6.shell;

public class MyShellSymbols {

    public static String PromptSymbol = ">";
    public static String MoreLineSymbol = "\\";
    public static String MultiLineSymbol = "|";

    public static String getPromptSymbol() {
	return PromptSymbol;
    }

    public static void setPromptSymbol(String promptSymbol) {
	PromptSymbol = promptSymbol;
    }

    public static String getMoreLineSymbol() {
	return MoreLineSymbol;
    }

    public static void setMoreLineSymbol(String moreLineSymbol) {
	MoreLineSymbol = moreLineSymbol;
    }

    public static String getMultiLineSymbol() {
	return MultiLineSymbol;
    }

    public static void setMultiLineSymbol(String multiLineSymbol) {
	MultiLineSymbol = multiLineSymbol;
    }

}
