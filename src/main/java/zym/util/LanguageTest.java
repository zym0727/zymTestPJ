package zym.util;

import zym.pojo.TestData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author zym
 * *
 */
public abstract class LanguageTest {

    private int uid;//用户id
    private int qid;//题目id
    private long submitTime;//提交时间
    private String code;//代码
    private String codeDir;//代码路径
    private String codeFile;//代码文件路径
    private String dockerDir;//容器中的工作路径
    private boolean isCompiled = false;//是否编译
    private List<String> compileCommands = new ArrayList<>();//编译指令
    private List<String> executeCommands = new ArrayList<>();//输出指令

    public LanguageTest(int uid, int qid, String code, long submitTime) {
        this.uid = uid;
        this.qid = qid;
        this.code = code;
        this.submitTime = submitTime;
        this.codeDir = String.format("%s/%s/%s/%s", "/c/Users", uid, qid, submitTime);
        this.codeFile = String.format("%s/%s", codeDir, getCodeFileName());
        this.dockerDir = String.format("/%s/%s/%s/%s", "usr", "src", uid, qid);
        this.compileCommands = getCompileCommands();
        this.executeCommands = getExecuteCommands();
    }

    protected abstract List<String> getCompileCommands();

    protected abstract List<String> getExecuteCommands();

    protected abstract String getCodeFileName();

    /**
     * 代码文件编译
     *
     * @return
     * @throws IOException
     */
    public String compile() throws IOException {
        //先创建编译前的代码文件
        File codeFile = new File(String.format("%s/%s/%s/%s/%s", "C:/Users", uid, qid, submitTime,
                getCodeFileName()));
        if (!codeFile.exists()) {
            codeFile.getParentFile().mkdirs();
            codeFile.createNewFile();
        }
        //把代码写入到该文件中
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(codeFile), "UTF-8"))) {
            writer.write(code);
            writer.flush();
            writer.close();
        }
        //启动系统进程，设定工作路径，调用指令
        ProcessBuilder processBuilder = new ProcessBuilder(compileCommands);
        processBuilder.directory(new File(String.format("%s/%s/%s/%s", "C:/Users", uid, qid, submitTime)));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        //获取编译结果返回
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                output.append(line).append(System.lineSeparator());
            isCompiled = true;
            return output.toString().isEmpty() ? null : output.toString();
        }
    }

    /**
     * 代码执行
     *
     * @param testDataList
     * @return
     * @throws IOException
     */
    public int execute(List<TestData> testDataList) throws IOException {
        if (!isCompiled) throw new IllegalStateException("not compiled");

        int correct = 0;
        // 执行所有的输入输出数据
        for (TestData testData : testDataList) {
            //执行一组输入输出
            System.out.println("数据库中的一组输出：" + testData.getOutput());
            String output = test(testData.getInput());
            System.out.println("两者是否相同：" + output.equals(testData.getOutput()));
            if (output.equals(testData.getOutput()))
                correct++;
        }
        return correct;
    }

    /**
     * 将一组输入进行运行并得到结果
     *
     * @param input
     * @return
     * @throws IOException
     */
    private String test(String input) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(executeCommands);
        processBuilder.directory(new File(String.format("%s/%s/%s/%s", "C:/Users", uid, qid, submitTime)));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        //把一组输入写到运行的程序中
        try (OutputStream outputStream = process.getOutputStream()) {
            outputStream.write(input.getBytes("UTF-8"));
            outputStream.flush();
        }
        //获取运行的输出结果
        StringBuilder results = new StringBuilder();
        try (Scanner in = new Scanner(process.getInputStream(), "UTF-8")) {
            while (in.hasNextLine()) {
                results.append(in.nextLine());
                if (in.hasNextLine())
                    results.append(System.lineSeparator());//windows环境下是"\r\n",linux环境下是"\n"，mac环境下是"\r"
            }
            System.out.println("此时InputStream结果为：" + results);
        }
        return results.toString();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDir() {
        return codeDir;
    }

    public void setCodeDir(String codeDir) {
        this.codeDir = codeDir;
    }

    public String getCodeFile() {
        return codeFile;
    }

    public void setCodeFile(String codeFile) {
        this.codeFile = codeFile;
    }

    public String getDockerDir() {
        return dockerDir;
    }

    public void setDockerDir(String dockerDir) {
        this.dockerDir = dockerDir;
    }

    public boolean isCompiled() {
        return isCompiled;
    }

    public void setCompiled(boolean compiled) {
        isCompiled = compiled;
    }

    public void setCompileCommands(List<String> compileCommands) {
        this.compileCommands = compileCommands;
    }

    public void setExecuteCommands(List<String> executeCommands) {
        this.executeCommands = executeCommands;
    }
}
