package com.chemicaldev.trix.core;

public class VMRegisters {
    public static int R0 = 0;
    public static int R1 = 0;
    public static int R2 = 0;
    public static int R3 = 0;

    public static void loadToRegister(int register, int value){
        switch (register){
            case 0:
                VMRegisters.R0 = value;
                break;
            case 1:
                VMRegisters.R1 = value;
                break;
            case 2:
                VMRegisters.R2 = value;
                break;
            case 3:
                VMRegisters.R3 = value;
                break;
        }
    }

    public static int getRegisterValue(int register){
        switch (register){
            case 0:
                return VMRegisters.R0;
            case 1:
                return VMRegisters.R1;
            case 2:
                return VMRegisters.R2;
            default: //default since R3 is the last register
                return VMRegisters.R3;
        }
    }
}
