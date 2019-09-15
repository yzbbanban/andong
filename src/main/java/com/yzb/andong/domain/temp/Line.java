package com.yzb.andong.domain.temp;

/**
 * Created by brander on 2019/9/14
 */
public class Line {

    /**
     * id : 1
     * name : 线别1
     * unit : 单位
     * banzhang : Admin
     * shuizhizhu : 123
     * num : 354345
     */

    private int id;
    private String name;
    private String unit;
    private String banzhang;
    private int shuizhizhu;
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBanzhang() {
        return banzhang;
    }

    public void setBanzhang(String banzhang) {
        this.banzhang = banzhang;
    }

    public int getShuizhizhu() {
        return shuizhizhu;
    }

    public void setShuizhizhu(int shuizhizhu) {
        this.shuizhizhu = shuizhizhu;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", banzhang='" + banzhang + '\'' +
                ", shuizhizhu=" + shuizhizhu +
                ", num=" + num +
                '}';
    }
}
