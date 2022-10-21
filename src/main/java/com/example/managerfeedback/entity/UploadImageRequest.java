package com.example.managerfeedback.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadImageRequest {
    MultipartFile file;
}

