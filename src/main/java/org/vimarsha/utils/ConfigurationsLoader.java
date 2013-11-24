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

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 */
public class ConfigurationsLoader {
    private XMLArchitectureConfigurationsLoader xmlHandler;
    private PerformanceEventsHolder performanceEventsHolder;

    public ConfigurationsLoader(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
        xmlHandler = new XMLArchitectureConfigurationsLoader(performanceEventsHolder, "config/events.config");
    }

    public void loadPerformanceEvents(Architecture architecture) throws IOException, SAXException, ParserConfigurationException {
        xmlHandler.setArchitecture(architecture);
        xmlHandler.parseDocument();
        this.performanceEventsHolder = xmlHandler.getPerformanceEventsHolder();
    }

    public PerformanceEventsHolder getPerformanceEventsHolder() {
        return performanceEventsHolder;
    }

    public void setPerformanceEventsHolder(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
    }
}
