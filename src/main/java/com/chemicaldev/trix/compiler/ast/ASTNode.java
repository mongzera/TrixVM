package com.chemicaldev.trix.compiler.ast;

import java.util.ArrayList;

public abstract class ASTNode implements ASTExtract{

    private ArrayList<ASTNode> child = new ArrayList<>();

    public void addChild(ASTNode child){
        if(this.acceptsNode(child.getType())) child.addChild(child);
        else throw new Error("Syntax Error!");
    }

    public abstract boolean acceptsNode(ASTTypes astType);
    public abstract ASTTypes getType();
}
