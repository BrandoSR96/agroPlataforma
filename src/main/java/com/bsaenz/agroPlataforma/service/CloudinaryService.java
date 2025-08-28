package com.bsaenz.agroPlataforma.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@ApplicationScoped
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService() {
        Dotenv dotenv = Dotenv.load();
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"),
                "api_key", dotenv.get("CLOUDINARY_API_KEY"),
                "api_secret", dotenv.get("CLOUDINARY_API_SECRET")
        ));
    }

    public String subirImagen(File archivo) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(archivo, ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }

}
