package com.example.managerfeedback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudflareService {

    @Value("${cloudflare.bearer}")
    private String bearer;

    @Value("${cloudflare.endpoint}")
    private String endpoint;

    @Value("${cloudflare.account}")
    private String account;

    @Qualifier("restTemplate")
    final RestTemplate restTemplate;

    final ResourceLoader resourceLoader;

    public ResponseEntity<String> imageUpload(MultipartFile multipartFile) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Authorization", "Bearer " + bearer);

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            map.add("file", multipartFile.getResource());

            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map,
                    headers);

            String url = endpoint + "/accounts/" + account + "/images/v1";
            System.out.println("url: " + url);

            ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> imageClone(String fromUrl) throws IOException {

        String tempFile = resourceLoader.getResource("test.png").getFile().getAbsolutePath();

        download(fromUrl, tempFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + bearer);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", new FileSystemResource(tempFile));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map,
                headers);

        String url = endpoint + "/accounts/" + account + "/images/v1";

        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        new File(tempFile).delete();
        return response;
    }

    public void download(String url, String toLocation) {
        try {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(toLocation);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            log.warn("Malformed URL: " + url);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.warn("File not found: " + toLocation);
            e.printStackTrace();
        } catch (IOException e) {
            log.warn("IOException: " + url + " " + toLocation);
            e.printStackTrace();
        }
    }
}
