package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Init {

    public Init() {
        // Ready for process user command.
        Env.cCommand = new Command();
    }

    public void initPdf(){
        Env.cPdf = new Pdf();
    }
    public void initTcpClient(String serverName, int port){
        Env.cTcpClient = new TcpClient(serverName, port);
    }

    public void initPlayer(List<List<String>> records){
        String[] playerAttr = new String[2];
        int recordSize = records.size();
        int wecx = 0;

        // userName && tagName read from ArrayList.
        playerAttr[0] = records.get(0).get(1);
        playerAttr[1] = records.get(0).get(2);

        // ArrayList object for Achievement class
        List<Achievement> objAchievement = new ArrayList<Achievement>();

        // Add Achievement obj into ArrayList
        int achievement  = recordSize - 1;
        while(wecx++ < achievement){
            String description = records.get(wecx).get(1);
            int level = Integer.parseInt(records.get(wecx).get(2));
            int max = Integer.parseInt(records.get(wecx).get(3));
            objAchievement.add(new Achievement(description, level, max));
        }
        Env.cPlayer = new Player(playerAttr[0], playerAttr[1], objAchievement);
    }

    public void initGui(){
        int wecx = 0;
        List<Achievement> objAchievement = Env.cPlayer.getObjAchievement();

        // GUI JFrame
        JFrame objJFrame = new JFrame();
        objJFrame.setSize(500, 400);
        objJFrame.setTitle(Env.jFrameTitle);

        objJFrame.addWindowListener(new GuiControl.cWindowListener());

        // Initializing the JPanel
        JPanel objJPanel = new JPanel();

        // Prepare data for JTable
        int achievementCount = objAchievement.size();
        // Save Achievement data by Row
        List<String[]> objJTableData = new ArrayList<String[]>();
        while(wecx < achievementCount){
            String[] strBuffer = new String[3];
            strBuffer[0] = objAchievement.get(wecx).description;
            strBuffer[1] = Integer.toString(objAchievement.get(wecx).level);
            strBuffer[2] = Integer.toString(objAchievement.get(wecx).maximum);
            objJTableData.add(strBuffer);
            wecx++;
        }
        // Q1C_a Order By for description and level.
        objJTableData.sort(new Comparator<String[]>() {
            public int compare(String[] str, String[] nextStr) {
                return str[1].compareTo(nextStr[1]);
            }
        });
        // Convert ArrayList into 2D array so that JTable can access.
        String[][] arr2DjTableData = new String[objJTableData.size()][];
        for (int fecx = 0; fecx < arr2DjTableData.length; fecx++) {
            String[] arrStrRow = objJTableData.get(fecx);
            arr2DjTableData[fecx] = arrStrRow;
        }
        Env.jTableData = arr2DjTableData;

        // Initializing the JTable
        JTable objJTable = new JTable(arr2DjTableData, Env.jTableColumnNames);
        objJFrame.add(objJTable);

        // Initializing the JScrollPane
        JScrollPane objJSP = new JScrollPane(objJTable);
        objJPanel.add(objJSP);

        // Initializing the JButton
        JButton objJBtn = new JButton("Close");
        objJFrame.add(objJBtn);
        objJBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiControl.closeWindow();
            }
        });

        // Initializing the JLabel
        Env.objTimeStamp = new Timestamp(System.currentTimeMillis());
        JLabel objJLabel = new JLabel();
        objJLabel.setText(Env.objTimeStamp.toString());
        objJFrame.add(objJLabel);

        // Size && position
        objJTable.setBounds(0, 0, 500, 200);
        objJLabel.setBounds(8,objJTable.getHeight(),200,30);
        objJBtn.setBounds(200,objJTable.getHeight() + 25,100,30);

        // Display Frame
        objJFrame.add(objJPanel);
        objJFrame.setVisible(true);
        objJFrame.pack();

        // Set Env reference
        Env.objJFrame = objJFrame;
    }
}
