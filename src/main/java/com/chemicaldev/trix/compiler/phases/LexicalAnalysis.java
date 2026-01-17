package com.chemicaldev.trix.compiler.phases;

import java.util.ArrayList;
import java.util.Scanner;

public class LexicalAnalysis {
    private static ArrayList<Token> tokens = new ArrayList<>();

    public static void process(String resDir){
        StringBuilder hllCode = new StringBuilder();
        Scanner scanner = new Scanner(LexicalAnalysis.class.getResourceAsStream(resDir));

        while(scanner.hasNextLine()){
            hllCode.append(scanner.nextLine()).append('\n');
        }

        String cleanHLL = stripComments(hllCode.toString());
        cleanHLL = spacePunctuations(cleanHLL).replaceAll("\\s+", " ");;
        //System.out.println(cleanHLL);
        tokenize(cleanHLL);

    }

    private static void tokenize(String code){
        String[] tokens = code.split(" ");

        for(String s : tokens){
            System.out.println(s);
        }
    }

    /**
     *
     * @param code
     * @return the original code but with spaces on the sides of these punctuations in regex
     */
    private static String spacePunctuations(String code){
        return code.replaceAll("\\s*([,;?!()\\[\\]{}<>=])\\s*", " $1 ");
    }


    private static String stripComments(String code){
        boolean isComment = false;
        boolean isMultulineComment = false;
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < code.length(); i++){
            if(isMultulineComment && code.substring(i).startsWith("*/")) {
                isMultulineComment = false;
                i += 2;
            }
            if(code.substring(i).startsWith("//")) isComment = true;
            if(code.substring(i).startsWith("/**")) isMultulineComment = true;

            if(!(isComment || isMultulineComment) ){
                builder.append(code.charAt(i));
            }
            if(isComment && code.charAt(i) == '\n') isComment = false;
        }

        return builder.toString().trim();
    }

    public class Token{
        public TokenType tokenType;
        public String token;
    }



}
