package com.suravi.springdata.miniproject.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiniProjectLogger {
	public static Logger getLogger(Class ClassName) {
        return LoggerFactory.getLogger(ClassName);
    }
}
