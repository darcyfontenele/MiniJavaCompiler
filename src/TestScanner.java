import java.io.File;
import java.io.*; 
import java.io.FileReader;

import Scanner.*;
import Parser.sym;
import Throwables.*;
import java_cup.runtime.Symbol;
import java.util.Scanner;

public class TestScanner {
    public static void main(String [] args) {
    	System.out.print("\nTrabalho de compiladores Parte I: Scanner\n\nEquipe: Felipe Maia e Davi Teles\n"); 
    	Boolean flag = true;
    	String n = null;
    	FileReader inFile = null;
    	File outFile = null; 
    	scanner s = null;
    	FileOutputStream fos = null;
    	Symbol t = null;
    	try {
            // create a scanner on the input file
        	System.out.print("\nDigite o nome do arquivo a ser analizado(esse arquivo deve ss encontrar na mesma pasta do .jar):\n"); 
        	
    		Scanner in = new Scanner(System.in);
    		
    		n = in.next();
            inFile = new FileReader(n);
            s = new scanner(inFile);
            outFile = new File(n + ".out");
            fos = new FileOutputStream(outFile);
            t = s.next_token();
            System.out.print("\n1\n");
            flag = t.sym != sym.EOF;
    	} catch (CompilerException e) {
    		System.err.println(e.getMessage());
    		return;
    		
        } catch (Exception e) {
        	System.err.println("Ocorreu um erro: " +  e.toString());
        	e.printStackTrace();
        	return;
        }
        	
    	Boolean error = false;
        while (flag){ 
        	try {
        		if(error){
        			t = s.next_token(); 
        			error = false;
        		}
            	 
                // print each token that we scan
                String tex = s.symbolToString(t) + " ";
                System.out.print(tex);
                fos.write(tex.getBytes()); 
                t = s.next_token(); 
                flag = t.sym != sym.EOF;
            } catch (CompilerException e) {
        		error = true;
        		String tex = "<ERROR: " +  e.getMessage()+ " >";
        		System.err.println(tex);
        		
        		try{ fos.write(tex.getBytes());}  catch (Exception ex) {}
            }  catch (Exception e) {
            	error = true;
            	String tex = "<ERROR: " +  e.toString() + " >";
             	System.err.println(tex);
             	try{ fos.write(tex.getBytes());}  catch (Exception ex) {}
             	e.printStackTrace();
             }
        }
        System.out.print("\nAnalize lexica realizada com sucesso!!"); 
       
       
   }
}


