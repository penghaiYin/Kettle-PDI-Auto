package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.exception.IORuntimeException;
import com.rocsea.etlcommon.model.bo.excel.EtlExcelSheetBO;
import com.rocsea.etlcommon.model.bo.kettlepdi.KettleEtlProductBO;
import com.rocsea.etlcommon.model.constant.*;
import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.etlcommon.model.enums.KettleModuleEnum;
import com.rocsea.etlcommon.utils.ExcelUtils;
import com.rocsea.etlcommon.utils.FileUtils;
import com.rocsea.kettlepdi.builder.DefaultKettleEtlBuilder;
import com.rocsea.kettlepdi.builder.KettleEtlRobot;
import com.rocsea.kettlepdi.factory.etl.KettleEtlFactory;
import com.rocsea.kettlepdi.factory.etl.KettleEtlStrategy;
import com.rocsea.kettlepdi.model.request.KettleEtlBuildRequest;
import com.rocsea.kettlepdi.service.file.FileResourceService;
import com.rocsea.kettlepdi.template.KettleEtlTemplate;
import com.rocsea.kettlepdi.template.KettleEtlTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * kettle机器服务
 *
 * @Author RocSea
 * @Date 2022/12/2
 */
@Service
public class KettleEtlBuildService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KettleEtlBuildService.class);
    @Resource
    private KettleEtlFactory kettleEtlFactory;
    @Resource
    private FileResourceService fileResourceService;

    /**
     * 完全自动构建
     * @param file Excel
     * @param range sheet页的范围
     */
    public void readExcel(MultipartFile file, String range) {
        List<EtlExcelSheetBO> etlExcelSheetBOS;
        try {
            etlExcelSheetBOS = ExcelUtils.readExcel(file.getInputStream(), range);
        } catch (IOException ex) {
            LOGGER.error("Excel读取异常", ex);
            throw new IORuntimeException("Excel读取异常");
        }
        Map<Integer, List<EtlExcelSheetBO>> listMap =
                etlExcelSheetBOS.stream().collect(Collectors.groupingBy(EtlExcelSheetBO::getEtlTemplateType));
        for (Map.Entry<Integer, List<EtlExcelSheetBO>> entry : listMap.entrySet()) {
            KettleEtlStrategy kettleEtlStrategy = kettleEtlFactory.getEtlBuildStrategy(entry.getKey());
            kettleEtlStrategy.build(etlExcelSheetBOS);
        }
    }

    /**
     * 构建
     * @param request 请求
     */
    public void buildKettleEtl(KettleEtlBuildRequest request) {
        // check request param
        checkRequestParam(request);

        // select build strategy
        String name = KettleEtlTemplateEnum.geText(request.getEtlTemplateType());
        KettleEtlTemplate template = KettleEtlTemplateFactory.getTemplate(name);

        KettleEtlRobot kettleBuildRobot = new KettleEtlRobot(new DefaultKettleEtlBuilder(template));
        // make product
        KettleEtlProductBO kettleEtlProductBO = kettleBuildRobot.makeProduct(request);
        // write file
        final String sourceTableName = request.getSourceTableName();
        final String filePath = fileResourceService.getPath() + EtlConstant.ETL_KETTLE_PATH;
        final String fileName = String.format(filePath, fileResourceService.getKettle().incrementAndGet(), sourceTableName);
        if (!FileUtils.writeTextToFile(fileName, kettleEtlProductBO.get())) {
            LOGGER.error("kettle构建写失败");
            throw new IORuntimeException("kettle构建写失败");
        }
    }

    private void checkRequestParam(KettleEtlBuildRequest request) {
        KettleModuleEnum.geText(request.getModuleType());
        KettleEtlTemplateEnum.geText(request.getEtlTemplateType());
    }
}
