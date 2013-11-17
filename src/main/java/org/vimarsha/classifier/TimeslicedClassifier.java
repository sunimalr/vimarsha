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
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class TimeslicedClassifier extends AbstractClassifier {
    public TimeslicedClassifier() {
        super();
    }

    @Override
    public HashMap<String,String> classify(ArrayList<String> list) throws ClassificationFailedException {
        String[] options = new String[4];
        options[0] = "-C";
        options[1] = "0.25";
        options[2] = "-M";
        options[3] = "2";
        J48 tree = new J48();
        FastVector<String> predictions = null;

        try {
            tree.setOptions(options);
            tree.buildClassifier(trainSet);
            Evaluation eval = new Evaluation(trainSet);
            eval.evaluateModel(tree, testSet);
            predictions = eval.predictions();
        } catch (Exception ex) {
            throw new ClassificationFailedException();
        }

        HashMap<String,String> functionWiseResult=new HashMap<String, String>();

        if(predictions!=null){
            int index=0;
            for (String prediction : predictions) {
                functionWiseResult.put(Integer.toString(index),prediction);
                index++;
            }
            return functionWiseResult;
        }else{
            return null;
        }
    }
}
