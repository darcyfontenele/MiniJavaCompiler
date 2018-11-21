package Tabelas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Classe {
	public String id;
	public String extendida;
	public Map variavel;
	public Map metodo;
	public int nivel;
	public int linha;
	
	public Classe(boolean main)
	{
		id = new String();
		metodo = new LinkedHashMap();
		extendida = null;
		nivel = 0;
		linha = 0;
		
		if(main)
		{
			variavel = null;
		}
		else
		{
			variavel = new LinkedHashMap();			
		}
	}
	public int instanceSize(){
        return variavel.values().size()*4;
    }
	public void ident(int n)
	{
		for(int i = 0; i < nivel; i++)
		{
			System.out.print("\t");
		}
	}
	public void print()
	{
		ident(nivel);
		System.out.println("CLASS");
		ident(nivel);
		System.out.print(id + " ");
		if(extendida != null)
			System.out.println("EXTEND " + extendida + " ");
		System.out.println("----------");
		if(variavel != null)
		{
			Set ss = variavel.entrySet();
			Iterator ii = ss.iterator();
			while(ii.hasNext())
			{
				Map.Entry entrada = (Map.Entry)ii.next();
				Variavel a = (Variavel)entrada.getValue();
				a.nivel = nivel + 1;
				a.print(true);
			}
		}
		if(metodo != null)
		{
			Set ss = metodo.entrySet();
			Iterator ii = ss.iterator();
			while(ii.hasNext())
			{
				Map.Entry entrada = (Map.Entry)ii.next();
				Metodo a = (Metodo)entrada.getValue();
				a.nivel = nivel + 1;
				a.print();
				System.out.println("----------");
			}
		}
	}
	
	public void verificar(Map classMap)
	{
		/*Verificar Repetiçoes de Atributos e Metodos nas Super Classes*/
		if(extendida != null)
		{
			String classPai = extendida;
			Classe exten = null;
			do
			{
				if(classMap.containsKey(classPai))
				{
					exten = (Classe)classMap.get(classPai);
					
					Set ss = variavel.entrySet();
					Iterator ii = ss.iterator();
					
					while(ii.hasNext())
					{
						Map.Entry entrada = (Map.Entry)ii.next();
						Variavel a = (Variavel)entrada.getValue();
						
						if(exten.variavel != null)
							if(exten.variavel.containsKey(a.id))
							{
								System.out.println("A variavel " + a.type + " " + a.id + " foi redefenida(sobreposta) na Super-Classe: " + exten.id + ".");
								System.out.println("Em " + (a.linha + 1));
							}
					}
					
					ss = metodo.entrySet();
					ii = ss.iterator();
					
					while(ii.hasNext())
					{
						Map.Entry entrada = (Map.Entry)ii.next();
						Metodo a = (Metodo)entrada.getValue();
						
						if(exten.metodo.containsKey(a.id))
						{
							boolean erroDeArgumento = false;
							
							Metodo b = (Metodo)exten.metodo.get(a.id);
							
							Set s1 = b.arg.entrySet();
							Set s2 = a.arg.entrySet();
				            Iterator i1 = s1.iterator();
				            Iterator i2 = s2.iterator();
				            
				            if(b.arg.size() < a.arg.size())
				            {
				            	erroDeArgumento = true;
				            }
							else if(b.arg.size() > a.arg.size())
							{
								erroDeArgumento = true;
							}
							else
							{
								while(i2.hasNext())
								{
									Map.Entry entrada1 = (Map.Entry)i1.next();
					            	Variavel vari1 = (Variavel)entrada1.getValue();
					            	
					            	Map.Entry entrada2 = (Map.Entry)i2.next();
					            	Variavel vari2 = (Variavel)entrada2.getValue();
					            	
					            	if(!eCompativel(classMap, vari1.type, vari2.type))
					            		erroDeArgumento = true;
								}	
							}
							
				            if(erroDeArgumento)
				            {
							    System.out.println("Erro de Sobrecarga: ");
							    System.out.print("\t Metodo: ( " + a.type + " " + a.id + "() ) ");
							    System.out.print("da super-Classe: " + exten.id + "::" + b.type + " " + b.id + "(");
				            	
				            	ss = b.arg.entrySet();
					            ii = ss.iterator();
					            
				            	while(ii.hasNext())
								{						
					            	entrada = (Map.Entry)ii.next();
					            	Variavel vari = (Variavel)entrada.getValue();
					            	System.out.print(vari.type);
				            		if(ii.hasNext())
					            		System.out.print(", ");
								}
				            	System.out.print(") ");
				            
				            	System.out.print("na sub-Classe:  "+ id + "::" + a.type + " " + a.id +"(");
			            	
			            		ss = a.arg.entrySet();
				            	ii = ss.iterator();
				            
				            	while(ii.hasNext())
				            	{
				            		entrada = (Map.Entry)ii.next();
					            	Variavel vari = (Variavel)entrada.getValue();
					            	System.out.print(vari.type);
					            	if(ii.hasNext())
					            		System.out.print(", ");
				            	}
				            	System.out.print(") "); 
				            	System.out.println("em " + (a.linha + 1));
				            }
						}
					}

					classPai = exten.extendida;
				}
				else
				{
					if(extendida.equals(classPai))
					    System.out.println("Erro de Herança na Super-Classe ( " + classPai + ") não foi encontrada, em " + (linha + 1));
					break;
				}
			}while(classPai != null);	
		}
		
		if(!metodo.isEmpty())
		{
			Set ss = metodo.entrySet();
			Iterator ii = ss.iterator();
			while(ii.hasNext())
			{
				Map.Entry entrada = (Map.Entry)ii.next();
				Metodo a = (Metodo)entrada.getValue();
				a.repeatVerifVar(classMap, id);
			}
		}
	}
	
	public void verifTypeExist(Map cl){
		if(variavel != null)
		{
			Set ss = variavel.entrySet();
			Iterator ii = ss.iterator();
			while(ii.hasNext())
			{
				Map.Entry entrada = (Map.Entry)ii.next();
				Variavel a = (Variavel)entrada.getValue();
				a.verifTypeExist(cl);
			}
		}
		if(metodo != null)
		{
			Set ss = metodo.entrySet();
			Iterator ii = ss.iterator();
			while(ii.hasNext())
			{
				Map.Entry entrada = (Map.Entry)ii.next();
				Metodo a = (Metodo)entrada.getValue();
				a.verifTypeExist(cl);
			}
		}
	}
	
	public boolean eCompativel(Map hm, String classe, String extension)
	{
		if(classe.equals("int") || extension.equals("int"))
		{
			if(classe.equals(extension))
				return true;
		}
		
		else if(classe.equals("int []") || extension.equals("int []"))
		{
			if(classe.equals(extension))
				return true;
		}
		
		else if(classe.equals("boolean") || extension.equals("boolean"))
		{
			if(classe.equals(extension))
				return true;
		}
		
		else if(classe.equals("null") || extension.equals("null"))
		{
				return false;
		}
		
		else
		{
			if(extension.equals(classe))
			{
				return true;
			}
			else
			{
				String extenType = "null";
				
				if(hm.containsKey(classe))
				{
					Classe myClass = (Classe)hm.get(classe);
										
					if(myClass.extendida != null)
					{
						String classPai = myClass.extendida;
						Classe exten = null;
						do
						{
							if(hm.containsKey(classPai))
							{
								exten = (Classe)hm.get(classPai);
								
								if(exten.id.equals(extension))
								{
									extenType = extension;
									break;
								}
								
								classPai = exten.extendida;
							}
							else
								break;
							
						}while(classPai != null);
					}	
				}
				if(extension.equals(extenType))
				    return true;				
			}
		}
		return false;
	}
}


