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

    public static final String PATTERN_EMAIL =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PATTERN_PASSWORD =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    public static final String PATTERN_USERNAME = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$";
    private static final String PATTERN_FULLNAME = "^[a-z]*[A-Z]$";


    public static String buildURI(String staticContext, String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }
        return staticContext + filePath;
    }
//    public static String saveImage(MultipartFile uploadedFile) {
//        return save(uploadedFile, FOLDER_NAME_IMAGE);
//    }

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

    public static boolean validateLanguage(String language) {
        List<String> fileTypeList = Arrays.asList(PropertiesUtil.getProperty("i18n.languages").split(","));
        if (StringUtils.isEmpty(language) || !fileTypeList.contains(language.toLowerCase())) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        if (!email.matches(PATTERN_EMAIL)) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        if (!password.matches(PATTERN_PASSWORD)) {
            return false;
        }
        return true;
    }

    public static boolean validateUser(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        if (!username.matches(PATTERN_USERNAME)) {
            return false;
        }
        return true;
    }

    public static boolean isValid(final String username) {

        if (username == null || username.length() == 0) {
            return false;
        }

        if (username.length() < 5 || username.length() > 30) {
            return false;
        }
        return true;
    }

    public static boolean validateFullName(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        if (!username.matches(PATTERN_FULLNAME)) {
            return false;
        }

        if (username == null || username.length() == 0) {
            return false;
        }

        if (username.length() < 5 || username.length() > 30) {
            return false;
        }
        return true;
    }
}
