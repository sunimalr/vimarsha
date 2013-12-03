/*
 * vimarsha, Performance analysis: Machine Learning Approach
 * Copyright (C) 2013 vimarsha
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.vimarsha.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.vimarsha.mediator.impl.UIHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.FieldPosition;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class TimeSlicedClassiferForm {
    private JPanel Tab3;
    private JComboBox architectureComboBox;
    private JButton classifyButton;
    private JButton exportButton;
    private ChartPanel chartPanel;
    private JPanel timeSlicedGraph;
    private JTextField trainingModelTextBox;
    private JTextField testDataTextField;

    public TimeSlicedClassiferForm() {

        for (String str : UIHandler.getInstance().getArchitectureList()) {
            architectureComboBox.addItem(str);
        }

        classifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().classifyTimeSliced();
                XYSeriesCollection data = UIHandler.getInstance().getXYChartDataSet();
                displayTimeSlicedChart(data);
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File file = null;
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(Tab3);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(fc.getSelectedFile());
                    UIHandler.getInstance().exportTimeSlicedAsCSV(fc.getSelectedFile());
                }
            }
        });
    }

    private void displayTimeSlicedChart(XYSeriesCollection dataSet) {
        JFreeChart chart = createChart(dataSet, "Time sliced classification results");
        this.chartPanel.setChart(chart);
        this.chartPanel.setRangeZoomable(false);
        this.chartPanel.setDomainZoomable(true);
        this.chartPanel.setVisible(true);
    }

    private JFreeChart createChart(XYSeriesCollection dataSet, String chartTitle) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                chartTitle,
                "Time slice number",
                "Classification",
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        //ValueAxis range = plot.getRangeAxis();
        ValueAxis domain = plot.getDomainAxis();

        range.setRange(0, 1.1);
        TickUnits units = new TickUnits();
        units.add(new NumberTickUnit(0));
        units.add(new NumberTickUnit(0.5));
        units.add(new NumberTickUnit(1));

        range.setStandardTickUnits(units);
        range.setNumberFormatOverride(new DecimalFormat() {
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
                if (number == 1)
                    return toAppendTo.append("badfs");
                else if (number == 0.5)
                    return toAppendTo.append("badma");
                else
                    return toAppendTo.append("good");
            }
        });

        return chart;
    }

    private void createUIComponents() {
        this.chartPanel = new ChartPanel(null);
    }


    public JComboBox getArchitectureComboBox() {
        return architectureComboBox;
    }

    public void setArchitectureComboBox(JComboBox architectureComboBox) {
        this.architectureComboBox = architectureComboBox;
    }


    public JTextField getTrainingModelTextBox() {
        return trainingModelTextBox;
    }

    public void setTrainingModelTextBox(JTextField trainingModelTextBox) {
        this.trainingModelTextBox = trainingModelTextBox;
    }


    public JTextField getTestDataTextField() {
        return testDataTextField;
    }

    public void setTestDataTextField(JTextField testDataTextField) {
        this.testDataTextField = testDataTextField;
    }

}
