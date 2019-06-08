[![Build Status](https://travis-ci.com/antop-dev/kotlin-msa.svg?branch=master)](https://travis-ci.com/antop-dev/kotlin-msa)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_kotlin-msa&metric=alert_status)](https://sonarcloud.io/dashboard?id=antop-dev_kotlin-msa)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=antop-dev_kotlin-msa&metric=coverage)](https://sonarcloud.io/dashboard?id=antop-dev_kotlin-msa)
[![Coverage Status](https://coveralls.io/repos/github/antop-dev/kotlin-msa/badge.svg?branch=master)](https://coveralls.io/github/antop-dev/kotlin-msa?branch=master)
[![GitHub forks](https://img.shields.io/github/forks/antop-dev/kotlin-msa.svg)](https://github.com/antop-dev/kotlin-msa/network)
[![GitHub stars](https://img.shields.io/github/stars/antop-dev/kotlin-msa.svg)](https://github.com/antop-dev/kotlin-msa/stargazers)
[![GitHub license](https://img.shields.io/github/license/antop-dev/kotlin-msa.svg)](https://github.com/antop-dev/kotlin-msa/blob/master/LICENSE)

# 코틀린 마이크로서비스 개발

![Imgur](https://i.imgur.com/asStqwV.jpg)

구글이 안드로이드 생태계에서 코틀린(Kotlin)의 지원을 발표하면서 코틀린은 주류 언어로 인식되기 시작했다. 마이크로서비스(Microservices)는 확장성 있고 관리하기 쉬운 웹 애플리케이션을 설계하는 데 도움이 되며, 코틀린은 현대적 관용구(Idio)를 활용해 개발을 단순화하고 고품질 서비스를 만들 수 있게 한다. 코틀린은 JVM과 100% 상호 운용성이 있어 기존 자바 코드를 가지고 작업하기 쉽다. 스프링(Spring), 잭슨(Jackson), 리액터(Reactor) 같은 인기 있는 자바 프레임워크에는 널 안전성(Null-safty)이나 타입 안전(type-safe) 선언 빌더와 같은 언어 기능을 활용하는 코틀린 모듈이 들어있다.
 
이 책은 운영 환경에서 테스트 가능한 코드로 서비스를 설계 및 구현해 독자가 기존 자바 구현보다 더 짧고 유지보수가 용이하고 편한 코드를 작성할 수 있게 한다.
 
넌블로킹(Non-blocking) 기술을 활용하고 서비스를 차기 수준의 업계 표준으로 끌어올리기 위해 리액티브(Reactive) 패러다임을 사용하면 좋은 점을 알게 될 것이다.
 
책을 읽는 도중에 대용량 처리 마이크로서비스를 만들기 위해 리액티브적으로 NoSQL 데이터베이스를 사용한다.
 
이 책에서는 다양한 클라우드 환경에서 실행할 수 있는 클라우드 네이티브 마이크로서비스(Cloud Native Microservice)를 만드는 방법과 이를 모니터링하는 방법을 알려준다.
 
마이크로서비스용 도커(Docker) 컨테이너(Container)를 만들고 이를 확장하는 방법을 알 수 있다. 마지막으로, 오픈시프트 온라인(Openshift Online)에 마이크로서비스를 배포한다.

## Chapter 07

Windows 10 Home 환경에서느 `Docker for Windows` 설치가 안된다. 그래서 VMWare에 `Ubuntu 16 + Docker` 설치하고 원격으로 제어한다. 

https://gist.github.com/styblope/dc55e0ad2a9848f2cc3307d4819d819f

https://www.jetbrains.com/help/idea/docker.html

### [docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin) 이슈

* Dockerfile 위치 관련 이슈
    
    `<build>` → `<dockerFileDir>` 안먹힌다. `<dockerFile>', '<contextDir>`도 안먹힌다.

    결국 `Dockerfile`을 프로젝트 루트에 위치 시키면 자동 인식이 된다. ㅠㅠ

    https://dmp.fabric8.io/#external-dockerfile
    
* `<image>` 에서 `<name>`을 원하는 대로 설정할 수 없다.
    
    무조건 `${groupId}/${articleId}/${version}`으로 생성된다.

* `<port>` 매핑이 안된다.

## Chapter 08

* `docker push` 할 때 `localhost` 키워드에 대한 이슈가 생김 `127.0.0.1`로 하면 된다.

  ```
  > docker tag hello 127.0.0.1:5000/hello
  > docer push 127.0.0.1:5000/hello 
  ```

* https://swarmpit.io/