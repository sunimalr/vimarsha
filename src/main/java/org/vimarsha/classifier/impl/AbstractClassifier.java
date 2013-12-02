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

package org.vimarsha.classifier.impl;

import org.vimarsha.exceptions.ClassificationFailedException;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */

abstract class AbstractClassifier {

    protected Instances testSet;
    protected Instances trainSet;
    protected ConverterUtils.DataSource trainingDataSource;
    protected ConverterUtils.DataSource testingDataSource;

    public void setTrainingDataSource(Instances dataSource) {
        this.trainSet = dataSource;
    }

    public void setTestingDataSource(String dataSource) throws IOException {
        this.testSet = new Instances(new BufferedReader(new FileReader(dataSource)));
        if (testSet.classIndex() == -1)
            testSet.setClassIndex(testSet.numAttributes() - 1);
    }

    public Object classify() throws ClassificationFailedException {
        return null;
    }

    public TreeMap<String, String> classify(ArrayList<String> list) throws ClassificationFailedException {
        return null;
    }

    public abstract Object getClassificationResult();
}
