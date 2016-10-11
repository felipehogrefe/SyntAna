package SyntAna;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import LexAna.LexicalAnalyzer;
import LexAna.Token;

public class Main {

	public static void main(String[] args) throws IOException {
		FileWriter arq = new FileWriter("C:/Users/Felipe/workspace/LexAna/src/saida.txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
		
		LexicalAnalyzer al = new LexicalAnalyzer();
		al.read("C:/Users/Felipe/workspace/LexAna/src/alo.txt");
		
		SyntaticAnalyzer sa = new SyntaticAnalyzer(al);
		
		
		
		
		gravarArq.println("---------ALO---------");
		
		while(al.isOver()){
			Token t = al.nextToken();
			gravarArq.println(t.toString());
		}
	}

}
