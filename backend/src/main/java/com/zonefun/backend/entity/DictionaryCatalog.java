package com.zonefun.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典目录表
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-21
 */
@TableName("dictionary_catalog")
@ApiModel(value = "DictionaryCatalog对象", description = "字典目录表")
public class DictionaryCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("字典编号")
    private String dictNo;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典英文名称")
    private String dictEnglishName;

    @ApiModelProperty("备注")
    private String description;

    @ApiModelProperty("创建日期")
    private LocalDateTime createTime;

    @ApiModelProperty("更新日期")
    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getDictNo() {
        return dictNo;
    }

    public void setDictNo(String dictNo) {
        this.dictNo = dictNo;
    }
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
    public String getDictEnglishName() {
        return dictEnglishName;
    }

    public void setDictEnglishName(String dictEnglishName) {
        this.dictEnglishName = dictEnglishName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DictionaryCatalog{" +
            "id=" + id +
            ", dictNo=" + dictNo +
            ", dictName=" + dictName +
            ", dictEnglishName=" + dictEnglishName +
            ", description=" + description +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
