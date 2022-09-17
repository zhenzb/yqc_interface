/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PublicPlatformAnnotation
 * Author:   Mr.Chen
 * Date:     2019/2/22 15:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.handongkeji.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Mr.Chen
 * @create 2019/2/22
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
//当前注解如何去保持
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicPlatformAnnotation {
}
