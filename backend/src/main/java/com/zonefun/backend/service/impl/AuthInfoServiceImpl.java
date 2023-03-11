//package com.zonefun.backend.service.impl;
//
//import com.github.pagehelper.PageHelper;
//import com.glsc.dscp.constant.DsConstant;
//import com.glsc.dscp.enums.IsDeletedEnum;
//import com.glsc.dscp.mapper.AuthInfoDAO;
//import com.glsc.dscp.mapper.AuthInfoMapper;
//import com.glsc.dscp.model.AuthInfo;
//import com.glsc.dscp.model.AuthInfoExample;
//import com.glsc.dscp.service.IAuthInfoService;
//import com.zonefun.backend.util.ClassUtil;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @ClassName AuthInfoServiceImpl
// * @Description 权限管理方法
// * @Date 2022/10/18 10:07
// * @Author ZoneFang
// */
//@Service
//public class AuthInfoServiceImpl implements IAuthInfoService {
//
//    @Autowired
//    private AuthInfoMapper authInfoMapper;
//
//    @Autowired
//    private AuthInfoDAO authInfoDAO;
//
//    /**
//     * 创建权限
//     *
//     * @param authInfo 权限信息
//     * @return int 创建成功的数量
//     * @date 2022/10/18 10:06
//     * @author ZoneFang
//     */
//    @Override
//    @Transactional
//    public int create(AuthInfo authInfo) {
//        // 校验权限url和名称是否存在 若不存在则插入失败
//        if (ObjectUtils.isEmpty(authInfo.getAuthUrl()) || ObjectUtils.isEmpty(authInfo.getAuthName())) {
//            return 0;
//        }
//        // 插入创建/更新时间
//        Date now = new Date();
//        authInfo.setCreateTime(now);
//        authInfo.setUpdateTime(now);
//        // 默认未删除
//        authInfo.setIsDeleted(IsDeletedEnum.NOT_DELETED.getValue());
//        return authInfoMapper.insert(authInfo);
//    }
//
//    /**
//     * 更新权限
//     *
//     * @param authInfo 权限信息
//     * @return int 更新条数
//     * @date 2022/10/18 10:23
//     * @author ZoneFang
//     */
//    @Override
//    @Transactional
//    public int update(AuthInfo authInfo) {
//        // 若authId不存在则不进行更新
//        if (ObjectUtils.isEmpty(authInfo.getAuthId())) {
//            return 0;
//        }
//        AuthInfo origAuthInfo = authInfoMapper.selectByPrimaryKey(authInfo.getAuthId());
//        origAuthInfo.setAuthName(authInfo.getAuthName());
//        origAuthInfo.setAuthUrl(authInfo.getAuthUrl());
//        // 更新时间
//        Date now = new Date();
//        origAuthInfo.setUpdateTime(now);
//        return authInfoMapper.updateByPrimaryKey(origAuthInfo);
//    }
//
//    /**
//     * 删除权限（逻辑删除）
//     *
//     * @param authId 权限编号
//     * @return int 删除条数
//     * @date 2022/10/18 10:58
//     * @author ZoneFang
//     */
//    @Override
//    @Transactional
//    public int deleteById(Integer authId) {
//        // 逻辑删除，先查找相关权限，再将其is_deleted置为1
//        AuthInfo authInfo = authInfoMapper.selectByPrimaryKey(authId);
//        authInfo.setIsDeleted(IsDeletedEnum.DELETED.getValue());
//        authInfo.setUpdateTime(new Date());
//        return authInfoMapper.updateByPrimaryKey(authInfo);
//    }
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
//    @Override
//    public List<AuthInfo> page(String authName, String authUrl, Integer pageSize, Integer pageNum) {
//        PageHelper.startPage(pageNum, pageSize);
//        AuthInfoExample example = new AuthInfoExample();
//        example.setOrderByClause("update_time desc");
//        AuthInfoExample.Criteria criteria = example.createCriteria();
//        if (!ObjectUtils.isEmpty(authName)) {
//            // 若有权限名称则双向模糊查询
//            criteria.andAuthNameLike("%" + authName + "%");
//        }
//        if (!ObjectUtils.isEmpty(authUrl)) {
//            // 若有权限URL则双向模糊查询
//            criteria.andAuthUrlLike("%" + authUrl + "%");
//        }
//        // 查询未删除的权限
//        criteria.andIsDeletedEqualTo(IsDeletedEnum.NOT_DELETED.getValue());
//        return authInfoMapper.selectByExample(example);
//    }
//
//    /**
//     * 查询权限
//     *
//     * @param authName 权限名称
//     * @return java.util.List<com.glsc.dscp.model.AuthInfo>
//     * @date 2022/10/18 15:50
//     * @author ZoneFang
//     */
//    @Override
//    public List<AuthInfo> listAuthInfos(String authName) {
//        AuthInfoExample example = new AuthInfoExample();
//        example.setOrderByClause("update_time desc");
//        AuthInfoExample.Criteria criteria = example.createCriteria();
//        if (!ObjectUtils.isEmpty(authName)) {
//            // 若有权限名称则双向模糊查询
//            criteria.andAuthNameLike("%" + authName + "%");
//        }
//        // 查询未删除的权限
//        criteria.andIsDeletedEqualTo(IsDeletedEnum.NOT_DELETED.getValue());
//        return authInfoMapper.selectByExample(example);
//    }
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
//    @Override
//    public List<AuthInfo> listRoleAuths(Integer roleId, String authName) {
//        // 获取角色的所有权限
//        List<AuthInfo> authInfos = authInfoDAO.listRoleAuths(roleId);
//        // 若有权限名称 则对其进行筛选
//        if (!ObjectUtils.isEmpty(authName)) {
//            authInfos = authInfos.stream().filter(auth ->
//                    auth.getAuthName().contains(authName)
//            ).collect(Collectors.toList());
//        }
//        return authInfos;
//    }
//
//    /**
//     * 加载后台管理所有权限路径
//     *
//     * @return java.lang.String
//     * @date 2022/10/27 10:13
//     * @author ZoneFang
//     */
//    @Override
//    @Transactional
//    public String reloadAllAuths() {
//        String result = "";
//        Date now = new Date();
//        // 插入表中的数据
//        List<AuthInfo> authInfos = new ArrayList<>();
//        // 获取后台管理接口包下所有接口类
//        Set<Class<?>> classes = ClassUtil.getClassByPackage("com.zonefun.backend.controller", false, Controller.class, RestController.class, RequestMapping.class);
//        for (Class aClass : classes) {
//            // 获取类上的前缀url
//            RequestMapping classRequestMapping = (RequestMapping) aClass.getAnnotation(RequestMapping.class);
//            if (ObjectUtils.isEmpty(classRequestMapping)) {
//                continue;
//            }
//            String[] prefixUrls = classRequestMapping.value();
//            String prefixUrl = "";
//            if (prefixUrls.length == 0) {
//                continue;
//            }
//            prefixUrl = prefixUrls[0];
//            // 获取每个接口类下所有方法
//            Method[] declaredMethods = aClass.getDeclaredMethods();
//            for (Method declaredMethod : declaredMethods) {
//                // 单个权限信息
//                AuthInfo authInfo = new AuthInfo();
//                // 单个权限信息是否已经完善了
//                boolean fullInfo = false;
//                // 获取该方法的接口注释
//                ApiOperation apiOperation = declaredMethod.getAnnotation(ApiOperation.class);
//                if (ObjectUtils.isEmpty(apiOperation)) {
//                    // 如果没有ApiOperation注解 则说明不是接口 直接下一个方法
//                    continue;
//                }
//                // apiOperation的value作为权限名称存入
//                authInfo.setAuthName(apiOperation.value());
//                // 先尝试获取RequestMapping注解
//                RequestMapping requestMapping = declaredMethod.getAnnotation(RequestMapping.class);
//                if (!ObjectUtils.isEmpty(requestMapping)) {
//                    // 若有该注解则获取value
//                    String[] authUrl = requestMapping.value();
//                    if (authUrl.length > 0) {
//                        // 拿第一个value作为权限url
//                        authInfo.setAuthUrl(prefixUrl + authUrl[0]);
//                        fullInfo = true;
//                    }
//                }
//                // 获取GetMapping注解
//                if (!fullInfo) {
//                    GetMapping getMapping = declaredMethod.getAnnotation(GetMapping.class);
//                    if (!ObjectUtils.isEmpty(getMapping)) {
//                        // 若有该注解则获取value
//                        String[] authUrl = getMapping.value();
//                        if (authUrl.length > 0) {
//                            // 拿第一个value作为权限url
//                            authInfo.setAuthUrl(prefixUrl + authUrl[0]);
//                            fullInfo = true;
//                        }
//                    }
//                }
//                if (!fullInfo) {
//                    // 获取PostMapping注解
//                    PostMapping postMapping = declaredMethod.getAnnotation(PostMapping.class);
//                    if (!ObjectUtils.isEmpty(postMapping)) {
//                        // 若有该注解则获取value
//                        String[] authUrl = postMapping.value();
//                        if (authUrl.length > 0) {
//                            // 拿第一个value作为权限url
//                            authInfo.setAuthUrl(prefixUrl + authUrl[0]);
//                            fullInfo = true;
//                        }
//                    }
//                }
//                // 对该权限进行查询 如果表中有了则不进行插入 只有信息完整了才进行判断
//                if (fullInfo) {
//                    AuthInfoExample authInfoExample = new AuthInfoExample();
//                    authInfoExample.createCriteria().andAuthUrlEqualTo(authInfo.getAuthUrl());
//                    List<AuthInfo> tableAuth = authInfoMapper.selectByExample(authInfoExample);
//                    if (ObjectUtils.isEmpty(tableAuth)) {
//                        authInfo.setIsDeleted(IsDeletedEnum.NOT_DELETED.getValue());
//                        authInfo.setCreateTime(now);
//                        authInfo.setUpdateTime(now);
//                        // 若表中不存在才将其插入表中
//                        authInfos.add(authInfo);
//                    }
//                }
//            }
//        }
//        // 批量插入
//        if (!ObjectUtils.isEmpty(authInfos)) {
//            int count = authInfoDAO.batchInsert(authInfos);
//            result = "成功插入" + count + "条权限！";
//        } else {
//            result = "未新增任何权限信息！";
//        }
//        return result;
//    }
//}
