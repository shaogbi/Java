package others.immutableobject;

import java.util.*;

public final class ImmutableRGBContainer {
  private final Map<String, ImmutableRGB> rgbMap;
  public ImmutableRGBContainer() {
    this.rgbMap = new HashMap<String, ImmutableRGB>();
    // put some ImmutableRGB instances
  }
  public ImmutableRGB getImmutableRGB(String rgbName) {
    return rgbMap.get(rgbName);
  }
  public Map<String, ImmutableRGB> getRouteMap() {
    // unmodifiableMap is only readable
    // if use GoogleGuava, use ImmutableMap instead
    return Collections.unmodifiableMap(rgbMap);
  }
}
