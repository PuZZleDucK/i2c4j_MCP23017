
// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
//    Abstract pin interface to interoperate LEDs, virtual Ascii displays and shift register pins
package org.puzzleduck.rpi;

import com.pi4j.io.gpio.*;
import java.util.*;

public interface AbstractPin{
  public void pulsePin(int duration, int pulseDelay);
  public boolean isOn();
}

