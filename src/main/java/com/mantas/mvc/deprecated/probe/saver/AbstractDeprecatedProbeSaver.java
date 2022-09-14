package com.mantas.mvc.deprecated.probe.saver;

import com.mantas.mvc.deprecated.probe.DeprecatedProbe;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.LocalDateTime;

public abstract class AbstractDeprecatedProbeSaver implements DeprecatedProbeSaver {

    protected abstract void doSave(DeprecatedMethodData data);

    @Override
    public void save(DeprecatedMethodData data) {
        doSave(data);
    }

    @Override
    public void save(MethodSignature signature) {
        DeprecatedMethodData data = new DeprecatedMethodData();
        data.setTypeName(signature.getDeclaringTypeName());
        data.setMethodName(signature.getMethod().getName());
        data.setActionTime(LocalDateTime.now().toString());

        DeprecatedProbe deprecatedProbe = signature.getMethod().getAnnotation(DeprecatedProbe.class);
        data.setValue(deprecatedProbe.value());
        data.setNamespace(deprecatedProbe.ns());
        data.setVersion(deprecatedProbe.version());

        doSave(data);
    }
}
