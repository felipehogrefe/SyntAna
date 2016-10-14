package SyntAna;

import java.io.PrintWriter;

import LexAna.LexicalAnalyzer;
import LexAna.Token;
import LexAna.TokenType;

public class SyntaticAnalyzer {
	static LexicalAnalyzer la;
	static Token currentTk;
	private static PrintWriter gravarArq;
	
	private static void getToken(){
		if(la.isOver()){
			currentTk = la.nextToken();
		}
		System.out.println(currentTk.getValue());
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
	
	public static void literal(){
//		Literal = Constante 
//				| ConstanteNumerica
		
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			escreveln("Constante");
			constante();
		}else if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			escreveln("Literal = ConstanteNumerica");
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
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			escreveln("ConstanteNumerica = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
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
		if(currentTk.checkType(TokenType.KEYARY)){
			getToken();
			escreveln("Tipo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");	
		}else if(currentTk.checkType(TokenType.KEYVOD)){
			getToken();
			escreveln("Tipo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");	
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)||
				currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)){
			escreveln("Tipo = TipoPrimitivo");	
			tipoPrimitivo();
		}else if(currentTk.checkType(TokenType.ID)){
			escreveln("Tipo = Nome");	
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
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			escreveln("TipoPrimitivo = TipoNumerico");	
			tipoNumerico();
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)){
			escreveln("TipoPrimitivo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public static void tipoNumerico(){
//		TipoNumerico =  'tkInt' 
//				| 'tkDbl'
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			escreveln("TipoNumerico = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}	
	
	public static void arrayCol(){
//		ArrayCol =  '[' ']' ArrayCol
//				| null	
		if(currentTk.checkType(TokenType.SEPACL)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				System.out.println("ArrayCol = '[' ']'");	
				getToken();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void nome(){
//		Nome = NomeSimples Composicao
		nomeSimples();
		composicao();
	}
	
	public static void composicao(){
//		Composicao = '.' 'id' Composicao
//				| null
		if(currentTk.checkType(TokenType.SEPPNT)){
			System.out.println("Composicao = '.' 'id' Composicao");
			getToken();
			nome();
		}else{
			
		}
	}
	
	public static void nomeSimples(){
//		NomeSimples = 'id'
		if(currentTk.checkType(TokenType.ID)){
			getToken();
		}else{
			erro();
		}
	}
	
	public static void modificador(){
//		Modificador  = 'static' 
//				| null
		if(currentTk.checkType(TokenType.KEYSTC)){
			System.out.println("Modificador = 'static'");
			getToken();
		}else{
		}
	}
	
	public void start(){
//		UnidadeDeCompilacao = DeclaracaoDeClasse
		System.out.println("UnidadeDeCompilacao = DeclaracaoDeClasse");
		getToken();
		declaracaoDeClasse();
	}
	
	public static void declaracaoDeClasse(){
//		DeclaracaoDeClasse = Modificador 'class' 'id' CorpoClasse
		System.out.println("DeclaracaoDeClasse = Modificador 'class' 'id' CorpoClasse");
		modificador();
		if(currentTk.checkType(TokenType.KEYCLS)){
			getToken();
			if(currentTk.checkType(TokenType.ID)){
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
		System.out.println("CorpoClasse = '{' DeclaracoesCorpoClasse '}'");
		if(currentTk.checkType(TokenType.SEPACH)){
			getToken();
			declaracoesCorpoClasse();
			if(currentTk.checkType(TokenType.SEPFCH)){
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
		System.out.println("DeclaracoesCorpoClasse = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
		declaracaoMembroClasse();
		declaracaoCorpoClasse1();
	}
	
	public static void declaracaoCorpoClasse1(){
//		DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1 
//				| null
		if(!currentTk.checkType(TokenType.SEPFCH)){
			System.out.println("DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
			declaracaoMembroClasse();
			declaracaoCorpoClasse1();
		}else{
			
		}
	}
	
	public static void declaracaoMembroClasse(){
//		DeclaracaoMembroClasse = DeclaracaoDeCampo 
//				| 'method' DeclaracaoDeMetodo
		System.out.print("DeclaracaoMembroClasse = ");
		if(currentTk.checkType(TokenType.KEYMTD)){
			System.out.println("'method' DeclaracaoDeMetodo");
			getToken();
			declaracaoDeMetodo();
		}else{
			System.out.println("DeclaracaoDeCampo");
			declaracaoDeCampo();
		}
	}
	
	public static void declaracaoDeCampo(){
//		DeclaracaoDeCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		System.out.println("DeclaracaoDeCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'");
		if(currentTk.checkType(TokenType.KEYATR)){
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				getToken();
			}else{
				erro();
			}
		}
	}
	
	public static void declaracoesVariavel(){
//		DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1
		System.out.println("DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1");
		declaracaoVariavel();
		declaracoesVariavel1();
	}
	
	public static void declaracoesVariavel1(){
//		DeclaracoesVariavel1 = ','  DeclaracaoVariavel DeclaracoesVariavel1 
//				| null
		if(currentTk.checkType(TokenType.SEPVRG)){
			System.out.println("DeclaracoesVariavel1 = ','  DeclaracaoVariavel DeclaracoesVariavel1 ");
			getToken();
			declaracaoVariavel();
			declaracoesVariavel1();
		}else{
		}
	}
	
	public static void declaracaoVariavel(){
//		DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1
		System.out.println("DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1");
		declaracaoVariavelId();
		declaracaoVariavel1();
	}
	
	public static void declaracaoVariavel1(){
//		DeclaracaoVariavel1 = Atribuicao 
//				| null
		if(currentTk.checkType(TokenType.ID)){
			System.out.println("DeclaracaoVariavel1 = Atribuicao");
			expressaoAtribuicao();
		}else{
			
		}
	}
	
	public static void declaracaoVariavelId(){
//		DeclaracaoVariavelId = 'id' ArrayCol
		System.out.println("DeclaracaoVariavelId = 'id' ArrayCol");
		if(currentTk.checkType(TokenType.ID)){
			getToken();
			arrayCol();
		}else{
			erro();
		}
	}
		
	public static void declaracaoDeMetodo(){
//		DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo
		System.out.println("DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo");
		cabecalhoMetodo();
		corpoMetodo();
	}
	
	public static void cabecalhoMetodo(){
//		CabecalhoMetodo = Modificador Tipo MetodoDeclaracao
		System.out.println("CabecalhoMetodo = Modificador Tipo MetodoDeclaracao");
		modificador();
		tipo();
		metodoDeclaracao();		
	}
	
	public static void metodoDeclaracao(){
//		MetodoDeclaracao = 'id' '[' ListaDeParametrosFormais ']'
		System.out.println("MetodoDeclaracao = 'id' '[' ListaDeParametrosFormais ']'");
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYMAIN)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				listaDeParametrosFormais();
				if(currentTk.checkType(TokenType.SEPFCL)){
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
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)||currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)
				||currentTk.checkType(TokenType.KEYVOD)||currentTk.checkType(TokenType.KEYARY)){
			System.out.println("ListaDeParametrosFormais = ParametrosFormais ListaDeParametrosFormais1");	
			parametrosFormais();
			listaDeParametrosFormais1();
		}else{
			
		}
	}
	
	public static void listaDeParametrosFormais1(){
//		ListaDeParametrosFormais1 = ',' ParametrosFormais ListaDeParametrosFormais
//				|null
		if(currentTk.checkType(TokenType.SEPVRG)){
			System.out.println("ListaDeParametrosFormais1 = ',' ParametrosFormais ListaDeParametrosFormais");	
			getToken();
			parametrosFormais();
			listaDeParametrosFormais();
		}else{
			
		}
	}
	
	public static void parametrosFormais(){
//		ParametrosFormais = Tipo DeclaracaoVariavelId 
		System.out.println("ParametrosFormais = Tipo DeclaracaoVariavelId");
		tipo();
		declaracaoVariavelId();
	}
	
	public static void corpoMetodo(){
//		CorpoMetodo = Bloco 
//				| ';'
		if(currentTk.checkType(TokenType.SEPPEV)){
			System.out.println("CorpoMetodo = ';'");
			getToken();
		}else{
			System.out.println("CorpoMetodo = Bloco ");
			bloco();
		}
	}

	public static void bloco(){
//		Bloco = '{' DeclaracaoDeBloco '}'
		System.out.println("Bloco = '{' DeclaracaoDeBloco '}'");
		if(currentTk.checkType(TokenType.SEPACH)){
			getToken();
			declaracaoDeBloco();
			if(currentTk.checkType(TokenType.SEPFCH)){
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
		System.out.println("DeclaracaoDeBloco = BlocoDeclaracao DeclaracaoDeBloco1");
		blocoDeclaracao();
		declaracaoDeBloco1();
	}
	
	public static void declaracaoDeBloco1(){
//		DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1 
//				| null 
		if(!currentTk.checkType(TokenType.SEPFCH)){
			/*se o currentTk for '}' significa que o bloco acabou, logo estamos no desvio null
			 caso contrario, para qualquer outro token ainda estamos no bloco*/
			System.out.println("DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1");
			declaracaoDeBloco();
			declaracaoDeBloco1();
		}else{
			
		}
	}
	
	public static void blocoDeclaracao(){
//		BlocoDeclaracao = DeclaracaoCampo 
//				| Declaracao
		if(currentTk.checkType(TokenType.KEYATR)){
			System.out.println("BlocoDeclaracao = DeclaracaoCampo");
			declaracaoCampo();
		}else{
			System.out.println("BlocoDeclaracao = Declaracao");
			declaracao();
		}
	}
	
	public static void declaracaoCampo(){
//		DeclaracaoCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		if(currentTk.checkType(TokenType.KEYATR)){
			System.out.println("'atr' Modificador Tipo DeclaracoesVariavel ';'");
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
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
		if(currentTk.checkType(TokenType.KEYIF)){
			System.out.println("Declaracao = DeclaracaoIf");
			declaracaoIf();
		}else if(currentTk.checkType(TokenType.KEYWHL)){
			System.out.println("Declaracao = DeclaracaoWhile");
			declaracaoWhile();
		}else if(currentTk.checkType(TokenType.KEYFOR)){
			System.out.println("Declaracao = DeclaracaoFor");
			declaracaoFor();
		}else{
			System.out.println("Declaracao = DeclaracaoSemSubDeclaracaoDireta");
			declaracaoSemSubDeclaracaoDireta();
		}
	}
	
	public static void declaracaoSemSubDeclaracaoDireta(){
//		DeclaracaoSemSubDeclaracaoDireta = Bloco 
//				| ';' 
//				| ExpressaoDeclaracao 
//		        | DeclaracaoReturn 
//		        | DeclaracaoBreak
		if(currentTk.checkType(TokenType.SEPACH)){
			System.out.println("DeclaracaoSemSubDeclaracaoDireta = Bloco");
			bloco();
		}else if(currentTk.checkType(TokenType.SEPPEV)){
			System.out.println( "DeclaracaoSemSubDeclaracaoDireta = \';\'");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYRET)){
			System.out.println("DeclaracaoSemSubDeclaracaoDireta = DeclaracaoReturn");
			declaracaoReturn();
		}else if(currentTk.checkType(TokenType.KEYBRK)){
			System.out.println("DeclaracaoSemSubDeclaracaoDireta = DeclaracaoBreak");
			declaracaoBreak();
		}else{
			System.out.println("DeclaracaoSemSubDeclaracaoDireta = ExpressaoDeclaracao");
			expressaoDeclaracao();
		}
	}
	
	public static void expressaoDeclaracao(){
//		ExpressaoDeclaracao = Atribuicao 
//				| ExpressaoIncrementoPre 
//		        | ExpressaoDecrementoPre 
//		        | ChamadaMetodo
//		        | ExpressaoCriaInstanciaDeClasse 

		if(currentTk.checkType(TokenType.KEYCLL)){
			System.out.println("ExpressaoDeclaracao = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			System.out.println("ExpressaoDeclaracao = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			System.out.println("ExpressaoDeclaracao = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			System.out.println("ExpressaoDeclaracao = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else{
			System.out.println("ExpressaoDeclaracao = Atribuicao ");
			atribuicao();
		}
	}	
	
	public static void declaracaoIf(){
//		DeclaracaoIf = 'if' '[' ExpressaoCondicional ']'  Declaracao   DeclaracaoElse
		System.out.println("DeclaracaoIf = 'if' '[' ExpressaoCondicional ']'  Declaracao   DeclaracaoElse");
		if(currentTk.checkType(TokenType.KEYIF)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
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
		if(currentTk.checkType(TokenType.KEYELS)){
			System.out.println("DeclaracaoElse = 'else' Declaracao");
			getToken();
			declaracao();
		}else{
			
		}
	}
	
	public static void declaracaoWhile(){
//		DeclaracaoWhile = 'while' '[' ExpressaoCondicional ']' Declaracao
		System.out.println("DeclaracaoWhile = 'while' '[' ExpressaoCondicional ']' Declaracao");
		if(currentTk.checkType(TokenType.KEYWHL)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
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
		System.out.println("DeclaracaoFor = 'for' '[' InicializadorFor ';' LimiteSuperiorFor ';' PassoFor ']' Declaracao");
		if(currentTk.checkType(TokenType.KEYFOR)){
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				inicializadorFor();
				if(currentTk.checkType(TokenType.SEPPEV)){
					getToken();
					limiteSuperiorFor();
					if(currentTk.checkType(TokenType.SEPPEV)){
						getToken();
						passoFor();
						if(currentTk.checkType(TokenType.SEPFCL)){
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
		System.out.println("InicializadorFor = 'int' 'id' '=' ValorInicializadorFor");
		if(currentTk.checkType(TokenType.KEYINT)){
			getToken();			
			if(currentTk.checkType(TokenType.ID)){
				getToken();
				if(currentTk.checkType(TokenType.OPRATR)){
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
		if(currentTk.checkType(TokenType.ID)){
			System.out.println("ValorInicializadorFor = 'id'");
			getToken();
		}else{
			System.out.println("ValorInicializadorFor = Literal");
			literal();
		}
	}
	
	public static void limiteSuperiorFor(){
//		LimiteSuperiorFor = ConstanteNumerica 
//				| Nome
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			System.out.println("LimiteSuperiorFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			System.out.println("LimiteSuperiorFor = Nome");
			nome();
		}
	}
	
	public static void passoFor(){
//		PassoFor = ConstanteNumerica 
//				| Nome
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			System.out.println("PassoFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			System.out.println("PassoFor = Nome");
			nome();
		}
	}
	
	public static void declaracaoReturn(){
//		DeclaracaoReturn = 'return' ExpressaoAtribuicao ';' 
//				| 'return' ';'
		if(currentTk.checkType(TokenType.KEYRET)){
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				System.out.println("DeclaracaoReturn = 'return' ';' ");
				getToken();
			}else{
				System.out.println("DeclaracaoReturn = 'return' ExpressaoAtribuicao ';'");
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
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
		if(currentTk.checkType(TokenType.KEYBRK)){
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				System.out.println("DeclaracaoReturn = 'break' ';' ");
				getToken();
			}else{
				System.out.println("DeclaracaoReturn = 'break' ExpressaoAtribuicao ';'");
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
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
		System.out.println("ExpressaoCriaInstanciaDeClasse = 'new' 'id' '[' ListaDeArgumentos ']'");
		if(currentTk.checkType(TokenType.KEYNEW)){
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				getToken();
				if(currentTk.checkType(TokenType.SEPACL)){
					getToken();
					listaDeArgumentos();
					if(currentTk.checkType(TokenType.SEPFCL)){
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
		if(!currentTk.checkType(TokenType.SEPFCL)){
			System.out.println("ListaDeArgumentos = ExpressaoAtribuicao   ListaDeArgumentos1");
			expressaoAtribuicao();
			listaDeArgumentos1();
		}else{
			
		}
		
	}
	
	public static void listaDeArgumentos1(){
//		ListaDeArgumentos1 = ',' ListaDeArgumentos   ListaDeArgumentos1 
//				| null 
		if(currentTk.checkType(TokenType.SEPVRG)){
			System.out.println("ListaDeArgumentos1 = ',' ListaDeArgumentos   ListaDeArgumentos1 ");
			getToken();
			listaDeArgumentos();
			listaDeArgumentos1();
		}else{
			
		}
	}
	
	public static void expressaoCriaArray(){
//		ExpressaoCriaArray = 'array' '[' Tipo ',' TamanhoArray ']'
		if(currentTk.checkType(TokenType.KEYARY)){
			System.out.println("ExpressaoCriaArray = 'array' '[' Tipo ',' TtamanhoArray ']'");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				tipo();
				if(currentTk.checkType(TokenType.SEPVRG)){
					getToken();
					tamanhoArray();
					if(currentTk.checkType(TokenType.SEPFCL)){
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
		if(currentTk.checkType(TokenType.ID)){
			System.out.println("TamanhoArray = 'id'");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYINT)){
			System.out.println("TamanhoArray = 'cntInt'");
			getToken();
		}
	}
	
	public static void chamadaMetodo(){
//		ChamadaMetodo = 'call' NomeMetodo '[' ListaDeArgumentos ']'
		System.out.println("ChamadaMetodo = 'call' NomeMetodo '[' ListaDeArgumentos ']'");
		
		if(currentTk.checkType(TokenType.KEYCLL)){
			getToken();	
			nomeMetodo();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				listaDeArgumentos();
				if(currentTk.checkType(TokenType.SEPFCL)){
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
		
		if(currentTk.checkType(TokenType.KEYPRT)||currentTk.checkType(TokenType.KEYREA)){
			System.out.println("NomeMetodo = "+currentTk.getValue());
			getToken();
		}else{
			System.out.println("NomeMetodo = Nome NomeMetodo1");
			nome();
			nomeMetodo1();
		}
	}
	
	public static void nomeMetodo1(){
//		NomeMetodo1 = '.' NomeMetodo
//				| null
		if(currentTk.checkType(TokenType.SEPPNT)){
			System.out.println("NomeMetodo1 = '.' NomeMetodo");
			getToken();
			nomeMetodo();
		}
	}
	
	public static void acessoArray(){
//		AcessoArray = 'aaray' Nome '[' ExpressaoAtribuicao ']'
		System.out.println("AcessoArray = 'aaray' Nome '[' ExpressaoAtribuicao ']'");
		if(currentTk.checkType(TokenType.KEYAAY)){
			getToken();	
			nome();
			if(currentTk.checkType(TokenType.SEPACL)){
				getToken();
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPFCL)){
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
		if(currentTk.checkType(TokenType.KEYARY)){
			System.out.println("Primario = ExpressaoCriaArray ");
			expressaoCriaArray();
		}else{
			System.out.println("Primario = PrimairoSemNovoArray ");
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

		if(currentTk.checkType(TokenType.KEYTHS)){
			System.out.println("PrimairoSemNovoArray = 'this'");
			getToken();
		}else if(currentTk.checkType(TokenType.SEPACL)){
			System.out.println("PrimairoSemNovoArray = '[' ExpressaoAtribuicao ']'");
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				getToken();
			}else{
				erro();
			}
		}else if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTSTR)||currentTk.checkType(TokenType.CNTCHR)||
				currentTk.checkType(TokenType.CTNINT)||currentTk.checkType(TokenType.CTNDBL)){
			System.out.println("PrimairoSemNovoArray = Literal");
			literal();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			System.out.println("PrimairoSemNovoArray = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else if(currentTk.checkType(TokenType.KEYCLL)){
			System.out.println("PrimairoSemNovoArray = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.KEYAAY)){
			System.out.println("PrimairoSemNovoArray = AcessoArray");
			acessoArray();
		}else{
			erro();
		}
	}
	
	public static void argumentos(){
//		Argumentos = '[' ExpressaoAtribuicao ']'
//				| null
		if(currentTk.checkType(TokenType.SEPACL)){
			System.out.println("Argumentos = '[' ExpressaoAtribuicao ']'");
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				getToken();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void atribuicao(){
//		Atribuicao = LadoEsquerdo '=' ExpressaoAtribuicao 
		System.out.println("Atribuicao = LadoEsquerdo '=' ExpressaoAtribuicao");
		ladoEsquerdo();
		if(currentTk.checkType(TokenType.OPRATR)){
			getToken();
			expressaoAtribuicao();
		}else{
			erro();
		}
	}
	
	public static void ladoEsquerdo(){
//		LadoEsquerdo = AcessoArray
//				| Nome1 Argumentos
		if(currentTk.checkType(TokenType.KEYAAY)){
			System.out.println("LadoEsquerdo = AcessoArray");
			acessoArray();
		}else{
			nome1();
			argumentos();
		}
	}
	
	public static void nome1(){
//		Nome1 = 'id' Nome1
//				| Literal Nome1
//				| null	
		if(currentTk.checkType(TokenType.ID)){
			System.out.println("Nome1 = 'id'");
			getToken();
			nome1();
		}else if(currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYLGC)||
				currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)){
			getToken();
			System.out.println("Nome1 = Literal");
			nome1();
		}else{
			
		}
	}
	
	public static void expressaoAtribuicao(){
//		ExpressaoAtribuicao = ExpressaoCondicional
		System.out.println("ExpressaoAtribuicao = ExpressaoCondicional");
		expressaoCondicional();
	}
	
	public static void expressaoCondicional(){
//		ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1
		System.out.println("ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1");
		expressaoOuCondicional();
		expressaoCondicional1();
	}
	
	public static void expressaoCondicional1(){
//		ExpressaoCondicional1 = '?' Expressao ':' ExpressaoCondicional
//				| null
		if(currentTk.checkType(TokenType.OPRTRI)){
			System.out.println("ExpressaoCondicional1 = '?' Expressao ':' ExpressaoCondicional");
			getToken();
			expressao();
			if(currentTk.checkType(TokenType.SEPDPT)){
				getToken();
				expressaoCondicional();
			}else{
				erro();
			}
		}
	}
	
	public static void expressaoOuCondicional(){
//		ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1
		System.out.println("ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1");
		expressaoECondicional();
		expressaoOuCondicional1();
	}
	
	public static void expressaoOuCondicional1(){
//		ExpressaoOuCondicional1 = '||' ExpressaoCondicional   ExpressaoOuCondicional1 
//				| null 
		
		if(currentTk.checkType(TokenType.OPROCD)){
			System.out.println("ExpressaoOuCondicional1 = '||' ExpressaoCondicional   ExpressaoOuCondicional1 ");
			getToken();
			expressaoCondicional();
			expressaoOuCondicional1();
		}else{
			
		}
	}
	
	public static void expressaoECondicional(){
//		ExpressaoECondicional = Expressao   ExpressaoECondicional1 
		System.out.println("ExpressaoECondicional = Expressao   ExpressaoECondicional1");
		expressao();
		expressaoECondicional1();
	}
	
	public static void expressaoECondicional1(){
//		ExpressaoECondicional1 = '&&' Expressao   ExpressaoECondicional1
//				| null 
		if(currentTk.checkType(TokenType.OPRECD)){
			System.out.println("ExpressaoECondicional1 = '&&' Expressao   ExpressaoECondicional1");
			getToken();
			expressao();
			expressaoECondicional1();
		}else{
		}
	}
	
	public static void expressao(){
//		Expressao = ExpressaoEqualidade   Expressao1
		System.out.println("Expressao = ExpressaoEqualidade   Expressao1");
		expressaoEqualidade();
		expressao1();
	}
	
	public static void expressao1(){
//		Expressao1 = '&' ExpressaoEqualidade   ExpressaoEqualidade1 
//				| null 
		if(currentTk.checkType(TokenType.OPRE)){
			System.out.println("Expressao1 = '&' ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			
		}
	}
	
	public static void expressaoEqualidade(){
//		ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1
		System.out.println("ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1");
		expressaoRelacional();
		expressaoEqualidade1();
	}
	
	public static void expressaoEqualidade1(){
//		ExpressaoEqualidade1 = '==' ExpressaoEqualidade   ExpressaoEqualidade1  
//			    | '!=' ExpressaoEqualidade   ExpressaoEqualidade1 
//			    | null 
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)){
			System.out.println("ExpressaoEqualidade1 ="+currentTk.getValue()+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			
		}
	}
	
	public static void expressaoRelacional(){
//		ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1
		System.out.println("ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1");
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
		if(currentTk.checkType(TokenType.OPRMNR)){
			System.out.println("ExpressaoRelacional1 = ExpressaoMenor");
			expressaoMenor();
		}else if(currentTk.checkType(TokenType.OPRMAR)){
			System.out.println("ExpressaoRelacional1 = ExpressaoMaior");
			expressaoMaior();
		}else if(currentTk.checkType(TokenType.OPRMEI)){
			System.out.println("ExpressaoRelacional1 = ExpressaoMenorOuIgual");
			expressaoMenorOuIgual();
		}else if(currentTk.checkType(TokenType.OPRMAI)){
			System.out.println("ExpressaoRelacional1 = ExpressaoMaiorOuIgual");
			expressaoMaiorOuIgual();
		}else if(currentTk.checkType(TokenType.KEYIOF)){
			System.out.println("ExpressaoRelacional1 = ExpressaoInstance");
			expressaoInstance();
		}else{
			
		}
	}
	
	public static void expressaoMenor(){
//		ExpressaoMenor = '<' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMNR)){
			System.out.println("ExpressaoMenor = '<' ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMaior(){
//		ExpressaoMaior = '<' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMAR)){
			System.out.println("ExpressaoMaior = '<' ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMenorOuIgual(){
//		ExpressaoMenorOuIgual = '<=' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMEI)){
			System.out.println("ExpressaoMenorOuIgual =  '<=' ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMaiorOuIgual(){
//		ExpressaoMaiorOuIgual = '<=' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMAI)){
			System.out.println("ExpressaoMaiorOuIgual =  '<=' ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoInstance(){
//		ExpressaoInstance = 'instanceof' 'id'
		if(currentTk.checkType(TokenType.KEYIOF)){
			System.out.println("ExpressaoInstance = 'instanceof' 'id'");
			getToken();
			if(currentTk.checkType(TokenType.ID)){	
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
		System.out.println("ExpressaoAditiva = ExpressaoMultiplicacao   ExpressaoAditiva1");
		expressaoMultiplicativa();
		expressaoAditiva1();
	}
	
	public static void expressaoAditiva1(){
//		ExpressaoAditiva1 = '+' ExpressaoMultiplicacao   ExpressaoAditiva1
//		        |  '-' ExpressaoMultiplicacao   ExpressaoAditiva1 
//		        | null
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			System.out.println("ExpressaoAditiva1 = "+currentTk.getValue()+" ExpressaoMultiplicacao   ExpressaoAditiva1");
			expressaoMultiplicativa();
			expressaoAditiva1();
		}
	}
	
	public static void expressaoMultiplicativa(){
//		ExpressaoMultiplicativa = ExpressaoUnaria   ExpressaoMultiplicativa1
		System.out.println("ExpressaoMultiplicacao = ExpressaoUnaria   ExpressaoMultiplicacao1");
		expressaoUnaria();
		expressaoMultiplicativa1();
	}
	
	public static void expressaoMultiplicativa1(){
//		ExpressaoMultiplicativa1 = ExpressaoMultiplicacao 
//				| ExpressaoDivisao 
//				| ExpressaoModulo
//				| null	
		if(currentTk.checkType(TokenType.OPRMTL)){
			System.out.println("ExpressaoMultiplicacao1 = ExpressaoMultiplicacao ");
			expressaoMultiplicacao();
		}else if(currentTk.checkType(TokenType.OPRDIV)){
			System.out.println("ExpressaoMultiplicacao1 = ExpressaoDivisao ");
			expressaoDivisao();
		}else if(currentTk.checkType(TokenType.OPRMOD)){
			System.out.println("ExpressaoMultiplicacao1 = ExpressaoModulo ");
			expressaoModulo();
		}else{
			
		}
	}
	
	public static void expressaoMultiplicacao(){
//		ExpressaoMultiplicacao = '*' ExpressaoUnaria   ExpressaoMultiplicativa
		if(currentTk.checkType(TokenType.OPRMTL)){
			System.out.println("ExpressaoMultiplicacao = '*' ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoDivisao(){
//		ExpressaoDivisao = '/' ExpressaoUnaria   ExpressaoMultiplicativa
		if(currentTk.checkType(TokenType.OPRDIV)){
			System.out.println("ExpressaoDivisao = '/' ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoModulo(){
//		ExpressaoModulo = '%' ExpressaoUnaria   ExpressaoMultiplicativa
		if(currentTk.checkType(TokenType.OPRMOD)){
			System.out.println("ExpressaoModulo = '%' ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoIncrementoPre(){
//		ExpressaoIncrementoPre =  '++' ExpressaoUnaria
		System.out.println("ExpressaoIncrementoPre =  '++' ExpressaoUnaria");
		if(currentTk.checkType(TokenType.OPRMMA)){
			getToken();
			expressaoUnaria();
		}
	}
	
	public static void expressaoDecrementoPre(){
//		ExpressaoDecrementoPre = '--' ExpressaoUnaria
		System.out.println("ExpressaoDecrementoPre = '--' ExpressaoUnaria");
		if(currentTk.checkType(TokenType.OPRMME)){
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
		
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			System.out.println("ExpressaoUnaria = "+currentTk.getValue()+"ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			System.out.println("ExpressaoUnaria = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			System.out.println("ExpressaoUnaria = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else{
			System.out.println("ExpressaoUnaria = ExpressaoPosFixada");
			expressaoPosFixada();
		}	
	}
	
	public static void expressaoIncrementoPos(){
//		ExpressaoIncrementoPos = ExpressaoPosFixada '++'
		System.out.println("ExpressaoIncrementoPos = ExpressaoPosFixada '++'");
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMMA)){
			getToken();
		}else{
				System.out.println(currentTk.getValue());
		}
	}
	
	public static void expressaoDecrementoPos(){
//		ExpressaoDecrementoPos = ExpressaoPosFixada '--'
		System.out.println("ExpressaoDecrementoPos = ExpressaoPosFixada '--'");
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMME)){
			getToken();
		}else{
				erro();
		}
	}
	
	public static void expressaoPosFixada(){
//		ExpressaoPosFixada = Primario PosFixo
//				| Nome PosFixo
		if(currentTk.checkType(TokenType.ID)){
			System.out.println("ExpressaoPosFixada = Nome PosFixo");
			nome();
			posFixo();
		}else{
			System.out.println("ExpressaoPosFixada = Primario PosFixo");
			primario();
			posFixo();
		}
	}
	
	public static void posFixo(){
//		PoxFixo = '++'
//				| '--'
//				| null
		if(currentTk.checkType(TokenType.OPRMMA)||currentTk.checkType(TokenType.OPRMME)){
			System.out.println("PoxFixo = "+currentTk.getValue());
			getToken();
		}else{
			
		}
	}
}	
