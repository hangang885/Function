package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import java.lang.Runnable

class coroutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        /*Log.d("hh","gg")
        main()
        Thread(Runnable {
            for(i in 1..10){
                Thread.sleep(1000)
                print("I'm working in Thread")
            }
        }).start()

        GlobalScope.launch {
            repeat(10){
                delay(1000)
                print("I'm working in Coroutine.")
            }
        }
        runBlocking {
            launch {
                for(i in 1..5) {
                    println(i)
                }
            }
        }

        runBlocking {
            var a = launch {
                for(i in 1..5) {
                    println(i)
                    delay(10)
                }
            }

            //새로운 Deferred 객체 생성
            val b = async {
                "async 종료"
            }

            println("async 대기")
            println(b.await())

            println("launch 취소")
            a.cancel()
            println("launch 종료")
        }


        runBlocking {
            var result = withTimeoutOrNull(50){
                for(i in 1.. 10){
                    println(i)
                    delay(10)
                }
                "Finish"
            }
            println(result)
        }

    }
    fun main(){
        val scope = GlobalScope

        scope.launch {
            for(i in 1..5)
                println(i)
        }
    }

    */
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO){
//                readFile()
                Log.d("ho","ho")
            }
            Log.d("코루틴","$result")
        }

    }
}


/*
* 코루틴(coroutine)
*   파이썬, C#, Go, Javascript 등 여러 언어에서 지원하고 있는 개념
*   협력형 멀티태스팅
*   동시성 프로그래밍 지원
*   비동기 처리를 쉽게 도와줌
*
* 1. 협력형 멀티태스킹
*   프로그래밍 언어로 표현하자면 Co + Routine 이다.
*   Co = "협력", "함께" 라는 의미
*   Routine = 하나의 태스크, 함수 정도로 생각하면 된다.
*   즉, 협력하는 함수
*
*   Routine
*       우리가 흔히 알고 있는 main routine 과 sub routine 이 존재한다.
*       main 함수가 말 그대로 Main 함수이다.
*       메인이 되는 함수는 다른 서브 함수인 plusOne 을 호출한다.
*       우리가 짜는 프로그램은 흔히 이런 형식으로 진행 되고 있다.
*
*               Sub Routine
*         -> 루틴에 진입하는 지점
*
*         <- 루틴을 빠져나오는 지점
*
*   public static void main(String[] args){     ->  Main routine
*       ...
*       int addedValue = plusOne(10);
*       }
*       int plusOne(int value) {                    -> Sub routine
*           int one = 1 ;
*           int addedValue = value + one;
*
*           return addedValue;
*       }
*
*
*
*       Sub Routine 은 루틴에 진입하는 지점과 루틴을 빠져나오는 지점이 명확하다.
*       즉, 메인 루틴이 서브 루틴을 호출하면, 서브루틴의 맨 처음 부분에 진입하여 return 문을 만나거나
*       서브 루틴을 닫는 괄호를 만나면 해당 서브루틴을 빠져나온다.
*       그러나 코루틴은 다르다
*
*                   Co Routine
*           -> 루틴에 진입
*           <- 탈출
*
*           -> 루틴에 진입
*           <- 탈출
*
*           -> 루틴에 진입
*           <- 탈출
*       코루틴도 routine 이기에 하나의 함수로 생각해야한다.
*       그러나 이 함수는 진입할 수 있는 진입점도 여러개이며 빠져나가는 탈출구도 여러개이다.
*       즉, 코루틴 함수는 꼭 return 문이나 마지막 닫는 괄호를 만나지 않더라도 언제든지 중간에 나갈 수 있다.
*
*       drawPerson 이라는 함수가 있고, 이 함수 안에는 startCoroutine 이라는 코루틴 빌더가 있다.
*       (실제로 startCoroutine 이라는 빌더가 존재하지 않는다. 실제 코루틴 라이브러리에는 다른 방식으로 코루틴을 만들지만 여기서는
*       쉽게 이해를 하기 위해 startCoroutine 이라고 사용한다.)
*
*       startCoroutine 이라는 코루틴을 만나게 되면 해당 함수는 코루틴으로 작동할 수 있다.
*       따라서, 언제는 함수 실행 중간중에 나갈 수도 있고, 다시 들어올 수도 있는 자격이 부여되는 것이다. 언제 코루틴을 중간에 나갈 수 있을까?
*       suspend 로 선언된 함수를 만나면 코루틴 밖으로 잠시 나갈 수 있다.
*
*       fun drawPerson(){       -> 1. 스레드의 main 함수가 drawPerson()을 호출하면 하나의 코루틴블럭이 생성된다.
*           startCoroutine {          drawPerson()은 언제든 진입, 탈출할 수 있는 자격이 주어진다.
*
*               drawHead()      -> 2. 코루틴 함수가 실행되는 과정에서 suspend 키워드를 가진 함수를 만나게되면,
*                                     더이상 아래 코드를 실행하지 않고 멈추고(suspend) 코루틴 block 을 탈출 한다.
*               drawBody()
*               DrawLegs()      -> 3. 메인 쓰레드의 다른 코드들이 실행된다. 그러나 Head는 어디선가 계속 그려지고 있다.
*
*                               -> 4. 다른 코드들이 실행되다가도, drawHead 가 끝이 나면 다시 코루틴으로 진입해서 아까 멈춘 부분
*                                     (drawHead) 아래부터 다시 실행된다.
*           }
*       }
*       suspend fun drawHead(){
*           delay(2000)
*           ...
*       }
*       suspend fun drawBody(){
*           delay(5000)
*           ...
*       }
*       suspend fun drawLegs(){
*           delay(3000)
*           ...
*       }
*
*
* 2. 동시성 프로그래밍
*   함수를 중간에 빠져나왔다가, 다른 함수에 진입하고, 다시 원점으로 돌아와 멈추었던 부분부터 다시 시작하는 이특성은 동시성 프로그래밍을
*   가능하게 한다.
*   병렬성 프로그래밍과 완전히 다른 개념이다.
*   예) 양쪽에 놓여진, 두 개의 도화지에 사람 그림을 그린다고 가정한다.
*       오른쪽 손에만 펜을 쥐고서 왼쪽 도화지에 사람 일부를 조금 그리고, 오른쪽 도화지에 가서 잠시 또 사람을 그리고,
*       다시 왼쪽 도화지에 사람을 찔끔 그리고 이 행위를 아주 빨리 반복하는 것
*       사실 내가 쥔 펜은 한순간에 하나의 도화지에만 닿는다. 그러나 이 행위를 멀리서 보면 마치 동시에 그림이 그려지고 있는 것 처럼
*       보일 것이다. 이것이 동시성 프로그래밍이다.
*   병렬성 프로그래밍은 이것과 다르다 병렬성은 실제로 양쪽 손에 펜을 하나씩 들고서 왼쪽과 오른쪽에 실제로 동시에 그리는것.
*   같은 시간동안 두 개의 그림을 그리는 것이다.
*
* 3. 비동기 처리가 이렇게 편해지다니
*       callback
*           fun goCompany(person:Person){
*               val 잠든한강 = person
*
*               wakeUp(잠든한강) { 비몽사몽한한강 ->
*                       takeShower(비몽사몽한한강) { 깨끗한한강 ->
*                           putOnShirt(깨끗한한강){ 옷입은한강 ->
*                               getOnBus(옷입은한강) -> { 버스를탄 한강
*                                   val 출근한한강 = finish(버스를탄 한강)
*                                   출근한한강.dowork()
*                       }
*                       }
*                   }
*               }
*           }
*      }
*
*       RxKotlin
*           fun goCompany(person: Person){
*               val 잠든 한강 = person
*
*               Observable
*                   .just(person)
*                   .observeOn(MAIN_Thread)
*                   .subscribeOn(IO_Thread)
*                   .flatMap{ 잠든한강 -> wakeUp(잠든한강) }
*                   .flatMap{ 비몽사몽한한강 -> takeShower(비몽사몽한한강) }
*                   .flatMap{ 깨끗한한강 -> putOnShirt(깨끗한한강) }
*                   .flatMap{ 옷입은한강 -> getOnBus(옷입은한강) }
*                   .flatMap{ 버스를한강 -> finish(버스를한강) }
*                   .subscribe({ 출근한한강 ->
*                           출근한한강.doWork()
*                   },{
*                       실패했을때처리()
*                   })
*           }
*
*       Kotlin + coroutine
*           suspend goCompany(person: Person){
*               val 잠든한강 = person
*
*               try{
*                   val 비몽사몽한한강 = wakeUp(잠든한강)
*                   val 깨끗한한강 = takeShower(비몽사몽한한강)
*                   val 옷입은한강 = putOnShirt(깨끗한한강)
*                   val 버스를탄한강 = getOnBus(옷입은한강)
*                   val 출근한한강 = finish(버스를탄한강)
*                   출근한한강.doWork()
*               }catch(e: Exception){
*               실패했을때처리()
*               }
*       }
*
* 코루틴을 사용해야하는 이유
*   구글이 안드로이드 공식 언어가 자바 -> 코틀린으로 변경한 후 최근 들어 대표적인 샘프예제들인 blueprint 와
*   sunflower 앱의 비동기처리를 coroutine 으로 바꾸었다.
*   Rx 라이브러리를 걷어내고 코루틴으로 새로 작성한 것이다.
*   이에 더불어 상당히 많은 외국 자료들이 올라고있다.
*   그 이유는 코루틴을 사용하면 비동기 처리가 너무 쉽게 이루어질 수 있기 때문이다.
*
* 코르틴은 제어범위 및 실행범위를 지정할 수 있다. 코루틴의 Scope
*   1. Global Scope
*       프로그램 어디서나 제어, 동작이 가능한 기본 범위
*   2. CoroutineScope
*       특정한 목적의 Dispatcher 를 지정하여 제어 및 동작이 가능한 범위
*           모든 플랫폼에서 지원되는 것은 아님 지원되는 플랫폼에 따라서 사용
*               1. Dispatcher.Default
*                       안드로이드의 기본 스레드풀 사용 CPU 를 많이 쓰는 작업에 최적화 ( 데이터 정렬, 복잡한 연산 등)
*               2. Dispatcher.IO(I/O에 최적화 된 동작)
*                       이미지 다운로드, 파일 입출력 등 입출력에 최적화 되어있는 디스페쳐 (네트워트, 디스크, DB 작업에 적합)
*               3. Dispatcher.Main 메인(UI) 스레드에서 동작
*                       안드로이드의 기본 스레드에서 코루틴 실행 UI 와 상호작용에 최적화
*               4. Dispatcher.Unconfined
*                       호출한 컨텍스트를 기본으로 사용하는데 중단 후 다시 실행될 때 컨텍스트가 바뀌면 바뀐 컨텍스트를 따라
*                       가는 특이한 디스패쳐
*               ※ 디스패처는 코루틴을 적당한 스레드에 할당하며, 코루틴 실행 도중 일시 정지 or 실행 재개를 담당한다
*                 (다음에 어떤 코루틴을 실행 시킬지 결정) 커스텀 스레드풀을 위한 디스패처도 생설할 수도 있다.
*
*
*
*    3. ViewModelScope
*       Jetpack 아키텍쳐의 뷰모델 컴포넌트 사용시 ViewModel 인스턴스에서 사용하기 위해 제공되는 스코프이다.
*       해당 스코프로 실행되는 코루틴은 뷰모델 인스턴스가 소멸될 때 자동으로 취소된다.
*
* Scope 에서 제어되도록 코루틴 생성
*   val scope = CoroutineScope(Dispatcher.Defaunt)
*   val coroutineA = scope.launch {}
*   val coroutineB = scope.async {}
*
* launch vs async
*       코루틴에서의 반환값이 있는지의 여부에 따라 나뉨
*           launch : 반환값이 없는 Job 객체
*           async : 반환갑이 있는 Deffered 객체 (마지막 값 Return)
*           모두 람다 함수의 형태
*       launch{
*           for( i in 1..10){
*               println(i)
*           }
*       }
*
*       async {
*           var sum = 0
*           for(i in 1..10) {
*               sum++
*           }
*           sum
*           }
*
* 루틴의 대기를 위한 추가적 함수들 (delay(), join(), await())
*
*   1. delay( milisecond: Long)
*       밀리세컨드 단위로 루틴을 잠시 대기시키는 함수
*   2. Job.join()
*       Job 의 실행이 끝날때 까지 대기하는 함수
*   3. Deferred.await()
*       Deferred 의 실행이 끝날때까지 대기하는 함수
*       Deferred 의 결과도 반환
*
* 코루틴 실행 도중 중단하는 방법
*   cancel()
*       코루틴에 cancel() 걸어주면 두가지 조건 발생, 코루틴 중단
*           1. 코루틴 내부의 delay() 함수 또는 yield() 함수가 사용된 위치까지 수행된 뒤 종료됨
*           2. cancel() 로 인해 속성인 isActive 가 false 되므로 이를 확인하여 수동으로 종료됨
*
*   withTimeoutOrNull()
*       제한시간 내에 수행되면 결과값을, 아닌경우 null 반환
*           withTimeoutOrNull(50){
*               for(i in 1..1000){
*                   println(i)
*                   delay(10)
*               }
*               "Finish"
*               }
*
*   suspend
*       코루틴 안에서 사용되며 suspend 함수가 호출될 경우 이전까지의 코드의 실행이 멈추며 suspend 함수가 처리가 완료된 후
*       멈춰 있던 원래 스코프의 다음 코드가 실행된다.
*
*   withContext 디스패쳐 분리 사용
*       suspend 함수를 코루틴 스코프에서 사용할 때 호출한 스코프와 다른 디스패쳐를 사용할 때가 있는데, 호출쪽 코루틴은
*       Main 디스페쳐로 UI를 제어, suspend 함수는 파일 io 를 하는 경우 withContext  를 사용하여  suspend 함수의
*       디스페쳐를 IO 로 변경하여 사용할 수 있습니다.
*
*
*
*
*
*
*
* */
