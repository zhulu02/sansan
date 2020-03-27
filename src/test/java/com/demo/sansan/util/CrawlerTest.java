package com.demo.sansan.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 *
 * 创建日期 2020年03月27日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CrawlerTest {

    @Autowired
    private Crawler crawler;


    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void parseIgPost() {

    }

    @Test
    public void crawlerSourceByCookie() {
    }

    /**
     * 读取文件
     * @param filePath
     * @return
     */
    public static String getDataFromFile(String filePath) {
        StringBuilder fileKeywords = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String s = "";
            while ((s = br.readLine()) != null) {
                fileKeywords.append(s).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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