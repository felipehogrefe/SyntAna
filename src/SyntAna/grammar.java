CONSTANTE = CNT | CTN
CNT = �cntLgc� 
| �cntChr� 
| �cntStr�
CTN  = �ctnInt� 
| �ctnDbl� 
| �ctnLng�
TIPO = TIPOPRIMITIVO 
| TIPOREF
TIPOPRIMITIVO = TIPONUMERICO 
| �tkLgc�
TIPONUMERICO = TIPOINT 
| TIPOPFLUT
TIPOINT = �tkInt� 
| �tkChr� 
| �tkLng�
TIPOPFLUT = �tkDbl�
TIPOREF = TIPOCLASSE 
|TIPOARRAY
TIPOCLASSE = NOME
TIPOARRAY = TIPOPRIMITIVO �[� �]� 
| NOME �[� �]�
NOME = NOMESIMPLES 
| NOMECOMP
NOMESIMPLES = �id�
NOMECOMP = NOME �.� �id�
MODIFICADOR  = �static� 
| null

UNIDADECOMP = DECCLASSE

DECCLASSE = MODIFICADOR �class� �id� CORPOCLASSE
CORPOCLASSE = �{� DECSCORPOCLASSE �}�
DECSCORPOCLASSE = DECCORPOCLASSE DECC1
DECC1 = DECCORPOCLASSE DECC� 
| null
DECCORPOCLASSE  = DECMEMBROCLASSE 
| DECCONSTRU
DECMEMBROCLASSE = DECCAMPO 
| DECMETODO
ECCAMPO = MODIFICADOR TIPO DECSVARIAVEL �;�
DECSVARIAVEL = DECVARIAVEL DECV1
DECV1 = �,�  DECVARIAVEL DECV1 
| null
DECVARIAVEL = DECVARIAVELID DECVARIAVELF
DECVARIAVELF = �=� INICVARIAVEL 
| null
DECVARIAVELID = �id� 
| DECVARIAVELID �[� �]�
INICVARIAVEL = EXPRESSAO | INICARRAY
DECMETODO = CABMETODO CORPOMETODO
CABMETODO = TIPO METODODEC 
| METODODEC
METODODEC = �id� �[� LISTPARAMFORMAL �]�
LISTPARAMFORMAL = PARAMFORMAIS LISTPARAMFORMAL1
LISTPARAMFORMAL1 = �,� PARAMFORMAIS LISTPARAMFORMAL
PARAMFORMAIS = TIPO DECVARIAVELID 
CORPOMETODO = BLOCO 
| �;�
DECCONSTRU = NOMESIMPLES �[� LISTPARAMFORMAL �]�
BLOCO = �{� DECBLOCO �}�
DECBLOCO = BLOCODEC DECB1
DECB1 = DECBLOCO DECB� 
| null
BLOCODEC = DECCAMPO 
| DECLARACAO
DECLARACAO = DECSEMSUBDECDIR 
| IFTHENDEC 
| IFTHELESEDEC 
| DECWHILE 
| DECFOR 
DECSEMSUBDECDIR = BLOCO 
| �;� 
| EXPRESSAODEC 
| DECRETURN 
| DECBREAK
EXPRESSAODEC = ATRIBUICAO 
| EXPRINCRPRE 
| EXPRDECREPRE 
| EXPRINCRPOS 
| EXPRDECREPOS 
| CHAMADAMETODO 
| EXPRCRIAINSTANCLASSE 
DECNSI = DECSEMSUBDECDIR 
| ITEDECNSI 
| WDECNSI 
| FDECNSI 
IFTHENDEC = �if� �[� EXPRESSAO �]�  DECLARACAO 
IFTHELESEDEC = �if� �[� EXPRESSAO �]� DECNSI  �else� DECLARACAO
ITEDECNSI = �if� �[� EXPRESSAO �]� DECNSI �else� DECNSI 
DECWHILE = �while� �[� EXPRESSAO �]� DECLARACAO
WDECNSI = �while� �[� EXPRESSAO �]� DECNSI 
DECFOR = �for� �[� FORINIT �;� LIMITESUP �;� PASSO �]� DECLARACAO
FDECNSI = �for� �[� FORINIT �;� LIMITESUP �;� PASSO �]� DECNSI 
FORINIT = LISTADECEXPR 
| DECVARLOCAL
LIMITESUP = CTN | NOME
PASSO = CTN 
| NOME
LISTADECEXPR = DECEXPR
| LISTADECEXPR �;� DECEXPR 
DECRETURN = �return� EXPRESSAO �;� 
| �return� �;�
DECBREAK = �break� EXPRESSAO �;� 
| �break� �;�
EXPRCRIAINSTANCLASSE = �new� TIPOCLASSE �[� LISTAARG �]�
LISTAARG = EXPRESSAO 
| LISTAARG LISTA1
LISTA1 = �;� LISTAARG LISTA� 
| null 
EXPRCRIAARRAY= �array� �[� TIPO �,� CTN �]�
CHAMADAMETODO = NOME �[� LISTAARG �]�
| PRIMARIO �,� �id� �[� LISTAARG �]�
ACESSAARRAY = NOME �[� EXPRESSAO �]�
PRIMARIO = PRISEMNOVOARRAY 
| EXPRCRIAARRAY
PRISEMNOVOARRAY = LITERAL
 | �this� 
 | �[� EXPRESSAO �]�
 | EXPRCRIAINSTANCLASSE
 | ACESSACAMPO 
