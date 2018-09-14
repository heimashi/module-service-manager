package com.rong360.msm.api;

import com.google.auto.service.AutoService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class MsmAnnotationProcessor extends AbstractProcessor {

    private static final String OPTION_MSM_INDEX = "MSM_INDEX";
    private Messager messager;
    private final HashMap<String, String> serviceClassMap = new HashMap<>();
    private final HashMap<String, String> serviceDescMap = new HashMap<>();
    private final HashMap<String, String> viewClassMap = new HashMap<>();
    private final HashMap<String, String> viewDescMap = new HashMap<>();
    private String msmIndex;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnv.getMessager();
        msmIndex = processingEnv.getOptions().get(OPTION_MSM_INDEX);
        if (msmIndex == null) {
            messager.printMessage(Diagnostic.Kind.ERROR, "MSM::No option " + OPTION_MSM_INDEX +
                    " passed to annotation processor");
            throw new RuntimeException("MSM::Please set MSM_INDEX in build.gradle");
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "MSM::msmIndex is " + msmIndex);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ModuleService.class.getCanonicalName());
        types.add(ModuleView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return false;
        }
        collectModuleService(roundEnvironment);
        collectModuleView(roundEnvironment);
        if (!(serviceClassMap.isEmpty()&&viewClassMap.isEmpty())) {
            createInfoIndexFile();
        } else {
            messager.printMessage(Diagnostic.Kind.WARNING, "MSM::No @ModuleService and @ModuleVie annotations found");
        }
        return false;
    }

    private void collectModuleService(RoundEnvironment env) {
        Set<? extends Element> routeElements = env.getElementsAnnotatedWith(ModuleService.class);
        if (routeElements == null || routeElements.isEmpty()) {
            return;
        }
        for (Element element : routeElements) {
            if (element instanceof TypeElement) {
                TypeElement classElement = (TypeElement) element;
                String fullClassName = classElement.getQualifiedName().toString();
                ModuleService moduleService = element.getAnnotation(ModuleService.class);
                String serviceName = moduleService.register();
                String desc = moduleService.desc();
                if (!"".equals(desc)) {
                    serviceDescMap.put(serviceName, desc);
                }
                if (serviceClassMap.containsKey(serviceName)) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "MSM::Duplicate service name: " + serviceName);
                    throw new RuntimeException("MSM::Please fix duplicate service name: " + serviceName);
                }
                messager.printMessage(Diagnostic.Kind.NOTE, "MSM::add service name:" + serviceName + " class:" +
                        fullClassName);
                serviceClassMap.put(serviceName, fullClassName);
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "@ModuleService is only valid for type", element);
            }
        }
    }

    private void collectModuleView(RoundEnvironment env) {
        Set<? extends Element> routeElements = env.getElementsAnnotatedWith(ModuleView.class);
        if (routeElements == null || routeElements.isEmpty()) {
            return;
        }
        for (Element element : routeElements) {
            if (element instanceof TypeElement) {
                TypeElement classElement = (TypeElement) element;
                String fullClassName = classElement.getQualifiedName().toString();
                ModuleView moduleView = element.getAnnotation(ModuleView.class);
                String viewName = moduleView.register();
                String desc = moduleView.desc();
                if (!"".equals(desc)) {
                    viewDescMap.put(viewName, desc);
                }
                if (viewClassMap.containsKey(viewName)) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "MSM::Duplicate view name: " + viewName);
                    throw new RuntimeException("MSM::Please fix duplicate view name: " + viewName);
                }
                messager.printMessage(Diagnostic.Kind.NOTE, "MSM::add view name:" + viewName + " class:" +
                        fullClassName);
                viewClassMap.put(viewName, fullClassName);
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "@ModuleView is only valid for type", element);
            }
        }
    }


    private void createInfoIndexFile() {
        BufferedWriter writer = null;
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(msmIndex);
            int period = msmIndex.lastIndexOf('.');
            String myPackage = period > 0 ? msmIndex.substring(0, period) : null;
            String clazz = msmIndex.substring(period + 1);
            writer = new BufferedWriter(sourceFile.openWriter());
            if (myPackage != null) {
                writer.write("package " + myPackage + ";\n\n");
            }
            writer.write("import java.util.HashMap;\n");
            writer.write("import android.view.View;\n");
            writer.write("import org.jetbrains.annotations.NotNull;\n");
            writer.write("import com.rong360.msm.api.ModuleInterface;\n\n");
            writer.write("/** This class is generated by MSM, do not edit. */\n");
            writer.write("public class " + clazz + " implements ModuleInterface{\n\n");
            writer.write("\n    private static final HashMap<String, Class<?>> serviceMap = new HashMap<String, Class<?>>();"
                    + "\n");
            writer.write("    private static final HashMap<String, Class<? extends View>> viewMap = new HashMap<String, Class<? extends View>>();\n");
            writer.write("\n    public " + clazz + "() {\n");
            writeIndexLines(writer);
            writer.write("    }\n\n");
            writer.write("    @NotNull\n     @Override\n"
                    + "    public HashMap<String, Class<? extends View>> getModuleView() {\n"
                    + "        return viewMap;\n"
                    + "    }\n\n");
            writer.write("    @NotNull\n     @Override\n"
                    + "    public HashMap<String, Class<?>> getModuleService() {\n"
                    + "        return serviceMap;\n"
                    + "    }");
            writer.write("\n\n}\n");
        } catch (IOException e) {
            throw new RuntimeException("Could not write source for " + msmIndex, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    //Silent
                }
            }
        }
    }


    private void writeIndexLines(BufferedWriter writer) throws IOException {
        for (String serviceName : serviceClassMap.keySet()) {
            String desc = serviceDescMap.get(serviceName);
            String fullClass = serviceClassMap.get(serviceName);
            if (desc != null && !"".equals(desc)) {
                writer.write("         /** " + desc + " */\n");
            }
            writer.write("         serviceMap.put(\"" + serviceName + "\", " + fullClass + ".class );\n");
        }
        for (String serviceName : viewClassMap.keySet()) {
            String desc = viewDescMap.get(serviceName);
            String fullClass = viewClassMap.get(serviceName);
            if (desc != null && !"".equals(desc)) {
                writer.write("         /** " + desc + " */\n");
            }
            writer.write("         viewMap.put(\"" + serviceName + "\", " + fullClass + ".class );\n");
        }
    }

}
