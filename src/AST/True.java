package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class True extends Exp {
  public True(int ln) {
    super(ln);
  }
  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  public String getType(){
	  return (String)"boolean";
  }
  
  public String accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
		 return v.visit(this, mapa, nivel, cl, mt);
  }

}
