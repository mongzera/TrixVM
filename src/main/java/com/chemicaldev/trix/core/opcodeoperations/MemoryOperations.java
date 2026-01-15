package com.chemicaldev.trix.core.opcodeoperations;

import com.chemicaldev.trix.core.Operations;
import com.chemicaldev.trix.core.VMRegisters;
import com.chemicaldev.trix.core.context.ContextWindow;

public class MemoryOperations {
    public static void eval(byte OP_CODE, byte REGISTER_1, byte REGISTER_2, byte REGISTER_3, ContextWindow contextWindow) {

        int a, b, c, d, e; //temporary variables
        switch (OP_CODE){
            /** Memory Store */
            case Operations.SW:
                a = contextWindow.programMemory.read(++contextWindow.contextState.programCounter); //Memory Address where to store the word
                contextWindow.heapMemory.write(a, VMRegisters.getRegisterValue(REGISTER_1));
                break;
            case Operations.SS:
                a = contextWindow.stackMemory.pop(); //Memory Address where to store the word
                b = contextWindow.programMemory.read(++contextWindow.contextState.programCounter);
                contextWindow.heapMemory.write(b, a);
                break;
            case Operations.SSR:
                a = contextWindow.stackMemory.pop(); //value, top of stack
                contextWindow.heapMemory.write(REGISTER_1, a);
                break;
        }

        contextWindow.incrementPC();
    }
}
