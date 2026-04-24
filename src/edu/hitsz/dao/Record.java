package edu.hitsz.dao;

public class Record {
    private String userName;
    private int score;
    private String time;

    public Record(String userName, int score, String time) {
        this.userName = userName;
        this.score = score;
        this.time = time;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
