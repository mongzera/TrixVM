package com.chemicaldev.trix.core;

import java.util.HashMap;

public class Operations {

    public static final byte PUSH =  0x01;
    public static final byte POP  =  0x02;
    public static final byte PEEK =  0x03;
    public static final byte BREAK = 0x04;
    public static final byte PUSHR = 0x05; // PUSH_REGISTER, this will take the value of a register and push it to the stack
    public static final byte DUP = 0x06; //duplicates the top of the stack

    //Arithmetic OpCode
    public static final byte ADD = 0x11;
    public static final byte SUB = 0x12;
    public static final byte MUL = 0x13;
    public static final byte DIV = 0x14;

    //Register Load OpCode
    public static final byte LI = 0x21; //Load Immediate
    public static final byte LW = 0x22; //Load Word ie. 32 bits = 4bytes
    public static final byte LS = 0x23; //Load from top of stack


    //Memory Store OpCode
    public static final byte SW = 0x31; //Store Word ie. 32 bits = 4bytes
    public static final byte SS = 0x32; //Store Stack: Stores top of stack to heap at addr x
    public static final byte SSR = 0x33; //Store Stack Register: Stores top of stack to heap at addr contained in register

    //Instruction Manipulation
    public static final byte JNZ = 0x40; //Set the program counter to x if top of stack /= 0
    public static final byte JZ = 0x41; //Set the program counter to x if top of stack = 0
    public static final byte CALL = 0x42; //Set the program counter to address of x, adds to CallStack
    public static final byte JMP = 0x43; //A label for a program instruction address

}
