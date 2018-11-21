package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class IdentifierType extends Type {
  public String s;

  public IdentifierType(String as, int ln) {
    super(ln);
    s=as;
  }
  public String toString()
  {
	  return s;
  }
  public String getType(){
	  return s;
  }
  
  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
	 v.visit(this, mapa, nivel, cl, mt);
  }

}
