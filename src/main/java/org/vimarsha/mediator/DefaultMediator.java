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

package org.vimarsha.mediator;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.vimarsha.classifier.FunctionWiseClassifier;
import org.vimarsha.classifier.TimeslicedClassifier;
import org.vimarsha.classifier.WholeProgramClassifier;
import org.vimarsha.exceptions.InstructionCountNotSetException;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.RawFileParseFailedException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.formatter.*;
import org.vimarsha.utils.*;
import org.xml.sax.SAXException;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class DefaultMediator implements Mediator{
    private PerformanceEventsHolder performanceEventsHolder;
    private ConfigurationsLoader configurationsLoader;
    private ArffWriter arffWriter;
    private PerfStatArffDataWriter perfStatArffDataWriter;
    private PerfReportArffDataWriter perfReportArffDataWriter;
    private PerfStatDataHolder perfStatDataHolder;
    private PerfReportDataHolder perfReportDataHolder;
    private PerfStatDataParser perfStatDataParser;
    private PerfReportDataParser perfReportDataParser;
    private BufferedReader bufferedReader;
    private WholeProgramClassifier wholeProgramClassifier;
    private FunctionWiseClassifier functionWiseClassifier;
    private TimeslicedClassifier timeslicedClassifier;
    private Architecture currentArchitecture;
    private File currentRawFile;
    private File currentArffFile;
    private DataFileType dataFileType;

    public DefaultMediator() {
        this.performanceEventsHolder = new PerformanceEventsHolder();
        this.configurationsLoader = new ConfigurationsLoader(this.performanceEventsHolder);
        this.perfStatDataHolder = new PerfStatDataHolder();
        this.perfReportDataHolder = new PerfReportDataHolder();
    }

    @Override
    public int setRawFile(File fileToOpen) throws IOException {
        this.currentRawFile = fileToOpen;
        this.dataFileType = new DataFileTypeDetector(fileToOpen).getDataFileType();
        return 100;
    }

    @Override
    public int setArffFile(File fileToOpen) {
        this.currentArffFile = fileToOpen;
        return 0;
    }

    @Override
    public int setArchitecture(String architecture) throws ParserConfigurationException, SAXException, IOException {
        this.currentArchitecture = Architecture.valueOf(architecture);
        this.configurationsLoader.loadPerformanceEvents(this.currentArchitecture);
        return 0;
    }

    @Override
    public int convertRawFileToArff() throws IOException, SymbolNotFoundException, RawEventNotFoundException, InstructionCountNotSetException, RawFileParseFailedException {
        switch (this.dataFileType){
            case PERF_REPORT:
                this.perfReportDataHolder = new PerfReportDataHolder();
                this.bufferedReader = new BufferedReader(new FileReader(this.currentRawFile));
                this.perfReportDataParser = new PerfReportDataParser(this.bufferedReader,this.perfReportDataHolder);
                this.perfReportDataParser.parse();
                this.perfReportArffDataWriter = new PerfReportArffDataWriter("output/temp.arff",this.performanceEventsHolder,this.perfReportDataHolder);
                this.perfReportArffDataWriter.writeToArffFile();
                this.bufferedReader.close();
                break;
            case PERF_STAT:

                break;
            default:

        }
        return 0;
    }

    @Override
    public int saveArffFile(File fileToSave) {
        return 0;
    }

    @Override
    public DefaultTableModel getTableModel() {
        return null;
    }

    @Override
    public DefaultCategoryDataset getBarChartDataSet() {
        return null;
    }

    @Override
    public ArrayList<String> getArchitectureList() {
        ArrayList<String> architectureList = new ArrayList<String>();
        for(Architecture architecture : Architecture.values()){
            architectureList.add(architecture.toString());
        }
        return architectureList;
    }

    @Override
    public ArrayList<String> getTrainingModels() {
        return null;
    }

    @Override
    public int classify() {
        return 0;
    }

    @Override
    public DefaultTableModel getClassificationResults() {
        return null;
    }

    @Override
    public XYSeriesCollection getXYChartDataSet() {
        return null;
    }

    @Override
    public int exportAsCSV() {
        return 0;
    }

    @Override
    public int exportAsImage() {
        return 0;
    }
}
