package edu.hitsz.dao;

import java.util.List;

public interface RecordDao {
    void addRecord(Record record);
    void deleteRecord(int index);
    List<Record> getAllRecords();
    void printLeaderboard();
}
