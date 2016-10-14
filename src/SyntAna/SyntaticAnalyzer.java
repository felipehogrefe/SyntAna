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
			escreve("ArrayCol = '['" +" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreveln("']'"+"("+currentTk.getValue()+")");	
				getToken();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void nome(){
//		Nome = NomeSimples Composicao
		escreveln("Nome = NomeSimples Composicao");	
		nomeSimples();
		composicao();
	}
	
	public static void composicao(){
//		Composicao = '.' 'id' Composicao
//				| null
		if(currentTk.checkType(TokenType.SEPPNT)){
			escreve("Composicao = '.' "+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				escreveln("'id' "+" ("+currentTk.getValue()+")"+" Composicao");
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
		if(currentTk.checkType(TokenType.ID)){
			escreveln("NomeSimples = 'id' "+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public static void modificador(){
//		Modificador  = 'static' 
//				| null
		if(currentTk.checkType(TokenType.KEYSTC)){
			escreveln("Modificador = 'static'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
		}
	}
	
	public void start(){
//		UnidadeDeCompilacao = DeclaracaoDeClasse
		escreveln("UnidadeDeCompilacao = DeclaracaoDeClasse");
		getToken();
		declaracaoDeClasse();
	}
	
	public static void declaracaoDeClasse(){
//		DeclaracaoDeClasse = Modificador 'class' 'id' CorpoClasse
		escreve("DeclaracaoDeClasse = Modificador");
		modificador();
		if(currentTk.checkType(TokenType.KEYCLS)){
			escreve(" 'id'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				escreveln(" 'class'"+" ("+currentTk.getValue()+")"+" CorpoClasse");
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
		if(currentTk.checkType(TokenType.SEPACH)){
			escreve("CorpoClasse = '{'"+" ("+currentTk.getValue()+")"+"DeclaracoesCorpoClasse");
			getToken();
			declaracoesCorpoClasse();
			if(currentTk.checkType(TokenType.SEPFCH)){
				escreveln("'}'"+" ("+currentTk.getValue()+")");
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
		escreveln("DeclaracoesCorpoClasse = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
		declaracaoMembroClasse();
		declaracaoCorpoClasse1();
	}
	
	public static void declaracaoCorpoClasse1(){
//		DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1 
//				| null
		if(!currentTk.checkType(TokenType.SEPFCH)){
			escreveln("DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
			declaracaoMembroClasse();
			declaracaoCorpoClasse1();
		}else{
			
		}
	}
	
	public static void declaracaoMembroClasse(){
//		DeclaracaoMembroClasse = DeclaracaoDeCampo 
//				| 'method' DeclaracaoDeMetodo
		if(currentTk.checkType(TokenType.KEYMTD)){
			escreveln("DeclaracaoMembroClasse = 'method' "+" ("+currentTk.getValue()+")"+" DeclaracaoDeMetodo");
			getToken();
			declaracaoDeMetodo();
		}else{
			escreveln("DeclaracaoMembroClasse = DeclaracaoDeCampo");
			declaracaoDeCampo();
		}
	}
	
	public static void declaracaoDeCampo(){
//		DeclaracaoDeCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		if(currentTk.checkType(TokenType.KEYATR)){
			escreve("DeclaracaoDeCampo = 'atr' "+" ("+currentTk.getValue()+")"+" Modificador Tipo DeclaracoesVariavel");
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				escreveln(" ';'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}
	}
	
	public static void declaracoesVariavel(){
//		DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1
		escreveln("DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1");
		declaracaoVariavel();
		declaracoesVariavel1();
	}
	
	public static void declaracoesVariavel1(){
//		DeclaracoesVariavel1 = ','  DeclaracaoVariavel DeclaracoesVariavel1 
//				| null
		if(currentTk.checkType(TokenType.SEPVRG)){
			escreveln("DeclaracoesVariavel1 = ',' "+" ("+currentTk.getValue()+")"+" DeclaracaoVariavel DeclaracoesVariavel1 ");
			getToken();
			declaracaoVariavel();
			declaracoesVariavel1();
		}else{
		}
	}
	
	public static void declaracaoVariavel(){
//		DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1
		escreveln("DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1");
		declaracaoVariavelId();
		declaracaoVariavel1();
	}
	
	public static void declaracaoVariavel1(){
//		DeclaracaoVariavel1 = Atribuicao 
//				| null
		if(currentTk.checkType(TokenType.ID)){
			escreveln("DeclaracaoVariavel1 = Atribuicao");
			expressaoAtribuicao();
		}else{
			
		}
	}
	
	public static void declaracaoVariavelId(){
//		DeclaracaoVariavelId = 'id' ArrayCol
		if(currentTk.checkType(TokenType.ID)){
			escreveln("DeclaracaoVariavelId = 'id'"+" ("+currentTk.getValue()+")"+" ArrayCol");
			getToken();
			arrayCol();
		}else{
			erro();
		}
	}
		
	public static void declaracaoDeMetodo(){
//		DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo
		escreveln("DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo");
		cabecalhoMetodo();
		corpoMetodo();
	}
	
	public static void cabecalhoMetodo(){
//		CabecalhoMetodo = Modificador Tipo MetodoDeclaracao
		escreveln("CabecalhoMetodo = Modificador Tipo MetodoDeclaracao");
		modificador();
		tipo();
		metodoDeclaracao();		
	}
	
	public static void metodoDeclaracao(){
//		MetodoDeclaracao = 'id' '[' ListaDeParametrosFormais ']'
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYMAIN)){
			escreve("MetodoDeclaracao = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve("'['"+" ("+currentTk.getValue()+")"+" ListaDeParametrosFormais");
				getToken();
				listaDeParametrosFormais();
				if(currentTk.checkType(TokenType.SEPFCL)){
					escreveln(" ']'"+" ("+currentTk.getValue()+")");
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
			escreveln("ListaDeParametrosFormais = ParametrosFormais ListaDeParametrosFormais1");	
			parametrosFormais();
			listaDeParametrosFormais1();
		}else{
			
		}
	}
	
	public static void listaDeParametrosFormais1(){
//		ListaDeParametrosFormais1 = ',' ParametrosFormais ListaDeParametrosFormais
//				|null
		if(currentTk.checkType(TokenType.SEPVRG)){
			escreveln("ListaDeParametrosFormais1 = ','"+" ("+currentTk.getValue()+")"+" ParametrosFormais ListaDeParametrosFormais");	
			getToken();
			parametrosFormais();
			listaDeParametrosFormais();
		}else{
			
		}
	}
	
	public static void parametrosFormais(){
//		ParametrosFormais = Tipo DeclaracaoVariavelId 
		escreveln("ParametrosFormais = Tipo DeclaracaoVariavelId");
		tipo();
		declaracaoVariavelId();
	}
	
	public static void corpoMetodo(){
//		CorpoMetodo = Bloco 
//				| ';'
		if(currentTk.checkType(TokenType.SEPPEV)){
			escreveln("CorpoMetodo = ';'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			escreveln("CorpoMetodo = Bloco ");
			bloco();
		}
	}

	public static void bloco(){
//		Bloco = '{' DeclaracaoDeBloco '}'
		if(currentTk.checkType(TokenType.SEPACH)){
			escreve("Bloco = '{'"+" ("+currentTk.getValue()+")"+" DeclaracaoDeBloco");
			getToken();
			declaracaoDeBloco();
			if(currentTk.checkType(TokenType.SEPFCH)){
				escreveln(" '}'"+" ("+currentTk.getValue()+")");
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
		escreveln("DeclaracaoDeBloco = BlocoDeclaracao DeclaracaoDeBloco1");
		blocoDeclaracao();
		declaracaoDeBloco1();
	}
	
	public static void declaracaoDeBloco1(){
//		DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1 
//				| null 
		if(!currentTk.checkType(TokenType.SEPFCH)){
			/*se o currentTk for '}' significa que o bloco acabou, logo estamos no desvio null
			 caso contrario, para qualquer outro token ainda estamos no bloco*/
			escreveln("DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1");
			declaracaoDeBloco();
			declaracaoDeBloco1();
		}else{
			
		}
	}
	
	public static void blocoDeclaracao(){
//		BlocoDeclaracao = DeclaracaoCampo 
//				| Declaracao
		if(currentTk.checkType(TokenType.KEYATR)){
			escreveln("BlocoDeclaracao = DeclaracaoCampo");
			declaracaoCampo();
		}else{
			escreveln("BlocoDeclaracao = Declaracao");
			declaracao();
		}
	}
	
	public static void declaracaoCampo(){
//		DeclaracaoCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		if(currentTk.checkType(TokenType.KEYATR)){
			escreve("DeclaracaoCampo = 'atr'"+" ("+currentTk.getValue()+")"+" Modificador Tipo DeclaracoesVariavel");
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				escreveln(" ';'"+" ("+currentTk.getValue()+")");
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
			escreveln("Declaracao = DeclaracaoIf");
			declaracaoIf();
		}else if(currentTk.checkType(TokenType.KEYWHL)){
			escreveln("Declaracao = DeclaracaoWhile");
			declaracaoWhile();
		}else if(currentTk.checkType(TokenType.KEYFOR)){
			escreveln("Declaracao = DeclaracaoFor");
			declaracaoFor();
		}else{
			escreveln("Declaracao = DeclaracaoSemSubDeclaracaoDireta");
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
			escreveln("DeclaracaoSemSubDeclaracaoDireta = Bloco");
			bloco();
		}else if(currentTk.checkType(TokenType.SEPPEV)){
			escreveln( "DeclaracaoSemSubDeclaracaoDireta = ';'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYRET)){
			escreveln("DeclaracaoSemSubDeclaracaoDireta = DeclaracaoReturn");
			declaracaoReturn();
		}else if(currentTk.checkType(TokenType.KEYBRK)){
			escreveln("DeclaracaoSemSubDeclaracaoDireta = DeclaracaoBreak");
			declaracaoBreak();
		}else{
			escreveln("DeclaracaoSemSubDeclaracaoDireta = ExpressaoDeclaracao");
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
			escreveln("ExpressaoDeclaracao = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			escreveln("ExpressaoDeclaracao = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			escreveln("ExpressaoDeclaracao = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			escreveln("ExpressaoDeclaracao = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else{
			escreveln("ExpressaoDeclaracao = Atribuicao ");
			atribuicao();
		}
	}	
	
	public static void declaracaoIf(){
//		DeclaracaoIf = 'if' '[' ExpressaoCondicional ']'  Declaracao   DeclaracaoElse
		if(currentTk.checkType(TokenType.KEYIF)){
			escreve("DeclaracaoIf = 'if'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve(" '['"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional");
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
					escreveln(" ']'"+" ("+currentTk.getValue()+")"+"  Declaracao   DeclaracaoElse");
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
			escreveln("DeclaracaoElse = 'else'"+" ("+currentTk.getValue()+")"+" Declaracao");
			getToken();
			declaracao();
		}else{
			
		}
	}
	
	public static void declaracaoWhile(){
//		DeclaracaoWhile = 'while' '[' ExpressaoCondicional ']' Declaracao
		if(currentTk.checkType(TokenType.KEYWHL)){
			escreve("DeclaracaoWhile = 'while'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve(" '['"+" ("+currentTk.getValue()+") ExpressaoCondicional");
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
					escreveln(" ']' Declaracao"+" ("+currentTk.getValue()+")");
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
		if(currentTk.checkType(TokenType.KEYFOR)){
			escreve("DeclaracaoFor = 'for'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve("'['"+" ("+currentTk.getValue()+") InicializadorFor");
				getToken();
				inicializadorFor();
				if(currentTk.checkType(TokenType.SEPPEV)){
					escreve(" ';' LimiteSuperiorFor"+" ("+currentTk.getValue()+")");
					getToken();
					limiteSuperiorFor();
					if(currentTk.checkType(TokenType.SEPPEV)){
						escreve(" ';' PassoFor"+" ("+currentTk.getValue()+")");
						getToken();
						passoFor();
						if(currentTk.checkType(TokenType.SEPFCL)){
							escreveln(" ']' Declaracao"+" ("+currentTk.getValue()+")");
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
		if(currentTk.checkType(TokenType.KEYINT)){
			escreve("InicializadorFor = 'int'"+" ("+currentTk.getValue()+")");
			getToken();			
			if(currentTk.checkType(TokenType.ID)){
				escreve("'id'"+" ("+currentTk.getValue()+")");
				getToken();
				if(currentTk.checkType(TokenType.OPRATR)){
					escreveln(" '='"+" ("+currentTk.getValue()+")"+" ValorInicializadorFor");
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
			escreveln("ValorInicializadorFor = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			escreveln("ValorInicializadorFor = Literal");
			literal();
		}
	}
	
	public static void limiteSuperiorFor(){
//		LimiteSuperiorFor = ConstanteNumerica 
//				| Nome
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			escreveln("LimiteSuperiorFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			escreveln("LimiteSuperiorFor = Nome");
			nome();
		}
	}
	
	public static void passoFor(){
//		PassoFor = ConstanteNumerica 
//				| Nome
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			escreveln("PassoFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			escreveln("PassoFor = Nome");
			nome();
		}
	}
	
	public static void declaracaoReturn(){
//		DeclaracaoReturn = 'return' ExpressaoAtribuicao ';' 
//				| 'return' ';'
		if(currentTk.checkType(TokenType.KEYRET)){
			escreve("DeclaracaoReturn = 'return'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				escreveln(" ';'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
					escreveln(" ExpressaoAtribuicao ';' "+" ("+currentTk.getValue()+")");
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
			escreve("DeclaracaoReturn = 'break'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				escreveln(" ';'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
					escreveln(" ExpressaoAtribuicao ';' "+" ("+currentTk.getValue()+")");
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
		if(currentTk.checkType(TokenType.KEYNEW)){
			escreve("ExpressaoCriaInstanciaDeClasse = 'new'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				escreve(" 'id'"+" ("+currentTk.getValue()+")");
				getToken();
				if(currentTk.checkType(TokenType.SEPACL)){
					escreve(" '[' ListaDeArgumentos"+" ("+currentTk.getValue()+")");
					getToken();
					listaDeArgumentos();
					if(currentTk.checkType(TokenType.SEPFCL)){
						escreveln(" ']'"+" ("+currentTk.getValue()+")");
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
			escreveln("ListaDeArgumentos = ExpressaoAtribuicao   ListaDeArgumentos1");
			expressaoAtribuicao();
			listaDeArgumentos1();
		}else{
			
		}
		
	}
	
	public static void listaDeArgumentos1(){
//		ListaDeArgumentos1 = ',' ListaDeArgumentos   ListaDeArgumentos1 
//				| null 
		if(currentTk.checkType(TokenType.SEPVRG)){
			escreveln("ListaDeArgumentos1 = ','"+" ("+currentTk.getValue()+")"+" ListaDeArgumentos   ListaDeArgumentos1 ");
			getToken();
			listaDeArgumentos();
			listaDeArgumentos1();
		}else{
			
		}
	}
	
	public static void expressaoCriaArray(){
//		ExpressaoCriaArray = 'array' '[' Tipo ',' TamanhoArray ']'
		if(currentTk.checkType(TokenType.KEYARY)){
			escreve("ExpressaoCriaArray = 'array'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve("'[' Tipo"+" ("+currentTk.getValue()+")");
				getToken();
				tipo();
				if(currentTk.checkType(TokenType.SEPVRG)){
					escreve(" ',' TamanhoArray"+" ("+currentTk.getValue()+")");
					getToken();
					tamanhoArray();
					if(currentTk.checkType(TokenType.SEPFCL)){
						escreveln(" ']'"+" ("+currentTk.getValue()+")");
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
			escreveln("TamanhoArray = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYINT)){
			escreveln("TamanhoArray = 'cntInt'"+" ("+currentTk.getValue()+")");
			getToken();
		}
	}
	
	public static void chamadaMetodo(){
//		ChamadaMetodo = 'call' NomeMetodo '[' ListaDeArgumentos ']'
		if(currentTk.checkType(TokenType.KEYCLL)){
			escreve("ChamadaMetodo = 'call'"+" ("+currentTk.getValue()+")");
			getToken();	
			nomeMetodo();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve(" NomeMetodo '['"+" ("+currentTk.getValue()+")");
				getToken();
				listaDeArgumentos();
				if(currentTk.checkType(TokenType.SEPFCL)){
					escreveln(" ListaDeArgumentos ']'"+" ("+currentTk.getValue()+")");
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
			escreveln("NomeMetodo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			escreveln("NomeMetodo = Nome NomeMetodo1");
			nome();
			nomeMetodo1();
		}
	}
	
	public static void nomeMetodo1(){
//		NomeMetodo1 = '.' NomeMetodo
//				| null
		if(currentTk.checkType(TokenType.SEPPNT)){
			escreveln("NomeMetodo1 = '.'"+" ("+currentTk.getValue()+")"+" NomeMetodo");
			getToken();
			nomeMetodo();
		}
	}
	
	public static void acessoArray(){
//		AcessoArray = 'aaray' Nome '[' ExpressaoAtribuicao ']'
		if(currentTk.checkType(TokenType.KEYAAY)){
			escreve("AcessoArray = 'aaray'"+" ("+currentTk.getValue()+")");
			getToken();	
			nome();
			if(currentTk.checkType(TokenType.SEPACL)){
				escreve("Nome '['"+" ("+currentTk.getValue()+")");
				getToken();
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPFCL)){
					escreveln("ExpressaoAtribuicao ']'"+" ("+currentTk.getValue()+")");
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
			escreveln("Primario = ExpressaoCriaArray ");
			expressaoCriaArray();
		}else{
			escreveln("Primario = PrimairoSemNovoArray ");
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
			escreveln("PrimairoSemNovoArray = 'this'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.SEPACL)){
			escreve("PrimairoSemNovoArray = '['"+" ("+currentTk.getValue()+")");
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				escreveln("ExpressaoAtribuicao ']'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTSTR)||currentTk.checkType(TokenType.CNTCHR)||
				currentTk.checkType(TokenType.CTNINT)||currentTk.checkType(TokenType.CTNDBL)){
			escreveln("PrimairoSemNovoArray = Literal");
			literal();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			escreveln("PrimairoSemNovoArray = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else if(currentTk.checkType(TokenType.KEYCLL)){
			escreveln("PrimairoSemNovoArray = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.KEYAAY)){
			escreveln("PrimairoSemNovoArray = AcessoArray");
			acessoArray();
		}else{
			erro();
		}
	}
	
	public static void argumentos(){
//		Argumentos = '[' ExpressaoAtribuicao ']'
//				| null
		if(currentTk.checkType(TokenType.SEPACL)){
			escreve("Argumentos = '['"+" ("+currentTk.getValue()+")");
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				escreveln(" ExpressaoAtribuicao ']'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			
		}
	}
	
	public static void atribuicao(){
//		Atribuicao = LadoEsquerdo '=' ExpressaoAtribuicao 
		ladoEsquerdo();
		if(currentTk.checkType(TokenType.OPRATR)){
			escreveln("Atribuicao = LadoEsquerdo '='"+" ("+currentTk.getValue()+")"+" ExpressaoAtribuicao ");
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
			escreveln("LadoEsquerdo = AcessoArray");
			acessoArray();
		}else{
			escreveln("LadoEsquerdo = Nome1 Argumentos");
			nome1();
			argumentos();
		}
	}
	
	public static void nome1(){
//		Nome1 = 'id' Nome1
//				| Literal Nome1
//				| null	
		if(currentTk.checkType(TokenType.ID)){
			escreveln("Nome1 = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
			nome1();
		}else if(currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYLGC)||
				currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)){
			escreveln("Nome1 = Literal");
			getToken();
			nome1();
		}else{
			
		}
	}
	
	public static void expressaoAtribuicao(){
//		ExpressaoAtribuicao = ExpressaoCondicional
		escreveln("ExpressaoAtribuicao = ExpressaoCondicional");
		expressaoCondicional();
	}
	
	public static void expressaoCondicional(){
//		ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1
		escreveln("ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1");
		expressaoOuCondicional();
		expressaoCondicional1();
	}
	
	public static void expressaoCondicional1(){
//		ExpressaoCondicional1 = '?' Expressao ':' ExpressaoCondicional
//				| null
		if(currentTk.checkType(TokenType.OPRTRI)){
			escreve("ExpressaoCondicional1 = '?'"+" ("+currentTk.getValue()+")");
			getToken();
			expressao();
			if(currentTk.checkType(TokenType.SEPDPT)){
				escreveln(" Expressao ':'"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional");
				getToken();
				expressaoCondicional();
			}else{
				erro();
			}
		}
	}
	
	public static void expressaoOuCondicional(){
//		ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1
		escreveln("ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1");
		expressaoECondicional();
		expressaoOuCondicional1();
	}
	
	public static void expressaoOuCondicional1(){
//		ExpressaoOuCondicional1 = '||' ExpressaoCondicional   ExpressaoOuCondicional1 
//				| null 
		
		if(currentTk.checkType(TokenType.OPROCD)){
			escreveln("ExpressaoOuCondicional1 = '||'"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional   ExpressaoOuCondicional1 ");
			getToken();
			expressaoCondicional();
			expressaoOuCondicional1();
		}else{
			
		}
	}
	
	public static void expressaoECondicional(){
//		ExpressaoECondicional = Expressao   ExpressaoECondicional1 
		escreveln("ExpressaoECondicional = Expressao   ExpressaoECondicional1");
		expressao();
		expressaoECondicional1();
	}
	
	public static void expressaoECondicional1(){
//		ExpressaoECondicional1 = '&&' Expressao   ExpressaoECondicional1
//				| null 
		if(currentTk.checkType(TokenType.OPRECD)){
			escreveln("ExpressaoECondicional1 = '&&'"+" ("+currentTk.getValue()+")"+" Expressao   ExpressaoECondicional1");
			getToken();
			expressao();
			expressaoECondicional1();
		}else{
		}
	}
	
	public static void expressao(){
//		Expressao = ExpressaoEqualidade   Expressao1
		escreveln("Expressao = ExpressaoEqualidade   Expressao1");
		expressaoEqualidade();
		expressao1();
	}
	
	public static void expressao1(){
//		Expressao1 = '&' ExpressaoEqualidade   ExpressaoEqualidade1 
//				| null 
		if(currentTk.checkType(TokenType.OPRE)){
			escreveln("Expressao1 = '&'"+" ("+currentTk.getValue()+")"+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			
		}
	}
	
	public static void expressaoEqualidade(){
//		ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1
		escreveln("ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1");
		expressaoRelacional();
		expressaoEqualidade1();
	}
	
	public static void expressaoEqualidade1(){
//		ExpressaoEqualidade1 = '==' ExpressaoEqualidade   ExpressaoEqualidade1  
//			    | '!=' ExpressaoEqualidade   ExpressaoEqualidade1 
//			    | null 
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)){
			escreveln("ExpressaoEqualidade1 ="+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			
		}
	}
	
	public static void expressaoRelacional(){
//		ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1
		escreveln("ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1");
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
			escreveln("ExpressaoRelacional1 = ExpressaoMenor");
			expressaoMenor();
		}else if(currentTk.checkType(TokenType.OPRMAR)){
			escreveln("ExpressaoRelacional1 = ExpressaoMaior");
			expressaoMaior();
		}else if(currentTk.checkType(TokenType.OPRMEI)){
			escreveln("ExpressaoRelacional1 = ExpressaoMenorOuIgual");
			expressaoMenorOuIgual();
		}else if(currentTk.checkType(TokenType.OPRMAI)){
			escreveln("ExpressaoRelacional1 = ExpressaoMaiorOuIgual");
			expressaoMaiorOuIgual();
		}else if(currentTk.checkType(TokenType.KEYIOF)){
			escreveln("ExpressaoRelacional1 = ExpressaoInstance");
			expressaoInstance();
		}else{
			
		}
	}
	
	public static void expressaoMenor(){
//		ExpressaoMenor = '<' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMNR)){
			escreveln("ExpressaoMenor = '<'"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMaior(){
//		ExpressaoMaior = '<' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMAR)){
			escreveln("ExpressaoMaior = '<'"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMenorOuIgual(){
//		ExpressaoMenorOuIgual = '<=' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMEI)){
			escreveln("ExpressaoMenorOuIgual =  '<='"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoMaiorOuIgual(){
//		ExpressaoMaiorOuIgual = '>=' ExpressaoAditiva
		if(currentTk.checkType(TokenType.OPRMAI)){
			escreveln("ExpressaoMaiorOuIgual =  '>='"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public static void expressaoInstance(){
//		ExpressaoInstance = 'instanceof' 'id'
		if(currentTk.checkType(TokenType.KEYIOF)){
			escreve("ExpressaoInstance = 'instanceof'"+" ("+currentTk.getValue()+")");
			getToken();
			if(currentTk.checkType(TokenType.ID)){	
				escreveln(" 'id'"+" ("+currentTk.getValue()+")");
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
		escreveln("ExpressaoAditiva = ExpressaoMultiplicacao   ExpressaoAditiva1");
		expressaoMultiplicativa();
		expressaoAditiva1();
	}
	
	public static void expressaoAditiva1(){
//		ExpressaoAditiva1 = '+' ExpressaoMultiplicacao   ExpressaoAditiva1
//		        |  '-' ExpressaoMultiplicacao   ExpressaoAditiva1 
//		        | null
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			escreveln("ExpressaoAditiva1 = "+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoMultiplicacao   ExpressaoAditiva1");
			expressaoMultiplicativa();
			expressaoAditiva1();
		}
	}
	
	public static void expressaoMultiplicativa(){
//		ExpressaoMultiplicativa = ExpressaoUnaria   ExpressaoMultiplicativa1
		escreveln("ExpressaoMultiplicacao = ExpressaoUnaria   ExpressaoMultiplicacao1");
		expressaoUnaria();
		expressaoMultiplicativa1();
	}
	
	public static void expressaoMultiplicativa1(){
//		ExpressaoMultiplicativa1 = ExpressaoMultiplicacao 
//				| ExpressaoDivisao 
//				| ExpressaoModulo
//				| null	
		if(currentTk.checkType(TokenType.OPRMTL)){
			escreveln("ExpressaoMultiplicacao1 = ExpressaoMultiplicacao ");
			expressaoMultiplicacao();
		}else if(currentTk.checkType(TokenType.OPRDIV)){
			escreveln("ExpressaoMultiplicacao1 = ExpressaoDivisao ");
			expressaoDivisao();
		}else if(currentTk.checkType(TokenType.OPRMOD)){
			escreveln("ExpressaoMultiplicacao1 = ExpressaoModulo ");
			expressaoModulo();
		}else{
			
		}
	}
	
	public static void expressaoMultiplicacao(){
//		ExpressaoMultiplicacao = '*' ExpressaoUnaria   ExpressaoMultiplicativa
		if(currentTk.checkType(TokenType.OPRMTL)){
			escreveln("ExpressaoMultiplicacao = '*'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
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
			escreveln("ExpressaoDivisao = '/'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
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
			escreveln("ExpressaoModulo = '%'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public static void expressaoIncrementoPre(){
//		ExpressaoIncrementoPre =  '++' ExpressaoUnaria
		escreveln("ExpressaoIncrementoPre =  '++'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
		if(currentTk.checkType(TokenType.OPRMMA)){
			getToken();
			expressaoUnaria();
		}
	}
	
	public static void expressaoDecrementoPre(){
//		ExpressaoDecrementoPre = '--' ExpressaoUnaria
		escreveln("ExpressaoDecrementoPre = '--'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
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
			escreveln("ExpressaoUnaria = "+currentTk.getCategory()+" ("+currentTk.getValue()+")"+"ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			escreveln("ExpressaoUnaria = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			escreveln("ExpressaoUnaria = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else{
			escreveln("ExpressaoUnaria = ExpressaoPosFixada");
			expressaoPosFixada();
		}	
	}
	
	public static void expressaoIncrementoPos(){
//		ExpressaoIncrementoPos = ExpressaoPosFixada '++'
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMMA)){
			escreveln("ExpressaoIncrementoPos = ExpressaoPosFixada '++'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
				System.out.println(currentTk.getValue());
		}
	}
	
	public static void expressaoDecrementoPos(){
//		ExpressaoDecrementoPos = ExpressaoPosFixada '--'
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMME)){
			escreveln("ExpressaoDecrementoPos = ExpressaoPosFixada '--'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
				erro();
		}
	}
	
	public static void expressaoPosFixada(){
//		ExpressaoPosFixada = Primario PosFixo
//				| Nome PosFixo
		if(currentTk.checkType(TokenType.ID)){
			escreveln("ExpressaoPosFixada = Nome PosFixo");
			nome();
			posFixo();
		}else{
			escreveln("ExpressaoPosFixada = Primario PosFixo");
			primario();
			posFixo();
		}
	}
	
	public static void posFixo(){
//		PoxFixo = '++'
//				| '--'
//				| null
		if(currentTk.checkType(TokenType.OPRMMA)||currentTk.checkType(TokenType.OPRMME)){
			escreveln("PoxFixo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			
		}
	}
}	
