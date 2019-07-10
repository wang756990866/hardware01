package com.syy.hardware.controller;

import com.syy.hardware.Service.HardwareService;
import com.syy.hardware.config.ShiroRealm;
import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.HAttributeClassify;
import com.syy.hardware.entity.HAttributeVal;
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

import java.util.ArrayList;
import java.util.HashMap;
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
@Api(value = "/hardware", description = "项目数据")
@RequestMapping("/hardware")
public class HardwareController {

    @Autowired
    private HardwareService hardwareService;

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    Response response=new Response();

    @RequestMapping("/getHardwaresByItemsId")
    @ResponseBody
    @ApiOperation(value="根据项目ID获取数据", notes="根据项目ID获取数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getHardwaresByItemsId(String items_id){
        Map<Object, Object> itemsById = hardwareService.getHardwaresByItemsId(items_id);
        Map<Object, Object> map = response.supperReturn(itemsById);

        return   map;
    }

    @RequestMapping("/hardwareAdd")
    @ResponseBody
    @ApiOperation(value="添加产品数据", notes="添加产品数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareAdd(String items_id,String data,String classify_id){

        JSONArray jsonArray = JSONArray.fromObject(data);

        List<HAttributeClassify> hAttributeClassifies = jsonArrayToBean(jsonArray);
        // List<HAttributeClassify> data2 = (List<HAttributeClassify>)jsonArray.toCollection(jsonArray, HAttributeClassify.class);

        int i = 1;
        int i1 = hardwareService.setHardware(items_id, hAttributeClassifies, classify_id);

        Map<Object, Object> map = response.supperReturn(i);
        return   map;
    }


    @RequestMapping("/codeAdd")
    @ResponseBody
    @ApiOperation(value="添加产品数据", notes="添加产品数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> codeAdd(String items_id,String code_number,String code_type){

        int y = hardwareService.setCode(items_id, code_number, code_type);


        Map<Object, Object> map = response.supperReturn(y);

        return   map;
    }


    @RequestMapping("/getclassify")
    @ResponseBody
    @ApiOperation(value="添加产品数据", notes="添加产品数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getclassify(String classify){

        List<HAttributeVal> hAttribute= hardwareService.getclassify(classify);


        Map<Object, Object> map = response.supperReturn(hAttribute);

        return   map;
    }


    @RequestMapping("/getCode")
    @ResponseBody
    @ApiOperation(value="获取二维码数据", notes="获取二维码数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getCode(String items_id,String classify){

        List< Map<Object, Object>> hAttribute= hardwareService.getCode(items_id,classify);

        Map<Object, Object> map = response.supperReturn(hAttribute);

        return   map;
    }


    @RequestMapping("/getCodeByName")
    @ResponseBody
    @ApiOperation(value="通过名称模糊获取到所有类别", notes="获取二维码数据")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getCodeByName(String queryString,String items_id,String classify){

        List< Map<Object, Object>> hAttribute= hardwareService.getCodeByName(queryString,items_id,classify);

        Map<Object, Object> map = response.supperReturn(hAttribute);

        return   map;
    }


    @RequestMapping("/hardwareUpdate")
    @ResponseBody
    @ApiOperation(value="根据hardwareId修改单条硬件属性值", notes="根据hardwareId修改单条硬件属性值")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareUpdate(String data,String items_id,String hardware_id,String classify){

        JSONArray jsonArray = JSONArray.fromObject(data);
        List<HAttributeClassify> hAttributeClassifies = jsonArrayToBean(jsonArray);
        List<HAttribute> data1=(List<HAttribute>)jsonArray.toCollection(jsonArray, HAttribute.class);

        int i= hardwareService.hardwareUpdate(hAttributeClassifies,items_id,hardware_id,classify);
        Map<Object, Object> map =new HashMap<>();
        if(i == 1){
            map = response.supperReturn(i);
        }else
        if(i == 2){
            map = response.errReturn(i);
        }

        return   map;
    }


