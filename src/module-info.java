open module pro.spring {
    requires spring.beans;
    requires spring.context;
    requires micrometer.commons;
    requires org.slf4j;
    exports main.java;
}