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
import org.junit.Before;
import org.junit.Test;
import org.vimarsha.utils.Architecture;
import org.vimarsha.utils.ConfigurationsLoader;
import org.vimarsha.utils.PerfStatDataHolder;
import org.vimarsha.utils.PerformanceEventsHolder;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfStatArffDataWriterTest extends TestCase {
    private PerfStatArffDataWriter perfStatArffDataWriter;
    private PerformanceEventsHolder performanceEventsHolder;
    private PerfStatDataHolder perfStatDataHolder;
    private ConfigurationsLoader configurationsLoader;
    private PerfStatDataParser perfStatDataParser;
    private ArffWriter arffWriter;
    private PerfStatArffDataWriter arffDataWriter;
    private BufferedReader bufferedReader;

    @Before
    public void setUp() throws Exception {
        this.performanceEventsHolder = new PerformanceEventsHolder();
        this.configurationsLoader = new ConfigurationsLoader(this.performanceEventsHolder);
        this.configurationsLoader.loadPerformanceEvents(Architecture.INTEL_NEHALEM);
        this.performanceEventsHolder = this.configurationsLoader.getPerformanceEventsHolder();
        this.bufferedReader = new BufferedReader(new FileReader("data/perfstat.out"));
        this.perfStatDataHolder = new PerfStatDataHolder();
        this.perfStatDataParser = new PerfStatDataParser(this.bufferedReader,this.perfStatDataHolder);
        this.perfStatDataParser.parse();
        this.arffDataWriter = new PerfStatArffDataWriter("output/perfstat-out-test.arff",this.performanceEventsHolder,this.perfStatDataHolder);
        this.arffDataWriter.writeToArffFile();
    }

    @Test
    public void testWriteToArffFile() throws Exception {
        assertEquals("0xc0",this.performanceEventsHolder.getInstructionCountEvent());
        assertEquals("r00c0",this.performanceEventsHolder.getPrettyInstructionCountEvent());
    }
}
