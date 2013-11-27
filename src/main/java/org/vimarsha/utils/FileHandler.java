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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class FileHandler {
    private FileChannel sourceFileChannel;
    private FileChannel destinationFileChannel;

    public FileHandler() {
        this.destinationFileChannel = null;
        this.sourceFileChannel = null;
    }

    public void copy(File source, File destination) throws IOException {
        this.sourceFileChannel = new FileInputStream(source).getChannel();
        this.destinationFileChannel = new FileInputStream(destination).getChannel();
        destinationFileChannel.transferFrom(sourceFileChannel, 0, sourceFileChannel.size());
        this.sourceFileChannel.close();
        this.destinationFileChannel.close();
    }
}
