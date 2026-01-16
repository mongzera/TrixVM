package com.chemicaldev.trix.core.opcodeoperations;

import com.chemicaldev.trix.core.Operations;
import com.chemicaldev.trix.core.context.ContextWindow;

public class BranchingOperations {
    public static void eval(byte OP_CODE, byte REGISTER_1, byte REGISTER_2, byte REGISTER_3, ContextWindow contextWindow) {

        int a, b, c, d, e; //temporary variables
        switch (OP_CODE){
            /** Jump Mechanisms USE RETURN INSTEAD OF BREAK*/
            case Operations.JMP:
                a = contextWindow.programMemory.read(++contextWindow.contextState.programCounter);
                contextWindow.contextState.programCounter = a;
                return;
            case Operations.JZ:
                a = contextWindow.programMemory.read(++contextWindow.contextState.programCounter);
                if(contextWindow.stackMemory.peek() == 0) contextWindow.contextState.programCounter = a;
                return;
            case Operations.JNZ:
                a = contextWindow.programMemory.read(++contextWindow.contextState.programCounter);
                if(contextWindow.stackMemory.peek() != 0) contextWindow.contextState.programCounter = a;
                return;
            case Operations.CALL:
                a = contextWindow.programMemory.read(++contextWindow.contextState.programCounter);
                contextWindow.callStack.push(++contextWindow.contextState.programCounter);
                contextWindow.contextState.programCounter = a;
                return;
        }



    }
}
