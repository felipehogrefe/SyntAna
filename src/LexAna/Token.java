package LexAna;

public class Token{
	public TokenType type;
	public String val;
	public int line;
	public int col;
	
	public Token (int line, int col,String val,LexicalAnalyzer la){
		this.type = setType(val,la);
		this.val = val;
		this.line = line;
		this.col = col;
	}
	
	public boolean checkType(TokenType tt){
		if(this.type==tt){
			return true;
		}
		return false;
	}
	
	private TokenType setType(String val,LexicalAnalyzer la) {
		//seta a categoria do token
		if(checkNUN(val,la)){
			return TokenType.OPRNUN;
		}else if(checkINT(val)){
			return TokenType.CTNINT;
		}else if(checkDBL(val)){
			return TokenType.CTNDBL;
		}else if(checkLGC(val)){
			return TokenType.CNTLGC;
		}else if(checkCHR(val)){
			return TokenType.CNTCHR;
		}else if(checkSTR(val)){
			return TokenType.CNTSTR;
		}else if(LexTable.lMap.containsKey(val)){
			//palavras reservadas
			return LexTable.lMap.get(val);
		}else if(checkCOM(val)){
			return TokenType.COM;
		}else if(checkID(val)){
			return TokenType.ID;
		}
		return null;
	}
	private boolean checkCOM(String val) {
		if(val.startsWith("//")){
			//comentario de linha unica
			return true;
		}
		return false;
	}
	
	private boolean checkINT(String val){
		if(val.matches("(\\d)+")){
			return true;
		}
		return false;
	}
	
	private boolean checkDBL(String val){
		if(val.matches("(\\d)+\\.(\\d)+")){
			return true;
		}
		return false;
	}
	
	private boolean checkLGC(String val){
		if(val == "true"||val == "false"){
			return true;
		}
		return false;
	}
	
	private boolean checkCHR(String val){
		if(val.startsWith("\'") && val.endsWith("\'")){
			return true;
		}
		return false;
	}
	
	private boolean checkSTR(String val){
		if(val.startsWith("\"") && val.endsWith("\"")){
			return true;
		}
		return false;
	}

	
	private boolean checkLIT(String val) {
		if(val=="(\\d)+\\.(\\d)+"||val=="(\\d)+\\."||val=="(\\d)+"){
			//numericas
			return true;
		}else if (val.startsWith("\"") && val.endsWith("\"")){
			//strings
			return true;
		}else if(val.matches("'(.?)'")){
			//chars
			return true;
		}
		else if(val == "true"||val == "false"){
			//booleanos
			return true;
		}else if(val=="\n"||val=="\t"||val=="\b"||val=="\r"||val=="\f"||val=="\\"
				||val=="\\"||val=="\'"||val=="\""){
			return true;
		}
		return false;
	}

	private boolean checkNUN(String val,LexicalAnalyzer la) {
		//separadores
		if(val.equals("-")){
			Character previousChar = previousNotBlankChar(la);
			if ((previousChar != null)
					&& Character.toString(previousChar).matches("[_a-zA-Z0-9]")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean checkOPR(String val){
		//operadores
		if(val.matches("[=<>!|+-^*~?:&/%]")||val.matches("[=<>!+^*|-~&/%][=]")||val=="++"
				||val=="--"||val==">>"||val=="<<"){
			return true;
		}
		return false;
	}

	private boolean checkID(String val) {
		//identificadores
		if (val.matches("^[A-Za-z][A-Za-z0-9_]*")) {
			return true;			
		}
		return false;
	}
	
	public Character previousNotBlankChar(LexicalAnalyzer la) {

		int previousColumn = la.gettStartC() - 1;
		char previousChar;

		while (previousColumn >= 0) {
			previousChar = la.getLine().charAt(previousColumn);
			if (previousChar != ' ' && previousChar != '\t') {
				return previousChar;
			}
			previousColumn--;
		}
		return null;
	}

	public TokenType getCategory() {
		return this.type;
	}
	
	public String toString(){
		return line+", "+col+", "+type+", "+val;
	}

	public String getValue() {
		return val;
	}
}