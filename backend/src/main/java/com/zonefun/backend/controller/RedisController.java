//package com.zonefun.backend.controller;
//
//import com.zonefun.backend.common.CommonResult;
//import com.zonefun.backend.dto.DelRedisKeyRequest;
//import com.zonefun.backend.dto.RedisKeyValueDTO;
//import com.zonefun.backend.util.RedisUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@Api(value = "RedisController", tags = {"RedisController-redis相关接口"})
//@Slf4j
//@RequestMapping("/redis")
//public class RedisController {
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    /**
//     * Redis删除key
//     *
//     * @param delRedisKeyRequest 删除redis的key请求体
//     * @return com.glsc.its.ext.common.CommonResult
//     * @date 2022/7/8 14:07
//     * @author ZoneFang
//     */
//    @PostMapping("/delRedisKey")
//    @ApiOperation("Redis删除key")
//    @ResponseBody
//    public CommonResult delRedisKey(@RequestBody DelRedisKeyRequest delRedisKeyRequest) {
//        String key = delRedisKeyRequest.getKey();
//        if (ObjectUtils.isEmpty(key)) {
//            return CommonResult.failed("请输入redis锁key！");
//        }
//        redisUtil.del(key);
//        return CommonResult.success();
//    }
//
//    /**
//     * 获取Redis键值对
//     *
//     * @param key 键
//     * @return com.glsc.its.ext.common.CommonResult<com.glsc.its.ext.dto.response.RedisKeyValueDTO>
//     * @date 2022/7/27 8:52
//     * @author ZoneFang
//     */
//    @GetMapping("/getRedisByKey")
//    @ApiOperation("获取Redis键值对")
//    @ResponseBody
//    public CommonResult<RedisKeyValueDTO> getRedisByKey(@ApiParam(value = "key", name = "键", required = true) @RequestParam("key") String key) {
//        if (ObjectUtils.isEmpty(key)) {
//            return CommonResult.failed("请输入需要查找的key！");
//        }
//        String value =(String) redisUtil.get(key);
//        return CommonResult.success(new RedisKeyValueDTO(key, value));
//    }
//}
