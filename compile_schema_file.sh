#! /bin/bash

#      생성할 클래스의 패키지를 지정   변환할 스키마 파일
xjc -p springbook.user.sqlservice.jaxb sqlmap.xsd -d src
