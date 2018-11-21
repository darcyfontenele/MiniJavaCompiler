package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class Plus extends Exp {
  public Exp e1,e2;
  
  public Plus(Exp ae1, Exp ae2, int ln) { 
    super(ln);
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public String accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
		 return v.visit(this, mapa, nivel, cl, mt);
  }

}
