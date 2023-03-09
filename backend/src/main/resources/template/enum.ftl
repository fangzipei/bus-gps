package ${package_name}.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ${enum_class_name}
 * @Description ${enum_description}
 * @Date ${date}
 * @Author ${author}
 */
@Slf4j
public enum ${enum_class_name} {

<#if enum_list?exists>
    <#list enum_list as enum>
    ${enum.enumItemName}("${enum.enumItemValue}", "${enum.enumItemDesc}")<#if enum_index == ((enum_list?size) - 1)>;<#else >,</#if>
    </#list>
</#if>

    private String value;
    private String description;

    ${enum_class_name}(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // 记录value-Enum的Map 在后续操作时可降低时间复杂度
    private static Map<String, ${enum_class_name}> valueMap = new HashMap<>();

    static {
        // 在静态代码块中初始化Map
        for (${enum_class_name} e : values()) {
            valueMap.put(e.getValue(), e);
        }
    }

    /**
     * 通过value获取Enum
     *
     * @return ${enum_class_name}
     * @date ${date}
     * @author ${author}
     */
    public static ${enum_class_name} getEnum(String value) {
        if (value == null || !valueMap.containsKey(value)) {
            log.error("{}中不存在{}！", "${enum_class_name}", value);
            return null;
        }
        return valueMap.get(value);
    }

    /**
     * 通过value获取对应的description
     *
     * @return ${enum_class_name}
     * @date ${date}
     * @author ${author}
     */
    public static String getDescription(String value) {
        if (value == null || !valueMap.containsKey(value)) {
            log.error("{}中不存在{}！", "${enum_class_name}", value);
            return null;
        }
        return valueMap.get(value).getDescription();
    }

}

