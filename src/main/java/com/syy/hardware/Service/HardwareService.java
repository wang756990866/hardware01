package com.syy.hardware.Service;


import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.HAttributeClassify;
import com.syy.hardware.entity.HAttributeVal;
import com.syy.hardware.entity.IAttribute;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface HardwareService {

    Map<Object, Object> getHardwaresByItemsId(String items_id);

    int setHardware(String items_id, List<HAttributeClassify> data,String classify_id);

    int setCode(String items_id,String code_number,String code_type);

    List<HAttributeVal> getclassify(String classify);

    List< Map<Object, Object>> getCode(String items_id,String classify);

    List< Map<Object, Object>> getCodeByName(String queryString,String items_id,String classify);

    int hardwareUpdate(List<HAttributeClassify> data1,String items_id,String hardware_id,String classify);

    Map<Object, Object> getHardwaresByIdAndName(String hardware_classify,String hardware_codeType,String hardwareName,String items_id,String hardware_id);

    int hardwareAttributeAdd(HAttribute data1, JSONArray jsonArray);

    List<HAttributeClassify> getHardwareAttribute();

    Map<Object, Object> getHardwareByCodeId(String code_id);

    int hardwareAttributeDelete(String attribute_id);

    int hardwareDelete(String hardware_id);

    int hardwareAttributeClassifyAdd(HAttributeClassify hAttributeClassify);

    int hardwareAttributeClassifyDelete(String classify_id);
}
