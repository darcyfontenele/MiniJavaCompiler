package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class Call extends Exp {
  public Exp e;
  public Identifier i;
  public ExpList el;
  
  public Call(Exp ae, Identifier ai, ExpList ael, int ln) {
    super(ln);
    e=ae; i=ai; el=ael;
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public String accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
		 return v.visit(this, mapa, nivel, cl, mt);
  }

}
