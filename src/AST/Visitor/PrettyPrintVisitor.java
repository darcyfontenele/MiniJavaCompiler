package AST.Visitor;
import java.util.Map;
import Tabelas.*;

import AST.*;

public class PrettyPrintVisitor implements Visitor {
  // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.
	
  // Exp e;
  public void visit(Return n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel);  
  }
  
  
  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n, Map mapa, int nivel, int level) {
	n.m.accept(this, mapa, level, nivel+1);
    for ( int i = 0; i < n.cl.size(); i++ ) { 
        n.cl.elementAt(i).accept(this, mapa, level, nivel+1);
    }
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(MainClass n, Map mapa, int nivel, int level) {
	Classe a = new Classe(true);
	a.linha = n.i1.line_number;
	if(mapa.containsKey(n.i1.toString()))
	{
		System.out.println("Classe definida duplamente: ( class " + n.i1.toString() + " ) em " + (n.line_number + 1));
		while(mapa.containsKey(a.id))
			a.id = a.id + "@";
	}
	a.id = n.i1.toString();
    n.i1.accept(this, mapa, level, nivel+1);
    
    Variavel v = new Variavel();
    v.type = (String)"String []";
    v.id = n.i2.toString();
    v.linha = n.i2.line_number;
    
    n.i2.accept(this, mapa, level, nivel+1);
    
    Metodo m = new Metodo();
    m.type = (String)"void";
    m.id = (String)"main";
    m.arg.put(v.id, v);
    m.linha = n.i2.line_number;
    
    n.s.accept(this, m.variavel, m.nivel+1, nivel+1);
    
    mapa.put(a.id, a);
    
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n, Map mapa, int nivel, int level) {
	Classe a = new Classe(false);
	a.linha = n.line_number;
	if(mapa.containsKey(n.i.toString()))
	{
		System.out.println("Classe definida duplamente: ( class " + n.i.toString() + " ) em " + (n.line_number + 1));
		while(mapa.containsKey(a.id))
			a.id = a.id + "@";
	}	
	a.id = n.i.toString();
    n.i.accept(this, mapa, level, nivel+1);
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this, a.variavel, a.nivel+1, nivel+1);
        if ( i+1 < n.vl.size() ) { }
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this, a.metodo, a.nivel+1, nivel+1);
    }
    mapa.put(a.id, a);
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclExtends n, Map mapa, int nivel, int level) {
	Classe a = new Classe(false);
	a.linha = n.line_number;
	if(mapa.containsKey(n.i.toString()))
	{
		System.out.println("Classe definida duplamente: ( class " + n.i.toString() + " ) em " + (n.line_number + 1));
		while(mapa.containsKey(a.id))
			a.id = a.id + "@";
	}	
	a.id = n.i.toString();
    n.i.accept(this, mapa, level, nivel+1);
    
    a.extendida = new String();
    a.extendida = n.j.s.toString();
    n.j.accept(this, mapa, level, nivel+1);
    
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this, a.variavel, a.nivel+1, nivel+1);
        if ( i+1 < n.vl.size() ) { }
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this, a.metodo, a.nivel+1, nivel+1);
    }
    mapa.put(a.id, a);
    
  }

  // Type t;
  // Identifier i;
  public void visit(VarDecl n, Map mapa, int nivel, int level) {
	Variavel a = new Variavel();
	a.type = n.t.toString();
	a.id = n.i.toString();
	a.linha = n.line_number;
	if(mapa.containsKey(a.id))
	{
		System.out.println("Variável duplamente definida: ( " + a.type + " " + a.id + " ) em " + (n.line_number + 1));
		while(mapa.containsKey(a.id))
			a.id = a.id + "@";
		
	}
	mapa.put(a.id, a);
    n.t.accept(this, mapa, level, nivel+1);
    n.i.accept(this, mapa, level, nivel+1);
  }

  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public void visit(MethodDecl n, Map mapa, int nivel, int level) {
	Metodo a = new Metodo();
	a.type = n.t.toString();
	a.id = n.i.toString();
	a.linha = n.line_number;
	
	if(mapa.containsKey(a.id))
	{
		System.out.println("Método duplamente definido: ( " + a.type + " " + a.id + "() ) em " + (n.line_number + 1));
		while(mapa.containsKey(a.id))
			a.id = a.id + "@";
	}
	
    n.t.accept(this, mapa, level, nivel+1);
    n.i.accept(this, mapa, level, nivel+1);
    
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.elementAt(i).accept(this, a.arg, level, nivel);
        if (i+1 < n.fl.size()) {  }
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this, a.variavel, a.nivel+1, nivel);
    }
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this, a.variavel, a.nivel+1, nivel);
        if ( i < n.sl.size() ) {  }
    }
    n.e.accept(this, mapa, level, nivel+1);
    mapa.put(a.id, a);
  }

  // Type t;
  // Identifier i;
  public void visit(Formal n, Map mapa, int nivel, int level) {
	Variavel a = new Variavel();
	a.type = n.t.toString();
	a.id = n.i.toString();
	a.linha = n.line_number;
	if(mapa.containsKey(a.id))
	{
		System.out.println("Argumento duplamente definido: ( " + a.type + a.id+" ) em " + (n.line_number + 1));
		while(mapa.containsKey(a.id))
			a.id = a.id + "@";
	}
	mapa.put(a.id, a);
    n.t.accept(this, mapa, level, nivel+1);
    n.i.accept(this, mapa, level, nivel+1);
  }

  public void visit(IntArrayType n, Map mapa, int nivel, int level) {
  }

  public void visit(BooleanType n, Map mapa, int nivel, int level) {

  }

  public void visit(IntegerType n, Map mapa, int nivel, int level) {

  }

  // String s;
  public void visit(IdentifierType n, Map mapa, int nivel, int level) {

  }

  // StatementList sl;
  public void visit(Block n, Map mapa, int nivel, int level) {
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this, mapa, level, nivel);
    }
  }

  // Exp e;
  // Statement s1,s2;
  public void visit(If n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel+1);
    n.s1.accept(this, mapa, level, nivel+1);
    n.s2.accept(this, mapa, level, nivel+1);
  }

  // Exp e;
  // Statement s;
  public void visit(While n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel+1);
    n.s.accept(this, mapa, level, nivel+1);
  }

  // Exp e;
  public void visit(Print n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel+1);
  }
  
  // Identifier i;
  // Exp e;
  public void visit(Assign n, Map mapa, int nivel, int level) {
    n.i.accept(this, mapa, level, nivel+1);
    n.e.accept(this, mapa, level, nivel+1);
  }

  // Identifier i;
  // Exp e1,e2;
  public void visit(ArrayAssign n, Map mapa, int nivel, int level) {
    n.i.accept(this, mapa, level, nivel+2);
    n.e1.accept(this, mapa, level, nivel+2);
    n.e2.accept(this, mapa, level, nivel+1);
  }

  // Exp e1,e2;
  public void visit(And n, Map mapa, int nivel, int level) {
    n.e1.accept(this, mapa, level, nivel);
    n.e2.accept(this, mapa, level, nivel);
  }

  // Exp e1,e2;
  public void visit(LessThan n, Map mapa, int nivel, int level) {
    n.e1.accept(this, mapa, level, nivel+1);
    n.e2.accept(this, mapa, level, nivel+1);
  }

  // Exp e1,e2;
  public void visit(Plus n, Map mapa, int nivel, int level) {
    n.e1.accept(this, mapa, level, nivel+1);
    n.e2.accept(this, mapa, level, nivel+1);
  }

  // Exp e1,e2;
  public void visit(Minus n, Map mapa, int nivel, int level) {
    n.e1.accept(this, mapa, level, nivel+1);
    n.e2.accept(this, mapa, level, nivel+1);
  }

  // Exp e1,e2;
  public void visit(Times n, Map mapa, int nivel, int level) {
    n.e1.accept(this, mapa, level, nivel+1);
    n.e2.accept(this, mapa, level, nivel+1);
  }

  // Exp e1,e2;
  public void visit(ArrayLookup n, Map mapa, int nivel, int level) {
    n.e1.accept(this, mapa, level, nivel+1);
    n.e2.accept(this, mapa, level, nivel+1);
  }

  // Exp e;
  public void visit(ArrayLength n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel+1);
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  public void visit(Call n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel+1);
    n.i.accept(this, mapa, level, nivel+1);
    for ( int i = 0; i < n.el.size(); i++ ) {
        n.el.elementAt(i).accept(this, mapa, level, nivel+1);
        if ( i+1 < n.el.size() ) {  }
    }
  }

  // int i;
  public void visit(IntegerLiteral n, Map mapa, int nivel, int level) {

  }

  public void visit(True n, Map mapa, int nivel, int level) {

  }

  public void visit(False n, Map mapa, int nivel, int level) {

  }

  // String s;
  public void visit(IdentifierExp n, Map mapa, int nivel, int level) {

  }

  public void visit(This n, Map mapa, int nivel, int level) {

  }

  // Exp e;
  public void visit(NewArray n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel+1);
  }

  // Identifier i;
  public void visit(NewObject n, Map mapa, int nivel, int level) {
	  n.i.accept(this, mapa, level, nivel+1);
  }

  // Exp e;
  public void visit(Not n, Map mapa, int nivel, int level) {
    n.e.accept(this, mapa, level, nivel);
  }

  // String s;
  public void visit(Identifier n, Map mapa, int nivel, int level) {

  }
}
