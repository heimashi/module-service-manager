package com.rong360.plugin;

import java.util.HashMap;

import org.objectweb.asm.AnnotationVisitor;

public class ModuleAnnotationVisitor extends AnnotationVisitor {

    private String nameString;
    private HashMap<String, String> valueMap;

    ModuleAnnotationVisitor(int api, AnnotationVisitor av, String name, HashMap<String, String> serviceMap) {
        super(api, av);
        nameString = name;
        valueMap = serviceMap;
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        if ("register".equals(name)) {
            valueMap.put((String) value, nameString);
        } else if ("desc".equals(name)) {
            //todo show desc
        }
    }
}
