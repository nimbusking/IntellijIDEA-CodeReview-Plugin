package com.veezean.idea.plugin.codereviewer.util;

import cn.hutool.core.io.FileUtil;
import com.veezean.idea.plugin.codereviewer.common.LocalDirUtil;
import com.veezean.idea.plugin.codereviewer.consts.Constants;
import com.veezean.idea.plugin.codereviewer.consts.FileConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 日志工具类
 *
 * @author Veezean
 * @since 2023/2/3
 */
public class Logger {

    private static volatile java.util.logging.Logger logger;
    private static final String LOGGER_NAME = "CodeReviewHelperPlugin";
    private static final long DAY_30 = 1000L * 60 * 60 * 24;
    private static final String LOG_TIME_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String LOG_FILE_DATE_FORMAT = "yyyyMMdd";

    static {
        logger = java.util.logging.Logger.getLogger(LOGGER_NAME);
        logger.setLevel(Level.INFO);
        initLoggerDir();
    }

    private synchronized static void initLoggerDir() {
        try {
            File logDir = getLogDir();

            // 清空30天前的日志文件
            FileUtil.loopFiles(logDir, file -> System.currentTimeMillis() - file.lastModified() > DAY_30)
                    .forEach(File::deleteOnExit);
            File logFile = new File(logDir,
                    FileConstants.LOG_FILE_NAME_PREFIX + LocalDate.now().format(DateTimeFormatter.ofPattern(LOG_FILE_DATE_FORMAT)) +
                            FileConstants.LOG_FILE_SUFFIX);

            // 日志文件输出
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            // fix set default charset encoding
            fileHandler.setEncoding(StandardCharsets.UTF_8.toString());
            fileHandler.setFormatter(new LogFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeLogDir() {
        initLoggerDir();
    }

    public static File getLogDir() {
        File logDir = new File(LocalDirUtil.getBaseDir(), FileConstants.LOG_HOME_NAME);
        if (!logDir.exists() || !logDir.isDirectory()) {
            logDir.mkdirs();
        }
        return logDir;
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void error(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static void error(String msg, Throwable e) {
        logger.log(Level.SEVERE, msg, e);
    }

    private static class LogFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder();
            builder.append(LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()),
                    ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern(LOG_TIME_FORMAT_TYPE)))
                    .append(" | ")
                    .append(record.getLevel().getName())
                    .append(" | ");

            // 尝试获取日志打印位置的类名、方法名、代码行号
            try {
                Arrays.stream(Thread.currentThread().getStackTrace())
                        .filter(stackTraceElement -> stackTraceElement.getClassName().startsWith(Constants.BASE_PACKAGE))
                        .filter(stackTraceElement -> !stackTraceElement.getClassName().startsWith(Logger.class.getName()))
                        .findFirst()
                        .ifPresent(stackTraceElement -> {
                            String className = stackTraceElement.getClassName();
                            builder.append(className.substring(className.lastIndexOf(".") + 1)).append(".")
                                    .append(stackTraceElement.getMethodName())
                                    .append("(").append(stackTraceElement.getLineNumber()).append(")");
                        });
            } catch (Exception e) {
                // do nothing...
            }

            builder.append(" | ")
                    .append(record.getThreadID())
                    .append(" | ")
                    .append(record.getMessage())
                    .append(System.lineSeparator());
            Throwable thrown = record.getThrown();
            if (thrown != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                pw.println();
                record.getThrown().printStackTrace(pw);
                builder.append(sw.toString());
                pw.close();
            }
            return builder.toString();
        }
    }
}
