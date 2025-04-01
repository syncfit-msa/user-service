package com.amcamp.infra.config.aws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
@Profile("prod")
public class EcsMetadataInstanceConfig implements ApplicationRunner {

    @Autowired
    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String metadataUri = System.getenv("ECS_CONTAINER_METADATA_URI_V4");
        if (metadataUri != null) {
            URL url = new URL(metadataUri + "/task");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(connection.getInputStream());

            JsonNode containers = rootNode.path("Containers");
            if (containers.isArray() && containers.size() > 0) {
                JsonNode networks = containers.get(0).path("Networks");
                if (networks.isArray() && networks.size() > 0) {
                    String privateIPv4Address = networks.get(0).path("IPv4Addresses").get(0).asText();

                    System.out.println("ECS Task ENI Private IP: " + privateIPv4Address);

                    eurekaInstanceConfigBean.setIpAddress(privateIPv4Address);
                    eurekaInstanceConfigBean.setPreferIpAddress(true);

                    String instanceId = privateIPv4Address + ":" + eurekaInstanceConfigBean.getAppname() + ":" +
                            eurekaInstanceConfigBean.getNonSecurePort();
                    eurekaInstanceConfigBean.setInstanceId(instanceId);

                    System.out.println("Updated Eureka registration with correct IP: " + privateIPv4Address);
                }
            }
        }
    }
}