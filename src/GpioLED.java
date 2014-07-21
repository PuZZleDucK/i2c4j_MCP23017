
// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
//    GPIO pin interface
package org.puzzleduck.rpi;

import com.pi4j.io.gpio.*;
import java.util.*;
import java.time.*;

public class GpioLED implements AbstractPin{
  private GpioPinDigitalOutput thisPin;
  public GpioLED(Pin newPin) {
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

