package SyntAna;

import java.io.PrintWriter;
import java.util.ArrayList;

import LexAna.LexicalAnalyzer;
import LexAna.Token;
import LexAna.TokenType;

public class SyntaticAnalyzer {
	static LexicalAnalyzer la;
	static Token currentTk;
	private static PrintWriter gravarArq;
	static ArrayList<String> producoes;
	
	private static void getToken(){
		if(la.isOver()){
			currentTk = la.nextToken();
		}
//		System.out.println(currentTk.getValue());
	}
	
	SyntaticAnalyzer(LexicalAnalyzer la, PrintWriter gravarArq){
		this.la = la;
		this.gravarArq = gravarArq;
	}
	
	static void erro(){
		System.out.println("ERRO - val: "+currentTk.getValue()+", categ: "+currentTk.getCategory()+", ["+currentTk.getLine()+","+currentTk.getCol()+"]");
		System.exit(1);
	}
	
	static void escreve(String str){
		gravarArq.print(str);
	}
	
	static void escreveln(String str){
		gravarArq.println(str);
	}
		
	public void start(){
//		UnidadeDeCompilacao = DeclaracaoDeClasse
		producoes = new ArrayList<>();
		String s="";
		producoes.add(s);
		s=("UnidadeDeCompilacao = DeclaracaoDeClasse");
		getToken();
		declaracaoDeClasse();
	}
	
