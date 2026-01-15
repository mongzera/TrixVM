package com.chemicaldev.trix.core.opcodeoperations;

import com.chemicaldev.trix.core.VMRegisters;
import com.chemicaldev.trix.core.context.ContextWindow;

import java.util.Scanner;

public class SystemCallOperations {
    private static final Scanner lineRead = new Scanner(System.in);
    public static void eval(byte OP_CODE, byte REGISTER_1, byte REGISTER_2, byte REGISTER_3, ContextWindow contextWindow) {

        int i_a, i_b;
        float f_a, f_b;

        switch (VMRegisters.getRegisterValue(VMRegisters.R0)){
            case 0:
                //end program
                break;
            case 1:
                //read integer
                i_a = lineRead.nextInt();
                contextWindow.stackMemory.push(i_a);
                break;
            case 2:
                //print integer
                i_a = contextWindow.stackMemory.pop();
                System.out.println(i_a);
                break;
            case 3:
                //read float
                f_a = lineRead.nextFloat();
                contextWindow.stackMemory.push(Float.floatToIntBits(f_a));
                break;
            case 4:
                //print float
                f_a = Float.intBitsToFloat(contextWindow.stackMemory.pop());
                System.out.println(f_a);
                break;
        }

        contextWindow.incrementPC();

    }
}
