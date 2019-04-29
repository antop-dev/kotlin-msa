# 2장. 스프링 부트 2.0 시작하기

[IntelliJ IDEA default keymap](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)

[보일러 플레이트 코드(boilerplate code)](https://en.wikipedia.org/wiki/Boilerplate_code)

꼭 필요하면서 간단한 기능인데, 많은 코드를 필요로 하는 코드

```java
public class Clazz {

    private int count;
    
    // ↓ Boilerplate code
    public int getCount() {
        return this.count;
    }
    
    public int setCount(int count) {
        this.count = count;
    }
}
```

컴포넌트 스캔은 @Component 애노테이션이 있는지 확인한다.

![Imgur](https://i.imgur.com/2NZZB88.png)

[YAML](https://ko.wikipedia.org/wiki/YAML)

[SpEL (Spring Expression Language)](https://www.baeldung.com/spring-expression-language)

