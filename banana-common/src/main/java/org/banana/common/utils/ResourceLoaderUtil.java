package org.banana.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResourceLoaderUtil implements ResourceLoaderAware {
    private Logger logger = LoggerFactory.getLogger(ResourceLoaderUtil.class);

    private static ResourceLoader rsl;

    private static Map<String,String> modelMap ;
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = new Resource[0];
        try {
            resources = resolver.getResources("classpath*:org/banana/**/model/**/*.class");
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelMap = new HashMap<>();
        for (Resource r : resources) {
            MetadataReader reader = null;
            try {
                reader = metaReader.getMetadataReader(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String className = reader.getClassMetadata().getClassName();
            String modelName = className.substring(className.lastIndexOf(".")+1);
            if(!className.contains("$")) {
                modelMap.put(modelName, className);
            }
        }
    }
    public static Map<String,String> getModelMap(){
        return modelMap;
    }
}
