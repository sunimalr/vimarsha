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

import org.jfree.chart.ChartPanel;
import org.vimarsha.mediator.UIHandler;

import javax.swing.*;
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
            }
        });
    }

    private void createUIComponents() {
        this.chartPanel = new ChartPanel(null);
    }
}
