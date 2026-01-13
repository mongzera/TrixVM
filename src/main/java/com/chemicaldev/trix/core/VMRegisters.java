package com.chemicaldev.trix.core;

public class VMRegisters {
    public static final byte R0 = 0x00;
    public static final byte R1 = 0x01;
    public static final byte R2 = 0x02;
    public static final byte R3 = 0x03;

    private static int R0_value = 0;
    private static int R1_value = 0;
    private static int R2_value = 0;
    private static int R3_value = 0;

    public static void loadToRegister(byte register, int value){
        switch (register){
            case R0:
                VMRegisters.R0_value = value;
                break;
            case R1:
                VMRegisters.R1_value = value;
                break;
            case R2:
                VMRegisters.R2_value = value;
                break;
            case R3:
                VMRegisters.R3_value = value;
                break;
        }
    }

    public static int getRegisterValue(byte register){
        switch (register){
            case R0:
                return VMRegisters.R0_value;
            case R1:
                return VMRegisters.R1_value;
            case R2:
                return VMRegisters.R2_value;
            default: //default since R3 is the last register
                return VMRegisters.R3_value;
        }
    }
}
