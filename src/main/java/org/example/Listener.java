package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener {

    public static class AddGroupListener implements ActionListener {
        private final NurseryGUI gui;
        private final Nursery nursery;

        public AddGroupListener(NurseryGUI gui, Nursery nursery) {
            this.gui = gui;
            this.nursery = nursery;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField groupNameField = new JTextField();
            JTextField groupNumberField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Название группы:"));
            panel.add(groupNameField);
            panel.add(new JLabel("Номер группы:"));
            panel.add(groupNumberField);

            int result = JOptionPane.showConfirmDialog(gui, panel, "Добавить группу", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String groupName = groupNameField.getText();
                String groupNumberStr = groupNumberField.getText();

                if (groupName.isEmpty() || groupNumberStr.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Пожалуйста, заполните все поля");
                    return;
                }

                int groupNumber = Integer.parseInt(groupNumberStr);
                nursery.addGroup(new Group(groupName, groupNumber, nursery.getConnection()));
                JOptionPane.showMessageDialog(gui, "Группа добавлена");
            }
        }
    }

    public static class RemoveGroupListener implements ActionListener {
        private final NurseryGUI gui;
        private final Nursery nursery;

        public RemoveGroupListener(NurseryGUI gui, Nursery nursery) {
            this.gui = gui;
            this.nursery = nursery;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField groupNameField = new JTextField();
            JTextField groupNumberField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Название группы:"));
            panel.add(groupNameField);
            panel.add(new JLabel("Номер группы:"));
            panel.add(groupNumberField);

            int result = JOptionPane.showConfirmDialog(gui, panel, "Удалить группу", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String groupName = groupNameField.getText();
                String groupNumberStr = groupNumberField.getText();

                if (groupName.isEmpty() || groupNumberStr.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Пожалуйста, заполните все поля");
                    return;
                }

                int groupNumber = Integer.parseInt(groupNumberStr);
                Group group = nursery.findGroupByNumberAndName(groupNumber, groupName);
                if (group != null) {
                    nursery.removeGroup(group);
                    JOptionPane.showMessageDialog(gui, "Группа удалена");
                } else {
                    JOptionPane.showMessageDialog(gui, "Такой группы нет");
                }
            }
        }
    }

    public static class AddKidListener implements ActionListener {
        private final NurseryGUI gui;
        private final Nursery nursery;

        public AddKidListener(NurseryGUI gui, Nursery nursery) {
            this.gui = gui;
            this.nursery = nursery;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField groupNameField = new JTextField();
            JTextField groupNumberField = new JTextField();
            JTextField kidFullNameField = new JTextField();
            JTextField kidGenderField = new JTextField();
            JTextField kidAgeField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Название группы:"));
            panel.add(groupNameField);
            panel.add(new JLabel("Номер группы:"));
            panel.add(groupNumberField);
            panel.add(new JLabel("ФИО ребенка:"));
            panel.add(kidFullNameField);
            panel.add(new JLabel("Пол ребенка:"));
            panel.add(kidGenderField);
            panel.add(new JLabel("Возраст ребенка:"));
            panel.add(kidAgeField);

            int result = JOptionPane.showConfirmDialog(gui, panel, "Добавить ребенка", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String groupName = groupNameField.getText();
                String groupNumberStr = groupNumberField.getText();
                String kidFullName = kidFullNameField.getText();
                String kidGender = kidGenderField.getText();
                String kidAgeStr = kidAgeField.getText();

                if (groupName.isEmpty() || groupNumberStr.isEmpty() || kidFullName.isEmpty() || kidGender.isEmpty() || kidAgeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Пожалуйста, заполните все поля");
                    return;
                }

                int groupNumber = Integer.parseInt(groupNumberStr);
                int kidAge = Integer.parseInt(kidAgeStr);

                Group group = nursery.findGroupByNumberAndName(groupNumber, groupName);
                if (group != null) {
                    group.addKid(new Kid(kidFullName, kidGender, kidAge));
                    JOptionPane.showMessageDialog(gui, "Ребенок добавлен");
                } else {
                    JOptionPane.showMessageDialog(gui, "Такой группы нет");
                }
            }
        }
    }

    public static class RemoveKidListener implements ActionListener {
        private final NurseryGUI gui;
        private final Nursery nursery;

        public RemoveKidListener(NurseryGUI gui, Nursery nursery) {
            this.gui = gui;
            this.nursery = nursery;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField groupNameField = new JTextField();
            JTextField groupNumberField = new JTextField();
            JTextField kidFullNameField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Название группы:"));
            panel.add(groupNameField);
            panel.add(new JLabel("Номер группы:"));
            panel.add(groupNumberField);
            panel.add(new JLabel("ФИО ребенка:"));
            panel.add(kidFullNameField);

            int result = JOptionPane.showConfirmDialog(gui, panel, "Удалить ребенка", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String groupName = groupNameField.getText();
                String groupNumberStr = groupNumberField.getText();
                String kidFullName = kidFullNameField.getText();

                if (groupName.isEmpty() || groupNumberStr.isEmpty() || kidFullName.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Пожалуйста, заполните все поля");
                    return;
                }

                int groupNumber = Integer.parseInt(groupNumberStr);

                Group group = nursery.findGroupByNumberAndName(groupNumber, groupName);
                if (group != null) {
                    Kid kidToRemove = Group.findKidInGroup(group, kidFullName);
                    if (kidToRemove != null) {
                        group.removeKid(kidToRemove);
                        JOptionPane.showMessageDialog(gui, "Ребенок удален");
                    } else {
                        JOptionPane.showMessageDialog(gui, "Такого ребенка нет");
                    }
                } else {
                    JOptionPane.showMessageDialog(gui, "Такой группы нет");
                }
            }
        }
    }

    public static class PrintGroupsListener implements ActionListener {
        private final Nursery nursery;
        private final JTextArea outputArea;

        public PrintGroupsListener(Nursery nursery, JTextArea outputArea) {
            this.nursery = nursery;
            this.outputArea = outputArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            outputArea.setFont(new Font("Arial", Font.PLAIN, 18));
            nursery.printGroups(outputArea);
        }
    }

    public static class ExportReportListener implements ActionListener {
        private final NurseryGUI gui;
        private final JTextArea outputArea;

        public ExportReportListener(NurseryGUI gui, JTextArea outputArea) {
            this.gui = gui;
            this.outputArea = outputArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = {"PDF", "TXT"};
            int choice = JOptionPane.showOptionDialog(gui, "Выберите формат отчета", "Выгрузить отчет", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == 0) { // PDF
                ReportExporter.exportToPDF(outputArea.getText(), gui);
            } else if (choice == 1) { // TXT
                ReportExporter.exportToTXT(outputArea.getText(), gui);
            }
        }
    }

}