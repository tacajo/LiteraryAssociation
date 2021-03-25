package com.LiteraryAssociation.config;

import com.LiteraryAssociation.camunda.FileFormFieldType;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.stereotype.Component;

@Component
public class CamundaConfiguration extends AbstractCamundaConfiguration {
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.getCustomFormTypes().add(new FileFormFieldType());
        super.preInit(processEngineConfiguration);
    }
}
