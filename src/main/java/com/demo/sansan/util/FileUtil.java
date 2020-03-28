package com.demo.sansan.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>文件工具类。</p>
 *
 * 创建日期 2020年03月28日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
@Component
public class FileUtil {


    /**
     * 读取文件
     * @param filePath
     * @return
     */
    public String getDataFromFile(String filePath) {
        StringBuilder fileKeywords = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String s = "";
            while ((s = br.readLine()) != null) {
                fileKeywords.append(s).append("\n");
            }
        } catch (IOException e) {
            log.error("读取文件异常");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return fileKeywords.toString();
    }
}
