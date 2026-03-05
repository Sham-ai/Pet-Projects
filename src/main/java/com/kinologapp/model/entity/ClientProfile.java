package com.kinologapp.model.entity;

/**
 * ClientProfile — данные роли CLIENT.
 */
public class ClientProfile {

    private boolean vip;

    public ClientProfile(boolean vip) {
        this.vip = vip;
    }

    public ClientProfile() {
        this(false);
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
