package org.eminentstar.conf.extend;

import org.apache.commons.lang3.StringUtils;
import org.eminentstar.conf.HelloConfigMode1;
import org.eminentstar.conf.HelloConfigMode2;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class HelloSelector implements ImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    String mode = (String)importingClassMetadata.getAnnotationAttributes(
      EnableHelloWithImportSelector.class.getName()).get("mode");

    if (StringUtils.equals(mode, "mode1")) {
      return new String[] {HelloConfigMode1.class.getName()};
    } else {
      return new String[] {HelloConfigMode2.class.getName()};
    }
  }
}
