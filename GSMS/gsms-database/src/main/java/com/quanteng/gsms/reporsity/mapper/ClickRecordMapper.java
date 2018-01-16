/**
 * 文 件 名:  ClickRecordMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/16 0016
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.reporsity.mapper;

import com.quanteng.gsms.reporsity.entity.ClickRecord;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/16 0016
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ClickRecordMapper {

    void add(ClickRecord clickRecord);

    void modify(ClickRecord clickRecord);

    void delete(String clickId);

    List<ClickRecord> query(Map<String, Object> params);

    int getTotal(Map<String, Object> params);

    ClickRecord queryByClickId(String clickId);

}