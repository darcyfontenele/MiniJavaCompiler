package AST.Visitor;

import AST.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public interface Visitor {
  // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.
  public void visit(Return n, Map mapa, int nivel, int level);
  public void visit(Program n, Map mapa, int nivel, int level);
  public void visit(MainClass n, Map mapa, int nivel, int level);
  public void visit(ClassDeclSimple n, Map mapa, int nivel, int level);
  public void visit(ClassDeclExtends n, Map mapa, int nivel, int level);
  public void visit(VarDecl n, Map mapa, int nivel, int level);
  public void visit(MethodDecl n, Map mapa, int nivel, int level);
  public void visit(Formal n, Map mapa, int nivel, int level);
  public void visit(IntArrayType n, Map mapa, int nivel, int level);
  public void visit(BooleanType n, Map mapa, int nivel, int level);
  public void visit(IntegerType n, Map mapa, int nivel, int level);
  public void visit(IdentifierType n, Map mapa, int nivel, int level);
  public void visit(Block n, Map mapa, int nivel, int level);
  public void visit(If n, Map mapa, int nivel, int level);
  public void visit(While n, Map mapa, int nivel, int level);
  public void visit(Print n, Map mapa, int nivel, int level);
  public void visit(Assign n, Map mapa, int nivel, int level);
  public void visit(ArrayAssign n, Map mapa, int nivel, int level);
  public void visit(And n, Map mapa, int nivel, int level);
  public void visit(LessThan n, Map mapa, int nivel, int level);
  public void visit(Plus n, Map mapa, int nivel, int level);
  public void visit(Minus n, Map mapa, int nivel, int level);
  public void visit(Times n, Map mapa, int nivel, int level);
  public void visit(ArrayLookup n, Map mapa, int nivel, int level);
  public void visit(ArrayLength n, Map mapa, int nivel, int level);
  public void visit(Call n, Map mapa, int nivel, int level);
  public void visit(IntegerLiteral n, Map mapa, int nivel, int level);
  public void visit(True n, Map mapa, int nivel, int level);
  public void visit(False n, Map mapa, int nivel, int level);
  public void visit(IdentifierExp n, Map mapa, int nivel, int level);
  public void visit(This n, Map mapa, int nivel, int level);
  public void visit(NewArray n, Map mapa, int nivel, int level);
  public void visit(NewObject n, Map mapa, int nivel, int level);
  public void visit(Not n, Map mapa, int nivel, int level);
  public void visit(Identifier n, Map mapa, int nivel, int level);
}
