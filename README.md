# 동호회 서비스 앱

## 프로젝트 소개

- 동창들과의 연락과 만남을 만들어주는 앱입니다.
- 앱의 완성 보다는 공부 목적으로 기술들의 구현에 초점을 두었습니다.

## 사용 기술 및 환경

JAVA 17, spring boot 3.2.2, spring data jpa,QueryDsl, mysql, docker, prometheus, grafana, ngrinder, nginx

## TODO

- [ ] OpenAPI spec 적용
- [x] Docker 활용
- [x] 모니터링(APM)
- [x] 부하 테스트 (ngrinder 사용)
- [x] 로드밸런서(nginx)를 사용한 분산 처리, 분산처리 전후 부하테스트 비교
- [ ] MSA 구현
- [ ] 디비 캐싱 (Redis)
- [ ] CI/CD

## 목차
- #### [분산처리와 부하테스트](#분산처리와-부하테스트)
- #### [MSA](#msa)
  - ##### [API Gateway](#api-gateway)
  - ##### [보상 트랜잭션](#보상-트랜잭션)
- #### [회고](회고)

## 분산처리와 부하테스트

docker compose 로 board app의 cpu와 memory를 제한시킨 뒤 세 개로 scale하고, nginx를 로드밸런서로 활용하여 분산처리를 구현했습니다.

### 모니터링
prometheusd와 grafana를 사용하여 모니터링 환경을 만들었습니다.

### 부하테스트

nGrinder를 사용하여 분산처리 전후로 나누어 부하테스트를 진행 했습니다.

nGrinder를 실행하는 환경과 Spring boot app을 실행하는 환경은 서로 간섭이 일어나지 않게 분리하여 진행했습니다.

결과를 봤을 때 분산처리를 했을 때가 하지 않았을 때보다 TPS가 3배 높아진 모습을 볼 수 있습니다.

분산처리
<img width="1224" alt="Screenshot 2024-03-14 at 4 07 09 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/d8cbfec1-cedd-4f5f-8d89-0c80ffae3a88">

분산처리 x
<img width="1214" alt="Screenshot 2024-03-14 at 4 07 33 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/fd430390-7519-4aaf-93ff-2dbecc457cea">

부하 테스트 중 GC이론에서 공부했던 STW로 보이는 현상을 볼 수 있었습니다. 

부하테스트 도중 TPS가 0에 가깝게 내려가는 시점이 있었는데, 해당 시점에 Grafana를 확인해본 결과 Tenured Gen이 max를 찍고 다시 내려가는 것을 볼 수 있었습니다.

관측 결과 Tenured gen의 메모리 공간이 다 차면서 GC가 실행되고 STW가 발생한 것으로 예상됩니다.

<img width="795" alt="Screenshot 2024-03-14 at 4 18 02 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/c7e35197-01ba-47e2-bd1e-190d488ec439">

<img width="474" alt="Screenshot 2024-03-14 at 4 18 13 PM" src="https://github.com/f-lab-edu/school-reunion/assets/46589339/204322d0-9fcf-424a-898b-590769546db8">

## MSA

### API Gateway

#### MSA 환경에서의 API 관리 문제점

MSA 환경에서는 각 서비스가 독립적으로 실행되기 때문에 API의 주소도 각기 다릅니다. 
#### 예시

