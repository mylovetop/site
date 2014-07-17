package com.site.dao;

import com.site.vo.UserInfoVO;

/**
 * Created by admin on 14-5-2.
 */
public interface IUserInfoDao {
    
    public UserInfoVO findUserInfoById(int id);
}
