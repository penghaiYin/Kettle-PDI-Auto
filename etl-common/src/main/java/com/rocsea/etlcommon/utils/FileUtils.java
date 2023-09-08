package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.exception.BusinessException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

/**
 * 导出工具类
 *
 * @Author RocSea
 * @Date 2022/7/14
 */
public final class FileUtils {
    private FileUtils() {
        // hide constructor
    }

    /**
     * 下载
     *
     * @param content 需要下载的内容
     * @return Boolean
     */
    public static Boolean download(HttpServletResponse response, String content, String filename) throws IOException {
        if (Objects.isNull(content)) {
            throw new BusinessException("download content is null");
        }
        ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        in.close();
        // 清空response
        response.reset();

        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName = new String(filename.getBytes("gb2312"), "iso8859-1");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();
        out.write(buffer);
        out.flush();
        out.close();
        return true;
    }

    /**
     * 将文本写入磁盘文件
     *
     * @param filePath 文件路径
     * @param text     需要写入的文本
     * @return 是否写入成功
     */
    public static boolean writeTextToFile(String filePath, String text) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    throw new IOException("目录创建失败");
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
