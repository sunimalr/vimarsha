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

package org.vimarsha.mediator;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class UIHandler {
    private static UIHandler uiHandlerInstance = null;

    private Mediator mediator;

    public static UIHandler getInstance() {
        if(uiHandlerInstance == null){
            uiHandlerInstance=new UIHandler();
        }
        return uiHandlerInstance;
    }

    private UIHandler() {
    }

    private void setArchitecture(String architecture){
        try{
            this.mediator.setArchitecture(architecture);
        }catch (Exception ex){
            this.showErrorDialog("Architecture Not Set!");
        }
    }

    private void setRawFile(File file){
        try{
            this.mediator.setRawFile(file);
        }catch (Exception ex){
            this.showErrorDialog("Raw file not set!");
        }
    }

    private void setArffFile(File file){
        this.mediator.setArffFile(file);
    }

    private void convertRawToArff(){
        this.mediator.convertRawFileToArff();
    }

    private void saveAsArff(File fileToSave){
        this.mediator.saveArffFile(fileToSave);
    }

    private DefaultTableModel getTableModel(){
        return this.mediator.getTableModel();
    }

    private DefaultCategoryDataset getBarChartDataSet(){
        return  this.mediator.getBarChartDataSet();
    }

    private ArrayList<String> getArchitectureList(){
        return this.mediator.getArchitectureList();
    }

    private ArrayList<String> getTrainingModels(){
        return this.mediator.getTrainingModels();
    }

    private void classify(){
        this.mediator.classify();
    }

    private DefaultTableModel getClassificationResults(){
        return this.mediator.getClassificationResults();
    }

    private XYSeriesCollection getXYChartDataSet(){
        return this.mediator.getXYChartDataSet();
    }

    private void exportAsCSV(){
        this.mediator.exportAsCSV();
    }

    private void exportAsImage(){
        this.mediator.exportAsImage();
    }

    private void showErrorDialog(String errorMessage){
        JOptionPane.showMessageDialog(null,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
    }

    private void showInfoDialog(String infoMessage){
        JOptionPane.showMessageDialog(null,infoMessage,"Error",JOptionPane.INFORMATION_MESSAGE);
    }
}
