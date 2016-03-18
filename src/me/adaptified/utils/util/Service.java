package me.adaptified.utils.util;

import net.pravian.aero.util.Loggers;

public interface Service {

    public void start();

    public void stop();

    public Loggers getLogger();

}
