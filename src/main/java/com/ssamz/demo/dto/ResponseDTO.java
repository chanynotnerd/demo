package com.ssamz.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 롬복 어노테이션을 사용하면 반복적인 코드 작성 없이
// getter, setter, hashCode, equals, toString 등의 메소드를 자동으로 생성해준다.


public class ResponseDTO<T> {	// <T>는 java의 Generic 문법, Generic은 클래스 내부에서 사용할 데이터 타입을 외부로 지정하는 기법이다.
    //  이를 통해 ResponseDTO 클래스를 다양한 타입에 대응할 수 있도록 만들어 코드의 재사용성을 높일 수 있다.
    //	HTTP 응답상태 코드 저장할 변수 선언
    private int status;

    // 실제 응답할 데이터 저장할 변수 선언
    private T data;
}
