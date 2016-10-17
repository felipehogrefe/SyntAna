package SyntAna;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

import LexAna.LexicalAnalyzer;
import LexAna.Token;
import LexAna.TokenType;

public class SyntaticAnalyzer {
	LexicalAnalyzer la;
	Token currentTk;
	private PrintWriter gravarArq;
	ArrayList<String> producoes;
	
	private void getToken(){
		if(la.isOver()){
			currentTk = la.nextToken();
		}
//		System.out.println(currentTk.getValue());
	}
	
	SyntaticAnalyzer(LexicalAnalyzer la, PrintWriter gravarArq){
		this.la = la;
		this.gravarArq = gravarArq;
	}
	
	void erro(){
		System.out.println("ERRO - val: "+currentTk.getValue()+", categ: "+currentTk.getCategory()+", ["+currentTk.getLine()+","+currentTk.getCol()+"]");
		System.exit(1);
	}
	
	void escreve(String str){
		gravarArq.print(str);
	}
	
	void escreveln(String str){
		gravarArq.println(str);
	}
	
	public String defStr(){
		String s="";
		s+= producoes.size();
		producoes.add(s);
		return s;
	}
		
	public void start(){
//		UnidadeDeCompilacao = DeclaracaoDeClasse
		producoes = new ArrayList<>();
		String s = defStr();
		producoes.set(producoes.indexOf(s), "UnidadeDeCompilacao = DeclaracaoDeClasse");
		getToken();
		declaracaoDeClasse();
		for(String p : producoes){
			System.out.println(p);
			if(!p.matches("\\d+")){
				escreveln(p);
			}
		}
	}
	
