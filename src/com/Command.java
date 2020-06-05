package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    private final String[] supportCommand = {"open", "search", "export", "tcpconnect", "exit"};

    public int check(String inputStr){
        int size = supportCommand.length - 1;
        String[] command = inputStr.split(Env.SPACE_DELIMITER,2);

        // Find out if the command is supported.
        do{
            if (command[0].equals(supportCommand[size])) {
                return size;
            }
        }while(size-- != 0);

        return -1;  // This command not support
    }

    public int run(int opt) throws IOException {
        // Separate command parameter
        String[] strBuffer = Env.inputStr.split(Env.SPACE_DELIMITER, 2);

        switch(opt){
            case 0: {
                if (Env.objJFrame != null){
                    System.out.println("A JFrame instance already exist, please close the window.");
                    return -2;
                }
                Env.strCsvName = strBuffer[1];
                displayCsvByGui(Env.strCsvName);
                return opt;
            }

            case 1: {
                if (Env.cPlayer == null) {
                    System.out.println("Please open a CSV file before searching.");
                    return -3;
                }

                if (searchAchievements(strBuffer[1])){
                    System.out.println("Achievement Found.");
                }else{
                    System.out.println("Achievement Not Found.");
                }
                return opt;
            }

            case 2: {
                if (Env.cPlayer == null) {
                    System.out.println("Please open a CSV file before converting.");
                    return -4;
                }
                convertCsvToPdf();
                return opt;
            }

            case 3: {
                if (Env.cPlayer == null) {
                    System.out.println("Please open a CSV file before connection.");
                    return -5;
                }
                // Default server name

                Env.cInit.initTcpClient(Env.serverName, Env.DEFAULT_PORT);
                if (Env.cTcpClient.getConnection() != null) {
                    System.out.println(Env.cTcpClient.toString());
                    Env.cTcpClient.runClient();
                }

                return opt;
            }

            case 4: {
                if (Env.objJFrame != null) {
                    GuiControl.closeWindow();
                }
                return opt;
            }

            default:
                System.out.println("'" + strBuffer[0] + "' is not recognized as an command.");
                return -1;
        }
    }

    private void displayCsvByGui(String csvPath){
        // Put all the CSV data into memory following ArrayList<List<String>>
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader objBufReader = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = objBufReader.readLine()) != null) {
                String[] raw = line.split(Env.COMMA_DELIMITER);
                records.add(Arrays.asList(raw));
            }
            objBufReader.close();

            // Init a Player class according to the CSV file ArrayList<>(record)
            Env.cInit.initPlayer(records);
            Env.cInit.initGui();

        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    private boolean searchAchievements(String target){
        List<Achievement> objAchievement = Env.cPlayer.getObjAchievement();
        int wecx = 0;
        int achievementSize = objAchievement.size();

        while(wecx < achievementSize){
            if(objAchievement.get(wecx).description.compareTo(target) == 0){
                return true;
            }
            wecx++;
        }
        return false;
    }

    private void convertCsvToPdf() {
        if(Env.cPdf == null){
            Env.cInit.initPdf();
        }
        Env.cPdf.create();
    }
}
