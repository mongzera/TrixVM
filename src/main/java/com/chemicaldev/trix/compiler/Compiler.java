package com.chemicaldev.trix.compiler;

import com.chemicaldev.trix.core.Operations;

import java.io.InputStream;
import java.util.Scanner;

public class Compiler {
    private int[] instructions = new int[1024]; //LENGTH MUST BE THE SAME AS PROGRAM_MEMORY_SIZE
    private int currentInstructionLine = 0;

    public Compiler(String fileDirectory){
        InputStream fileStream = this.getClass().getResourceAsStream(fileDirectory);
        try{
            Scanner fileReader = new Scanner(fileStream);


            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();

                parse(line);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("ERROR: Cannot find file on resource folder [" + fileDirectory + "]");
        }
    }

    private void parse(String line){
        line = line.toLowerCase();

        if(line.length() <= 1) { return;}
        if(line.equals("\n")) return;

        String[] tokens = line.split(" ");

        switch (tokens[0]){
            case "push":
                addInstruction(toInstruction(Operations.PUSH));
                addInstruction(Integer.parseInt(tokens[1]));
                break;
            case "pop":
                addInstruction(toInstruction(Operations.POP));
                break;
            case "break":
                addInstruction((toInstruction(Operations.BREAK)));
                break;
            //ARITHMETIC
            case "add":
                addInstruction(toInstruction(Operations.ADD));
                break;
            case "sub":
                addInstruction(toInstruction(Operations.SUB));
                break;
            case "mul":
                addInstruction(toInstruction(Operations.MUL));
                break;
            case "div":
                addInstruction(toInstruction(Operations.DIV));
                break;
        }
    }

    private void addInstruction(int line){
        instructions[currentInstructionLine++] = line;

    }

    public int[] getInstructions(){
        return instructions;
    }

    public static int toInstruction(byte arg1, byte arg2, byte arg3, byte arg4){
        int instruction = arg1;
        instruction = (instruction << 8) | arg2;
        instruction = (instruction << 8) | arg3;
        instruction = (instruction << 8) | arg4;

        return instruction;
    }

    public static int toInstruction(byte arg1){
        return Compiler.toInstruction(arg1, (byte)0, (byte) 0, (byte) 0);
    }

    public static int toInstruction(byte arg1, byte arg2){
        return Compiler.toInstruction(arg1, arg2, (byte) 0, (byte) 0);
    }

    public static int toInstruction(byte arg1, byte arg2, byte arg3){
        return Compiler.toInstruction(arg1, arg2, arg3, (byte) 0);
    }



}
