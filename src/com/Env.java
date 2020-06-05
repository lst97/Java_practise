package com;

import javax.swing.*;
import java.sql.Timestamp;

public class Env {
    // Class Obj Reference
    public static Init cInit = null;
    public static Player cPlayer = null;
    public static Command cCommand = null;
    public static Pdf cPdf = null;
    public static TcpClient cTcpClient = null;

    // userInput
    public static String inputStr;
    public static String strCsvName;

    // DELIMITER
    public static final String COMMA_DELIMITER = ",";
    public static final String SPACE_DELIMITER = " ";
    public static final String DOT_DELIMITER = "\\.";

    // GUI
    public static JFrame objJFrame;
    public static Timestamp objTimeStamp = null;
    public static String[][] jTableData = null;
    public static final String jFrameTitle = "ICTPRG523_AT3_S10000399";
    public static final String[] jTableColumnNames = {"Description", "Level", "Out of Possible"};

    // Client
    public static final int DEFAULT_PORT = 7000;
    public static final String serverName = "localhost";
}
