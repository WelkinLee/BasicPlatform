package fundamental.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: YangRichard
 * Date: 14-10-26
 * Time: 下午9:09
 * To change this template use File | Settings | File Templates.
 */
public final class FundamentalLogger {
    private Logger logger;

    private FundamentalLogger(Class<?> clazz) {
        super();
        logger = LoggerFactory.getLogger(clazz);
    }

    private FundamentalLogger(String loggerName) {
        super();
        logger = LoggerFactory.getLogger(loggerName);
    }

    public static FundamentalLogger getLogger(String loggerName) {
        return new FundamentalLogger(loggerName);
    }

    public static FundamentalLogger getLogger(Class<?> clazz) {
        return new FundamentalLogger(clazz);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(String format,Object... argArray) {
    logger.debug(format, argArray);
    }

    public void debug(String msg, Throwable t) {
        logger.debug(msg, t);
    }

    public void debug(Throwable t) {
        logger.debug(t.getMessage(), t);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(String format, Object... argArray) {
        logger.warn(format, argArray);
    }

    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public void warn(Throwable t) {
        logger.warn(t.getMessage(), t);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(String format, Object... argArray) {
        logger.error(format, argArray);
    }

    public void error(String msg, Throwable t) {
        logger.error(msg, t);
    }

    public void error(Throwable t) {
        logger.error(t.getMessage(), t);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(String format, Object... argArray) {
        logger.info(format, argArray);
    }

    public void info(String msg, Throwable t) {
        logger.info(msg, t);
    }

    public void info(Throwable t) {
        logger.info(t.getMessage(), t);
    }

    public void trace(String msg) {
        logger.trace(msg);
    }

    public void trace(String format, Object... argArray) {
        logger.trace(format, argArray);
    }

    public void trace(String msg, Throwable t) {
        logger.trace(msg, t);
    }

    public void trace(Throwable t) {
        logger.trace(t.getMessage(), t);
    }
}
