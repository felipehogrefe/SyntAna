package SyntAna;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import LexAna.LexicalAnalyzer;
import LexAna.Token;

public class Main {

	public static void main(String[] args) throws IOException {
		FileWriter arq = new FileWriter("C:/Users/Felipe/workspace/SyntAna/src/saida.txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
		
		LexicalAnalyzer la = new LexicalAnalyzer();
		la.read("C:/Users/Felipe/workspace/SyntAna/src/alo.txt");
		
//		la.printFile();
		
		SyntaticAnalyzer sa = new SyntaticAnalyzer(la);
		
		sa.start();
		

		gravarArq.println("---------ALO---------");
		
//		while(la.isOver()){
//			Token t = la.nextToken();
//			gravarArq.println(t.toString());
//		}
		arq.close();
		System.out.print("PRONTO");
	}

}
