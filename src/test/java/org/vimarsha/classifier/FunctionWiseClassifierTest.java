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

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.vimarsha.classifier.impl.FunctionWiseClassifier;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class FunctionWiseClassifierTest extends TestCase {

    private FunctionWiseClassifier cls;
    private Instances testSet;

    @Before
    public void setUp() throws Exception {
        cls = new FunctionWiseClassifier();
        testSet = new Instances(new BufferedReader(new FileReader("resources/intel_nehalem_training.arff")));
        if (testSet.classIndex() == -1)
            testSet.setClassIndex(testSet.numAttributes() - 1);
        cls.setTrainingDataSource(testSet);
        cls.setTestingDataSource("resources/functionwise_test.arff");
    }

    @Test
    public void testClassify() throws Exception {
        LinkedList<String> fucntionlist = new LinkedList<String>();
        fucntionlist.add("pspeedy(Points*,float,long*,int,parsec_barrier_t*)");
        fucntionlist.add("dist(Point,Point,int)");
        LinkedHashMap<String, String> res = cls.classify(fucntionlist);
        String tmp = fucntionlist.remove(0);
        assertEquals("badfs", res.get(tmp));
        tmp = fucntionlist.remove(0);
        assertEquals("badma", res.get(tmp));
    }
}

