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

package org.vimarsha.classifier.impl;

import org.vimarsha.exceptions.ClassificationFailedException;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.filters.unsupervised.attribute.Remove;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class WholeProgramClassifier extends AbstractClassifier {

    private String classificationResult;
    private String treeModel;

    public WholeProgramClassifier() {
        super();
    }

    /**
     * Classifies whole program test instances,
     *
     * @return String containing the classification result of the evaluated program's dataset.
     * @throws ClassificationFailedException
     */
    @Override
    public Object classify() throws ClassificationFailedException {
        J48 j48 = new J48();
        Remove rm = new Remove();
        String output = null;
        rm.setAttributeIndices("1");
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(rm);
        fc.setClassifier(j48);
        try {
            fc.buildClassifier(trainSet);
            this.treeModel = j48.toString();
            double pred = fc.classifyInstance(testSet.instance(0));
            output = testSet.classAttribute().value((int) pred);
            classificationResult = output;
        } catch (Exception ex) {
            throw new ClassificationFailedException();
        }
        return output;
    }

    /**
     * Get the resulting String of a previously evaluated dataset.
     *
     * @return
     */
    @Override
    public Object getClassificationResult() {
        return classificationResult;
    }

    public String getTreeModel() {
        return this.treeModel;
    }
}
