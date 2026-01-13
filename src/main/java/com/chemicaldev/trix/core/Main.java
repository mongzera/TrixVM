package com.chemicaldev.trix.core;

import com.chemicaldev.trix.compiler.Compiler;

public class Main {

    int[] instructions = new int[]{
            Compiler.toInstruction(Operations.LI, VMRegisters.R0), //load immediate to R0
            54,
            Compiler.toInstruction(Operations.SW, VMRegisters.R0), //store word value from R0
            0x0,               //to this heap memory address
            Compiler.toInstruction(Operations.SW, VMRegisters.R0),
            0x1,
            Operations.BREAK

    };

    int[] pushAdd = new int[]{
            Compiler.toInstruction(Operations.PUSH),
            5,
            Compiler.toInstruction(Operations.PUSH),
            5,
            Compiler.toInstruction(Operations.ADD)
    };

    public Main(){
        //System.out.println("0x" + Integer.toBinaryString(Compiler.toInstruction(Operations.PUSH)));

        Compiler compiler = new Compiler("/TrixCode.txb");

        VirtualMachineEngine engine = new VirtualMachineEngine();
        engine.prepare(compiler.getInstructions());

        engine.operate();
    }



    public static void main(String[] args) {
        new Main();
    }
}