package com.chemicaldev.trix.core;

import java.util.HashMap;

public class Operations {

    public static final byte PUSH =  0x01;
    public static final byte POP  =  0x02;
    public static final byte PEEK =  0x03;
    public static final byte BREAK = 0x04;

    //Arithmetic OpCode
    public static final byte ADD = 0x11;
    public static final byte SUB = 0x12;
    public static final byte MUL = 0x13;
    public static final byte DIV = 0x14;

    //Register Load OpCode
    public static final byte LI = 0x21; //Load Immediate
    public static final byte LW = 0x22; //Load Word ie. 32 bits = 4bytes

    //Memory Store OpCode
    public static final byte SW = 0x31; //Store Word ie. 32 bits = 4bytes






}
