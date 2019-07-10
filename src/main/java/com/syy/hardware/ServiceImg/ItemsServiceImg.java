package com.syy.hardware.ServiceImg;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.syy.hardware.Service.ItemsService;
import com.syy.hardware.dao.IAttributeDao;
import com.syy.hardware.dao.IAttributeValDao;
import com.syy.hardware.dao.IItemsAttributeValDao;
import com.syy.hardware.dao.IItemsDao;
import com.syy.hardware.entity.*;
import com.syy.hardware.util.Uuid;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
public class ItemsServiceImg implements ItemsService {


    @Autowired
    private IItemsDao itiemsDao;

    @Autowired
    private IItemsAttributeValDao itemsAttributeValDao;

    @Autowired
    private IAttributeDao attributeDao;

    @Autowired
    private IAttributeValDao attributeValDao;

    Uuid uuid=new Uuid();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    public static void main(String args[]) {
        System.out.println("Hello World!");
        Uuid uuid=new Uuid();
        for (int i=0;i < 3;i++){
            /*String attribute_name = data.get(i).getAttribute_name();

            if (attribute_name == ""){

            }*/
            String id = uuid.getUUid();
            System.out.printf(id);
            System.out.printf("------------");
        }
    }

    @Override
    public List<IItems> getItems(){
        List<IItems> nickname = this.itiemsDao.selectList(
                new EntityWrapper<IItems>().eq("items_id", "items01")
        );


        return nickname;
    }

    /**
     *  通过项目id查询单条数据
     * @param id
     * @return
     */
    @Override
    public  Map<Object, Object> getItemsById(String id){
        List<Map<Object, Object>> itemsById = itiemsDao.getItemsById(id);
        Map<Object, Object> map = duoZy(itemsById);
        return map;
    }

    /**
     * 多列转多行
     * @param list
     * @return
     */
    private Map<Object,Object> duoZy(List<Map<Object, Object>> list){

        Map<Object,Object> map =new HashMap<>();//存储单条数据
        map.put("itemsId",list.get(0).get("items_id"));
        map.put("itemsName",list.get(0).get("items_name"));
        for (int i=0;i < list.size();i++ ){
            map.put(list.get(i).get("attribute_name_val"),list.get(i).get("attribute_val"));
            map.put("type",list.get(i).get("attribute_val_type"));
            map.put("code",list.get(i).get("attribute_val_type"));
        }

        return map;
    }

    /**
     * 获取
     * @param page
     * @param items_branch
     * @return
     */
    @Override
    public Map<Object, Object> getItemsAll(Pagination page, String items_branch,String items_years){
        List<Map<Object, Object>> itemsAll = itiemsDao.getItemsAll( items_branch,items_years);
        List<IAttribute> iAttributes = attributeDao.selectList(
                new EntityWrapper<IAttribute>()
        );
        Map<Object, Object> map = ListToMap(itemsAll,iAttributes);
        return map;
    }

