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

package org.vimarsha.utils.impl;

import weka.core.Instances;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class AttributeValueDiscretizer {
    private Instances dataFile;
    private File inputArffFile;

    public AttributeValueDiscretizer(File inputArffFile) {
        this.inputArffFile = inputArffFile;
    }

    public TreeMap<String, Integer> binAttribute(int attrIndex) throws IOException, IllegalArgumentException {
        TreeMap<String, Integer> bins = new TreeMap<String, Integer>();
        dataFile = new Instances(new BufferedReader(new FileReader(inputArffFile)));
        double min = dataFile.kthSmallestValue(attrIndex, 1);
        double max = dataFile.kthSmallestValue(attrIndex, dataFile.numInstances());
        double foldsize = (max - min) / 12;
        double lowerbound, upperbound;
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder sb;
        for (int j = 0; j < 12; j++) {
            lowerbound = j * foldsize;
            upperbound = (j + 1) * foldsize;
            sb = new StringBuilder();
            sb.append(df.format(lowerbound));
            sb.append(" - ");
            sb.append(df.format(upperbound));
            bins.put(sb.toString(), 0);
        }
        if (foldsize == 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < dataFile.numInstances(); i++) {
            long bin = Math.round(Math.floor(dataFile.instance(i).value(attrIndex) / foldsize));
            lowerbound = bin * foldsize;
            upperbound = (bin + 1) * foldsize;
            sb = new StringBuilder();
            sb.append(df.format(lowerbound));
            sb.append(" - ");
            sb.append(df.format(upperbound));
            if (bins.containsKey(sb.toString())) {
                bins.put(sb.toString(), (Integer) bins.get(sb.toString()) + 1);
            } else {
                bins.put(sb.toString(), 1);
            }
        }
        return bins;
    }
}
