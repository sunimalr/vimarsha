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

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * A class which returns a chart data set when a result set is passed.
 *
 * @author gayashan
 */
public class ChartDataGenerator {
    /**
     * Default constructor.
     */
    public ChartDataGenerator() {
    }

    /**
     * Returns an XYSeriesCollection when the a list of results are provided.
     * X axis is an auto incremented numeric value.
     * Y axis contains provided results.
     *
     * @param results   results of time sliced classification
     * @param chartName name for the chart
     * @return XYSeriesCollection
     */
    public XYSeriesCollection getTimeSlicedChartDataSet(LinkedList<String> results, String chartName) {
        XYSeries series = new XYSeries(chartName);
        int count = 0;
        for (String result : results) {
            if (result.equalsIgnoreCase(PropertiesLoader.getInstance().getBADFSClass())) {  //badfs = 1
                series.add(++count, 1);
            } else if (result.equalsIgnoreCase(PropertiesLoader.getInstance().getBADMAClass())) {   //badma = 0.5
                series.add(++count, 0.5);
            } else {    //good = 0
                series.add(++count, 0);
            }
        }
        return new XYSeriesCollection(series);
    }

    /**
     * Returns a DefaultCategoryDataset when a collection of key-value pairs are provided.
     * X axis is the key.
     * Y axis is the value.
     *
     * @param results     TreeMap of attribute value counts
     * @param binnedEvent attribute name
     * @return DefaultCategoryDataset
     */
    public DefaultCategoryDataset getBinnedChartDataSet(LinkedHashMap<String, Integer> results, String binnedEvent) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : results.keySet()) {
            dataset.setValue(results.get(key), binnedEvent, key);
        }
        return dataset;
    }
}
