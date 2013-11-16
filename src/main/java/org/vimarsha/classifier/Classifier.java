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

package org.vimarsha.classifier;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 * Date: 11/16/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */

public interface Classifier {

    public void setTrainingDataSource(String dataSource);
    public void setTestingDataSource(String dataSource);
    public String classify();
}
