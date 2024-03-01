package com.ivanfang.fangtimes.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class AwsS3Util {

    private static final String BUCKET = "";
    private static final Region REGION = Region.AP_NORTHEAST_1;
    private static final String ACCESS_KEY_ID = "";
    private static final String SECRET_ACCESS_KEY = "";

    // upload file to aws s3
    public static void upload(String filename, InputStream inputStream) throws IOException {
        // create a client
        S3Client client = S3Client.builder()
                .region(REGION)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY_ID, SECRET_ACCESS_KEY)))
                .build();


        // create a put-object-request
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(filename)
                .build();

        // use client to send the put-object-request
        client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }

}
