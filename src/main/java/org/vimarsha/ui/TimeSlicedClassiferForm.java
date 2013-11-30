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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.vimarsha.mediator.UIHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class TimeSlicedClassiferForm {
    private JPanel Tab3;
    private JComboBox archComboBox;
    private JButton classifyButton;
    private JButton exportButton;
    private ChartPanel chartPanel;
    private JPanel timeSlicedGraph;

    public TimeSlicedClassiferForm() {

        for (String str : UIHandler.getInstance().getArchitectureList()) {
            archComboBox.addItem(str);
        }

        archComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().setArchitecture((String) archComboBox.getSelectedItem());
            }
        });

        classifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().classifyTimeSliced();
                XYSeriesCollection data = UIHandler.getInstance().getXYChartDataSet();
                displayTimeSlicedChart(data);
            }
        });
    }

    private void displayTimeSlicedChart(XYSeriesCollection dataSet) {
        JFreeChart chart = createChart(dataSet,"Time sliced classification results");
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
        return chart;
    }

    private void createUIComponents() {
        this.chartPanel = new ChartPanel(null);
    }
}
