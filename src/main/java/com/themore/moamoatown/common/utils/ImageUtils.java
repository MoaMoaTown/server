package com.themore.moamoatown.common.utils;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.io.IOException;

/**
 * url로 저장된 이미지를 base64 인코딩
 * @author 임원정
 * @since 2024.09.01
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.09.01  	임원정       최초 생성
 * </pre>
 */

@Log4j
public class ImageUtils {
    public static String encodeImageToBase64(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream is = url.openStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = os.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}