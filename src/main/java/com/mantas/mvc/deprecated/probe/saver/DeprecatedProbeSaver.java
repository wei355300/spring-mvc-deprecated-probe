package com.mantas.mvc.deprecated.probe.saver;

import org.aspectj.lang.reflect.MethodSignature;

public interface DeprecatedProbeSaver {

    /**
     * 保存数据
     *
     * 仅存储最新一条数据
     * @param data
     */
    void save(DeprecatedMethodData data);

    void save(MethodSignature signature);
}
