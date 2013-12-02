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

import org.vimarsha.exceptions.RawFileParseFailedException;
import org.vimarsha.formatter.DataParser;
import org.vimarsha.utils.impl.PerfReportDataHolder;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfReportDataParser implements DataParser {
    private BufferedReader fileReader;
    private PerfReportDataHolder perfReportDataHolder;
    private String programName;
    private boolean getAll;

    public PerfReportDataParser(BufferedReader fileReader, PerfReportDataHolder perfReportDataHolder, String programName) {
        this.fileReader = fileReader;
        this.setPerfReportDataHolder(perfReportDataHolder);
        this.programName = programName;
        this.getAll = false;
    }

    public PerfReportDataParser(BufferedReader fileReader, PerfReportDataHolder perfReportDataHolder) {
        this.fileReader = fileReader;
        this.setPerfReportDataHolder(perfReportDataHolder);
        this.getAll = true;
    }

    @Override
    public void parse() throws IOException, RawFileParseFailedException {
        String line = null;
        String rawEvent = null;
        String tmp = null;
        String symbol = null;
        float overhead = -1, interpolatedVal = -1;
        String out = "";
        int eventCount = -1;

        //TODO Move the constants to a configuration file
        while ((line = fileReader.readLine()) != null) {
            line = line.trim();
            if (line.contains("Events")) {
                rawEvent = line.split(" +")[4];     //split the line and acquire raw event name
                tmp = line.split(" +")[2];
                if (tmp.contains("K")) {              //some raw event counts are scaled in K format eg: 4K = 4000
                    eventCount = Integer.parseInt(tmp.split("K")[0]) * 1000;
                } else {
                    eventCount = Integer.parseInt(tmp);
                }
            }

            if ((!line.contains("#")) && (line.split(" +")).length != 1) {
                String[] tokens = line.split(" +");
                //tokens[1] = command which executed the function
                //tokens[2] = object which function belongs to
                //tokens[1] gives all the functions (including external libraries, kernel, etc)
                //tokens[2] gives only the functions available in the program
                if (tokens[1].equals(this.programName) || this.getAll) {
                    int size = tokens.length;
                    overhead = Float.parseFloat(tokens[0].split("%")[0]);
                    if (size <= 5) {
                        symbol = tokens[4];
                    } else {
                        int i = 4;
                        while (i < size) {
                            symbol += tokens[i];
                            ++i;
                        }
                    }
                    //interpolated value of the performance count event per function = perf count event valu x overhead%
                    interpolatedVal = (float) ((eventCount / 100.0) * overhead);
                    this.getPerfReportDataHolder().addValue(symbol, rawEvent, String.valueOf(interpolatedVal));
                } else {
                    throw new RawFileParseFailedException();
                }
            }
        }
    }

    @Override
    public void setFileReader(BufferedReader fileReader) {
        this.fileReader = fileReader;
    }

    public PerfReportDataHolder getPerfReportDataHolder() {
        return perfReportDataHolder;
    }

    public void setPerfReportDataHolder(PerfReportDataHolder perfReportDataHolder) {
        this.perfReportDataHolder = perfReportDataHolder;
    }
}
