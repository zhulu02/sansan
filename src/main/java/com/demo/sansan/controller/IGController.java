
package com.demo.sansan.controller;



import com.demo.sansan.bean.IgPost;
import com.demo.sansan.bean.IgUser;
import com.demo.sansan.service.IgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Instagram。</p>
 *
 * 创建日期 2020年03月24日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Controller
@RequestMapping("/ig")
public class IGController {


    @Autowired
    private IgService igService;

    /**
     * 探索
     * @param map
     * @return
     */
    @RequestMapping("/explore")
    public String explore(ModelMap map) {
        //设置栏目id
        map.put("column_id", 0);
        return "explore";
    }


    /**
     * 关注用户
     * @param map
     * @return
     */
    @RequestMapping("/user")
    public String user(ModelMap map) {
        //设置栏目id
        map.put("column_id", 1);

        List<IgUser> igUsers = igService.queryAll();
        map.addAttribute("igUsers", igUsers);
        return "user";
    }


    /**
     * 访问用户时间线
     * @param username
     * @return
     */
    @RequestMapping("/timeline")
    public String timeline(@RequestParam("username") String username, ModelMap map) {

        IgPost igPost = new IgPost();
        String message = username;
        igPost.setImgPath("/image/1.jpg");
        igPost.setPublishTime(System.currentTimeMillis());
        igPost.setText("hahah");

        List<IgPost> igPosts = new ArrayList<>();
        igPosts.add(igPost);

        //将数据存入modelMap
        map.put("message", message);
        map.addAttribute("igPosts", igPosts);
        return "timeline";
    }



    /**
     * 访问帖子详情
     * @return
     */
    @RequestMapping("/content")
    public String content() {
        return "content";
    }


}
