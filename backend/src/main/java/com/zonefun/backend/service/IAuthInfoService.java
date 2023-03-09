//package com.zonefun.algorithm.service;
//
//import com.glsc.dscp.model.AuthInfo;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @ClassName IAuthInfoService
// * @Description 权限管理相关方法
// * @Date 2022/10/18 10:05
// * @Author ZoneFang
// */
//@Service
//public interface IAuthInfoService {
//
//    /**
//     * 创建权限
//     *
//     * @param authInfo 权限信息
//     * @return int
//     * @date 2022/10/18 10:06
//     * @author ZoneFang
//     */
//    int create(AuthInfo authInfo);
//
//    /**
//     * 更新权限
//     *
//     * @param authInfo 权限信息
//     * @return int 更新条数
//     * @date 2022/10/18 10:23
//     * @author ZoneFang
//     */
//    int update(AuthInfo authInfo);
//
//    /**
//     * 删除权限
//     *
//     * @param authId 权限编号
//     * @return int 删除条数
//     * @date 2022/10/18 10:58
//     * @author ZoneFang
//     */
//    int deleteById(Integer authId);
//
//    /**
//     * 分页查询权限
//     *
//     * @param authName 权限名称
//     * @param authUrl  权限URL
//     * @param pageSize 分页大小
//     * @param pageNum  页码
//     * @return java.util.List<com.glsc.dscp.model.AuthInfo>
//     * @date 2022/10/18 13:28
//     * @author ZoneFang
//     */
//    List<AuthInfo> page(String authName, String authUrl, Integer pageSize, Integer pageNum);
//
//    /**
//     * 查询权限
//     *
//     * @param authName 权限名称
//     * @return java.util.List<com.glsc.dscp.model.AuthInfo>
//     * @date 2022/10/18 15:50
//     * @author ZoneFang
//     */
//    List<AuthInfo> listAuthInfos(String authName);
//
//    /**
//     * 查询角色权限
//     *
//     * @param roleId   角色编号
//     * @param authName 权限名称
//     * @return java.util.List<com.glsc.dscp.model.AuthInfo>
//     * @date 2022/10/20 11:00
//     * @author ZoneFang
//     */
//    List<AuthInfo> listRoleAuths(Integer roleId, String authName);
//
//    /**
//     * 加载后台管理所有权限路径
//     *
//     * @return java.lang.String
//     * @date 2022/10/27 10:13
//     * @author ZoneFang
//     */
//    String reloadAllAuths();
//}
