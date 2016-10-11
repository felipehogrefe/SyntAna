package SyntAna;

import LexAna.LexicalAnalyzer;
import LexAna.Token;
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
			Token tk = la.nextToken();
			TokenType tkt = tk.getCategory();
			if(tkt==TokenType.SEPVRG){
				DECVARIAVEL();
				DECV1();
			}else if(tkt==TokenType.SEPACL){
				
			}
			if(la.nextToken().checkType(TokenType.SEPACL)){
				if(la.nextToken().checkType(TokenType.SEPFCL)){
//					DECVARIAVELID = ‘id’ | DECVARIAVELID ‘[’ ‘]’
				}else if((la.nextToken().checkType(TokenType.SEPFCL))){
					LISTPARAMFORMAL();
//					METODODEC = ‘id’ ‘[’ LISTPARAMFORMAL ‘]’
				}
			}
		}
		
//		DECCORPOCLASSE  = DECMEMBROCLASSE | DECCONSTRU
//		DECMEMBROCLASSE = DECCAMPO | DECMETODO
//				DECCAMPO = MODIFICADOR TIPO DECSVARIAVEL ‘;’
//					TIPO = TIPOPRIMITIVO | TIPOREF
//					DECSVARIAVEL = DECVARIAVEL DECV1
//						DECV1 = ‘,’  DECVARIAVEL DECV1
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
	
	public static void LISTPARAMFORMAL(){
		LISTPARAMFORMAL = PARAMFORMAIS LISTPARAMFORMAL1
				LISTPARAMFORMAL1 = ‘,’ PARAMFORMAIS LISTPARAMFORMAL
				PARAMFORMAIS = TIPO DECVARIAVELID
	}
	
	public static void DECV1(){
		if(la.nextToken().checkType(TokenType.SEPVRG)){
			DECVARIAVEL();
			DECV1();
		}
	}
	
	public static void DECVARIAVEL(){
		if(la.nextToken().checkType(TokenType.ID)){
			DECVARIAVELF();
		}else{
//			erro
		}
	}
	
	public static void DECVARIAVELF(){
		if(la.nextToken().checkType(TokenType.OPRATR)){
			INICVARIAVEL();
		}
	}
	
	public static void INICVARIAVEL(){
		EXPRESSAO();
		INICARRAY();
	}
	
	public static void DECC1(){
		DECCORPOCLASSE();
		DECC1();
//		OU
//		NULL();
	}
	
	

}
