package com.ctgu.oa.dao.impl;

import com.ctgu.oa.dao.NodeDao;
import com.ctgu.oa.entity.Node;
import com.ctgu.oa.utils.MybatisUtils;

import java.util.List;

/**
 * @author Boliang Weng
 */
public class NodeDaoImpl implements NodeDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<Node> selectNodeByUserId(Long userId) {
        return (List<Node>) MybatisUtils.executeQuery(sqlSession -> {
            NodeDao mapper = sqlSession.getMapper(NodeDao.class);
            return mapper.selectNodeByUserId(userId);
        });
    }
}
