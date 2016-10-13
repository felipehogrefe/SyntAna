package SyntAna;

import LexAna.LexicalAnalyzer;
import LexAna.Token;
import LexAna.TokenType;

public class SyntaticAnalyzer {
	LexicalAnalyzer la;
	Token currentTk;
	
	private void getToken(){
		currentTk = la.nextToken();
		currentTk.print();
		
	}
	
	SyntaticAnalyzer(LexicalAnalyzer lex){
		this.la = lex;
		la.printFile();
	}
	
	public void start(){
//		UNIDADECOMP = DECCLASSE
		getToken();
		DECCLASSE();
	}

	
	public void LITERAL(){
//		LITERAL = CNT | CTN
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			getToken();
		}else if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			getToken();
		}else{
//			erro
		}
	}
	
	public void CNT(){
//		CNT = 'cntLgc' 
//				| 'cntChr' 
//				| 'cntStr'
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			getToken();
		}else{
//			erro
		}
	}
	
	public void CTN(){
//		CTN = 	'ctnInt' 
//				| 'ctnDbl' 
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			getToken();
		}else{
//			erro
		}
	}
	
	
	public void TIPO(){
//		TIPO =  NOME
//				| TIPOPRIMITIVO
//				| 'void'
		if(currentTk.checkType(TokenType.KEYVOD)){
			getToken();
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)||
				currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)){
			TIPOPRIMITIVO();
		}else if(currentTk.checkType(TokenType.ID)){
			NOME();
		}
	}
	
	public void TIPOPRIMITIVO(){
//		TIPOPRIMITIVO =  'tkLgc' 
//				| 'tkChr' 
//				| 'tkStr'
//				| TIPOPRIMITIVONUM
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			TIPOPRIMITIVONUM();
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)){
			getToken();
		}
	}
	
	public void TIPOPRIMITIVONUM(){
//		TIPOPRIMITIVONUM = 'tkInt'  
//				| 'tkDbl' 
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			getToken();
		}else{
//			erro
		}
	}	
	
	public void NOME(){
//		NOME = 'id' NOMECOMP
		if(currentTk.checkType(TokenType.ID)){
			getToken();
			NOMECOMP();
		}else{
//			erro
		}
	}
	
	public void NOMECOMP(){
//		NOMECOMP = '.' NOME
//				| null
		if(currentTk.checkType(TokenType.SEPPNT)){
			getToken();
			NOME();
		}
	}
	
	public void MODIFICADOR(){
		if(currentTk.checkType(TokenType.KEYSTC)){
			getToken();
		}
	}
	
	public void DECCLASSE(){
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
	
	public void CORPOCLASSE(){
		if(currentTk.checkType(TokenType.SEPACH)){
			getToken();
			DECSCORPOCLASSE();
			if(currentTk.checkType(TokenType.SEPFCH)){
				getToken();
			}else{
//				erro
			}
		}else{
//			erro
		}
		
	}
	
	public void DECSCORPOCLASSE(){
//		DECSCORPOCLASSE = DECMEMBROCLASSE DECC1
		DECMEMBROCLASSE();
		DECC1();
		
	}
	
	public void DECC1(){
//		DECC1 = DECMEMBROCLASSE DECC1 
//				| null
		if(!currentTk.checkType(TokenType.SEPFCH)){
			DECMEMBROCLASSE();
			DECC1();
		}
	}
	
	public void DECMEMBROCLASSE(){
//		DECMEMBROCLASSE =  DECCAMPO 
//				| '_' DECMETODO
		if(currentTk.checkType(TokenType.KEYMTD)){
			getToken();
			DECMETODO();
		}else{
			DECCAMPO();
		}
	}
	
	public void DECCAMPO(){
//		DECCAMPO = MODIFICADOR TIPO DECSVARIAVEL ';'
		MODIFICADOR();
		TIPO();
		DECSVARIAVEL();
		if(currentTk.checkType(TokenType.SEPPEV)){
			getToken();
		}else{
//			erro
		}
	}
	
	public void DECSVARIAVEL(){
//		DECSVARIAVEL = DECVARIAVEL DECV1
		DECVARIAVEL();
		DECV1();
	}
	
	public void DECV1(){
		if(currentTk.checkType(TokenType.SEPVRG)){
			getToken();
			DECVARIAVEL();
			DECV1();
		}
	}
	
	public void DECVARIAVEL(){
//		DECVARIAVEL = DECVARIAVELID DECVARIAVELF
		DECVARIAVELID();
		DECVARIAVELF();
	}
	
	public void DECVARIAVELF(){
//		DECVARIAVELF = '=' EXPRESSAO 
//				| null
		if(currentTk.checkType(TokenType.OPRATR)){
			getToken();
			EXPRATR();
		}
	}
	
	public void DECVARIAVELID(){
//		DECVARIAVELID = 'id' COLARRAY
		if(currentTk.checkType(TokenType.ID)){
			getToken();
			COLARRAY();
		}else{
//			erro
		}
	}
	
	public void COLARRAY(){
//		COLARRAY = '[' ']'
//				| null
		if(currentTk.checkType(TokenType.SEPACL)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
			}else{
//				erro
			}
		}
	}
	
	public void DECMETODO(){
//		DECMETODO = CABMETODO CORPOMETODO
		CABMETODO();
		CORPOMETODO();
	}
	
	public void CABMETODO(){
//		CABMETODO = MODIFICADOR TIPO DECPARAMFOR
		MODIFICADOR();
		TIPO();
		DECPARAMFOR();		
	}
	
	public void LISTPARAMFORMAL(){
//		LISTPARAMFORMAL = PARAMFORMAIS LISTPARAMFORMAL1
//				| null
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)||currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)
				||currentTk.checkType(TokenType.KEYVOD)){
			PARAMFORMAIS();
			LISTPARAMFORMAL1();
		}
	}
	
	public void LISTPARAMFORMAL1(){
//		LISTPARAMFORMAL1 = �,� PARAMFORMAIS LISTPARAMFORMAL
//				| null
		if(currentTk.checkType(TokenType.SEPVRG)){
			getToken();
			PARAMFORMAIS();
			LISTPARAMFORMAL();
		}
	}
	
	
	public void PARAMFORMAIS(){
//		PARAMFORMAIS = TIPO DECPRAMFOR
		TIPO();
		DECPARAMFOR();
	}
	
	public void CORPOMETODO(){
//		CORPOMETODO = BLOCO 
//				| ';'
		if(currentTk.checkType(TokenType.SEPPEV)){
			getToken();
		}else{
			BLOCO();
		}
	}
	
	public void DECPARAMFOR(){
//		DECPARAMFOR = 'id' '[' LISTPARAMFORMAL ']'
		if(currentTk.checkType(TokenType.ID)){
			getToken();	
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();	
				LISTPARAMFORMAL();
				if(currentTk.checkType(TokenType.SEPACL)){
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
	
	public void DECCONSTRU(){
//		DECCONSTRU = 'id' '[' LISTPARAMFORMAL ']'
		if(currentTk.checkType(TokenType.ID)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				LISTPARAMFORMAL();
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
	
	public void BLOCO(){
//		BLOCO = '{' DECBLOCO '}'
		if(currentTk.checkType(TokenType.SEPACH)){
			getToken();
			DECBLOCO();
			if(currentTk.checkType(TokenType.SEPFCH)){
				getToken();
			}else{
//				erro
			}
		}else{
//			erro
		}
	}
	
	public void DECBLOCO(){
//		DECBLOCO = BLOCODEC DECB1
		BLOCODEC();
		DECB1();
	}
	
	public void DECB1(){
//		DECB1 = DECBLOCO DECB1 
//				| null
		if(!currentTk.checkType(TokenType.SEPFCH)){
			/*se o currentTk for '}' significa que o bloco acabou, logo estamos no desvio null
			 caso contrario, para qualquer outro token ainda estamos no bloco*/
			DECBLOCO();
			DECB1();
		}
	}
	

	public void BLOCODEC(){
//		BLOCODEC = DECCAMPO 
//				| DECLARACAO
		if(currentTk.checkType(TokenType.KEYIF)||currentTk.checkType(TokenType.KEYWHL)||currentTk.checkType(TokenType.KEYFOR)||
				currentTk.checkType(TokenType.SEPPEV)||currentTk.checkType(TokenType.SEPACH)){
			DECLARACAO();
		}else{
			DECCAMPO();
		}
	}
	
	public void DECLARACAO(){
//		DECLARACAO = DECSEMSUBDECDIR 
//		        | DECWHILE 
//		        | DECFOR 
//		        | IFDEC
		if(currentTk.checkType(TokenType.KEYIF)){
			DECIF();
		}else if(currentTk.checkType(TokenType.KEYWHL)){
			DECWHILE();
		}else if(currentTk.checkType(TokenType.KEYFOR)){
			DECFOR();
		}else{
			DECSEMSUBDECDIR();
		}
	}
	
	public void DECSEMSUBDECDIR(){
//		DECSEMSUBDECDIR = BLOCO 
//				| ';' 
//				| EXPRESSAODEC 
//		        | DECRETURN 
//		        | DECBREAK
		if(currentTk.checkType(TokenType.SEPACH)){
			BLOCO();
		}else if(currentTk.checkType(TokenType.SEPPEV)){
			getToken();
		}else if(currentTk.checkType(TokenType.KEYRET)){
			DECRETURN();
		}else if(currentTk.checkType(TokenType.KEYBRK)){
			DECBREAK();
		}else{
			EXPRESSAODEC();
		}
		
	}
	
	public void EXPRESSAODEC(){
//		EXPRESSAODEC = EXPRINCRPRE 
//				| EXPRDECREPRE 
//		        | EXPRCRIAINSTANCLASSE
//		        | NOME '[' LISTAARG ']' ATR
		if(currentTk.checkType(TokenType.ID)){
			NOME();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				LISTAARG();
				if(currentTk.checkType(TokenType.SEPFCL)){
					getToken();
					ATR();
				}
			}
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			EXPRINCRPRE();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			EXPRDECREPRE();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			EXPRCRIAINSTANCLASSE();
		}
	}
	
	public void ATR(){
//		ATR = '=' EXPRATR
		if(currentTk.checkType(TokenType.OPRATR)){
			getToken();
			EXPRATR();
		}
		
	}
	
	
	public void DECIF(){
//		IFTHENDEC = 'if' '[' EXPRATR ']'  DECLARACAO 
		if(currentTk.checkType(TokenType.KEYIF)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				EXPRATR();
				if(currentTk.checkType(TokenType.SEPFCL)){
					getToken();
					DECLARACAO();
					DECELSE();
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
	
	public void DECELSE(){
//		ELSEDEC = 'else' DECLARACAO
//				| null
		if(currentTk.checkType(TokenType.KEYELS)){
			getToken();
			DECLARACAO();
		}
	}
	
	public void DECWHILE(){
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
	
	public void DECFOR(){
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
	
	public void FORINIT(){
//		FORINIT = TIPOPRIMITIVONUM NOME '=' EXPRATR
		TIPOPRIMITIVONUM();
		NOME();
		if(currentTk.checkType(TokenType.OPRATR)){
			getToken();
			EXPRATR();
		}
	}
	
	public void LIMITESUP(){
//		LIMITESUP = CTN 
//				| NOME
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			CTN();
		}else{
			NOME();
		}
	}
	
	public void PASSO(){
//		PASSO = CTN 
//				| NOME
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			CTN();
		}else{
			NOME();
		}
	}
	
	public void LISTADECEXPR(){
//		LISTADECEXPR = EXPRESSAODEC LISTADECEXPR1
		EXPRESSAODEC();
		LISTADECEXPR1();
	}
	
	public void LISTADECEXPR1(){
//		LISTADECEXPR1 = ';' LISTADECEXPR LISTADECEXPR1
//				| null
		if(currentTk.checkType(TokenType.SEPPEV)){
			getToken();
			LISTADECEXPR(); 
			LISTADECEXPR1();
		}
	}
	
	
	public void DECRETURN(){
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
	
	public void DECBREAK(){
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
	
	public void LISTAARG(){
//		LISTAARG = EXPRATR LISTA1
		EXPRATR();
		LISTA1();
	}
	
	public void LISTA1(){
		if(currentTk.checkType(TokenType.SEPVRG)){
			getToken();
			LISTAARG();
			LISTA1();
		}
	}
	
	public void EXPRCRIAINSTANCLASSE(){
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
	
	public void CHAMADAMETODO(){
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
	
	public void ACESSAARRAY(){
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
	
	public void EXPRCRIAARRAY(){
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
	
	public void PRIMARIO(){
//		PRIMARIO = 'this' 
//		        | '[' EXPRATR ']'
//		        | LITERAL
//		        | EXPRCRIAINSTANCLASSE
//		    	| CHAMADAMETODO 
//				| EXPRCRIAARRAY
		if(currentTk.checkType(TokenType.KEYARY)){
			EXPRCRIAARRAY();
		}else if(currentTk.checkType(TokenType.KEYTHS)){
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
	
	public void ATRIBUICAO(){
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
	
	public void NOME1(){
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
	
	public void EXPRATR(){
//		EXPRATR = EXPRCOND 
//				| ATRIBUICAO
	}
	
	public void EXPRCOND(){
//		EXPRCOND = EXPRORCOND EXPRCOND1
		EXPRORCOND();
		EXPRCOND1();
	}
	
	public void EXPRCOND1(){
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
	
	public void EXPRORCOND(){
//		EXPRORCOND = EXPRECOND EXPROR1
		EXPRECOND();
		EXPROR1();
	}
	
	public void EXPROR1(){
//		EXPROR1 = '||' EXPRECOND EXPROR1 
//				| null 
		if(currentTk.checkType(TokenType.OPROCD)){
			getToken();
			EXPRECOND();
			EXPROR1();
		}
	}
	
	public void EXPRECOND(){
//		EXPRECOND = EXPRIOR EXPREC1 
		EXPRIOR();
		EXPREC1();
	}
	
	public void EXPREC1(){
//		EXPREC1 = '&&' EXPRIOR EXPREC1 
//				| null
		if(currentTk.checkType(TokenType.OPRECD)){
			getToken();
			EXPRIOR();
			EXPREC1();
		}
	}
	
	public void EXPRIOR(){
//		EXPRIOR = EXPREOR EXPRI1
		EXPREOR();
		EXPRI1();
	}
	
	public void EXPRI1(){
//		EXPRI1 = '|' EXPROIR EXPRI' 
//				| null
		if(currentTk.checkType(TokenType.OPRIOR)){
			getToken();
			EXPRIOR();
			EXPRI1();
		}
	}
	
	public void EXPREOR(){
//		EXPREOR = EXPRE EXPREOR1
		EXPRE();
		EXPREOR1();
	}
	
	public void EXPREOR1(){
//		EXPREOR1 = '^' EXPREOR EXPREOR1 
//				| null
		if(currentTk.checkType(TokenType.OPREOR)){
			getToken();
			EXPREOR();
			EXPREOR1();
		}
	}
	
	public void EXPRE(){
//		EXPRE = EXPREQUA EXPRE1
		EXPREQUA();
		EXPRE1();
	}
	
	public void EXPRE1(){
//		EXPRE1 = '&' EXPREQUA EXPRE1 
//				| null
		if(currentTk.checkType(TokenType.OPRE)){
			getToken();
			EXPREQUA();
			EXPRE1();
		}
	}
	
	public void EXPRINCRPOS(){
//		EXPRINCRPOS = EXPRPOSF '++'
		EXPRPOSF();
		if(currentTk.checkType(TokenType.OPRMMA)){
			getToken();
		}else{
//			erro
		}
	}
	
	public void EXPRDECREPOS(){
//		EXPRDECREPOS = EXPRPOSF '--'
		EXPRPOSF();
		if(currentTk.checkType(TokenType.OPRMME)){
			getToken();
		}else{
//			erro
		}
	}
	
	public void EXPRINCRPRE(){
//		EXPRINCRPRE =  '++' EXPRUNARIA
		if(currentTk.checkType(TokenType.OPRMMA)){
			getToken();
			EXPRUNARIA();
		}
	}
	
	public void EXPRDECREPRE(){
//		EXPRINCRPRE =  '--' EXPRUNARIA
		if(currentTk.checkType(TokenType.OPRMME)){
			getToken();
			EXPRUNARIA();
		}
	}
	
	public void EXPUNNMM(){
//		EXPUNNMM = EXPRPOSF 
//				| '~' EXPRUNARIA 
//				| '!' EXPRUNARIA
		if(currentTk.checkType(TokenType.OPRDIF)||currentTk.checkType(TokenType.OPRNEG)){
			EXPRUNARIA();
		}else{
			EXPRPOSF();
		}
	}
	
	public void EXPRUNARIA(){
//		EXPRUNARIA = '+' EXPRUNARIA
//		        | '-' EXPRUNARIA 
//		        | EXPRPOSF 
//		        | EXPRINCRPRE
//		        | EXPRDECREPRE 
//		        | EXPUNNMM
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			getToken();
			EXPRUNARIA();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			EXPRDECREPRE();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			EXPRINCRPRE();
		}else{
			EXPRPOSF();
		}	
	}
	
	public void EXPRREL(){
//		EXPRREL = EXPRADD EXPRREL1
		EXPRADD();
		EXPRREL1();
	}
	
	public void EXPREQUA(){
//		EXPREQUA = EXPRREL EXPREQ1
		EXPRREL();
		EXPREQ1();
	}
	
	public void EXPREQ1(){
//		EXPREQ1 = '==' EXPREQUA EXPREQ1 
//			    | '!=' EXPREQUA EXPREQ1 
//				| null
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)){
			getToken();
			EXPREQUA();
			EXPREQ1();
		}
	}
	
	public void EXPRREL1(){
//		EXPRREL1 =  '<' EXPRADD EXPRREL1 
//				| '>' EXPRADD EXPRREL1 
//				| '<=' EXPRADD EXPRREL1  
//				| '>=' EXPRADD EXPRREL1 
//				| 'instanceof' 'id' EXPRREL1
//				| null
		if(currentTk.checkType(TokenType.OPRMNR)||currentTk.checkType(TokenType.OPRMAR)||currentTk.checkType(TokenType.OPRMEI)||
				currentTk.checkType(TokenType.OPRMAI)){
			getToken();
			EXPRADD();
			EXPRREL1();
		}else if(currentTk.checkType(TokenType.KEYIOF)){
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				getToken();
				EXPRREL1();
			}else{
//				erro
			}
		}
	}
	
	public void EXPRADD(){
//		EXPRADD = EXPRMULT EXPRADD1
		EXPRMULT();
		EXPRADD1();		
	}
		
	public void  EXPRMULT1(){
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
	
	public void EXPRMULT(){
//		EXPRMULT = EXPRUNARIA EXPRMULT1
		EXPRUNARIA();
		EXPRMULT1();
	}
	
	public void EXPRADD1(){
//		EXPRADD1 = '+' EXPRMULT EXPRADD1
//		        |  '-' EXPRMULT EXPRADD1 
//		        | null	
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			getToken();
			EXPRMULT();
			EXPRADD1();
		}
	}
	
	public void EXPRPOSF(){
//		EXPRPOSF = 'this' 
//		        | '[' EXPRATR ']'
//		        | LITERAL
//		        | EXPRCRIAINSTANCLASSE
//		    	| CHAMADAMETODO 
//				| EXPRCRIAARRAY
//				| NOME 
		if(currentTk.checkType(TokenType.KEYARY)){
			EXPRCRIAARRAY();
		}else if(currentTk.checkType(TokenType.KEYTHS)){
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
			getToken();	
			NOME();
			CHAMADA();
		}
	}
	
	public void CHAMADA(){
//		CHAMADA =  '[' LISTAARG ']'
//				| null
		if(currentTk.checkType(TokenType.SEPACL)){
			getToken();	
			LISTAARG();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();	
			}else{
//				erro
			}
		}
	}
}	
