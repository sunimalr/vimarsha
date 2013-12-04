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

package org.vimarsha.utils.impl;

import org.vimarsha.utils.Architecture;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * A class which can be used load the architecture configurations when created.
 *
 * @author gayashan
 */
public class ConfigurationsLoader {
    private XMLArchitectureConfigurationsLoader xmlHandler;
    private PerformanceEventsHolder performanceEventsHolder;

    /**
     * Creates a new XMLArchitectureConfigurationsLoader with the provided performanceEventsHolder.
     *
     * @param performanceEventsHolder a PerformanceEventsHolder
     */
    public ConfigurationsLoader(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
        xmlHandler = new XMLArchitectureConfigurationsLoader(performanceEventsHolder, PropertiesLoader.getInstance().getEventsXMLFileName());
    }

    /**
     * Load the performance events related to the provided architecture.
     *
     * @param architecture architecture
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void loadPerformanceEvents(Architecture architecture) throws IOException, SAXException, ParserConfigurationException {
        xmlHandler.setArchitecture(architecture);
        xmlHandler.parseDocument();
        this.performanceEventsHolder = xmlHandler.getPerformanceEventsHolder();
    }

    /**
     * Returns the PerformanceEventsHolder with the performance events information related to the architecture.
     *
     * @return PerformanceEventsHolder
     */
    public PerformanceEventsHolder getPerformanceEventsHolder() {
        return performanceEventsHolder;
    }

    /**
     * Set the PerformanceEventsHolder dynamically.
     *
     * @param performanceEventsHolder a PerformanceEventsHolder
     */
    public void setPerformanceEventsHolder(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
    }
}
