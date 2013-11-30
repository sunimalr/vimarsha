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
import org.jfree.data.xy.XYSeries;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private Object classificationResult;
    private HashMap<String,String> wholeProgramClassificationResultsBuffer;
    private Architecture currentArchitecture;
    private Instances currentTrainingModel;
    private File currentRawFile;
    private File currentArffFile;
    private DataFileType dataFileType;
    private ArffHandler arffHandler;
    private TableDataHandler tableDataHandler;
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
        this.arffHandler = new ArffHandler(reader,this.performanceEventsHolder);
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
        switch (architecture){
            case INTEL_NEHALEM:
                this.currentTrainingModel = new Instances(new BufferedReader(new FileReader("resources/intel_nehalem_training.arff")));
                if(this.currentTrainingModel.classIndex() == -1){
                    this.currentTrainingModel.setClassIndex(this.currentTrainingModel.numAttributes() - 1);
                }
                return 100;
            case POWER7:
                this.currentTrainingModel = new Instances(new BufferedReader(new FileReader("resources/ibm_power7_training.arff")));
                if(this.currentTrainingModel.classIndex() == -1){
                    this.currentTrainingModel.setClassIndex(this.currentTrainingModel.numAttributes() - 1);
                }
                return 100;
            default:
                this.currentTrainingModel = null;
        }
        return -1;
    }

    public String getTrainingModel() throws TrainingModelNotSetException {
        if(this.currentTrainingModel == null){
            throw new TrainingModelNotSetException();
        }
        return this.currentTrainingModel.relationName();
    }

    @Override
    public String getArchitecture() throws ArchitectureNotSetException {
        if(this.currentArchitecture == null){
            throw new ArchitectureNotSetException();
        }
        return this.currentArchitecture.name();
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
                this.currentArffFile = new File("output/tempreport.arff");
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
                this.currentArffFile = new File("output/tempstat.arff");
                this.bufferedReader.close();
                return 100;
            default:

        }
        return -1;
    }

    @Override
    public int saveArffFile(File fileToSave) throws IOException {
        new FileHandler().copy(this.currentArffFile,fileToSave.getAbsolutePath());
        return 0;
    }

    @Override
    public ArrayList<String> getArffAttributesList(){
        return this.arffHandler.getPerformanceEventsList(this.rawfileconverted);
    }

    @Override
    public DefaultTableModel getArffAttributeInfo(int index) {
        return this.arffHandler.getArffAttributeInfo(index);
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
    public int classifyWholeProgram() throws IOException, ClassificationFailedException {
        this.wholeProgramClassifier = new WholeProgramClassifier();
        this.wholeProgramClassifier.setTrainingDataSource(this.currentTrainingModel);
        this.wholeProgramClassifier.setTestingDataSource(this.currentArffFile.getAbsolutePath());
        this.wholeProgramClassifier.classify();
        this.wholeProgramClassificationResultsBuffer.put(new Timestamp(new Date().getTime()).toString(),(String)this.wholeProgramClassifier.getClassificationResult());
        return 0;
    }

    @Override
    public ArrayList<String> getWholeProgramTimestamps() {
        return new ArrayList<String>(this.wholeProgramClassificationResultsBuffer.keySet());
    }

    @Override
    public String getWholeProgramClassificationResult(String timestamp) throws TimestampNotFoundException {
        if(!this.wholeProgramClassificationResultsBuffer.containsKey(timestamp)){
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
        return 0;
    }

    @Override
    public int classifyTimeSliced() {
        return 0;
    }


    @Override
    public DefaultTableModel getFunctionWiseClassificationResultsTableModel() {
        tableDataHandler = new TableDataHandler(new ArrayList<String>(((HashMap<String,String>)this.classificationResult).keySet()));
        return tableDataHandler.getFunctionwiseTableModel(new ArrayList<String>(((HashMap<String,String>)this.classificationResult).values()));
    }


    @Override
    public XYSeriesCollection getXYChartDataSet() {
        XYSeries series =  new XYSeries("Classified result");
        series.add(1,1);
        series.add(2,0);
        series.add(3,1);
        series.add(4,1);
        series.add(6,0);
        series.add(7,1);
        series.add(8,0);
        series.add(9,1);
        series.add(10,1);
        series.add(11,0);
        series.add(12,1);
        series.add(13,0);
        series.add(14,1);
        series.add(15,1);
        series.add(16,0);
        series.add(17,0);
        series.add(18,0);
        series.add(19,1);
        series.add(20,1);
        series.add(21,0);
        series.add(22,1);
        series.add(23,0);
        series.add(24,1);
        series.add(25,1);
        series.add(26,0);
        series.add(27,0);
        series.add(28,0);
        series.add(29,1);
        series.add(30,1);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
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
