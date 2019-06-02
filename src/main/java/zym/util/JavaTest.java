package zym.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zym
 * *
 */
public class JavaTest extends LanguageTest {

    public JavaTest(int uid, int qid, String code, long submitTime) {
        super(uid, qid, code, submitTime);
    }

    /**
     * docker run -v /c/Users/bootstrap:/usr/src/bootstrap -w /usr/src/bootstrap nodejs npm run dev
     * Java编译，例如docker run --rm -v /c/Users/code:/usr/src/code -w /usr/src/code openjdk:8 javac hello.java
     * 通过windows命令运行一个docker容器，把c盘上的代码挂载到容器中，使用容器的javac命令来编译工作目录中的hello.java文件
     * windows中的docker挂载一般是对/c/Users目录下有效，除非修改虚拟机的共享目录
     * 另外。一般oj系统对使用Java语言的代码有要求，如主类名需要为Main，输入为Scanner sc = new Scanner(System.in)
     *
     * @return
     */
    @Override
    protected List<String> getCompileCommands() {
        ArrayList<String> compileCommands = new ArrayList<>();//编译指令，指令中以空格分割为各个元素
        compileCommands.add("docker");
        compileCommands.add("run");
        compileCommands.add("--rm");//用完就删除
        compileCommands.add("-v");//把宿主机上的目录挂载到容器里
        compileCommands.add(String.format("%s:%s", getCodeDir(), getDockerDir()));
        compileCommands.add("-w");//指定容器的某目录为工作目录
        compileCommands.add(getDockerDir());
        compileCommands.add("openjdk:8");//jdk镜像
        compileCommands.add("javac");
        compileCommands.add(getCodeFileName());
        return compileCommands;
    }

    /**
     * Java运行，例如docker run --rm -v /c/Users/code:/usr/src/code -w /usr/src/code openjdk:8 timeout 3s java hello
     *
     * @return
     */
    @Override
    protected List<String> getExecuteCommands() {
        ArrayList<String> executeCommands = new ArrayList<>();
        executeCommands.add("docker");
        executeCommands.add("run");
        executeCommands.add("-i");//开启交互模式，即可以进行标准输入
        executeCommands.add("--rm");//用完就删除
        executeCommands.add("-v");//把宿主机上的目录挂载到容器里
        executeCommands.add(String.format("%s:%s", getCodeDir(), getDockerDir()));
        executeCommands.add("-w");//指定容器的某目录为工作目录
        executeCommands.add(getDockerDir());
        executeCommands.add("openjdk:8");
        executeCommands.add("timeout");//Linux timeout 限时函数
        executeCommands.add("3s");
        executeCommands.add("java");
        executeCommands.add("Main");
        return executeCommands;
    }

    @Override
    protected String getCodeFileName() {
        return "Main.java";
    }
}
