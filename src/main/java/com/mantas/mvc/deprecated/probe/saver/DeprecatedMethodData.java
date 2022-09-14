package com.mantas.mvc.deprecated.probe.saver;

import lombok.Data;

import java.io.Serializable;

/**
 * 标记为 @Deprecated 的method的数据,
 *
 * 包括: class类型, method名称
 */
@Data
public class DeprecatedMethodData implements Serializable {

    private static final long serialVersionUID = 6863143762224366053L;

    private String value;
    private String namespace;
    private String version;
    private String typeName;
    private String methodName;
    private String actionTime;

    @Override
    public String toString() {
        return "{" +
                "value='" + value + '\'' +
                ", namespace='" + namespace + '\'' +
                ", version='" + version + '\'' +
                ", typeName='" + typeName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", actionTime='" + actionTime + '\'' +
                '}';
    }
}
