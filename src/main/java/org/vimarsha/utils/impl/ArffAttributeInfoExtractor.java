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

package org.vimarsha.utils.impl;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A general class that extracts attribute information from an arff file when the file and
 * architecture details are provided.
 *
 * @author gayashan
 */
public class ArffAttributeInfoExtractor {
    private ArffLoader.ArffReader arffReader;
    private Instances arffData;
    private Instances arffStructure;
    private PerformanceEventsHolder performanceEventsHolder;

    /**
     * Creates a new arff attribute extractor.
     *
     * @param fileToOpen              File to open
     * @param performanceEventsHolder PerformanceEventsHolder
     * @throws IOException
     */
    public ArffAttributeInfoExtractor(File fileToOpen, PerformanceEventsHolder performanceEventsHolder) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileToOpen));
        this.arffReader = new ArffLoader.ArffReader(reader);
        this.performanceEventsHolder = performanceEventsHolder;
        this.arffData = this.arffReader.getData();
        this.arffStructure = this.arffReader.getStructure();
        reader.close();
    }

    /**
     * Returns the performance events list in the arff file.
     *
     * @param rawfileconverted Whether a raw file was provided or an arff file was provided
     * @return ArrayList
     */
    public ArrayList<String> getPerformanceEventsList(boolean rawfileconverted) {
        if (!rawfileconverted) {
            ArrayList<String> events = new ArrayList<String>();
            for (int i = 0; i < this.arffData.numAttributes(); i++) {
                events.add(this.arffStructure.attribute(i).name());
            }
            return events;
        } else {
            return this.performanceEventsHolder.getPrettyEventsHolder();
        }
    }

    /**
     * Returns a table model with the attribute related info when the selected attribute index is passed.
     *
     * @param index index of the attribute
     * @return DefaultTableModel
     */
    public DefaultTableModel getArffAttributeInfo(int index) {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        ArrayList<String> tmp = new ArrayList<String>();
        defaultTableModel.addColumn("Statistics", new String[]{"Name", "Variance", "Min", "Max", "Mean"});
        tmp.add(this.arffData.attribute(index).name());
        tmp.add(String.valueOf(this.arffData.variance(index)));
        tmp.add(String.valueOf(this.arffData.kthSmallestValue(index, 1)));          //min value is the 1st smallest value
        tmp.add(String.valueOf(this.arffData.kthSmallestValue(index, this.arffData.numInstances())));       //max value is the last smallest value
        tmp.add(String.valueOf(this.arffData.meanOrMode(index)));
        defaultTableModel.addColumn("Value", tmp.toArray());
        return defaultTableModel;
    }

    /**
     * Returns the number of instances in the arff file.
     *
     * @return int
     */
    public int getNumberOfInstances() {
        return this.arffData.numInstances();
    }
}
