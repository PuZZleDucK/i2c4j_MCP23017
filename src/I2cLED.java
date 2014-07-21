
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

