package com.vyfido.mappergen.util;

/**
 * Implement routines for process and manipulate the information exists in an object String
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public final class StringUtils {

  /**
   * Constructor
   */
  private StringUtils() {

  }
  /**
   * count the number the repeats the a letter in the string
   * @param text text to evaluate
   * @param symbol character to evaluate
   * @return a number between zero(0) and the result the search
   */
  public static int countSymbols(String text, char symbol) {
    char[] texts = text.toCharArray();
    int cont = 0;
    for(char txt : texts) {
      if(txt == symbol) {
        cont++;
      }
    }
    return cont;
  }

  /**
   * get the indices for search a symbol in a string
   * @param text text to search
   * @param symbol character to search
   * @return array with position(indices) when occurred the symbol
   */
  public static int[] getSymbols(String text, char symbol) {
    int[] indices = new int[countSymbols(text,symbol)];
    int ind = 0;
    int inc = 0;
    char[] texts = text.toCharArray();
    for(char txt : texts) {
      if(txt == symbol) {
        indices[inc] = ind;
        inc++;
      }
      ind++;
    }
    return indices;
  }

  /**
   * Get the letter in a position defined the text
   * @param text text to evaluate
   * @param pos position to get
   * @return substring 
   */
  public static String getValue(String text, int pos) {
    if(text != null) {

      if(pos<0 || pos>text.length()) {
        return "";
      }

      StringBuilder sb = new StringBuilder("");
      char[] texts = text.toCharArray();
      for(int ind=pos;ind<texts.length;ind++) {
        if(texts[ind] != ' ' && texts[ind] != ',' && texts[ind] != ')' ) {
          sb.append(texts[ind]);
        }else {
          break;
        }
      }
      return sb.toString();
    }else {
      return "";
    }
  }

  /**
   * Put the first letter uppercase the rest to lowercase
   * @param text text to convert
   * @return text modified or null if the input is null
   */
  public static String capitalice(String text) {
    if(text != null) {
      return (text.length()>0) ? 
          (""+Character.toUpperCase(text.charAt(0)))+text.toLowerCase().substring(1,text.length())
          : "";
    }else {
      return null;
    }
  }

  /**
   * Break the string after character or symbol defined
   * @param text text to modified
   * @param symbol break symbol
   * @return substring result the process or blank in case the null
   */
  public static String getLastValue(String text, String symbol) {
    if(text != null && symbol != null) {
      return text.substring(text.lastIndexOf(symbol)+1, text.length());
    }else {
      return  "";
    }
  }

  /**
   * Break the string before character or symbol defined
   * @param text text to modified
   * @param symbol break symbol
   * @return substring result the process or blank in case the null
   */
  public static String getBeforeValue(String text, String symbol) {
    if(text != null && symbol != null) {
      return text.substring(0,text.indexOf(symbol)<=0 ? text.length() : text.indexOf(symbol));
    }else {
      return  "";
    }
  }

  /**
   * Convert a text when the symbol _ is remove and the next letter is changed for a uppercase
   * @param text text to convert
   * @return text changed 
   */
  public static String toCamelCase(String text) {
    if(text == null) {
      return null;
    }else {
      String[] atoms = text.toLowerCase().split("_");
      StringBuilder textcase = new StringBuilder("");
      for(int ind=0;ind<atoms.length;ind++) {
        if(ind == 0) {
          textcase.append(atoms[ind]);
        }else {
          textcase.append(capitalice(atoms[ind]));
        }
      }
      return textcase.toString();
    }
  }

  /**
   * Get the text and include a symbol '_' when occurred a uppercase
   * @param camel text to convert
   * @return text convert
   */
  public static String toNormalCase(String camel) {
    if(camel != null) {
      char[] atoms = camel.toCharArray();
      StringBuilder normalcase = new StringBuilder();
      normalcase.append(atoms[0]);
      for(int ind=1;ind<atoms.length;ind++) {
        if(Character.isUpperCase(atoms[ind])) {
          normalcase.append("_").append(atoms[ind]);
        }else {
          normalcase.append(atoms[ind]);
        }
      }

      return normalcase.toString();
    }else {
      return null;
    }
  }

}
