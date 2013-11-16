package org.vimarsha.classifier;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 * Date: 11/16/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
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
