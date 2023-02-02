package com.example.lan_transmission_tool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.PostConstruct;

//@EnableWebMvc
@Configuration
public class ResourceConfig extends WebMvcConfigurationSupport {

    @Value("${file.path}")
    private String fileRootPath;

    @Value("${file.count:10}")
    private Integer fileCount;

    private String resourceRedisKey = "transmission_tool_files";


    public Integer getFileCount() {
        return fileCount;
    }

    public String getFileRootPath() {
        return fileRootPath;
    }

    public String getResourceRedisKey() {
        return resourceRedisKey;
    }

    @PostConstruct
    public void init(){
        if (!StringUtils.hasLength(fileRootPath)){
            fileRootPath = System.getProperty("user.dir");
        }
        if (!fileRootPath.endsWith("/")){
            fileRootPath = fileRootPath+"/";
            fileRootPath = fileRootPath.replace("\\","/");
        }
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+fileRootPath);
        super.addResourceHandlers(registry);
    }



}
