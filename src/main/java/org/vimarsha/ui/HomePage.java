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

package org.vimarsha.ui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class HomePage {
    private JPanel panel1;
    private JTabbedPane homeTabbedPane;
    private DataLoaderForm dataLoaderForm1;
    private WholeProgramClassifierForm wholeProgramClassifierForm1;
    private FunctionWiseClassifierForm functionWiseClassifierForm1;
    private TimeSlicedClassiferForm timeSlicedClassiferForm1;
    private JPanel dataLoaderPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomePage");
        frame.setContentPane(new HomePage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
