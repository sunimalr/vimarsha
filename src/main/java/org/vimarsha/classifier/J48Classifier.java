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

package org.vimarsha.classifier;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class J48Classifier implements Classifier {

    private Instances testSet;
    private Instances trainSet;
    private DataSource trainingDataSource;
    private DataSource testingDataSource;

    public J48Classifier() {
    }

    @Override
    public void setTrainingDataSource(String dataSource) {
        try {
            this.trainingDataSource = new DataSource(dataSource);
            trainSet = trainingDataSource.getDataSet();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public void setTestingDataSource(String dataSource) {
        try {
            this.testingDataSource = new DataSource(dataSource);
            testSet = testingDataSource.getDataSet();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }


    @Override
    public String classify() {
        String[] options = new String[4];
        options[0] = "-C";
        options[1] = "0.25";
        options[2] = "-M";
        options[3] = "2";
        J48 tree = new J48();
        String output = null;

        try {
            tree.setOptions(options);
            tree.buildClassifier(trainSet);
            Evaluation eval = new Evaluation(trainSet);
            eval.evaluateModel(tree, testSet);
            output = eval.toSummaryString("\nResults\n======\n", false);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return output;
    }
}
