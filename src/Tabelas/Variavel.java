package Tabelas;

import java.util.Map;

public class Variavel {
	public String type;
	public String id;
	public int nivel;
	public int linha;
	
	public Variavel()
	{
		type = new String();
		id = new String();
		nivel = 0;
		linha = 0;
	}
	
	public void ident(int n)
	{
		for(int i = 0; i < nivel; i++)
		{
			System.out.print("\t");
		}
	}
	
	public void print(boolean var)
	{
		if(var)
		{
			ident(nivel);
			System.out.println("VARIABLE");
			ident(nivel);
			System.out.print(type + " " + id + " ");
			System.out.println("");
		}
		else
		{
			System.out.print(type + " " + id + " ");
		}
	}
	
	public void verifTypeExist(Map cl){
		if(!type.equals("int") && !type.equals("int []") && !type.equals("boolean") && !cl.containsKey(type) && !type.equals("String []"))
			System.out.println("O Tipo: ( " + type + " ) não foi definido, em " + (linha + 1));
	}
}
