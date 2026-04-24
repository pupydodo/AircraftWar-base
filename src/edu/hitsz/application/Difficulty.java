package edu.hitsz.application;

/**
 * 游戏难度选择。
 */
public enum Difficulty {
    EASY("简单", "scores_EASY.txt"),
    NORMAL("普通", "scores_NORMAL.txt"),
    HARD("困难", "scores_HARD.txt");

    private final String displayName;
    private final String recordFileName;

    Difficulty(String displayName, String recordFileName) {
        this.displayName = displayName;
        this.recordFileName = recordFileName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRecordFileName() {
        return recordFileName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

