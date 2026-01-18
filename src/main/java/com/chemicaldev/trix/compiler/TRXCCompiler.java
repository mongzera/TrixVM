package com.chemicaldev.trix.compiler;

public class TRXCCompiler {
    private static int allocatedMemory = 0;

    public static int memAlloc(int size){
        if(size <= 0) throw new Error("Can't allocate no word!");
        int memAddr = allocatedMemory;
        allocatedMemory += size;

        return memAddr;
    }
}
