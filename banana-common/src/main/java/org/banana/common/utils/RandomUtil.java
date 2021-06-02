package org.banana.common.utils;

import java.util.UUID;

/**
 * Desc: RandomUtil
 * Created by mskj-mohao on 2021/6/2 2:34 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class RandomUtil {

    public static String getUUid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
