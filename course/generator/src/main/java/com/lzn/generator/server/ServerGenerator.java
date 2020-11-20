package com.lzn.generator.server;

import com.lzn.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import java.util.*;

import java.io.IOException;

public class ServerGenerator {
    static String MODULE = "business";
    static String toServicePath = "server\\src\\main\\java\\com\\lzn\\service\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\lzn\\"+MODULE+"\\controller\\admin\\";
    public static void main(String[] args) throws IOException, TemplateException {
        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        Map<String, String> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", MODULE);

        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java" ,
                map);
    }
}
