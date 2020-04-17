
package com.demo.sansan.controller;


import com.demo.sansan.bean.IgPost;
import com.demo.sansan.service.IgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;

/**
 * <p>Instagram。</p>
 *
 * 创建日期 2020年03月24日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
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
        List<List<IgPost>> result = igService.explore();
        log.info("传递{}组帖子给前端", result.size());
        map.put("lists", result);
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
        return "user";
    }

    /**
     * 访问用户时间线
     * @param username
     * @return
     */
    @RequestMapping("/timeline")
    public String timeline(@RequestParam("username") String username, ModelMap map) {
        igService.timeline(username, map);
        return "timeline";
    }


    /**
     * 访问帖子详情
     * @return
     */
    @RequestMapping("/content")
    public String content(@RequestParam("id") String id, ModelMap map) {
        igService.content(id, map);
        return "content";
    }


}
