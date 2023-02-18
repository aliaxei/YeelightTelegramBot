package com.kislytgbot.solution.YeelightBot.api.yapi.services;

import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustAction;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustProperty;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightEffect;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightProperty;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.api.yapi.flow.YeelightFlow;

import java.util.Map;

public interface YeelightDeviceService {

    /**
     * Setter for Yeelight device effect
     *
     * @param effect Effect to set (if null, 'sudden' is chosen)
     */
    void setEffect(YeelightEffect effect);

    /**
     * Setter for Yeelight device effect duration
     *
     * @param duration Duration to set (&gt;= 0)
     */
    void setDuration(int duration);

    /**
     * Retrieve properties of device
     *
     * @param properties Required properties (if no property is specified, all properties are retrieve)
     * @return Required properties map (Property → string value)
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    Map<String, String> getProperties(YeelightProperty... properties) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Change the device color temperature
     *
     * @param colorTemp Color temperature value [1700;6500]
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setColorTemperature(int colorTemp) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Change the device color
     *
     * @param r Red value [0;255]
     * @param g Green value [0;255]
     * @param b Blue value [0;255]
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Change the device backlight color
     *
     * @param r Red value [0;255]
     * @param g Green value [0;255]
     * @param b Blue value [0;255]
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setBacklightRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Change hue and sat of the device
     *
     * @param hue Hue value [0;359]
     * @param sat Sat value [0;100]
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setHSV(int hue, int sat) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Change the device brightness
     *
     * @param brightness Brightness value [1;100]
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setBrightness(int brightness) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Switch on or off the device power
     *
     * @param power Power value (true = on, false = off)
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setPower(boolean power) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Switch on or off the backlight device power
     *
     * @param power Power value (true = on, false = off)
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setBacklightPower(boolean power) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Toggle the device power
     *
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void toggle() throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Save current state of the device as 'default'.
     * If device is power off and then power on (hard power reset), the device will show 'default' saved state
     *
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setDefault() throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Start a flow
     *
     * @param flow Flow to start
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void startFlow(YeelightFlow flow) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Stop a flow
     *
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void stopFlow() throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Start a power off device timer
     *
     * @param delay Timer delay in minutes (&gt;= 0)
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void addCron(int delay) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Retrieve delay in minutes of current power off timer
     *
     * @return Delay in minutes of current power off timer (0 if there is no timer)
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    int getCronDelay() throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Remove/stop current power off timer
     *
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void deleteCron() throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Adjust some parameters. (Main used by controllers)
     *
     * @param property Property to adjust (if null, Circle chosen)
     * @param action   Direction of adjustment (if null, Color chose). For property {@link YeelightAdjustProperty#COLOR}, action can only be {@link YeelightAdjustAction#CIRCLE}
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setAdjust(YeelightAdjustProperty property, YeelightAdjustAction action) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Name the device. The name will be stored in the device and can be accessed in device properties
     *
     * @param name Device name value (if null, empty name will sent)
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setName(String name) throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Set red backlight to device.
     *
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void setRedBacklight() throws YeelightResultErrorException, YeelightSocketException;

    /**
     * Turn off all light(Backlight and general lamp light)
     *
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException      when socket error occurs
     */
    void turnOffLight() throws YeelightResultErrorException, YeelightSocketException;
}
