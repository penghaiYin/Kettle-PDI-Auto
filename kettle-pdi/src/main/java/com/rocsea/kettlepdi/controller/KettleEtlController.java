package com.rocsea.kettlepdi.controller;
import com.rocsea.etlcommon.model.response.ResponseWrapper;
import com.rocsea.kettlepdi.service.kettle.KettleEtlBuildService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author RocSea
 * @Date 2022/7/11
 */
@RestController
@RequestMapping("kettle/etl")
public class KettleEtlController {
    @Resource
    private KettleEtlBuildService kettleEtlBuildService;

    @ApiOperation(value = "上传Excel完成全部构建")
    @PostMapping(value = "/excel")
    public ResponseWrapper<String> readExcel(@RequestParam(value = "range", required = false) String range,
                                             @RequestParam("file") MultipartFile file) {
        kettleEtlBuildService.readExcel(file, range);
        return ResponseWrapper.success("操作成功");
    }

}
