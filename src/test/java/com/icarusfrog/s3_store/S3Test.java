package com.icarusfrog.s3_store;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Paths;

public class S3Test {
    private static final String BUCKET_NAME = "com.icarusfrog.test-bucket-2024-11-17";
    private static final String KEY_NAME = "test-key";
    S3AsyncClient s3AsyncClient;
    public S3Test() {
        s3AsyncClient = S3AsyncClient.crtBuilder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }

    @Test
    void test() {
        s3AsyncClient.createBucket(req -> req.bucket(BUCKET_NAME)).join();
        PutObjectResponse putObjectResponse = s3AsyncClient.putObject(
                req -> req.bucket(BUCKET_NAME).key(KEY_NAME),
                AsyncRequestBody.fromFile(Paths.get("D:\\dev\\java\\s3-store\\src\\test\\resources\\testFile.txt"))
        ).join();

        GetObjectResponse getObjectResponse = s3AsyncClient.getObject(
                req -> req.bucket(BUCKET_NAME).key(KEY_NAME),
                AsyncResponseTransformer.toFile(Paths.get("D:\\dev\\java\\s3-store\\src\\test\\resources\\testFile2.txt"))
        ).join();
    }
}
