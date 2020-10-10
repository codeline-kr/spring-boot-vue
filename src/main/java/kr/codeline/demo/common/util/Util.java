package kr.codeline.demo.common.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
public class Util {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyEncrypt(String password, String encrypted) {
        return BCrypt.checkpw(password, encrypted);
    }

    public static List<String> ipRange(String ip) {
        List<String> ip_range = new ArrayList<>();

        String[] tmp = StringUtils.split(ip, ".");

        if (tmp.length != 4) {
            ip_range.add(ip);
            return ip_range;
        }

        ip_range.add(tmp[0] + ".");
        ip_range.add(String.format("%s.%s.", tmp[0], tmp[1]));
        ip_range.add(String.format("%s.%s.%s.", tmp[0], tmp[1], tmp[2]));
        ip_range.add(String.format("%s.%s.%s.%s", tmp[0], tmp[1], tmp[2], tmp[3]));

        return ip_range;
    }

    public static String now() {
        return now("yyyy-MM-dd");
    }

    public static String now(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static File thumb(File file, int width) {
        // 파일 이름
        String fileName = file.getName();

        // 기존 파일 이름에 _width 추가하여 저장. (test_100.jpg)
        fileName = String.format("%s_%d.%s", FilenameUtils.getBaseName(fileName), width,
                FilenameUtils.getExtension(fileName));

        // 파일 경로와 함께 리턴
        File resized = new File(file.getParent(), fileName);

        try {
            Thumbnails.of(file).size(width, width).toFile(resized);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resized;
    }

    public static List<Map<String, Object>> jsonToList(Map<String, Object> map, String key) {
        List<Map<String, Object>> data = new ArrayList<>();

        ObjectMapper om = new ObjectMapper();
        String json = MapUtils.getString(map, key);

        if (json != null) {
            try {
                data = om.readValue(json, new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (Exception ex) {
                log.error("parse error : {}", ex.getMessage());
            }
        }

        map.put(key, data);

        return data;
    }
}