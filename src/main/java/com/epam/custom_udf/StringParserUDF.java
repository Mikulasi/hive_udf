package com.epam.custom_udf;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class StringParserUDF extends UDF {

    private Text result = new Text();

    public Text evaluate(String criteria, String param) {

        if (criteria == null || param == null) {
            throw new IllegalArgumentException("Enter 2 parameters!");
        }
        switch (criteria) {
            case "os":
                result.set(UserAgent.parseUserAgentString(param).getOperatingSystem().getName());
                break;
            case "device":
                result.set(UserAgent.parseUserAgentString(param).getOperatingSystem().getDeviceType().getName());
                break;
            case "browser":
                result.set(UserAgent.parseUserAgentString(param).getBrowser().getName());
                break;
            default:
                throw new IllegalArgumentException("Wrong parameter!!! Enter \"os\", \"device\" or \"browser\"");
        }

        return result;
    }
}
