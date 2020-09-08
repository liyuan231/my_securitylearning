package com.liyuan.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuan.entity.Rest;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ResponseUtil {
    public static void responseJsonWriter(HttpServletResponse response, Rest<Map<String, Object>> okData) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String restBody = objectMapper.writeValueAsString(okData);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(restBody);
        printWriter.flush();
        printWriter.close();
    }
}
