package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class Print extends Statement {
  public Exp e;

  public Print(Exp ae, int ln) {
    super(ln);
    e=ae; 
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
	 v.visit(this, mapa, nivel, cl, mt);
  }

}
