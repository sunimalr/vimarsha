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

import org.vimarsha.exceptions.DataFileTypeHeaderNotSetException;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class DataFileTypeDetector {
    private DataFileType dataFileType;
    private File file;
    private BufferedReader bufferedReader;

    public DataFileTypeDetector(File file) throws FileNotFoundException {
        this.file = file;
        this.bufferedReader = new BufferedReader(new FileReader(file));
        this.dataFileType = DataFileType.UNKNOWN;
    }

    /***
     * Expected header format "@datafiletype=PERF_REPORT"
     * @return
     * @throws IOException
     */
    public DataFileType getDataFileType() throws IOException, DataFileTypeHeaderNotSetException {
        String line = bufferedReader.readLine();
        while(line.startsWith("#")){
            line = bufferedReader.readLine();
        }
        line = line.trim();
        if(line.contains("@datafiletype")){
            if((line.split("=")[1]).equalsIgnoreCase("PERF_STAT")){
                this.dataFileType = DataFileType.PERF_STAT;
            } else if((line.split("=")[1]).equalsIgnoreCase("PERF_REPORT")){
                this.dataFileType = DataFileType.PERF_REPORT;
            }
        } else {
            throw new DataFileTypeHeaderNotSetException();
        }
        this.bufferedReader.close();
        return this.dataFileType;
    }
}
