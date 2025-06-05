package com.ajoufinder.be.alarm.domain.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlarmTarget {
    AlarmType value();  // 어떤 상황인지 명시
}

