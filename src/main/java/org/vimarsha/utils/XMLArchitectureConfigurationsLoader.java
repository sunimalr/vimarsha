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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class XMLArchitectureConfigurationsLoader extends DefaultHandler {
    private final PerformanceEventsHolder performanceEventsHolder;
    private final String fileName;
    private String content;
    private boolean selected;
    private Architecture architecture;

    public XMLArchitectureConfigurationsLoader(PerformanceEventsHolder eventsHolder, String fileName) {
        this.fileName = fileName;
        this.performanceEventsHolder = eventsHolder;
        this.selected = false;
    }

    public void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(fileName, this);
        } catch (ParserConfigurationException e) {
            //TODO Handle exception
        } catch (SAXException e) {
            //TODO Handle exception
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            //TODO Handle exception
            System.out.println("IO error");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("architecture")){
            if(attributes.getValue("id").equals(architecture.toString())){
                this.selected =  true;
                performanceEventsHolder.setArchitecture(architecture);
            } else {
                this.selected = false;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("instruction-count") && selected){
            performanceEventsHolder.setInstructionCountEvent(content);
        } else if (qName.equalsIgnoreCase("event") && selected){
            performanceEventsHolder.addRawEvent(content);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content = new String(ch, start, length);
    }

    public PerformanceEventsHolder getPerformanceEventsHolder(){
        return this.performanceEventsHolder;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }
}
