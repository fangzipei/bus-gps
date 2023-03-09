package com.zonefun.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zonefun.backend.entity.DictionaryItem;
import com.zonefun.backend.mapper.DictionaryItemMapper;
import com.zonefun.backend.service.IDictionaryItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典项表 服务实现类
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-21
 */
@Service
public class DictionaryItemServiceImpl extends ServiceImpl<DictionaryItemMapper, DictionaryItem> implements IDictionaryItemService {

}
