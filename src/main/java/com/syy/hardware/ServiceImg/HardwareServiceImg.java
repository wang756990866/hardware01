package com.syy.hardware.ServiceImg;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.syy.hardware.Service.HardwareService;
import com.syy.hardware.dao.*;
import com.syy.hardware.entity.*;
import com.syy.hardware.util.Uuid;
import com.syy.hardware.util.ZXingCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ServiceImg
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 18:35
 * @Version 1.0
 **/
@Service
public class HardwareServiceImg implements HardwareService {

    @Autowired
    private HHardwareDao hardwareDao;

    @Autowired
    private HHardwareAttributeValDao hardwareAttributeValDao;

    @Autowired
    private HAttributeDao attributeDao;

    @Autowired
    private HAttributeValDao attributeValDao;

    @Autowired
    private IItemsDao itemsDao;

    @Autowired
    private CodeDao codeDao;

    Uuid uuid=new Uuid();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @Override
    public Map<Object, Object> getHardwaresByItemsId(String items_id){

        List<Map<Object, Object>> list=hardwareDao.getHardwaresByItemsId(items_id);
        List<HAttribute> hAttributes = attributeDao.selectList(
                new EntityWrapper<HAttribute>()
        );
        Map<Object, Object> map1 = hardwareListToMap(list, hAttributes);

        return map1;
    }

    @Override
    @Transactional
    public int setHardware(String items_id, List<HAttribute> data){
        //用来存储项目表相关信息
        HHardware hardware=new HHardware();
        //用来存储属性值表相关信息
        List<HAttributeVal> attributeValList=new ArrayList<>();
        //用来项目和属性值关联表相关信息
        List<HHardwareAttributeVal> itemsAttributeValList=new ArrayList<>();
        String uUid = uuid.getUUid();
        hardware.setHardware_id(uUid);
        hardware.setItems_id(items_id);
        hardware.setHardware_date(df.format(new Date()));
        for (int i=0;i < data.size();i++){
            //类型
            String attribute_name = data.get(i).getAttribute_name();
            //属性值
            String attributeQVal = data.get(i).getAttributeQVal();
            //单条属性值
            HAttributeVal attributeVal=new HAttributeVal();
            //单条项目和属性值关联表相关信息
            HHardwareAttributeVal hardwareAttributeVal=new HHardwareAttributeVal();
            //单条属性值的uuid
            String attribute_val_id = uuid.getUUid();
            //判断是否是name属性|| 如果是就将它添加到产品实体类中
            if ("名称".equals(attribute_name)|| "名称" == attribute_name){
                hardware.setHardware_name(attributeQVal);
            }
            String type=data.get(i).getAttribute_type();
            if( !"名称".equals(attribute_name) && "名称" != attribute_name && "00" != type && !"00".equals(type)){
                if("01" == type || "01".equals(type) ){
                    //添加单个属性值信息
                    attributeVal.setAttribute_id(data.get(i).getAttribute_id());
                    attributeVal.setAttribute_val(attributeQVal);
                    attributeVal.setAttribute_val_id(attribute_val_id);
                    attributeValList.add(attributeVal);
                    System.out.println();
                    hardwareAttributeVal.setAttribute_val_id(attribute_val_id);
                }else
                if("02" == type || "02".equals(type) ){
                    //添加单条项目属性关联信息
                    hardwareAttributeVal.setAttribute_val_id(attributeQVal);
                }
                hardwareAttributeVal.setHid(uuid.getUUid());
                hardwareAttributeVal.setHardware_id(uUid);
                hardwareAttributeVal.setAttribute_id(data.get(i).getAttribute_id());
                hardwareAttributeVal.setAttribute_val_type(data.get(i).getAttribute_type());
                itemsAttributeValList.add(hardwareAttributeVal);

            }

        }
        Integer insert = hardwareDao.insert(hardware);
        attributeValDao.setattributeVal(attributeValList);
        hardwareAttributeValDao.setItemsAttributeVal(itemsAttributeValList);
        return insert;
    }

    @Override
    @Transactional
    public int setCode(String items_id,String code_number,String code_type){
        IItems items = itemsDao.getOneItemsById(items_id);
        String items_Name = items.getItems_name();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String format = df.format(new Date());// new Date()为获取当前系统时间
        ZXingCode code=new ZXingCode();
        int parseInt = Integer.parseInt(code_number);
        System.out.println(parseInt);
        for (int i = 0; i < parseInt; i++) {
            String randomUUID = code.randomUUID();
            Code code2=new Code();
            code2.setCode_id(randomUUID);
            code2.setCode_name(items_Name + "-"+code_type+"--"+randomUUID);
            code2.setCode_date(format);
            code2.setCode_url("http://syy-vr.com:8084/hardware/"+items_Name + "-"+code_type+"--"+randomUUID+".png");
            code2.setCode_itemsId(items_id);
            code2.setCode_type(code_type);
            code2.setCode_states("1");
            code.getLogoQRCode(items_Name,"http://syy-hardware.com/html/code.html?id="+randomUUID, items_Name + "-"+code_type+"--"+randomUUID);
            codeDao.insert(code2);
        }
        return 0;
    }


