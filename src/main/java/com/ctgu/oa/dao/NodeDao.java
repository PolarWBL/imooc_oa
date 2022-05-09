package com.ctgu.oa.dao;

import com.ctgu.oa.entity.Node;

import java.util.List;

/**
 * @author Boliang Weng
 */
public interface NodeDao {
    /**
     * 通过用户id查询其拥有的节点
     * @param userId 用户id
     * @return 返回节点列表
     */
    public List<Node> selectNodeByUserId(Long userId);
}
