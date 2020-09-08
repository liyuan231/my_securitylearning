package com.liyuan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestUtil {
    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    private RequestUtil() {
    }

    public static String obtainBody(ServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = request.getReader();) {
            String aLine = null;
            while ((aLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(aLine);
            }
        } catch (IOException e) {
            logger.error("RequestBody read error!");
        }
        return stringBuilder.toString();
    }
}
