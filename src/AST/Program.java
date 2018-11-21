package AST;
import java.util.Map;

import AST.Visitor.*;
import Tabelas.*;

public class Program extends ASTNode {
  public MainClass m;
  public ClassDeclList cl;

  public Program(MainClass am, ClassDeclList acl, int ln) {
    super(ln);
    m=am; cl=acl; 
  }

  public void accept(Visitor v, Map mapa, int level, int nivel) {
    v.visit(this, mapa, level, nivel);
  }
  
  public void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt) {
	 v.visit(this, mapa, nivel, cl, mt);
  }
  
}
