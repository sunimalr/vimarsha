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

package org.vimarsha.mediator.impl;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.vimarsha.classifier.impl.FunctionWiseClassifier;
import org.vimarsha.classifier.impl.TimeslicedClassifier;
import org.vimarsha.classifier.impl.WholeProgramClassifier;
import org.vimarsha.exceptions.*;
import org.vimarsha.formatter.impl.*;
import org.vimarsha.mediator.Mediator;
import org.vimarsha.utils.Architecture;
import org.vimarsha.utils.DataFileType;
import org.vimarsha.utils.impl.*;
import org.xml.sax.SAXException;
import weka.core.Instances;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class DefaultMediator implements Mediator {
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
    private Object classificationResult;
    private HashMap<String, String> wholeProgramClassificationResultsBuffer;
    private Architecture currentArchitecture;
    private Instances currentTrainingModel;
    private File currentRawFile;
    private File currentArffFile;
    private DataFileType dataFileType;
    private ArffHandler arffHandler;
    private TableDataGenerator tableDataGenerator;
    private ChartDataGenerator chartDataGenerator;
    private boolean rawfileconverted;


    public DefaultMediator() {
        this.performanceEventsHolder = new PerformanceEventsHolder();
        this.configurationsLoader = new ConfigurationsLoader(this.performanceEventsHolder);
        this.rawfileconverted = false;
        this.currentTrainingModel = null;
        this.currentArchitecture = null;
        this.wholeProgramClassificationResultsBuffer = new HashMap<String, String>();
    }

    @Override
    public int setRawFile(File fileToOpen) throws IOException, DataFileTypeHeaderNotSetException {
        this.perfStatDataHolder = new PerfStatDataHolder();
        this.perfReportDataHolder = new PerfReportDataHolder();
        this.rawfileconverted = true;
        this.currentRawFile = fileToOpen;
        this.dataFileType = new DataFileTypeDetector(fileToOpen).getDataFileType();
        return 100;
    }

    @Override
    public int setArffFile(File fileToOpen) throws IOException {
        this.perfStatDataHolder = new PerfStatDataHolder();
        this.perfReportDataHolder = new PerfReportDataHolder();
        this.rawfileconverted = false;
        this.currentArffFile = fileToOpen;
        setLocalArffFile(this.currentArffFile);
        return 0;
    }

    private int setLocalArffFile(File fileToOpen) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileToOpen));
        this.arffHandler = new ArffHandler(reader, this.performanceEventsHolder);
        return 0;
    }

    @Override
    public int setArchitecture(String architecture) throws ParserConfigurationException, SAXException, IOException {
        this.performanceEventsHolder = new PerformanceEventsHolder();
        this.configurationsLoader = new ConfigurationsLoader(this.performanceEventsHolder);
        this.currentArchitecture = Architecture.valueOf(architecture);
        this.configurationsLoader.loadPerformanceEvents(this.currentArchitecture);
        loadTrainingModel(Architecture.valueOf(architecture));
        return 0;
    }

    private int loadTrainingModel(Architecture architecture) throws IOException {
        this.currentTrainingModel = new Instances(new BufferedReader(new FileReader(this.performanceEventsHolder.getTrainingModel())));
        if (this.currentTrainingModel.classIndex() == -1) {
            this.currentTrainingModel.setClassIndex(this.currentTrainingModel.numAttributes() - 1);
        }
        return 100;
    }

    public String getTrainingModel() {
        if (this.currentTrainingModel != null) {
            return this.currentTrainingModel.relationName();
        }
        return "Not set";
    }

    @Override
    public String getTestDataName() {
        if (this.currentArffFile == null && this.currentRawFile == null) {
            return "Not set";
        }
        if (rawfileconverted) {
            return this.currentRawFile.getName();
        }
        return this.currentArffFile.getName();
    }

    @Override
    public String getArchitecture() throws ArchitectureNotSetException {
        if (this.currentArchitecture == null) {
            throw new ArchitectureNotSetException();
        }
        return this.currentArchitecture.name();
    }

    @Override
    public int convertRawFileToArff() throws IOException, SymbolNotFoundException, RawEventNotFoundException, InstructionCountNotSetException, RawFileParseFailedException {
        switch (this.dataFileType) {
            case PERF_REPORT:
                this.perfReportDataHolder = new PerfReportDataHolder();
                this.bufferedReader = new BufferedReader(new FileReader(this.currentRawFile));
                this.perfReportDataParser = new PerfReportDataParser(this.bufferedReader, this.perfReportDataHolder);
                this.perfReportDataParser.parse();
                this.perfReportArffDataWriter = new PerfReportArffDataWriter(PropertiesLoader.getInstance().getTempReportARFF(), this.performanceEventsHolder, this.perfReportDataHolder);
                this.perfReportArffDataWriter.writeToArffFile();
                this.setLocalArffFile(new File(PropertiesLoader.getInstance().getTempReportARFF()));
                this.currentArffFile = new File(PropertiesLoader.getInstance().getTempReportARFF());
                this.bufferedReader.close();
                return 100;
            case PERF_STAT:
                this.perfStatDataHolder = new PerfStatDataHolder();
                this.bufferedReader = new BufferedReader(new FileReader(this.currentRawFile));
                this.perfStatDataParser = new PerfStatDataParser(this.bufferedReader, this.perfStatDataHolder);
                this.perfStatDataParser.parse();
                this.perfStatArffDataWriter = new PerfStatArffDataWriter(PropertiesLoader.getInstance().getTempStatARFF(), this.performanceEventsHolder, this.perfStatDataHolder);
                this.perfStatArffDataWriter.writeToArffFile();
                this.setLocalArffFile(new File(PropertiesLoader.getInstance().getTempStatARFF()));
                this.currentArffFile = new File(PropertiesLoader.getInstance().getTempStatARFF());
                this.bufferedReader.close();
                return 100;
            default:

        }
        return -1;
    }

    @Override
    public int saveArffFile(File fileToSave) throws IOException {
        new FileHandler().copy(this.currentArffFile, fileToSave.getAbsolutePath());
        return 0;
    }

    @Override
    public ArrayList<String> getArffAttributesList() {
        return this.arffHandler.getPerformanceEventsList(this.rawfileconverted);
    }

    @Override
    public DefaultTableModel getArffAttributeInfo(int index) {
        return this.arffHandler.getArffAttributeInfo(index);
    }

    @Override
    public DefaultCategoryDataset getBarChartDataSet(String selectedEvent) {
        this.chartDataGenerator = new ChartDataGenerator();
        return this.chartDataGenerator.getBinnedChartDataSet(null, selectedEvent);
    }

    @Override
    public ArrayList<String> getArchitectureList() {
        ArrayList<String> architectureList = new ArrayList<String>();
        for (Architecture architecture : Architecture.values()) {
            architectureList.add(architecture.toString());
        }
        return architectureList;
    }

    @Override
    public ArrayList<String> getTrainingModels() {
        return null;
    }

    @Override
    public int classifyWholeProgram() throws IOException, ClassificationFailedException {
        this.wholeProgramClassifier = new WholeProgramClassifier();
        this.wholeProgramClassifier.setTrainingDataSource(this.currentTrainingModel);
        this.wholeProgramClassifier.setTestingDataSource(this.currentArffFile.getAbsolutePath());
        this.wholeProgramClassifier.classify();
        this.wholeProgramClassificationResultsBuffer.put(new Timestamp(new Date().getTime()).toString(), (String) this.wholeProgramClassifier.getClassificationResult());
        return 100;
    }

    @Override
    public ArrayList<String> getWholeProgramTimestamps() {
        return new ArrayList<String>(this.wholeProgramClassificationResultsBuffer.keySet());
    }

    @Override
    public String getWholeProgramClassificationResult(String timestamp) throws TimestampNotFoundException {
        if (!this.wholeProgramClassificationResultsBuffer.containsKey(timestamp)) {
            throw new TimestampNotFoundException();
        }
        return this.wholeProgramClassificationResultsBuffer.get(timestamp);
    }

    @Override
    public int classifyFunctionWise() throws IOException, ClassificationFailedException {
        this.functionWiseClassifier = new FunctionWiseClassifier();
        this.functionWiseClassifier.setTrainingDataSource(this.currentTrainingModel);
        this.functionWiseClassifier.setTestingDataSource(this.currentArffFile.getAbsolutePath());
        this.functionWiseClassifier.classify(this.perfReportDataHolder.getFinalSymbolsList());
        this.classificationResult = this.functionWiseClassifier.getClassificationResult();
        return 100;
    }

    @Override
    public int classifyTimeSliced() throws IOException, ClassificationFailedException {
        this.timeslicedClassifier = new TimeslicedClassifier();
        this.timeslicedClassifier.setTrainingDataSource(this.currentTrainingModel);
        this.timeslicedClassifier.setTestingDataSource(this.currentArffFile.getAbsolutePath());
        this.timeslicedClassifier.classify();
        this.classificationResult = this.timeslicedClassifier.getClassificationResult();
        return 100;
    }


    @Override
    public DefaultTableModel getFunctionWiseClassificationResultsTableModel() {
        this.tableDataGenerator = new TableDataGenerator();
        return tableDataGenerator.getFunctionwiseTableModel((TreeMap<String, String>) this.classificationResult);
    }


    @Override
    public XYSeriesCollection getXYChartDataSet() {
        this.chartDataGenerator = new ChartDataGenerator();
        return this.chartDataGenerator.getTimeSlicedChartDataSet((LinkedList<String>) this.classificationResult, "Classification result");
    }

    @Override
    public int exportFunctionWiseResultsAsCSV(File fileToSave) throws IOException {
        new FileHandler().exportFunctionWiseResultsAsCSV((TreeMap<String, String>) this.classificationResult, fileToSave.getAbsolutePath());
        return 100;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int exportTimeSlicedResultsAsCSV(File fileToSave) throws IOException {
        new FileHandler().exportTimeSlicedResultsAsCSV((LinkedList<String>) this.classificationResult, fileToSave.getAbsolutePath());
        return 100;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int exportAsImage() {
        return 0;
    }
}