    @Override
    public List< Map<Object, Object>> getCode(String items_id,String classify){
        List< Map<Object, Object>> listData = new ArrayList<>();
        List<HAttributeVal> list = hardwareDao.getclassify(classify);
        for (int i = 0; i < list.size(); i++) {
            Map<Object, Object> mapData=new HashMap<>();
            String ClassName=list.get(i).getAttribute_val();
            List<Code> listCode=codeDao.getCodeByClsaaAndItemsId(items_id,ClassName);
            mapData.put("codeType",ClassName);
            mapData.put("codeUrl",listCode);
            listData.add(mapData);
        }

        return listData;
    }

    @Override
    public List< Map<Object, Object>> getCodeByName(String queryString,String items_id,String classify){

        List<Code> list=codeDao.getCodeByName(queryString,items_id,classify);

        List< Map<Object, Object>> list1=new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map<Object, Object> map = new HashMap<>();
            map.put("value",list.get(i).getCode_id());
            list1.add(map);
        }
        return list1;
    }


    @Override
    public int hardwareUpdate(List<HAttribute> data,String items_id,String hardware_id){
        String codetype="";
        String codeid="";
        for (int i = 0; i <  data.size(); i++) {
            String attribute_code = data.get(i).getAttribute_code();
            if(attribute_code == "codeType" || "codeType".equals(attribute_code)){
                codetype = data.get(i).getAttributeQVal();
            }
            if(attribute_code == "codeId" || "codeId".equals(attribute_code)){
                codeid = data.get(i).getAttributeQVal();
            }
        }

        if("attribute_val_id_14".equals(codetype) || "attribute_val_id_14".equals(codetype)){
            if(codeid == null || "".equals(codeid)){
                return 2;
            }

            Code code = codeDao.selectOneById(codeid);
            if (code == null){
                return 2;
            }
            codeDao.updateOneById(codeid,"02");
        }

        for (int i=0;i < data.size();i++){
            HAttribute ha=data.get(i);
            String hardwareName=ha.getAttribute_name();
            String attribute_type=ha.getAttribute_type();
            String attribute_QVal=ha.getAttributeQVal();
            if("名称".equals(hardwareName)||"名称" == hardwareName){
                hardwareDao.updateNameById(attribute_QVal,hardware_id);
            }else {
                if("01".equals(attribute_type) || "01" == attribute_type ){
                    HHardwareAttributeVal hhavByHidAndAid = hardwareAttributeValDao.getHhavByHidAndAid(hardware_id, ha.getAttribute_id());
                    String attribute_val_id = hhavByHidAndAid.getAttribute_val_id();
                    attributeValDao.updateOneById(attribute_val_id,attribute_QVal);
                }else
                if("02".equals(attribute_type)||"02" == attribute_type){
                    if(attribute_QVal.length() >= 12){
                        hardwareAttributeValDao.updateAttributeValId(attribute_QVal,hardware_id,ha.getAttribute_id());
                    }
                }
            }


        }
        return 1;
    }

    @Override
    public Map<Object, Object> getHardwaresByIdAndName(String hardware_name,String items_id,String hardware_id){
        List<Map<Object, Object>> list=hardwareDao.getHardwaresByIdAndName(items_id,hardware_id,hardware_name);
        List<HAttribute> hAttributes = attributeDao.selectList(
                new EntityWrapper<HAttribute>()
        );
        Map<Object, Object> map1 = hardwareListToMap(list, hAttributes);

        return map1;
    }

    private Map<Object,Object> hardwareListToMap(List<Map<Object, Object>> list,List<HAttribute> hAttributes){
        //存储所有条数据
        Map<Object,Object> mapAll =new HashMap<>();
        //存储成一条条项目
        List list1 =new ArrayList();
        //存储单个项目
        Map<String,Object> itemMap =new HashMap<>();

        if (list.size() >= 1){
            String itemsName = new String(list.get(0).get("hardware_id").toString());
            for (int i=0;i < list.size();i++ ){
                String items_id_I = new String(list.get(i).get("hardware_id").toString());
                if (!itemsName.equals(items_id_I)){
                    itemsName = items_id_I;
                    Map<String,Object> itemI =new HashMap<>();
                    for(Iterator it = itemMap.keySet().iterator() ; it.hasNext();){
                        String key = it.next().toString();
                        itemI.put(key, itemMap.get(key));
                        System.out.println();
                    }
                    list1.add(itemI);
                    itemMap.clear();
                }
                itemMap.put("hardware_id",list.get(i).get("hardware_id"));
                itemMap.put("hardware_name",list.get(i).get("hardware_name"));
                itemMap.put(list.get(i).get("attribute_code").toString(),list.get(i).get("attribute_val"));

            }
        }
        list1.add(itemMap);

        mapAll.put("hardware",list1);

        for (int i=0;i < hAttributes.size();i++){
            String attribute_type = hAttributes.get(i).getAttribute_type();
            if("02".equals(attribute_type)|| "02" == attribute_type){
                List<HAttributeVal> list2 = attributeValDao.selectValById(hAttributes.get(i).getAttribute_id());
                hAttributes.get(i).setAttributeList(list2);
            }
        }
        HAttribute ha=new HAttribute();
        ha.setAttribute_name("名称");
        ha.setAttribute_code("hardware_name");
        ha.setAttribute_type("01");
        hAttributes.add(ha);
        mapAll.put("attribute",hAttributes);

        return mapAll;
    }

    @Override
    public List<HAttributeVal> getclassify(String classify){
        List<HAttributeVal> list = hardwareDao.getclassify(classify);
        return list;
    }

}
