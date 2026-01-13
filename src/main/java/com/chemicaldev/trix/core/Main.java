package com.chemicaldev.trix.core;

import com.chemicaldev.trix.compiler.Compiler;

public class Main {

    public Main(){
        //System.out.println("0x" + Integer.toBinaryString(Compiler.toInstruction(Operations.PUSH)));

        Compiler compiler = new Compiler("/SegmentedCode.txb");

        VirtualMachineEngine engine = new VirtualMachineEngine();
        engine.prepare(compiler.getInstructions());

        engine.operate();
    }



    public static void main(String[] args) {
        new Main();
    }
}