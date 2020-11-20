package com.lzn.generator.server;

import com.lzn.generator.util.DbUtil;
import com.lzn.generator.util.Field;
import com.lzn.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.omg.CORBA.FREE_MEM;

import java.util.*;

import java.io.IOException;

public class ServerGenerator {
    static String MODULE = "business";
    static String toServicePath = "server\\src\\main\\java\\com\\lzn\\service\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\lzn\\"+MODULE+"\\controller\\admin\\";
    static String toDtoPath = "server\\src\\main\\java\\com\\lzn\\dto\\";

    public static void main(String[] args) throws Exception {
        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        List<Field> fieldList = DbUtil.getColumnByTableName(domain);
        Set<String> typeSet = getJavaTypes(fieldList);

        Map<String, Object> map = new HashMap<>();

        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", MODULE);

        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

//        // 生成dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);

        // 生成service
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java" ,
                map);
        // 生成controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java" ,
                map);



    }

    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
