package com.chemicaldev.trix.compiler;

import com.chemicaldev.trix.core.Operations;
import com.chemicaldev.trix.core.VMRegisters;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Compiler {
    private int[] instructions = new int[1024]; //LENGTH MUST BE THE SAME AS PROGRAM_MEMORY_SIZE
    private int currentInstructionLine = 0;

    private HashMap<String, ArrayList<Integer>> instructionAddressOfSegmentToBeFilled = new HashMap<>();
    private HashMap<String, Integer> instructionAddresses = new HashMap<>();

    public Compiler(String fileDirectory){
        InputStream fileStream = this.getClass().getResourceAsStream(fileDirectory);
        try{
            Scanner fileReader = new Scanner(fileStream);


            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();

                parse(line);
            }

            //fill blank instruction addresses
            fillBlankInstructionAddressesOfSegment();


        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("ERROR: Cannot find file on resource folder [" + fileDirectory + "]");
        }
    }

    private void parse(String line){
        line = line.trim().toLowerCase();

        if(line.length() <= 1) { return;}
        if(line.equals("\n")) return;

        String[] tokens = line.split(" ");

        //check if line is a segment
        if(tokens[0].trim().startsWith(":")){
            instructionAddresses.put(tokens[0].trim(), currentInstructionLine);
            return;
        }

        switch (tokens[0]){
            case "push":
                addInstruction(toInstruction(Operations.PUSH));
                addInstruction(Integer.parseInt(tokens[1]));
                break;
            case "pop":
                addInstruction(toInstruction(Operations.POP));
                break;
            case "break":
                addInstruction(toInstruction(Operations.BREAK));
                break;
            case "pushr":
                addInstruction(toInstruction(Operations.PUSHR, getRegister(tokens[1].trim())));
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
            //LOAD TO REGISTER
            case "li":
                addInstruction(toInstruction(Operations.LI, getRegister(tokens[1])));
                addInstruction(Integer.parseInt(tokens[2]));
                break;
            case "lw":
                addInstruction(toInstruction(Operations.LW, getRegister(tokens[1])));
                addInstruction(Integer.parseInt(tokens[2]));
                break;
            //JUMPS
            case "jz":
                addInstruction(toInstruction(Operations.JZ));
                addBlankInstructionSegment(tokens[1].trim());
                break;
            case "jnz":
                addInstruction(toInstruction(Operations.JNZ));
                addBlankInstructionSegment(tokens[1].trim());
                break;
        }
    }

    /** This function will leave the current line blank
     * The line number will be remembered where we will go back to
     * when we're done loading the program.
     * We will then substitute the instruction count of the segment to these blank instruction.
     * @param segmentName
     */
    private void addBlankInstructionSegment(String segmentName){
        if(!instructionAddressOfSegmentToBeFilled.containsKey(segmentName)){
            instructionAddressOfSegmentToBeFilled.put(segmentName, new ArrayList<>());
        }

        instructionAddressOfSegmentToBeFilled.get(segmentName).add(currentInstructionLine++);
    }

    /**
     *  This will substitute the blank instruction lines with the segment instruction line
     */
    private void fillBlankInstructionAddressesOfSegment(){
        Iterator<String> segmentNames = instructionAddressOfSegmentToBeFilled.keySet().iterator();

        while(segmentNames.hasNext()){
            String segmentName = segmentNames.next();
            ArrayList<Integer> blankInstructions = instructionAddressOfSegmentToBeFilled.get(segmentName);

            for(int address : blankInstructions){
                instructions[address] = instructionAddresses.get(segmentName);
            }
        }
    }

    private byte getRegister(String registerToken){
        switch (registerToken){
            case "r0":
                return VMRegisters.R0;
            case "r1":
                return VMRegisters.R1;
            case "r2":
                return VMRegisters.R2;
            default: //return r3
                return VMRegisters.R3;
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
