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

/**
 * A class which holds data generated from parsing the output of perf stat tool in time sliced data collection.
 *
 * @author gayashan
 */
public class PerfStatTimeSlicedDataHolder extends PerfReportDataHolder {
    /**
     * Default constructor
     */
    public PerfStatTimeSlicedDataHolder() {
        super();
    }
}
