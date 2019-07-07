package com.niushengqiang.autostart.startter.third;

import com.niushengqiang.autostart.startter.first.A;
import com.niushengqiang.autostart.startter.secound.B;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;


/**
 * 动态加载bean的程序
 */
public class DyamicLoadBeanSelector implements ImportSelector {


    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        System.out.println(annotationTypes);
        String[] a={A.class.getName()};
        return a;
    }

}
