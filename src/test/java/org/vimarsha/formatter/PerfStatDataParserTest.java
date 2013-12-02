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

package org.vimarsha.formatter;

import junit.framework.TestCase;
import org.junit.Test;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.formatter.impl.PerfStatDataParser;
import org.vimarsha.utils.impl.PerfStatDataHolder;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfStatDataParserTest extends TestCase {
    private BufferedReader bufferedReader;
    private PerfStatDataHolder perfStatDataHolder;
    private PerfStatDataParser perfStatDataParser;

    @Override
    public void setUp() throws Exception {
        this.bufferedReader = new BufferedReader(new FileReader("data/perfstat.out"));
        this.perfStatDataHolder = new PerfStatDataHolder();
        this.perfStatDataParser = new PerfStatDataParser(this.bufferedReader, this.perfStatDataHolder);
        this.perfStatDataParser.parse();
    }

    @Test
    public void testParse() throws Exception {
        assertEquals("155903550566", this.perfStatDataParser.getPerfStatDataHolder().getValue("r00c0"));
        assertEquals("227362", this.perfStatDataParser.getPerfStatDataHolder().getValue("r0149"));
        assertEquals("654243829", this.perfStatDataParser.getPerfStatDataHolder().getValue("r0151"));
        assertEquals("4641640916", this.perfStatDataParser.getPerfStatDataHolder().getValue("r02a2"));
        assertEquals("21443166", this.perfStatDataParser.getPerfStatDataHolder().getValue("r0126"));
        assertEquals("364744378", this.perfStatDataParser.getPerfStatDataHolder().getValue("r08a2"));
        assertEquals("14425", this.perfStatDataParser.getPerfStatDataHolder().getValue("r04b8"));
        assertEquals("53190504", this.perfStatDataParser.getPerfStatDataHolder().getValue("r40cb"));
    }

    @Test(expected = RawEventNotFoundException.class)
    public void testRawEventNotFoundException() throws SymbolNotFoundException {
        try {
            this.perfStatDataHolder.getValue("0x1491");
        } catch (RawEventNotFoundException e) {
        }
    }
}
