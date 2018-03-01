/**
 * 文 件 名:  TaskResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/14 0014
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.task;

<<<<<<< HEAD
import com.quanteng.gsmp.resource.task.request.CHReq;
=======
>>>>>>> 7471c5ec7df3c08f281e3684a78bccf89b489c99
import com.quanteng.gsmp.resource.task.request.CSReq;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <定时任务接口> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/14 0014
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/task")
public interface TaskResource {

    @POST
    @Path("/cs")
    @Produces(MediaType.APPLICATION_JSON)
    void conversionStatistics(CSReq req);

    @POST
    @Path("/ch")
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
    void conversionHandle(CHReq req);
=======
    void conversionHandle(String opType);
>>>>>>> 7471c5ec7df3c08f281e3684a78bccf89b489c99

}