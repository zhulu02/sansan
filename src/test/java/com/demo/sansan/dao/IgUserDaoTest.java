package com.demo.sansan.dao;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.bean.IgUser;
import com.demo.sansan.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 *
 * 创建日期 2020年03月30日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class IgUserDaoTest {
    @Autowired
    private IgUserDao igUserDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void queryById() {
    }

    @Test
    public void queryAll() {
       List<IgUser> igUserList =  igUserDao.queryAll();
        System.out.println(igUserList.toString());
    }

    @Test
    public void insert() {
        String json = FileUtil.getDataFromFile("/Users/zhulu/workspace/demo/sansan/source");
        List<IgPost> igPosts = new Gson().fromJson(json, new TypeToken<List<IgPost>>() {
        }.getType());
        Map<String,IgUser> igUsers = new HashMap<>();

        for (IgPost igPost : igPosts) {
            if(!igUsers.containsKey(igPost.getUserId())){
                IgUser igUser = new IgUser(igPost.getUserName(),igPost.getNickName());
                igUser.setUserId(igPost.getUserId());
                igUser.setSmallHeadImg(igPost.getUserHead());
                igUsers.put(igUser.getUserId(),igUser);
            }
        }
        for (String s : igUsers.keySet()) {
            igUserDao.insert(igUsers.get(s));
        }
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteById() {
    }
}