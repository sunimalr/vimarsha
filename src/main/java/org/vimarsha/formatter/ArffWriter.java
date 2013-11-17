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

package org.vimarsha.formatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */

public class ArffWriter implements OutputWriter {
    private String fileName;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private ArrayList<String> headers;
    private File file;

    public ArffWriter(String fileName, ArrayList<String> headers) throws IOException{
        this.fileName = fileName;
        this.headers = headers;
        file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        fileWriter = new FileWriter(file.getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fileWriter);
        writeHeader(headers);
    }

    private void writeHeader(ArrayList<String> headers) throws IOException {
        for(String header : headers){
            bufferedWriter.write(header);
        }
    }

    @Override
    public void write(String stringToWrite) throws IOException {
        bufferedWriter.write(stringToWrite);

    }

    @Override
    public void writeLines(ArrayList<String> arrayList) throws IOException {
        for(String line : arrayList){
            bufferedWriter.write(line);
        }
    }

    public void close() throws IOException {
        fileWriter.close();
        bufferedWriter.close();
    }
}
