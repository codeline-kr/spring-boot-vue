package kr.codeline.demo.common.service;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    private NumberFormat nf = new DecimalFormat("##.###");

    public String displayFileSize(long size) {
        return FileUtils.byteCountToDisplaySize(size);
    }

    public String fileNm(String file) {
        return FilenameUtils.getBaseName(file);
    }

    public String fileExt(String file) {
        return FilenameUtils.getExtension(file);
    }

    public String rtn2br(String str) {
        return StringUtils.replace(str, "\n", "<br/>");
    }

    public String nf(Object num) {
        if (num == null)
            return "";

        return nf.format(num);
    }

    public String nf(Object num, String format) {
        if (num == null)
            return "";

        return new DecimalFormat(format).format(num);
    }

    public String nf(String num, String format) {
        if (num == null)
            return "";

        return new DecimalFormat(format).format(NumberUtils.toDouble(StringUtils.replace(num, ",", "")));
    }

    public String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}