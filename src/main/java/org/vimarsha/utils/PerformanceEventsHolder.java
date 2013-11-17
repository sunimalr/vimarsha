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

import org.vimarsha.exceptions.InstructionCountNotSetException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerformanceEventsHolder {
    private ArrayList<String> eventsHolder;
    private String instructionCountEvent;
    private Architecture architecture;

    public PerformanceEventsHolder(){
        eventsHolder = new ArrayList<String>();
        instructionCountEvent = null;
    }

    public void setEventsList(ArrayList<String> eventsHolder){
        this.eventsHolder = eventsHolder;
    }

    public void setInstructionCountEvent(String instructionCountEvent){
        this.instructionCountEvent = instructionCountEvent;
    }

    public void addRawEvent(String rawEvent){
        eventsHolder.add(rawEvent);
    }

    public String getInstructionCountEvent() throws InstructionCountNotSetException{
        if(instructionCountEvent == null){
            throw new InstructionCountNotSetException();
        }
        return this.instructionCountEvent;
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }
}
