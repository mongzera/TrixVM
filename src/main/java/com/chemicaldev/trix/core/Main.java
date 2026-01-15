package com.chemicaldev.trix.core;

import com.chemicaldev.trix.compiler.Compiler;

public class Main {

    public Main(){
        //System.out.println("0x" + Integer.toBinaryString(Compiler.toInstruction(Operations.PUSH)));

        Compiler txb1 = new Compiler("/SegmentedCode.txb");
        Compiler txb2 = new Compiler("/TrixCode.txb");

        VirtualMachineEngine engine = new VirtualMachineEngine();

        engine.createContextWindow(txb1.getInstructions());
        engine.createContextWindow(txb2.getInstructions());
        engine.prepare();

        engine.operate();
    }



    public static void main(String[] args) {
        new Main();
    }
}