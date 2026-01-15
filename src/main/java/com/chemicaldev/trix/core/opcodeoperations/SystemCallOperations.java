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
            case 5:
                //read char[]
                break;
            case 6:
                //print char[]
                i_a = contextWindow.stackMemory.pop();

                boolean terminated = false;
                StringBuilder str = new StringBuilder();
                int offset = 0;

                while (!terminated) {
                    int word = contextWindow.heapMemory.read(i_a + offset++);

                    for (int i = 0; i < 4; i++) {
                        int ch = (word >> ((3-i) * 8)) & 0xFF; // extract byte

                        if (ch == 0) {
                            terminated = true;
                            break;
                        }

                        str.append((char) ch);
                    }
                }

                System.out.print(str);
                break;

        }

        contextWindow.incrementPC();

    }
}
