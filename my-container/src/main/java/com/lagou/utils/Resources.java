package com.lagou.utils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author liangzj
 * @date 2021/1/24 19:11
 */
public class Resources {

    /**
     * 根据路径将文件读取为输入流
     *
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path) {
        // TODO 这里再看一下
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        System.out.println(Resources.class.getClassLoader().getResource(""));
        System.out.println(Resources.class.getClassLoader().getResource(path));
        return resourceAsStream;
    }

    /**
     * 获取指定路径下的所有url
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Set<URL> getURLSet(String path) throws IOException {
        Enumeration<URL> urlEnumeration = Resources.class.getClassLoader().getResources(path);
        Set<URL> urls = new HashSet<>();
        while (urlEnumeration.hasMoreElements()) {
            urls.add(urlEnumeration.nextElement());
        }
        return urls;
    }

    /**
     * 根据后后缀获取
     *
     * @param suffix
     * @return
     * @throws IOException
     */
    public static List<InputStream> getFileBySuffix(String suffix) throws IOException {
        Set<URL> urlSet = getURLSet("");
        if (urlSet == null && urlSet.size() == 0) {
            return Collections.emptyList();
        }

        List<InputStream> resultList = new LinkedList<>();
        for (URL url : urlSet) {
            File file = new File(url.getFile());
            // 获取指定结尾的文件
            resolveClassNames(file, suffix, resultList);
        }

        return resultList;
    }

    private static void resolveClassNames(File file, String suffix, List<InputStream> suffixFileList) throws FileNotFoundException {
        if (file == null) {
            return;
        }
        // 如果是文件，判断是否是指定后缀结尾的文件
        if (file.isFile() && file.getAbsolutePath().endsWith(suffix)) {
            suffixFileList.add(new FileInputStream(file));
        }

        // 如果当前文件是文件夹，则再向下一层遍历
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                resolveClassNames(subFile, suffix, suffixFileList);
            }
        }
    }
}
