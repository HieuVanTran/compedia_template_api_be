package vn.compedia.api.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import vn.compedia.api.utility.PropertiesUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;


@Log4j2
public class StringUtil {

    public static String buildURI(String staticContext, String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }
        return staticContext + filePath;
    }

    public static String base64Encode(String originalInput) {
        return new String(Base64.encodeBase64(originalInput.getBytes()));
    }

    public static String encryptPassword(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-256");
            crypt.reset();
            crypt.update(password.getBytes(StandardCharsets.UTF_8));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("[encryptPassword|NoSuchAlgorithmException] cause error", e);
            e.printStackTrace();
        }
        return sha1;
    }

    public static String encryptPassword(String password, String salt) {
        return encryptPassword(password + salt);
    }

    public static String generateOtp() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public static String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static Object jsonToObject(String json, Class<?> objectClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(json, objectClass);
        } catch (IOException e) {
            log.error("[jsonToObject] cause error", e);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateLanguge(String language) {
        List<String> fileTypeList = Arrays.asList(PropertiesUtil.getProperty("i18n.languages").split(","));
        if (StringUtils.isEmpty(language) || !fileTypeList.contains(language.toLowerCase())) {
            return false;
        }
        return true;
    }
}
