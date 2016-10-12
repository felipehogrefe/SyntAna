package SyntAna;

import LexAna.LexicalAnalyzer;
import LexAna.Token;
import LexAna.TokenType;

public class SyntaticAnalyzer {
	static LexicalAnalyzer la;
	static Token currentTk;
	
	private static void getToken(){
		currentTk = la.nextToken();
	}
	
	SyntaticAnalyzer(LexicalAnalyzer la){
		this.la = la;
	}
	
	public void start(){
//		UNIDADECOMP = DECCLASSE
		getToken();
		DECCLASSE();
	}
	
	public static void NOME(){
//		NOME = 'id' NOMECOMP
		if(currentTk.checkType(TokenType.ID)){
			getToken();
			NOMECOMP();
		}else{
//			erro
		}
	}
	
	public static void NOMECOMP(){
//		NOMECOMP = '.' NOME
//				| null
		if(currentTk.checkType(TokenType.SEPPNT)){
			getToken();
			NOME();
		}
	}
	
	public static void LITERAL(){
//		LITERAL = CNT | CTN
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			getToken();
		}else if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			getToken();
		}else{
//			erro
		}
	}
	
	public static void TIPOPRIMITIVONUM(){
//		TIPOPRIMITIVONUM = 'tkInt'  
//				| 'tkDbl' 
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			getToken();
		}else{
//			erro
		}
	}
	
	public static void CNT(){
//		CNT = 'cntLgc' 
//				| 'cntChr' 
//				| 'cntStr'
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			getToken();
		}else{
//			erro
		}
	}
	
	public static void CTN(){
//		CTN = 	'ctnInt' 
//				| 'ctnDbl' 
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			getToken();
		}else{
//			erro
		}
	}
	
	public static void DECCLASSE(){
//		DECCLASSE = MODIFICADOR ‘class’ ‘id’ CORPOCLASSE
		MODIFICADOR();
		if(currentTk.checkType(TokenType.KEYCLS)){
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				getToken();
				CORPOCLASSE();
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void MODIFICADOR(){
		if(currentTk.checkType(TokenType.KEYSTC)){
			getToken();
		}
	}
	
	public static void CORPOCLASSE(){
		if(currentTk.checkType(TokenType.SEPACH)){
			getToken();
			DECSCORPOCLASSE();
		}else{
//			erro
		}
		if(la.nextToken().checkType(TokenType.SEPFCH)){
			getToken();
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
	
	public static void ITEDECNSI(){
//		ITEDECNSI = 'if' '[' EXPRATR ']' DECNSI 'else' DECNSI 
		if(currentTk.checkType(TokenType.KEYIF)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				EXPRATR();
			}
		}
	}
	
	public static void DECWHILE(){
//		WDECNSI = 'while' '[' EXPRATR ']' DECLARACAO 
		if(currentTk.checkType(TokenType.KEYWHL)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				EXPRATR();
				if(currentTk.checkType(TokenType.SEPFCL)){
					getToken();
					DECLARACAO();
				}else{
//					erro
				}
			}else{
//				erro
			}
		}else{
//			erro
		}
	}

	
	public static void WDECNSI(){
//		WDECNSI = 'while' '[' EXPRATR ']' DECNSI 
		if(currentTk.checkType(TokenType.KEYWHL)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				EXPRATR();
				if(currentTk.checkType(TokenType.SEPFCL)){
					getToken();
					DECNSI();
				}else{
//					erro
				}
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void DECFOR(){
//		DECFOR = 'for' '[' FORINIT ';' LIMITESUP ';' PASSO ']' DECLARACAO
		if(currentTk.checkType(TokenType.KEYFOR)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				FORINIT();
				if(currentTk.checkType(TokenType.SEPPEV)){
					getToken();
					LIMITESUP();
					if(currentTk.checkType(TokenType.SEPPEV)){
						getToken();
						PASSO();
						if(currentTk.checkType(TokenType.SEPFCL)){
							getToken();
							DECLARACAO();
						}else{
//							erro
						}
					}else{
//						erro
					}
				}else{
//					erro
				}
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void FDECNSI(){
//		FDECNSI = 'for' '[' FORINIT ';' LIMITESUP ';' PASSO ']' DECNSI 
		if(currentTk.checkType(TokenType.KEYFOR)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				FORINIT();
				if(currentTk.checkType(TokenType.SEPPEV)){
					getToken();
					LIMITESUP();
					if(currentTk.checkType(TokenType.SEPPEV)){
						getToken();
						PASSO();
						if(currentTk.checkType(TokenType.SEPFCL)){
							getToken();
							DECNSI();
						}else{
//							erro
						}
					}else{
//						erro
					}
				}else{
//					erro
				}
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void FORINIT(){
//		FORINIT = TIPOPRIMITIVONUM NOME '=' EXPRATR
		TIPOPRIMITIVONUM();
		NOME();
		if(currentTk.checkType(TokenType.OPRATR)){
			getToken();
			EXPRATR();
		}
	}
	
	public static void LIMITESUP(){
//		LIMITESUP = CTN 
//				| NOME
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			CTN();
		}else{
			NOME();
		}
	}
	
	public static void PASSO(){
//		PASSO = CTN 
//				| NOME
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			CTN();
		}else{
			NOME();
		}
	}
	
	public static void DECRETURN(){
//		DECRETURN = 'return' EXPRATR ';'
		if(currentTk.checkType(TokenType.KEYRET)){
			getToken();
			EXPRATR();
			if(currentTk.checkType(TokenType.SEPPEV)){
				getToken();
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void DECBREAK(){
//		DECBREAK = 'break' ';'
		if(currentTk.checkType(TokenType.KEYBRK)){
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				getToken();
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void LISTAARG(){
//		LISTAARG = EXPRATR LISTA1
		EXPRATR();
		LISTA1();
	}
	
	public static void LISTA1(){
		if(currentTk.checkType(TokenType.SEPVRG)){
			getToken();
			LISTAARG();
		}
	}
	
	public static void EXPRCRIAINSTANCLASSE(){
//		EXPRCRIAINSTANCLASSE = 'new' TIPOCLASSE '[' LISTAARG ']'
		if(currentTk.checkType(TokenType.KEYNEW)){
			getToken();
			NOME();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				LISTAARG();
				if(currentTk.checkType(TokenType.SEPFCL)){
					getToken();
				}else{
//					erro
				}
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void CHAMADAMETODO(){
//		CHAMADAMETODO = NOME '[' LISTAARG ']'
		NOME();
		if(currentTk.checkType(TokenType.SEPACL)){
			getToken();
			LISTAARG();
			if(currentTk.checkType(TokenType.SEPFCL)){
				getToken();				
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void ACESSAARRAY(){
//		ACESSAARRAY = NOME '[' EXPRATR ']'
		NOME();
		if(currentTk.checkType(TokenType.SEPACL)){
			getToken();
			EXPRATR();
			if(currentTk.checkType(TokenType.SEPFCL)){
				getToken();				
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void EXPRCRIAARRAY(){
//		EXPRCRIAARRAY= 'array' '[' TIPO ',' CTN ']'
		if(currentTk.checkType(TokenType.KEYARY)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				TIPO();
				if(currentTk.checkType(TokenType.SEPVRG)){
					getToken();
					CTN();
					if(currentTk.checkType(TokenType.SEPFCL)){
						getToken();
					}else{
//						erro
					}
				}else{
//					erro
				}
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public static void PRIMARIO(){
//		PRIMARIO = PRISEMNOVOARRAY 
//				| EXPRCRIAARRAY
		if(currentTk.checkType(TokenType.KEYARY)){
			EXPRCRIAARRAY();
		}else{
			PRISEMNOVOARRAY();
		}
	}
	
	public static void PRISEMNOVOARRAY(){
//		PRISEMNOVOARRAY = LITERAL
//		    	| 'this' 
//		        | '[' EXPRATR ']'
//		        | EXPRCRIAINSTANCLASSE
//		    	| CHAMADAMETODO 
//		        | ACESSAARRAY
		if(currentTk.checkType(TokenType.KEYTHS)){
			getToken();
		}else if(currentTk.checkType(TokenType.SEPACL)){
			getToken();
			EXPRATR();
		}else if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTSTR)||currentTk.checkType(TokenType.CNTCHR)||
				currentTk.checkType(TokenType.CTNINT)||currentTk.checkType(TokenType.CTNDBL)){
			getToken();			
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			EXPRCRIAINSTANCLASSE();
		}else if(currentTk.checkType(TokenType.ID)){
			CHAMADAMETODO();
		}
	}
	
	public static void ATRIBUICAO(){
//		ATRIBUICAO = NOME NOME1  '=' EXPRATR
		NOME();
		NOME1();
		if(currentTk.checkType(TokenType.OPRATR)){
			getToken();
		}else{
//			erro
		}
		EXPRATR();
	}
	
	public static void NOME1(){
		if(currentTk.checkType(TokenType.SEPACL)){
			getToken();
			EXPRATR();
			if(currentTk.checkType(TokenType.SEPFCL)){
				getToken();
			}else{
//				erro
			}
		}
	}
	
	public static void EXPRATR(){
//		EXPRATR = EXPRCOND 
//				| ATRIBUICAO
	}
	
	public static void EXPRCOND(){
//		EXPRCOND = EXPRORCOND EXPRCOND1
		EXPRORCOND();
		EXPRCOND1();
	}
	
	public static void EXPRCOND1(){
//		EXPRCOND1 = EXPORC 
//				| '?' EXPRATR ':' EXPRCOND
		if(currentTk.checkType(TokenType.OPRTRI)){
			getToken();
			EXPRATR();
			if(currentTk.checkType(TokenType.SEPDPT)){
				getToken();
				EXPRCOND();
			}else{
//				erro
			}
		}
	}
	
	public static void EXPRORCOND(){
//		EXPRORCOND = EXPRECOND EXPROR1
		EXPRECOND();
		EXPROR1();
	}
	
	public static void EXPROR1(){
//		EXPROR1 = '||' EXPRECOND EXPROR1 
//				| null 
		if(currentTk.checkType(TokenType.OPROCD)){
			getToken();
			EXPRECOND();
			EXPROR1();
		}
	}
	
	public static void EXPRECOND(){
//		EXPRECOND = EXPRIOR EXPREC1 
		EXPRIOR();
		EXPREC1();
	}
	
	public static void EXPREC1(){
//		EXPREC1 = '&&' EXPRIOR EXPREC1 
//				| null
		if(currentTk.checkType(TokenType.OPRECD)){
			getToken();
			EXPRIOR();
			EXPREC1();
		}
	}
	
	public static void EXPRIOR(){
//		EXPRIOR = EXPREOR EXPRI1
		EXPREOR();
		EXPRI1();
	}
	
	public static void EXPRI1(){
//		EXPRI1 = '|' EXPROIR EXPRI' 
//				| null
		if(currentTk.checkType(TokenType.OPRIOR)){
			getToken();
			EXPRIOR();
			EXPRI1();
		}
	}
	
	public static void EXPREOR(){
//		EXPREOR = EXPRE EXPREOR1
		EXPRE();
		EXPREOR1();
	}
	
	public static void EXPREOR1(){
//		EXPREOR1 = '^' EXPREOR EXPREOR1 
//				| null
		if(currentTk.checkType(TokenType.OPREOR)){
			getToken();
			EXPREOR();
			EXPREOR1();
		}
	}
	
	public static void EXPRE(){
//		EXPRE = EXPREQUA EXPRE1
		EXPREQUA();
		EXPRE1();
	}
	
	public static void EXPRE1(){
//		EXPRE1 = '&' EXPREQUA EXPRE1 
//				| null
		if(currentTk.checkType(TokenType.OPRE)){
			getToken();
			EXPREQUA();
			EXPRE1();
		}
	}
	
	public static void EXPRINCRPOS(){
//		EXPRINCRPOS = EXPRPOSF '++'
		EXPRPOSF();
		if(currentTk.checkType(TokenType.OPRMMA)){
			getToken();
		}else{
//			erro
		}
	}
	
	public static void EXPRDECREPOS(){
//		EXPRDECREPOS = EXPRPOSF '--'
		EXPRPOSF();
		if(currentTk.checkType(TokenType.OPRMME)){
			getToken();
		}else{
//			erro
		}
	}
	
	public static void EXPRUNARIA(){
//		EXPRUNARIA = '--' EXPRUNARIA
//				| '--' EXPRUNARIA
//		        | '+' EXPRUNARIA
//		        | '-' EXPRUNARIA 
//				| '~' EXPRUNARIA 
//				| '!' EXPRUNARIA
//       		| EXPRPOSF 
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)||currentTk.checkType(TokenType.OPRDIF)
				||currentTk.checkType(TokenType.OPRDIF)||currentTk.checkType(TokenType.OPRDIF)||currentTk.checkType(TokenType.OPRDIF)){
			getToken();
			EXPRUNARIA();
		}else{
			getToken();
			EXPRPOSF();
		}
	}
	
	public static void EXPRREL(){
//		EXPRREL = EXPRADD EXPRREL1
		EXPRADD();
		EXPRREL1();
	}
	
	public static void EXPREQUA(){
//		EXPREQUA = EXPRREL EXPREQ1
		EXPRREL();
		EXPREQ1();
	}
	
	public static void EXPREQ1(){
//		EXPREQ1 = '==' EXPREQUA EXPREQ1 
//			    | '!=' EXPREQUA EXPREQ1 
//				| null
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)){
			getToken();
			EXPREQUA();
			EXPREQ1();
		}
	}
	
	public static void EXPRREL1(){
//		EXPRREL1 =  '<' EXPRADD EXPRREL1 
//				| '>' EXPRADD EXPRREL1 
//				| '<=' EXPRADD EXPRREL1  
//				| '>=' EXPRADD EXPRREL1 
//				| 'instanceof' TIPOREF EXPRREL1
//				| null
		if(currentTk.checkType(TokenType.OPRMNR)||currentTk.checkType(TokenType.OPRMAR)||currentTk.checkType(TokenType.OPRMEI)||
				currentTk.checkType(TokenType.OPRMAI)){
			getToken();
			EXPRADD();
			EXPRREL1();
		}else if(currentTk.checkType(TokenType.KEYIOF)){
			getToken();
			TIPOREF();
			EXPRREL1();
		}
	}
	
	public static void EXPRADD(){
//		EXPRADD = EXPRMULT EXPRADD1
		EXPRMULT();
		EXPRADD1();		
	}
		
	public static void  EXPRMULT1(){
//		EXPRMULT1 = '*' EXPRUNARIA EXPRMULT1 
//				| '/' EXPRUNARIA EXPRMULT1 
//				| '%' EXPRUNARIA EXPRMULT1
//		        | null		
		if(currentTk.checkType(TokenType.OPRMOD)||currentTk.checkType(TokenType.OPRDIV)||currentTk.checkType(TokenType.OPRMTL)){
			getToken();
			EXPRUNARIA();
			EXPRMULT1();
		}
	}
	
	public static void EXPRMULT(){
//		EXPRMULT = EXPRUNARIA EXPRMULT1
		EXPRUNARIA();
		EXPRMULT1();
	}
	
	public static void EXPRADD1(){
//		EXPRADD1 = '+' EXPRMULT EXPRADD1
//		        |  '-' EXPRMULT EXPRADD1 
//		        | null	
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			getToken();
			EXPRMULT();
			EXPRADD1();
		}
	}
	
	public static void EXPRPOSF(){
//		EXPRPOSF = PRIMARIO 
//				| NOME 
//				| EXPRINCRPOS 
//		        | EXPRDECREPOS
	}
}	
