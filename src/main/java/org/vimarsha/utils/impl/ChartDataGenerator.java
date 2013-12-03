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

import java.util.LinkedList;
import java.util.TreeMap;

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
            if (result.equalsIgnoreCase(PropertiesLoader.getInstance().getBADFSClass())) {
                series.add(++count, 1);
            } else if (result.equalsIgnoreCase(PropertiesLoader.getInstance().getBADMAClass())) {
                series.add(++count, 0.5);
            } else {
                series.add(++count, 0);
            }
        }
        return new XYSeriesCollection(series);
    }

    public DefaultCategoryDataset getBinnedChartDataSet(TreeMap<String, Integer> results, String binnedEvent) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : results.keySet()) {
            dataset.setValue(results.get(key), binnedEvent, key);
        }
//        dataset.setValue(30, binnedEvent, "1");
//        dataset.setValue(7, binnedEvent, "2");
//        dataset.setValue(8, binnedEvent, "3");
//        dataset.setValue(5, binnedEvent, "4");
//        dataset.setValue(12, binnedEvent, "5");
        return dataset;
    }
}
