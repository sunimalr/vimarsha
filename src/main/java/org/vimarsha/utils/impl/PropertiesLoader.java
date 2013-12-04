/*
 * vimarsha, Performance analysis: Machine Learning Approach
 * Copyright (C) 2013 vimarsha
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.vimarsha.utils.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A singleton class which is used to load the properties from the config.properties file.
 *
 * @author gayashan
 */
public class PropertiesLoader {
    private static PropertiesLoader ourInstance = null;
    private Properties properties;

    /**
     * Returns the instance of PropertiesLoader
     *
     * @return PropertiesLoader
     */
    public static PropertiesLoader getInstance() {
        if (ourInstance == null) {
            ourInstance = new PropertiesLoader();
        }
        return ourInstance;
    }

    /**
     * private constructor
     */
    private PropertiesLoader() {
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream("config/config.properties"));
        } catch (IOException e) {
        }
    }

    /**
     * Returns the program name
     *
     * @return String
     */
    public String getProgramName() {
        return this.properties.getProperty("name");
    }

    /**
     * Returns the xml file name
     *
     * @return String
     */
    public String getEventsXMLFileName() {
        return this.properties.getProperty("events");
    }

    /**
     * Returns the temporary report arff file name
     *
     * @return String
     */
    public String getTempReportARFF() {
        return this.properties.getProperty("report_arff");
    }

    /**
     * Returns the temporary stat arff file name
     *
     * @return String
     */
    public String getTempStatARFF() {
        return this.properties.getProperty("stat_arff");
    }

    /**
     * Returns the attribute header
     *
     * @return String
     */
    public String getAttributeHeader() {
        return this.properties.getProperty("attribute") + " ";
    }

    /**
     * Returns the relation header
     *
     * @return String
     */
    public String getRelationHeader() {
        return this.properties.getProperty("relation") + " ";
    }

    /**
     * Returns the numeric value header name
     *
     * @return String
     */
    public String getNumericValueHeaderName() {
        return " " + this.properties.getProperty("numeric");
    }

    /**
     * Returns the status header
     *
     * @return String
     */
    public String getStatusHeader() {
        return this.properties.getProperty("status_header");
    }

    /**
     * Returns the classification result string
     *
     * @return String
     */
    public String getClassificationResultString() {
        return this.properties.getProperty("classification") + " ";
    }

    /**
     * Returns a line of hyphens
     *
     * @return String
     */
    public String getLineOfHyphens() {
        return this.properties.getProperty("line") + System.getProperty("line.separator");
    }


    /**
     * Returns the data header
     *
     * @return String
     */
    public String getDataHeader() {
        return this.properties.getProperty("data");
    }

    /**
     * Returns the event line detector string
     *
     * @return String
     */
    public String getEventLineDetector() {
        return this.properties.getProperty("event_line_detector");
    }

    /**
     * Returns the thousand suffix
     *
     * @return String
     */
    public String getThousandSuffix() {
        return this.properties.getProperty("thousand_suffix");
    }

    /**
     * Returns the comment prefix
     *
     * @return String
     */
    public String getCommentPrefix() {
        return this.properties.getProperty("comment_prefix");
    }

    /**
     * Returns the separator regex
     *
     * @return String
     */
    public String getSeparatorRegex() {
        return this.properties.getProperty("separator");
    }

    /**
     * Returns the spaces regex
     *
     * @return String
     */
    public String getSpacesRegex() {
        return " " + this.properties.getProperty("spaces");
    }

    /**
     * Returns the function id string
     *
     * @return String
     */
    public String getFunctionIDString() {
        return " " + this.properties.getProperty("function_id_string") + " ";
    }

    /**
     * Returns the missing value indicator
     *
     * @return String
     */
    public String getMissingValueIndicator() {
        return this.properties.getProperty("missing_value_indicator");
    }

    /**
     * Returns the value separator
     *
     * @return String
     */
    public String getValueSeparator() {
        return this.properties.getProperty("value_separator");
    }

    /**
     * Returns the arff comment string
     *
     * @return String
     */
    public String getARFFCommentString() {
        return this.properties.getProperty("arff_comment") + " ";
    }

    /**
     * Returns not counted value indicator
     *
     * @return String
     */
    public String getNotCountedValueIndicator() {
        return this.properties.getProperty("not_counted_value");
    }

    /**
     * Returns the badma class name
     *
     * @return String
     */
    public String getBADMAClass() {
        return this.properties.getProperty("badma");
    }

    /**
     * Returns the badfs class name
     *
     * @return String
     */
    public String getBADFSClass() {
        return this.properties.getProperty("badfs");
    }

    /**
     * Returns the good class name
     *
     * @return String
     */
    public String getGOODClass() {
        return this.properties.getProperty("good");
    }

    /**
     * Returns the perf event prefix 1
     *
     * @return String
     */
    public String getPerfEventPrettyPrefix1() {
        return this.properties.getProperty("perf_event_pretty_prefix1");
    }

    /**
     * Returns the perf event prefix 2
     *
     * @return String
     */
    public String getPerfEventPrettyPrefix2() {
        return this.properties.getProperty("perf_event_pretty_prefix2");
    }

    /**
     * Returns the perf event prefix 3
     *
     * @return String
     */
    public String getPerfEventPrettyPrefix3() {
        return this.properties.getProperty("perf_event_pretty_prefix3");
    }

    /**
     * Returns the perf event prefix
     *
     * @return String
     */
    public String getPerfEventPrefix() {
        return this.properties.getProperty("perf_event_prefix");
    }

    /**
     * Returns xml tag for architecture
     *
     * @return String
     */
    public String getXMLTagArchitecture() {
        return this.properties.getProperty("architecture");
    }

    /**
     * Returns the xml tag for id
     *
     * @return String
     */
    public String getXMLTagID() {
        return this.properties.getProperty("id");
    }

    /**
     * Returns the xml tag for instruction count
     *
     * @return String
     */
    public String getXMLTagInsCount() {
        return this.properties.getProperty("instruction-count");
    }

    /**
     * Returns the xml tag for event
     *
     * @return String
     */
    public String getXMLTagEvent() {
        return this.properties.getProperty("event");
    }

    /**
     * Returns the xml tag for training model
     *
     * @return String
     */
    public String getXMLTagTrainingModel() {
        return this.properties.getProperty("training-model");
    }

    /**
     * Returns the xml tag for relation header
     *
     * @return
     */
    public String getXMLTagRelationHeader() {
        return this.properties.getProperty("relation-header");
    }
}
