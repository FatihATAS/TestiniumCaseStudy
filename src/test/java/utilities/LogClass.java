package utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class LogClass {
    static Logger logger;
    public LogClass(){
        PropertyConfigurator.configure("log4j.properties");
        logger = Logger.getLogger(LogClass.class.getName());
    }
    public void info(String message){
        logger.info(message);
    }
    public void warn(String message){
        logger.warn(message);
    }
    public void error(String message){
        logger.error(message);
    }
    public void fatal(String message){
        logger.fatal(message);
    }
    public void debug(String message){
        logger.debug(message);
    }

}
