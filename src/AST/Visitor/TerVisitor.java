package AST.Visitor;

import AST.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import Tabelas.Classe;
import Tabelas.Metodo;

public interface TerVisitor {
  // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.
	
  public boolean eCompativel(Map mapa, String classe, String extension);
	
  public void visit(Return n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(Program n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(MainClass n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(ClassDeclSimple n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(ClassDeclExtends n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(VarDecl n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(MethodDecl n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(Formal n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(IntArrayType n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(BooleanType n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(IntegerType n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(IdentifierType n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(Block n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(If n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(While n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(Print n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(Assign n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(ArrayAssign n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(And n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(LessThan n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(Plus n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(Minus n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(Times n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(ArrayLookup n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(ArrayLength n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(Call n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(IntegerLiteral n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(True n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(False n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(IdentifierExp n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(This n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(NewArray n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(NewObject n, Map mapa, int nivel, Classe cl, Metodo mt);
  public String visit(Not n, Map mapa, int nivel, Classe cl, Metodo mt);
  public void visit(Identifier n, Map mapa, int nivel, Classe cl, Metodo mt);
}
