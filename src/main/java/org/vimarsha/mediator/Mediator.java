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

package org.vimarsha.mediator;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public interface Mediator {
    public int setFile(File fileToOpen);
    public int setArchitecture(String architecture);
    public int convertRawFileToArff();
    public int saveArffFile(File fileToSave);
    //Dataset needed to populate jtable instances
    public DefaultTableModel getTableModel();
    //Dataset needed to create bar charts
    public DefaultCategoryDataset getBarChartDataSet();

    public ArrayList<String> getArchitectureList();
    public ArrayList<String> getTrainingModels();
    public int classify();
    public DefaultTableModel getClassificationResults();

    //Dataset needed to create time sliced chart
    public XYSeriesCollection getXYChartDataSet();

    public int exportAsCSV();
    public int exportAsImage();
}
