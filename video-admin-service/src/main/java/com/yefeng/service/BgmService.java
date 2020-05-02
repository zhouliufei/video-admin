package com.yefeng.service;

import com.yefeng.dto.BgmPageInputDTO;
import com.yefeng.pojo.Bgm;
import com.yefeng.util.JsonResult;

public interface BgmService {

    /**
     * 根据当前页和分页大小获取bgm列表
     * */
    JsonResult queryBgmList(BgmPageInputDTO inputDTO);
    /**
     * 根据id，更新bgm行的数据
     * */
    JsonResult updateBgmStatus(Bgm bgm);
    /**
     * 根据id，删除bgm数据
     * */
    JsonResult deleteBgm(Bgm bgm);

    /**
     * 新增Bgm
     **/
    String addBgm(String filePath, String name, String author);
}
