package org.vimarsha.formatter.impl;

import org.vimarsha.exceptions.InstructionCountNotSetException;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.formatter.DataWriter;
import org.vimarsha.formatter.OutputWriter;
import org.vimarsha.utils.impl.PerfReportDataHolder;
import org.vimarsha.utils.impl.PerformanceEventsHolder;
import org.vimarsha.utils.impl.PropertiesLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * A class which writes the parsed data from perf report to an arff file.
 *
 * @author gayashan
 */
public class PerfReportArffDataWriter extends DataWriter {
    private PerfReportDataHolder perfReportDataHolder;

    /**
     * Generates a PerfReportArffDataWriter
     *
     * @param fileName                arff file name
     * @param performanceEventsHolder a PerformanceEventsHolder
     * @param perfReportDataHolder    a PerfReportDataHolder
     * @throws IOException
     */
    public PerfReportArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfReportDataHolder perfReportDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfReportDataHolder = perfReportDataHolder;
    }

    /**
     * Writes to the arff file
     *
     * @throws SymbolNotFoundException   thrown when the symbol(function) is not found
     * @throws InstructionCountNotSetException
     *                                   thrown when instruction count is not set in the configurations
     * @throws RawEventNotFoundException thrown when raw event is not found
     * @throws IOException
     */
    @Override
    public void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        int i = 1;
        //for each event name in performanceEventsHolder check whether it is present in perfReportDataHolder
        //if it doesn't mark it as a missing value
        for (String symbol : this.perfReportDataHolder.getSymbolsList()) {
            for (String event : this.performanceEventsHolder.getEventsHolder()) {
                if (!this.perfReportDataHolder.getRawEventsCollection(symbol).contains(event)) {
                    this.perfReportDataHolder.addValue(symbol, event, PropertiesLoader.getInstance().getMissingValueIndicator());
                }
            }
            //If the instruction_count raw event is set in the performance events and if the instruction count event value is available
            //(not an unknown value "?")
            if (this.perfReportDataHolder.getRawEventsCollection(symbol).contains(this.performanceEventsHolder.getInstructionCountEvent()) &&
                    !(this.perfReportDataHolder.getValue(symbol, this.performanceEventsHolder.getInstructionCountEvent()).equalsIgnoreCase(PropertiesLoader.getInstance().getMissingValueIndicator()))) {
                StringBuilder out = new StringBuilder();
                //write a comment to the arff file the function/symbol name
                this.arffWriter.write(PropertiesLoader.getInstance().getARFFCommentString() + System.getProperty("line.separator") +
                        PropertiesLoader.getInstance().getARFFCommentString() + i + PropertiesLoader.getInstance().getFunctionIDString() + symbol +
                        System.getProperty("line.separator") + PropertiesLoader.getInstance().getARFFCommentString() + System.getProperty("line.separator"));
                //add that symbol to the final list of perfReportDataHolder so it can be used when function wise classification results are displayed
                this.perfReportDataHolder.addToFinalSymbolsList(symbol);
                ++i;

                for (String event : this.performanceEventsHolder.getEventsHolder()) {
                    //if the current value is missing
                    if (this.perfReportDataHolder.getValue(symbol, event).equalsIgnoreCase(PropertiesLoader.getInstance().getMissingValueIndicator())) {
                        out.append(String.valueOf(this.perfReportDataHolder.getValue(symbol, event)) + PropertiesLoader.getInstance().getValueSeparator());
                    } else { //if the value is present
                        //calculate the normalized value = performance event count value * 10^9 / number of instructions retired
                        if (!this.perfReportDataHolder.getValue(symbol, this.performanceEventsHolder.getInstructionCountEvent()).equalsIgnoreCase("0.0")) {
                            out.append(new BigDecimal(Float.parseFloat(this.perfReportDataHolder.getValue(symbol, event)) * java.lang.Math.pow(10, 9) /
                                    Float.parseFloat(this.perfReportDataHolder.getValue(symbol, this.performanceEventsHolder.getInstructionCountEvent()))).setScale(4, RoundingMode.CEILING).toPlainString());
                        } else {
                            out.append(PropertiesLoader.getInstance().getMissingValueIndicator());
                        }
                        out.append(PropertiesLoader.getInstance().getValueSeparator());
                    }
                }
                //add the status class - to be classified
                out.append(PropertiesLoader.getInstance().getMissingValueIndicator() + System.getProperty("line.separator"));
                this.arffWriter.write(out.toString());
            }
        }
        this.arffWriter.close();
    }

    /**
     * Set the output writer
     *
     * @param outputWriter an OutputWriter
     */
    @Override
    public void setOutputWriter(OutputWriter outputWriter) {
        this.arffWriter = (ArffWriter) outputWriter;
    }

    /**
     * Set the PerformanceEventsHolder
     *
     * @param performanceEventsHolder a PerformanceEventsHolder
     */
    @Override
    public void setEventsHolder(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
    }

    /**
     * Returns the list of headers
     *
     * @return ArrayList of headers
     */
    public ArrayList<String> getHeaders() {
        return this.headers;
    }
}
