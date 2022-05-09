package com.ctgu.oa.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Boliang Weng
 */
public class DruidFactory extends UnpooledDataSourceFactory {
    public DruidFactory() {
        this.dataSource = new DruidDataSource();
    }

    @Override
    public DataSource getDataSource() {
        try {
            //druid连接池需要调用init()方法来初始化连接池, 这是druid的规定和特性, c3p0没有此要求
            ((DruidDataSource)this.dataSource).init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.dataSource;
    }
}
