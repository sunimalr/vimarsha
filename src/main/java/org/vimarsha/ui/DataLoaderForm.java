/*
 *
 *  vimarsha, Performance analysis: Machine Learning Approach
 *  Copyright (C) 2013 vimarsha
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 * /
 */

package org.vimarsha.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.vimarsha.mediator.impl.UIHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class DataLoaderForm {
    private JPanel Tab0;
    private JButton openRAWFileButton;
    private JButton openARFFFileButton;
    private JButton saveToARFFFileButton;
    private JTable table1;
    private JTable table2;
    private JComboBox architectureComboBox;
    private JTable attributesTable;
    private JTable attributesSummaryTable;
    private ChartPanel attributeDetailsChart;
    private JList attributeList;
    private JTextField trainingModelTextBox;
    private JTextField testDataTextField;

    public DataLoaderForm() {


        for (String str : UIHandler.getInstance().getArchitectureList()) {
            architectureComboBox.addItem(str);
            architectureComboBox.setSelectedItem(null);
        }

        architectureComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().setArchitecture((String) architectureComboBox.getSelectedItem());
                trainingModelTextBox.setText(UIHandler.getInstance().getTrainingModel());
            }
        });
        openRAWFileButton.addActionListener(new ActionListener() {
            File file = null;

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(Tab0);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }
                if ((UIHandler.getInstance().setRawFile(file) == 100) && (UIHandler.getInstance().convertRawToArff() == 100)) {
                    saveToARFFFileButton.setEnabled(true);
                    attributeList.setListData(UIHandler.getInstance().getArffAttribiutesTableModel().toArray());
                    testDataTextField.setText(UIHandler.getInstance().getTestDataName());
                }

            }
        });
        openARFFFileButton.addActionListener(new ActionListener() {
            File file = null;

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(Tab0);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    UIHandler.getInstance().setArffFile(file);
                    attributeList.setListData(UIHandler.getInstance().getArffAttribiutesTableModel().toArray());
                    saveToARFFFileButton.setEnabled(true);
                    testDataTextField.setText(UIHandler.getInstance().getTestDataName());
                }

            }
        });
        saveToARFFFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File file = null;
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(Tab0);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(fc.getSelectedFile());
                    UIHandler.getInstance().saveAsArff(fc.getSelectedFile());
                }

            }
        });

        attributeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int selectedEvent = attributeList.getSelectedIndex();
                attributesSummaryTable.setModel(UIHandler.getInstance().getArffAttributeInfo(attributeList.getSelectedIndex()));
                DefaultCategoryDataset data = UIHandler.getInstance().getBarChartDataSet(selectedEvent);
                drawBarChart(data);
            }
        });
    }

    private void drawBarChart(DefaultCategoryDataset data) {
        JFreeChart chart = ChartFactory.createBarChart("Binned event data", "bins", "count", data, PlotOrientation.VERTICAL, false, true, false);
        this.attributeDetailsChart.setChart(chart);
        this.attributeDetailsChart.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        attributeDetailsChart = new ChartPanel(null);
    }


    public JTextField getTrainingModelTextBox() {
        return trainingModelTextBox;
    }

    public void setTrainingModelTextBox(JTextField trainingModelTextBox) {
        this.trainingModelTextBox = trainingModelTextBox;
    }


    public JComboBox getArchitectureComboBox() {
        return architectureComboBox;
    }

    public void setArchitectureComboBox(JComboBox architectureComboBox) {
        this.architectureComboBox = architectureComboBox;
    }


    public JTextField getTestDataTextField() {
        return testDataTextField;
    }

    public void setTestDataTextField(JTextField testDataTextField) {
        this.testDataTextField = testDataTextField;
    }
}
