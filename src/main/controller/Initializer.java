package main.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import main.model.Config;

import java.io.File;
import java.io.IOException;

public class Initializer {
    private Config config;

    public Config init() {
        return readProp();
    }

    private Config readProp() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            // Read YAML file into Java object
            config = mapper.readValue(new File("src/resources/config.yaml"), Config.class);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Readeing config failed");
        }
    }
}