    @RequestMapping("/getHardwaresByIdAndName")
    @ResponseBody
    @ApiOperation(value="根据hardwareId修改单条硬件属性值", notes="根据hardwareId修改单条硬件属性值")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getHardwaresByIdAndName(String hardware_classify,String hardware_codeType,String hardware_name,String items_id,String hardware_id){

        Map<Object, Object> itemsById = hardwareService.getHardwaresByIdAndName(hardware_classify,hardware_codeType,hardware_name,items_id,hardware_id);


        Map<Object, Object> map = response.supperReturn(itemsById);

        return   map;
    }


    @RequestMapping("/hardwareAttributeAdd")
    @ResponseBody
    @ApiOperation(value="新增单条硬件属性值", notes="新增单条硬件属性值")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareAttributeAdd(String data){
        JSONArray array= new JSONArray();
        JSONObject jsonObject1 = JSONObject.fromObject(data);
        if(jsonObject1.get("attributeList") != null){
            array=(JSONArray)jsonObject1.get("attributeList");
        }


        HAttribute Attribute =(HAttribute)JSONObject.toBean(jsonObject1, HAttribute.class);

        int id = hardwareService.hardwareAttributeAdd(Attribute,array);
        Map<Object, Object> map = response.supperReturn(id);
        return map;
    }


    @RequestMapping("/getHardwareAttribute")
    @ResponseBody
    @ApiOperation(value="查询硬件属性列表", notes="查询硬件属性列表")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getHardwareAttribute(){

        List<HAttributeClassify> hAttributeClassifies = hardwareService.getHardwareAttribute();
        Map<Object, Object> map = response.supperReturn(hAttributeClassifies);
        return map;
    }


    @RequestMapping("/getHardwareByCodeId")
    @ResponseBody
    @ApiOperation(value="查询硬件属性列表", notes="查询硬件属性列表")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> getHardwareByCodeId(String code_id){

        Map<Object, Object> hardwareByCodeId = hardwareService.getHardwareByCodeId(code_id);
        Map<Object, Object> map = response.supperReturn(hardwareByCodeId);
        return map;
    }

    @RequestMapping("/hardwareAttributeDelete")
    @ResponseBody
    @ApiOperation(value="根据ID删除属性", notes="根据ID删除属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareAttributeDelete(String attribute_id){

        int i =hardwareService.hardwareAttributeDelete(attribute_id);
        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }


    @RequestMapping("/hardwareDelete")
    @ResponseBody
    @ApiOperation(value="根据ID删除属性", notes="根据ID删除属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareDelete(String hardware_id){

        int i =hardwareService.hardwareDelete(hardware_id);
        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }


    @RequestMapping("/hardwareAttributeClassifyAdd")
    @ResponseBody
    @ApiOperation(value="根据ID删除属性", notes="根据ID删除属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareAttributeClassifyAdd(String data){
        JSONObject jsonObject1 = JSONObject.fromObject(data);
        HAttributeClassify hAttributeClassify = (HAttributeClassify)
            JSONObject.toBean(jsonObject1, HAttributeClassify.class);
        int i =hardwareService.hardwareAttributeClassifyAdd(hAttributeClassify);
        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }

    @RequestMapping("/hardwareAttributeClassifyDelete")
    @ResponseBody
    @ApiOperation(value="根据ID删除属性", notes="根据ID删除属性")
    @CrossOrigin(allowCredentials="true",maxAge = 3600,origins = "*")
    public Map<Object, Object> hardwareAttributeClassifyDelete(String classify_id){

        int i =hardwareService.hardwareAttributeClassifyDelete(classify_id);

        Map<Object, Object> map = response.supperReturn(i);
        return map;
    }

    private List<HAttributeClassify> jsonArrayToBean(JSONArray jsonArray){
        List<HAttributeClassify> data2 =new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject attributeClassifyEntry = (JSONObject)jsonArray.get(i);
            String s = attributeClassifyEntry.toString();
            Map classMap = new HashMap();
            classMap.put("attributeList",HAttribute.class);
            HAttributeClassify hAttributeClassify = (HAttributeClassify)JSONObject.toBean(attributeClassifyEntry, HAttributeClassify.class, classMap);
            data2.add(hAttributeClassify);
        }
        return data2;
    }
}
