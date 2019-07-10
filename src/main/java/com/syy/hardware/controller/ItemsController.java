package com.syy.hardware.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.syy.hardware.Service.ItemsService;
import com.syy.hardware.config.ShiroRealm;
import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.IAttribute;
import com.syy.hardware.entity.IAttributeVal;
import com.syy.hardware.entity.IItems;
import com.syy.hardware.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 15:59
 * @Version 1.0
 **/
@RestController
@Api(value = "/items", description = "项目数据")
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    Response response=new Response();

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @RequestMapping("/getItemsById")
    @ResponseBody
    @ApiOperation(value="根据项目ID获取一条数据", notes="根据项目ID获取一条数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public String getItemsById(String itemsId){

        Map<Object, Object> itemsById = itemsService.getItemsById(itemsId);
        logger.info("通过ID获取数据");
        return  "根据ID获取到一条数据 : " + itemsById;
    }

    @RequestMapping("/getItemsAll")
    @ResponseBody
    @ApiOperation(value="获取项目列表", notes="获取项目列表")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public  Map<Object, Object> getItemsAll(String items_branch,String items_years){
        Integer page=0;
        Integer size=0;
        Map<Object, Object> itemsAll = itemsService.getItemsAll(new Page<>(page, size), items_branch,items_years);
        Map<Object, Object> map = response.supperReturn(itemsAll);
        System.out.printf("完成查询");
        return  map;
    }

    @RequestMapping("/getItemsByIdAndName")
    @ResponseBody
    @ApiOperation(value="通过ID，Name获取项目", notes="通过ID，Name获取数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getItemsByIdAndName(String items_id,String items_name,String items_branch,String items_years){

        Map<Object, Object> itemsByIdAndName = itemsService.getItemsByIdAndName(items_id, items_name, items_branch, items_years);
        Map<Object, Object> map = response.supperReturn(itemsByIdAndName);

        return map;
    }

    @RequestMapping("/getItemsVal")
    @ResponseBody
    @ApiOperation(value="通过所有项目", notes="通过ID，Name获取数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object,Object> getItemsVal(){
        IItems iItems=new IItems();
        List<IItems> itemsVal = itemsService.getItemsVal(iItems);
        Map<Object, Object> map = response.supperReturn(itemsVal);
        return map;
    }



    @RequestMapping("/setItems")
    @ResponseBody
    @ApiOperation(value="添加项目", notes="通过ID，Name获取数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object,Object> setItems(String data,String items_branch, String items_years){

        JSONArray jsonArray = JSONArray.fromObject(data);
        List<IAttribute> data1=(List<IAttribute>)jsonArray.toCollection(jsonArray,IAttribute.class);
        int id = itemsService.setItems(data1, items_branch, items_years);
        Map<Object, Object> map = response.supperReturn(id);

        return map;
    }

    @RequestMapping("/itemsAttributeAdd")
    @ResponseBody
    @ApiOperation(value="添加项目属性", notes="添加属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object,Object> itemsAttributeAdd(String data){
        System.out.printf(data);
        JSONObject jsonObject1 = JSONObject.fromObject(data);
        JSONArray array=(JSONArray)jsonObject1.get("attributeList");
        IAttribute data1=(IAttribute)JSONObject.toBean(jsonObject1, IAttribute.class);
        int id = itemsService.itemsAttributeAdd(data1,array);
        Map<Object, Object> map = response.supperReturn(id);
        return map;
    }


    @RequestMapping("/getItemsAttribute")
    @ResponseBody
    @ApiOperation(value="查询项目属性列表", notes="查询项目属性列表")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getItemsAttribute(){

        List<IAttribute> list= itemsService.getItemsAttribute();

        Map<Object, Object> map = response.supperReturn(list);
        return map;
    }


    @RequestMapping("/itemAttributeDelete")
    @ResponseBody
    @ApiOperation(value="根据ID删除属性", notes="根据ID删除属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> itemAttributeDelete(String attribute_id){

        int i= itemsService.itemAttributeDelete(attribute_id);

        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }

    @RequestMapping("/itemsDelete")
    @ResponseBody
    @ApiOperation(value="根据ID删除项目", notes="根据ID删除项目")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> itemsDelete(String items_id){

        int i= itemsService.itemsDelete(items_id);

        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }


    @RequestMapping("/itemAttributeUpdate")
    @ResponseBody
    @ApiOperation(value="根据ID修改项目属性（具体属性）", notes="根据ID修改项目属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> itemAttributeUpdate(String data){

        JSONObject jsonObject1 = JSONObject.fromObject(data);
        jsonObject1.get("");

        IAttribute data1=(IAttribute)JSONObject.toBean(jsonObject1, IAttribute.class);
        int i = itemsService.itemAttributeUpdate(data1);
        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }

    @RequestMapping("/itemsUpdate")
    @ResponseBody
    @ApiOperation(value="修改项目属性值", notes="修改项目属性值")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> itemsUpdate(String data,String items_id){

        JSONArray jsonObject1 = JSONArray.fromObject(data);
        List<IAttribute> list=new ArrayList<IAttribute>();
        for (int i = 0; i < jsonObject1.size(); i++) {
            JSONObject o = (JSONObject)jsonObject1.get(i);
            IAttribute data1=(IAttribute)JSONObject.toBean(o, IAttribute.class);
            list.add(data1);
        }

        int i = itemsService.itemsUpdate(list,items_id);
        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }
}
