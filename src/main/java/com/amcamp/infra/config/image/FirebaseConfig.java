package com.amcamp.infra.config.image;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;

    @PostConstruct
    public FirebaseApp firebaseApp() throws IOException {

        ByteArrayInputStream credentialsStream = new ByteArrayInputStream(
                firebaseProperties.configFile().getBytes(StandardCharsets.UTF_8)
        );

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                .setStorageBucket(firebaseProperties.bucket())
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);

        return app;
    }

    @Bean
    public Bucket bucket() throws IOException {
        return StorageClient.getInstance().bucket(firebaseProperties.bucket());
    }

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}
