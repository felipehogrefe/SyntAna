package LexAna;

public enum TokenType {	
	
	ID(1),
	KEYINT(2), 
	KEYDBL(3), 
	KEYLGC(4), 
	KEYCHR(5), 
	KEYLNG(6),
	KEYSTR(7),
	CTNINT(8), 
	CTNDBL(9), 
	CNTLGC(10), 
	CNTCHR(11), 
	CNTSTR(12),
	COM(13), 
	KEYCLS(14),
	KEYMAN(15),
	KEYSTA(16),
	KEYPRT(17),
	KEYREA(18),
	KEYFOR(19),
	KEYWHL(20),
	KEYIF(21),
	KEYRET(22),
	KEYELS(23),
	KEYTHS(24),
	KEYNEW(25),
	KEYARY(26),
	KEYIOF(27),
	SEPPEV(28),
	SEPVRG(29),
	SEPPNT(30),
	SEPAPR(31),
	SEPFPR(32),
	SEPACH(33),
	SEPFCH(34),
	SEPACL(35),
	SEPFCL(36),
	OPRMMA(37),//++
	OPRMME(38),//--
	OPRE(39),//&
	OPRECD(40),//&&
	OPROCD(41),//||
	OPRDPT(42),//:
	OPRTRI(43),//?
	OPRDIV(44),
	OPRADC(45),
	OPRMEN(46),
	OPRATR(47),//=
	OPRMNR(48),//<
	OPRMAR(49),//>
	OPRMEI(50),//<=
	OPRMAI(51),//>=
	OPRNAO(52),//~
	OPRMTL(53),//*
	OPRDIF(54),//!=
	OPREOR(55),//^
	OPRIOR(56),//|
	OPRIGL(57),//==
	KEYMAIN(58),
	KEYPVT(59),
	KEYPBL(60),
	KEYSTC(61),
	OPRNEG(62),
	OPRNOT(63),
	OPRMOD(64),
	OPRICI(65),//+=
	OPRDCI(66),//-=
	OPRMTI(67),//*/
	OPRDVI(68),// /=
	OPRNUN(69),
	NULL(70);
	
	private int value;
	
	private TokenType(int val){
		this.value=val;
	}
	
	public int getType(){
		return value;
	}

}
