package com.oa.bean;

import java.util.Objects;

public class Dept {
    private String deptno;
    private String dname;
    private String location;

    public Dept() {

    }

    public Dept(String deptno, String dname, String location) {
        this.deptno = deptno;
        this.dname = dname;
        this.location = location;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno='" + deptno + '\'' +
                ", dname='" + dname + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dept dept = (Dept) o;
        return Objects.equals(deptno, dept.deptno) && Objects.equals(dname, dept.dname) && Objects.equals(location, dept.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptno, dname, location);
    }
}
