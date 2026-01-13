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
        int OP_CODE = instruction & 0xFFFF0000;

        if(OP_CODE == 0 || OP_CODE == Operations.BREAK) {
            VMState.CPU_ON = false; // Turn off VM
            if(stackMemory.getPointer() >= 0) System.out.println(stackMemory.peek());

            heapMemory.print("HEAP MEMORY");
        }

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
                a = instruction & 0x0000FFFF; //Register
                VMRegisters.loadToRegister(a, programMemory.read(++programCounter));
                break;
            case Operations.LW:
                a = instruction & 0x0000FFFF; //Register
                b = programMemory.read(++programCounter); //Memory Address of the Word in HeapStorage
                VMRegisters.loadToRegister(a, heapMemory.read(b));
                break;

            /** Memory Store */
            case Operations.SW:
                a = instruction & 0x0000FFFF; //Register that has the value
                b = programMemory.read(++programCounter); //Memory Address where to store the word
                heapMemory.write(b, VMRegisters.getRegisterValue(a));
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
