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

package org.vimarsha.utils;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class XMLArchitectureConfigurationsLoaderTest extends TestCase {
    private XMLArchitectureConfigurationsLoader xmlArchitectureConfigurationsLoader;
    private PerformanceEventsHolder performanceEventsHolder;
    private PerformanceEventsHolder dummyEventsHolder;

    @After
    public void tearDown() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        performanceEventsHolder = new PerformanceEventsHolder();
        dummyEventsHolder = new PerformanceEventsHolder();
        xmlArchitectureConfigurationsLoader = new XMLArchitectureConfigurationsLoader(performanceEventsHolder, "config/events.xml");
        xmlArchitectureConfigurationsLoader.setArchitecture(Architecture.INTEL_NEHALEM);
        xmlArchitectureConfigurationsLoader.parseDocument();

        ArrayList<String> dummyList = new ArrayList<String>(Arrays.asList("0x149", "0x151", "0x2a2", "0x126", "0x227", "0x224", "0x8a2", "0x1b0", "0x20f0", "0x2f1", "0x1f2", "0x1b8", "0x2b8", "0x4b8", "0x40cb"));
        dummyEventsHolder.setArchitecture(Architecture.INTEL_NEHALEM);
        dummyEventsHolder.setInstructionCountEvent("0xc0");
        dummyEventsHolder.setEventsHolder(dummyList);
    }

    @Test
    public void testGetPerformanceEventsHolder() throws Exception {
        assertEquals("INTEL_NEHALEM", xmlArchitectureConfigurationsLoader.getPerformanceEventsHolder().getArchitecture().toString());
        assertEquals("0xc0", xmlArchitectureConfigurationsLoader.getPerformanceEventsHolder().getInstructionCountEvent());
        assertEquals(dummyEventsHolder.getEventsHolder(), xmlArchitectureConfigurationsLoader.getPerformanceEventsHolder().getEventsHolder());
    }
}
