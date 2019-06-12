package com.syy.hardware.Service;


import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.syy.hardware.entity.IAttribute;
import com.syy.hardware.entity.IItems;

import java.util.List;
import java.util.Map;

public interface ItemsService {

    List<IItems>  getItems();
    Map<Object, Object> getItemsById(String id);
    Map<Object, Object> getItemsAll(Pagination page, String items_branch,String items_years);

    Map<Object, Object> getItemsByIdAndName(String items_id,String items_name,String items_branch,String items_years);

    List<IItems> getItemsVal(IItems iItems);
    int setItems(List<IAttribute> data,String items_branch,String items_years);
}
