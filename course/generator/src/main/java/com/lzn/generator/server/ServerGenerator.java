package com.lzn.generator.server;

import com.lzn.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import java.util.*;

import java.io.IOException;

public class ServerGenerator {
    static String toServicePath = "server\\src\\main\\java\\com\\lzn\\service\\";
    public static void main(String[] args) throws IOException, TemplateException {
        String Domain = "Section";
        String domain = "section";
        Map<String, String> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java" ,
                map);
    }
}
