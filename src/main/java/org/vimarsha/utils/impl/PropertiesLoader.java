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
 * Created with IntelliJ IDEA.
 * User: gayashan
 */
public class PropertiesLoader {
    private static PropertiesLoader ourInstance = null;
    private Properties properties;

    public static PropertiesLoader getInstance() {
        if (ourInstance == null) {
            ourInstance = new PropertiesLoader();
        }
        return ourInstance;
    }

    private PropertiesLoader() {
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream("config/config.properties"));
        } catch (IOException e) {
        }
    }

    public String getProgramName() {
        return this.properties.getProperty("name");
    }

    public String getEventsXMLFileName() {
        return this.properties.getProperty("events");
    }

    public String getTempReportARFF() {
        return this.properties.getProperty("report_arff");
    }

    public String getTempStatARFF() {
        return this.properties.getProperty("stat_arff");
    }

    public String getAttributeHeader() {
        return this.properties.getProperty("attribute") + " ";
    }

    public String getRelationHeader() {
        return this.properties.getProperty("relation") + " ";
    }

    public String getNumericValueHeaderName() {
        return " " + this.properties.getProperty("numeric");
    }

    public String getStatusHeader() {
        return this.properties.getProperty("status_header");
    }

    public String getDataHeader() {
        return this.properties.getProperty("data");
    }

    public String getEventLineDetector() {
        return this.properties.getProperty("event_line_detector");
    }

    public String getThousandSuffix() {
        return this.properties.getProperty("thousand_suffix");
    }

    public String getCommentPrefix() {
        return this.properties.getProperty("comment_prefix");
    }

    public String getSpacesRegex() {
        return " " + this.properties.getProperty("spaces");
    }

    public String getFunctionIDString() {
        return " " + this.properties.getProperty("function_id_string") + " ";
    }

    public String getMissingValueIndicator() {
        return this.properties.getProperty("missing_value_indicator");
    }

    public String getValueSeparator() {
        return this.properties.getProperty("value_separator");
    }

    public String getARFFCommentString() {
        return this.properties.getProperty("arff_comment") + " ";
    }

    public String getNotCountedValueIndicator() {
        return this.properties.getProperty("not_counted_value");
    }

    public String getBADMAClass() {
        return this.properties.getProperty("badma");
    }

    public String getBADFSClass() {
        return this.properties.getProperty("badfs");
    }

    public String getGOODClass() {
        return this.properties.getProperty("good");
    }

    public String getPerfEventPrettyPrefix1() {
        return this.properties.getProperty("perf_event_pretty_prefix1");
    }

    public String getPerfEventPrettyPrefix2() {
        return this.properties.getProperty("perf_event_pretty_prefix2");
    }

    public String getPerfEventPrettyPrefix3() {
        return this.properties.getProperty("perf_event_pretty_prefix3");
    }

    public String getPerfEventPrefix() {
        return this.properties.getProperty("perf_event_prefix");
    }

    public String getXMLTagArchitecture() {
        return this.properties.getProperty("architecture");
    }

    public String getXMLTagID() {
        return this.properties.getProperty("id");
    }

    public String getXMLTagInsCount() {
        return this.properties.getProperty("instruction-count");
    }

    public String getXMLTagEvent() {
        return this.properties.getProperty("event");
    }

    public String getXMLTagTrainingModel() {
        return this.properties.getProperty("training-model");
    }

    public String getXMLTagRelationHeader() {
        return this.properties.getProperty("relation-header");
    }
}
