package com.faniko.api_faniko.config;

import com.faniko.api_faniko.utils.AppConstantsTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile(AppConstantsTest.TEST_PROFILE)
public class MongoTestData implements CommandLineRunner {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.getDb().drop();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:data/*.json");
        for (Resource resource : resources) {
            if (resource.getFilename() == null) {
                continue;
            }
            String collectionName = resource.getFilename().replace(".json", "");

            List<?> data = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Object>>() {});
            mongoTemplate.insert(data, collectionName);
        }
    }
}
