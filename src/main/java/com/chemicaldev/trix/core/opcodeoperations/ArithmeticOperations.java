package com.chemicaldev.trix.core.opcodeoperations;

import com.chemicaldev.trix.core.Operations;
import com.chemicaldev.trix.core.context.ContextWindow;

public class ArithmeticOperations {
    public static void eval(byte OP_CODE, byte REGISTER_1, byte REGISTER_2, byte REGISTER_3, ContextWindow contextWindow) {

        int a, b, c, d, e; // Temporary Variables
        switch (OP_CODE){
            /** Arithmetic */
            case Operations.I_ADD:
                b = contextWindow.stackMemory.pop();
                a = contextWindow.stackMemory.pop();
                contextWindow.stackMemory.push(a + b);
                break;
            case Operations.I_SUB:
                b = contextWindow.stackMemory.pop();
                a = contextWindow.stackMemory.pop();
                contextWindow.stackMemory.push(a - b);
                break;
            case Operations.I_MUL:
                b = contextWindow.stackMemory.pop();
                a = contextWindow.stackMemory.pop();
                contextWindow.stackMemory.push(a * b);
                break;
            case Operations.I_DIV:
                b = contextWindow.stackMemory.pop();
                a = contextWindow.stackMemory.pop();
                contextWindow.stackMemory.push(a / b);
                break;
        }

        contextWindow.incrementPC();
    }
}
