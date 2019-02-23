package com.log.mybatis.service;

import com.log.mybatis.beans.UserBean;
import com.log.mybatis.mapper.UserMapper;
import com.log.mybatis.utils.DBUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserService {
    public static void main(String[] args) {
//        insertUser();
//        deleteUser(2);
//        selectUserById(4);
//        selectAllUser();
    }


    /**
     * 新增用户
     */
    private static void insertUser() {
        SqlSession session = DBUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserBean user = new UserBean("懿", "1314520", 7000.0);
        try {
            mapper.insertUser(user);
            System.out.println(user.toString());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }


    /**
     * 删除用户
     */
    private static void deleteUser(int id){
        SqlSession session= DBUtils.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        try {
            mapper.deleteUser(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }


    /**
     * 根据id查询用户
     */
    private static void selectUserById(int id){
        SqlSession session=DBUtils.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        try {
            UserBean user=    mapper.selectUserById(id);
            System.out.println(user.toString());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    /**
     * 查询所有的用户
     */
    private static void selectAllUser(){
        SqlSession session=DBUtils.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        try {
            List<UserBean> user=mapper.selectAllUser();
            System.out.println(user.toString());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

}
