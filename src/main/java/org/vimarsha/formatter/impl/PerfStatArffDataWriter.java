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
import org.vimarsha.utils.impl.PerfStatDataHolder;
import org.vimarsha.utils.impl.PerfStatTimeSlicedDataHolder;
import org.vimarsha.utils.impl.PerformanceEventsHolder;
import org.vimarsha.utils.impl.PropertiesLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfStatArffDataWriter extends DataWriter {
    private PerfStatDataHolder perfStatDataHolder;
    private PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder;

    public PerfStatArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfStatDataHolder perfStatDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfStatDataHolder = perfStatDataHolder;
    }

    public PerfStatArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfStatTimeSlicedDataHolder perfStatTimeSlicedDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfStatTimeSlicedDataHolder = perfStatTimeSlicedDataHolder;
    }

    @Override
    public void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        String out = "";
        float instructionCount;
        if (!this.perfStatDataHolder.getValue(this.performanceEventsHolder.getPrettyInstructionCountEvent()).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
            instructionCount = Float.parseFloat(this.perfStatDataHolder.getValue(this.performanceEventsHolder.getPrettyInstructionCountEvent()));
        } else {
            throw new InstructionCountNotSetException();
        }
        for (String rawEvent : this.performanceEventsHolder.getPrettyEventsHolder()) {
            if (!this.perfStatDataHolder.getValue(rawEvent).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
                out += new BigDecimal(Float.parseFloat(this.perfStatDataHolder.getValue(rawEvent)) * java.lang.Math.pow(10, 9) / instructionCount).setScale(4, RoundingMode.CEILING).toPlainString();
            } else {
                out += PropertiesLoader.getInstance().getMissingValueIndicator();
            }
            out += PropertiesLoader.getInstance().getValueSeparator();
        }
        out += PropertiesLoader.getInstance().getMissingValueIndicator() + System.getProperty("line.separator");
        this.arffWriter.write(out);
        this.arffWriter.close();
    }

    public void writeManyToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        String out = "";
        for (String symbol : this.perfStatTimeSlicedDataHolder.getSymbolsList()) {
            float instructionCount;
            if (!this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(this.performanceEventsHolder.getPrettyInstructionCountEvent()).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
                instructionCount = Float.parseFloat(this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(this.performanceEventsHolder.getPrettyInstructionCountEvent()));
            } else {
                throw new InstructionCountNotSetException();
            }
            for (String rawEvent : this.performanceEventsHolder.getPrettyEventsHolder()) {
                if (!this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(rawEvent).equalsIgnoreCase(PropertiesLoader.getInstance().getNotCountedValueIndicator())) {
                    out += new BigDecimal(Float.parseFloat(this.perfStatTimeSlicedDataHolder.getRawEventValueCollection(symbol).get(rawEvent)) * java.lang.Math.pow(10, 9) / instructionCount).setScale(4, RoundingMode.CEILING).toPlainString();
                } else {
                    out += PropertiesLoader.getInstance().getMissingValueIndicator();
                }
                out += PropertiesLoader.getInstance().getValueSeparator();
            }
            out += PropertiesLoader.getInstance().getMissingValueIndicator() + System.getProperty("line.separator");
        }
        this.arffWriter.write(out);
        this.arffWriter.close();
    }
}
