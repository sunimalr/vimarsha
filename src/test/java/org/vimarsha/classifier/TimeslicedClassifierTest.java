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
import org.vimarsha.classifier.impl.TimeslicedClassifier;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class TimeslicedClassifierTest extends TestCase {

    private TimeslicedClassifier cls;
    private Instances testSet;

    @Before
    public void setUp() throws Exception {
        cls = new TimeslicedClassifier();
        testSet = new Instances(new BufferedReader(new FileReader("resources/intel_nehalem_timesliced_training.arff")));
        if (testSet.classIndex() == -1)
            testSet.setClassIndex(testSet.numAttributes() - 1);
        cls.setTrainingDataSource(testSet);
        cls.setTestingDataSource("resources/timesliced_testing.arff");
    }

    @Test
    public void testClassify() throws Exception {
        LinkedList<String> res;
        res = (LinkedList<String>) cls.classify();
        assertEquals("badfs", res.get(0));
        assertEquals("badfs", res.get(1));
        assertEquals("badfs", res.get(2));
        assertEquals("badfs", res.get(3));
        assertEquals("badfs", res.get(4));
        assertEquals("badfs", res.get(5));
        assertEquals("badfs", res.get(6));
        assertEquals("good", res.get(7));
        assertEquals("good", res.get(8));
        assertEquals("good", res.get(9));
        assertEquals("good", res.get(10));
    }
}
