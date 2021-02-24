package com.lagou.factory.annotation;

import com.lagou.domain.BeanDefinition;
import com.lagou.factory.support.DefaultBeanFactory;
import com.lagou.utils.Resources;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * 注解配置的bean信息获取
 *
 * @author liangzj
 * @date 2021/2/22 1:26
 */
public class AnnotationBeanDefinitionReader {

    private String scanPath;

    private DefaultBeanFactory beanFactory;

    private final List<Class> componentClassList = new LinkedList<>();

    private final List<BeanDefinition> beanDefinitions = new LinkedList<>();

    public AnnotationBeanDefinitionReader(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 加载bean
     */
    public void loadBeanDefinitions() {
        try {
            // 读取根目录下所有类
            Set<URL> urls = Resources.getURLSet(scanPath);
            // 扫描并加载路径下所有需要加载的类
            scanBeanDefinitions(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册beanDefinition
     */
    public void registerBeanDefinitions() {
        beanDefinitions.forEach(beanDefinition ->
                beanFactory.registerBeanDefinition(beanDefinition.getId(), beanDefinition)
        );
    }

    /**
     * 扫描并加载beanDefinition
     *
     * @param urls
     */
    private void scanBeanDefinitions(Set<URL> urls) {
        for (URL url : urls) {
            File file = new File(url.getFile());
            // 获取所有class文件
            List<String> classNames = new LinkedList<>();
            resolveClassNames(file, classNames);
            // 获取由容器管理的类
            listComponentClass(classNames);
            // 解析类信息并转化成beanDefinition
            resolveBeanDefinitions();
        }

    }

    /**
     * 解析beanDefinitions
     */
    private void resolveBeanDefinitions() {
        for (Class aClass : componentClassList) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(aClass.getSimpleName());
            beanDefinition.setClazz(aClass);
            String[] dependsOn = listDependsOn(aClass);
            beanDefinition.setDependsOn(dependsOn);

            beanDefinitions.add(beanDefinition);
        }
    }

    /**
     * 获取依赖字段
     *
     * @param aClass
     * @return
     */
    private String[] listDependsOn(Class aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        List<String> dependsOn = new ArrayList<>(declaredFields.length);
        for (Field field : declaredFields) {
            if (field.getAnnotation(Autowired.class) != null) {
                dependsOn.add(field.getName());
            }
        }
        return dependsOn.toArray(new String[dependsOn.size()]);
    }

    /**
     * 获取由容器管理的类类型
     *
     * @param classNames
     * @return
     */
    private void listComponentClass(List<String> classNames) {
        try {
            for (String className : classNames) {
                Class<?> aClass = Class.forName(className);
                // 判断类是否是需要由容器管理的类
                if (isComponent(aClass)) {
                    componentClassList.add(aClass);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断这个类是否是组件
     *
     * @param aClass
     * @return
     */
    private boolean isComponent(Class<?> aClass) {
        Service service = aClass.getAnnotation(Service.class);
        Component component = aClass.getAnnotation(Component.class);

        if (service != null || component != null) {
            return true;
        }

        return false;
    }

    /**
     * 获取所有class全限定名
     *
     * @param file
     * @param classNames
     */
    private void resolveClassNames(File file, List<String> classNames) {
        if (file == null) {
            return;
        }
        // 如果是文件，判断是否是“.class”文件
        if (file.isFile() && file.getAbsolutePath().endsWith(".class")) {
            // 获取类的绝对路径
            String classAbsolutePath = file.getAbsolutePath().replaceAll("\\\\", "/");
            // 获取类的全限定名
            String fullQualifiedName = classAbsolutePath
                    .replaceAll("^(.+test-classes/)(.+)(.class)$", "$2")
                    .replaceAll("^(.+classes/)(.+)(.class)$", "$2")
                    .replaceAll("/", ".");
            classNames.add(fullQualifiedName);
        }

        // 如果当前文件是文件夹，则再向下一层遍历
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                resolveClassNames(subFile, classNames);
            }
        }
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }
}
