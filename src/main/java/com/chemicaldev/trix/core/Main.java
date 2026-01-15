package com.chemicaldev.trix.core;

import com.chemicaldev.trix.compiler.codegenerator.CodeGeneration;

public class Main {

    public Main(){
        //System.out.println("0x" + Integer.toBinaryString(CodeGeneration.toInstruction(Operations.PUSH)));

        //CodeGeneration txb1 = new CodeGeneration("/SegmentedCode.txb");
        //CodeGeneration txb2 = new CodeGeneration("/SyscallFloatInput.txb");
        CodeGeneration txb2 = new CodeGeneration("/SampleTextPrint.txb");

        VirtualMachineEngine engine = new VirtualMachineEngine();

        engine.createContextWindow(txb2.getInstructions());
        //engine.createContextWindow(txb2.getInstructions());
        engine.prepare();

        engine.operate();
    }



    public static void main(String[] args) {
        new Main();
    }
}