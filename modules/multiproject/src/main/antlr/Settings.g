grammar Settings;

options {
       output = AST;
       ASTLabelType = CommonTree;
       backtrack=true;
       memoize=true;
}


tokens {
INCLUDE;
MODULENAME;
}
 
@lexer::header{ package antw.multiproject.dsl; }
 
@header {
  package antw.multiproject.dsl;
}


settings: includes;
includes: 'include' modules -> ^(INCLUDE modules);
modules: module (',' module)*; 
module: '\'' moduleName '\'' -> ^(MODULENAME moduleName);
moduleName: Id;


fragment Digit : '0'..'9' ;
fragment Letter : 'a'..'z' | 'A'..'Z' | '_';
Id: Letter (Letter | Digit)* ;



WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ 	{ $channel = HIDDEN; } ; 
