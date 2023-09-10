package com.nntk.sb.domain;

/**
 * 商品信息（表：t_user）
 *
 * @author MQG
 * @date null
 */
public class TUser {
    private Integer id;

    private String username;

    private Byte sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }
}