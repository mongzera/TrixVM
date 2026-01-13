package com.chemicaldev.trix.memory;

public class MemoryChunk {
    private int size = 0;
    private int[] MEMORY_ARRAY;

    public MemoryChunk(){
        createChunk(256);
    }

    public MemoryChunk(int size){
        createChunk(size);
    }

    private void createChunk(int size){
        //allocate memory
        this.size = size;
        MEMORY_ARRAY = new int[size];
    }

    public int read(int address){
        if(checkBounds(address)) return MEMORY_ARRAY[address];
        else throw new Error("Memory read out of bounds!");
    }

    public void write(int address, int value){
        if(checkBounds(address)) MEMORY_ARRAY[address] = value;
        else throw new Error("Memory write out of bounds!");
    }

    public void print(String title){
        int width = 6 + title.length();

        System.out.println("|  " + title + "  |");
        for(int i = 0; i < width; i++) System.out.print("-");
        System.out.print("\n");

        for(int i = 0; i < 30; i++){
            System.out.println(String.format("0x%s:%s", Integer.toHexString(i), MEMORY_ARRAY[i]));
        }
    }

    private boolean checkBounds(int address){
        if(address < 0 || address >= size) return false;
        else return true;
    }

}
