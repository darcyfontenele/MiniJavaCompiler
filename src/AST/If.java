package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public class If extends Statement {
  public Exp e;
  public Statement s1,s2;

  public If(Exp ae, Statement as1, Statement as2, int ln) {
    super(ln);
    e=ae; s1=as1; s2=as2;
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
	 v.visit(this, mapa, nivel, cl, mt);
  }

}

