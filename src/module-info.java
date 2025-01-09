open module pro.spring {
    requires spring.beans;
    requires spring.context;
    requires micrometer.commons;
    requires org.slf4j;
    requires spring.web;
    requires spring.data.jpa;
    exports main.java;
}