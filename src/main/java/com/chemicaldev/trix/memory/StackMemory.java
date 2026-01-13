package com.chemicaldev.trix.memory;

public class StackMemory {
    private int pointer = -1;
    private MemoryChunk memoryChunk;

    public StackMemory(){
        memoryChunk = new MemoryChunk(256);
    }

    public void push(int value){
        memoryChunk.write(++pointer, value);
    }

    public int pop(){
        return memoryChunk.read(pointer--);
    }

    public int peek(){
        return memoryChunk.read(pointer);
    }

    public int getPointer(){
        return pointer;
    }

    public void print(){
        memoryChunk.print("[STACK MEMORY KIND] PTR: " + pointer);
    }
}
