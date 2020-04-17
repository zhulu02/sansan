

package com.demo.sansan.dao;


import com.demo.sansan.bean.IgUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>IgUser处理接口。</p>
 *
 * 创建日期 2020年03月25日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public interface IgUserDao {

    /**
     * 根据id查找用户对象
     * @param id
     * @return
     */
    IgUser queryById(String id);

    /**
     * 查找所有
     * @return
     */
    List<IgUser> queryAll();

    /**
     * 插入
     * @param igUser
     */
    void insert(IgUser igUser);

    /**
     * 修改
     * @param igUser
     */
    void update(IgUser igUser);

    /**
     *  根据id删除
     * @param id
     */
    void deleteById(String id);

    /**
     *  根据用户名查找
     * @param userName
     * @return
     */
    IgUser queryByUserName(String userName);

}
