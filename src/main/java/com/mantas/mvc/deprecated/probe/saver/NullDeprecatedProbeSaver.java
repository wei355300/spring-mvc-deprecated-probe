package com.mantas.mvc.deprecated.probe.saver;

import lombok.extern.slf4j.Slf4j;

/**
 * Log打印数据
 */
@Slf4j
public class NullDeprecatedProbeSaver extends AbstractDeprecatedProbeSaver {

    @Override
    public void doSave(DeprecatedMethodData data) {
        log.info("deprecated method data: ".concat(data.toString()));
    }
}
