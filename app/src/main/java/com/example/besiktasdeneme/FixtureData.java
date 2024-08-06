package com.example.besiktasdeneme;

public class FixtureData {
    private String date;
    private String teamLogo1;
    private String teamLogo2;
    private String teamName1;
    private String teamName2;
    private String score1;
    private String score2;

    public FixtureData(String date, String teamLogo1, String teamLogo2, String teamName1, String teamName2, String score1, String score2) {
        this.date = date;
        this.teamLogo1 = teamLogo1;
        this.teamLogo2 = teamLogo2;
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.score1 = score1;
        this.score2 = score2;
    }

    public String getDate() {
        return date;
    }

    public String getTeamLogo1() {
        return teamLogo1;
    }

    public String getTeamLogo2() {
        return teamLogo2;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore2() {
        return score2;
    }
}

