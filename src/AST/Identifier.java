package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class Identifier extends ASTNode {
  public String s;

  public Identifier(String as, int ln) { 
    super(ln);
    s=as;
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
	 v.visit(this, mapa, nivel, cl, mt);
  }


  public String toString(){
    return s;
  }
  public String getType(){
	  return (String)"null";
  }
}
