package zym.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zym
 * *
 */
public class CppTest extends LanguageTest {

    public CppTest(int uid, int qid, String code, long submitTime) {
        super(uid, qid, code, submitTime);
    }

    /**
     * C++编译，例如docker run --rm -v /c/Users/code:/usr/src/code -w /usr/src/code gcc g++ hello.cpp
     *
     * @return
     */
    @Override
    protected List<String> getCompileCommands() {
        ArrayList<String> compileCommands = new ArrayList<>();
        compileCommands.add("docker");
        compileCommands.add("run");
        compileCommands.add("--rm");//用完就删除
        compileCommands.add("-v");//把宿主机上的目录挂载到容器里
        compileCommands.add(String.format("%s:%s", getCodeDir(), getDockerDir()));
        compileCommands.add("-w");//指定容器的某目录为工作目录
        compileCommands.add(getDockerDir());
        compileCommands.add("gcc");//gcc镜像
        compileCommands.add("g++");
        compileCommands.add(getCodeFileName());
        return compileCommands;
    }

    /**
     * C++运行，例如docker run -i --rm -v /c/Users/code:/usr/src/code -w /usr/src/code gcc timeout 3s ./a.out
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
        executeCommands.add("gcc");//gcc镜像
        executeCommands.add("timeout");//Linux timeout 限时函数
        executeCommands.add("3s");
        executeCommands.add("./a.out");//C++编译后统一为a.out
        return executeCommands;
    }

    @Override
    protected String getCodeFileName() {
        return "Main.cpp";
    }

}
