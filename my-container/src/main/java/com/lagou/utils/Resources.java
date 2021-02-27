package com.lagou.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

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
}