    /**
     * 多列转多行
     * @param list
     * @return
     */
    private Map<Object,Object> ListToMap(List<Map<Object, Object>> list,List<IAttribute> iAttributes){
        //存储所有条数据
        Map<Object,Object> mapAll =new HashMap<>();
        //存储成一条条项目
        List list1 =new ArrayList();
        //存储单个项目
        Map<String,Object> itemMap =new HashMap<>();

        if (list.size() >= 1){
            String itemsName = new String(list.get(0).get("items_id").toString());
            for (int i=0;i < list.size();i++ ){
                String items_id_I = new String(list.get(i).get("items_id").toString());
                if (!itemsName.equals(items_id_I)){
                    itemsName = items_id_I;
                    Map<String,Object> itemI =new HashMap<>();
                    for(Iterator it = itemMap.keySet().iterator() ; it.hasNext();){
                        String key = it.next().toString();
                        itemI.put(key, itemMap.get(key));
                    }
                    list1.add(itemI);
                    itemMap.clear();
                }
                itemMap.put("itemsId",list.get(i).get("items_id"));
                itemMap.put("itemsName",list.get(i).get("items_name"));
                itemMap.put("hardware",list.get(i).get("items_branch"));
                itemMap.put(list.get(i).get("attribute_name").toString(),list.get(i).get("attribute_val"));
            }
        }
        list1.add(itemMap);

        mapAll.put("items",list1);

        IAttribute attributeEntiy=new IAttribute();
        attributeEntiy.setAttribute_id("attribute_id_-01");
        attributeEntiy.setAttribute_name("itemsId");
        attributeEntiy.setAttribute_name_val("项目ID");
        attributeEntiy.setAttribute_type("00");
        iAttributes.add(attributeEntiy);
        IAttribute attributeEntiy1=new IAttribute();
        attributeEntiy1.setAttribute_id("attribute_id_-02");
        attributeEntiy1.setAttribute_name("itemsName");
        attributeEntiy1.setAttribute_name_val("项目名称");
        attributeEntiy1.setAttribute_type("01");
        iAttributes.add(attributeEntiy1);
        IAttribute attributeEntiy2=new IAttribute();
        attributeEntiy2.setAttribute_id("attribute_id_-03");
        attributeEntiy2.setAttribute_name("hardware");
        attributeEntiy2.setAttribute_name_val("所属技术部");
        attributeEntiy2.setAttribute_type("00");
        iAttributes.add(attributeEntiy2);
        for (int i=0;i < iAttributes.size();i++){
            String attribute_type = iAttributes.get(i).getAttribute_type();
            if("02".equals(attribute_type)|| "02" == attribute_type){
                List<IAttributeVal> list2 = attributeValDao.selectValById(iAttributes.get(i).getAttribute_id());
                System.out.printf(list2.toString());
                iAttributes.get(i).setAttributeList(list2);
            }
        }
        mapAll.put("attribute",iAttributes);

        return mapAll;
    }




    @Override
    public Map<Object, Object> getItemsByIdAndName(String items_id,String items_name,String items_branch,String items_years){
        HashMap map=new HashMap();
        map.put("items_id",items_id);
        map.put("items_name",items_name);
        map.put("items_branch",items_branch);
        map.put("items_years",items_years);
        List<Map<Object, Object>> itemsByIdAndName = itiemsDao.getItemsByIdAndName(map);
        List<IAttribute> iAttributes = attributeDao.selectList(
                new EntityWrapper<IAttribute>()
        );
        Map<Object, Object> mapData = ListToMap(itemsByIdAndName,iAttributes);
        return mapData;
    }
    @Override
    public List<IItems> getItemsVal(IItems iItems){
        List<IItems> itemsVal = itiemsDao.getItemsVal(iItems);
        return itemsVal;
    }

