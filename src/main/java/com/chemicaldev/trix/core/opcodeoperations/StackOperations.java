package com.chemicaldev.trix.core.opcodeoperations;

import com.chemicaldev.trix.core.Operations;
import com.chemicaldev.trix.core.VMRegisters;
import com.chemicaldev.trix.core.VMState;
import com.chemicaldev.trix.core.context.ContextWindow;

public class StackOperations{

    public static void eval(byte OP_CODE, byte REGISTER_1, byte REGISTER_2, byte REGISTER_3, ContextWindow contextWindow) {

        int a, b, c, d, e; //temporary variables
        switch (OP_CODE) {
            case Operations.PUSH:
                contextWindow.stackMemory.push(contextWindow.programMemory.read(++contextWindow.contextState.programCounter));
                break;
            case Operations.POP:
                a = contextWindow.stackMemory.pop();
                System.out.println(a);
                break;
            case Operations.PEEK:
                System.out.println(contextWindow.stackMemory.peek());
                break;
            case Operations.DUP:
                a = contextWindow.stackMemory.pop();
                contextWindow.stackMemory.push(a);
                contextWindow.stackMemory.push(a);
                break;
            case Operations.BREAK:
                if (contextWindow.callStack.getPointer() >= 0) {
                    contextWindow.contextState.programCounter = contextWindow.callStack.pop();

                    return;
                }

                contextWindow.terminate(); // Terminate program

                break;
            case Operations.PUSHR:
                contextWindow.stackMemory.push(VMRegisters.getRegisterValue(REGISTER_1));
                break;
        }

        contextWindow.incrementPC();
    }
}
