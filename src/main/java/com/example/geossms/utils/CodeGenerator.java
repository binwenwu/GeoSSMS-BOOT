package com.example.geossms.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

/**
 * mp代码生成器
 * by 吴斌文
 * @since 2023-04-26
 */
public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }


    @Value("${server.ip}")
    private static String serverIp;

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://"+serverIp+":31621/geossms?serverTimezone=GMT%2b8", "root", "VubCMiHvT1")
                .globalConfig(builder -> {
                    builder.author("珞珈壹佰叁") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\Jiaoshou\\Desktop\\GeoSSMS-BOOT\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.geossms") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\Jiaoshou\\Desktop\\GeoSSMS-BOOT\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("sys_file") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
