
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

  private static byte bankDirA = 0x00;
  private static byte bankDirB = 0x01;
  private static byte GpioA = 0x12;
  private static byte GpioB = 0x13;

  private static byte pinA0 = 0x01;
  private static byte pinA1 = 0x02;
  private static byte pinA2 = 0x04;
  private static byte pinA3 = 0x08;
  private static byte pinA4 = 0x10;
  private static byte pinA5 = 0x20;
  private static byte pinA6 = 0x40;
  private static byte pinA7 = 0x80;

  private static byte pinB0 = 0x01;
  private static byte pinB1 = 0x02;
  private static byte pinB2 = 0x04;
  private static byte pinB3 = 0x08;
  private static byte pinB4 = 0x10;
  private static byte pinB5 = 0x20;
  private static byte pinB6 = 0x40;
  private static byte pinB7 = 0x80;

  byte[] bankA = {pinA0, pinA1, pinA2, pinA3, pinA4, pinA5, pinA6, pinA7};
  byte[] bankB = {pinB0, pinB1, pinB2, pinB3, pinB4, pinB5, pinB6, pinB7};


  private byte thisPin;

  public I2cLED(int pinNumber) {
//    GpioController gpio = GpioFactory.getInstance();
//    thisPin = gpio.provisionDigitalOutputPin(newPin, "auto", PinState.LOW);
//    thisPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
//    gpio.shutdown();
    if( pinNumber < 8) {
      thisPin = pinNumber;
      i2cIO.write(bankDirA, bankA[thisPin]);//clear for output;
//        i2cIO.write(0x00, (byte)0x00);//clear for output
//        i2cIO.write(0x12, (byte)0xFF);// bank1, all on
    } else {
      thisPin = (pinNumber-8);
      i2cIO.write(bankDirB, bankB[thisPin]);
//      i2cIO.write(0x01, bankB[thisPin]);
    }
  }

  public void pulsePin(int duration, int pulseDelay) {
//    currentState = true;
    if(thisPin <= pinA7) {
      i2cIO.write(GpioA, bankA[thisPin]);// bankA
    } else {
      i2cIO.write(GpioB, bankB[thisPin]);
    }

    Instant changeBack = Instant.now().plusNanos(duration);
    System.out.println("Trigger:"+changeBack);
    Timer eventTimer = new Timer();
    eventTimer.schedule(new OffTask(), duration);

    try{ 
      Thread.sleep(pulseDelay);
    } catch(Exception e) {
      System.out.println("Sleep Fail:"+e);
    }
  }

  public boolean isOn() {
//lying    return false;
  }

  private class OffTask extends TimerTask {
    public void run() {
      if(thisPin <= pinA7) {
        i2cIO.write(GpioA, bankA[thisPin]);// bankA
      } else {
        i2cIO.write(GpioB, bankB[thisPin]);
      }
    }
  }

}

