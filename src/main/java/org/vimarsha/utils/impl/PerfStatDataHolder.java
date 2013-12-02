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

import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.utils.DataHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfStatDataHolder implements DataHolder {
    private HashMap<String, String> dataStore;

    public PerfStatDataHolder() {
        dataStore = new HashMap<String, String>();
    }

    public void addValue(String rawEvent, String value) {
        dataStore.put(rawEvent, value);
    }

    public String getValue(String rawEvent) throws RawEventNotFoundException {
        if (dataStore.containsKey(rawEvent)) {
            return dataStore.get(rawEvent);
        }
        throw new RawEventNotFoundException();
    }

    public ArrayList<String> getRawEventsCollection() {
        return new ArrayList<String>(dataStore.keySet());
    }

    public HashMap<String, String> getDataStore() {
        return dataStore;
    }
}
