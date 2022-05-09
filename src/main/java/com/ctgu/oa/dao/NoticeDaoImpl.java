package com.ctgu.oa.dao;

import com.ctgu.oa.entity.Notice;
import com.ctgu.oa.utils.MybatisUtils;

import java.util.List;

/**
 * @author Boliang Weng
 */
public class NoticeDaoImpl implements NoticeDao{
    @Override
    public void insertNotice(Notice notice) {
        MybatisUtils.executeUpdate(sqlSession -> {
            NoticeDao mapper = sqlSession.getMapper(NoticeDao.class);
            mapper.insertNotice(notice);
            return null;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Notice> selectNoticeById(Long receiverId) {
        return (List<Notice>) MybatisUtils.executeQuery(sqlSession -> {
            NoticeDao mapper = sqlSession.getMapper(NoticeDao.class);
            return mapper.selectNoticeById(receiverId);
        });
    }


}