	public void literal(){
//		Literal = Constante 
//				| ConstanteNumerica
		String s = defStr();		
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			producoes.set(producoes.indexOf(s), "Literal = Constante");
			constante();
		}else if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			producoes.set(producoes.indexOf(s), "Literal = ConstanteNumerica");
			constanteNumerica();
		}else{
			erro();
		}
	}
	
	public void constante(){
//		Constante = 'cntLgc' 
//				| 'cntChr' 
//				| 'cntStr'	
		String s = defStr();
		if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTCHR)||currentTk.checkType(TokenType.CNTSTR)){
			producoes.set(producoes.indexOf(s), "Constante = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public void constanteNumerica(){
//		ConstanteNumerica = 'ctnInt' 
//				| 'ctnDbl
		String s = defStr();
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			producoes.set(producoes.indexOf(s), "ConstanteNumerica = "+currentTk.getCategory()
					+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	
	public void tipo(){
//		Tipo = TipoPrimitivo 
//				| Nome
//				| 'void'
//				| 'array'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYARY)){
			getToken();
			producoes.set(producoes.indexOf(s),"Tipo = "+currentTk.getCategory()
					+" ("+currentTk.getValue()+")");	
		}else if(currentTk.checkType(TokenType.KEYVOD)){
			getToken();
			producoes.set(producoes.indexOf(s),"Tipo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");	
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)||
				currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)){
			producoes.set(producoes.indexOf(s),"Tipo = TipoPrimitivo");	
			tipoPrimitivo();
		}else if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s),"Tipo = Nome");	
			nome();
		}else{
			erro();	
		}
	}
	
	public void tipoPrimitivo(){
//		TipoPrimitivo = TipoNumerico 
//				| 'tkLgc'
//				| 'tkChr' 
//				| 'tkStr' 	
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			producoes.set(producoes.indexOf(s), "TipoPrimitivo = TipoNumerico");	
			tipoNumerico();
		}else if(currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)){
			producoes.set(producoes.indexOf(s), "TipoPrimitivo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public void tipoNumerico(){
//		TipoNumerico =  'tkInt' 
//				| 'tkDbl'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYINT)){
			producoes.set(producoes.indexOf(s), "TipoNumerico = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}	
	
	public void arrayCol(){
//		ArrayCol =  '[' ']' ArrayCol
//				| null	
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPACL)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				producoes.set(producoes.indexOf(s), "ArrayCol = '['" +" ("+tk1.getValue()+") ']'"+"("+currentTk.getValue()+")");	
				getToken();
			}else{
				erro();
			}
		}else{
			producoes.set(producoes.indexOf(s), "ArrayCol = null");
		}
	}
	
	public void nome(){
//		Nome = NomeSimples Composicao
		String s = defStr();
		producoes.set(producoes.indexOf(s), "Nome = NomeSimples Composicao");	
		nomeSimples();
		composicao();
	}
	
	public void composicao(){
//		Composicao = '.' 'id' Composicao
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPPNT)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				producoes.set(producoes.indexOf(s), "Composicao = '.' "+" ("+tk1.getValue()+") 'id' "+" ("+currentTk.getValue()+")"+" Composicao");
				getToken();
				composicao();
			}else{
				erro();
			}
		}else{
			producoes.set(producoes.indexOf(s), "Composicao = null");
		}
	}
	
	public void nomeSimples(){
//		NomeSimples = 'id'
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "NomeSimples = 'id' "+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public void modificador(){
//		Modificador  = 'static' 
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYSTC)){
			producoes.set(producoes.indexOf(s), "Modificador = 'static'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			producoes.set(producoes.indexOf(s), "Modificador = null");
		}
	}
	
	public void declaracaoDeClasse(){
//		DeclaracaoDeClasse = Modificador 'class' 'id' CorpoClasse
		String s = defStr();
		modificador();
		if(currentTk.checkType(TokenType.KEYCLS)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){
				producoes.set(producoes.indexOf(s), "DeclaracaoDeClasse = Modificador 'class'"+" ("+tk1.getValue()+") 'id'"+" ("+currentTk.getValue()+")"+" CorpoClasse");
				getToken();
				corpoClasse();
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public void corpoClasse(){
//		CorpoClasse = '{' DeclaracoesCorpoClasse '}'
		String s = defStr();		
		if(currentTk.checkType(TokenType.SEPACH)){
			Token tk1 = currentTk;
			getToken();
			declaracoesCorpoClasse();
			if(currentTk.checkType(TokenType.SEPFCH)){
				producoes.set(producoes.indexOf(s), "CorpoClasse = '{'"+" ("+tk1.getValue()+") "+"DeclaracoesCorpoClasse '}'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			erro();
		}	
	}
	
	public void declaracoesCorpoClasse(){
//		DeclaracoesCorpoClasse = DeclaracaoMembroClasse DeclaracaoCorpoClasse1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "DeclaracoesCorpoClasse = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
		declaracaoMembroClasse();
		declaracaoCorpoClasse1();
	}
	
	public void declaracaoCorpoClasse1(){
//		DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1 
//				| null
		String s = defStr();
		if(!currentTk.checkType(TokenType.SEPFCH)){
			producoes.set(producoes.indexOf(s), "DeclaracaoCorpoClasse1 = DeclaracaoMembroClasse DeclaracaoCorpoClasse1");
			declaracaoMembroClasse();
			declaracaoCorpoClasse1();
		}else{
			producoes.set(producoes.indexOf(s), "DeclaracaoCorpoClasse1 = null");
		}
	}
	
	public void declaracaoMembroClasse(){
//		DeclaracaoMembroClasse = DeclaracaoDeCampo 
//				| 'method' DeclaracaoDeMetodo
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYMTD)){
			producoes.set(producoes.indexOf(s), "DeclaracaoMembroClasse = 'method'"+" ("+currentTk.getValue()+")"+" DeclaracaoDeMetodo");
			getToken();
			declaracaoDeMetodo();
		}else{
			producoes.set(producoes.indexOf(s), "DeclaracaoMembroClasse = DeclaracaoDeCampo");
			declaracaoDeCampo();
		}
	}
	
	public void declaracaoDeCampo(){
//		DeclaracaoDeCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYATR)){
			Token tk1 = currentTk;
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				producoes.set(producoes.indexOf(s), "DeclaracaoDeCampo = 'atr' "+" ("+tk1.getValue()+")"+" Modificador Tipo DeclaracoesVariavel ';'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}
	}
	
	public void declaracoesVariavel(){
//		DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "DeclaracoesVariavel = DeclaracaoVariavel DeclaracoesVariavel1");
		declaracaoVariavel();
		declaracoesVariavel1();
	}
	
	public void declaracoesVariavel1(){
//		DeclaracoesVariavel1 = ','  DeclaracaoVariavel DeclaracoesVariavel1 
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPVRG)){
			producoes.set(producoes.indexOf(s), "DeclaracoesVariavel1 = ',' "+" ("+currentTk.getValue()+")"+" DeclaracaoVariavel DeclaracoesVariavel1 ");
			getToken();
			declaracaoVariavel();
			declaracoesVariavel1();
		}else{
			producoes.set(producoes.indexOf(s), "DeclaracoesVariavel1 = null");
		}
	}
	
	public void declaracaoVariavel(){
		String s = defStr();
//		DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1
		producoes.set(producoes.indexOf(s), "DeclaracaoVariavel = DeclaracaoVariavelId DeclaracaoVariavel1");
		declaracaoVariavelId();
		declaracaoVariavel1();
	}
	
	public void declaracaoVariavel1(){
//		DeclaracaoVariavel1 = Atribuicao 
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "DeclaracaoVariavel1 = Atribuicao");
			expressaoAtribuicao();
		}else{
			producoes.set(producoes.indexOf(s), "DeclaracaoVariavel1 = null");
		}
	}
	
	public void declaracaoVariavelId(){
//		DeclaracaoVariavelId = 'id' ArrayCol
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "DeclaracaoVariavelId = 'id'"+" ("+currentTk.getValue()+")"+" ArrayCol");
			getToken();
			arrayCol();
		}else{
			erro();
		}
	}
		
	public void declaracaoDeMetodo(){
//		DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo
		String s = defStr();
		producoes.set(producoes.indexOf(s), "DeclaracaoDeMetodo = CabecalhoMetodo CorpoMetodo");
		cabecalhoMetodo();
		corpoMetodo();
	}
	
	public void cabecalhoMetodo(){
//		CabecalhoMetodo = Modificador Tipo MetodoDeclaracao
		String s = defStr();
		producoes.set(producoes.indexOf(s), "CabecalhoMetodo = Modificador Tipo MetodoDeclaracao");
		modificador();
		tipo();
		metodoDeclaracao();		
	}
	
	public void metodoDeclaracao(){
//		MetodoDeclaracao = 'id' '[' ListaDeParametrosFormais ']'
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYMAIN)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				listaDeParametrosFormais();
				if(currentTk.checkType(TokenType.SEPFCL)){
					producoes.set(producoes.indexOf(s), "MetodoDeclaracao = 'id'"+" ("+tk1.getValue()
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
	
	public void listaDeParametrosFormais(){
//		ListaDeParametrosFormais = ParametrosFormais ListaDeParametrosFormais1
//				|null
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)||currentTk.checkType(TokenType.KEYLGC)||currentTk.checkType(TokenType.KEYCHR)||
				currentTk.checkType(TokenType.KEYSTR)||currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)
				||currentTk.checkType(TokenType.KEYVOD)||currentTk.checkType(TokenType.KEYARY)){
			producoes.set(producoes.indexOf(s), "ListaDeParametrosFormais = ParametrosFormais ListaDeParametrosFormais1");	
			parametrosFormais();
			listaDeParametrosFormais1();
		}else{
			producoes.set(producoes.indexOf(s), "ListaDeParametrosFormais = null");
		}
	}
	
	public void listaDeParametrosFormais1(){
//		ListaDeParametrosFormais1 = ',' ParametrosFormais ListaDeParametrosFormais
//				|null
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPVRG)){
			producoes.set(producoes.indexOf(s), "ListaDeParametrosFormais1 = ','"+" ("+currentTk.getValue()+")"+" ParametrosFormais ListaDeParametrosFormais");	
			getToken();
			parametrosFormais();
			listaDeParametrosFormais();
		}else{
			producoes.set(producoes.indexOf(s), "ListaDeParametrosFormais1 = null");
		}
	}
	
	public void parametrosFormais(){
//		ParametrosFormais = Tipo DeclaracaoVariavelId 
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ParametrosFormais = Tipo DeclaracaoVariavelId");
		tipo();
		declaracaoVariavelId();
	}
	
	public void corpoMetodo(){
//		CorpoMetodo = Bloco 
//				| ';'
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPPEV)){
			producoes.set(producoes.indexOf(s), "CorpoMetodo = ';'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			producoes.set(producoes.indexOf(s), "CorpoMetodo = Bloco ");
			bloco();
		}
	}

	public void bloco(){
//		Bloco = '{' DeclaracaoDeBloco '}'
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPACH)){
			Token tk1 = currentTk;
			getToken();
			declaracaoDeBloco();
			if(currentTk.checkType(TokenType.SEPFCH)){
				producoes.set(producoes.indexOf(s), "Bloco = '{'"+" ("+tk1.getValue()+")"+" DeclaracaoDeBloco '}'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public void declaracaoDeBloco(){
//		DeclaracaoDeBloco = BlocoDeclaracao DeclaracaoDeBloco1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "DeclaracaoDeBloco = BlocoDeclaracao DeclaracaoDeBloco1");
		blocoDeclaracao();
		declaracaoDeBloco1();
	}
	
	public void declaracaoDeBloco1(){
//		DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1 
//				| null 
		String s = defStr();
		if(!currentTk.checkType(TokenType.SEPFCH)){
			/*se o currentTk for '}' significa que o bloco acabou, logo estamos no desvio null
			 caso contrario, para qualquer outro token ainda estamos no bloco*/
			producoes.set(producoes.indexOf(s), "DeclaracaoDeBloco1 = DeclaracaoDeBloco DeclaracaoDeBloco1");
			declaracaoDeBloco();
			declaracaoDeBloco1();
		}else{
			producoes.set(producoes.indexOf(s), "DeclaracaoDeBloco1 = null");
		}
	}
	
	public void blocoDeclaracao(){
//		BlocoDeclaracao = DeclaracaoCampo 
//				| Declaracao
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYATR)){
			producoes.set(producoes.indexOf(s), "BlocoDeclaracao = DeclaracaoCampo");
			declaracaoCampo();
		}else{
			producoes.set(producoes.indexOf(s), "BlocoDeclaracao = Declaracao");
			declaracao();
		}
	}
	
	public void declaracaoCampo(){
//		DeclaracaoCampo = 'atr' Modificador Tipo DeclaracoesVariavel ';'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYATR)){
			Token tk1 = currentTk;
			getToken();
			modificador();
			tipo();
			declaracoesVariavel();
			if(currentTk.checkType(TokenType.SEPPEV)){
				producoes.set(producoes.indexOf(s), "DeclaracaoCampo = 'atr'"+" ("+tk1.getValue()+")"
						+" Modificador Tipo DeclaracoesVariavel ';'"+" ("+currentTk.getValue()+")");
				getToken();				
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public void declaracao(){
//		Declaracao = DeclaracaoSemSubDeclaracaoDireta 
//				| DeclaracaoIf 
//		        | DeclaracaoFor 
//		        | DeclaracaoWhile 
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYIF)){
			producoes.set(producoes.indexOf(s), "Declaracao = DeclaracaoIf");
			declaracaoIf();
		}else if(currentTk.checkType(TokenType.KEYWHL)){
			producoes.set(producoes.indexOf(s), "Declaracao = DeclaracaoWhile");
			declaracaoWhile();
		}else if(currentTk.checkType(TokenType.KEYFOR)){
			producoes.set(producoes.indexOf(s), "Declaracao = DeclaracaoFor");
			declaracaoFor();
		}else{
			producoes.set(producoes.indexOf(s), "Declaracao = DeclaracaoSemSubDeclaracaoDireta");
			declaracaoSemSubDeclaracaoDireta();
		}
	}
	
	public void declaracaoSemSubDeclaracaoDireta(){
//		DeclaracaoSemSubDeclaracaoDireta = Bloco 
//				| ';' 
//				| ExpressaoDeclaracao 
//		        | DeclaracaoReturn 
//		        | DeclaracaoBreak
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPACH)){
			producoes.set(producoes.indexOf(s), "DeclaracaoSemSubDeclaracaoDireta = Bloco");
			bloco();
		}else if(currentTk.checkType(TokenType.SEPPEV)){
			producoes.set(producoes.indexOf(s), "DeclaracaoSemSubDeclaracaoDireta = ';'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYRET)){
			producoes.set(producoes.indexOf(s), "DeclaracaoSemSubDeclaracaoDireta = DeclaracaoReturn");
			declaracaoReturn();
		}else if(currentTk.checkType(TokenType.KEYBRK)){
			producoes.set(producoes.indexOf(s), "DeclaracaoSemSubDeclaracaoDireta = DeclaracaoBreak");
			declaracaoBreak();
		}else{
			producoes.set(producoes.indexOf(s), "DeclaracaoSemSubDeclaracaoDireta = ExpressaoDeclaracao");
			expressaoDeclaracao();
		}
	}
	
	public void expressaoDeclaracao(){
//		ExpressaoDeclaracao = Atribuicao 
//				| ExpressaoIncrementoPre 
//		        | ExpressaoDecrementoPre 
//		        | ChamadaMetodo
//		        | ExpressaoCriaInstanciaDeClasse 
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYCLL)){
			producoes.set(producoes.indexOf(s), "ExpressaoDeclaracao = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			producoes.set(producoes.indexOf(s), "ExpressaoDeclaracao = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			producoes.set(producoes.indexOf(s), "ExpressaoDeclaracao = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			producoes.set(producoes.indexOf(s), "ExpressaoDeclaracao = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoDeclaracao = Atribuicao ");
			atribuicao();
		}
	}	
	
	public void declaracaoIf(){
//		DeclaracaoIf = 'if' '[' ExpressaoCondicional ']'  Declaracao   DeclaracaoElse
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYIF)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
					producoes.set(producoes.indexOf(s), "DeclaracaoIf = 'if'"+" ("+tk1.getValue()+") '['"
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
	
	public void declaracaoElse(){
//		DeclaracaoElse = 'else' Declaracao
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYELS)){
			producoes.set(producoes.indexOf(s), "DeclaracaoElse = 'else'"+" ("+currentTk.getValue()+")"+" Declaracao");
			getToken();
			declaracao();
		}else{
			
		}
	}
	
	public void declaracaoWhile(){
//		DeclaracaoWhile = 'while' '[' ExpressaoCondicional ']' Declaracao
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYWHL)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				expressaoCondicional();
				if(currentTk.checkType(TokenType.SEPFCL)){
					producoes.set(producoes.indexOf(s), "DeclaracaoWhile = 'while'"+" ("+tk1.getValue()+") '['"
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
	
	public void declaracaoFor(){
//		DeclaracaoFor = 'for' '[' InicializadorFor ';' LimiteSuperiorFor ';' PassoFor ']' Declaracao
		String s = defStr();
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
							producoes.set(producoes.indexOf(s), "DeclaracaoFor = 'for'"+" ("+tk1.getValue()+") '['"
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
	
	public void inicializadorFor(){
//		InicializadorFor = 'int' 'id' '=' ValorInicializadorFor
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYINT)){
			Token tk1 = currentTk;
			getToken();			
			if(currentTk.checkType(TokenType.ID)){
				Token tk2 = currentTk;
				getToken();
				if(currentTk.checkType(TokenType.OPRATR)){
					producoes.set(producoes.indexOf(s), "InicializadorFor = 'int'"+" ("+tk1.getValue()+") 'id'"
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
	
	public void valorInicializadorFor(){
//		ValorInicializadorFor = Literal
//				| 'id'
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "ValorInicializadorFor = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			producoes.set(producoes.indexOf(s), "ValorInicializadorFor = Literal");
			literal();
		}
	}
	
	public void limiteSuperiorFor(){
//		LimiteSuperiorFor = ConstanteNumerica 
//				| Nome
		String s = defStr();
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			producoes.set(producoes.indexOf(s), "LimiteSuperiorFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			producoes.set(producoes.indexOf(s), "LimiteSuperiorFor = Nome");
			nome();
		}
	}
	
	public void passoFor(){
//		PassoFor = ConstanteNumerica 
//				| Nome
		String s = defStr();
		if(currentTk.checkType(TokenType.CTNDBL)||currentTk.checkType(TokenType.CTNINT)){
			producoes.set(producoes.indexOf(s), "PassoFor = ConstanteNumerica");
			constanteNumerica();
		}else{
			producoes.set(producoes.indexOf(s), "PassoFor = Nome");
			nome();
		}
	}
	
	public void declaracaoReturn(){
//		DeclaracaoReturn = 'return' ExpressaoAtribuicao ';' 
//				| 'return' ';'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYRET)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				producoes.set(producoes.indexOf(s), "DeclaracaoReturn = 'return'"+" ("+tk1.getValue()+") ';'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
					producoes.set(producoes.indexOf(s), "DeclaracaoReturn = 'return'"+" ("+tk1.getValue()+") ExpressaoAtribuicao ';' "
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
	
	public void declaracaoBreak(){
//		DeclaracaoBreak = 'break' ExpressaoAtribuicao ';' 
//				| 'break' ';'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYBRK)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.SEPPEV)){
				producoes.set(producoes.indexOf(s), "DeclaracaoBreak = 'break'"+" ("+tk1.getValue()+") ';'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPPEV)){
					producoes.set(producoes.indexOf(s), "DeclaracaoBreak = 'break'"+" ("+tk1.getValue()+") ExpressaoAtribuicao ';' "
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
	
	public void expressaoCriaInstanciaDeClasse(){
//		ExpressaoCriaInstanciaDeClasse = 'new' 'id' '[' ListaDeArgumentos ']'
		String s = defStr();
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
						producoes.set(producoes.indexOf(s), "ExpressaoCriaInstanciaDeClasse = 'new'"+" ("+tk1.getValue()+") 'id'"
								+" ("+tk2.getValue()+") '[' ListaDeArgumentos"+" ("+tk3.getValue()
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
	
	public void listaDeArgumentos(){
//		ListaDeArgumentos = ExpressaoAtribuicao   ListaDeArgumentos1
//				| null
		String s = defStr();
		if(!currentTk.checkType(TokenType.SEPFCL)){
			producoes.set(producoes.indexOf(s), "ListaDeArgumentos = ExpressaoAtribuicao   ListaDeArgumentos1");
			expressaoAtribuicao();
			listaDeArgumentos1();
		}else{
			producoes.set(producoes.indexOf(s), "ListaDeArgumentos = null");
		}
		
	}
	
	public void listaDeArgumentos1(){
//		ListaDeArgumentos1 = ',' ListaDeArgumentos   ListaDeArgumentos1 
//				| null 
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPVRG)){
			producoes.set(producoes.indexOf(s), "ListaDeArgumentos1 = ','"+" ("+currentTk.getValue()+")"+" ListaDeArgumentos   ListaDeArgumentos1 ");
			getToken();
			listaDeArgumentos();
			listaDeArgumentos1();
		}else{
			producoes.set(producoes.indexOf(s), "ListaDeArgumentos1 = null");
		}
	}
	
	public void expressaoCriaArray(){
//		ExpressaoCriaArray = 'array' '[' Tipo ',' TamanhoArray ']'
		String s = defStr();
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
						producoes.set(producoes.indexOf(s), "ExpressaoCriaArray = 'array'"+" ("+tk1.getValue()+") '[' Tipo"
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
	
	public void tamanhoArray(){
//		TamanhoArray = 'id'
//				| 'cntInt'
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "TamanhoArray = 'id'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.KEYINT)){
			producoes.set(producoes.indexOf(s), "TamanhoArray = 'cntInt'"+" ("+currentTk.getValue()+")");
			getToken();
		}
	}
	
	public void chamadaMetodo(){
//		ChamadaMetodo = 'call' NomeMetodo '[' ListaDeArgumentos ']'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYCLL)){
			Token tk1 = currentTk;
			getToken();	
			nomeMetodo();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				listaDeArgumentos();
				if(currentTk.checkType(TokenType.SEPFCL)){
					producoes.set(producoes.indexOf(s), "ChamadaMetodo = 'call'"+" ("+tk1.getValue()+") NomeMetodo '['"
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
	
	public void nomeMetodo(){
//		NomeMetodo = Nome NomeMetodo1
//					| 'print'
//					| 'read'
		String s = defStr();		
		if(currentTk.checkType(TokenType.KEYPRT)||currentTk.checkType(TokenType.KEYREA)){
			producoes.set(producoes.indexOf(s), "NomeMetodo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			producoes.set(producoes.indexOf(s), "NomeMetodo = Nome NomeMetodo1");
			nome();
			nomeMetodo1();
		}
	}
	
	public void nomeMetodo1(){
//		NomeMetodo1 = '.' NomeMetodo
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPPNT)){
			producoes.set(producoes.indexOf(s), "NomeMetodo1 = '.'"+" ("+currentTk.getValue()+")"+" NomeMetodo");
			getToken();
			nomeMetodo();
		}else{
			producoes.set(producoes.indexOf(s), "NomeMetodo1 = null");
		}
	}
	
	public void acessoArray(){
//		AcessoArray = 'aaray' Nome '[' ExpressaoAtribuicao ']'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYAAY)){
			Token tk1 = currentTk;
			getToken();	
			nome();
			if(currentTk.checkType(TokenType.SEPACL)){
				Token tk2 = currentTk;
				getToken();
				expressaoAtribuicao();
				if(currentTk.checkType(TokenType.SEPFCL)){
					producoes.set(producoes.indexOf(s), "AcessoArray = 'aaray'"+" ("+tk1.getValue()+") Nome '['"
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
	
	public void primario(){
//		Primario = PrimairoSemNovoArray 
//				| ExpressaoCriaArray
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYARY)){
			producoes.set(producoes.indexOf(s), "Primario = ExpressaoCriaArray ");
			expressaoCriaArray();
		}else{
			producoes.set(producoes.indexOf(s), "Primario = PrimairoSemNovoArray ");
			primairoSemNovoArray();
		}
	}
	
	public void primairoSemNovoArray(){
//		PrimairoSemNovoArray = Literal
//		    	| 'this' 
//		        | '[' ExpressaoAtribuicao ']'
//		        | ExpressaoCriaInstanciaDeClasse
//		    	| ChamadaMetodo 
//				| AcessoArray
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYTHS)){
			producoes.set(producoes.indexOf(s), "PrimairoSemNovoArray = 'this'"+" ("+currentTk.getValue()+")");
			getToken();
		}else if(currentTk.checkType(TokenType.SEPACL)){
			Token tk1 = currentTk;
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				producoes.set(producoes.indexOf(s), "PrimairoSemNovoArray = '['"+" ("+tk1.getValue()
					+") ExpressaoAtribuicao ']'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else if(currentTk.checkType(TokenType.CNTLGC)||currentTk.checkType(TokenType.CNTSTR)||currentTk.checkType(TokenType.CNTCHR)||
				currentTk.checkType(TokenType.CTNINT)||currentTk.checkType(TokenType.CTNDBL)){
			producoes.set(producoes.indexOf(s), "PrimairoSemNovoArray = Literal");
			literal();
		}else if(currentTk.checkType(TokenType.KEYNEW)){
			producoes.set(producoes.indexOf(s), "PrimairoSemNovoArray = ExpressaoCriaInstanciaDeClasse");
			expressaoCriaInstanciaDeClasse();
		}else if(currentTk.checkType(TokenType.KEYCLL)){
			producoes.set(producoes.indexOf(s), "PrimairoSemNovoArray = ChamadaMetodo");
			chamadaMetodo();
		}else if(currentTk.checkType(TokenType.KEYAAY)){
			producoes.set(producoes.indexOf(s), "PrimairoSemNovoArray = AcessoArray");
			acessoArray();
		}else{
			erro();
		}
	}
	
	public void argumentos(){
//		Argumentos = '[' ExpressaoAtribuicao ']'
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.SEPACL)){
			Token tk1 = currentTk;
			getToken();
			expressaoAtribuicao();
			if(currentTk.checkType(TokenType.SEPFCL)){
				producoes.set(producoes.indexOf(s), "Argumentos = '['"+" ("+tk1.getValue()+") ExpressaoAtribuicao ']'"
						+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			producoes.set(producoes.indexOf(s), "Argumentos = null");
		}
	}
	
	public void atribuicao(){
//		Atribuicao = LadoEsquerdo '=' ExpressaoAtribuicao 
		String s = defStr();
		ladoEsquerdo();
		if(currentTk.checkType(TokenType.OPRATR)){
			producoes.set(producoes.indexOf(s), "Atribuicao = LadoEsquerdo '='"+" ("+currentTk.getValue()+")"+" ExpressaoAtribuicao ");
			getToken();
			expressaoAtribuicao();
		}else{
			erro();
		}
	}
	
	public void ladoEsquerdo(){
//		LadoEsquerdo = AcessoArray
//				| Nome1 Argumentos
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYAAY)){
			producoes.set(producoes.indexOf(s), "LadoEsquerdo = AcessoArray");
			acessoArray();
		}else{
			producoes.set(producoes.indexOf(s), "LadoEsquerdo = Nome1 Argumentos");
			nome1();
			argumentos();
		}
	}
	
	public void nome1(){
//		Nome1 = 'id' Nome1
//				| Tipo Nome1
//				| null	
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "Nome1 = 'id'"+" ("+currentTk.getValue()+") Nome1");
			getToken();
			nome1();
		}else if(currentTk.checkType(TokenType.KEYINT)||currentTk.checkType(TokenType.KEYDBL)||currentTk.checkType(TokenType.KEYLGC)||
				currentTk.checkType(TokenType.KEYCHR)||currentTk.checkType(TokenType.KEYSTR)){
			producoes.set(producoes.indexOf(s), "Nome1 = Tipo Nome1");
			tipo();
			nome1();
		}else{
			producoes.set(producoes.indexOf(s), "Nome1 = null");
		}
	}
	
	public void expressaoAtribuicao(){
//		ExpressaoAtribuicao = ExpressaoCondicional
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoAtribuicao = ExpressaoCondicional");
		expressaoCondicional();
	}
	
	public void expressaoCondicional(){
//		ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoCondicional = ExpressaoOuCondicional   ExpressaoCondicional1");
		expressaoOuCondicional();
		expressaoCondicional1();
	}
	
	public void expressaoCondicional1(){
//		ExpressaoCondicional1 = '?' Expressao ':' ExpressaoCondicional
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRTRI)){
			Token tk1 = currentTk;
			getToken();
			expressao();
			if(currentTk.checkType(TokenType.SEPDPT)){
				producoes.set(producoes.indexOf(s), "ExpressaoCondicional1 = '?'"+" ("+tk1.getValue()
						+") Expressao ':'"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional");
				getToken();
				expressaoCondicional();
			}else{
				erro();
			}
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoCondicional1 = null");
		}
	}
	
	public void expressaoOuCondicional(){
//		ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoOuCondicional = ExpressaoECondicional   ExpressaoOuCondicional1");
		expressaoECondicional();
		expressaoOuCondicional1();
	}
	
	public void expressaoOuCondicional1(){
//		ExpressaoOuCondicional1 = '||' ExpressaoCondicional   ExpressaoOuCondicional1 
//				| null 
		String s = defStr();		
		if(currentTk.checkType(TokenType.OPROCD)){
			producoes.set(producoes.indexOf(s), "ExpressaoOuCondicional1 = '||'"+" ("+currentTk.getValue()+")"+" ExpressaoCondicional   ExpressaoOuCondicional1 ");
			getToken();
			expressaoCondicional();
			expressaoOuCondicional1();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoOuCondicional1 = null");
		}
	}
	
	public void expressaoECondicional(){
//		ExpressaoECondicional = Expressao   ExpressaoECondicional1 
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoECondicional = Expressao   ExpressaoECondicional1");
		expressao();
		expressaoECondicional1();
	}
	
	public void expressaoECondicional1(){
//		ExpressaoECondicional1 = '&&' Expressao   ExpressaoECondicional1
//				| null 
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRECD)){
			producoes.set(producoes.indexOf(s), "ExpressaoECondicional1 = '&&'"+" ("+currentTk.getValue()+")"+" Expressao   ExpressaoECondicional1");
			getToken();
			expressao();
			expressaoECondicional1();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoECondicional1 = null");
		}
	}
	
	public void expressao(){
//		Expressao = ExpressaoEqualidade   Expressao1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "Expressao = ExpressaoEqualidade   Expressao1");
		expressaoEqualidade();
		expressao1();
	}
	
	public void expressao1(){
//		Expressao1 = '&' ExpressaoEqualidade   ExpressaoEqualidade1 
//				| null 
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRE)){
			producoes.set(producoes.indexOf(s), "Expressao1 = '&'"+" ("+currentTk.getValue()+")"+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			producoes.set(producoes.indexOf(s), "Expressao1 = null");
		}
	}
	
	public void expressaoEqualidade(){
//		ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoEqualidade = ExpressaoRelacional ExpressaoEqualidade1");
		expressaoRelacional();
		expressaoEqualidade1();
	}
	
	public void expressaoEqualidade1(){
//		ExpressaoEqualidade1 = '==' ExpressaoEqualidade   ExpressaoEqualidade1  
//			    | '!=' ExpressaoEqualidade   ExpressaoEqualidade1 
//			    | null 
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRIGL)||currentTk.checkType(TokenType.OPRDIF)){
			producoes.set(producoes.indexOf(s), "ExpressaoEqualidade1 ="+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoEqualidade   ExpressaoEqualidade1");
			getToken();
			expressaoEqualidade();
			expressaoEqualidade1();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoEqualidade1 = null");
		}
	}
	
	public void expressaoRelacional(){
//		ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoRelacional = ExpressaoAditiva   ExpressaoRelacional1");
		expressaoAditiva();
		expressaoRelacional1();
	}
	
	public void expressaoRelacional1(){
//		ExpressaoRelacional1 =  ExpressaoMenor 
//				| ExpressaoMaior 
//				| ExpressaoMenorOuIgual 
//				| ExpressaoMaiorOuIgual 
//				| ExpressaoInstance	
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMNR)){
			producoes.set(producoes.indexOf(s), "ExpressaoRelacional1 = ExpressaoMenor");
			expressaoMenor();
		}else if(currentTk.checkType(TokenType.OPRMAR)){
			producoes.set(producoes.indexOf(s), "ExpressaoRelacional1 = ExpressaoMaior");
			expressaoMaior();
		}else if(currentTk.checkType(TokenType.OPRMEI)){
			producoes.set(producoes.indexOf(s), "ExpressaoRelacional1 = ExpressaoMenorOuIgual");
			expressaoMenorOuIgual();
		}else if(currentTk.checkType(TokenType.OPRMAI)){
			producoes.set(producoes.indexOf(s), "ExpressaoRelacional1 = ExpressaoMaiorOuIgual");
			expressaoMaiorOuIgual();
		}else if(currentTk.checkType(TokenType.KEYIOF)){
			producoes.set(producoes.indexOf(s), "ExpressaoRelacional1 = ExpressaoInstance");
			expressaoInstance();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoRelacional1 = null");
		}
	}
	
	public void expressaoMenor(){
//		ExpressaoMenor = '<' ExpressaoAditiva
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMNR)){
			producoes.set(producoes.indexOf(s), "ExpressaoMenor = '<'"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public void expressaoMaior(){
//		ExpressaoMaior = '<' ExpressaoAditiva
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMAR)){
			producoes.set(producoes.indexOf(s), "ExpressaoMaior = '<'"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public void expressaoMenorOuIgual(){
//		ExpressaoMenorOuIgual = '<=' ExpressaoAditiva
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMEI)){
			producoes.set(producoes.indexOf(s), "ExpressaoMenorOuIgual =  '<='"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public void expressaoMaiorOuIgual(){
//		ExpressaoMaiorOuIgual = '>=' ExpressaoAditiva
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMAI)){
			producoes.set(producoes.indexOf(s), "ExpressaoMaiorOuIgual =  '>='"+" ("+currentTk.getValue()+")"+" ExpressaoAditiva");
			getToken();
			expressaoAditiva();		
		}else{
			erro();
		}
	}
	
	public void expressaoInstance(){
//		ExpressaoInstance = 'instanceof' 'id'
		String s = defStr();
		if(currentTk.checkType(TokenType.KEYIOF)){
			Token tk1 = currentTk;
			getToken();
			if(currentTk.checkType(TokenType.ID)){	
				producoes.set(producoes.indexOf(s), "ExpressaoInstance = 'instanceof'"+" ("+tk1.getValue()
						+") 'id'"+" ("+currentTk.getValue()+")");
				getToken();
			}else{
				erro();
			}
		}else{
			erro();
		}
	}
	
	public void expressaoAditiva(){
//		ExpressaoAditiva = ExpressaoMultiplicacao   ExpressaoAditiva1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoAditiva = ExpressaoMultiplicacao   ExpressaoAditiva1");
		expressaoMultiplicativa();
		expressaoAditiva1();
	}
	
	public void expressaoAditiva1(){
//		ExpressaoAditiva1 = '+' ExpressaoMultiplicacao   ExpressaoAditiva1
//		        |  '-' ExpressaoMultiplicacao   ExpressaoAditiva1 
//		        | null
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			producoes.set(producoes.indexOf(s), "ExpressaoAditiva1 = "+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoMultiplicacao   ExpressaoAditiva1");
			expressaoMultiplicativa();
			expressaoAditiva1();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoAditiva1 = null");
		}
	}
	
	public void expressaoMultiplicativa(){
//		ExpressaoMultiplicativa = ExpressaoUnaria   ExpressaoMultiplicativa1
		String s = defStr();
		producoes.set(producoes.indexOf(s), "ExpressaoMultiplicacao = ExpressaoUnaria   ExpressaoMultiplicacao1");
		expressaoUnaria();
		expressaoMultiplicativa1();
	}
	
	public void expressaoMultiplicativa1(){
//		ExpressaoMultiplicativa1 = ExpressaoMultiplicacao 
//				| ExpressaoDivisao 
//				| ExpressaoModulo
//				| null	
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMTL)){
			producoes.set(producoes.indexOf(s), "ExpressaoMultiplicacao1 = ExpressaoMultiplicacao ");
			expressaoMultiplicacao();
		}else if(currentTk.checkType(TokenType.OPRDIV)){
			producoes.set(producoes.indexOf(s), "ExpressaoMultiplicacao1 = ExpressaoDivisao ");
			expressaoDivisao();
		}else if(currentTk.checkType(TokenType.OPRMOD)){
			producoes.set(producoes.indexOf(s), "ExpressaoMultiplicacao1 = ExpressaoModulo ");
			expressaoModulo();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoMultiplicacao1 = null");
		}
	}
	
	public void expressaoMultiplicacao(){
//		ExpressaoMultiplicacao = '*' ExpressaoUnaria   ExpressaoMultiplicativa
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMTL)){
			producoes.set(producoes.indexOf(s), "ExpressaoMultiplicacao = '*'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public void expressaoDivisao(){
//		ExpressaoDivisao = '/' ExpressaoUnaria   ExpressaoMultiplicativa
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRDIV)){
			producoes.set(producoes.indexOf(s), "ExpressaoDivisao = '/'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public void expressaoModulo(){
//		ExpressaoModulo = '%' ExpressaoUnaria   ExpressaoMultiplicativa
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMOD)){
			producoes.set(producoes.indexOf(s), "ExpressaoModulo = '%'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria   ExpressaoMultiplicativa1");
			getToken();
			expressaoUnaria();
			expressaoMultiplicativa1();
		}else{
			erro();
		}
	}
	
	public void expressaoIncrementoPre(){
//		ExpressaoIncrementoPre = '++' ExpressaoUnaria
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMMA)){
			producoes.set(producoes.indexOf(s), "ExpressaoIncrementoPre = '++'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}
	}
	
	public void expressaoDecrementoPre(){
//		ExpressaoDecrementoPre = '--' ExpressaoUnaria
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMME)){
			producoes.set(producoes.indexOf(s), "ExpressaoDecrementoPre = '--'"+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}
	}
	
	public void expressaoUnaria(){
//		ExpressaoUnaria = ExpressaoIncrementoPre
//				| ExpressaoDecrementoPre 
//				| '+' ExpressaoUnaria
//				| '-' ExpressaoUnaria 
//				| ExpressaoPosFixada
		String s = defStr();		
		if(currentTk.checkType(TokenType.OPRADC)||currentTk.checkType(TokenType.OPRMEN)){
			producoes.set(producoes.indexOf(s), "ExpressaoUnaria = "+currentTk.getCategory()+" ("+currentTk.getValue()+")"+" ExpressaoUnaria");
			getToken();
			expressaoUnaria();
		}else if(currentTk.checkType(TokenType.OPRMME)){
			producoes.set(producoes.indexOf(s), "ExpressaoUnaria = ExpressaoDecrementoPre");
			expressaoDecrementoPre();
		}else if(currentTk.checkType(TokenType.OPRMMA)){
			producoes.set(producoes.indexOf(s), "ExpressaoUnaria = ExpressaoIncrementoPre");
			expressaoIncrementoPre();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoUnaria = ExpressaoPosFixada");
			expressaoPosFixada();
		}	
	}
	
	public void expressaoIncrementoPos(){
//		ExpressaoIncrementoPos = ExpressaoPosFixada '++'
		String s = defStr();
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMMA)){
			producoes.set(producoes.indexOf(s), "ExpressaoIncrementoPos = ExpressaoPosFixada '++'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			erro();
		}
	}
	
	public void expressaoDecrementoPos(){
		String s = defStr();
//		ExpressaoDecrementoPos = ExpressaoPosFixada '--'
		expressaoPosFixada();
		if(currentTk.checkType(TokenType.OPRMME)){
			producoes.set(producoes.indexOf(s), "ExpressaoDecrementoPos = ExpressaoPosFixada '--'"+" ("+currentTk.getValue()+")");
			getToken();
		}else{
				erro();
		}
	}
	
	public void expressaoPosFixada(){
//		ExpressaoPosFixada = Primario PosFixo
//				| Nome PosFixo
		String s = defStr();
		if(currentTk.checkType(TokenType.ID)){
			producoes.set(producoes.indexOf(s), "ExpressaoPosFixada = Nome PosFixo");
			nome();
			posFixo();
		}else{
			producoes.set(producoes.indexOf(s), "ExpressaoPosFixada = Primario PosFixo");
			primario();
			posFixo();
		}
	}
	
	public void posFixo(){
//		PoxFixo = '++'
//				| '--'
//				| null
		String s = defStr();
		if(currentTk.checkType(TokenType.OPRMMA)||currentTk.checkType(TokenType.OPRMME)){
			producoes.set(producoes.indexOf(s), "PoxFixo = "+currentTk.getCategory()+" ("+currentTk.getValue()+")");
			getToken();
		}else{
			producoes.set(producoes.indexOf(s), "PoxFixo = null");
		}
	}
}	
