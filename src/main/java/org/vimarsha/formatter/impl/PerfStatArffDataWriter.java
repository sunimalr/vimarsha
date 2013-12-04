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
import org.vimarsha.formatter.DataWriter;
import org.vimarsha.utils.impl.PerfStatDataHolder;
import org.vimarsha.utils.impl.PerfStatTimeSlicedDataHolder;
import org.vimarsha.utils.impl.PerformanceEventsHolder;
import org.vimarsha.utils.impl.PropertiesLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A class which can be used to generate an arff file after parsing the output from the perf stat tool.
 *
 * @author gayashan
 */
public class PerfStatArffDataWriter extends DataWriter {
    private PerfStatDataHolder perfStatDataHolder;
    private PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder;

    /**
     * Create PerfStatArffDataWriter which contains a PerfStatDataHolder
     *
     * @param fileName                arff file name
     * @param performanceEventsHolder a PerformanceEventsHolder
     * @param perfStatDataHolder      a PerfStatDataHolder
     * @throws IOException
     */
    public PerfStatArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfStatDataHolder perfStatDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfStatDataHolder = perfStatDataHolder;
    }

    /**
     * Create PerfStatArffDataWriter which contains a PerfStatTimeSlicedDataHolder
     *
     * @param fileName                     arff file name
     * @param performanceEventsHolder      a PerformanceEventsHolder
     * @param perfStatTimeSlicedDataHolder a PerfStatTimeSlicedDataHolder
     * @throws IOException
     */
    public PerfStatArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfStatTimeSlicedDataHolder = perfStatTimeSlicedDataHolder;
    }

    /**
     * Write a single instance result from perf stat to an arff file
     *
     * @throws SymbolNotFoundException
     * @throws InstructionCountNotSetException
     *
     * @throws RawEventNotFoundException
     * @throws IOException
     */
    @Override
    public void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        StringBuilder out = new StringBuilder();
        float instructionCount;
        // if the instruction retired count is available - otherwise the normalization cannot be done. So the conversion is useless.
        if (!this.perfStatDataHolder.getValue(this.performanceEventsHolder.getPrettyInstructionCountEvent()).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
            instructionCount = Float.parseFloat(this.perfStatDataHolder.getValue(this.performanceEventsHolder.getPrettyInstructionCountEvent()));
        } else {
            throw new InstructionCountNotSetException();
        }
        for (String rawEvent : this.performanceEventsHolder.getPrettyEventsHolder()) {
            //if each event value is counted
            if (!this.perfStatDataHolder.getValue(rawEvent).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
                //calculate the normalized value = performance event count value * 10^9 / number of instructions retired
                out.append(new BigDecimal(Float.parseFloat(this.perfStatDataHolder.getValue(rawEvent)) * java.lang.Math.pow(10, 9) / instructionCount).setScale(4, RoundingMode.CEILING).toPlainString());
            } else {
                //else add a missing value indicator
                out.append(PropertiesLoader.getInstance().getMissingValueIndicator());
            }
            out.append(PropertiesLoader.getInstance().getValueSeparator());
        }
        out.append(PropertiesLoader.getInstance().getMissingValueIndicator() + System.getProperty("line.separator"));
        this.arffWriter.write(out.toString());
        this.arffWriter.close();
    }

    /**
     * Write many instances of perf stat outputs to an arff file.
     *
     * @throws SymbolNotFoundException
     * @throws InstructionCountNotSetException
     *
     * @throws RawEventNotFoundException
     * @throws IOException
     */
    public void writeManyToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        StringBuilder out = new StringBuilder();
        //for each symbol a new instance is present - add a new line to the arff file
        for (String symbol : this.perfStatTimeSlicedDataHolder.getSymbolsList()) {
            float instructionCount;
            // if the instruction retired count is available - otherwise the normalization cannot be done. So the conversion is useless.
            if (!this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(this.performanceEventsHolder.getPrettyInstructionCountEvent()).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
                instructionCount = Float.parseFloat(this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(this.performanceEventsHolder.getPrettyInstructionCountEvent()));
            } else {
                throw new InstructionCountNotSetException();
            }
            for (String rawEvent : this.performanceEventsHolder.getPrettyEventsHolder()) {
                //if each event value is counted
                if (!this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(rawEvent).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
                    //calculate the normalized value = performance event count value * 10^9 / number of instructions retired
                    out.append(new BigDecimal(Float.parseFloat(this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(rawEvent)) * java.lang.Math.pow(10, 9) / instructionCount).setScale(4, RoundingMode.CEILING).toPlainString());
                } else {
                    //else add a missing value indicator
                    out.append(PropertiesLoader.getInstance().getMissingValueIndicator());
                }
                out.append(PropertiesLoader.getInstance().getValueSeparator());
            }
            out.append(PropertiesLoader.getInstance().getMissingValueIndicator() + System.getProperty("line.separator"));
        }
        this.arffWriter.write(out.toString());
        this.arffWriter.close();
    }
}
