package com.moa.global.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public String uploadDiaryImage(String userNickname, UUID imageId, MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String folderPath = "diary-images/" + userNickname + "/" + imageId;

        String fileDirectory = folderPath + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .key(fileDirectory)
                .metadata(Collections.singletonMap("Content-Disposition", "inline"))
                .build();

        RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());

        s3Client.putObject(putObjectRequest, requestBody);

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        return String.valueOf(s3Client.utilities().getUrl(getUrlRequest));
    }

}
