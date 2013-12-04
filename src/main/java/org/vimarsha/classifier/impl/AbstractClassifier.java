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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */

abstract class AbstractClassifier {

    protected Instances testSet;
    protected Instances trainSet;

    /**
     * Sets the data set with training instances.
     *
     * @param dataSource - Weka Instances object that contains training instances.
     */
    public void setTrainingDataSource(Instances dataSource) {
        this.trainSet = dataSource;
    }

    /**
     * Sets the data set that need to be evaluated. (Test set)
     *
     * @param dataSource - Weka Instances object that contains training instances.
     * @throws IOException
     */
    public void setTestingDataSource(String dataSource) throws IOException {
        this.testSet = new Instances(new BufferedReader(new FileReader(dataSource)));
        if (testSet.classIndex() == -1)
            testSet.setClassIndex(testSet.numAttributes() - 1);
    }

    /**
     * Classifies test instances with the classifier trained with training data set.
     *
     * @return Classification results
     * @throws ClassificationFailedException
     */
    public Object classify() throws ClassificationFailedException {
        return null;
    }

    /**
     * Classifies test instances in the associated with the names labels mentioned in the arraylist passed as the argument.
     *
     * @param list - labels of instances contained in the test set that need to be classified.
     * @return TreeMap containing the instance labels and the associated classification results.
     * @throws ClassificationFailedException
     */
    public TreeMap<String, String> classify(LinkedList<String> list) throws ClassificationFailedException {
        return null;
    }

    public abstract Object getClassificationResult();
}
