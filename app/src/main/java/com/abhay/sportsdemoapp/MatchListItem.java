package com.abhay.sportsdemoapp;

public class MatchListItem {
    private String team1;
    private String team2;
    private String type;
    private String matchStatus;

    public MatchListItem(String team1, String team2, String type, String matchStatus) {
        this.team1 = team1;
        this.team2 = team2;
        this.type=type;
        this.matchStatus=matchStatus;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getType() {
        return type;
    }

    public String getMatchStatus() {
        return matchStatus;
    }
}
