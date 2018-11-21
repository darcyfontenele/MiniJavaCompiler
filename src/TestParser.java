import java.io.*; 

import Scanner.*;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import Tabelas.Classe;
import Tabelas.Metodo;
import Throwables.*;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.util.*;

public class TestParser {
    public static void main(String [] args){
        try {
        	Map cl = new HashMap();
    		Map pro = new LinkedHashMap(cl);
    		System.out.println("*--------------------------------*");
        	System.out.println("| Trabalho Final de Compiladores |");
        	System.out.println("| Equipe: Darcy Fontenele        |");
        	System.out.println("|         Levy Gurgel            |");
        	System.out.println("*--------------------------------*\n");
        	File dir = new File(".");
        	File[] archiveList = dir.listFiles();
        	for(File f : archiveList){
        		if(f.getName().contains(".java")){
        			System.out.println(f.getName());
        		}
        	}
        	System.out.println("Digite o nome de um arquivo .java listado acima:");
        	System.out.println("Obs: O .java deve está na pasta do compilador!");
        	Scanner in = new Scanner(System.in);
        	String n = in.next();
        	FileReader inFile = new FileReader(n);     	
        	System.out.println("Erros Léxicos e Sintáticos:\n");       	
        	scanner s = new scanner(inFile);
            parser p = new parser(s);
            Symbol root;
            root = p.parse();            
            System.out.println("Erros Semânticos:\n");          
            Program program = (Program)root.value;
            program.accept(new PrettyPrintVisitor(), pro, 0, 0);            
            Set ss = pro.entrySet();
            Iterator ii = ss.iterator();
            while(ii.hasNext()){
            	Map.Entry entrada = (Map.Entry)ii.next();
            	Classe a = (Classe)entrada.getValue();
            	a.verificar(pro);
            	a.verifTypeExist(pro);
            }          
            inFile = new FileReader(n);
            scanner s2 = new scanner(inFile);
            parser p2 = new parser(s2);
            Symbol root2;
            root2 = p2.parse();                        
            program = (Program)root2.value;
            program.accept2(new PrettySecVisitor(), pro, 0, null, null);            
            System.out.println("Tabelas Geradas:\n");            
            ss = pro.entrySet();
            ii = ss.iterator();
            while(ii.hasNext()){
            	Map.Entry entrada = (Map.Entry)ii.next();
            	Classe a = (Classe)entrada.getValue();
            	a.print();
            }                                  
            System.out.println("Analize Concluída com Sucesso!\n"); 
            System.out.println("Gerando Código Assembly\n");            
            CodeVisitor cV = new CodeVisitor();
            FileWriter writer = new FileWriter(new File(n+".s"),true);  
            PrintWriter dotS = new PrintWriter(writer);           
            program.accept2(cV, pro, 0, null, null);
            String cd = cV.getCode();
            dotS.print(cd);            
            writer.close();
            dotS.close();          
            System.out.println("Codigo Gerado com Sucesso!\n");    
        } catch (CompilerException e) {
            // an error in the user's arguments or input, or some
            // other kind of error that we've already handled in the
            // appropriate way (not a bug that the error got here)
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // yuck: some kind of error in the compiler implementation
            // that we're not expecting (a bug!)
            System.err.println("Unexpected internal compiler error: " + 
                               e.toString());
            // print out a stack dump
            e.printStackTrace();
        }
    }
}