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

package org.vimarsha.mediator.impl;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.vimarsha.exceptions.ArchitectureNotSetException;
import org.vimarsha.exceptions.ClassificationFailedException;
import org.vimarsha.exceptions.TimestampNotFoundException;
import org.vimarsha.exceptions.TrainingModelNotSetException;
import org.vimarsha.mediator.Mediator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class UIHandler {
    private static UIHandler uiHandlerInstance = null;

    private Mediator mediator;

    public static UIHandler getInstance() {
        if (uiHandlerInstance == null) {
            uiHandlerInstance = new UIHandler();
        }
        return uiHandlerInstance;
    }

    private UIHandler() {
        this.mediator = new DefaultMediator();
    }

    public void setArchitecture(String architecture) {
        try {
            this.mediator.setArchitecture(architecture);
        } catch (Exception ex) {
            this.showErrorDialog("Architecture Not Set!");
        }
    }

    public int setRawFile(File file) {
        try {
            return this.mediator.setRawFile(file);
        } catch (Exception ex) {
            this.showErrorDialog("Raw file not set!");
            return -1;
        }
    }

    public void setArffFile(File file) {
        try {
            this.mediator.setArffFile(file);
        } catch (IOException e) {
            this.showErrorDialog("ARFF File Not Set!");
        }
    }

    public int convertRawToArff() {
        try {
            int res = this.mediator.convertRawFileToArff();
            if (res == 100) {
                this.showInfoDialog("File Conversion Successful!");
            }
            return res;
        } catch (Exception ex) {
            this.showErrorDialog("File Conversion Error!");
            return -1;
        }
    }

    public void saveAsArff(File fileToSave) {

        try {
            this.mediator.saveArffFile(fileToSave);
        } catch (IOException e) {
            this.showErrorDialog("File Save Failed!");
        }
    }


    public ArrayList<String> getArffAttribiutesTableModel() {
        return this.mediator.getArffAttributesList();
    }

    public DefaultCategoryDataset getBarChartDataSet(String selectedEvent) {
        return this.mediator.getBarChartDataSet(selectedEvent);
    }

    public ArrayList<String> getArchitectureList() {
        return this.mediator.getArchitectureList();
    }

    public String getArchitecture() {
        try {
            return this.mediator.getArchitecture();
        } catch (ArchitectureNotSetException e) {
            this.showErrorDialog(e.getMessage());
        }
        return null;
    }


    public String getTrainingModel() {
        try {
            return this.mediator.getTrainingModel();
        } catch (TrainingModelNotSetException e) {
            this.showErrorDialog(e.getMessage());
        }
        return null;
    }

    public String getTestDataName() {
        return this.mediator.getTestDataName();
    }

    public ArrayList<String> getTrainingModels() {
        return this.mediator.getTrainingModels();
    }

    public void classifyFunctionWise() {
        try {
            this.mediator.classifyFunctionWise();
        } catch (IOException e) {
            this.showErrorDialog("Data source not set!");
        } catch (ClassificationFailedException e) {
            this.showErrorDialog(e.getMessage());
        }
    }

    public void classifyWholeProgram() {
        try {
            this.mediator.classifyWholeProgram();
        } catch (IOException e) {
            this.showErrorDialog("Data source not set!");
        } catch (ClassificationFailedException e) {
            this.showErrorDialog(e.getMessage());
        }
    }

    public ArrayList<String> getWholeProgramTestSetList() {
        return mediator.getWholeProgramTimestamps();
    }


    public void classifyTimeSliced() {
        try {
            this.mediator.classifyTimeSliced();
        } catch (IOException e) {
            this.showErrorDialog("Data source not set!");
        } catch (ClassificationFailedException e) {
            e.getMessage();
        }
    }


    public DefaultTableModel getClassificationResults() {
        return this.mediator.getFunctionWiseClassificationResultsTableModel();
    }

    public XYSeriesCollection getXYChartDataSet() {
        return this.mediator.getXYChartDataSet();
    }

    public void exportAsCSV() {
        this.mediator.exportAsCSV();
    }

    public void exportAsImage() {
        this.mediator.exportAsImage();
    }

    public DefaultTableModel getArffAttributeInfo(int attrNo) {
        return mediator.getArffAttributeInfo(attrNo);
    }

    public String getWholeProgramClassificationResult(String timestamp) {
        String tmp = null;
        try {
            tmp = mediator.getWholeProgramClassificationResult(timestamp);
        } catch (TimestampNotFoundException e) {
            this.showErrorDialog(e.getMessage());
        }
        return tmp;
    }

    private void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfoDialog(String infoMessage) {
        JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
