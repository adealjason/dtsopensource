package org.github.dtsopensource.remote.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月9日 下午5:04:11
 */
@Slf4j
public class FileCreator {

    private FileCreator() {
    }

    /**
     * @param lines
     * @param localAbsolutePath
     * @return
     */
    public static boolean createFile(String line, String localAbsolutePath) {
        BufferedWriter writer = null;
        try {
            File destDirFile = new File(localAbsolutePath);
            log.info(destDirFile.getAbsolutePath());
            //判断父类目录存在不,不则自动创建
            File parentDirc = new File(destDirFile.getParentFile().getAbsolutePath());
            if (!parentDirc.exists()) {
                parentDirc.mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destDirFile, true), "utf-8"));
            process(line, writer);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            close(writer);
        }
    }

    private static void process(String line, BufferedWriter writer) throws IOException {
        writer.write(line);
        writer.write("\r\n");
        writer.flush();
    }

    private static void close(Closeable writer) {
        if (writer == null) {
            return;
        }
        try {
            writer.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static List<String> parseTxtFile(String localAbsolutePath) {
        List<String> data = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(localAbsolutePath);
            bufferedReader = new BufferedReader(new InputStreamReader(fis, Charset.forName("utf-8")));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (StringUtils.isEmpty(line.trim())) {// 过滤空行
                    continue;
                }
                data.add(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            close(bufferedReader);
            close(fis);
        }
        return data;
    }

}
