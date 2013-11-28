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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class WholeProgramClassifierForm {
    private JPanel Tab1;
    private JComboBox architectureComboBox;
    private JButton classifyButton;
    private JTextPane classificationTextPane;
    private JComboBox modelComboBox;
    private JList testDataSetList;

    public WholeProgramClassifierForm() {

        for (String str : UIHandler.getInstance().getArchitectureList()) {
            architectureComboBox.addItem(str);
        }

        architectureComboBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                System.out.println("focus");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        architectureComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().setArchitecture((String) architectureComboBox.getSelectedItem());
            }
        });

        classifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().classifyWholeProgram();
                testDataSetList.setListData(UIHandler.getInstance().getWholeProgramTestSetList().toArray());
            }
        });

        testDataSetList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                classificationTextPane.setText(UIHandler.getInstance().getWholeProgramClassificationResult((String) testDataSetList.getSelectedValue()));
            }
        });
    }

    public Component getCombobox() {
        return this.architectureComboBox;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
