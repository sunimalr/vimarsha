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

import org.vimarsha.mediator.impl.UIHandler;
import org.vimarsha.utils.impl.PropertiesLoader;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class HomePage {
    private JPanel panel1;
    private JTabbedPane homeTabbedPane;
    private DataLoaderForm dataLoaderForm1;
    private WholeProgramClassifierForm wholeProgramClassifierForm1;
    private FunctionWiseClassifierForm functionWiseClassifierForm1;
    private TimeSlicedClassiferForm timeSlicedClassiferForm1;
    private JPanel dataLoaderPanel;

    public HomePage() {
        JFrame frame = new JFrame(PropertiesLoader.getInstance().getProgramName());
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        homeTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                wholeProgramClassifierForm1.getArchitectureComboBox().setSelectedItem(UIHandler.getInstance().getArchitecture());
                functionWiseClassifierForm1.getArchitectureComboBox().setSelectedItem(UIHandler.getInstance().getArchitecture());
                timeSlicedClassiferForm1.getArchitectureComboBox().setSelectedItem(UIHandler.getInstance().getArchitecture());
                wholeProgramClassifierForm1.getTrainingModelTextBox().setText(UIHandler.getInstance().getTrainingModel());
                functionWiseClassifierForm1.getTrainingModelTextBox().setText(UIHandler.getInstance().getTrainingModel());
                timeSlicedClassiferForm1.getTrainingModelTextBox().setText(UIHandler.getInstance().getTrainingModel());
                wholeProgramClassifierForm1.getTestDataTextField().setText(UIHandler.getInstance().getTestDataName());
                functionWiseClassifierForm1.getTestDataTextField().setText(UIHandler.getInstance().getTestDataName());
                timeSlicedClassiferForm1.getTestDataTextField().setText(UIHandler.getInstance().getTestDataName());
            }
        });
    }
}
