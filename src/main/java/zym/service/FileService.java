package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zym.dao.*;
import zym.pojo.*;
import zym.util.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class FileService {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private ItemBankMapper itemBankMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private LanguageMarkMapper languageMarkMapper;

    @Autowired
    private TestDataMapper testDataMapper;

    @Autowired
    private MajorClassMapper majorClassMapper;

    public String saveUploadHomework(MultipartFile uploadFile, HttpServletRequest request, String courseId,
                                     String homeworkId, String userId) throws IOException, ParseException {
        String path = request.getSession().getServletContext().getRealPath("/");
        String uploadPath = path + "homeworkFile" + File.separator + courseId + File.separator + userId
                + File.separator + homeworkId;
        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();   //如果该目录不存在，就创建此抽象路径名指定的目录(包括所有必需但不存在的父目录)。
        } else {
            File files[] = saveFile.listFiles();
            if (files != null) {
                for (File curFile : files) {
                    curFile.delete();
                }
            }
            saveFile.mkdirs();//如果该目录存在且已有文件，删除并重新创建。
        }
        String fileName = uploadFile.getOriginalFilename();
        String finalFile = uploadPath + File.separator + fileName;
        uploadFile.transferTo(new File(finalFile));//写到最后存储的地方
        //保存此次作业记录
        HomeworkScore homeworkScore = new HomeworkScore();
        homeworkScore.setStudentId(Integer.parseInt(userId));
        homeworkScore.setHomeworkId(Integer.parseInt(homeworkId));
        homeworkScore.setFilePath(finalFile);
        homeworkScore.setFileName(fileName);
        homeworkService.saveHomeworkScore(homeworkScore);
        return JSONObject.toJSONString("success");
    }

    public ResponseEntity<byte[]> downloadHomework(String fileName, HttpServletRequest request, String courseId,
                                                   String homeworkId, String userId) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/");
        String downloadPath = path + "homeworkFile" + File.separator + courseId + File.separator + userId
                + File.separator + homeworkId + File.separator + fileName;//得到文件所在位置
        System.out.println(fileName);
        //防止中文乱码
        String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
        File file = new File(downloadPath);
        HttpHeaders headers = new HttpHeaders();//设置响应头
        //通知浏览器以attachment（下载方式）打开
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    public String insertItemBankList(MultipartFile uploadFile) throws IOException {
        List<String[]> data = ExcelUtil.readExcel(uploadFile);
        ItemBank itemBank = new ItemBank();
        String result = "success";
        for (String[] mes : data) {
            itemBank.setItemName(mes[0]);
            List<ItemBank> itemBankList = itemBankMapper.selectRepeat(itemBank);
            //去重验证，题库名称相同不插入,同时对于""的一类值不添加进来
            if (itemBankList != null && itemBankList.size() > 0)
                result = "repeat";
            else {
                itemBank.setDescription(mes[1]);
                itemBankMapper.insert(itemBank);
            }
        }
        return JSONObject.toJSONString(result);
    }

    public String insertQuestionList(MultipartFile uploadFile) throws IOException {
        List<String[]> data = ExcelUtil.readExcel(uploadFile);
        String result = "success";
        Question question = new Question();
        ItemBank itemBank = new ItemBank();
        LanguageMark languageMark = new LanguageMark();
        TestData testData = new TestData();
        for (String[] mes : data) {
            if (mes.length > 3 && !mes[3].equals("")) {//题目的长度大于3，题目中的第四列不为空，为题库名称
                question.setQuestionNumber(mes[0]);
                List<Question> questionList = questionMapper.selectRepeat(question);
                //去重验证，题目编号相同不插入,同时对于""的一类值不添加进来
                if (questionList != null && questionList.size() > 0)
                    result = "repeat";
                else {
                    question.setQuestionName(mes[1]);
                    question.setDescription(mes[2]);
                    question.setAnswer(mes[4]);
                    itemBank.setItemName(mes[3]);
                    List<ItemBank> itemBankList = itemBankMapper.selectRepeat(itemBank);
                    //没有改题库id出错了
                    if (itemBankList != null && itemBankList.size() > 0) {
                        boolean isWong = false;
                        //语言标记非空情况下
                        if (!mes[0].equals("")) {
                            languageMark.setMark(mes[5]);
                            List<LanguageMark> languageMarkList = languageMarkMapper.selectRepeat(languageMark);
                            //没有语言标记id出错了
                            if (languageMarkList != null && languageMarkList.size() > 0)
                                question.setLanguageId(languageMarkList.get(0).getId());
                            else {
                                result = "error";
                                isWong = true;
                            }

                        }
                        question.setItemId(itemBankList.get(0).getId());
                        //正确情况下插入
                        if (!isWong)
                            questionMapper.insert(question);

                    } else
                        result = "error";
                }
            } else {//输入输出数据
                Question test = new Question();
                test.setQuestionNumber(mes[0]);
                List<Question> questionList = questionMapper.selectRepeat(test);
                //没有题目id出错了
                if (questionList != null && questionList.size() > 0) {
                    testData.setQuestionId(questionList.get(0).getId());
                    testData.setInput(mes[1]);
                    testData.setOutput(mes[2]);
                    testDataMapper.insert(testData);
                } else
                    result = "error";

            }
        }
        return JSONObject.toJSONString(result);
    }

    public String insertMajorClassList(MultipartFile uploadFile) throws IOException {
        List<String[]> data = ExcelUtil.readExcel(uploadFile);
        MajorClass majorClass = new MajorClass();
        String result = "success";
        for (String[] mes : data) {
            majorClass.setClassNumber(mes[0]);
            List<MajorClass> majorClassList = majorClassMapper.selectRepeat(majorClass);
            //去重验证，班级编号相同不插入,同时对于""的一类值不添加进来
            if (majorClassList != null && majorClassList.size() > 0)
                result = "repeat";
            else {
                majorClass.setClassName(mes[1]);
                majorClass.setGrade(mes[2]);
                majorClassMapper.insert(majorClass);
            }
        }
        return JSONObject.toJSONString(result);
    }
}
