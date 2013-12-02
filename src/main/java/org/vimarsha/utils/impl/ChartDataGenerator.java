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

package org.vimarsha.utils.impl;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class ChartDataGenerator {
    public ChartDataGenerator() {
    }

    public XYSeriesCollection getTimeSlicedChartDataSet(LinkedList<String> results, String chartName) {
        XYSeries series = new XYSeries(chartName);
        int count = 0;
        for (String result : results) {
            if (result.equalsIgnoreCase("badfs")) {
                series.add(++count, 1);
            } else if (result.equalsIgnoreCase("badma")) {
                series.add(++count, 0.5);
            } else {
                series.add(++count, 0);
            }
        }
        return new XYSeriesCollection(series);
    }
}
