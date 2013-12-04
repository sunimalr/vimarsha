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

import au.com.bytecode.opencsv.CSVWriter;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * A class which can be used to create output files of different formats.
 *
 * @author gayashan
 */
public class FileHandler {
    private FileChannel sourceFileChannel;
    private FileChannel destinationFileChannel;
    private CSVWriter csvWriter;

    /**
     * Default constructor.
     */
    public FileHandler() {
        this.destinationFileChannel = null;
        this.sourceFileChannel = null;
    }

    /**
     * Copy a given arff file to a given destination.
     *
     * @param source      source file
     * @param destination destination
     * @throws IOException
     */
    public void copy(File source, String destination) throws IOException {
        Instances instances = new Instances(new BufferedReader(new FileReader(source)));
        ArffSaver arffSaver = new ArffSaver();
        arffSaver.setInstances(instances);
        arffSaver.setFile(new File(destination));
        arffSaver.writeBatch();
    }

    /**
     * Export the function wise results to a csv file at a given destination.
     * 1st column : function name
     * 2nd column : classification
     *
     * @param results     results from function wise classification
     * @param destination destination
     * @throws IOException
     */
    public void exportFunctionWiseResultsAsCSV(TreeMap<String, String> results, String destination) throws IOException {
        this.csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(destination)));
        String[] header = new String[]{"Function", "Classsification"};
        ArrayList<String[]> lines = new ArrayList<String[]>();
        lines.add(header);
        for (String key : results.keySet()) {
            lines.add(new String[]{key, results.get(key)});
        }
        this.csvWriter.writeAll(lines);
        this.csvWriter.flush();
    }

    /**
     * Export the time sliced results to a csv file at a given destination.
     * 1st column : time slice number
     * 2nd column : classification
     *
     * @param results     results from time sliced classification
     * @param destination destination
     * @throws IOException
     */
    public void exportTimeSlicedResultsAsCSV(LinkedList<String> results, String destination) throws IOException {
        this.csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(destination)));
        String[] header = new String[]{"Time slice no", "Classification"};
        ArrayList<String[]> lines = new ArrayList<String[]>();
        lines.add(header);
        int count = 0;
        for (String result : results) {
            lines.add(new String[]{String.valueOf(++count), result});
        }
        this.csvWriter.writeAll(lines);
        this.csvWriter.flush();
    }
}
