package edu.hitsz.dao;

import java.util.List;

public interface RecordDao {
    void addRecord(Record record);
    List<Record> getAllRecords();
    void printLeaderboard();
}
