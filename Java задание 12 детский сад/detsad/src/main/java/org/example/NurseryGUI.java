package org.example;

import javax.swing.*;
import java.awt.*;

public class NurseryGUI extends JFrame {
    private final Nursery nursery;
    private final JTextArea outputArea;

    public NurseryGUI() {
        nursery = new Nursery();

        setTitle("Детский сад");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 2 строки, 2 столбца, отступы между кнопками

        JButton addGroupButton = new JButton("Добавить группу");
        addGroupButton.setFont(new Font("Arial", Font.BOLD, 16)); // Увеличим шрифт
        addGroupButton.setPreferredSize(new Dimension(150, 50)); // Увеличим размер кнопки
        addGroupButton.addActionListener(new Listener.AddGroupListener(this, nursery));
        buttonPanel.add(addGroupButton);

        JButton removeGroupButton = new JButton("Удалить группу");
        removeGroupButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeGroupButton.setPreferredSize(new Dimension(150, 50));
        removeGroupButton.addActionListener(new Listener.RemoveGroupListener(this, nursery));
        buttonPanel.add(removeGroupButton);

        JButton addKidButton = new JButton("Добавить ребенка");
        addKidButton.setFont(new Font("Arial", Font.BOLD, 16));
        addKidButton.setPreferredSize(new Dimension(150, 50));
        addKidButton.addActionListener(new Listener.AddKidListener(this, nursery));
        buttonPanel.add(addKidButton);

        JButton removeKidButton = new JButton("Удалить ребенка");
        removeKidButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeKidButton.setPreferredSize(new Dimension(150, 50));
        removeKidButton.addActionListener(new Listener.RemoveKidListener(this, nursery));
        buttonPanel.add(removeKidButton);

        JButton printGroupsButton = new JButton("Вывести группы");
        printGroupsButton.setFont(new Font("Arial", Font.BOLD, 16));
        printGroupsButton.setPreferredSize(new Dimension(150, 50));
        printGroupsButton.addActionListener(new Listener.PrintGroupsListener(nursery, outputArea));
        buttonPanel.add(printGroupsButton);

        JButton exportReportButton = new JButton("Выгрузить отчет");
        exportReportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportReportButton.setPreferredSize(new Dimension(150, 50));
        exportReportButton.addActionListener(new Listener.ExportReportListener(this, outputArea));
        buttonPanel.add(exportReportButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NurseryGUI::new);
    }
}