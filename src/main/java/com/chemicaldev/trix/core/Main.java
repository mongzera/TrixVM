package com.chemicaldev.trix.core;

import com.chemicaldev.trix.compiler.codegenerator.CodeGeneration;
import com.chemicaldev.trix.compiler.phases.LexicalAnalysis;

public class Main {

    public Main(){

        //runVM();
        compiler();
    }

    private void compiler(){
        LexicalAnalysis.process("/test.trxc");
    }

    private void runVM(){
        CodeGeneration txb1 = new CodeGeneration("/fib.txb");
        CodeGeneration txb2 = new CodeGeneration("/SampleTextPrint.txb");

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