package SyntAna;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import LexAna.LexicalAnalyzer;
import LexAna.Token;

public class Main {

	public static void main(String[] args) throws IOException {
//		FileWriter arq = new FileWriter("/home/laccan/workspace/SyntAna/src/saida.txt");
		FileWriter arq = new FileWriter("C:/Users/Felipe/workspace/SyntAna/src/saidaAlo.txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
		
		LexicalAnalyzer la = new LexicalAnalyzer();
//		la.read("/home/laccan/workspace/SyntAna/src/alo.txt");
		la.read("C:/Users/Felipe/workspace/SyntAna/src/alo.txt");
		
//		la.printFile();
		
		SyntaticAnalyzer sa = new SyntaticAnalyzer(la,gravarArq);
		
		sa.start();
		
		for(String p : SyntaticAnalyzer.producoes){
			System.out.println(p);
			if(!p.equals("")){
				SyntaticAnalyzer.escreveln(p);
			}
		}
	
		arq.close();
		System.out.print("PRONTO");
	}

}
