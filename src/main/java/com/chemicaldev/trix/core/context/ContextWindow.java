package com.chemicaldev.trix.core.context;

import com.chemicaldev.trix.core.VMRegisters;
import com.chemicaldev.trix.core.VMState;
import com.chemicaldev.trix.memory.MemoryChunk;
import com.chemicaldev.trix.memory.StackMemory;

public class ContextWindow {
    //Initialize Memories
    public ContextState contextState = new ContextState();
    public MemoryChunk programMemory = new MemoryChunk(1024);
    public MemoryChunk heapMemory = new MemoryChunk(1024);
    public StackMemory stackMemory = new StackMemory();
    public StackMemory callStack = new StackMemory();

    public boolean toBeTerminated = false;
    public ContextWindow(int[] instructions){
        loadInstruction(instructions);
    }

    public void loadInstruction(int[] instructions){

        //This will overwrite existing program instructions loaded to programMemory
        for(int i = 0; i < instructions.length; i++){
            programMemory.write(i, instructions[i]);
        }

        contextState.HAS_LOADED_INSTRUCTIONS = true;
    }

    public void incrementPC(){
        contextState.programCounter++;
    }

    public void terminate(){
        toBeTerminated = true;
    }


}
