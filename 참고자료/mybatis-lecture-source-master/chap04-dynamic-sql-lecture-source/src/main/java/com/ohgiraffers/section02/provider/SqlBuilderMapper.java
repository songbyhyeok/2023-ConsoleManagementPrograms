package com.ohgiraffers.section02.provider;

import com.ohgiraffers.common.MenuDTO;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

public interface SqlBuilderMapper {
    @InsertProvider(type=SqlBuilderProvider.class, method="registMenu")
    int registMenu(MenuDTO menu);

    @UpdateProvider(type=SqlBuilderProvider.class, method="modifyMenu")
    int modifyMenu(MenuDTO menu);

    /* Map이나 getter가 있는 DTO가 아니라 기본 자료형 값을 전달해야 하는 경우 @Param 어노테이션을 이용한다.
    * 전달해야 하는 값이 2개 이상인 경우도 @Param 어노테이션의 key 값으로 값을 찾을 수 있다.
    * 단, Provider의 메소드의 매개변수 선언부는 없어야 한다. */
    @DeleteProvider(type=SqlBuilderProvider.class, method="deleteMenu")
    int deleteMenu(@Param("code") int code);

}
