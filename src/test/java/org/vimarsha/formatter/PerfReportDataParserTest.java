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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.utils.PerfDataHolder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfReportDataParserTest extends TestCase {
    private PerfReportDataParser perfReportDataParser;
    private PerfDataHolder perfDataHolder;
    private BufferedReader bufferedReader;
    private static final String PROGRAM;
    private ByteArrayOutputStream out;
    static {
        PROGRAM = "ppical-bad_fs";
    }

    @Before
    public void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        this.bufferedReader = new BufferedReader(new FileReader("data/ppical-bad_fs_4_1000000.txt"));
        perfDataHolder = new PerfDataHolder();
        this.perfReportDataParser = new PerfReportDataParser(bufferedReader, perfDataHolder, PROGRAM);
        this.perfReportDataParser.parse();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParse() throws Exception {
        assertEquals(211.28851, perfDataHolder.getValue("Thread_sum_with_fs", "0xc0"), 0.000004);
        assertEquals(187.5923,perfDataHolder.getValue("Thread_sum_with_fs","0x151"),0.0000005);
        assertEquals(138.67849,perfDataHolder.getValue("Thread_sum_with_fs","0x20f0"), 0.000008);
        assertEquals(196.56660,perfDataHolder.getValue("Thread_sum_with_fs","0x4b8"), 0.000005);
        assertEquals(18.377,perfDataHolder.getValue("Serial_pi","0xc0"), 0.0000009);
    }

    @Test(expected = RawEventNotFoundException.class)
    public void testRawEventNotFounfException() throws SymbolNotFoundException {
        try {
            perfDataHolder.getValue("Thread_sum_with_fs","0x149");
        } catch (RawEventNotFoundException e) {
        }
    }
}
