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

import org.vimarsha.formatter.DataParser;
import org.vimarsha.utils.impl.PerfStatDataHolder;
import org.vimarsha.utils.impl.PerfStatTimeSlicedDataHolder;
import org.vimarsha.utils.impl.PropertiesLoader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfStatDataParser implements DataParser {
    private BufferedReader bufferedReader;
    private PerfStatDataHolder perfStatDataHolder;
    private PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder;

    public PerfStatDataParser(BufferedReader filReader, PerfStatDataHolder perfStatDataHolder) {
        this.bufferedReader = filReader;
        this.setPerfStatDataHolder(perfStatDataHolder);
    }

    public PerfStatDataParser(BufferedReader bufferedReader, PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder) {
        this.bufferedReader = bufferedReader;
        this.perfStatTimeSlicedDataHolder = perfStatTimeSlicedDataHolder;
    }

    @Override
    public void parse() throws IOException {
        String line = null;
        String rawEvent = null;
        String value = null;

        line = this.bufferedReader.readLine();  //read header
        while ((line = this.bufferedReader.readLine()) == "" || line.startsWith(PropertiesLoader.getInstance().getCommentPrefix()))
            ;

        while ((line = this.bufferedReader.readLine()) != null) {
            line = line.trim();
            String[] tokens = line.split(":");
            rawEvent = tokens[1];
            value = tokens[0];
            this.getPerfStatDataHolder().addValue(rawEvent, value);
        }
    }

    public void parseMultipleInstances() throws IOException {
        String line = null;
        String rawEvent = null;
        String value = null;
        int count = 0;

        line = this.bufferedReader.readLine(); //read header
        while ((line = this.bufferedReader.readLine()) != null) {
            if (line == "" || line.isEmpty()) continue;
            if (line.startsWith(PropertiesLoader.getInstance().getCommentPrefix())) {
                ++count;
                continue;
            }
            line = line.trim();
            String[] tokens = line.split(":");
            rawEvent = tokens[1];
            value = tokens[0];
            this.perfStatTimeSlicedDataHolder.addValue(String.valueOf(count), rawEvent, value);
        }
    }

    @Override
    public void setFileReader(BufferedReader fileReader) {
        this.bufferedReader = fileReader;
    }

    public PerfStatDataHolder getPerfStatDataHolder() {
        return perfStatDataHolder;
    }

    public void setPerfStatDataHolder(PerfStatDataHolder perfStatDataHolder) {
        this.perfStatDataHolder = perfStatDataHolder;
    }

    public PerfStatTimeSlicedDataHolder getPerfStatTimeSlicedDataHolder() {
        return perfStatTimeSlicedDataHolder;
    }

    public void setPerfStatTimeSlicedDataHolder(PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder) {
        this.perfStatTimeSlicedDataHolder = perfStatTimeSlicedDataHolder;
    }

}