| CHAMADAMETODO 
| ACESSAARRAY
EXPRESSAO = EXPRATR
LADOESQ = NOME 
| ACESSACAMPO 
| ACESSAARRAY
ATRIBUICAO = LADOESQ  �=� EXPRATR 
EXPRATR = EXPRCOND 
| ATRIBUICAO
EXPRCOND = EXPRORCOND EXPRCOND1
EXPRCOND1 = EXPORC 
| �?� EXPRESSAO �:� EXPRCOND
EXPRORCOND = EXPRECOND EXPROR1
EXPROR1 = �||� EXPRECOND EXPROR1 
| null
EXPRECOND = EXPRIOR EXPREC1 
EXPREC1 = �&&� EXPRIOR EXPREC1 
| null
EXPRIOR = EXPREOR EXPRI1
EXPRI1 = �|� EXPROIR EXPRI� 
| null
EXPREOR = EXPRE EXPREOR1
EXPREOR1 = �^� EXPREOR EXPREOR1 
| null
EXPRE = EXPREQUA EXPRE1
EXPRE1 = �&� EXPREQUA EXPRE1 
| null
EXPREQUA = EXPRREL EXPREQ1
EXPREQ1 = �==� EXPREQUA EXPREQ1 
| null
| �!=� EXPREQUA EXPREQ1 
| null
EXPRREL = EXPRADD EXPRREL1
EXPRREL1 =  EXPME 
| EXPMA 
| EXPMEI 
| EXPMAI 
| EXPIOF
EXPME = �<� EXPRADD EXPME 
| null
EXPMA = �>� EXPRADD EXPMA 
| null
EXPMEI =  �<=� EXPRADD EXPMEI 
| null
EXPMAI = �>=� EXPRADD EXPMAI 
| null
EXPIOF = �instanceof� TIPOREF EXPIOF
EXPRADD = EXPRMULT EXPRADD1
EXPRADD1 = �+� EXPRMULT EXPRADD1
|  �-� EXPRMULT EXPRADD1 
| null
EXPRMULT = EXPRUNARIA EXPRMULT1
EXPRMULT1 = EXPMU | EXPDI 
| EXPMO
EXPMU = �*� EXPRUNARIA EXPMU
EXPDI = �/� EXPRUNARIA EXPDI
EXMO = �%� EXPRUNARIA EXMO
EXPUNNMM = EXPRPOSF 
| �~� EXPRUNARIA 
| �!� EXPRUNARIA
EXPRDECREPRE = �--� EXPRUNARIA
EXPRINCRPRE =  �++� EXPRUNARIA
EXPRUNARIA = EXPRINCRPRE
| EXPRDECREPRE 
| �+� EXPRUNARIA
| �-� EXPRUNARIA 
| EXPUNNMM
EXPRDECREPOS = EXPRPOSF �--�
EXPRINCRPOS = EXPRPOSF �++�
EXPRPOSF = PRIMARIO 
| NOME 
| EXPRINCRPOS 
| EXPRDECREPOS