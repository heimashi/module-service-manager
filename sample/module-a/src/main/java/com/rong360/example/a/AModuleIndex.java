package com.rong360.example.a;

import android.view.View;

import com.rong360.msm.api.ModuleInterface;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AModuleIndex implements ModuleInterface {

    private static final HashMap<String, Class<?>> serviceMap = new HashMap<String, Class<?>>();
    private static final HashMap<String, Class<? extends View>> viewMap = new HashMap<String, Class<? extends View>>();

    public AModuleIndex() {
        serviceMap.put("AModuleCalculateService", com.rong360.example.a.AModuleCalculateService.class);
        serviceMap.put("AModuleCalculateService2", com.rong360.example.a.AModuleCalculateService2.class);
        viewMap.put("AModuleView", com.rong360.example.a.AModuleView.class);
    }

    @NotNull
    @Override
    public HashMap<String, Class<? extends View>> getModuleView() {
        return viewMap;
    }

    @NotNull
    @Override
    public HashMap<String, Class<?>> getModuleService() {
        return serviceMap;
    }
}
