package com.chemicaldev.trix.compiler.ast.declarations;

import com.chemicaldev.trix.compiler.ast.ASTNode;
import com.chemicaldev.trix.compiler.ast.ASTTypes;
import com.chemicaldev.trix.compiler.ast.values.ASTValue;

public class ASTVarDecl extends ASTValue {
    public String name;
    public ASTValue value;

    public ASTVarDecl(String dataType, String name, ASTValue value){
        this.dataType = dataType;
        this.name = name;
        this.value = value;
    }

    @Override
    public String extract() {
        String outTXB;

        switch (dataType){
            case "int":
                outTXB = """
                         i_push
                         """;
        }

        return "";
    }

    @Override
    public boolean acceptsNode(ASTTypes astType) {
        return false;
    }

    @Override
    public ASTTypes getType() {
        return ASTTypes.VAR_DECL;
    }
}
