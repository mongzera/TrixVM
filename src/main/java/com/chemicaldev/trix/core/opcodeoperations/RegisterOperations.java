package com.chemicaldev.trix.core.opcodeoperations;

import com.chemicaldev.trix.core.Operations;
import com.chemicaldev.trix.core.VMRegisters;
import com.chemicaldev.trix.core.context.ContextWindow;

public class RegisterOperations {
    public static void eval(byte OP_CODE, byte REGISTER_1, byte REGISTER_2, byte REGISTER_3, ContextWindow contextWindow) {
        int a, b, c, d, e; //temporary variables

        switch (OP_CODE){
            /** Registers */
            case Operations.LI:
                VMRegisters.loadToRegister(REGISTER_1, contextWindow.programMemory.read(++contextWindow.contextState.programCounter));
                break;
            case Operations.LW:
                a = contextWindow.programMemory.read(++contextWindow.contextState.programCounter); //Memory Address of the Word in HeapStorage
                VMRegisters.loadToRegister(REGISTER_1, contextWindow.heapMemory.read(a));
                break;
            case Operations.LWR:
                VMRegisters.loadToRegister(REGISTER_1, contextWindow.heapMemory.read(REGISTER_2));
                break;
            case Operations.LS:
                a = contextWindow.stackMemory.pop(); // Pop stack
                VMRegisters.loadToRegister(REGISTER_1, a);
                break;
        }

        contextWindow.incrementPC();
    }
}
