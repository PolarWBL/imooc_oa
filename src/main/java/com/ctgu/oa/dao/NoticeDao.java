package com.ctgu.oa.dao;

import com.ctgu.oa.entity.Notice;

import java.util.List;

/**
 * @author Boliang Weng
 */
public interface NoticeDao {
    /**
     * 插入通知信息
     * @param notice 通知信息
     */
    public void insertNotice(Notice notice);

    /**
     * 查询自己最近10条通知
     * @param receiverId 被通知人id
     * @return 返回通知list集合
     */
    public List<Notice> selectNoticeById(Long receiverId);
}
