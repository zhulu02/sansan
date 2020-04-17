package com.demo.sansan.dao;

import com.demo.sansan.bean.IgPost;

import java.util.List;

/**
 * <p>Instagram帖子处理接口。</p>
 *
 * 创建日期 2020年04月17日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public interface IgPostDao {

    void insert(IgPost igPost);

    void bulkInsert(List<IgPost> igPosts);

    List<IgPost> queryLatest();

    IgPost queryById(String postId);

    void updateImg(IgPost igPost);

    List<IgPost> queryByUserName(String userName);
}
