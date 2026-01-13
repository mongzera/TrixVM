package com.chemicaldev.trix.core;

import com.chemicaldev.trix.memory.MemoryChunk;
import com.chemicaldev.trix.memory.StackMemory;

public class VirtualMachineEngine {
    //Initialize Memories
    private int programCounter = 0;

    MemoryChunk programMemory = new MemoryChunk(1024);
    MemoryChunk heapMemory = new MemoryChunk(1024);
    StackMemory stackMemory = new StackMemory();

    public void prepare(int[] instructions){
        VMState.CPU_ON = true;
        loadInstruction(instructions);
    }

    /** The Main Loop */
    public void operate(){
        /** Initializations */

        while(VMState.CPU_ON){

            // Check first if there is loaded instructions
            if(!VMState.HAS_LOADED_INSTRUCTIONS) return;

            evalInstruction(programMemory.read(programCounter));
        }
    }

    private void evalInstruction(int instruction){
        byte OP_CODE = (byte)(instruction >> 24);
        byte REGISTER = (byte)((instruction >> 16) & 0x00FF);

        //System.out.println(String.format("Instruction: 0x%s", Integer.toHexString(instruction)));
        if(OP_CODE == 0 || OP_CODE == Operations.BREAK) {
            VMState.CPU_ON = false; // Turn off VM
            if(stackMemory.getPointer() >= 0) System.out.println(stackMemory.peek());

            if(VMState.DBG_ON) heapMemory.print("HEAP MEMORY");
        }

        //temp variables
        int a, b, c, d, e;
        switch (OP_CODE){
            case Operations.PUSH:
                stackMemory.push(programMemory.read(++programCounter));
                break;
            case Operations.POP:
                a = stackMemory.pop();
                System.out.println(a);
                break;

            /** Arithmetic */
            case Operations.ADD:
                b = stackMemory.pop();
                a = stackMemory.pop();
                stackMemory.push(a + b);
                break;
            case Operations.SUB:
                b = stackMemory.pop();
                a = stackMemory.pop();
                stackMemory.push(a - b);
                break;
            case Operations.MUL:
                b = stackMemory.pop();
                a = stackMemory.pop();
                stackMemory.push(a * b);
                break;
            case Operations.DIV:
                b = stackMemory.pop();
                a = stackMemory.pop();
                stackMemory.push(a / b);
                break;

            /** Registeres */
            case Operations.LI:
                VMRegisters.loadToRegister(REGISTER, programMemory.read(++programCounter));
                break;
            case Operations.LW:
                a = programMemory.read(++programCounter); //Memory Address of the Word in HeapStorage
                VMRegisters.loadToRegister(REGISTER, heapMemory.read(a));
                break;

            /** Memory Store */
            case Operations.SW:
                a = programMemory.read(++programCounter); //Memory Address where to store the word
                heapMemory.write(a, VMRegisters.getRegisterValue(REGISTER));
                break;

        }

        programCounter++;
    }

    public void loadInstruction(int[] instructions){

        //This will overwrite existing program instructions loaded to programMemory
        for(int i = 0; i < instructions.length; i++){
            programMemory.write(i, instructions[i]);
        }

        VMState.HAS_LOADED_INSTRUCTIONS = true;
    }
}
