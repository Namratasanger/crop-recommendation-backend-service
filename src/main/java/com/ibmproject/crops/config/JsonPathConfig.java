package com.ibmproject.crops.config;

import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;
import java.util.Set;

@Configuration
public class JsonPathConfig implements CommandLineRunner {
    private final JsonProvider jsonProvider = new JacksonJsonProvider();
    private final MappingProvider mappingProvider = new JacksonMappingProvider();

    @Override
    public void run(String... args) throws Exception {
        com.jayway.jsonpath.Configuration
                .setDefaults(
                        new com.jayway.jsonpath.Configuration.Defaults() {
                            @Override
                            public JsonProvider jsonProvider() {
                                return jsonProvider;
                            }

                            @Override
                            public Set<Option> options() {
                                return EnumSet.noneOf(Option.class);
                            }

                            @Override
                            public MappingProvider mappingProvider() {
                                return mappingProvider;
                            }
                        }
                );
    }
}
