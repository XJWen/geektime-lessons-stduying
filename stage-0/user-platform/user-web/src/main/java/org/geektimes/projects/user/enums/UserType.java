package org.geektimes.projects.user.enums;

/***
 * 枚举不能充当实体类
 * 枚举底层实际 public final class UserType extends java.lang.Enum
 * */
public enum UserType {
    NORMAL,
    VIP;

    // 枚举中构造器是 private
    UserType(){

    }

    public static void main(String[] args) {
        UserType.VIP.ordinal();
    }
}
