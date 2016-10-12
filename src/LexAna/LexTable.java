package LexAna;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class LexTable {
	public static Map<String, TokenType> lMap = new HashMap<>();
	public static List<Character> sList = new ArrayList<>();//symbolList
	
	static{
		//palavras reservadas da linguagem
		lMap.put("main", TokenType.KEYMAIN);
		lMap.put("if", TokenType.KEYIF);
		lMap.put("private", TokenType.KEYPVT);
		lMap.put("print", TokenType.KEYPRT);
		lMap.put("this", TokenType.KEYTHS);
		lMap.put("boolean", TokenType.KEYLGC);
		lMap.put("double", TokenType.KEYDBL);
		lMap.put("public", TokenType.KEYPBL);
		lMap.put("else", TokenType.KEYELS);
		lMap.put("instanceof", TokenType.KEYIOF);
		lMap.put("return", TokenType.KEYRET);
		lMap.put("int", TokenType.KEYINT);
		lMap.put("static", TokenType.KEYSTC);
		lMap.put("char", TokenType.KEYCHR);
		lMap.put("long", TokenType.KEYLNG);
		lMap.put("class", TokenType.KEYCLS);
		lMap.put("while", TokenType.KEYWHL);
		lMap.put("for", TokenType.KEYFOR);
		lMap.put("new", TokenType.KEYNEW);
		lMap.put("read", TokenType.KEYREA);
		lMap.put("break",TokenType.KEYBRK);

		lMap.put("(", TokenType.SEPAPR);
		lMap.put(")", TokenType.SEPFPR);
		lMap.put("[", TokenType.SEPACL);
		lMap.put("]", TokenType.SEPFCL);
		lMap.put("{", TokenType.SEPACH);
		lMap.put("}", TokenType.SEPFCH);
		lMap.put(",", TokenType.SEPVRG);
		lMap.put(";", TokenType.SEPPEV);
		lMap.put(".", TokenType.SEPPNT);
		lMap.put(":", TokenType.SEPDPT);
		
		//operadores da linguagem
		lMap.put("=", TokenType.OPRATR);
		lMap.put("<", TokenType.OPRMNR);
		lMap.put(">", TokenType.OPRMAR);
		lMap.put("!", TokenType.OPRNOT);
		lMap.put("~", TokenType.OPRNEG);
		lMap.put("?", TokenType.OPRTRI);
		lMap.put(":", TokenType.OPRDPT);
		lMap.put("==", TokenType.OPRIGL);
		lMap.put("<=", TokenType.OPRMEI);
		lMap.put(">=", TokenType.OPRMAI);
		lMap.put("!=", TokenType.OPRDIF);
		lMap.put("&&", TokenType.OPRECD);
		lMap.put("||", TokenType.OPROCD);
		lMap.put("++", TokenType.OPRMMA);
		lMap.put("--", TokenType.OPRMME);
		lMap.put("+", TokenType.OPRADC);
		lMap.put("-", TokenType.OPRMEN);
		lMap.put("*", TokenType.OPRMTL);
		lMap.put("/", TokenType.OPRDIV);
		lMap.put("&", TokenType.OPRE);
		lMap.put("^", TokenType.OPREOR);
		lMap.put("%", TokenType.OPRMOD);
		lMap.put("+=", TokenType.OPRICI);
		lMap.put("-=", TokenType.OPRDCI);
		lMap.put("*=", TokenType.OPRMTI);
		lMap.put("/=", TokenType.OPRDVI);
		lMap.put("|", TokenType.OPRIOR);
		
		//simbolos que compoe a linguagem
		//!|%|^|&|*|(|)|-|+|=|{|}|||~|[|]|\|;|'|:|"|<|>|?|,|.|/|#|@|`|_
		sList.add('<');
		sList.add('>');
		sList.add('+');
		sList.add('-');
		sList.add('*');
		sList.add('!');
		sList.add('%');
		sList.add('^');
		sList.add('&');
		sList.add('(');
		sList.add(')');
		sList.add('=');
		sList.add('{');
		sList.add('}');
		sList.add('|');
		sList.add('~');
		sList.add('[');
		sList.add(']');
		sList.add('\\');
		sList.add(';');
		sList.add('\'');
		sList.add(':');
		sList.add('"');
		sList.add('?');
		sList.add(',');
		sList.add('.');
		sList.add('/');
		sList.add(' ');
		
		
	}
	
}
