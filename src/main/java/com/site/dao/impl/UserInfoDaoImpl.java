package com.site.dao.impl;

import com.site.dao.IUserInfoDao;
import com.site.vo.UserInfoVO;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by admin on 14-5-2.
 */
@Repository("UserDao")
public class UserInfoDaoImpl implements IUserInfoDao {

    protected Logger logger = Logger.getLogger(this.getClass());





    @Override
    public UserInfoVO findUserInfoById(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return (UserInfoVO)sqlSession.selectOne("com.site.dao.UserInfoDao.findUserInfoById", map);

    }

    @Resource
    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
