package kr.codeline.demo.module.user.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Map<String, String> adminLogin(String user_id);

    void updateAdminAccessTime(String grj_seq);

    void resetAdminPassword(String pgds_seq, String user_pw);

    Map<String, String> userLogin(String user_id);

    void updateUserAccessTime(Map<String, Object> param);

    void resetUserPassword(Map<String, String> param);

    void insertLoginLog(Map<String, Object> param);

}