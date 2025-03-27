package com.amcamp.domain.image.application;

import com.amcamp.domain.wishlist.dao.WishlistRepository;
import com.amcamp.domain.wishlist.domain.Wishlist;
import com.amcamp.global.error.exception.CustomException;
import com.amcamp.global.error.exception.ErrorCode;
import com.amcamp.global.util.MemberUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Bucket bucket;
    private final Firestore firestore;
    private final MemberUtil memberUtil;
    private final WishlistRepository wishlistRepository;

    @Transactional
    public void uploadWishlistImage(MultipartFile file, String wishlistId) {
        memberUtil.getCurrentMember();

        try {
            String fileName = saveImage(file);
            String fileUrl =  "https://storage.googleapis.com/" + bucket.getName() + "/" + fileName;

            WriteResult writeResult = storeImageInfo(fileUrl, wishlistId);

            Optional<Wishlist> target = wishlistRepository.findById(Long.parseLong(wishlistId));
            if (target.isPresent()) {
                Wishlist wishlist = target.get();
                wishlist.setImageUrl(fileUrl);
                wishlistRepository.save(wishlist);
            } else {
                throw new CustomException(ErrorCode.WISHLIST_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FIREBASE_UPLOAD_EXCEPTION);
        }
    }

    @Transactional
    public String uploadInitWishlistImage(MultipartFile file) {
        memberUtil.getCurrentMember();

        try {
            String fileName = saveImage(file);

            return "https://storage.googleapis.com/" + bucket.getName() + "/" + fileName;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FIREBASE_UPLOAD_EXCEPTION);
        }
    }

    @Transactional(readOnly = true)
    public byte[] downloadImage(String wishlistId) {
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection("images")
                    .whereEqualTo("wishlist_id", wishlistId)
                    .get();

            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> docs = querySnapshot.getDocuments();
            if (docs.isEmpty()) {
                throw new CustomException(ErrorCode.FIREBASE_IMAGE_NOT_FOUND);
            }

            DocumentSnapshot latestDoc = docs.get(0);
            for (DocumentSnapshot doc : docs) {
                Long currentUploadedAt = doc.getLong("uploaded_at");
                Long latestUploadedAt = latestDoc.getLong("uploaded_at");
                if (currentUploadedAt != null && latestUploadedAt != null && currentUploadedAt > latestUploadedAt) {
                    latestDoc = doc;
                }
            }

            String imageUrl = latestDoc.getString("image_url");
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            try (InputStream in = connection.getInputStream()) {
                return in.readAllBytes();
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FIREBASE_DOWNLOAD_ERROR);
        }
    }

    private String saveImage(MultipartFile file) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String hashedFileName = hashFilename(file.getOriginalFilename());
            String fileName = "wishlist/" + hashedFileName + "_" + timestamp;

            InputStream content = file.getInputStream();
            Blob blob = bucket.create(fileName, content, file.getContentType());
            blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            return fileName;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FIREBASE_UPLOAD_EXCEPTION);
        }
    }

    public WriteResult storeImageInfo(String fileUrl, String wishlistId) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("wishlist_id", wishlistId);
            data.put("image_url", fileUrl);
            data.put("uploaded_at", System.currentTimeMillis());

            ApiFuture<WriteResult> writeResult = firestore.collection("images").document().set(data);
            return writeResult.get();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FIREBASE_UPLOAD_EXCEPTION);
        }

    }

    private String hashFilename(String filename) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(filename.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }

            String fullHash = sb.toString();
            return fullHash.substring(0, Math.min(25, fullHash.length()));
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorCode.FIREBASE_UPLOAD_EXCEPTION);
        }
    }
}