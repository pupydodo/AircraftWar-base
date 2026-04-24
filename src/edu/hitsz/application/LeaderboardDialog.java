package edu.hitsz.application;

import edu.hitsz.dao.Record;
import edu.hitsz.dao.RecordDao;
import edu.hitsz.dao.RecordDaoImpl;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 游戏结束后弹出的排行榜窗口。
 */
public class LeaderboardDialog extends JDialog {

    private final RecordDao recordDao;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nameField;
    private final int currentScore;

    public LeaderboardDialog(Frame owner, Difficulty difficulty, int currentScore) {
        super(owner, "得分排行榜 - " + difficulty.getDisplayName(), true);
        this.currentScore = currentScore;
        this.recordDao = new RecordDaoImpl(difficulty.getRecordFileName());

        setSize(640, 480);
        setLocationRelativeTo(owner);

        JPanel rootPanel = new JPanel(new BorderLayout(12, 12));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titleLabel = new JLabel("游戏结束 - 当前得分：" + currentScore, SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
        rootPanel.add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"排名", "姓名", "得分", "时间"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rootPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("玩家姓名："));
        nameField = new JTextField(18);
        nameField.setText("player");
        inputPanel.add(nameField);
        JButton addButton = new JButton("保存本局成绩");
        addButton.addActionListener(e -> addCurrentRecord());
        inputPanel.add(addButton);
        bottomPanel.add(inputPanel);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("删除选中记录");
        deleteButton.addActionListener(e -> deleteSelectedRecord());
        actionPanel.add(deleteButton);
        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> refreshTable());
        actionPanel.add(refreshButton);
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());
        actionPanel.add(closeButton);
        bottomPanel.add(actionPanel);

        rootPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(rootPanel);

        refreshTable();
        promptAndMaybeAddCurrentRecord();
    }

    private void promptAndMaybeAddCurrentRecord() {
        String input = JOptionPane.showInputDialog(this, "请输入玩家姓名（取消则不保存本局成绩）", "保存成绩", JOptionPane.PLAIN_MESSAGE);
        if (input == null) {
            return;
        }
        String userName = input.trim();
        if (userName.isEmpty()) {
            return;
        }
        nameField.setText(userName);
        addCurrentRecord();
    }

    private void addCurrentRecord() {
        String userName = nameField.getText() == null ? "" : nameField.getText().trim();
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入姓名后再保存成绩。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        String currentTime = formatter.format(new Date());
        recordDao.addRecord(new Record(userName, currentScore, currentTime));
        refreshTable();
    }

    private void deleteSelectedRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一条记录。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        recordDao.deleteRecord(selectedRow);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Record> records = recordDao.getAllRecords();
        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            tableModel.addRow(new Object[]{i + 1, record.getUserName(), record.getScore(), record.getTime()});
        }
    }
}


