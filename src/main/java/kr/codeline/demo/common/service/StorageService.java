package kr.codeline.demo.common.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.codeline.demo.common.util.UploadResult;
import kr.codeline.demo.common.util.Util;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 저장 서비스 파일은 ${props.upload.path} 하위 년월/uuid 로 저장됨
 * 201905/12324-ffdsf-ccvdg-sfgg.jpg
 * 
 * @author 김영명
 */
@Slf4j
@Service
public class StorageService {

    @Value("${props.upload.path}")
    String uploadPath;

    /**
     * 
     * @param file 업로드할 파일
     * @return UploadResult {path=20190507/e98ff4f7-93a3-4aeb-813b-12f20a03db96.JPG,
     *         name=샘플파일.jpg, size=126495}
     * @throws IOException
     */
    public UploadResult upload(MultipartFile file) throws IOException {
        String path = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String id = Util.uuid();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (StringUtils.isNotEmpty(extension)) {
            id = id + "." + extension;
        }

        File save = new File(String.format("%s%s%s%s%s", uploadPath, "/", path, "/", id));

        FileUtils.forceMkdirParent(save);
        file.transferTo(save);

        return UploadResult.builder().path(String.format("%s%s%s", path, "/", id)).name(file.getOriginalFilename())
                .size(file.getSize()).size_h(FileUtils.byteCountToDisplaySize(file.getSize())).build();
    }

    /**
     * 파일 bytes 가져오기
     * 
     * @param path 201905
     * @param id   xxxxxxxxxx.xxx(파일명)
     */
    public byte[] bytes(String... paths) throws IOException {
        return Files.readAllBytes(Paths.get(uploadPath, paths));
    }

    /**
     * 파일 bytes 가져오기
     * 
     * @param path 201905/xxxxxxxxxx.xxx
     */
    public byte[] bytes(String path) throws IOException {
        return bytes(path.split("/"));
    }

    public Resource resources(String path) throws IOException {
        Path filePath = Paths.get(uploadPath, path.split("/"));
        Resource resource = new UrlResource(filePath.toUri());

        return resource;
    }

    /**
     * out 으로 파일 bytes 보내기
     */
    public void to(String path, OutputStream out) throws IOException {
        IOUtils.copy(resources(path).getInputStream(), out);
    }

    public void thumb(String path, OutputStream out, Integer width) throws IOException {
        File file = null;

        if (width != null) {
            File origin = file(path);

            File thumbFile = new File(origin.getParent(), String.format("%s_%d.%s", FilenameUtils.getBaseName(path),
                    width, FilenameUtils.getExtension(path)));

            log.debug("thumFile : {}, {}", thumbFile, thumbFile.exists());

            if (!thumbFile.exists()) {
                // 썸네일 만듬
                Util.thumb(file(path), width);
            }
            file = thumbFile;
        } else {
            file = file(path);
        }

        IOUtils.copy(FileUtils.openInputStream(file), out);
    }

    /**
     * 경로에 있는 파일객체 반환
     * 
     * @param path 201905/xxxxxxxxxx.xxx
     */
    public File file(String path) {
        return new File(String.format("%s%s%s", uploadPath, "/", path));
    }

    public void deleteFile(String path) {
        if (StringUtils.isNotEmpty(path) && StringUtils.split(path, "/").length >= 2) {
            FileUtils.deleteQuietly(file(path));
        }
    }
}