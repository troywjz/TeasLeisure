package com.zbkj.service.dao.tl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.common.model.tl.StallDailyStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface StallDailyStatusDao extends BaseMapper<StallDailyStatus> {
    @Insert("INSERT INTO eb_tl_stall_daily_status (stall_id,status_date,is_open,hours,updated_by,create_time,update_time) " +
            "VALUES (#{stallId},#{statusDate},#{isOpen},#{hours},#{userId},NOW(),NOW()) " +
            "ON DUPLICATE KEY UPDATE is_open=VALUES(is_open),hours=VALUES(hours),updated_by=VALUES(updated_by),update_time=NOW()")
    int upsertToday(@Param("stallId") Long stallId, @Param("statusDate") java.sql.Date statusDate,
                    @Param("isOpen") Boolean isOpen, @Param("hours") String hours, @Param("userId") Integer userId);
}
