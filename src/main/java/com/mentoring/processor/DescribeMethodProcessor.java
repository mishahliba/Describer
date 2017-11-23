package com.mentoring.processor;

import com.mentoring.annotation.DescribeMethods;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by beerman on 23.11.2017.
 */
@SupportedAnnotationTypes("com.mentoring.annotation.DescribeMethods")
@SupportedSourceVersion( SourceVersion.RELEASE_7 )
public class DescribeMethodProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Collection<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(DescribeMethods.class);
        List<TypeElement> types = ElementFilter.typesIn(annotatedElements);

        String className=null;
        for(TypeElement type : types){
            className = String.valueOf(type.getAnnotation(DescribeMethods.class).value());
        }
        System.out.println("Found annotation with class : " + className);
        return true;
    }
}
