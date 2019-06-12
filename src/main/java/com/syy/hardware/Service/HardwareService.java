package com.syy.hardware.Service;


import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.HAttributeVal;

import java.util.List;
import java.util.Map;

public interface HardwareService {

    Map<Object, Object> getHardwaresByItemsId(String items_id);

    int setHardware(String items_id, List<HAttribute> data);

    int setCode(String items_id,String code_number,String code_type);

    List<HAttributeVal> getclassify(String classify);

    List< Map<Object, Object>> getCode(String items_id,String classify);

    List< Map<Object, Object>> getCodeByName(String queryString,String items_id,String classify);

    int hardwareUpdate(List<HAttribute> data1,String items_id,String hardware_id);

    Map<Object, Object> getHardwaresByIdAndName(String hardwareName,String items_id,String hardware_id);
}
