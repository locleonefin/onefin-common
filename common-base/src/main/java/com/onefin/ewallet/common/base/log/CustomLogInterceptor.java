package com.onefin.ewallet.common.base.log;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.status.StatusLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Log interceptor for all connector(s)
 * Capture all log events and truncate all PAN
 */
@Plugin(name = "LogInterceptor", category = "Core", elementType = "rewritePolicy", printObject = true)
public class CustomLogInterceptor implements RewritePolicy {

    protected static final Logger logger = StatusLogger.getLogger();

    /**
     * https://payspacemagazine.com/banks/how-to-find-out-the-issuing-bank-for-a-credit-card/
     * Matches a character in the range "0" to "9" with length 12 to 19
     */
    private static final String _12TO19DIGIT = "[0-9]{12,19}";
    private static final Pattern _12TO19DIGIT_PATTERN = Pattern.compile(_12TO19DIGIT);

    private List<Integer> byPassTruncateList = new ArrayList<>(
        Arrays.asList(55, 155, 255, 355, 455, 555)
    );

    private CustomLogInterceptor() {
    }

    @PluginFactory
    public static CustomLogInterceptor createPolicy() {
        return new CustomLogInterceptor();
    }

    /**
     * Rewrite all log events.
     */
    @Override
    public LogEvent rewrite(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();
        // write your code to manipulate your message here
        String maskedMessage = message;
        try {
            // byPassTruncate if log int level in list
            if (!byPassTruncateList.contains(event.getLevel().intLevel())) {
                maskedMessage = maskBankCard(message);
            }
        } catch (Exception e) {
            maskedMessage = message;
            System.out.println("Failed While Masking!!!!!!!!!!!!!!!!!!!!!!");
        }
        return Log4jLogEvent.newBuilder().setLoggerName(event.getLoggerName()).setMarker(event.getMarker()).setLoggerFqcn(event.getLoggerFqcn()).setLevel(event.getLevel()).setMessage(new SimpleMessage(maskedMessage)).setThrown(event.getThrown()).setContextMap(event.getContextMap()).setContextStack(event.getContextStack()).setThreadName(event.getThreadName()).setSource(event.getSource()).setTimeMillis(event.getTimeMillis()).build();

    }

    /**
     * Add log event to filter pattern
     */
    private String maskBankCard(String message) {
        Matcher matcher = null;
        StringBuffer buffer = new StringBuffer();

        matcher = _12TO19DIGIT_PATTERN.matcher(message);
        maskMatcherCard(matcher, buffer);

        return buffer.toString();
    }

    /**
     * Detect if log event match with log pattern => truncate PAN
     */
    private StringBuffer maskMatcherCard(Matcher matcher, StringBuffer buffer) {
        while (matcher.find()) {
            // Min card length
            String maskTextStart = matcher.group().substring(0, 6);
            String maskTextEnd = matcher.group().substring(matcher.group().length() - 4, matcher.group().length());
            String maskText = maskTextStart + StringUtils.repeat("*", matcher.group().length() - 10) + maskTextEnd;
            matcher.appendReplacement(buffer, maskText);
        }
        matcher.appendTail(buffer);
        return buffer;
    }
}