	public static void literal(){
//		Literal = Constante 
//				| ConstanteNumerica
		String s="";
		producoes.add(s);
		
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			s="Literal = Constante";
			constante();
		}else if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			s="Literal = ConstanteNumerica";
			constanteNumerica();
		}else{
			erro();
		}
	}
	
	public static void constante(){
//		Constante = 'cntLgc' 
//				| 'cntChr' 
//				| 'cntStr'	
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			escreveln("Constante = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public static void constanteNumerica(){
//		ConstanteNumerica = 'ctnInt' 
//				| 'ctnDbl
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			s="ConstanteNumerica = "+currentTk.getCategory()+" ("+currentTk.getValue()+")";
			getToken();
		}else{
			erro();
		}
	}
	
	
	public static void tipo(){
//		Tipo = TipoPrimitivo 
//				| Nome
//				| 'void'
//				| 'array'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYARY)){
			getToken();
			s="Tipo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")";	
		}else if(currentTk.checkType(TokenType.KEYVOD)){
			getToken();
			s="Tipo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")";	
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)||
				currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)){
			s="Tipo = TipoPrimitivo";	
			tipoPrimitivo();
		}else if(currentTk.checkType(TokenType.ID)){
			s="Tipo = Nome";	
			nome();
		}else{
			erro();	
		}
	}
	
	public static void tipoPrimitivo(){
//		TipoPrimitivo = TipoNumerico 
//				| 'tkLgc'
//				| 'tkChr' 
//				| 'tkStr' 	
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			s="TipoPrimitivo = TipoNumerico";	
			tipoNumerico();
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)){
			s="TipoPrimitivo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")";
			getToken();
		}else{
			erro();
		}
	}
	
	public static void tipoNumerico(){
//		TipoNumerico =  'tkInt' 
//				| 'tkDbl'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			s="TipoNumerico = "+currentTk.getCategory()+" ("+currentTk.getValue()+")";
			getToken();
		}else{
			erro();
		}
	}	
	
	public static void arrayCol(){
//		ArrayCol =  '[' ']' ArrayCol
//				| null	
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPACL)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				s="ArrayCol = '['" +" ("+tk1.getValue()+") ']'"+"("+currentTk.getValue()+")";	
				getToken();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void nome(){
//		Nome = NomeSimples Composicao
		String s="";
		producoes.add(s);
		s="Nome = NomeSimples Composicao";	
		nomeSimples();
		composicao();
	}
	
	public static void composicao(){
//		Composicao = '.' 'id' Composicao
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPPNT)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				s="Composicao = '.' "+" ("+tk1.getValue()+") 'id' "+" ("+currentTk.getValue()+")"+" Composicao";
				getToken();
				composicao();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void nomeSimples(){
//		NomeSimples = 'id'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("NomeSimples = 'id' "+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public static void modificador(){
//		Modificador  = 'static' 
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYSTC)){
			s=("Modificador = 'static'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
		}
	}
	
	public static void declaracaoDeClasse(){
//		DeclaracaoDeClasse = Modificador 'class' 'id' CorpoClasse
		String s="";
		producoes.add(s);
		modificador();
		if(currentTk.checkType(TokenType.KEYCLS)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				s=("DeclaracaoDeClasse = Modificador 'class'"+" ("+tk1.getValue()+") 'id'"+" ("+currentTk.getValue()+")"+" CorpoClasse");
				getToken();
				corpoClasse();
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void corpoClasse(){
//		CorpoClasse = '{' DeclaracoesCorpoClasse '}'
		String s="";
		producoes.add(s);		
		if(currentTk.checkType(TokenType.SEPACH)){
			Token tk1 = currentTk;
			getToken();
			declaracoesCorpoClasse();
			if(currentTk.checkType(TokenType.SEPFCH)){
				s=("CorpoClasse = '{'"+" ("+tk1.getValue()+") "+"DeclaracoesCorpoClasse '}'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			erro();
		}	
	}
	
	public static void declaracoesCorpoClasse(){
//		DeclaracoesCorpoClasse = DeclaracaoMembroClasse DeclaracaoCorpoClasse1
		String s="";
		producoes.add(s);
		s=("DeclaracoesCorpoClasse = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
		declaracaoMembroClasse();
		declaracaoCorpoClasse1();
	}
	
	public static void declaracaoCorpoClasse1(){
//		DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1 
//				| null
		String s="";
		producoes.add(s);
		if(!currentTk.checkType(TokenType.SEPFCH)){
			s=("DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
			declaracaoMembroClasse();
			declaracaoCorpoClasse1();
		}else{
			
		}
	}
	
	public static void declaracaoMembroClasse(){
//		DeclaracaoMembroClasse = DeclaracaoDeCampo 
//				| 'method' DeclaracaoDeMetodo
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYMTD)){
			s=("DeclaracaoMembroClasse = 'method'"+" ("+currentTk.getValue()+")"+" DeclaracaoDeMetodo");
			getToken();
			declaracaoDeMetodo();
		}else{
			s=("DeclaracaoMembroClasse = DeclaracaoDeCampo");
			declaracaoDeCampo();
		}
	}
	
	public static void declaracaoDeCampo(){
//		DeclaracaoDeCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYATR)){
			Token tk1 = currentTk;
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				s=("DeclaracaoDeCampo = 'atr' "+" ("+tk1.getValue()+")"+" Modificador Tipo DeclaracoesVariavel ';'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}
	}
	
	public static void declaracoesVariavel(){
//		DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1
		String s="";
		producoes.add(s);
		s=("DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1");
		declaracaoVariavel();
		declaracoesVariavel1();
	}
	
	public static void declaracoesVariavel1(){
//		DeclaracoesVariavel1 = ','  DeclaracaoVariavel DeclaracoesVariavel1 
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPVRG)){
			s=("DeclaracoesVariavel1 = ',' "+" ("+currentTk.getValue()+")"+" DeclaracaoVariavel DeclaracoesVariavel1 ");
			getToken();
			declaracaoVariavel();
			declaracoesVariavel1();
		}else{
		}
	}
	
	public static void declaracaoVariavel(){
		String s="";
		producoes.add(s);
//		DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1
		s=("DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1");
		declaracaoVariavelId();
		declaracaoVariavel1();
	}
	
	public static void declaracaoVariavel1(){
//		DeclaracaoVariavel1 = Atribuicao 
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("DeclaracaoVariavel1 = Atribuicao");
			expressaoAtribuicao();
		}else{
			
		}
	}
	
	public static void declaracaoVariavelId(){
//		DeclaracaoVariavelId = 'id' ArrayCol
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("DeclaracaoVariavelId = 'id'"+" ("+currentTk.getValue()+")"+" ArrayCol");
			getToken();
			arrayCol();
		}else{
			erro();
		}
	}
		
	public static void declaracaoDeMetodo(){
//		DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo
		String s="";
		producoes.add(s);
		s=("DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo");
		cabecalhoMetodo();
		corpoMetodo();
	}
	
	public static void cabecalhoMetodo(){
//		CabecalhoMetodo = Modificador Tipo MetodoDeclaracao
		String s="";
		producoes.add(s);
		s=("CabecalhoMetodo = Modificador Tipo MetodoDeclaracao");
		modificador();
		tipo();
		metodoDeclaracao();		
	}
	
	public static void metodoDeclaracao(){
//		MetodoDeclaracao = 'id' '[' ListaDeParametrosFormais ']'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYMAIN)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				listaDeParametrosFormais();
				if(currentTk.checkType(TokenType.SEPFCL)){
					s=("MetodoDeclaracao = 'id'"+" ("+tk1.getValue()
							+") '['"+" ("+tk2.getValue()+")"+" ListaDeParametrosFormais ']'"
							+" ("+currentTk.getValue()+")");
					getToken();
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}		
	}
	
	public static void listaDeParametrosFormais(){
//		ListaDeParametrosFormais = ParametrosFormais ListaDeParametrosFormais1
//				|null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)||currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)
				||currentTk.checkType(TokenType.KEYVOD)||currentTk.checkType(TokenType.KEYARY)){
			s=("ListaDeParametrosFormais = ParametrosFormais ListaDeParametrosFormais1");	
			parametrosFormais();
			listaDeParametrosFormais1();
		}else{
			
		}
	}
	
	public static void listaDeParametrosFormais1(){
//		ListaDeParametrosFormais1 = ',' ParametrosFormais ListaDeParametrosFormais
//				|null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPVRG)){
			s=("ListaDeParametrosFormais1 = ','"+" ("+currentTk.getValue()+")"+" ParametrosFormais ListaDeParametrosFormais");	
			getToken();
			parametrosFormais();
			listaDeParametrosFormais();
		}else{
			
		}
	}
	
	public static void parametrosFormais(){
//		ParametrosFormais = Tipo DeclaracaoVariavelId 
		String s="";
		producoes.add(s);
		s=("ParametrosFormais = Tipo DeclaracaoVariavelId");
		tipo();
		declaracaoVariavelId();
	}
	
	public static void corpoMetodo(){
//		CorpoMetodo = Bloco 
//				| ';'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPPEV)){
			s=("CorpoMetodo = ';'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			s=("CorpoMetodo = Bloco ");
			bloco();
		}
	}

	public static void bloco(){
//		Bloco = '{' DeclaracaoDeBloco '}'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPACH)){
			Token tk1 = currentTk;
			getToken();
			declaracaoDeBloco();
			if(currentTk.checkType(TokenType.SEPFCH)){
				s=("Bloco = '{'"+" ("+tk1.getValue()+")"+" DeclaracaoDeBloco '}'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void declaracaoDeBloco(){
//		DeclaracaoDeBloco = BlocoDeclaracao DeclaracaoDeBloco1
		String s="";
		producoes.add(s);
		s=("DeclaracaoDeBloco = BlocoDeclaracao DeclaracaoDeBloco1");
		blocoDeclaracao();
		declaracaoDeBloco1();
	}
	
	public static void declaracaoDeBloco1(){
//		DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1 
//				| null 
		String s="";
		producoes.add(s);
		if(!currentTk.checkType(TokenType.SEPFCH)){
			/*se o currentTk for '}' significa que o bloco acabou, logo estamos no desvio null
			 caso contrario, para qualquer outro token ainda estamos no bloco*/
			s=("DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1");
			declaracaoDeBloco();
			declaracaoDeBloco1();
		}else{
			
		}
	}
	
	public static void blocoDeclaracao(){
//		BlocoDeclaracao = DeclaracaoCampo 
//				| Declaracao
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYATR)){
			s=("BlocoDeclaracao = DeclaracaoCampo");
			declaracaoCampo();
		}else{
			s=("BlocoDeclaracao = Declaracao");
			declaracao();
		}
	}
	
	public static void declaracaoCampo(){
//		DeclaracaoCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYATR)){
			Token tk1 = currentTk;
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				s=("DeclaracaoCampo = 'atr'"+" ("+tk1.getValue()+")"
						+" Modificador Tipo DeclaracoesVariavel ';'"+" ("+currentTk.getValue()+")");
				getToken();				
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void declaracao(){
//		Declaracao = DeclaracaoSemSubDeclaracaoDireta 
//				| DeclaracaoIf 
//		        | DeclaracaoFor 
//		        | DeclaracaoWhile 
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYIF)){
			s=("Declaracao = DeclaracaoIf");
			declaracaoIf();
		}else if(currentTk.checkType(TokenType.KEYWHL)){
			s=("Declaracao = DeclaracaoWhile");
			declaracaoWhile();
		}else if(currentTk.checkType(TokenType.KEYFOR)){
			s=("Declaracao = DeclaracaoFor");
			declaracaoFor();
		}else{
			s=("Declaracao = DeclaracaoSemSubDeclaracaoDireta");
			declaracaoSemSubDeclaracaoDireta();
		}
	}
	
	public static void declaracaoSemSubDeclaracaoDireta(){
//		DeclaracaoSemSubDeclaracaoDireta = Bloco 
//				| ';' 
//				| ExpressaoDeclaracao 
//		        | DeclaracaoReturn 
//		        | DeclaracaoBreak
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPACH)){
			s=("DeclaracaoSemSubDeclaracaoDireta = Bloco");
			bloco();
		}else if(currentTk.checkType(TokenType.SEPPEV)){
			s=( "DeclaracaoSemSubDeclaracaoDireta = ';'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYRET)){
			s=("DeclaracaoSemSubDeclaracaoDireta = DeclaracaoReturn");
			declaracaoReturn();
		}else if(currentTk.checkType(TokenType.KEYBRK)){
			s=("DeclaracaoSemSubDeclaracaoDireta = DeclaracaoBreak");
			declaracaoBreak();
		}else{
			s=("DeclaracaoSemSubDeclaracaoDireta = ExpressaoDeclaracao");
			expressaoDeclaracao();
		}
	}
	
	public static void expressaoDeclaracao(){
//		ExpressaoDeclaracao = Atribuicao 
//				| ExpressaoIncrementoPre 
//		        | ExpressaoDecrementoPre 
//		        | ChamadaMetodo
//		        | ExpressaoCriaInstanciaDeClasse 
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYCLL)){
			s=("ExpressaoDeclaracao = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			s=("ExpressaoDeclaracao = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			s=("ExpressaoDeclaracao = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			s=("ExpressaoDeclaracao = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else{
			s=("ExpressaoDeclaracao = Atribuicao ");
			atribuicao();
		}
	}	
	
	public static void declaracaoIf(){
//		DeclaracaoIf = 'if' '[' ExpressaoCondicional ']'  Declaracao   DeclaracaoElse
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYIF)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
					s=("DeclaracaoIf = 'if'"+" ("+tk1.getValue()+") '['"
							+" ("+tk2.getValue()+")"+" ExpressaoCondicional ']'"
							+" ("+currentTk.getValue()+")"+"  Declaracao   DeclaracaoElse");
					getToken();
					declaracao();
					declaracaoElse();
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void declaracaoElse(){
//		DeclaracaoElse = 'else' Declaracao
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYELS)){
			s=("DeclaracaoElse = 'else'"+" ("+currentTk.getValue()+")"+" Declaracao");
			getToken();
			declaracao();
		}else{
			
		}
	}
	
	public static void declaracaoWhile(){
//		DeclaracaoWhile = 'while' '[' ExpressaoCondicional ']' Declaracao
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYWHL)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
					s=("DeclaracaoWhile = 'while'"+" ("+tk1.getValue()+") '['"
							+" ("+tk2.getValue()+") ExpressaoCondicional ']' Declaracao"
							+" ("+currentTk.getValue()+")");
					getToken();
					declaracao();
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void declaracaoFor(){
//		DeclaracaoFor = 'for' '[' InicializadorFor ';' LimiteSuperiorFor ';' PassoFor ']' Declaracao
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYFOR)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				inicializadorFor();
				if(currentTk.checkType(TokenType.SEPPEV)){
					Token tk3 = currentTk;
					getToken();
					limiteSuperiorFor();
					if(currentTk.checkType(TokenType.SEPPEV)){
						Token tk4 = currentTk;
						getToken();
						passoFor();
						if(currentTk.checkType(TokenType.SEPFCL)){
							s=("DeclaracaoFor = 'for'"+" ("+tk1.getValue()+") '['"
									+" ("+tk2.getValue()+") InicializadorFor ';'"+" ("+tk3.getValue()+")"
									+" LimiteSuperiorFor ';'"+" ("+tk4.getValue()+") PassoFor ']'"
									+" ("+currentTk.getValue()+")  Declaracao");
							getToken();
							declaracao();
						}else{
							erro();
						}
					}else{
						erro();
					}
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void inicializadorFor(){
//		InicializadorFor = 'int' 'id' '=' ValorInicializadorFor
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYINT)){
			Token tk1 = currentTk;
			getToken();			
			if(currentTk.checkType(TokenType.ID)){
				Token tk2 = currentTk;
				getToken();
				if(currentTk.checkType(TokenType.OPRATR)){
					s=("InicializadorFor = 'int'"+" ("+tk1.getValue()+") 'id'"
						+" ("+tk2.getValue()+") '='"+" ("+currentTk.getValue()+")"+" ValorInicializadorFor");
					getToken();
					valorInicializadorFor();
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void valorInicializadorFor(){
//		ValorInicializadorFor = Literal
//				| 'id'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("ValorInicializadorFor = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			s=("ValorInicializadorFor = Literal");
			literal();
		}
	}
	
	public static void limiteSuperiorFor(){
//		LimiteSuperiorFor = ConstanteNumerica 
//				| Nome
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			s=("LimiteSuperiorFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			s=("LimiteSuperiorFor = Nome");
			nome();
		}
	}
	
	public static void passoFor(){
//		PassoFor = ConstanteNumerica 
//				| Nome
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			s=("PassoFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			s=("PassoFor = Nome");
			nome();
		}
	}
	
	public static void declaracaoReturn(){
//		DeclaracaoReturn = 'return' ExpressaoAtribuicao ';' 
//				| 'return' ';'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYRET)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				s=("DeclaracaoReturn = 'return'"+" ("+tk1.getValue()+") ';'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
					s=("DeclaracaoReturn = 'return'"+" ("+tk1.getValue()+") ExpressaoAtribuicao ';' "
							+" ("+currentTk.getValue()+")");
					getToken();
				}else{
					erro();
				}
			}
		}else{
			erro();
		}
	}
	
	public static void declaracaoBreak(){
//		DeclaracaoBreak = 'break' ExpressaoAtribuicao ';' 
//				| 'break' ';'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYBRK)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				s=("DeclaracaoBreak = 'break'"+" ("+tk1.getValue()+") ';'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
					s=("DeclaracaoBreak = 'break'"+" ("+tk1.getValue()+") ExpressaoAtribuicao ';' "
							+" ("+currentTk.getValue()+")");
					getToken();
				}else{
					erro();
				}
			}
		}else{
			erro();
		}
	}
	
	public static void expressaoCriaInstanciaDeClasse(){
//		ExpressaoCriaInstanciaDeClasse = 'new' 'id' '[' ListaDeArgumentos ']'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYNEW)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				Token tk2 = currentTk;
				getToken();
				if(currentTk.checkType(TokenType.SEPACL)){
					Token tk3 = currentTk;
					getToken();
					listaDeArgumentos();
					if(currentTk.checkType(TokenType.SEPFCL)){
						s=("ExpressaoCriaInstanciaDeClasse = 'new'"+" ("+tk1.getValue()+") 'id'"
								+" ("+tk2.getValue()+") '[' ListaDeArgumentos"+" ("+tk3.getValue()
								+") ']'"+" ("+currentTk.getValue()+")");
						getToken();
					}else{
//						erro
					}
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void listaDeArgumentos(){
//		ListaDeArgumentos = ExpressaoAtribuicao   ListaDeArgumentos1
//				| null
		String s="";
		producoes.add(s);
		if(!currentTk.checkType(TokenType.SEPFCL)){
			s=("ListaDeArgumentos = ExpressaoAtribuicao   ListaDeArgumentos1");
			expressaoAtribuicao();
			listaDeArgumentos1();
		}else{
			
		}
		
	}
	
	public static void listaDeArgumentos1(){
//		ListaDeArgumentos1 = ',' ListaDeArgumentos   ListaDeArgumentos1 
//				| null 
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPVRG)){
			s=("ListaDeArgumentos1 = ','"+" ("+currentTk.getValue()+")"+" ListaDeArgumentos   ListaDeArgumentos1 ");
			getToken();
			listaDeArgumentos();
			listaDeArgumentos1();
		}else{
			
		}
	}
	
	public static void expressaoCriaArray(){
//		ExpressaoCriaArray = 'array' '[' Tipo ',' TamanhoArray ']'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYARY)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				tipo();
				if(currentTk.checkType(TokenType.SEPVRG)){
					Token tk3 = currentTk;
					getToken();
					tamanhoArray();
					if(currentTk.checkType(TokenType.SEPFCL)){
						s=("ExpressaoCriaArray = 'array'"+" ("+tk1.getValue()+") '[' Tipo"
								+" ("+tk2.getValue()+") ',' TamanhoArray"+" ("+tk3.getValue()
								+") ']'"+" ("+currentTk.getValue()+")");
						getToken();
					}else{
						erro();
					}
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void tamanhoArray(){
//		TamanhoArray = 'id'
//				| 'cntInt'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("TamanhoArray = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYINT)){
			s=("TamanhoArray = 'cntInt'"+" ("+currentTk.getValue()+")");
			getToken();
		}
	}
	
	public static void chamadaMetodo(){
//		ChamadaMetodo = 'call' NomeMetodo '[' ListaDeArgumentos ']'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYCLL)){
			Token tk1 = currentTk;
			getToken();	
			nomeMetodo();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				listaDeArgumentos();
				if(currentTk.checkType(TokenType.SEPFCL)){
					s=("ChamadaMetodo = 'call'"+" ("+tk1.getValue()+") NomeMetodo '['"
						+" ("+tk2.getValue()+") ListaDeArgumentos ']'"+" ("+currentTk.getValue()+")");
					getToken();				
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void nomeMetodo(){
//		NomeMetodo = Nome NomeMetodo1
//					| 'print'
//					| 'read'
		String s="";
		producoes.add(s);		
		if(currentTk.checkType(TokenType.KEYPRT)||currentTk.checkType(TokenType.KEYREA)){
			s=("NomeMetodo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			s=("NomeMetodo = Nome NomeMetodo1");
			nome();
			nomeMetodo1();
		}
	}
	
	public static void nomeMetodo1(){
//		NomeMetodo1 = '.' NomeMetodo
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPPNT)){
			s=("NomeMetodo1 = '.'"+" ("+currentTk.getValue()+")"+" NomeMetodo");
			getToken();
			nomeMetodo();
		}
	}
	
	public static void acessoArray(){
//		AcessoArray = 'aaray' Nome '[' ExpressaoAtribuicao ']'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYAAY)){
			Token tk1 = currentTk;
			getToken();	
			nome();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPFCL)){
					s=("AcessoArray = 'aaray'"+" ("+tk1.getValue()+") Nome '['"
						+" ("+tk2.getValue()+") ExpressaoAtribuicao ']'"+" ("+currentTk.getValue()+")");
					getToken();				
				}else{
					erro();
				}
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void primario(){
//		Primario = PrimairoSemNovoArray 
//				| ExpressaoCriaArray
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYARY)){
			s=("Primario = ExpressaoCriaArray ");
			expressaoCriaArray();
		}else{
			s=("Primario = PrimairoSemNovoArray ");
			primairoSemNovoArray();
		}
	}
	
	public static void primairoSemNovoArray(){
//		PrimairoSemNovoArray = Literal
//		    	| 'this' 
//		        | '[' ExpressaoAtribuicao ']'
//		        | ExpressaoCriaInstanciaDeClasse
//		    	| ChamadaMetodo 
//				| AcessoArray
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYTHS)){
			s=("PrimairoSemNovoArray = 'this'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.SEPACL)){
			Token tk1 = currentTk;
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				s=("PrimairoSemNovoArray = '['"+" ("+tk1.getValue()
					+") ExpressaoAtribuicao ']'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTSTR)||currentTk.checkType(TokenType.CNTCHR)||
				currentTk.checkType(TokenType.CTNINT)||currentTk.checkType(TokenType.CTNDBL)){
			s=("PrimairoSemNovoArray = Literal");
			literal();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			s=("PrimairoSemNovoArray = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else if(currentTk.checkType(TokenType.KEYCLL)){
			s=("PrimairoSemNovoArray = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.KEYAAY)){
			s=("PrimairoSemNovoArray = AcessoArray");
			acessoArray();
		}else{
			erro();
		}
	}
	
	public static void argumentos(){
//		Argumentos = '[' ExpressaoAtribuicao ']'
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.SEPACL)){
			Token tk1 = currentTk;
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				s=("Argumentos = '['"+" ("+tk1.getValue()+") ExpressaoAtribuicao ']'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void atribuicao(){
//		Atribuicao = LadoEsquerdo '=' ExpressaoAtribuicao 
		String s="";
		producoes.add(s);
		ladoEsquerdo();
		if(currentTk.checkType(TokenType.OPRATR)){
			s=("Atribuicao = LadoEsquerdo '='"+" ("+currentTk.getValue()+")"+" ExpressaoAtribuicao ");
			getToken();
			expressaoAtribuicao();
		}else{
			erro();
		}
	}
	
	public static void ladoEsquerdo(){
//		LadoEsquerdo = AcessoArray
//				| Nome1 Argumentos
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYAAY)){
			s=("LadoEsquerdo = AcessoArray");
			acessoArray();
		}else{
			s=("LadoEsquerdo = Nome1 Argumentos");
			nome1();
			argumentos();
		}
	}
	
	public static void nome1(){
//		Nome1 = 'id' Nome1
//				| Literal Nome1
//				| null	
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("Nome1 = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
			nome1();
		}else if(currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYLGC)||
				currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)){
			s=("Nome1 = Literal");
			getToken();
			nome1();
		}else{
			
		}
	}
	
	public static void expressaoAtribuicao(){
//		ExpressaoAtribuicao = ExpressaoCondicional
		String s="";
		producoes.add(s);
		s=("ExpressaoAtribuicao = ExpressaoCondicional");
		expressaoCondicional();
	}
	
	public static void expressaoCondicional(){
//		ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1
		String s="";
		producoes.add(s);
		s=("ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1");
		expressaoOuCondicional();
		expressaoCondicional1();
	}
	
	public static void expressaoCondicional1(){
//		ExpressaoCondicional1 = '?' Expressao ':' ExpressaoCondicional
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRTRI)){
			Token tk1 = currentTk;
			getToken();
			expressao();
			if(currentTk.checkType(TokenType.SEPDPT)){
				s=("ExpressaoCondicional1 = '?'"+" ("+tk1.getValue()
						+") Expressao ':'"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional");
				getToken();
				expressaoCondicional();
			}else{
				erro();
			}
		}
	}
	
	public static void expressaoOuCondicional(){
//		ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1
		String s="";
		producoes.add(s);
		s=("ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1");
		expressaoECondicional();
		expressaoOuCondicional1();
	}
	
	public static void expressaoOuCondicional1(){
//		ExpressaoOuCondicional1 = '||' ExpressaoCondicional   ExpressaoOuCondicional1 
//				| null 
		String s="";
		producoes.add(s);		
		if(currentTk.checkType(TokenType.OPROCD)){
			s=("ExpressaoOuCondicional1 = '||'"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional   ExpressaoOuCondicional1 ");
			getToken();
			expressaoCondicional();
			expressaoOuCondicional1();
		}else{
			
		}
	}
	
	public static void expressaoECondicional(){
//		ExpressaoECondicional = Expressao   ExpressaoECondicional1 
		String s="";
		producoes.add(s);
		s=("ExpressaoECondicional = Expressao   ExpressaoECondicional1");
		expressao();
		expressaoECondicional1();
	}
	
	public static void expressaoECondicional1(){
//		ExpressaoECondicional1 = '&&' Expressao   ExpressaoECondicional1
//				| null 
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRECD)){
			s=("ExpressaoECondicional1 = '&&'"+" ("+currentTk.getValue()+")"+" Expressao   ExpressaoECondicional1");
			getToken();
			expressao();
			expressaoECondicional1();
		}else{
		}
	}
	
	public static void expressao(){
//		Expressao = ExpressaoEqualidade   Expressao1
		String s="";
		producoes.add(s);
		s=("Expressao = ExpressaoEqualidade   Expressao1");
		expressaoEqualidade();
		expressao1();
	}
	
	public static void expressao1(){
//		Expressao1 = '&' ExpressaoEqualidade   ExpressaoEqualidade1 
//				| null 
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRE)){
			s=("Expressao1 = '&'"+" ("+currentTk.getValue()+")"+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			
		}
	}
	
	public static void expressaoEqualidade(){
//		ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1
		String s="";
		producoes.add(s);
		s=("ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1");
		expressaoRelacional();
		expressaoEqualidade1();
	}
	
	public static void expressaoEqualidade1(){
//		ExpressaoEqualidade1 = '==' ExpressaoEqualidade   ExpressaoEqualidade1  
//			    | '!=' ExpressaoEqualidade   ExpressaoEqualidade1 
//			    | null 
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)){
			s=("ExpressaoEqualidade1 ="+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			
		}
	}
	
	public static void expressaoRelacional(){
//		ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1
		String s="";
		producoes.add(s);
		s=("ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1");
		expressaoAditiva();
		expressaoRelacional1();
	}
	
	public static void expressaoRelacional1(){
//		ExpressaoRelacional1 =  ExpressaoMenor 
//				| ExpressaoMaior 
//				| ExpressaoMenorOuIgual 
//				| ExpressaoMaiorOuIgual 
//				| ExpressaoInstance	
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMNR)){
			s=("ExpressaoRelacional1 = ExpressaoMenor");
			expressaoMenor();
		}else if(currentTk.checkType(TokenType.OPRMAR)){
			s=("ExpressaoRelacional1 = ExpressaoMaior");
			expressaoMaior();
		}else if(currentTk.checkType(TokenType.OPRMEI)){
			s=("ExpressaoRelacional1 = ExpressaoMenorOuIgual");
			expressaoMenorOuIgual();
		}else if(currentTk.checkType(TokenType.OPRMAI)){
			s=("ExpressaoRelacional1 = ExpressaoMaiorOuIgual");
			expressaoMaiorOuIgual();
		}else if(currentTk.checkType(TokenType.KEYIOF)){
			s=("ExpressaoRelacional1 = ExpressaoInstance");
			expressaoInstance();
		}else{
			
		}
	}
	
	public static void expressaoMenor(){
//		ExpressaoMenor = '<' ExpressaoAditiva
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMNR)){
			s=("ExpressaoMenor = '<'"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMaior(){
//		ExpressaoMaior = '<' ExpressaoAditiva
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMAR)){
			s=("ExpressaoMaior = '<'"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMenorOuIgual(){
//		ExpressaoMenorOuIgual = '<=' ExpressaoAditiva
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMEI)){
			s=("ExpressaoMenorOuIgual =  '<='"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMaiorOuIgual(){
//		ExpressaoMaiorOuIgual = '>=' ExpressaoAditiva
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMAI)){
			s=("ExpressaoMaiorOuIgual =  '>='"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoInstance(){
//		ExpressaoInstance = 'instanceof' 'id'
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.KEYIOF)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){	
				s=("ExpressaoInstance = 'instanceof'"+" ("+tk1.getValue()
						+") 'id'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public static void expressaoAditiva(){
//		ExpressaoAditiva = ExpressaoMultiplicacao   ExpressaoAditiva1
		String s="";
		producoes.add(s);
		s=("ExpressaoAditiva = ExpressaoMultiplicacao   ExpressaoAditiva1");
		expressaoMultiplicativa();
		expressaoAditiva1();
	}
	
	public static void expressaoAditiva1(){
//		ExpressaoAditiva1 = '+' ExpressaoMultiplicacao   ExpressaoAditiva1
//		        |  '-' ExpressaoMultiplicacao   ExpressaoAditiva1 
//		        | null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			s=("ExpressaoAditiva1 = "+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoMultiplicacao   ExpressaoAditiva1");
			expressaoMultiplicativa();
			expressaoAditiva1();
		}
	}
	
	public static void expressaoMultiplicativa(){
//		ExpressaoMultiplicativa = ExpressaoUnaria   ExpressaoMultiplicativa1
		String s="";
		producoes.add(s);
		s=("ExpressaoMultiplicacao = ExpressaoUnaria   ExpressaoMultiplicacao1");
		expressaoUnaria();
		expressaoMultiplicativa1();
	}
	
	public static void expressaoMultiplicativa1(){
//		ExpressaoMultiplicativa1 = ExpressaoMultiplicacao 
//				| ExpressaoDivisao 
//				| ExpressaoModulo
//				| null	
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMTL)){
			s=("ExpressaoMultiplicacao1 = ExpressaoMultiplicacao ");
			expressaoMultiplicacao();
		}else if(currentTk.checkType(TokenType.OPRDIV)){
			s=("ExpressaoMultiplicacao1 = ExpressaoDivisao ");
			expressaoDivisao();
		}else if(currentTk.checkType(TokenType.OPRMOD)){
			s=("ExpressaoMultiplicacao1 = ExpressaoModulo ");
			expressaoModulo();
		}else{
			
		}
	}
	
	public static void expressaoMultiplicacao(){
//		ExpressaoMultiplicacao = '*' ExpressaoUnaria   ExpressaoMultiplicativa
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMTL)){
			s=("ExpressaoMultiplicacao = '*'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoDivisao(){
//		ExpressaoDivisao = '/' ExpressaoUnaria   ExpressaoMultiplicativa
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRDIV)){
			s=("ExpressaoDivisao = '/'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoModulo(){
//		ExpressaoModulo = '%' ExpressaoUnaria   ExpressaoMultiplicativa
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMOD)){
			s=("ExpressaoModulo = '%'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoIncrementoPre(){
//		ExpressaoIncrementoPre = '++' ExpressaoUnaria
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMMA)){
			s=("ExpressaoIncrementoPre = '++'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}
	}
	
	public static void expressaoDecrementoPre(){
//		ExpressaoDecrementoPre = '--' ExpressaoUnaria
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMME)){
			s=("ExpressaoDecrementoPre = '--'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}
	}
	
	public static void expressaoUnaria(){
//		ExpressaoUnaria = ExpressaoIncrementoPre
//				| ExpressaoDecrementoPre 
//				| '+' ExpressaoUnaria
//				| '-' ExpressaoUnaria 
//				| ExpressaoPosFixada
		String s="";
		producoes.add(s);		
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			s=("ExpressaoUnaria = "+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			s=("ExpressaoUnaria = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			s=("ExpressaoUnaria = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else{
			s=("ExpressaoUnaria = ExpressaoPosFixada");
			expressaoPosFixada();
		}	
	}
	
	public static void expressaoIncrementoPos(){
//		ExpressaoIncrementoPos = ExpressaoPosFixada '++'
		String s="";
		producoes.add(s);
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMMA)){
			s=("ExpressaoIncrementoPos = ExpressaoPosFixada '++'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public static void expressaoDecrementoPos(){
		String s="";
		producoes.add(s);
//		ExpressaoDecrementoPos = ExpressaoPosFixada '--'
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMME)){
			s=("ExpressaoDecrementoPos = ExpressaoPosFixada '--'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
				erro();
		}
	}
	
	public static void expressaoPosFixada(){
//		ExpressaoPosFixada = Primario PosFixo
//				| Nome PosFixo
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.ID)){
			s=("ExpressaoPosFixada = Nome PosFixo");
			nome();
			posFixo();
		}else{
			s=("ExpressaoPosFixada = Primario PosFixo");
			primario();
			posFixo();
		}
	}
	
	public static void posFixo(){
//		PoxFixo = '++'
//				| '--'
//				| null
		String s="";
		producoes.add(s);
		if(currentTk.checkType(TokenType.OPRMMA)||currentTk.checkType(TokenType.OPRMME)){
			s=("PoxFixo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			
		}
	}
}	
