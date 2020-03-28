package com.demo.sansan.service;

import com.demo.sansan.bean.IgUser;
import com.demo.sansan.dao.IgUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


/**
 * <p>Instagram 数据处理类。</p>
 *
 * 创建日期 2020年03月25日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
@Service
public class IgService {

    @Autowired
    private IgUserDao igUserDao;

    /**
     * 查询所有
     * @return
     */
    public List<IgUser> queryAll() {
        return igUserDao.queryAll();
    }
}
