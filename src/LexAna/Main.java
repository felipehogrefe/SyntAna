package LexAna;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws IOException {
		

		FileWriter arq = new FileWriter("C:/Users/Felipe/workspace/LexAna/src/saida.txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
		
		LexicalAnalyzer al = new LexicalAnalyzer();
		al.read("C:/Users/Felipe/workspace/LexAna/src/alo.txt");
		gravarArq.println("---------ALO---------");
		
		while(al.isOver()){
			Token t = al.nextToken();
			gravarArq.println(t.toString());
		}
		
		

		LexicalAnalyzer al1 = new LexicalAnalyzer();
		al1.read("C:/Users/Felipe/workspace/LexAna/src/fibo.txt");
		gravarArq.println("---------FIBO---------");
		
		while(al1.isOver()){
			Token t = al1.nextToken();
			gravarArq.println(t.toString());
		}
		
		LexicalAnalyzer al2 = new LexicalAnalyzer();
		al2.read("C:/Users/Felipe/workspace/LexAna/src/shell.txt");

		gravarArq.println("---------SHELL---------");
		
		while(al2.isOver()){
			Token t = al2.nextToken();
			gravarArq.println(t.toString());
		}
		arq.close();
		
		System.out.print("PRONTO");
	}

}
