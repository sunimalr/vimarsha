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

import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfDataHolder {
    private HashMap<String, HashMap<String, Float>> dataStore;

    public PerfDataHolder(){
        dataStore = new HashMap<String, HashMap<String, Float>>();
    }

    public void addValue(String symbol, String rawEvent, Float value){
        if(dataStore.containsKey(symbol)){
            dataStore.get(symbol).put(rawEvent,value);
        }else{
            HashMap<String,Float> tmp = new HashMap<String, Float>();
            tmp.put(rawEvent,value);
            dataStore.put(symbol,tmp);
        }
    }

    /***
     * Get the list of functions/programs
     * TODO: Check whether it is in order.
     * @return
     */
    public ArrayList<String> getSymbolsList(){
        ArrayList<String> symbolsList = new ArrayList<String>();
        symbolsList.addAll(dataStore.keySet());
        return symbolsList;
    }

    public HashMap<String,Float> getRawEventValueCollection(String symbol) throws SymbolNotFoundException {
        if(dataStore.containsKey(symbol)){
            return dataStore.get(symbol);
        }
        throw new SymbolNotFoundException();
    }

    public ArrayList<String> getRawEventsCollection(String symbol) throws SymbolNotFoundException {
        if(dataStore.containsKey(symbol)){
            return new ArrayList<String>(dataStore.get(symbol).keySet());
        }
        throw new SymbolNotFoundException();
    }

    public Float getValue(String symbol, String rawEvent) throws SymbolNotFoundException, RawEventNotFoundException {
        if(dataStore.containsKey(symbol)){
            if(dataStore.get(symbol).containsKey(rawEvent)){
                return dataStore.get(symbol).get(rawEvent);
            } else {
                throw new RawEventNotFoundException();
            }
        }
        throw new SymbolNotFoundException();
    }

    public HashMap<String, HashMap<String,Float>> getDataStore(){
        return dataStore;
    }
}