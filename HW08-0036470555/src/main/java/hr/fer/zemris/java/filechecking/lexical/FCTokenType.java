package hr.fer.zemris.java.filechecking.lexical;

/**
 * Enumeration of tokenTypes.
 * 
 * @author Ivan Pavić
 */
public enum FCTokenType {
    /** Označava da više nema tokena **/
    EOF,
    /** Identifikator **/
    IDENT,
    /** Ključna riječ **/
    KEYWORD,
    /** Dvotočka **/
    COLON,
    /** Navodnik **/
    QUOTE,
    /** Otvorena vitičasta zagrada **/
    OPENED_BRACE,
    /** Zatvorena vitičasta zagrada **/
    CLOSED_BRACE,
    /** Uskilčnik(negator) **/
    NEGATOR,
    /** Znak dolara **/
    DOLLAR,
    /** Definira stazu do neke datoteke **/
    PATH,
    /** Separator kosa crta **/
    SLASH,
    /** String **/
    STRING,
    /** Identifikator supstitucije **/
    MONKEY
}
