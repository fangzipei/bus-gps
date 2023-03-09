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
 * 字典项表
 * </p>
 *
 * @author ZoneFun
 * @since 2022-11-21
 */
@TableName("dictionary_item")
@ApiModel(value = "DictionaryItem对象", description = "字典项表")
public class DictionaryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("字典编号")
    private String dictNo;

    @ApiModelProperty("字典项值")
    private String itemValue;

    @ApiModelProperty("字典项描述")
    private String itemDescription;

    @ApiModelProperty("字典项英文名")
    private String itemEnglishName;

    @ApiModelProperty("备注")
    private String remark;

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
    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public String getItemEnglishName() {
        return itemEnglishName;
    }

    public void setItemEnglishName(String itemEnglishName) {
        this.itemEnglishName = itemEnglishName;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "DictionaryItem{" +
            "id=" + id +
            ", dictNo=" + dictNo +
            ", itemValue=" + itemValue +
            ", itemDescription=" + itemDescription +
            ", itemEnglishName=" + itemEnglishName +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
