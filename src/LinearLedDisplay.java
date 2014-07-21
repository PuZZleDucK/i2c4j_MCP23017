// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
// Prerequisites:
//      -Java8.
//      -Pi4J & WiringPi.
//      -A Raspberry Pi to run it on.
//      -A LED strip.
//
package org.puzzleduck.rpi;

import com.pi4j.io.gpio.*;
import java.util.*;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;


public class LinearLedDisplay{ //pin mappings.
  private AbstractDisplay ad;
  private AbstractDisplay adRev;
  private int pulseDuration = 370;
  private int pulseDelay = 155;
  private int endDelay = 255;

  private static final int i2cBus = 1;
  private static final int address = 0x20;
  private static I2CBus bus;
  private static I2CDevice i2cIO;

  public static void main(String args[]) throws Exception{
    System.out.println(" :: i2c Test :: ");

        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            System.out.println(" :: Got BUS");
            i2cIO = bus.getDevice(address);
            System.out.println(" :: Got Device");
            Thread.sleep(500);
//            i2cIO.write(0x12, (byte)0x0);//pin 1 on port a
//            i2cIO.write(0x13, (byte)0x0);//pin 1 on port b
            i2cIO.write(0x00, (byte)0x00);//clear for output


//            i2cIO.write(0x09, (byte)0x0F);//bank a .. all? on xf
            i2cIO.write(0x12, (byte)0xFF);// bank1, all on
//now turn it off you fool!!!
for(int dummy = 0; dummy < 1; dummy = dummy) {
for(int x = 1; x < 256; x=x+x) {
            System.out.println(" ::       now:" + x);
            Thread.sleep(200);
            i2cIO.write(0x12, (byte)x);//latch a .. on/all
}
for(int x = 256; x > 0; x=x/2) {
            System.out.println(" ::       now:" + x);
            Thread.sleep(200);
            i2cIO.write(0x12, (byte)x);//latch a .. on/all
}
Thread.sleep(200);
i2cIO.write(0x12, (byte)0);//latch a .. on/all

}
            Thread.sleep(1500);
            i2cIO.write(0x12, (byte)0x00);//latch a .. on/all


            Thread.sleep(500);
            System.out.println(" ::   wrote a1");


/*
//for python: readandchangepin(MCP23017_GPIOA, pin, value, self.i2c.readU8(MCP23017_OLATA))
//for j: readandchangepin(MCP23017_GPIOA, pin, value, self.i2c.readU8(MCP23017_OLATA))
MCP23017_IODIRA = 0x00
MCP23017_IODIRB = 0x01
MCP23017_GPIOA = 0x12
MCP23017_GPIOB = 0x13
MCP23017_GPPUA = 0x0C
MCP23017_GPPUB = 0x0D
MCP23017_OLATA = 0x14
MCP23017_OLATB = 0x15
MCP23008_GPIOA = 0x09
MCP23008_GPPUA = 0x06
MCP23008_OLATA = 0x0A
*/

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
            System.out.println("Connected to bus OK!!!");




    LinearLedDisplay lld = new LinearLedDisplay(21);
    Thread.sleep(2000); // suspense...
    lld.demo();
  }

  public void demo() throws Exception {
    ad.updateDisplay();
    

    
//parallel & ordered
    for(int loopCount = 0; loopCount < 5; loopCount++) {  //LOOP
      ad.getLEDs().parallelStream().forEachOrdered(p -> p.pulsePin(pulseDuration, pulseDelay));
      Thread.sleep(endDelay);
      adRev.getLEDs().parallelStream().forEachOrdered(p -> p.pulsePin(pulseDuration, pulseDelay));
      Thread.sleep(endDelay);
    }
    

//cron schedules & make


    ad.shutdown();
  }

  public LinearLedDisplay(int count) {
    System.out.print(" :: Virtual LedDisplay Init [");
    ad = new AbstractDisplay();
/*
    ad.addPins(new GpioLED(RaspiPin.GPIO_17), new GpioLED(RaspiPin.GPIO_18),
               new GpioLED(RaspiPin.GPIO_20), new GpioLED(RaspiPin.GPIO_19),
               new GpioLED(RaspiPin.GPIO_11), new GpioLED(RaspiPin.GPIO_10),
               new GpioLED(RaspiPin.GPIO_14), new GpioLED(RaspiPin.GPIO_06),
               new GpioLED(RaspiPin.GPIO_13), new GpioLED(RaspiPin.GPIO_12),
               new GpioLED(RaspiPin.GPIO_05), new GpioLED(RaspiPin.GPIO_04),
               new GpioLED(RaspiPin.GPIO_03), new GpioLED(RaspiPin.GPIO_02),
               new GpioLED(RaspiPin.GPIO_01), new GpioLED(RaspiPin.GPIO_00),
               new GpioLED(RaspiPin.GPIO_16), new GpioLED(RaspiPin.GPIO_15),
               new GpioLED(RaspiPin.GPIO_07), new GpioLED(RaspiPin.GPIO_09),
               new GpioLED(RaspiPin.GPIO_08));
*/
    for(int x = 0; x < count; x++) {
      ad.addPin(new AsciiLED());
      System.out.print(x+".");
    }
    System.out.print("\b] :: \n");
    adRev = new AbstractDisplay();
    for(AbstractPin thisPin : ad.getLEDs()) {
      adRev.addPinRev(thisPin);
    }
  }

}

