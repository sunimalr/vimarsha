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

package org.vimarsha.utils.impl;

import javax.swing.table.DefaultTableModel;
import java.util.LinkedHashMap;

/**
 * A class responsible for generating data needed for tables.
 *
 * @author gayashan
 */
public class TableDataGenerator {
    /**
     * Default constructor
     */
    public TableDataGenerator() {
    }

    /**
     * Returns a DefaultTableModel generated from results of function wise classification
     *
     * @param results results of function wise classification
     * @return DefaultTableModel
     */
    public DefaultTableModel getFunctionwiseTableModel(LinkedHashMap<String, String> results) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Function");
        tableModel.addColumn("Classification");
        for (String result : results.keySet()) {
            tableModel.addRow(new String[]{result, results.get(result)});
        }
        return tableModel;
    }
}
