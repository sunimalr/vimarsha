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

import org.vimarsha.exceptions.InstructionCountNotSetException;
import org.vimarsha.utils.Architecture;

import java.util.ArrayList;

/**
 * A class which holds performance count events per architecture.
 * Also holds the training model specified for the architecture.
 *
 * @author gayashan
 */
public class PerformanceEventsHolder {
    private ArrayList<String> eventsHolder;
    private String instructionCountEvent;
    private Architecture architecture;
    private String trainingModel;
    private String relationHeader;

    /**
     * Default constructor.
     */
    public PerformanceEventsHolder() {
        setEventsHolder(new ArrayList<String>());
        instructionCountEvent = null;
    }

    /**
     * Set the instruction count raw event.
     *
     * @param instructionCountEvent instruction retired event
     */
    public void setInstructionCountEvent(String instructionCountEvent) {
        this.instructionCountEvent = instructionCountEvent;
    }

    /**
     * Return the instruction count raw event.
     *
     * @return String
     * @throws InstructionCountNotSetException
     *
     */
    public String getInstructionCountEvent() throws InstructionCountNotSetException {
        if (instructionCountEvent == null) {
            throw new InstructionCountNotSetException();
        }
        return this.instructionCountEvent;
    }

    /**
     * Add a raw event to the events list.
     *
     * @param rawEvent performance event
     */
    public void addRawEvent(String rawEvent) {
        getEventsHolder().add(rawEvent);
    }

    /**
     * Returns the prettified Instruction count raw event id
     *
     * @return String
     * @throws InstructionCountNotSetException
     *
     */
    public String getPrettyInstructionCountEvent() throws InstructionCountNotSetException {
        if (instructionCountEvent == null) {
            throw new InstructionCountNotSetException();
        }
        String event = instructionCountEvent.split(PropertiesLoader.getInstance().getPerfEventPrefix())[1];
        if (event.length() < 4) {   //TODO Handle properly
            return new String(PropertiesLoader.getInstance().getPerfEventPrettyPrefix1() + event);
        }
        return new String(PropertiesLoader.getInstance().getPerfEventPrettyPrefix3() + event);
    }

    /**
     * Returns the architecture.
     *
     * @return Architecture
     */
    public Architecture getArchitecture() {
        return architecture;
    }

    /**
     * Set the architecture.
     *
     * @param architecture architecture
     */
    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    /**
     * Get a list of performance events related to the architecture.
     *
     * @return ArrayList
     */
    public ArrayList<String> getEventsHolder() {
        return eventsHolder;
    }

    /**
     * Set the events holder which holds the list of events.
     *
     * @param eventsHolder ArrayList to store events
     */
    public void setEventsHolder(ArrayList<String> eventsHolder) {
        this.eventsHolder = eventsHolder;
    }

    /**
     * Returns a prettified events holder.
     * Needed when creating the arff file headers
     * Since training files contain headers in the format "r0####" instead of "0x####"
     *
     * @return ArrayList of prettified events
     */
    public ArrayList<String> getPrettyEventsHolder() {
        ArrayList<String> events = new ArrayList<String>();
        for (String event : this.eventsHolder) {
            event = event.split(PropertiesLoader.getInstance().getPerfEventPrefix())[1];
            if (event.length() < 4) {
                events.add(PropertiesLoader.getInstance().getPerfEventPrettyPrefix2() + event);
            } else {
                events.add(PropertiesLoader.getInstance().getPerfEventPrettyPrefix3() + event);
            }
        }
        return events;
    }

    /**
     * Returns the file name of the training model.
     *
     * @return String
     */
    public String getTrainingModel() {
        return trainingModel;
    }

    /**
     * Set the file name of the training model.
     *
     * @param trainingModel training model name
     */
    public void setTrainingModel(String trainingModel) {
        this.trainingModel = trainingModel;
    }

    /**
     * Returns the relation header for the architecture.
     *
     * @return String
     */
    public String getRelationHeader() {
        return relationHeader;
    }

    /**
     * Set the relationHeader.
     *
     * @param relationHeader relation header string
     */
    public void setRelationHeader(String relationHeader) {
        this.relationHeader = relationHeader;
    }

}
