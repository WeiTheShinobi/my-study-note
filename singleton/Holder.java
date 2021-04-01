package com.weitheshinobi.single;

public class Holder {

    private Holder(){

    }

    public static Holder getInstance(){
        return InnerClass.HOLDER;
    }

    private static class InnerClass {
        private static final Holder HOLDER = new Holder();
    }
}
