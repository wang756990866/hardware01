package com.syy.hardware.controller;

import com.syy.hardware.Service.HardwareService;
import com.syy.hardware.config.ShiroRealm;
import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.HAttributeVal;
import com.syy.hardware.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<Object, Object> hardwareAdd(String items_id,String data){

        JSONArray jsonArray = JSONArray.fromObject(data);
        List<HAttribute> data1=(List<HAttribute>)jsonArray.toCollection(jsonArray, HAttribute.class);
        int i = hardwareService.setHardware(items_id,data1);

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
    public Map<Object, Object> hardwareUpdate(String data,String items_id,String hardware_id){

        JSONArray jsonArray = JSONArray.fromObject(data);
        List<HAttribute> data1=(List<HAttribute>)jsonArray.toCollection(jsonArray, HAttribute.class);

        int i= hardwareService.hardwareUpdate(data1,items_id,hardware_id);
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
    public Map<Object, Object> getHardwaresByIdAndName(String hardware_name,String items_id,String hardware_id){

        Map<Object, Object> itemsById = hardwareService.getHardwaresByIdAndName(hardware_name,items_id,hardware_id);


        Map<Object, Object> map = response.supperReturn(itemsById);

        return   map;
    }
}
