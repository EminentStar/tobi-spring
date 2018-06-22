package springbook.conf;

import org.springframework.core.io.Resource;

/**
 * SqlServiceContext에서 sql mapping file의 위치를 지정하는 것을 분리하기 위함.
 */
public interface SqlMapConfig {
  Resource getSqlMapResource();
}
