package com.zonefun.backend.util;

import cn.hutool.core.util.ClassLoaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @ClassName ClassUtil
 * @Description 类相关工具类
 * @Date 2022/10/27 10:26
 * @Author ZoneFang
 */
@Slf4j
public class ClassUtil {
    /**
     * 获取包下所有类
     *
     * @param packageName 包名
     * @return java.lang.Class[]
     * @date 2022/10/27 10:27
     * @author ZoneFang
     */
    public static Class[] getClassByPackage(String packageName) {
        try {
            Enumeration<URL> resources = ClassLoader.getSystemResources(packageName.replaceAll("\\.", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String[] file = new File(url.getFile()).list();
                Class[] classList = new Class[file.length];
                for (int i = 0; i < file.length; i++) {
                    classList[i] = Class.forName(packageName + "." + file[i].replaceAll("\\.class", ""));
                }
                return classList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【{}】获取指定包下的类列表出错！", packageName);
        }
        return null;
    }

    /**
     * 获取包下所有有传入注解的类
     *
     * @param packageName 包名
     * @param checkAll    若传入了多个注解是否需要都满足
     * @param annotations 注解类名数组
     * @return java.lang.Class[]
     * @date 2022/10/27 10:45
     * @author ZoneFang
     */
    public static Set<Class<?>> getClassByPackage(String packageName, boolean checkAll, Class... annotations) {
        try {
            log.debug("获取【{}】包下的所有类", packageName);
            Enumeration<URL> resources = ClassLoaderUtil.getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            log.debug("是否获取到了类：{}", resources.hasMoreElements());
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                // 处理url
                Set<Class<?>> classList = handleUrl(url, packageName, packageName.replaceAll("\\.", "/"), true);
                // 若传入了注解则获取有这些注解的类
                if (annotations.length > 0) {
                    classList = classList.stream().filter(cls -> {
                        if (checkAll) {
                            boolean isFilter = true;
                            // 对传入注解进行循环判断
                            for (Class annotation : annotations) {
                                Annotation clsAnnotation = cls.getAnnotation(annotation);
                                // 若有一个注解不存在则被筛出
                                if (ObjectUtils.isEmpty(clsAnnotation)) {
                                    isFilter = false;
                                    break;
                                }
                            }
                            return isFilter;
                        } else {
                            boolean isFilter = false;
                            // 对传入注解进行循环判断
                            for (Class annotation : annotations) {
                                Annotation clsAnnotation = cls.getAnnotation(annotation);
                                // 若有一个注解存在则成功
                                if (!ObjectUtils.isEmpty(clsAnnotation)) {
                                    isFilter = true;
                                    break;
                                }
                            }
                            return isFilter;
                        }
                    }).collect(Collectors.toSet());
                }
                return classList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【{}】获取指定包下的类列表出错！", packageName);
        }
        return null;
    }

    /**
     * 处理url，将url所指文件/jar转为类列表
     *
     * @param url            url
     * @param packageName    包名
     * @param packageDirName 包名转的文件夹路径名
     * @param recursive      是否嵌套
     * @return java.util.List<java.lang.Class>
     * @date 2022/11/8 16:24
     * @author ZoneFang
     */
    private static Set<Class<?>> handleUrl(URL url, String packageName, String packageDirName, boolean recursive) {
        Set<Class<?>> classes = new HashSet<>();
        // 得到协议的名称
        String protocol = url.getProtocol();
        // 如果是以文件的形式保存在服务器上
        if ("file".equals(protocol)) {
            // 获取包的物理路径
            String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
            // 以文件的方式扫描整个包下的文件 并添加到集合中
            addClass(classes, filePath, packageName);
        } else if ("jar".equals(protocol)) {
            // 如果是jar包文件
            // 定义一个JarFile
            JarFile jar;
            try {
                // 获取jar
                jar = ((JarURLConnection) url.openConnection()).getJarFile();
                // 从此jar包 得到一个枚举类
                Enumeration<JarEntry> entries = jar.entries();
                // 同样的进行循环迭代
                while (entries.hasMoreElements()) {
                    // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    // 如果是以/开头的
                    if (name.charAt(0) == '/') {
                        // 获取后面的字符串
                        name = name.substring(1);
                    }
                    // 如果前半部分和定义的包名相同
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        // 如果以"/"结尾 是一个包
                        if (idx != -1) {
                            // 获取包名 把"/"替换成"."
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        // 如果可以迭代下去 并且是一个包
                        if ((idx != -1) || recursive) {
                            // 如果是一个.class文件 而且不是目录
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                // 去掉后面的".class" 获取真正的类名
                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                try {
                                    // 添加到classes
                                    classes.add(Class.forName(packageName + '.' + className));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    /**
     * 文件类url组装类列表方法
     *
     * @param classes     现有类列表
     * @param filePath    文件路径
     * @param packageName 包路径
     * @return void
     * @date 2022/11/8 16:29
     * @author ZoneFang
     */
    public static void addClass(Set<Class<?>> classes, String filePath, String packageName) {
        File[] files = new File(filePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        assert files != null;
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (!packageName.isEmpty()) {
                    className = packageName + "." + className;
                }
                doAddClass(classes, className);
            }

        }
    }

    /**
     * 组装class列表 通过classLoader来加载类
     *
     * @param classes   现有类列表
     * @param className 需要组装的类名
     * @return void
     * @date 2022/11/8 16:28
     * @author ZoneFang
     */
    public static void doAddClass(Set<Class<?>> classes, final String className) {
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        try {
            classes.add(classLoader.loadClass(className));
        } catch (ClassNotFoundException e) {
            log.error("未能找到类：{}", className);
            e.printStackTrace();
        }
    }
}
