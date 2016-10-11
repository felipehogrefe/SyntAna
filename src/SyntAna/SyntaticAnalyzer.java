package SyntAna;

import LexAna.LexicalAnalyzer;
import LexAna.TokenType;

public class SyntaticAnalyzer {
	static LexicalAnalyzer la;
	
	SyntaticAnalyzer(LexicalAnalyzer la){
		this.la = la;
	}
	
	public void start(){
//		UNIDADECOMP = DECCLASSE
		DECCLASSE();
	}
	
	public static void DECCLASSE(){
//		DECCLASSE = MODIFICADOR ‘class’ ‘id’ CORPOCLASSE
		MODIFICADOR();
		if(la.nextToken().checkType(TokenType.KEYCLS)){
			if(la.nextToken().checkType(TokenType.ID)){
				CORPOCLASSE();
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void MODIFICADOR(){
		if(la.nextToken().checkType(TokenType.KEYSTC)){
			
		}else if(false){
			
		}else{
//			erro
		}
	}
	
	public static void CORPOCLASSE(){
		if(la.nextToken().checkType(TokenType.SEPACH)){
			DECSCORPOCLASSE();
		}else{
//			erro
		}
		if(la.nextToken().checkType(TokenType.SEPFCH)){
		}else{
//			erro
		}
	}
	
	public static void DECSCORPOCLASSE(){
		DECCORPOCLASSE();
		DECC1();
	}
	
	public static void DECCORPOCLASSE(){
		if(la.nextToken().checkType(TokenType.ID)){
		//se entrar aqui é DECCAMPO ou DECMETODO	
		}
		
//		DECCORPOCLASSE  = DECMEMBROCLASSE | DECCONSTRU
//		DECMEMBROCLASSE = DECCAMPO | DECMETODO
//				DECCAMPO = MODIFICADOR TIPO DECSVARIAVEL ‘;’
//					TIPO = TIPOPRIMITIVO | TIPOREF
//					DECSVARIAVEL = DECVARIAVEL DECV1
//						DECVARIAVEL = DECVARIAVELID DECVARIAVELF
//							DECVARIAVELID = ‘id’ | DECVARIAVELID ‘[’ ‘]’
//							//se terminar com ; é deccampo
//		
//					DECMETODO = CABMETODO CORPOMETODO
//						CABMETODO = TIPO METODODEC | METODODEC
//							TIPO = TIPOPRIMITIVO | TIPOREF
//							METODODEC = ‘id’ ‘[’ LISTPARAMFORMAL ‘]’
//							//se tiver { é decmetodo
//		OU
//		DECCONSTRU(); arrastar pra analise semantica
	}
	
	public static void DECC1(){
		DECCORPOCLASSE();
		
		DECC1();
//		OU
//		NULL();
	}
	
	

}
