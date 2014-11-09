package io.anw.Signs.Objects;

import io.anw.Signs.Utils.SQL.DatabaseManager;

public class AuroraSign {

    private String name;

    public AuroraSign(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return DatabaseManager.getInstance().getState(getName());
    }

    public int getPlayers() {
        return DatabaseManager.getInstance().getPlayers(getName());
    }

    public int getMaxPlayers() {
        return DatabaseManager.getInstance().getMaxPlayers(getName());
    }

}
