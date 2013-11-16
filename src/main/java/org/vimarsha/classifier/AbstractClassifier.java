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

package org.vimarsha.classifier;

import org.vimarsha.exceptions.ClassificationFailedException;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */

abstract class AbstractClassifier {

    protected Instances testSet;
    protected Instances trainSet;
    protected ConverterUtils.DataSource trainingDataSource;
    protected ConverterUtils.DataSource testingDataSource;

    public void setTrainingDataSource(String dataSource) {
        try {
            this.trainingDataSource = new ConverterUtils.DataSource(dataSource);
            trainSet = trainingDataSource.getDataSet();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void setTestingDataSource(String dataSource) {
        try {
            this.testingDataSource = new ConverterUtils.DataSource(dataSource);
            testSet = testingDataSource.getDataSet();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public String classify() throws ClassificationFailedException {
        return null;
    }

    public HashMap<String,String> classify(ArrayList<String> list) throws ClassificationFailedException {
        return null;
    }
}
