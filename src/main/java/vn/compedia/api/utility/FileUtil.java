package vn.compedia.api.utility;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.util.DateUtil;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class FileUtil {

    public final static String EXT_PDF = "pdf";
    public final static String EXT_OFFICE = "xls,xlsx,doc,docx,ppt";
    private static final String FOLDER_NAME_PARENT = "resources";
    private static final String FOLDER_NAME_IMAGE = "image";
    private static final String FOLDER_NAME_FILE = "upload_file";
    private static final String SEPARATOR = "/";

    // Save file if success then return file path, else return null
    public static Map<String, String> saveFiles(MultipartFile[] uploadedFile) {
        Map<String, String> outputList = new LinkedHashMap<>();
        for (MultipartFile multipartFile : uploadedFile) {
            if (multipartFile.isEmpty()) {
                return new LinkedHashMap<>();
            }
            String mimeType = multipartFile.getContentType().split("/")[0];
            if ("image".contains(mimeType)) {
                outputList.put(saveImage(multipartFile), multipartFile.getOriginalFilename());
            } else {
                outputList.put(saveFile(multipartFile), multipartFile.getOriginalFilename());
            }
        }
        return outputList;
    }

    // Save file if success then return file path, else return null
    public static String saveFile(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_FILE);
    }

    // Save file if success then return file path, else return null
    public static String saveImage(MultipartFile uploadedFile) {
        return save(uploadedFile, FOLDER_NAME_IMAGE);
    }

    private static String save(MultipartFile uploadedFile, String folderName) {
        try {
            return save(uploadedFile.getInputStream(), uploadedFile.getOriginalFilename(), folderName);
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    private static String save(InputStream inputStream, String fileName, String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String fileId = generateFileId();

        String folder = buildFolderUpload(folderName);
        File inFiles = new File(folder);
        if (!inFiles.exists() && !inFiles.mkdirs()) {
            log.error("Can't create folder");
        }
        File file = new File(folder + File.separator + fileId + "." + FilenameUtils.getExtension(fileName));
        try {
            if (file.exists()) {
                file.delete();
            }
            FileUtils.copyInputStreamToFile(inputStream, file);
            return SEPARATOR + folderName + SEPARATOR + todayFolder + SEPARATOR + fileId + "." + FilenameUtils.getExtension(fileName);
        } catch (IOException e) {
            log.error("Save file error", e);
            return null;
        }
    }

    public static FileInputStream getInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (FileNotFoundException ignored) {

        }
        return null;
    }

    public static String getFilePathFromDatabase(String databaseFilePath) {
        return SEPARATOR + FOLDER_NAME_PARENT + SEPARATOR + databaseFilePath;
    }

    // Get only file name
    public static String getFilenameFromFilePath(String databaseFilePath) {
        if (StringUtils.isBlank(databaseFilePath)) {
            return "";
        }
        return databaseFilePath.substring(databaseFilePath.lastIndexOf(SEPARATOR) + 1);
    }

    // Create file id (unique)
    private static String generateFileId() {
        return DateUtil.getCurrentDateStr();
    }

    private static String buildFolderUpload(String folderName) {
        String todayFolder = DateUtil.getTodayFolder();
        String folderSave = PropertiesUtil.getProperty("vn.compedia.static.location");

        // in project
        return folderSave
                + File.separator + folderName
                + File.separator + todayFolder
                + File.separator;
    }

    public static String getFileExtFromFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean isPdfFileExt(String fileName) {
        return StringUtils.equalsIgnoreCase(getFileExtFromFileName(fileName), EXT_PDF);
    }

    public static boolean isOfficeFileExt(String fileName) {
        return StringUtils.contains(EXT_OFFICE, getFileExtFromFileName(fileName).toLowerCase());
    }

    public static boolean isAcceptFileType(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types"));
    }

    public static boolean isAcceptAudioFileType(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_file_types_audio"));
    }


    public static boolean isAcceptFileTypeImage(String fileName) {
        return isAcceptFileType(fileName, PropertiesUtil.getProperty("accept_image_file_types"));
    }

    public static boolean isAcceptFileType(String fileName, String acceptTypes) {
        if (StringUtils.isBlank(acceptTypes) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(acceptTypes.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName).toLowerCase());
    }

    // PDF
    public static boolean isAcceptFilePDFType(String fileName) {
        String fileTypeString = getAcceptFilePDFString();
        if (StringUtils.isBlank(fileTypeString) || StringUtils.isBlank(fileName)) {
            return false;
        }
        List<String> fileTypeList = Arrays.asList(fileTypeString.split(","));
        return fileTypeList.contains(getFileExtFromFileName(fileName));
    }

    public static String getAcceptFilePDFString() {
        return PropertiesUtil.getProperty("accept_file_types_pdf");
    }

    public static void deleteFileByListPath(List<String> fileList) {
        for (String filePath : fileList) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean a = file.delete();
                System.out.println(a);
            }
        }
    }
}
