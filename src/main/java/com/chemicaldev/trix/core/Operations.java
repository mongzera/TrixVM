package com.chemicaldev.trix.core;

import java.util.HashMap;

public class Operations {

    public static final int PUSH =  0x00010000;
    public static final int POP  =  0x00020000;
    public static final int PEEK =  0x00030000;
    public static final int BREAK = 0x00040000;

    //Arithmetic OpCode
    public static final int ADD = 0x00110000;
    public static final int SUB = 0x00120000;
    public static final int MUL = 0x00130000;
    public static final int DIV = 0x00140000;

    //Register Load OpCode
    public static final int LI = 0x00210000; //Load Immediate
    public static final int LW = 0x00220000; //Load Word ie. 32 bits = 4bytes

    //Memory Store OpCode
    public static final int SW = 0x00310000; //Store Word ie. 32 bits = 4bytes






}
