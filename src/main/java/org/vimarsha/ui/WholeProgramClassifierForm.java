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

import org.vimarsha.mediator.impl.UIHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class WholeProgramClassifierForm {
    private JPanel Tab1;
    private JComboBox architectureComboBox;
    private JButton classifyButton;
    private JTextPane classificationTextPane;
    private JList testDataSetList;
    private JTextField trainingModelTextBox;

    public WholeProgramClassifierForm() {

        for (String str : UIHandler.getInstance().getArchitectureList()) {
            architectureComboBox.addItem(str);
        }

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


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
