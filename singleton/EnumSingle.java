package com.weitheshinobi.single;

import java.lang.reflect.Constructor;

public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }

}


class test {
    public static void main(String[] args) throws Exception {
        EnumSingle instance1 = EnumSingle.INSTANCE;

//        枚舉class透過反編譯，發現他的建構子是(string,int)
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);

        declaredConstructor.setAccessible(true);
        EnumSingle instance2 = declaredConstructor.newInstance();
        System.out.println(instance1);
        System.out.println(instance2);

//        執行結果：Exception in thread "main" java.lang.IllegalArgumentException: Cannot reflectively create enum objects
    }
}