package com.tendyron.wifi.web.logger;

import com.tendyron.wifi.web.logger.LogOperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for business log
 * 
 * @author Neo
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BusinessLog {

	public String content();

	public LogOperationType operation();
}
