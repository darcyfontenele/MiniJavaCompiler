package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public abstract class Exp extends ASTNode {
    public Exp(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v, Map mapa, int level, int nivel);

    
    public abstract String accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt);
    

}
