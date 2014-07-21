
// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
//    Abstract dsplay to interoperate LEDs, virtual Ascii displays and shift register pins
package org.puzzleduck.rpi;

import java.util.*;
import java.time.*;
import org.puzzleduck.rpi.*;

public class AbstractDisplay {
  private final ArrayList<AbstractPin> abstractLEDs;
  private String ledOn = "0";
  private String ledOff = ".";

  public AbstractPin getFirst() {
    return abstractLEDs.get(0);
  }

  public AbstractPin getNext() {
    return abstractLEDs.get(2);
  }

  public AbstractDisplay() {
    abstractLEDs = new ArrayList<AbstractPin>();
  }

  public void addPin(AbstractPin newPin) {
    abstractLEDs.add(newPin);
  }

  public void addPinRev(AbstractPin newPin) {
    abstractLEDs.add(0, newPin);
  }

  public void addPins(AbstractPin... newPins) {
    for(AbstractPin newPin : newPins) {
      abstractLEDs.add(newPin);
    }
  }

  public void updateDisplay() {
      Timer repeat = new Timer();
      repeat.schedule(new StartDisplay(), 100);
  }
  
  public class StartDisplay extends TimerTask {
    public void run() {
      System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");//count leds and loop
      System.out.print("[");
      for(AbstractPin newPin : abstractLEDs){
        if(newPin instanceof AsciiLED) {//check pin type
          if(newPin.isOn()) {
            System.out.print(ledOn+"");
          } else {
            System.out.print(ledOff+"");
          }
        }
      }
      System.out.print("\b]");
        Timer repeat = new Timer();
        repeat.schedule(new StartDisplay(), 100);
    }//run
  }//class

  public void shutdown() {
    try {
      Thread.sleep(2000);
    } catch(Exception e){
      System.out.println("E:"+e);
    }
    System.out.println("");
    System.exit(0);
  }

  public ArrayList<AbstractPin> getLEDs() {
    return abstractLEDs;
  }
}

