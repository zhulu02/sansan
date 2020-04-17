package com.demo.sansan.dao;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 *
 * 创建日期 2020年03月30日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class IgPostDaoTest {
    @Autowired
    private IgPostDao igPostDao;


    @Before
    public void setUp() throws Exception {
    }



    @Test
    public void insert() {
        String json = FileUtil.getDataFromFile("/Users/zhulu/workspace/demo/sansan/source");
        List<IgPost> igPosts = new Gson().fromJson(json, new TypeToken<List<IgPost>>() {
        }.getType());
        igPostDao.insert(igPosts.get(0));

    }



    @Test
    public void bulkInsert() {
        String json = FileUtil.getDataFromFile("/Users/zhulu/workspace/demo/sansan/source");
        List<IgPost> igPosts = new Gson().fromJson(json, new TypeToken<List<IgPost>>() {
        }.getType());
        for (IgPost igPost : igPosts) {
            System.out.println(igPost.getText());
            System.out.println(igPost.getLink());
        }
//        System.out.println(igPosts.size());
//        igPostDao.bulkInsert(igPosts);

    }

    @Test
    public void queryLatest() {
        List<IgPost> igPosts = igPostDao.queryLatest();
        System.out.println(new Gson().toJson(igPosts));
    }


    @Test
    public void queryById() {
        IgPost igPosts= igPostDao.queryById("B--eHU7FZyW");
       igPosts.setSmallImg(null);
        System.out.println(new Gson().toJson(igPosts));
    }

    @Test
    public void updateImg(){
        IgPost igPosts= igPostDao.queryById("B--eHU7FZyW");
        igPosts.setImg("11");
    }

    @Test
    public void queryByUserName() {
        List<IgPost> igPosts= igPostDao.queryByUserName("hk01_hk");
        System.out.println(igPosts.size());
        System.out.println(new Gson().toJson(igPosts.get(0)));
    }

}