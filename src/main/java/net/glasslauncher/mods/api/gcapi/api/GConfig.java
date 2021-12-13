package net.glasslauncher.mods.api.gcapi.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GConfig {

    String value() default "config";

    String visibleName();
}
