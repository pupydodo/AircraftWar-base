package edu.hitsz.dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    private List<Record> records;
    private String fileName;

    public RecordDaoImpl(String difficulty) {
        records = new ArrayList<>();
        this.fileName = "data/scores_" + difficulty + ".txt";
        loadRecords();
    }

    private void loadRecords() {
        File file = new File(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    records.add(new Record(parts[0], Integer.parseInt(parts[1]), parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Record record : records) {
                bw.write(record.getUserName() + "," + record.getScore() + "," + record.getTime());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRecord(Record record) {
        records.add(record);
        saveRecords();
    }

    @Override
    public List<Record> getAllRecords() {
        return records;
    }

    @Override
    public void printLeaderboard() {
        records.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));
        System.out.println("************************************************");
        System.out.println("                   得分排行榜                    ");
        System.out.println("************************************************");
        for (int i = 0; i < records.size(); i++) {
            Record r = records.get(i);
            System.out.println("第" + (i + 1) + "名: " + r.getUserName() + "," + r.getScore() + "," + r.getTime());
        }
    }
}
