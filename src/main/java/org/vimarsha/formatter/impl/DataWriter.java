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

import org.vimarsha.exceptions.InstructionCountNotSetException;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.formatter.OutputWriter;
import org.vimarsha.utils.impl.PerformanceEventsHolder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
abstract class DataWriter {
    protected ArffWriter arffWriter;
    protected ArrayList<String> headers;
    protected PerformanceEventsHolder performanceEventsHolder;
    protected OutputWriter outputWriter;

    public DataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder) throws IOException {
        this.headers = new ArrayList<String>();
        this.performanceEventsHolder = performanceEventsHolder;
        this.headers = generateHeaders(this.performanceEventsHolder);
        this.arffWriter = new ArffWriter(fileName, this.headers);
    }

    public abstract void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException;

    public void setOutputWriter(OutputWriter outputWriter) {
        this.arffWriter = (ArffWriter) outputWriter;
    }

    public void setEventsHolder(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
    }

    public ArrayList<String> generateHeaders(PerformanceEventsHolder performanceEventsHolder) {
        ArrayList<String> headers = new ArrayList<String>();
        headers.add("@relation badfs_badma_good_events_" + performanceEventsHolder.getArchitecture().toString());
        for (String event : performanceEventsHolder.getPrettyEventsHolder()) {
            headers.add("@attribute " + event + " numeric");
        }
        headers.add("@attribute status {good, badfs, badma}");
        headers.add("@data");
        return headers;
    }

    public ArffWriter getOutputWriter() {
        return arffWriter;
    }

    public PerformanceEventsHolder getPerformanceEventsHolder() {
        return performanceEventsHolder;
    }
}
