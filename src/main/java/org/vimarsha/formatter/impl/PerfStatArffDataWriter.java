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
import org.vimarsha.utils.impl.PerformanceEventsHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfStatArffDataWriter extends DataWriter {
    private PerfStatDataHolder perfStatDataHolder;

    public PerfStatArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfStatDataHolder perfStatDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfStatDataHolder = perfStatDataHolder;
    }

    @Override
    public void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        String out = "";
        float instructionCount;
        if (!this.perfStatDataHolder.getValue(this.performanceEventsHolder.getPrettyInstructionCountEvent()).equalsIgnoreCase("<not counted>")) {
            instructionCount = Float.parseFloat(this.perfStatDataHolder.getValue(this.performanceEventsHolder.getPrettyInstructionCountEvent()));
        } else {
            throw new InstructionCountNotSetException();
        }
        for (String rawEvent : this.performanceEventsHolder.getPrettyEventsHolder()) {
            if (!this.perfStatDataHolder.getValue(rawEvent).equalsIgnoreCase("<not counted>")) {
                out += new BigDecimal(Float.parseFloat(this.perfStatDataHolder.getValue(rawEvent)) * java.lang.Math.pow(10, 9) / instructionCount).setScale(4, RoundingMode.CEILING).toPlainString();
            } else {
                out += "?";
            }
            out += ",";
        }
        out += "?\n";
        this.arffWriter.write(out);
        this.arffWriter.close();
    }


}
