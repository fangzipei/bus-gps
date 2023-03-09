//package com.zonefun.algorithm.controller;
//
//import com.glsc.dscp.annotation.CheckSqlInjection;
//import com.glsc.dscp.common.CommonException;
//import com.glsc.dscp.common.CommonPage;
//import com.glsc.dscp.common.CommonResult;
//import com.glsc.dscp.dto.AuthIdRequest;
//import com.glsc.dscp.dto.AuthRequest;
//import com.glsc.dscp.model.AuthInfo;
//import com.glsc.dscp.service.IAuthInfoService;
//import com.zonefun.algorithm.annotations.RecordAdminLog;
//import com.zonefun.algorithm.annotations.RequiredPermission;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @ClassName AuthInfoController
// * @Description 权限管理相关接口
// * @Date 2022/10/18 10:02
// * @Author ZoneFang
// */
//@Controller
//@Api(value = "AuthInfoController", tags = {"AuthInfoController-权限管理"})
//@RequestMapping("/auth")
//@Slf4j
//@RequiredPermission
//@Validated
//@RecordAdminLog
//public class AuthInfoController {
//
//    @Autowired
//    private IAuthInfoService authInfoService;
//
//    /**
//     * 权限创建
//     *
//     * @param authRequest 权限请求
//     * @return com.glsc.dscp.common.CommonResult<java.lang.Integer> 返回成功条数
//     * @date 2022/10/18 10:13
//     * @author ZoneFang
//     */
//    @ResponseBody
//    @PostMapping("/create")
//    @ApiOperation("权限创建")
//    public CommonResult<Integer> create(@RequestBody @Validated AuthRequest authRequest) {
//        // 装箱
//        AuthInfo authInfo = new AuthInfo();
//        BeanUtils.copyProperties(authRequest, authInfo);
//        // 获取更新条数
//        int count = authInfoService.create(authInfo);
//        if (count < 1) {
//            throw new CommonException("新增失败！");
//        }
//        return CommonResult.success(count);
//    }
//
//    /**
//     * 权限更新
//     *
//     * @param authRequest 权限请求
//     * @return com.glsc.dscp.common.CommonResult<java.lang.Integer> 更新条数
//     * @date 2022/10/18 10:14
//     * @author ZoneFang
//     */
//    @ResponseBody
//    @PostMapping("/update")
//    @ApiOperation("权限更新")
//    public CommonResult<Integer> update(@RequestBody @Validated AuthRequest authRequest) {
//        // 装箱
//        AuthInfo authInfo = new AuthInfo();
//        BeanUtils.copyProperties(authRequest, authInfo);
//        // 获取更新条数
//        int count = authInfoService.update(authInfo);
//        if (count < 1) {
//            throw new CommonException("更新失败！");
//        }
//        return CommonResult.success(count);
//    }
//
//    /**
//     * 删除权限
//     *
//     * @param authIdRequest 权限编号
//     * @return com.glsc.dscp.common.CommonResult<java.lang.Integer> 删除条数
//     * @date 2022/10/18 11:06
//     * @author ZoneFang
//     */
//    @PostMapping("/delete")
//    @ApiOperation("权限删除")
//    @ResponseBody
//    public CommonResult<Integer> delete(@RequestBody @Validated AuthIdRequest authIdRequest) {
//        Integer authId = authIdRequest.getAuthId();
//        // 获取删除条数
//        int count = authInfoService.deleteById(authId);
//        if (count < 1) {
//            throw new CommonException("删除失败！");
//        }
//        return CommonResult.success(count);
//    }
//
//    /**
//     * 分页查询权限
//     *
//     * @param authName 权限名称
//     * @return com.glsc.dscp.common.CommonPage<com.glsc.dscp.model.AuthInfo>
//     * @date 2022/10/18 13:17
//     * @author ZoneFang
//     */
//    @ApiOperation("分页查询权限")
//    @GetMapping("/page")
//    @ResponseBody
//    public CommonResult<CommonPage<AuthInfo>> page(@CheckSqlInjection @ApiParam(value = "权限名称") @RequestParam(value = "authName", required = false) String authName,
//                                                   @CheckSqlInjection @ApiParam(value = "权限URL") @RequestParam(value = "authUrl", required = false) String authUrl,
//                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
//                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        List<AuthInfo> authInfos = authInfoService.page(authName, authUrl, pageSize, pageNum);
//        return CommonResult.success(CommonPage.restPage(authInfos));
//    }
//
//    /**
//     * 查询权限
//     *
//     * @param authName 权限名称
//     * @return com.glsc.dscp.common.CommonResult<java.util.List < com.glsc.dscp.model.AuthInfo>>
//     * @date 2022/10/18 15:49
//     * @author ZoneFang
//     */
//    @ApiOperation("查询权限")
//    @GetMapping("/listAuthInfos")
//    @ResponseBody
//    public CommonResult<List<AuthInfo>> listAuthInfos(@CheckSqlInjection @ApiParam(value = "权限名称") @RequestParam(value = "authName", required = false) String authName) {
//        List<AuthInfo> authInfos = authInfoService.listAuthInfos(authName);
//        return CommonResult.success(authInfos);
//    }
//
//    /**
//     * 查询角色权限
//     *
//     * @param roleId 角色编号
//     * @return com.glsc.dscp.common.CommonResult<java.util.List < com.glsc.dscp.model.AuthInfo>>
//     * @date 2022/10/20 10:55
//     * @author ZoneFang
//     */
//    @ApiOperation("查询角色权限")
//    @GetMapping("/listRoleAuths")
//    @ResponseBody
//    public CommonResult<List<AuthInfo>> listRoleAuths(@ApiParam(value = "角色编号", required = true) @RequestParam(value = "roleId") Integer roleId,
//                                                      @ApiParam(value = "权限名称") @RequestParam(value = "authName", required = false) String authName) {
//        List<AuthInfo> authInfos = authInfoService.listRoleAuths(roleId, authName);
//        return CommonResult.success(authInfos);
//    }
//
//    /**
//     * 重载后台管理所有权限
//     *
//     * @return com.glsc.dscp.common.CommonResult
//     * @date 2022/10/27 10:01
//     * @author ZoneFang
//     */
//    @ApiOperation("重载后台管理所有权限")
//    @PostMapping("/reloadAllAuths")
//    @ResponseBody
//    public CommonResult<String> reloadAllAuths() {
//        return CommonResult.success(authInfoService.reloadAllAuths());
//    }
//}
