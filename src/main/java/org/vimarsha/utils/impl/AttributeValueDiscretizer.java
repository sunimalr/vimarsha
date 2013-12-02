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
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class AttributeValueDiscretizer {

    public HashMap<Integer, Integer> binAttribute(File arffFile, int attrIndex) throws IOException {
        Instances dataFile = null;
        HashMap<Integer, Integer> bins = new HashMap<Integer, Integer>();
        dataFile = new Instances(new BufferedReader(new FileReader(arffFile)));
        double min = dataFile.kthSmallestValue(attrIndex, 1);
        double max = dataFile.kthSmallestValue(attrIndex, dataFile.numInstances());
        double foldsize = (max - min) / 10;
        for (int i = 0; i < dataFile.numInstances(); i++) {
            double tmp = dataFile.instance(i).value(attrIndex);
            long bin = Math.round(Math.floor(tmp / foldsize));
            if (bins.containsKey(i)) {
                bins.put(i, bins.get(i) + 1);
            } else {
                bins.put(i, 1);
            }
        }
        return bins;
    }
}
