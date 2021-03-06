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
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class FunctionWiseClassifierForm {
    private JComboBox architectureComboBox;
    private JButton classifyButton;
    private JButton exportButton;
    private JComboBox trainingComboBox;
    private JTable functionWiseResultsTable;
    private JPanel Tab2;
    private JTextField trainingModelTextBox;
    private JTextField testDataTextField;

    public FunctionWiseClassifierForm() {

        for (String str : UIHandler.getInstance().getArchitectureList()) {
            architectureComboBox.addItem(str);
        }

        classifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().classifyFunctionWise();
                TableModel tmp = UIHandler.getInstance().getClassificationResults();
                functionWiseResultsTable.setModel(tmp);
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File file = null;
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(Tab2);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println(fc.getSelectedFile());
                    UIHandler.getInstance().exportFunctionWiseAsCSV(fc.getSelectedFile());
                }
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


    public JTextField getTestDataTextField() {
        return testDataTextField;
    }

    public void setTestDataTextField(JTextField testDataTextField) {
        this.testDataTextField = testDataTextField;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
