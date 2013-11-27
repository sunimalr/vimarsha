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
import org.vimarsha.exceptions.*;
import org.vimarsha.formatter.*;
import org.vimarsha.utils.*;
import org.xml.sax.SAXException;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

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
    private ArffHandler arffHandler;
    private TableDataHandler tableDataHandler;
    private boolean rawfileconverted;


    public DefaultMediator() {
        this.performanceEventsHolder = new PerformanceEventsHolder();
        this.configurationsLoader = new ConfigurationsLoader(this.performanceEventsHolder);
        this.perfStatDataHolder = new PerfStatDataHolder();
        this.perfReportDataHolder = new PerfReportDataHolder();
        this.rawfileconverted = false;
    }

    @Override
    public int setRawFile(File fileToOpen) throws IOException, DataFileTypeHeaderNotSetException {
        this.rawfileconverted = true;
        this.currentRawFile = fileToOpen;
        this.dataFileType = new DataFileTypeDetector(fileToOpen).getDataFileType();
        return 100;
    }

    @Override
    public int setArffFile(File fileToOpen) throws IOException {
        this.rawfileconverted = false;
        this.currentArffFile = fileToOpen;
        setLocalArffFile(this.currentArffFile);
        return 0;
    }

    private int setLocalArffFile(File fileToOpen) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileToOpen));
        this.arffHandler = new ArffHandler(reader,this.performanceEventsHolder);
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
                this.perfReportArffDataWriter = new PerfReportArffDataWriter("output/tempreport.arff",this.performanceEventsHolder,this.perfReportDataHolder);
                this.perfReportArffDataWriter.writeToArffFile();
                this.setLocalArffFile(new File("output/tempreport.arff"));
                this.bufferedReader.close();
                return 100;
            case PERF_STAT:
                this.perfStatDataHolder = new PerfStatDataHolder();
                this.bufferedReader = new BufferedReader(new FileReader(this.currentRawFile));
                this.perfStatDataParser = new PerfStatDataParser(this.bufferedReader,this.perfStatDataHolder);
                this.perfStatDataParser.parse();
                this.perfStatArffDataWriter = new PerfStatArffDataWriter("output/tempstat.arff",this.performanceEventsHolder,this.perfStatDataHolder);
                this.perfStatArffDataWriter.writeToArffFile();
                this.setLocalArffFile(new File("output/tempstat.arff"));
                this.bufferedReader.close();
                return 100;
            default:

        }
        return -1;
    }

    @Override
    public int saveArffFile(File fileToSave) throws IOException {
        //TODO thrown FileNotFoundException - fileToSave doesn't exist
        File dest = fileToSave;
        if(!dest.exists()){
            dest = new File(fileToSave.getAbsolutePath());
        }
        System.out.println(dest.getAbsolutePath() + dest.exists());
        new FileHandler().copy(this.currentArffFile,dest);
        return 0;
    }

    @Override
    public DefaultTableModel getArffAttributesTableModel() {
        this.tableDataHandler = new TableDataHandler(this.arffHandler.getPerformanceEventsList(this.rawfileconverted));
        return this.tableDataHandler.getTableModel();
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
    public DefaultTableModel getClassificationResultsTableModel() {
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
