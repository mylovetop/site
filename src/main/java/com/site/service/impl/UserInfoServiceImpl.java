package com.site.service.impl;

import com.site.dao.IUserInfoDao;
import com.site.service.IUserInfoService;
import com.site.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 14-5-2.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private IUserInfoDao userInfoDao;

    @Override
    public UserInfoVO findUserInfoById(int id) {
        return userInfoDao.findUserInfoById(id);
    }

    public IUserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(IUserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }
}
