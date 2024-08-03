package com.scm.SCM.services.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.SCM.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String imageUpload(MultipartFile contactImage) {
        String filename= UUID.randomUUID().toString();
        try{
            byte[] data=new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id",filename
            ));
        return this.getUrlFromPublicId(filename);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url().transformation(
                new Transformation<>().width(500).height(500).crop("fill")
        ).generate(publicId);
    }
}
