package com.chemicaldev.trix.compiler.ast.values;

import com.chemicaldev.trix.compiler.TRXCCompiler;
import com.chemicaldev.trix.compiler.ast.ASTNode;

public abstract class ASTValue extends ASTNode {
    public String dataType;
    public int size;
    public String getMemAddr(){
        return String.valueOf(TRXCCompiler.memAlloc(size));
    }
}
