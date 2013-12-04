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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

/**
 * Class that bins data in a testset that belongs to a particular attribute
 *
 * @author sunimal
 */
public class AttributeValueDiscretizer {
    private Instances dataFile;
    private File inputArffFile;

    /**
     * Constructs an AttributeValueDiscretizer with a given ARFF file.
     *
     * @param inputArffFile - ARFF file in which the attributes that need be binned exist
     */
    public AttributeValueDiscretizer(File inputArffFile) {
        this.inputArffFile = inputArffFile;
    }

    /**
     * Bins specified attribute data of all instances in the test data file.
     *
     * @param attrIndex - index of the selected attibute
     * @return LinkedHashMap containing bins' data
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public LinkedHashMap<String, Integer> binAttribute(int attrIndex) throws IOException, IllegalArgumentException {
        LinkedHashMap<String, Integer> bins = new LinkedHashMap<String, Integer>();
        dataFile = new Instances(new BufferedReader(new FileReader(inputArffFile)));
        //since kthSmallestValue cannot handle missing values, they need to be removed.
        dataFile.deleteWithMissing(attrIndex);
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
