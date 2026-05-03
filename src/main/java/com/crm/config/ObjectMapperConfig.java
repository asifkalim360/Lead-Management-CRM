package com.crm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperConfig
{
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }
}
