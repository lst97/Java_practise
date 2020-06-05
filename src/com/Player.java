package com;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String userName;
    private String tagName;
    private List<Achievement> objAchievement;

    public Player(String userName, String tagName, List<Achievement> objAchievement){
        initAchievement();
        this.userName = userName;
        this.tagName = tagName;
        this.objAchievement = objAchievement;
    }

    private void initAchievement(){ this.objAchievement = new ArrayList<Achievement>(); }
    public List<Achievement> getObjAchievement(){ return this.objAchievement; }
}
