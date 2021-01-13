package classloader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MyClassLoader extends ClassLoader {
    public static Class getClassFromJavaFile(String dirPath, String pakagePath) {
        //校验参数是否为空
        if (pakagePath == null || "".equals(pakagePath) || dirPath == null || "".equals(dirPath))
        {
            return null;
        }
        // 将路径中的 . 替换为 / 
        String pakageDir = pakagePath.replaceAll("\\.", "/");
        // src/main/java为java
        String filePath = dirPath.concat("/src/main/java/").concat(pakageDir).concat(".java");
        // 文件的特定目录

        //编译
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        int compilationResult = javac.run(null, null, null, filePath);
        //compilationResult == 0,说明编译成功，在Java文件的同目录下会生成相应的class文件
        if (compilationResult != 0)
        {
            throw new IllegalArgumentException("编译失败");
        }

        Class<?> clazz = null;
        try {
            //使用自定义ClassLoader
            MyClassLoader loader = new MyClassLoader(dirPath);
            clazz = loader.findClass(pakagePath);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return clazz;
    }
    // 文件目录，例如:"file:/today/javadir/src/main/java/"
    private String classDir;

    @Override
    public Class<?> findClass(String name) {
        //class文件的真实路径
        String realPath = classDir + name.replace(".", "/") + ".class";
        byte[] cLassBytes = null;
        Path path = null;

        try {

            path = Paths.get(new URI(realPath));
            cLassBytes = Files.readAllBytes(path);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        //调用父类的defineClass方法
        return defineClass(name, cLassBytes, 0, cLassBytes.length);
    }

    public MyClassLoader(String classDir) {
        //拼接 “file:/”前缀
        this.classDir = "file:/".concat(classDir).concat("/src/main/java/");
    }


}
