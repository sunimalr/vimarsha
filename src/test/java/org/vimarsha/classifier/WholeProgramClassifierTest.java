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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class WholeProgramClassifierTest extends TestCase {

    WholeProgramClassifier cls;
    Instances testSet;


    @Before
    public void setUp() throws Exception {
        cls = new WholeProgramClassifier();
        testSet = new Instances(new BufferedReader(new FileReader("resources/intel_nehalem_training.arff")));
        if (testSet.classIndex() == -1)
            testSet.setClassIndex(testSet.numAttributes() - 1);
        cls.setTrainingDataSource(testSet);
        cls.setTestingDataSource("resources/wholeprogram_testset_badfs.arff");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testClassify() throws Exception {
        assertEquals("badfs", cls.classify());
        cls.setTestingDataSource("resources/wholeprogram_testset_good.arff");
        assertEquals("good", cls.classify());
    }

}
