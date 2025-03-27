package com.amcamp.domain.image.api;

import com.amcamp.domain.image.application.ImageService;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import com.google.cloud.firestore.WriteResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public void uploadImage(@RequestPart("wishlistId") String wishlistId,
                            @RequestPart("files") MultipartFile file) {
        imageService.uploadWishlistImage(file, wishlistId);
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadImage(@RequestParam("wishlistId") String wishlistId) {
        byte[] imageData = imageService.downloadImage(wishlistId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.set("Content-Disposition", "attachment; filename=image.jpg");

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
