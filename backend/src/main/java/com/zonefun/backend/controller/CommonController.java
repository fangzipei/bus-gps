package com.zonefun.backend.controller;

import com.zonefun.backend.common.CommonResult;
import com.zonefun.backend.dto.*;
import com.zonefun.backend.entity.BusInfoEntity;
import com.zonefun.backend.service.IBusInfoService;
import com.zonefun.backend.service.IBusPositionService;
import com.zonefun.backend.service.IBusTourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CommonController
 * @Description 通用接口
 * @Date 2023/3/11 9:48
 * @Author ZoneFang
 */
@RestController
@RequestMapping("/common")
@Api("CommonController")
public class CommonController {

    @Autowired
    private IBusTourService busTourService;

    @Autowired
    private IBusInfoService busInfoService;

    @Autowired
    private IBusPositionService busPositionService;

    @PostMapping("/login")
    @ApiOperation("司机登录")
    public CommonResult<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse login = busTourService.login(loginRequest.getBusNo(), loginRequest.getDriverId(), loginRequest.getLongitude(), loginRequest.getLatitude());
        return CommonResult.success(login);
    }

    @GetMapping("/getBusInfos")
    @ApiOperation("获取公交车信息")
    public CommonResult<List<BusInfoEntity>> getBusInfos() {
        return CommonResult.success(busInfoService.getBusInfos());
    }

    @PostMapping("/endTour")
    @ApiOperation("强行终止行程")
    public CommonResult endTour(@RequestBody EndTourRequest endTourRequest) {
        busTourService.endTour(endTourRequest.getBusNo(), endTourRequest.getDriverId());
        return CommonResult.success();
    }

    @PostMapping("/savePosition")
    @ApiOperation("保存地点")
    public CommonResult<SavePositionResponse> savePosition(@RequestBody SavePositionRequest request) {
        SavePositionResponse savePositionResponse =
                busPositionService.savePosition(request.getLongitude(), request.getLatitude(),
                        request.getTourId(), request.getVelocity());
        return CommonResult.success(savePositionResponse);
    }

    @GetMapping("/clientQuery")
    @ApiOperation("乘客端查询公交线路和站点")
    public CommonResult<List<BusInfoEntity>> clientQuery(@RequestParam("busNo") String busNo, @RequestParam("stopName") String stopName) {
        return CommonResult.success(busInfoService.clientQuery(busNo, stopName));
    }

    @GetMapping("/queryPositions")
    @ApiOperation("客户端查询位置")
    public CommonResult<List<QueryPositionsResponse>> queryPositions(@RequestParam("busNo") String busNo) {
        return CommonResult.success(busPositionService.queryPositions(busNo));
    }
}