    @Transactional
    @Override
    public int setItems(List<IAttribute> data,String items_branch,String items_years){
        //用来存储项目表相关信息
        IItems items=new IItems();
        //用来存储属性值表相关信息
        List<IAttributeVal> attributeValList=new ArrayList<>();
        //用来项目和属性值关联表相关信息
        List<IItemsAttributeVal> itemsAttributeValList=new ArrayList<>();
        String items_id = uuid.getUUid();
        items.setItems_id(items_id);
        items.setItems_branch(items_branch);
        items.setItems_years(items_years);
        items.setItems_date(df.format(new Date()));
        for (int i=0;i < data.size();i++){
            //类型
            String attribute_name = data.get(i).getAttribute_name();
            //属性值
            String attributeQVal = data.get(i).getAttributeQVal();
            //单条属性值
            IAttributeVal attributeVal=new IAttributeVal();
            //单条项目和属性值关联表相关信息
            IItemsAttributeVal itemsAttributeVal=new IItemsAttributeVal();
            //单条属性值的uuid
            String attribute_val_id = uuid.getUUid();
            if ("itemsName".equals(attribute_name)|| "itemsName" == attribute_name){
                items.setItems_name(attributeQVal);
            }

            String type=data.get(i).getAttribute_type();
            if( !"itemsName".equals(attribute_name) && "itemsName" != attribute_name && "00" != type && !"00".equals(type)){
                if("01" == type || "01".equals(type) ){
                    //添加单个属性值信息
                    attributeVal.setAttribute_id(data.get(i).getAttribute_id());
                    attributeVal.setAttribute_val(attributeQVal);
                    attributeVal.setAttribute_val_id(attribute_val_id);
                    attributeValList.add(attributeVal);
                    itemsAttributeVal.setAttribute_val_id(attribute_val_id);
                }else
                if("02" == type || "02".equals(type)){
                    itemsAttributeVal.setAttribute_val_id(attributeQVal);
                }

                //添加单条项目属性关联信息
                itemsAttributeVal.setIid(uuid.getUUid());
                itemsAttributeVal.setItems_id(items_id);
                itemsAttributeVal.setAttribute_id(data.get(i).getAttribute_id());

                itemsAttributeVal.setAttribute_val_type(data.get(i).getAttribute_type());
                itemsAttributeValList.add(itemsAttributeVal);
            }

        }
        Integer insert = itiemsDao.insert(items);
        attributeValDao.setattributeVal(attributeValList);
        itemsAttributeValDao.setItemsAttributeVal(itemsAttributeValList);
        return insert;
    }

    @Transactional
    @Override
    public int itemsAttributeAdd(IAttribute data1, JSONArray jsonArray){

        String attributeId=uuid.getUUid();
        if(data1.getAttribute_type() == "02" || "02".equals(data1.getAttribute_type())){
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object=(JSONObject)jsonArray.get(i);
                String attribute_val = (String)object.get("attribute_val");
                IAttributeVal attributeVal=new IAttributeVal();
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
    public List<IAttribute> getItemsAttribute(){

        List<IAttribute> list=attributeDao.selectList(
                new EntityWrapper<IAttribute>()
        );

        for (int i = 0; i < list.size(); i++) {
            IAttribute hAttribute = list.get(i);
            if (hAttribute.getAttribute_type() == "02" || "02".equals(hAttribute.getAttribute_type())){
                List<IAttributeVal> hAttributeValList= attributeValDao.selectValById(hAttribute.getAttribute_id());
                hAttribute.setAttributeList(hAttributeValList);
            }
        }
        return list;
    }

    @Override
    public int itemAttributeDelete(String attribute_id){
        int i =1;
        Integer integer = attributeDao.deleteByAttributeId(attribute_id);

        Integer integer1 =itemsAttributeValDao.deleteByAttributeId(attribute_id);
        return integer;
    }

    @Override
    @Transactional
    public int itemsDelete(String items_id){
        Integer integer = itiemsDao.deleteByItemsId(items_id);
        return integer;
    }

    @Override
    @Transactional
    public int  itemAttributeUpdate(IAttribute data1){
        String attribute_type = data1.getAttribute_type();


        //Integer integer = attributeDao.updateOne(data1);

        return 0;
    }

    @Override
    @Transactional
    public int itemsUpdate(List<IAttribute> data,String items_id){
        String codetype="";
        String codeid="";
        for (int i = 0; i < data.size(); i++) {

            IAttribute ha=data.get(i);
            //代号
            String hardwareName=ha.getAttribute_name();
            //类型
            String attribute_type=ha.getAttribute_type();
            //输入值
            String attribute_QVal=ha.getAttributeQVal();

            if("名称".equals(hardwareName)||"名称" == hardwareName){
                itiemsDao.updateNameById(attribute_QVal,items_id);
            }else{

                if("01".equals(attribute_type) || "01" == attribute_type ){

                }

            }
        }

        return 0;
    }

}
