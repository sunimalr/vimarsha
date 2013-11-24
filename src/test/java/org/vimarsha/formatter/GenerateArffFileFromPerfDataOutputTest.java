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
import org.vimarsha.utils.*;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class GenerateArffFileFromPerfDataOutputTest extends TestCase {
    private ConfigurationsLoader configurationsLoader;
    private PerfReportDataHolder perfReportDataHolder;
    private PerformanceEventsHolder performanceEventsHolder;
    private PerfReportDataParser perfReportDataParser;
    private ArffWriter arffWriter;
    private PerfReportArffDataWriter arffDataWriter;
    private BufferedReader bufferedReader;
    private static final String PROGRAM;
    static {
        PROGRAM = "ppical-bad_fs";
    }

    @Override
    public void setUp() throws Exception {
        this.performanceEventsHolder = new PerformanceEventsHolder();
        this.configurationsLoader = new ConfigurationsLoader(this.performanceEventsHolder);
        this.configurationsLoader.loadPerformanceEvents(Architecture.INTEL_NEHALEM);
        this.performanceEventsHolder = this.configurationsLoader.getPerformanceEventsHolder();
        this.bufferedReader = new BufferedReader(new FileReader("data/ppical-bad_fs_4_1000000.txt"));
        this.perfReportDataHolder = new PerfReportDataHolder();
        this.perfReportDataParser = new PerfReportDataParser(this.bufferedReader, this.perfReportDataHolder, PROGRAM);
        this.perfReportDataParser.parse();
        this.arffDataWriter = new PerfReportArffDataWriter("output/ppical-test.arff",this.performanceEventsHolder,this.perfReportDataHolder);
        this.arffDataWriter.writeToArffFile();
    }

    @Test
    public void testGenrateArffFileFromPerfDataOutput() throws Exception {
        assertEquals("0xc0",this.performanceEventsHolder.getInstructionCountEvent());
        assertEquals("211.28851",this.perfReportDataHolder.getValue("Thread_sum_with_fs","0xc0"));
        assertEquals("@attribute r02a2 numeric",this.arffDataWriter.getHeaders().get(3));
        BufferedReader bufferedReader = new BufferedReader(new FileReader("output/ppical-test.arff"));
//        assertEquals(this.arffDataWriter.getHeaders().get(0),bufferedReader.readLine());
        for(String out: this.arffDataWriter.getHeaders()){
            assertEquals(out,bufferedReader.readLine());
        }
        bufferedReader.readLine();
        assertEquals("% 1 Function: Serial_pi",bufferedReader.readLine());
        bufferedReader.readLine();
        bufferedReader.readLine();
        assertEquals("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",bufferedReader.readLine());
        bufferedReader.close();
    }
}
