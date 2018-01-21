package com.opensourceteam.mybatis.crud;

import com.github.pagehelper.PageHelper;
import com.opensourceteam.modules.business.test.dao.TTestUuidMapper;
import com.opensourceteam.modules.business.test.po.TTestUuid;
import com.opensourceteam.modules.common.mybatis.mapper.MybatisHelper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 开发人:刘文
 * 日期:  2018/1/17.
 * 功能描述:
 */
public class TestCRUDMybatisUUID {

    Logger logger = LoggerFactory.getLogger(TestCRUDMybatisUUID.class) ;
    SqlSession session = null;
    TTestUuidMapper mapper = null;
    @Before
    public void before(){
         session = MybatisHelper.getSqlSession();
         mapper = session.getMapper(TTestUuidMapper.class);

    }
    @After
    public void after(){
        session.commit();
        session.close();
    }

    /**
     * 插入一条数据
     */
    @Test
    public void insert(){
        TTestUuid test = new TTestUuid();
        String value = "89" ;
        test.setName("test_" + value);
        test.setAge(20);
        test.setRemark("中文remark_" + value);
        mapper.insert(test);
    }

    /**
     * 插入一条数据
     */
    @Test
    public void insertBatch(){
        for( int i = 0 ; i < 10 ; i++){
            TTestUuid test = new TTestUuid();
            String value = "" + i ;
            test.setName("test_" + value);
            test.setRemark("中文remark_" + value);
            test.setAge(20);
            mapper.insert(test);
        }

    }

    /**
     * 更新一条数据(根据主键更新)
     */
    @Test
    public void update(){
        TTestUuid test = new TTestUuid();
        String value = "3" ;
        test.setId("75a406d1fbec11e7af7b00fff3451ede");
        test.setName("test_" + value);
        test.setRemark("中文remark_" + value);
        mapper.updateByPrimaryKey(test);
    }

    /**
     * 删除一条数据(根据主键)
     */
    @Test
    public void deleteByPrimaryKey(){
        mapper.deleteByPrimaryKey("75a406d1fbec11e7af7b00fff3451ede");
    }

    /**
     * 删除一条数据(根据对象的主键值值)
     */
    @Test
    public void delete(){
        TTestUuid test = new TTestUuid();
        test.setId("42b1c49dfbec11e7af7b00fff3451ede");
        mapper.delete(test);
    }

    @Test
    public void selectAll(){
        List<TTestUuid> list = mapper.selectAll();
        for(TTestUuid po : list){
            logger.debug("po:{}",po);
        }
    }

    /**
     * 查询后排序
     */
    @Test
    public void orderBy(){

        Condition condition = new Condition(TTestUuid.class);
        condition.setOrderByClause("age desc,name desc");
        List<TTestUuid> list = mapper.selectByExample(condition);
        for(TTestUuid po : list){
            logger.debug("po:{}",po);
        }
        System.out.println();
    }

    /**
     * 排序后分页
     */
    @Test
    public void pageAndOrder(){
        /**
         * pageNum: 页数
         * pageSize: 每页的数量
         */
        PageHelper.startPage(2, 10);
        Condition condition = new Condition(TTestUuid.class);
        condition.setOrderByClause("age desc,name desc");
        List<TTestUuid> list = mapper.selectByExample(condition);
        for(TTestUuid po : list){
            logger.debug("po:{}",po);
        }
        System.out.println();
    }
    /**
     * 分页查询
     */
    @Test
    public void selectPageHelperStartPage(){
        /**
         * pageNum: 页数
         * pageSize: 每页的数量
         */
        PageHelper.startPage(2, 10);
        List<TTestUuid> list = mapper.select(null);
        for(TTestUuid po : list){
            logger.debug("po:{}",po);
        }
        System.out.println();
    }

    /**
     * 分页查询
     */
    @Test
    public void selectPageHelperStartPage2(){
        for(int i = 0 ; i< 10 ;i ++){
            /**
             * pageNum: 页数 (页数从1开始)
             * pageSize: 每页的数量
             */
            PageHelper.startPage(i, 10);
            logger.debug("页数:{}",i);
            List<TTestUuid> list = mapper.select(null);

            for(TTestUuid po : list){
                logger.debug("po:{}",po);
            }
            System.out.println();
        }
    }


    /**
     * 分页查询
     */
    @Test
    public void selectRowBoundsMapper(){
        TTestUuid query = new TTestUuid();
        for( int i = 1; i <= 10 ; i++){
            logger.debug("页数:{}",i);
            /**
             * offset: 页数
             * limit: 每页的数量
             */
            RowBounds rowBounds = new RowBounds(i,10);
            List<TTestUuid> list = mapper.selectByRowBounds(query,rowBounds) ;
            for(TTestUuid po : list){
                logger.debug("po:{}",po);
            }
            System.out.println();
        }

    }
}
