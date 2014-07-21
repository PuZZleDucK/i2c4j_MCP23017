
// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
//    GPIO pin interface
package org.puzzleduck.rpi;

import com.pi4j.io.gpio.*;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.util.*;
import java.time.*;

private byte pinA0 = 0x01;
private byte pinA1 = 0x02;
private byte pinA2 = 0x04;
private byte pinA3 = 0x08;
private byte pinA4 = 0x10;
private byte pinA5 = 0x20;
private byte pinA6 = 0x40;
private byte pinA7 = 0x80;

private byte pinB0 = 0x01;
private byte pinB1 = 0x02;
private byte pinB2 = 0x04;
private byte pinB3 = 0x08;
private byte pinB4 = 0x10;
private byte pinB5 = 0x20;
private byte pinB6 = 0x40;
private byte pinB7 = 0x80;

byte[] bankA = {pinA0, pinA1, pinA2, pinA3, pinA4, pinA5, pinA6, pinA7};
byte[] bankB = {pinB0, pinB1, pinB2, pinB3, pinB4, pinB5, pinB6, pinB7};


public class I2cLED implements AbstractPin{
  private GpioPinDigitalOutput thisPin;

  private static final int i2cBus = 1;
  private static final int address = 0x20;
  private static I2CBus bus;


  public I2cLED(Pin newPin) {
    GpioController gpio = GpioFactory.getInstance();
    thisPin = gpio.provisionDigitalOutputPin(newPin, "auto", PinState.LOW);
    thisPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    gpio.shutdown();
  }

  public void pulsePin(int duration, int pulseDelay) {
    thisPin.pulse(duration);
    try{ 
      Thread.sleep(pulseDelay);
    } catch(Exception e) {
      System.out.println("Sleep Fail:"+e);
    }
  }

  public boolean isOn() {
    return false;//report false for ascii display
  }

}//class

