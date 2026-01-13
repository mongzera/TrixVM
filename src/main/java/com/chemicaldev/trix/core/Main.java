package com.chemicaldev.trix.core;

public class Main {

    int[] instructions = new int[]{
            Operations.LI | 0, //load immediate to R0
            54,
            Operations.SW | 0, //store word value from R0
            0x0,               //to this heap memory address
            Operations.SW | 0,
            0x1,
            Operations.BREAK

    };

    public Main(){
        VirtualMachineEngine engine = new VirtualMachineEngine();
        engine.prepare(instructions);

        engine.operate();
    }

    public static void main(String[] args) {
        new Main();
    }
}