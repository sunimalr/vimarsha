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
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.filters.unsupervised.attribute.Remove;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class FunctionWiseClassifier extends AbstractClassifier {

    private HashMap<String, String> output;

    public FunctionWiseClassifier() {
        super();
    }

    @Override
    public HashMap<String, String> classify(ArrayList<String> list) throws ClassificationFailedException {
        output = new HashMap<String, String>();
        J48 j48 = new J48();
        Remove rm = new Remove();
        rm.setAttributeIndices("1");
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(rm);
        fc.setClassifier(j48);
        try {
            fc.buildClassifier(trainSet);
            for (int i = 0; i < testSet.numInstances(); i++) {
                double pred = fc.classifyInstance(testSet.instance(i));
                output.put(list.get(i), testSet.classAttribute().value((int) pred));
            }
        } catch (Exception ex) {
            throw new ClassificationFailedException();
        }
        return output;
    }

    @Override
    public Object getClassificationResult() {
        return output;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
