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

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Check first if there is loaded instructions
            if(!VMState.HAS_LOADED_INSTRUCTIONS) return;
            int instruction = programMemory.read(programCounter);
            if(VMState.DBG_ON) System.out.println("0x" + Integer.toHexString(instruction) + "| PC: " + programCounter);
            evalInstruction(instruction);
            if(VMState.DBG_ON) System.out.printf("[R0=%s, R1=%s, R2=%s, R3=%s]%n", VMRegisters.getRegisterValue(VMRegisters.R0), VMRegisters.getRegisterValue(VMRegisters.R1), VMRegisters.getRegisterValue(VMRegisters.R2), VMRegisters.getRegisterValue(VMRegisters.R3));
            if(VMState.MEM_DBG_ON) stackMemory.print();
        }
    }

    private void evalInstruction(int instruction){
        byte OP_CODE = (byte)(instruction >> 24);
        byte REGISTER_1 = (byte)((instruction >> 16) & 0x00FF);
        byte REGISTER_2 = (byte)((instruction >> 8) & 0x0000FF);
        byte REGISTER_3 = (byte)((instruction) & 0x000000FF);

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
            case Operations.PEEK:
                System.out.println(stackMemory.peek());
                break;
            case Operations.DUP:
                a = stackMemory.pop();
                stackMemory.push(a);
                stackMemory.push(a);
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
                stackMemory.push(VMRegisters.getRegisterValue(REGISTER_1));
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
                VMRegisters.loadToRegister(REGISTER_1, programMemory.read(++programCounter));
                break;
            case Operations.LW:
                a = programMemory.read(++programCounter); //Memory Address of the Word in HeapStorage
                VMRegisters.loadToRegister(REGISTER_1, heapMemory.read(a));
                break;
            case Operations.LWR:
                VMRegisters.loadToRegister(REGISTER_1, heapMemory.read(REGISTER_2));
                break;
            case Operations.LS:
                a = stackMemory.pop(); // Pop stack
                VMRegisters.loadToRegister(REGISTER_1, a);
                break;

            /** Memory Store */
            case Operations.SW:
                a = programMemory.read(++programCounter); //Memory Address where to store the word
                heapMemory.write(a, VMRegisters.getRegisterValue(REGISTER_1));
                break;
            case Operations.SS:
                a = stackMemory.pop(); //Memory Address where to store the word
                b = programMemory.read(++programCounter);
                heapMemory.write(b, a);
                break;
            case Operations.SSR:
                a = stackMemory.pop(); //value, top of stack
                heapMemory.write(REGISTER_1, a);
                break;

            /** Jump Mechanisms USE RETURN INSTEAD OF BREAK*/
            case Operations.JMP:
                a = programMemory.read(++programCounter);
                programCounter = a;
                return;
            case Operations.JZ:
                a = programMemory.read(++programCounter);
                if(stackMemory.peek() == 0) programCounter = a;
                return;
            case Operations.JNZ:
                a = programMemory.read(++programCounter);
                if(stackMemory.peek() != 0) programCounter = a;
                return;
            case Operations.CALL:
                a = programMemory.read(++programCounter);
                callStack.push(++programCounter);

                programCounter = a;
                return;

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
