package org.vimarsha.formatter;

import org.vimarsha.exceptions.InstructionCountNotSetException;
import org.vimarsha.exceptions.RawEventNotFoundException;
import org.vimarsha.exceptions.SymbolNotFoundException;
import org.vimarsha.utils.PerfDataHolder;
import org.vimarsha.utils.PerformanceEventsHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PerfDataArffWriter implements DataWriter {
    private ArffWriter arffWriter;
    private PerfDataHolder perfDataHolder;
    private PerformanceEventsHolder performanceEventsHolder;
    private ArrayList<String> headers;

    public PerfDataArffWriter(String fileName, PerformanceEventsHolder performanceEventsHolder, PerfDataHolder perfDataHolder) throws IOException {
        this.headers = new ArrayList<String>();
        this.performanceEventsHolder = performanceEventsHolder;
        this.headers = generateHeaders(this.performanceEventsHolder);
        this.arffWriter = new ArffWriter(fileName,this.headers);
        this.perfDataHolder = perfDataHolder;
    }

    //TODO move constants to a configuration file
    public ArrayList<String> generateHeaders(PerformanceEventsHolder performanceEventsHolder){
        ArrayList<String> headers = new ArrayList<String>();
        headers.add("@relation badfs_badma_good_events_"+ performanceEventsHolder.getArchitecture().toString());
        for(String event: performanceEventsHolder.getPrettyEventsHolder()){
            headers.add("@attribute "+ event + " numeric");
        }
        headers.add("@attribute status {good, badfs, badma}");
        headers.add("@data");
        return headers;
    }

    @Override
    public void writeToArffFile() throws SymbolNotFoundException, InstructionCountNotSetException, RawEventNotFoundException, IOException {
        int i = 1;
        for(String symbol : this.perfDataHolder.getSymbolsList()){
            for(String event : this.performanceEventsHolder.getEventsHolder()){
                if (!this.perfDataHolder.getRawEventsCollection(symbol).contains(event)){
                    this.perfDataHolder.addValue(symbol,event,"?");
                }
            }
            //If the instruction_count raw event is set in the performance events and if the instruction count event value is available
            //(not an unknown value "?")
            if(this.perfDataHolder.getRawEventsCollection(symbol).contains(this.performanceEventsHolder.getInstructionCountEvent()) &&
                    !(this.perfDataHolder.getValue(symbol,this.performanceEventsHolder.getInstructionCountEvent()).equalsIgnoreCase("?"))){
                String out = "";
                this.arffWriter.write("%\n% "+ i + " Function: " + symbol + "\n%\n");
                ++i;

                for (String event: this.performanceEventsHolder.getEventsHolder()){
                    if(this.perfDataHolder.getValue(symbol,event).equalsIgnoreCase("?")){
                        out += String.valueOf(this.perfDataHolder.getValue(symbol,event)) + ",";
                    } else {
                        out += new BigDecimal(Float.parseFloat(this.perfDataHolder.getValue(symbol,event)) * java.lang.Math.pow(10, 9) /
                                Float.parseFloat(this.perfDataHolder.getValue(symbol,this.performanceEventsHolder.getInstructionCountEvent()))).setScale(4,RoundingMode.CEILING).toPlainString();
                        /*out += String.valueOf(Float.parseFloat(this.perfDataHolder.getValue(symbol,event)) * java.lang.Math.pow(10, 9) /
                                Float.parseFloat(this.perfDataHolder.getValue(symbol,this.performanceEventsHolder.getInstructionCountEvent())));*/
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
