package security.web;

import org.bookManageSystem.fundamental.logger.FundamentalLogger;
import org.bookManageSystem.fundamental.security.entity.App;
import org.bookManageSystem.fundamental.security.service.AppService;
import org.bookManageSystem.fundamental.util.json.JsonMapper;
import org.bookManageSystem.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunhui
 * Date: 14-5-12
 * Time: 上午9:33
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/app")
public class AppServiceWeb {
    private static FundamentalLogger logger = FundamentalLogger.getLogger(AppServiceWeb.class);

    @Autowired
    private AppService appService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("description") String description/*,@FormParam("status") String status*/){
        if(name==null ||name.trim().equals("")|| description==null|| description.trim().equals("")/* ||status==null|| status.trim().equals("")*/){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        long id;
        try{
            id = appService.getIdByName(name);
        }
        catch (Exception ex){
            id=0;
        }
        if(id==0){
        Date now=new Date();
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // String createtime = dateFormat.format( now );

        App app=new App();
        app.setName(name);
        app.setDescription(description);
        app.setStatus("启用");
        app.setCreatetime(now);

        appService.add(app);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }
        else{
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "企业名已存在!");
        }
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/list")
    @GET
    public String list(){

        List<App> list=appService.list();

        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON+ ";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){
        App app = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,App.class);

        int result = appService.delete(app);
        if(result>0){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
        App app = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,App.class);
        Date now=new Date();
        app.setCreatetime(now);
        int result = appService.update(app);
        if(result>0){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }

}