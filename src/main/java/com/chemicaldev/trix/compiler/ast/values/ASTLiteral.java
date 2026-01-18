package com.chemicaldev.trix.compiler.ast.values;

import com.chemicaldev.trix.compiler.ast.ASTTypes;

public class ASTLiteral extends ASTValue{
    public String value;

    public ASTLiteral(String dataType, String value){
        this.dataType = dataType;
        this.value = value;
    }

    @Override
    public boolean acceptsNode(ASTTypes astType) {
        return false;
    }

    @Override
    public ASTTypes getType() {
        return ASTTypes.LITERAL;
    }

    @Override
    public String extract() {
        return value;
    }
}
