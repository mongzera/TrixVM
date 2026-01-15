package com.chemicaldev.trix.core;

import com.chemicaldev.trix.core.context.ContextWindow;
import com.chemicaldev.trix.core.opcodeoperations.*;

public class VirtualMachineEngine {

    public static final int N_CONTEXT_WINDOW = 2;
    public int activeContexts = 0;
    public ContextWindow[] contextWindows = new ContextWindow[N_CONTEXT_WINDOW];
    public ContextWindow currentContextWindow = null;

    public void prepare(){
        VMState.CPU_ON = true;
    }

    public void createContextWindow(int[] instructions) {
        if (activeContexts >= N_CONTEXT_WINDOW) {
            //print error here that we cannot make more contextWindow
            return;
        }
        contextWindows[activeContexts++] = new ContextWindow(instructions);
    }

    /** The Main Loop */
    public void operate(){
        /** Initializations */
        int currentContext = 0;

        while(VMState.CPU_ON){

            if(VMState.SLOW_DOWN){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            currentContextWindow = contextWindows[currentContext];

            //load registers from contextWindow
            VMRegisters.loadFromContextWindow(currentContextWindow);

            int programCounter = currentContextWindow.contextState.programCounter;

            // Check first if there is loaded instructions
            if(!currentContextWindow.contextState.HAS_LOADED_INSTRUCTIONS) return;

            int instruction = currentContextWindow.programMemory.read(programCounter);

            if(VMState.DBG_ON) System.out.println("0x" + Integer.toHexString(instruction) + "| PC: " + programCounter);

            evalInstruction(instruction, currentContextWindow);

            if(VMState.DBG_ON) System.out.printf("[R0=%s, R1=%s, R2=%s, R3=%s]%n", VMRegisters.getRegisterValue(VMRegisters.R0), VMRegisters.getRegisterValue(VMRegisters.R1), VMRegisters.getRegisterValue(VMRegisters.R2), VMRegisters.getRegisterValue(VMRegisters.R3));
            if(VMState.MEM_DBG_ON) currentContextWindow.stackMemory.print();

            currentContext = (++currentContext) % activeContexts;

            VMRegisters.saveToContextWindow(currentContextWindow);
        }
    }

    private void evalInstruction(int instruction, ContextWindow currentContext){
        byte OP_CODE = (byte)(instruction >> 24);
        byte REGISTER_1 = (byte)((instruction >> 16) & 0x00FF);
        byte REGISTER_2 = (byte)((instruction >> 8) & 0x0000FF);
        byte REGISTER_3 = (byte)((instruction) & 0x000000FF);
        byte OP_CODE_TYPE = (byte)(OP_CODE >> 4);

        switch (OP_CODE_TYPE){
            case Operations.STACK_OPCODE -> StackOperations.eval(OP_CODE, REGISTER_1, REGISTER_2, REGISTER_3, currentContext);
            case Operations.ARITHMETIC_OPCODE -> ArithmeticOperations.eval(OP_CODE, REGISTER_1, REGISTER_2, REGISTER_3, currentContext);
            case Operations.REGISTER_OPCODE -> RegisterOperations.eval(OP_CODE, REGISTER_1, REGISTER_2, REGISTER_3, currentContext);
            case Operations.MEMORY_OPCODE -> MemoryOperations.eval(OP_CODE, REGISTER_1, REGISTER_2, REGISTER_3, currentContext);
            case Operations.BRANCHING_OPCODE -> BranchingOperations.eval(OP_CODE, REGISTER_1, REGISTER_2, REGISTER_3, currentContext);
            case Operations.SYSCALL_OPCODE -> SystemCallOperations.eval(OP_CODE, REGISTER_1, REGISTER_2, REGISTER_3, currentContext);
        }
    }

}
