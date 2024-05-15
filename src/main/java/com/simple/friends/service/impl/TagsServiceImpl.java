package com.simple.friends.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.friends.mapper.TagsMapper;
import com.simple.friends.model.domain.Tags;
import com.simple.friends.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
* @author hgy
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2024-05-15 10:31:46
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;


}