- 서비스1 - [http://foo-bar1.com/order](http://service1.com/order)
- 서비스2 - [http://foo-bar2.com/product](http://service2.com/product)

이처럼 여러 서비스가 존재할 경우, 각 서비스마다 API 주소를 알아야 하는 불편함이 발생합니다. 서비스가 수십 개가 된다면 이러한 복잡성은 더욱 증가합니다. 이로 인해 다음과 같은 문제점이 생길 수 있습니다:

- **복잡성 증가**: 각 서비스의 API 주소를 모두 관리하고 기억해야 합니다.
- **유지보수 어려움**: 서비스 주소가 변경될 때마다 클라이언트 측 코드를 수정해야 합니다.

이러한 문제를 해결하기 위해 API 게이트웨이를 도입할 수 있습니다. API 게이트웨이는 서비스들 위에 하나의 레이어를 추가하여 모든 API 요청을 관리합니다. 사용자가 API 게이트웨이로 요청을 보내면, 게이트웨이는 해당 요청을 분석하여 적절한 서비스로 요청을 전달하고, 각 서비스로부터 응답을 받아 다시 사용자에게 전달합니다. 따라서 사용자는 각 서비스의 API를 일일이 알 필요 없이, API 게이트웨이의 API 주소 하나만 알면 됩니다.

#### 예시

- 사용자 요청: http://apigateway.com/order
    - API 게이트웨이에서 요청을 받아 http://service1.com/order로 전달
- 사용자 요청: http://apigateway.com/product
    - API 게이트웨이에서 요청을 받아 http://service2.com/product로 전달

이번 프로젝트에서는 Spring Cloud를 사용하여 API 게이트웨이를 구현하였습니다. Spring Cloud Gateway를 활용하여 간편하게 API 게이트웨이를 구축하고 설정할 수 있었습니다.

향후 api gateway에서 공통적으로 인증/인가 처리를 한 뒤 각 서비스에 요청을 보내는 기능도 구현할 예정입니다.

### 보상 트랜잭션

트랜잭션이란 하나의 작업 단위로 트랜잭션의 작업 안에서의 작업은 모두 실행되거나 하나라도 실패하면 모두 실행되지 않아야 합니다. 모놀로식 애플리케이션에서의 트랜잭션은 @Transactional 어노테이션을 붙이거나, try catch문을 사용하여 구현하는 등 쉽게 적용할 수 있습니다.

하지만 MSA와 같이 서비스들이 각자 분리되어 있으면 각 작업간의 성공 실패 여부, 롤백 여부를 즉시 확인하기 어렵습니다. api로 통신하여 정보를 주고받는 방법이 있지만, 성공 실패 여부를 확인하기 위해 여러번의 api 통신을 하게 되면 그만큼 애플리케이션과 네트워크의 작업량이 늘어 오버헤드가 발생할 수 있습니다. 또한 네트워크 상황에 따라 통신에 실패할 가능성도 존재합니다.

#### SAGA

이를 해결하기 위해 MSA에서는 몇 가지 트랜잭션 관리 전략이 있으며, 대표적으로 SAGA패턴이 있습니다. SAGA 패턴은 보상 트랜잭션을 사용하여 분산 시스템에서 트랜잭션의 일관성을 유지합니다. 이번 프로잭트에서는 SAGA 패턴의 Choreograpy 전략을 사용했습니다.

Choreograpy 전략은 메시지브로커를 사용해 서비스간 통신을 합니다. 각 서비스는 작업에 대한 이벤트를 구독한 뒤 성공 여부를 주고받고 실패 이벤트 발생시 성공했던 작업을 취소합니다. 각 서비스는 독립적으로 이벤트를 실행합니다.

이 프로젝트에서는 회원이 삭제될 때 회원이 작성한 글도 모두 삭제 되어야하는 상황에 보상 트랜잭션을 적용했습니다.

#### Choreograpy 패턴

![Untitled](https://github.com/hang-out-with-us/backend/assets/46589339/a72f4e59-91d7-45c0-a662-b4417cf91a9b)

#### Auth

```java
@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RabbitTemplate rabbitTemplate;

    public MemberService(MemberRepository memberRepository, RabbitTemplate rabbitTemplate) {
        this.memberRepository = memberRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public MemberDeleteResponse deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.delete();

        return sendMessageWithRetry(member.getId());
    }

    private MemberDeleteResponse sendMessageWithRetry(Long memberId) {
        int retryCount = 0;
        String response = "";

        while (retryCount < 3) {
            log.info("Sending message to RabbitMQ, attempt {}", retryCount + 1);
            response = (String) rabbitTemplate
                    .convertSendAndReceive("board.exchange", "member-id." + memberId, memberId);

            if ("OK".equals(response)) {
                log.info("Successfully received response for member id: {}", memberId);
                break;
            }

            log.warn("Timeout waiting for response, retrying... attempt {}", retryCount + 1);
            
            // board service에서 메시지를 받지 못 했을 때 queue에서 해당 메세지 삭제
            if (response == null) {
                deleteMessage(memberId);
            }
            retryCount++;
        }
        if (retryCount == 3) {
            compensateMember(memberId);
            return new MemberDeleteResponse(false, "fail delete member", memberId);
        }

        return new MemberDeleteResponse(true, "success delete member", memberId);
    }
    

    private void deleteMessage(Long memberId) {
        rabbitTemplate.setRoutingKey("member-id." + memberId);
        rabbitTemplate.receive("board.delete");
    }

    // 보상 트랜잭션
    private void compensateMember(Long memberId) {
        log.info("compensate member id: {}", memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.undelete();
    }
}
```

#### Board

```java
@Slf4j
@Service
public class BoardService {

		...

		@Transactional
    @RabbitListener(queues = "board.delete")
    public String deleteBoardsByMemberId(Long memberId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            boardRepository.deleteAllByMemberId(memberId);
            channel.basicAck(tag, false);
            log.info("Delete boards by member id: {}", memberId);
            return "OK";
        } catch (Exception e) {
            log.error("Error deleting boards by member id: {}, message: {}", memberId, e.getMessage(), e);
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                log.error("Failed to acknowledge message: {}", ioException.getMessage(), ioException);
            }
            return "fail";
        }
    }
}
```

#### 플로우

1. member 삭제 요청을 받습니다.
2. AuthService에서 member를 삭제 후 convertSendAndReceive()로 해당 member id를 담아 rabbitMQ에 메시지를 보냅니다.
    1. 일정 시간 내에 응답을 받지 못하거나 실패 메시지를 받으면 최대 3번 재요청을 보냅니다.
    2. 실패시 → 4
3. BoardService에서 메시지를 받으면 member id로 게시물들을 삭제하고 성공 메시지, 실패시 실패 메시지를 보냅니다.
4. 실패시 compensateMember() 메소드로 보상 트랜잭션을 실행합니다. (member 삭제 롤백)

##### 메시지 전송 실패 - 보상 트랜잭션

![Screenshot 2024-06-10 at 2 47 09 AM](https://github.com/hang-out-with-us/backend/assets/46589339/90260dbf-2343-4c96-af3a-e41566b16e74)

##### 메시지 전송 성공

![Screenshot 2024-06-10 at 2 56 42 AM](https://github.com/hang-out-with-us/backend/assets/46589339/c9dcbd36-4c11-45ac-8a0b-7d15fde78c43)

![Screenshot 2024-06-10 at 2 57 11 AM](https://github.com/hang-out-with-us/backend/assets/46589339/6b408432-151d-4fdd-803e-6bddcf5add48)

## 회고

### MSA

큰 기업들에서 제공하는 서비스가 많아지면서 마이크로 서비스 아키텍쳐를 많이 사용하고 있습니다. MSA라는 것이 있다는 정도만 알고 있다가 이번 기회에 프로젝트를 통해 MSA를 경험해보았습니다.

msa의 개념만 알고 있을 땐 각 서비스를 독립적으로 운영하면 다른 서비스에 문제가 생겨도 영향을 받지 않아서 여러 서비스를 운영하는 기업에서는 좋은 방법인 것 같다 라고 단순하게 생각했었습니다.

하지만 서비스간의 통신 방법, 네트워크 장애가 생겼을 때의 문제, 서비스들이 공통적으로 사용하는 데이터의 정합성은 어떻게 유지할 것인지, MSA 환경에서의 트랜잭션의 어려움, 인증 인가 문제 등 여러가지 문제점들도 있었습니다.

이러한 문제점들에 대해 어떤 방법으로 해결할 수 있을지 공부하고 생각해보는 기회가 되었고, MSA에 대해 더 자세히 이해하고, 모놀리식과 마이크로 서비스간의 장단점에 대해 알아볼 수 있는 경험이 되었습니다.

### 부하테스트, 모니터링

애플리케이션을 실서비스로 제공하려면 예상되는 부하 수준을 파악하고, 애플리케이션이 그 부하를 견딜 수 있는지 확인해야 합니다. 이를 통해 적절한 자원 할당을 계획할 수 있으며, 서비스의 안정성과 성능을 보장할 수 있습니다.

이전 프로젝트에서는 단순히 기능이 정상적으로 동작하는지에만 초점을 맞추었고, 성능 측정도 콘솔에 stopwatch 결과를 출력하는 정도에 그쳤습니다. 

이번 프로젝트에서는 Ngrinder를 사용하여 실서비스와 가깝게 부하 테스트를 수행했습니다. 부하 테스트를 진행하면서 Prometheus와 Grafana를 사용하여 서버의 상태를 모니터링했습니다. 이를 통해 어느 부분에 부하가 집중되는지, 자원의 사용량이 어떻게 변하는지를 세세히 확인할 수 있었습니다.
