
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

  private static final int address = 0x20;

  private static byte bankDirA = (byte)0x00;
  private static byte bankDirB = (byte)0x01;
  private static byte GpioA = (byte)0x12;
  private static byte GpioB = (byte)0x13;

  private static byte pinA0 = (byte)0x01;
  private static byte pinA1 = (byte)0x02;
  private static byte pinA2 = (byte)0x04;
  private static byte pinA3 = (byte)0x08;
  private static byte pinA4 = (byte)0x10;
  private static byte pinA5 = (byte)0x20;
  private static byte pinA6 = (byte)0x40;
  private static byte pinA7 = (byte)0x80;

  private static byte pinB0 = (byte)0x01;
  private static byte pinB1 = (byte)0x02;
  private static byte pinB2 = (byte)0x04;
  private static byte pinB3 = (byte)0x08;
  private static byte pinB4 = (byte)0x10;
  private static byte pinB5 = (byte)0x20;
  private static byte pinB6 = (byte)0x40;
  private static byte pinB7 = (byte)0x80;

  private static byte[] bankA = {pinA0, pinA1, pinA2, pinA3, pinA4, pinA5, pinA6, pinA7};
  private static byte[] bankB = {pinB0, pinB1, pinB2, pinB3, pinB4, pinB5, pinB6, pinB7};

  private byte thisPin;
  private boolean isBankA;
  private I2CDevice i2cIO;
  private I2CBus bus;

  public I2cLED(int pinNumber) {

    try {
      bus = I2CFactory.getInstance(I2CBus.BUS_1);
      i2cIO = bus.getDevice(address);
      System.out.print(" :: "+bus+i2cIO);
//    GpioController gpio = GpioFactory.getInstance();
//    thisPin = gpio.provisionDigitalOutputPin(newPin, "auto", PinState.LOW);
//    thisPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
//    gpio.shutdown();



      if( pinNumber < 8) {
        isBankA = true;
        thisPin = (byte)pinNumber;
        i2cIO.write(bankDirA, (byte)0x0);// 0 for out; not this ->bankA[thisPin]
      } else {
        isBankA = false;
        thisPin = (byte)(pinNumber-8);
        i2cIO.write(bankDirB, (byte)0x0);
      }
    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }

  }

  public void pulsePin(int duration, int pulseDelay) {
    try{ 
      if(isBankA) {
        i2cIO.write(GpioA, bankA[thisPin]);// bank A
      } else {
        i2cIO.write(GpioB, bankB[thisPin]);
      }

      Instant changeBack = Instant.now().plusNanos(duration);
      System.out.println("Trigger:"+changeBack);
      Timer eventTimer = new Timer();
      eventTimer.schedule(new OffTask(), duration);

      Thread.sleep(pulseDelay);
    } catch(Exception e) {
      System.out.println("Sleep Fail:"+e);
    }
  }

  public boolean isOn() {
    return false;//lying
  }

  private class OffTask extends TimerTask {
    public void run() {
      System.out.println("<-- event");
      try{ 
        if(isBankA) {
          i2cIO.write(GpioA, (byte)0x0);// hack off for now
        } else {
          i2cIO.write(GpioB, (byte)0x0);
        }
      } catch(Exception e) {
        System.out.println("Sleep Fail:"+e);
      }
    }//run
  }//offtask

}

