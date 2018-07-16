package org.eminentstar.modelbinding.converter;

import org.eminentstar.enumeration.Level;
import org.springframework.core.convert.converter.Converter;

public class StringToLevelConverter implements Converter<String, Level> {
  @Override
  public Level convert(String text) {
    return Level.valueOf(Integer.parseInt(text));
  }
}
