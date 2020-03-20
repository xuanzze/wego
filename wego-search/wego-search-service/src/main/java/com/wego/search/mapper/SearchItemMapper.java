package com.wego.search.mapper;

import com.wego.common.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {
    public List<SearchItem> getSearchItemList();

    //根据商品的id查询商品的数据
    public SearchItem getSearchItemById(Long itemId);
}
