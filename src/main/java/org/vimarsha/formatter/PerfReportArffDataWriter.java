package org.vimarsha.formatter;

import org.vimarsha.exceptions.InstructionCountNotSetException;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.utils.PerfReportDataHolder;
import org.vimarsha.utils.PerformanceEventsHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfReportArffDataWriter extends DataWriter {
    private PerfReportDataHolder perfReportDataHolder;

    public PerfReportArffDataWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfReportDataHolder perfReportDataHolder) throws IOException {
        super(fileName, performanceEventsHolder);
        this.perfReportDataHolder = perfReportDataHolder;
    }

    @Override
    public void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        int i = 1;
        for(String symbol : this.perfReportDataHolder.getSymbolsList()){
            for(String event : super.getPerformanceEventsHolder().getEventsHolder()){
                if (!this.perfReportDataHolder.getRawEventsCollection(symbol).contains(event)){
                    this.perfReportDataHolder.addValue(symbol,event,"?");
                }
            }
            //If the instruction_count raw event is set in the performance events and if the instruction count event value is available
            //(not an unknown value "?")
            if(this.perfReportDataHolder.getRawEventsCollection(symbol).contains(this.performanceEventsHolder.getInstructionCountEvent()) &&
                    !(this.perfReportDataHolder.getValue(symbol,this.performanceEventsHolder.getInstructionCountEvent()).equalsIgnoreCase("?"))){
                String out = "";
                this.arffWriter.write("%\n% "+ i + " Function: " + symbol + "\n%\n");
                ++i;

                for (String event: this.performanceEventsHolder.getEventsHolder()){
                    if(this.perfReportDataHolder.getValue(symbol,event).equalsIgnoreCase("?")){
                        out += String.valueOf(this.perfReportDataHolder.getValue(symbol,event)) + ",";
                    } else {
                        out += new BigDecimal(Float.parseFloat(this.perfReportDataHolder.getValue(symbol,event)) * java.lang.Math.pow(10, 9) /
                                Float.parseFloat(this.perfReportDataHolder.getValue(symbol,this.performanceEventsHolder.getInstructionCountEvent()))).setScale(4,RoundingMode.CEILING).toPlainString();
                        /*out += String.valueOf(Float.parseFloat(this.perfReportDataHolder.getValue(symbol,event)) * java.lang.Math.pow(10, 9) /
                                Float.parseFloat(this.perfReportDataHolder.getValue(symbol,this.performanceEventsHolder.getInstructionCountEvent())));*/
                        out += ",";
                    }
                }
                out += "?\n";
                this.arffWriter.write(out);
            }
        }
        this.arffWriter.close();
    }

    @Override
    public void setOutputWriter(OutputWriter outputWriter) {
        this.arffWriter = (ArffWriter) outputWriter;
    }

    @Override
    public void setEventsHolder(PerformanceEventsHolder performanceEventsHolder) {
        this.performanceEventsHolder = performanceEventsHolder;
    }

    public ArrayList<String> getHeaders(){
        return this.headers;
    }
}
