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
import zym.pojo.HomeworkScore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author zym
 * *
 */
@Service
public class FileService {

    @Autowired
    private HomeworkService homeworkService;

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
}
