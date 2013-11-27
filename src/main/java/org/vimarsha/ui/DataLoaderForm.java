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

import org.jfree.chart.ChartPanel;
import org.vimarsha.mediator.UIHandler;

import javax.swing.*;
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
    private JButton convertToARFFFileButton;
    private JButton saveToARFFFileButton;
    private JLabel fileNameLabel;
    private JTable table1;
    private JTable table2;
    private ChartPanel chartPanel1;
    private JComboBox archComboBox;
    private JTable attributesTable;
    private JTable attributesSummaryTable;
    private ChartPanel attributeDetailsChart;

    public DataLoaderForm() {

        for(String str : UIHandler.getInstance().getArchitectureList()){
            archComboBox.addItem(str);
        }

        archComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().setArchitecture((String)archComboBox.getSelectedItem());
            }
        });
        openRAWFileButton.addActionListener(new ActionListener() {
            File file=null;
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc=new JFileChooser();
                int returnVal=fc.showOpenDialog(Tab0);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }
                if(UIHandler.getInstance().setRawFile(file)==100){
                    convertToARFFFileButton.setEnabled(true);
                }

            }
        });
        openARFFFileButton.addActionListener(new ActionListener() {
            File file=null;
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc=new JFileChooser();
                int returnVal=fc.showOpenDialog(Tab0);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }
                UIHandler.getInstance().setArffFile(file);
            }
        });
        convertToARFFFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIHandler.getInstance().convertRawToArff();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        attributeDetailsChart = new ChartPanel(null);
    }
}
