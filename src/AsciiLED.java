
// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
//    Virtual Ascii pins
package org.puzzleduck.rpi;

import com.pi4j.io.gpio.*;
import java.util.*;
import java.time.*;

public class AsciiLED implements AbstractPin{
  private boolean currentState;

  public AsciiLED() {
    currentState = false;
  }

  public void pulsePin(int duration, int pulseDelay) {
    currentState = true;
    Instant changeBack = Instant.now().plusNanos(duration);
//    System.out.println("Trigger:"+changeBack);
    Timer eventTimer = new Timer();
    eventTimer.schedule(new OffTask(), duration);

    try{ 
      Thread.sleep(pulseDelay);
    } catch(Exception e) {
      System.out.println("Sleep Fail:"+e);
    }
  }

  public boolean isOn() {
    return currentState;
  }

  private class OffTask extends TimerTask {
    public void run() {
      currentState = false;
    }
  }

}

