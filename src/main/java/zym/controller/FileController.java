package zym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import zym.exception.MessageException;
import zym.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author zym
 * *
 */
@Controller
@RequestMapping(path = {"/file"})
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/homework/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadHomework(MultipartFile uploadFile, HttpServletRequest request, String courseId,
                                 String homeworkId, String userId) throws IOException, ParseException {
        if (uploadFile == null)
            throw new MessageException("文件为空");
        if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(homeworkId) || StringUtils.isEmpty(userId))
            throw new MessageException("参数为空");
        return fileService.saveUploadHomework(uploadFile, request, courseId, homeworkId, userId);
    }

    @RequestMapping(value = "/homework/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadHomework(String fileName, HttpServletRequest request, String courseId,
                                                   String homeworkId, String userId) throws IOException {
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(courseId) || StringUtils.isEmpty(homeworkId)
                || StringUtils.isEmpty(userId))
            throw new MessageException("参数为空");
        return fileService.downloadHomework(fileName, request, courseId, homeworkId, userId);
    }

    @RequestMapping(value = "/teacher/itemBank/upload", method = RequestMethod.POST)
    @ResponseBody
    public String batchAddItemBank(MultipartFile uploadFile) throws IOException {
        if (uploadFile == null)
            throw new MessageException("文件为空");
        return fileService.insertItemBankList(uploadFile);
    }

    @RequestMapping(value = "/teacher/question/upload", method = RequestMethod.POST)
    @ResponseBody
    public String batchAddQuestion(MultipartFile uploadFile) throws IOException {
        if (uploadFile == null)
            throw new MessageException("文件为空");
        return fileService.insertQuestionList(uploadFile);
    }
}
