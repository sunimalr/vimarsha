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

package org.vimarsha.formatter.impl;

import org.vimarsha.formatter.OutputWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class which can be used to create arff files.
 *
 * @author gayashan
 */
public class ArffWriter implements OutputWriter {
    private String fileName;
    private BufferedWriter bufferedWriter;
    private ArrayList<String> headers;

    /**
     * Creates an ArffWriter instance. Opens the file and writes the header to the file.
     *
     * @param fileName arff file name
     * @param headers  header string
     * @throws IOException thrown when file is not found
     */
    public ArffWriter(String fileName, ArrayList<String> headers) throws IOException {
        this.fileName = fileName;
        this.headers = headers;
        bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        this.writeLines(headers);
    }

    /**
     * Write the given line to the file.
     *
     * @param stringToWrite string to write
     * @throws IOException
     */
    @Override
    public void write(String stringToWrite) throws IOException {
        bufferedWriter.write(stringToWrite);
        bufferedWriter.newLine();
    }

    /**
     * Write given list of lines to the file.
     *
     * @param arrayList list of lines
     * @throws IOException
     */
    @Override
    public void writeLines(ArrayList<String> arrayList) throws IOException {
        for (String line : arrayList) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
    }

    /**
     * Close the file.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        bufferedWriter.close();
    }
}
