package zym.util;

import zym.pojo.param.InputOutput;
import zym.pojo.param.TestInfo;

import java.io.*;
import java.sql.Timestamp;
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

    public String compile() throws IOException {
        File codeFile = new File(String.format("%s/%s/%s/%s/%s", "C:/Users", uid, qid, submitTime,getCodeFileName()));
        if (!codeFile.exists()) {
            codeFile.getParentFile().mkdirs();
            codeFile.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(codeFile), "UTF-8"))) {
            writer.write(code);
            writer.flush();
            writer.close();
        }

        ProcessBuilder processBuilder = new ProcessBuilder(compileCommands);
        processBuilder.directory(new File(String.format("%s/%s/%s/%s", "C:/Users", uid, qid, submitTime)));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                output.append(line + "\n");
            isCompiled = true;
            return output.toString().isEmpty() ? null : output.toString();
        }
    }

    public TestInfo execute(ArrayList<InputOutput> inputOutputs) throws IOException {
        if (!isCompiled) throw new IllegalStateException("not compiled");

        int correct = 0;
        ArrayList<InputOutput> results = new ArrayList<>();
        // test all test cases
        for (InputOutput inputOutput : inputOutputs) {
            String output = test(inputOutput.getInput());
            InputOutput actualInputOutput = new InputOutput();
            actualInputOutput.setInput(inputOutput.getInput());
            actualInputOutput.setOutput(output);
            if (output.equals(inputOutput.getOutput())) {
                correct++;
                actualInputOutput.setCorrect(true);
            } else {
                actualInputOutput.setCorrect(false);
            }
            results.add(actualInputOutput);
        }
        TestInfo testInfo = new TestInfo(uid, qid, new Timestamp(submitTime), code,
                (double) correct / (double) inputOutputs.size());
        testInfo.setInputOutputs(results);
        return testInfo;
    }

    private String test(String input) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(executeCommands);
        processBuilder.directory(new File(String.format("%s/%s/%s/%s", "C:/Users", uid, qid, submitTime)));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (OutputStream outputStream = process.getOutputStream()) {
            outputStream.write(input.getBytes("UTF-8"));
            outputStream.flush();
        }
        StringBuilder results = new StringBuilder();
        try (Scanner in = new Scanner(process.getInputStream(),"UTF-8")) {
            while (in.hasNextLine()){
                results.append(in.nextLine());
                System.out.println("此时InputStream结果为："+results);
            }
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
