package kr.codeline.demo.web.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.codeline.demo.common.exception.MessageException;
import kr.codeline.demo.common.service.StorageService;
import kr.codeline.demo.common.util.UploadResult;
import lombok.extern.slf4j.Slf4j;
import uap_clj.java.api.Browser;

/**
 * 파일관련 컨트롤러
 * 
 * @author 김영명
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private StorageService service;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/{file_seq}")
    @ResponseBody
    public ResponseEntity<Resource> down(HttpServletRequest request, @PathVariable String file_seq) {
        Map<String, String> file = Map.of(); // fileService.get(file_seq);

        String path = MapUtils.getString(file, "file_save");
        String name = MapUtils.getString(file, "file_name");

        path = StringUtils.replace(path, "\\", "/");
        path = StringUtils.replace(path, "..", "");
        path = StringUtils.replace(path, "./", "");

        return file_dn(request, path, name);
    }

    private ResponseEntity<Resource> file_dn(HttpServletRequest request, String path, String name) {
        try {
            Resource resource = service.resources(path);
            MediaType contentType = getContentType(name);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=\"" + getFileName(request, name) + "\"")
                    .contentType(contentType).contentLength(resource.contentLength()).body(resource);
        } catch (IOException ex) {
            log.error("file not found : {}", path);

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{file_seq}")
    @ResponseBody
    public Map<String, String> delete(@PathVariable String file_seq) {
        try {
            // fileService.remove(file_seq);

            return Map.of("result", "success");

        } catch (Exception ex) {
            return Map.of("error", ex.getMessage());
        }
    }

    @PostMapping("image")
    @ResponseBody
    public Map<String, String> imageUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadResult result = service.upload(file);

            log.debug("upload result : {}", result);

            return Map.of("location",
                    String.format("%s/file/image?path=%s", servletContext.getContextPath(), result.getPath()));

        } catch (IOException ex) {
            log.error("image upload error : {}", ex);

            return Map.of("error", ex.getMessage());
        }
    }

    /**
     * 이미지 다운로드
     */
    @GetMapping("/image")
    public void image(HttpServletResponse response, @RequestParam String path,
            @RequestParam(required = false) Integer width) {
        try {
            path = StringUtils.replace(path, "\\", "/");
            path = StringUtils.replace(path, "..", "");
            path = StringUtils.replace(path, "./", "");

            service.thumb(path, response.getOutputStream(), width);
        } catch (IOException ex) {
            log.error("image not found : {}", path);
        }
    }

    @PostMapping("upload")
    @ResponseBody
    public UploadResult fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadResult result = service.upload(file);

            log.debug("upload result : {}", result);

            return result;

        } catch (IOException ex) {
            log.error("upload error : {}", ex);

            throw new MessageException(ex.getMessage());
        }
    }

    @GetMapping("download/{dir}/{name}")
    public ResponseEntity<Resource> download(HttpServletRequest request, @PathVariable String dir,
            @PathVariable String name, @RequestParam String n) {
        String path = dir + "/" + name;

        path = StringUtils.replace(path, "\\", "/");
        path = StringUtils.replace(path, "..", "");
        path = StringUtils.replace(path, "./", "");

        return file_dn(request, path, n);
    }

    private MediaType getContentType(String file_name) {
        try {
            return MediaType.parseMediaType(servletContext.getMimeType(file_name));
        } catch (Exception ex) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @SuppressWarnings("unchecked")
    private String getFileName(HttpServletRequest request, String file_name) throws UnsupportedEncodingException {
        String agent = request.getHeader("User-Agent");

        String browser = ((Map<String, String>) Browser.lookup(agent)).get("family");

        if (Arrays.asList("Edge", "IE").contains(browser)) {
            return URLEncoder.encode(file_name, "UTF-8").replaceAll("\\+", "%20");
        } else {
            return new String(file_name.getBytes("UTF-8"), "ISO-8859-1");
        }
    }
}