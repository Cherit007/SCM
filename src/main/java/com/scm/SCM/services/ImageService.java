package com.scm.SCM.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String imageUpload(MultipartFile contactImage);
    String getUrlFromPublicId(String publicId);
}
