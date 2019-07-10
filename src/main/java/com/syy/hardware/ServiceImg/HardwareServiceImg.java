package com.syy.hardware.ServiceImg;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.syy.hardware.Service.HardwareService;
import com.syy.hardware.dao.*;
import com.syy.hardware.entity.*;
import com.syy.hardware.util.ChangeToPinYin;
import com.syy.hardware.util.Uuid;
import com.syy.hardware.util.ZXingCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private HAttributeClassifyDao attributeClassifyDao;

    @Autowired
    private HAttributeValDao attributeValDao;

    @Autowired
    private IItemsDao itemsDao;

    @Autowired
    private CodeDao codeDao;

    ChangeToPinYin changeToPinYin = new ChangeToPinYin();

    Uuid uuid=new Uuid();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @Override
    public Map<Object, Object> getHardwaresByItemsId(String items_id){

        List<Map<Object, Object>> list=hardwareDao.getHardwaresByItemsId(items_id);

        Map<Object, Object> map1 = hardwareListToMap(list);

        return map1;
    }


    @Transactional
    @Override
    public int setHardware(String items_id, List<HAttributeClassify> data1,String classify_name){

        String codetype="";
        String codeid="";
        for (int x = 0; x < data1.size(); x++) {
            List<HAttribute> data = data1.get(x).getAttributeList();
            for (int i = 0; i <  data.size(); i++) {
                String attribute_code = data.get(i).getAttribute_code();
                if(attribute_code == "codeId" || "codeId".equals(attribute_code)){
                    codeid = data.get(i).getAttributeQVal();
                }
            }
            if(codeid != null || codeid != ""){
                if(codeid == null || "".equals(codeid)){
                    return 0;
                }

                Code code = codeDao.selectOneById(codeid);
                if (code == null){
                    return 0;
                }
                codeDao.updateOneById(codeid,"2");
            }
        }


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
        hardware.setCode_type("1");
        HAttributeClassify hAttributeClassify=new HAttributeClassify();
        hAttributeClassify.setClassify_name(classify_name);
        HAttributeClassify hAttributeClassify1 = attributeClassifyDao.selectOne(hAttributeClassify);
        String classify_id = hAttributeClassify1.getClassify_id();
        hardware.setClassify_id(classify_id);
        for (int x = 0; x < data1.size(); x++) {
            List<HAttribute> data = data1.get(x).getAttributeList();
            String classify_name1 = data1.get(x).getClassify_name();
            if (classify_name.equals(classify_name1) || classify_name1.equals("通用")){

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
            }
        }
        Integer insert = hardwareDao.insertHardware(hardware);
        attributeValDao.setattributeVal(attributeValList);
        //hardwareAttributeValDao.setItemsAttributeVal(itemsAttributeValList);
        Integer insert1 = 0 ;
        for (HHardwareAttributeVal val:itemsAttributeValList) {
            insert1 = hardwareAttributeValDao.insert(val);
        }
        return 1;
       /* return insert1;*/
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
        String code_states = "1";
        List<Code> list=codeDao.getCodeByName(queryString,items_id,classify,code_states);

        List< Map<Object, Object>> list1=new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map<Object, Object> map = new HashMap<>();
            map.put("value",list.get(i).getCode_id());
            list1.add(map);
        }
        return list1;
    }


    @Override
    @Transactional
    public int hardwareUpdate(List<HAttributeClassify> data1,String items_id,String hardware_id,String classify){
        String codeid="";
        int returnInt = 1;
        for (int x = 0; x < data1.size(); x++) {
            List<HAttribute> data = data1.get(x).getAttributeList();

            for (int i = 0; i <  data.size(); i++) {
                String attribute_code = data.get(i).getAttribute_code();
                if(attribute_code == "codeId" || "codeId".equals(attribute_code)){
                    codeid = data.get(i).getAttributeQVal();
                    System.out.println();
                }
            }
            if(codeid != null || codeid != ""){
                if(codeid == null || "".equals(codeid)){
                    System.out.println();
                    return 2;
                }

                Code code = codeDao.selectOneById(codeid);
                String code_states = code.getCode_states();
                if (code == null){
                    return 2;
                }else
                if(!"2".equals(code_states)){
                    codeDao.updateOneById(codeid,"2");
                }

            }
        }

        for (int x = 0; x < data1.size(); x++) {
            List<HAttribute> data = data1.get(x).getAttributeList();
            String classify_name = data1.get(x).getClassify_name();
            if(classify_name.equals(classify) || "通用".equals(classify_name)){
                for (int i=0;i < data.size();i++){
                    HAttribute ha=data.get(i);
                    String hardwareName=ha.getAttribute_name();
                    String attribute_type=ha.getAttribute_type();
                    String attribute_QVal=ha.getAttributeQVal();
                    if("名称".equals(hardwareName)||"名称" == hardwareName){
                        hardwareDao.updateNameById(attribute_QVal,hardware_id);
                    }else {
                        HHardwareAttributeVal hhavByHidAndAid = hardwareAttributeValDao.getHhavByHidAndAid(hardware_id, ha.getAttribute_id());

                        if(hhavByHidAndAid == null || "".equals(hhavByHidAndAid)){
                            HHardwareAttributeVal hardwareAttributeVal=new HHardwareAttributeVal();
                            String hardwareAttributeValId = uuid.getUUid();
                            hardwareAttributeVal.setHid(hardwareAttributeValId);
                            hardwareAttributeVal.setHardware_id(hardware_id);
                            hardwareAttributeVal.setAttribute_id(ha.getAttribute_id());
                            hardwareAttributeVal.setAttribute_val_type(attribute_type);
                            String hAttributeValID = uuid.getUUid();
                            if("01".equals(attribute_type) || "01" == attribute_type ){
                                HAttributeVal hAttributeVal=new HAttributeVal();
                                hAttributeVal.setAttribute_val_id(hAttributeValID);
                                hAttributeVal.setAttribute_val(attribute_QVal);
                                hAttributeVal.setAttribute_id(hardwareAttributeValId);
                                attributeValDao.insert(hAttributeVal);
                                hardwareAttributeVal.setAttribute_val_id(hAttributeValID);
                            }else{
                                hardwareAttributeVal.setAttribute_val_id(attribute_QVal);
                            }
                            hardwareAttributeValDao.insert(hardwareAttributeVal);
                        }else {
                            String attribute_val_id = hhavByHidAndAid.getAttribute_val_id();
                            if("01".equals(attribute_type) || "01" == attribute_type ){
                                attributeValDao.updateOneById(attribute_val_id,attribute_QVal);
                            }else
                            if("02".equals(attribute_type)||"02" == attribute_type){
                                if(attribute_QVal.length() >= 12){
                                    hardwareAttributeValDao.updateAttributeValId(attribute_QVal,hardware_id,ha.getAttribute_id());
                                }
                            }
                        }

                    }
                }
            }


        }

        return returnInt;
    }

    @Override
    public Map<Object, Object> getHardwaresByIdAndName(
            String hardware_classify,String hardware_codeType,String hardware_name,String items_id,String hardware_id){
        if("null".equals(hardware_classify)){
            hardware_classify = "";
        }
        if("null".equals(hardware_codeType)){
            hardware_codeType = "";
        }
        List<Map<Object, Object>> list = new ArrayList<>();
        if("".equals(hardware_classify) &&  "".equals(hardware_codeType)  ){
            List<Map<Object, Object>> hardwaresByIdAndName=hardwareDao.getHardwaresByIdAndName(
                    "","",items_id,hardware_id,hardware_name,"");
            list.addAll(hardwaresByIdAndName);
        }else {
            List<Map<Object, Object>> list1 = new ArrayList<>();
            List<Map<Object, Object>> hardwaresByIdAndName1 = hardwareDao.getHardwaresByIdAndName(
                    hardware_classify, hardware_codeType, items_id, hardware_id, hardware_name, "");
            list1.addAll(hardwaresByIdAndName1);

            for (int i = 0; i < list1.size(); i++) {
                Map<Object, Object> map = list1.get(i);
                String hardware_id1 = (String)map.get("hardware_id");
                List<Map<Object, Object>> hardwaresByIdAndName = hardwareDao.getHardwaresByIdAndName(
                        "", "", items_id, hardware_id1, hardware_name, "");
                list.addAll(hardwaresByIdAndName);
            }
        }


        Map<Object, Object> map1 = hardwareListToMap(list);

        return map1;
    }

    @Transactional
    @Override
    public int hardwareAttributeAdd(HAttribute data1, JSONArray jsonArray){

        String attributeId=uuid.getUUid();
        String attribute_name = data1.getAttribute_name();
        String stringCode = changeToPinYin.getStringPinYin(attribute_name);
        data1.setAttribute_code(stringCode);
        if(data1.getAttribute_type() == "02" || "02".equals(data1.getAttribute_type())){

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object=(JSONObject)jsonArray.get(i);
                String attribute_val = (String)object.get("attribute_val");
                HAttributeVal attributeVal=new HAttributeVal();

                attributeVal.setAttribute_val_id(uuid.getUUid());
                attributeVal.setAttribute_val(attribute_val);
                attributeVal.setAttribute_id(attributeId);
                attributeValDao.insert(attributeVal);
            }
        }
        data1.setAttribute_id(attributeId);
        Integer insert = attributeDao.insert(data1);
        return insert;
    }

    @Override
    public List<HAttributeClassify>  getHardwareAttribute(){

        /*List<HAttribute> list=attributeDao.selectList(
                new EntityWrapper<HAttribute>()
        );
        for (int i = 0; i < list.size(); i++) {
            HAttribute hAttribute = list.get(i);
            if (hAttribute.getAttribute_type() == "02" || "02".equals(hAttribute.getAttribute_type())){
                List<HAttributeVal> hAttributeValList= attributeValDao.selectValById(hAttribute.getAttribute_id());
                hAttribute.setAttributeList(hAttributeValList);
            }
        }*/
        //获取有多少属性
        List<HAttribute> hAttributes = attributeDao.selectList(
                new EntityWrapper<HAttribute>()
        );
        //获取有多少分类
        List<HAttributeClassify> hAttributeClassifies = attributeClassifyDao.selectList(
                new EntityWrapper<HAttributeClassify>()
        );
        //创建属性用来判断是否已添加名称属性
        int y = 0;
        //循环所有的分类
        for (int x = 0; x < hAttributeClassifies.size(); x++) {
            //获取该分类ID
            String classify_id = hAttributeClassifies.get(x).getClassify_id();

            List<HAttribute> attributeList2 = hAttributeClassifies.get(x).getAttributeList();
            if (null == attributeList2 || attributeList2.size() < 1){

                List<HAttribute> attributeList1 = new ArrayList<>();
                hAttributeClassifies.get(x).setAttributeList(attributeList1);

            }

            if(classify_id.equals("classify_00")  && y == 0){
                y++;
                HAttribute ha=new HAttribute();
                ha.setAttribute_name("名称");
                ha.setAttribute_code("hardware_name");
                ha.setAttribute_type("01");
                ha.setClassify_id("classify_00");
                ha.setAttribute_id("attribute_00");
                hAttributeClassifies.get(x).getAttributeList().add(ha);
            }
            //循环所有属性
            for (int i=0;i < hAttributes.size();i++){
                //获取该条属性的分类ID
                String classify_id1 = hAttributes.get(i).getClassify_id();

                //判断该属性是否是该分类的
                if (classify_id.equals(classify_id1)){
                    //获取该费类的状态（是单选还是多选）
                    String attribute_type = hAttributes.get(i).getAttribute_type();
                    if("02".equals(attribute_type)|| "02" == attribute_type){
                        //获取该属性多选的值
                        List<HAttributeVal> list2 = attributeValDao.selectValById(hAttributes.get(i).getAttribute_id());
                        //将该条属性的 各个属性值填充到该属性
                        hAttributes.get(i).setAttributeList(list2);
                    }
                    //获取该条属性
                    HAttribute hAttribute = hAttributes.get(i);
                    //获取当前分类的属性列表
                    List<HAttribute> attributeList = hAttributeClassifies.get(x).getAttributeList();
                    //判断是否存在属性列表没有则创建
                    if(attributeList == null || attributeList.size() < 1){
                        //创建属性列表
                        List<HAttribute> attributeList1 = new ArrayList<>();
                        //填充属性
                        attributeList1.add(hAttribute);
                        //添加属性列表至分类
                        hAttributeClassifies.get(x).setAttributeList(attributeList1);
                    }else {
                        //将该条属性填充到属性列表
                        attributeList.add(hAttribute);
                    }
                }
            }

        }
        return hAttributeClassifies;
    }

    @Override
    public Map<Object, Object> getHardwareByCodeId(String code_id){
        String hardware_id="";
        Code code = codeDao.selectOneById(code_id);
        System.out.printf(code.toString());
        List<Map<Object, Object>> list=hardwareDao.getHardwaresByIdAndName("","","","","",code_id);
        for (int i = 0; i < list.size(); i++) {
            Map<Object, Object> map =list.get(i);
            hardware_id = map.get("hardware_id").toString();
        }
        List<Map<Object, Object>> hardwaresByIdAndName = hardwareDao.getHardwaresByIdAndName("","","", hardware_id, "", "");
        Map<Object, Object> map1 = hardwareListToMap(hardwaresByIdAndName);
        return map1;
    }
    @Override
    public int hardwareAttributeDelete(String attribute_id){

        int i =attributeDao.deleteByAttributeId(attribute_id);
        int i1 = hardwareAttributeValDao.deleteByHardwareId(attribute_id);
        return i;
    }

    @Override
    public int hardwareDelete(String hardware_id){

        int i = hardwareDao.deleteByHardwareId(hardware_id);

        return i;
    }

    private Map<Object,Object> hardwareListToMap(List<Map<Object, Object>> list){

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
                itemMap.put("classify_name",list.get(i).get("classify_name"));
                itemMap.put("attribute_val_id",list.get(i).get("attribute_val_id"));

            }
        }
        list1.add(itemMap);

        mapAll.put("hardware",list1);

        List<HAttributeClassify> hardwareAttribute = getHardwareAttribute();


        mapAll.put("attribute",hardwareAttribute);

        return mapAll;
    }

    @Override
    public List<HAttributeVal> getclassify(String classify){
        List<HAttributeVal> list = hardwareDao.getclassify(classify);
        return list;
    }

    @Override
    public int hardwareAttributeClassifyAdd(HAttributeClassify hAttributeClassify){
        String classify_name = hAttributeClassify.getClassify_name();
        String stringPinYin = changeToPinYin.getStringPinYin(classify_name);
        ArrayList<Map> maps = attributeClassifyDao.selectIntByCode(stringPinYin);
        let: for (int i = 0; i < 100; i++) {
            if (maps == null || maps.size() < 1 ){
                hAttributeClassify.setClassify_code(stringPinYin);
                break let;
            }
            stringPinYin = stringPinYin + i;
            maps = attributeClassifyDao.selectIntByCode(stringPinYin);
        }
        hAttributeClassify.setClassify_id(uuid.getUUid());
        Integer insert = attributeClassifyDao.insert(hAttributeClassify);
        return insert;
    }

    @Override
    public int hardwareAttributeClassifyDelete(String classify_id){
        List<HAttribute> hAttributes = attributeDao.seletByClassifyId(classify_id);

        for (HAttribute hAttribute : hAttributes) {
            String attribute_id = hAttribute.getAttribute_id();
            hardwareAttributeValDao.deleteByHardwareId(attribute_id);
            attributeDao.deleteByAttributeId(attribute_id);
        }
        Integer integer = attributeClassifyDao.deleteByClassify_id(classify_id);
        return integer;
    }
}
