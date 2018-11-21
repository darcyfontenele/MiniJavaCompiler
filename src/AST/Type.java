package AST;
import java.util.Map;

import Tabelas.Classe;
import Tabelas.Metodo;

import AST.Visitor.SecVisitor;
import AST.Visitor.Visitor;

public abstract class Type extends ASTNode {
    public Type(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v, Map mapa, int level, int nivel);
    
    public abstract void accept2(SecVisitor v, Map mapa, int nivel, Classe cl, Metodo mt);

}
