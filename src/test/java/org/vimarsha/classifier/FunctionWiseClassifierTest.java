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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class FunctionWiseClassifierTest extends TestCase {

    private FunctionWiseClassifier cls;

    @Before
    public void setUp() throws Exception {
        cls = new FunctionWiseClassifier();
        cls.setTrainingDataSource("resources/intel_nehalem_training.arff");
        cls.setTestingDataSource("resources/functionwise_test.arff");
    }

    @Test
    public void testClassify() throws Exception {
        ArrayList<String> fucntionlist = new ArrayList<String>();
        fucntionlist.add("pspeedy(Points*,float,long*,int,parsec_barrier_t*)");
        fucntionlist.add("dist(Point,Point,int)");
        HashMap<String, String> res = cls.classify(fucntionlist);
        String tmp = fucntionlist.remove(0);
        assertEquals("badfs", res.get(tmp));
        tmp = fucntionlist.remove(0);
        assertEquals("badma", res.get(tmp));
    }
}

