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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * A class which parses a given xml file to extract data.
 *
 * @author gayashan
 */
public class XMLArchitectureConfigurationsLoader extends DefaultHandler {
    private final PerformanceEventsHolder performanceEventsHolder;
    private final String fileName;
    private String content;
    private boolean selected;
    private Architecture architecture;

    /**
     * Generates a xml data parser.
     *
     * @param eventsHolder a PerformanceEventsHolder
     * @param fileName     xml file name
     */
    public XMLArchitectureConfigurationsLoader(PerformanceEventsHolder eventsHolder, String fileName) {
        this.fileName = fileName;
        this.performanceEventsHolder = eventsHolder;
        this.selected = false;
    }

    /**
     * Parse the document through the xml parser
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void parseDocument() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(fileName, this);

    }

    /**
     * When a starting element is found trigger this method.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(PropertiesLoader.getInstance().getXMLTagArchitecture())) {
            if (attributes.getValue(PropertiesLoader.getInstance().getXMLTagID()).equals(architecture.toString())) {
                this.selected = true;
                performanceEventsHolder.setArchitecture(architecture);
            } else {
                this.selected = false;
            }
        }
    }

    /**
     * When an ending element is found trigger this method.
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(PropertiesLoader.getInstance().getXMLTagInsCount()) && selected) {
            performanceEventsHolder.setInstructionCountEvent(content);
        } else if (qName.equalsIgnoreCase(PropertiesLoader.getInstance().getXMLTagEvent()) && selected) {
            performanceEventsHolder.addRawEvent(content);
        } else if (qName.equalsIgnoreCase(PropertiesLoader.getInstance().getXMLTagTrainingModel()) && selected) {
            performanceEventsHolder.setTrainingModel(content);
        } else if (qName.equalsIgnoreCase(PropertiesLoader.getInstance().getXMLTagRelationHeader()) && selected) {
            performanceEventsHolder.setRelationHeader(content);
        }
    }

    /**
     * When in between starting and ending element trigger this method.
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content = new String(ch, start, length);
    }

    /**
     * Returns the PerformanceEventsHolder
     *
     * @return PerformanceEventsHolder
     */
    public PerformanceEventsHolder getPerformanceEventsHolder() {
        return this.performanceEventsHolder;
    }

    /**
     * Set the architecture
     *
     * @param architecture architecture
     */
    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }
}
