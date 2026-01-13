package com.chemicaldev.trix.core;

import com.chemicaldev.trix.memory.MemoryChunk;
import com.chemicaldev.trix.memory.StackMemory;

public class VirtualMachineEngine {
    //Initialize Memories
    private int programCounter = 0;

    MemoryChunk programMemory = new MemoryChunk(1024);
    MemoryChunk heapMemory = new MemoryChunk(1024);
    StackMemory stackMemory = new StackMemory();
    StackMemory callStack = new StackMemory();

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
            //System.out.println("PC: " + programCounter);
            evalInstruction(programMemory.read(programCounter));
            //stackMemory.print();
        }
    }

    private void evalInstruction(int instruction){
        byte OP_CODE = (byte)(instruction >> 24);
        byte REGISTER = (byte)((instruction >> 16) & 0x00FF);

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
            case Operations.BREAK:
                if(callStack.getPointer() >= 0){
                    programCounter = callStack.pop();
                    return;
                }

                VMState.CPU_ON = false; // Turn off VM
                //if(VMState.DBG_ON) stackMemory.print();

                //if(VMState.DBG_ON) heapMemory.print("HEAP MEMORY");

                break;
            case Operations.PUSHR:
                stackMemory.push(VMRegisters.getRegisterValue(REGISTER));
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

            /** Registers */
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

            /** Jump Mechanisms */
            case Operations.JZ:
                a = programMemory.read(++programCounter);

                //push to call stack, we resume our last operation when we are done
                callStack.push(++programCounter);
                if(stackMemory.peek() == 0) programCounter = a;
                break;

            case Operations.JNZ:

                a = programMemory.read(++programCounter);
                callStack.push(++programCounter);
                if(stackMemory.peek() != 0) programCounter = a;
                break;

                //DONT USE: Not sure what to do or if it is needed
//            case Operations.JZR:
//                if(stackMemory.peek() == 0) programCounter = VMRegisters.getRegisterValue(REGISTER);
//                break;
//
//            case Operations.JNZR:
//                if(stackMemory.peek() != 0) programCounter = VMRegisters.getRegisterValue(REGISTER);
//                break;
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
