package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class ArrayAssign extends Statement {
  public Identifier i;
  public Exp e1,e2;

  public ArrayAssign(Identifier ai, Exp ae1, Exp ae2, int ln) {
    super(ln);
    i=ai; e1=ae1; e2=ae2;
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
	 v.visit(this, mapa, nivel, cl, mt);
  }

}

