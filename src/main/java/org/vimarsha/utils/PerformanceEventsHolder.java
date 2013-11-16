package main.java.org.vimarsha.utils;

import main.java.org.vimarsha.exceptions.InstructionCountNotSetException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerformanceEventsHolder {
    private ArrayList<String> eventsHolder;
    private String instructionCountEvent;

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
}
