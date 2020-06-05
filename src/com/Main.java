package com;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        init();
        while (commandProxy() != 4);
    }

    // Handle Class, GUI and Global variable initializing
    public static void init(){
        Env.cInit = new Init();
    }

    // To decide which function should run base one userInput.
    public static int commandProxy() throws IOException {
        Command cCommand = Env.cCommand;
        Scanner scanObj = new Scanner(System.in);
        Env.inputStr = scanObj.nextLine();

        // Check which command function should run by slip the input String.
        int funOpt = cCommand.check(Env.inputStr);
        return cCommand.run(funOpt);
    }
}
