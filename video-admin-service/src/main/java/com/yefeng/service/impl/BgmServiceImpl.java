package com.yefeng.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yefeng.dto.AdminUser;
import com.yefeng.dto.BgmPageInputDTO;
import com.yefeng.mapper.BgmMapper;
import com.yefeng.mapper.UserMapper;
import com.yefeng.pojo.Bgm;
import com.yefeng.pojo.User;
import com.yefeng.service.BgmService;
import com.yefeng.service.UserService;
import com.yefeng.util.JsonResult;
import com.yefeng.util.MD5Util;
import com.yefeng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    public JsonResult queryBgmList(BgmPageInputDTO inputDTO) {
        try {
            PageHelper.startPage(inputDTO.getPage(),inputDTO.getPageSize());
            List<Bgm> bgmList = bgmMapper.queryBgmList(inputDTO);
            if(bgmList != null && bgmList.size() > 0) {
                PageInfo<Bgm> pageInfo = new PageInfo<Bgm>(bgmList);
                return JsonResult.ok(pageInfo);
            }
            return JsonResult.ok("已无数据");
        }catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorMessage("服务请求出错");
        }
    }

    public JsonResult updateBgmStatus(Bgm bgm) {
        if(bgm != null) {
            try {
                bgmMapper.updateByPrimaryKeySelective(bgm);
                return JsonResult.ok("bgm更新成功");
            }catch (Exception e) {
                return JsonResult.errorMessage("更新bgm状态服务请求出错" + e.getMessage());
            }
        }
        return JsonResult.ok("bgm请求参数不能为空");
    }

    public JsonResult deleteBgm(Bgm bgm) {
        if(bgm != null && !StringUtil.isEmpty(bgm.getId())) {
            try {
                bgmMapper.deleteByPrimaryKey(bgm.getId());
                return JsonResult.ok("bgm删除成功");
            }catch (Exception e) {
                return JsonResult.errorMessage("更新bgm状态服务请求出错" + e.getMessage());
            }
        }
        return JsonResult.ok("bgm请求参数不能为空");
    }

    public String addBgm(String filePath, String name, String author) {
        Bgm bgm = new Bgm();
        Date date = new Date();
        bgm.setAuthor(author);
        bgm.setName(name);
        bgm.setPath(filePath);
        bgm.setCreateTime(date);
        bgm.setUpdateTime(date);
        bgm.setId(UUID.randomUUID().toString());
        if(bgmMapper.insert(bgm) > 0) {
            return "新增bgm成功";
        }
        return "新增bgm失败";
    }
}
