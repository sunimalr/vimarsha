package org.vimarsha.classifier;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
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

    public String classify() {
        return null;
    }

    public HashMap<String,String> classify(ArrayList<String> list){
        return null;
    }
}
