package com.weitheshinobi.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class LazyMan {

    private static boolean flag = false;

    private LazyMan(){

        synchronized (LazyMan.class){
            if (flag == false){
                flag = true;
            }else {
                throw new RuntimeException("不要反射");
            }
        }
    }

    private volatile static LazyMan lazyMan;

    public static LazyMan getInstance(){
        if(lazyMan == null){
            synchronized (LazyMan.class){
                if(lazyMan == null){
//                    不是一個原子性操作
                    lazyMan = new LazyMan();
                }
            }
        }
        return lazyMan;
    }

    public static void main(String[] args) throws Exception {
//        LazyMan instance = LazyMan.getInstance();


        Field flag = LazyMan.class.getDeclaredField("flag");
        flag.setAccessible(true);

        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan instance = declaredConstructor.newInstance();

        flag.set(instance,false);
        LazyMan instance1 = declaredConstructor.newInstance();


        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());

//        透過反射，成功取得兩個不同的hashcode
    }

}
