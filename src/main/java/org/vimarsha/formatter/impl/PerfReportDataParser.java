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
import org.vimarsha.utils.impl.PropertiesLoader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A class which can be used to parse and extract event count values from a perf report output file.
 *
 * @author gayashan
 */
public class PerfReportDataParser implements DataParser {
    private BufferedReader fileReader;
    private PerfReportDataHolder perfReportDataHolder;
    private String programName;
    private boolean getAll;

    /**
     * Construct a PerfReportDataParser
     *
     * @param fileReader           a buffered file reader
     * @param perfReportDataHolder a PerfReportDataHolder
     * @param programName          the programName
     */
    public PerfReportDataParser(BufferedReader fileReader, PerfReportDataHolder perfReportDataHolder, String programName) {
        this.fileReader = fileReader;
        this.setPerfReportDataHolder(perfReportDataHolder);
        this.programName = programName;
        this.getAll = false;
    }

    /**
     * Construct a PerfReportDataParser
     *
     * @param fileReader           a buffered file reader
     * @param perfReportDataHolder a PerfReportDataHolder
     */
    public PerfReportDataParser(BufferedReader fileReader, PerfReportDataHolder perfReportDataHolder) {
        this.fileReader = fileReader;
        this.setPerfReportDataHolder(perfReportDataHolder);
        this.getAll = true;
    }

    /**
     * Parse the output file and extract the performance event count values
     *
     * @throws IOException
     * @throws RawFileParseFailedException
     */
    @Override
    public void parse() throws IOException, RawFileParseFailedException {
        String line = null;
        String rawEvent = null;
        String tmp = null;
        StringBuilder symbol = null;
        float overhead = -1, interpolatedVal = -1;
        int eventCount = -1;

        while ((line = fileReader.readLine()) != null) {
            line = line.trim();
            //event count per each function is detected by looking for event line detector string in each commented line
            //eg: # Events: 235  raw 0xc0
            if (line.contains(PropertiesLoader.getInstance().getEventLineDetector())) {
                rawEvent = line.split(PropertiesLoader.getInstance().getSpacesRegex())[4];     //split the line and acquire raw event name
                tmp = line.split(PropertiesLoader.getInstance().getSpacesRegex())[2];
                if (tmp.contains(PropertiesLoader.getInstance().getThousandSuffix())) {              //some raw event counts are scaled in K format eg: 4K = 4000
                    eventCount = Integer.parseInt(tmp.split(PropertiesLoader.getInstance().getThousandSuffix())[0]) * 1000;
                } else {
                    eventCount = Integer.parseInt(tmp);
                }
            }

            //if the line is not a comment and if it is not an empty line
            if ((!line.contains(PropertiesLoader.getInstance().getCommentPrefix())) && (line.split(PropertiesLoader.getInstance().getSeparatorRegex())).length != 1) {
                String[] tokens = line.split(PropertiesLoader.getInstance().getSeparatorRegex());
                //tokens[1] = command which executed the function
                //tokens[2] = object which function belongs to
                //tokens[1] gives all the functions (including external libraries, kernel, etc)
                //tokens[2] gives only the functions available in the program
                if (tokens[1].equals(this.programName) || this.getAll) {
                    int size = tokens.length;
                    overhead = Float.parseFloat(tokens[0]);
                    symbol = new StringBuilder(tokens[3].split(PropertiesLoader.getInstance().getSpacesRegex())[1]);
                    //interpolated value of the performance count event per function = perf count event valu x overhead%
                    interpolatedVal = (float) ((eventCount / 100.0) * overhead);
                    this.getPerfReportDataHolder().addValue(symbol.toString(), rawEvent, String.valueOf(interpolatedVal));
                } else {
                    throw new RawFileParseFailedException();
                }
            }
        }
    }

    /**
     * Set the file reader.
     *
     * @param fileReader a BufferedReader
     */
    @Override
    public void setFileReader(BufferedReader fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Returns the PerfReportDataHolder which is generated after parsing
     *
     * @return PerfReportDataHolder - contains the extracted performance event count values
     */
    public PerfReportDataHolder getPerfReportDataHolder() {
        return perfReportDataHolder;
    }

    /**
     * Set the PerfReportDataHolder
     *
     * @param perfReportDataHolder a PerfReportDataHolder
     */
    public void setPerfReportDataHolder(PerfReportDataHolder perfReportDataHolder) {
        this.perfReportDataHolder = perfReportDataHolder;
    }
}
