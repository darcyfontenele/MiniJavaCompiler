package AST.Visitor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import Tabelas.*;

import AST.*;

public class CodeVisitor implements SecVisitor {
  // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.
	
  // Exp e;
	private StringBuilder code;
	
	ClassDecl curClass = null;
    MethodDecl curMethod = null;
    MainClass mC = null;
    
    private int labelCounter;

	
  public boolean eCompativel(Map mapa, String classe, String extension)
	{
		return false;
	}
  
  public String getCode(){
	  return code.toString();	  
  }
    
  public int getVarOffsetInMethod(Identifier id){
      for(int i = 0; i < curMethod.fl.size(); i++){
          if(curMethod.fl.elementAt(i).i.s.equals(id.s))
              return (i+2)*(4);
      }
      for(int i = 0; i < curMethod.vl.size(); i++){
          if(curMethod.vl.elementAt(i).i.s.equals(id.s))
              return (i+1)*(-4);
      }
      return 0;
  }

  public int getVarOffsetInClass(Identifier id){
      for(int i = 0; i < curClass.vl.size(); i++){
          if(curClass.vl.elementAt(i).i.s.equals(id.s))
              return i*4;
      }
      return 0;
  }
	
  public void visit(Return n, Map mapa, int nivel, Classe cl, Metodo mt) {
    n.e.accept2(this, mapa, nivel+1, cl, mt);  
  }
  
  
  
  
  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n, Map mapa, int nivel, Classe cl, Metodo mt) {
	labelCounter = 0;
	code = new StringBuilder();
	n.m.accept2(this, mapa, nivel+1, null, null);
    for ( int i = 0; i < n.cl.size(); i++ ) { 
        n.cl.elementAt(i).accept2(this, mapa, nivel+1, null, null);
    }
    //return code.toString(); // vai retornar isso de alguma forma
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(MainClass n, Map mapa, int nivel, Classe cl, Metodo mt) {
	
	Classe c = (Classe)mapa.get(n.i1.s);
	//n.i1.accept2(this, mapa, nivel+1, c, null);
    mC = n;
    Metodo m = (Metodo)c.metodo.get("main");
    //n.i2.accept2(this, mapa, nivel+1, c, m);
    
    code.append("\t.text\n");
	code.append("\t.globl _asm_main\n\n");
    code.append("_asm_main:\n");
    code.append("\tpushl\t%ebp\n");
    code.append("\tmovl\t%esp,%ebp\n\n");
    n.s.accept2(this, mapa, nivel+1, c, m);   
    code.append("\tmovl\t%ebp,%esp\n");
    code.append("\tpopl\t%ebp\n");
    code.append("\tret\n\n");
    
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n, Map mapa, int nivel, Classe cl, Metodo mt) {
	Classe c = (Classe)mapa.get(n.i.s);
	/*
    n.i.accept2(this, mapa, nivel+1, c, null);
    
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept2(this, mapa, nivel+1, c, null);
        if ( i+1 < n.vl.size() ) { }
    }
    */
	  
	curClass  = n;
    curMethod = null;

    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept2(this, mapa, nivel+1, c, null);
    }
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclExtends n, Map mapa, int nivel, Classe cl, Metodo mt) {
		Classe c = (Classe)mapa.get(n.i.s);
		/*
	    n.i.accept2(this, mapa, nivel+1, c, null);
	    
	    n.j.accept2(this, mapa, nivel+1, c, null);
	    
	    for ( int i = 0; i < n.vl.size(); i++ ) {
	        n.vl.elementAt(i).accept2(this, mapa, nivel+1, c, null);
	        if ( i+1 < n.vl.size() ) { }
	    }*/
	  
	  	curClass  = n;
	    curMethod = null; 
	    
	    for ( int i = 0; i < n.ml.size(); i++ ) {
	        n.ml.elementAt(i).accept2(this, mapa, nivel+1, c, null);
	    }
  }

  // Type t;
  // Identifier i;
  public void visit(VarDecl n, Map mapa, int nivel, Classe cl, Metodo mt) {
    /*n.t.accept2(this, mapa, nivel+1, cl, mt);
    n.i.accept2(this, mapa, nivel+1, cl, mt);*/
	  return;
  }

  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public void visit(MethodDecl n, Map mapa, int nivel, Classe cl, Metodo mt) {
	Metodo m = (Metodo)cl.metodo.get(n.i.s);
	
    /*n.t.accept2(this, mapa, nivel+1, cl, m);
    n.i.accept2(this, mapa, nivel+1, cl, m);
    
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.elementAt(i).accept2(this, mapa, nivel+1, cl, m);
        if (i+1 < n.fl.size()) {  }
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept2(this, mapa, nivel+1, cl, m);
    }*/
    curMethod = n;
    code.append(curClass.i.s +"_" + n.i.s +":\n");
    code.append("\tpushl\t%ebp\n");
    code.append("\tmovl\t%esp,%ebp\n");
    code.append("\tsub\t$" + (n.vl.size())*4 + ",%esp\n\n");
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept2(this, mapa, nivel+1, cl, m);
        if ( i < n.sl.size() ) {  }
    }
    
    n.e.accept2(this, mapa, nivel+1, cl, m);
    code.append("\tmovl\t%ebp,%esp\n");
    code.append("\tpopl\t%ebp\n");
    code.append("\tret\n\n");
  
  }

  // Type t;
  // Identifier i;
  public void visit(Formal n, Map mapa, int nivel, Classe cl, Metodo mt) {
   /* n.t.accept2(this, mapa, nivel, cl, mt);
    n.i.accept2(this, mapa, nivel, cl, mt);*/
	  return;
  }

  public void visit(IntArrayType n, Map mapa, int nivel, Classe cl, Metodo mt) {
  }

  public void visit(BooleanType n, Map mapa, int nivel, Classe cl, Metodo mt) {

  }

  public void visit(IntegerType n, Map mapa, int nivel, Classe cl, Metodo mt) {

  }

  // String s;
  public void visit(IdentifierType n, Map mapa, int nivel, Classe cl, Metodo mt) {

  }

  // StatementList sl;
  public void visit(Block n, Map mapa, int nivel, Classe cl, Metodo mt) {
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept2(this, mapa, nivel, cl, mt);
    }
  }

  // Exp e;
  // Statement s1,s2;
  public void visit(If n, Map mapa, int nivel, Classe cl, Metodo mt) {
	int els = labelCounter++;
    int done = labelCounter++;

    n.e.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tjz\tL" + els + "\n");
    n.s1.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tjmp\tL" + done + "\nL" + els + ":\n");
    n.s2.accept2(this, mapa, nivel+1, cl, mt);
    code.append("L" + done + ":\n");
  }

  // Exp e;
  // Statement s;
  public void visit(While n, Map mapa, int nivel, Classe cl, Metodo mt) {
	int test = labelCounter++;
    int done = labelCounter++;
	
    code.append("L" + test + ":\n");
    n.e.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tjz\tL" + done + "\n");
    n.s.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tjmp\tL" + test + "\n");
    code.append("L" + done + ":\n");
  }

  // Exp e;
  public void visit(Print n, Map mapa, int nivel, Classe cl, Metodo mt) {
	n.e.accept2(this, mapa, nivel+1, cl, mt);
	code.append("\tpush\t%eax\n");
    code.append("\tcall\t_put\n");
    code.append("\tadd\t$4,%esp\n\n");
  }
  
  // Identifier i;
  // Exp e; // começa no aqui
  
  //getVarOffsetInClass
  
  //getVarOffsetInMethod
  
  public void visit(Assign n, Map mapa, int nivel, Classe cl, Metodo mt) {
    
    
    String identif = n.i.s;
    n.e.accept2(this, mapa, nivel+1, cl, mt);
    
    Classe c = (Classe)mapa.get(curClass.i.s);
        
    if(c.variavel != null && c.variavel.containsKey(n.i.s))
    {
    	code.append("\tmov\t" + (curMethod.fl.size()+2)*4 + "(%ebp),%edx\n");//lê this
        code.append("\tmov\t%eax," + getVarOffsetInClass(n.i) + "(%edx)\n");
    }else 
    	code.append("\tmov\t%eax," + getVarOffsetInMethod(n.i) + "(%ebp)\n");
    
        
  }

  // Identifier i;
  // Exp e1,e2;
  public void visit(ArrayAssign n, Map mapa, int nivel, Classe cl, Metodo mt) {
    n.i.accept2(this, mapa, nivel+2, cl, mt);
    code.append("\tpush\t%eax\n");    
	n.e1.accept2(this, mapa, nivel+2, cl, mt);
	code.append("\tpush\t%eax\n");
	n.e2.accept2(this, mapa, nivel+1, cl, mt);
	code.append("\tinc\t%eax\n");
    code.append("\timul\t$4,%eax\n");
    code.append("\tpop\t%edx\n");
    code.append("\tpop\t%ecx\n");
    code.append("\tmov\t%edx,%eax(%ecx)\n");
	code.append("\tpush\t%eax\n");
  
  }

  // Exp e1,e2;
  public String visit(And n, Map mapa, int nivel, Classe cl, Metodo mt) {
    n.e1.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tpush\t%eax\n");
    n.e2.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tpop\t%edx\n");
    code.append("\tand\t%edx,%eax\n");
	return (String)"boolean";
  }

  // Exp e1,e2;
  public String visit(LessThan n, Map mapa, int nivel, Classe cl, Metodo mt) {
	    n.e1.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpush\t%eax\n");
	    n.e2.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpop\t%edx\n");
        code.append("\tcmp\t%edx,%eax\n"); //compares left and right
        int less = labelCounter++;
        int done = labelCounter++;
        code.append("\tjl\tL" + less + "\n\tmov\t$0,%eax\n\tjmp\tL" + done + "\n");
        code.append("L" + less + ":\n\tmov\t$1,%eax\n");
        code.append("L" + done + ":\n");
		return (String)"boolean";
  }

  // Exp e1,e2;
  public String visit(Plus n, Map mapa, int nivel, Classe cl, Metodo mt) {
	    n.e1.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpush\t%eax\n");
	    n.e2.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpop\t%edx\n");
        code.append("\tadd\t%edx,%eax\n");
	   
		return (String)"int";
  }

  // Exp e1,e2;
  public String visit(Minus n, Map mapa, int nivel, Classe cl, Metodo mt) {
	    n.e1.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpush\t%eax\n");
	    n.e2.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpop\t%edx\n");
        code.append("\tsub\t%edx,%eax\n");
       	return (String)"int";
  }

  // Exp e1,e2;
  public String visit(Times n, Map mapa, int nivel, Classe cl, Metodo mt) {
	    n.e1.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpush\t%eax\n");
	    n.e2.accept2(this, mapa, nivel+1, cl, mt);
	    code.append("\tpop\t%edx\n");
        code.append("\timul\t%edx,%eax\n");
		return (String)"int";
  }

  // Exp e1,e2;
  public String visit(ArrayLookup n, Map mapa, int nivel, Classe cl, Metodo mt) {
	n.e1.accept2(this, mapa, nivel+1, cl, mt);
	code.append("\tpush\t%eax\n");
	n.e2.accept2(this, mapa, nivel+1, cl, mt);
	code.append("\tpop\t%edx\n");
    code.append("\tinc\t%eax\n");
    code.append("\timul\t$4,%eax\n");
    code.append("\tmov\t%eax(%edx),%eax\n");
	return (String)"int";	
  }

  // Exp e;
  public String visit(ArrayLength n, Map mapa, int nivel, Classe cl, Metodo mt) {
    n.e.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tmov\t$0(%edx),%eax\n");
	return (String)"int";
  }

  
  // Exp e;
  // Identifier i;
  // ExpList el;
  public String visit(Call n, Map mapa, int nivel, Classe cl, Metodo mt) {
	String expType = n.e.accept2(this, mapa, nivel+1, cl, mt);
	code.append("\tpush\t%eax\n");
	
	for(int i = n.el.size()-1; i >= 0; i--){
        n.el.elementAt(i).accept2(this, mapa, nivel+1, cl, mt);
        code.append("\tpush\t%eax\n");
    }
	
	Classe a = null;
	
	if(!expType.equals(cl.id))
	{
		if(mapa.containsKey(expType))
		{
			a = (Classe)mapa.get(expType);
		}
	}
	else
		a = cl;	
	
	n.i.accept2(this, mapa, nivel+1, cl, mt);
	
	Metodo b = null;
	if(a != null)
		if(a.metodo.containsKey(n.i.s))
		{
			b = (Metodo)a.metodo.get(n.i.s);
		}
		else
		{
			if(a.extendida != null)////
			{
				String classPai = a.extendida;
				Classe exten = null;
				do
				{
					exten = (Classe)mapa.get(classPai);
					
					if(exten.metodo.containsKey(n.i.s))
					{
						b = (Metodo)exten.metodo.get(n.i.s);
						break;
					}
					
					classPai = exten.extendida;
					
				}while(classPai != null);
			}
		}
	    code.append("\tcall\t" +a.id + "_" + b.id + "\n");
		if(n.el.size() > 0){
			code.append("\tadd\t$" + (n.el.size()+1)*4 + ",%esp\n");
			if(b != null)
			{
				return b.type;
			}
		}
		else
		{
			if(b != null)
				return b.type;
		}
		return "null";
    
  }

  // int i;
  public String visit(IntegerLiteral n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  code.append("\tmov\t$"+ n.i +",%eax\n");
	  return (String)"int";
  }

  public String visit(True n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  code.append("\tmov\t$" + 1 + ",%eax\n");
		return (String)"boolean";
  }

  public String visit(False n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  code.append("\tmov\t$" + 0 + ",%eax\n");
		return (String)"boolean";
  }

  // String s;
  public String visit(IdentifierExp n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  String identifType = null;
	  if(cl.variavel.containsKey(n.s))
	  {
		Variavel a = (Variavel)cl.variavel.get(n.s);
		identifType = new String(a.type);
	  }
	  else if(mt.arg.containsKey(n.s))
	  {
		  Variavel a = (Variavel)mt.arg.get(n.s);
		  identifType = new String(a.type);
	  }
	  else if(mt.variavel.containsKey(n.s))
	  {
		  Variavel a = (Variavel)mt.variavel.get(n.s);
		  identifType = new String(a.type);
	  }
	  else if(cl.extendida != null)////
	  {
		  String classPai = cl.extendida;
			Classe exten = null;
			do
			{
				exten = (Classe)mapa.get(classPai);
				if(exten != null)
				{
					if(exten.variavel.containsKey(n.s))
					{
						Variavel a = (Variavel)exten.variavel.get(n.s);
						identifType = a.type;
						break;
					}
					
					classPai = exten.extendida;
				}
				else
				{
					classPai = null;
					
				}
			}while(classPai != null);
	  }
	  
	  if(identifType == null)
	  {
		  identifType = "null";
	  }
		
	  return identifType;
	  
  }

  public String visit(This n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  code.append("\tmov\t" + (curMethod.fl.size()+2)*4 + "(%ebp),%eax\n");
	  return (String)cl.id;
  }

  // Exp e;
  public String visit(NewArray n, Map mapa, int nivel, Classe cl, Metodo mt) {
    n.e.accept2(this, mapa, nivel+1, cl, mt);
    code.append("\tinc\t%eax\n");
    code.append("\tpush\t%eax\n");
    code.append("\tcall\t_mjmalloc\n");
    code.append("\tadd\t$4,%esp\n\n");	
	return (String)"int []";
  }

  // Identifier i;
  public String visit(NewObject n, Map mapa, int nivel, Classe cl, Metodo mt) {
	Classe c = (Classe)mapa.get(n.i.s);
	int s = c.instanceSize();
	//n.i.accept2(this, mapa, nivel+1, cl, mt);
	code.append("\tpush\t$" + s + "\n");
    code.append("\tcall\t_mjmalloc\n");
    code.append("\tadd\t$4,%esp\n\n");
	return n.i.s;
  }

  // Exp e;
  public String visit(Not n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  n.e.accept2(this, mapa, nivel, cl, mt);
	  code.append("\txor\t$1,%eax\n");
  	  return (String)"boolean";
  }

  // String s;
  public void visit(Identifier n, Map mapa, int nivel, Classe cl, Metodo mt) {
	  Classe c;
	  if(curClass != null){ 
		  c = (Classe)mapa.get(curClass.i.s);
		  if(c.variavel.containsKey(n.s)){
	          code.append("\tmov\t" + (curMethod.fl.size()+2)*4 + "(%ebp),%edx\n"); //lê this
	          code.append("\tmov\t%eax," + getVarOffsetInClass(n) + "(%edx)\n");
	          //return table.getClass(curClass.i.s).getAttribute().type;
	      }
	      code.append("\tmov\t"+getVarOffsetInMethod(n)+"(%ebp),%eax\n");
	      //return table.getMethod(curMethod.id.name, curClass.id.name).getVar(n.name).type;
	  }
	  else c = (Classe)mapa.get(mC.i1.s);
	  
	 
  }
}
