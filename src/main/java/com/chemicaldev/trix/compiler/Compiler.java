package com.chemicaldev.trix.compiler;

public class Compiler {
    public Compiler(String program){
        //Start parsing file here
    }

    public static int toInstruction(byte arg1, byte arg2, byte arg3, byte arg4){
        int instruction = arg1;
        instruction = (instruction << 8) | arg2;
        instruction = (instruction << 8) | arg3;
        instruction = (instruction << 8) | arg4;

        return instruction;
    }

    public static int toInstruction(byte arg1){
        return Compiler.toInstruction(arg1, (byte)0, (byte) 0, (byte) 0);
    }

    public static int toInstruction(byte arg1, byte arg2){
        return Compiler.toInstruction(arg1, arg2, (byte) 0, (byte) 0);
    }

    public static int toInstruction(byte arg1, byte arg2, byte arg3){
        return Compiler.toInstruction(arg1, arg2, arg3, (byte) 0);
    }



}
