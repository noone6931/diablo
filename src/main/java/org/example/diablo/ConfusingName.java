package org.example.diablo;

public class ConfusingName {
    protected int companyId;
    protected String address;

//    public class OrderFactory
//
//    ;
//
//    public class LoginProxy
//
//    ;
//
//    public class ResourceObserver
//
//    ;
//
//    // 非 setter/getter方法 的参数名称，不允许与本类成员变量同名

}

class Son extends ConfusingName {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        // 超过 120 个字符的情况下，换行缩进 4 个空格，并且方法前的点号一起换行
        builder.append("yang").append("hao")
                .append("chen")
                .append("chen")
                .append("chen");
    }
}

