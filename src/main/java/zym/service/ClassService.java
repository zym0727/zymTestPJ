package zym.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zym.dao.MajorClassMapper;
import zym.pojo.MajorClass;
import zym.pojo.param.ClassPage;

import java.util.List;

/**
 * @author zym
 * *
 */
@Service
public class ClassService {

    @Autowired
    private MajorClassMapper majorClassMapper;

    public JSONObject getMajorClassList(ClassPage classPage) {
        classPage.setOffset((classPage.getPageNumber() - 1) * classPage.getPageSize());
        JSONObject result = new JSONObject();
        result.put("total", majorClassMapper.countAll(classPage));
        result.put("rows", majorClassMapper.selectClassList(classPage));
        return result;
    }

    public MajorClass getMajorClass(int id) {
        return majorClassMapper.selectByPrimaryKey(id);
    }

    public String deleteMajorClass(int id) {
        majorClassMapper.deleteByPrimaryKey(id);
        return JSONObject.toJSONString("success");
    }

    public String saveMajorClass(MajorClass majorClass) {
        if (checkRepeat(majorClass))
            return JSONObject.toJSONString("repeat");
        majorClassMapper.insert(majorClass);
        return JSONObject.toJSONString("success");
    }

    public String updateMajorClass(MajorClass majorClass) {
        if (checkRepeat(majorClass))
            return JSONObject.toJSONString("repeat");
        majorClassMapper.updateByPrimaryKeySelective(majorClass);
        return JSONObject.toJSONString("success");
    }

    public String deleteBatch(String ids) {
        majorClassMapper.batchDelete(ids.split(","));
        return JSONObject.toJSONString("success");
    }

    private Boolean checkRepeat(MajorClass majorClass) {
        List<MajorClass> majorClassList = majorClassMapper.selectRepeat(majorClass);
        if (majorClass.getId() != null) { //修改
            MajorClass origin = majorClassMapper.selectByPrimaryKey(majorClass.getId());
            //班级编号不能重复
            return majorClassList != null && majorClassList.size() > 0 &&
                    !majorClassList.get(0).getId().equals(origin.getId());
        } else  //添加
            return majorClassList != null && majorClassList.size() > 0;
    }
}
