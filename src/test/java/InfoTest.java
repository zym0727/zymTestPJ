import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zym.dao.*;
import zym.pojo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

/**
 * @author zym
 * *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml",
        "classpath:springMVC/spring-mvc.xml"})
//@Transactional
public class InfoTest {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MajorClassMapper majorClassMapper;

    @Autowired
    private HomeworkScoreMapper homeworkScoreMapper;

    @Test
    public void insertUser() {
        String k = "刘子慧、叶政儒、叶阳水、丁美君、杭佳弘、黄萱琇、周怡梅、王雅筑、涂智尧、杨薇爱、曾冠宇、张静怡、强骏宜、李珮霖、欧阳士琳、洪伟文、陈幼颖、林玮伦、姚奕翔、林佳湖、林建辉、周新秀、陈韦志、陈冠志、林裕仁、阙协兴、杨安琪、吴伊婷、王昆霖、谢峻容、徐佩君、荣嘉妤、李又宜、李舒华、李宗霞、王志鸿、常哲燕、许佳纯、涂雅雯、李珮明、李希季、钱雅芳、蔡亦淑、刘恩亚、陈永海、潘长菁、蔡美书、冯伟伦、张俊吉、杨盈秀、侯家荣、黄皓瑄、林怡雯、许庆松、张嘉珍、蔡俊玮、施惠玲、吴钰君、杨湘伯、郑宜珊、刘逸群、蔡亦秀、张轩爱、曹惠雯、王庆怡、陈嘉文、方庆云、吴振豪、毛香芸、林建星、江诚琇、陈家伟、许家慧、黄雅珊、刘必顺、陈怡婷";
        String[] map = k.split("、");
        Users users = new Users();
        users.setRoleId(3);
        int j = 14;
        users.setClassId(j);
        users.setEnabled(1);
        int i;
        String s = "student";
        for (i = j * 30 - 20; i < j * 30 + 10; i++) {
            String op = s + i;
            users.setAccount(op);
            users.setPassword(op);
            users.setUserNumber("20152681" + j + (i - (j * 30 - 21) < 10 ? "0" + (i - (j * 30 - 21)) : i - (j * 30 - 21)));
            users.setUserName(map[i - (j * 30 - 20)]);
            users.setSex((int) Math.round(Math.random() * 10) >= 3 ? 1 : 0);
            users.setTelephone("1528" + (int) (Math.random() * 10000000));
            usersMapper.insert(users);
        }
    }

    @Test
    public void insertHomeworkScore() throws ParseException {
        Homework homework = homeworkMapper.selectByPrimaryKey(30);
        Random r = new Random();
        Course course = courseMapper.selectByPrimaryKey(homework.getCourseId());
        List<MajorClass> majorClassList = majorClassMapper.getMajorClassListByIds(course.getClassIds());
        Users users = new Users();
        HomeworkScore homeworkScore = new HomeworkScore();
        String time = "2019-05-15 17:50:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        homeworkScore.setSubmitTime(simpleDateFormat.parse(time));
        homeworkScore.setAnswer("用于做成绩统计分析的填充数据，和题目的答案是否正确无关");
        homeworkScore.setEvaluate("用于做成绩统计分析的填充数据，和题目的答案是否正确无关,评价也全部一样");
        homeworkScore.setHomeworkId(homework.getId());
        majorClassList.forEach(majorClass -> {
            users.setClassId(majorClass.getId());
            List<Users> usersList = usersMapper.selectByUsers(users);
            usersList.forEach(users1 -> {
                homeworkScore.setStudentId(users1.getId());
                int score = (int) (10 * r.nextGaussian() + 70);
                homeworkScore.setScore(score > 100 ? 100 : score);
                homeworkScoreMapper.insert(homeworkScore);
            });
        });
    }
}
