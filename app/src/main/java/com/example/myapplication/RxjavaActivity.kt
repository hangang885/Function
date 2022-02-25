package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class RxjavaActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)

//        val source = Observable.create { emitter: ObservableEmitter<String?> ->
//            emitter.onNext("Hello")
//            emitter.onNext("Yena")
//            emitter.onComplete()
//        }
//        source.subscribe { x: String? -> println(x) }

//        val source = Observable.create { emitter: ObservableEmitter<String?> ->
//            emitter.onNext("Hello")
//            emitter.onError(Throwable())
//            emitter.onNext("Yena")
//        }
//        source.subscribe(
//            { x: String? -> println(x) },
//            { throwable: Throwable? ->
//                println(
//                    "Good bye"
//                )
//            }
//        )

//        var source = Observable.just("Hello", "Yena")
//        source.subscribe { x: String? -> println(x) }

//        val itemArray = arrayOf("Morning", "Afternoon", "Evening")
//        val source: Observable<*> = Observable.fromArray(*itemArray)
//        source.subscribe { x: Any -> println(x) }

//        val itemList = ArrayList<String>()
//        itemList.add("Morning")
//        itemList.add("Afternoon")
//        itemList.add("Evening")
//        val source: Observable<*> = Observable.fromIterable<Any>(itemList)
//        source.subscribe { x: Any -> println(x) }

//        val publisher: Publisher<String> = Publisher<String> { Subscriber ->
//            Subscriber.onNext("Morning")
//            Subscriber.onNext("Afternoon")
//            Subscriber.onNext("Evening")
//            Subscriber.onComplete()
//        }
//        val source: Observable<String> = Observable.fromPublisher(publisher)
//        source.subscribe { x: String? -> println(x) }

//        val callable: Callable<String> = Callable<String> { "RxJava is cool" }
//        val source: Observable<*> = Observable.fromCallable<Any>(callable)
//        source.subscribe { x: Any -> println(x) }

        Single.create<String> { emitter -> emitter.onSuccess("Hello") }
            .subscribe(System.out::println)

        Completable.create { emitter ->
            println("OK")
            emitter.onComplete()
        }.subscribe { println() }

    }


}

// RxJava

//  RxAndroid
//  - RxJava 에 안드로이드용 스케쥴러 등 몇 가지 클래스를 추가해 안드로이드 개발을 쉽게 해주는 라이브러리

//  RxJava
//  - ReactiveX(Reactive Extensions)를 Java 로 구현한 라이브러리

//  ReactiveX 는 관찰 가능한(Observable) 스트림을 사용하는 비동기 프로그래밍을 위한 API 이다

//  Reactive Programming
//  - 데이터 흐름과 변화의 전파와 관련있는 선언적 프로그래밍 패러다임

//  Observable
//  RxJava 에서는 Observable 를 구독하는 Observer 이 존재하고 Observable 이 순차적으로 발행하는 데이터에 대해서
//  반응한다. Observable 은 다음의 3가지 이벤트를 사용하여 동작한다.
//  onNext() : 하나의 소스 Observable 에서 Observable 까지 한 번에 하나씩 순차적으로 데이터를 발행
//  onComplete() : 데이터 발행이 끝났음을 알리는 완료 이벤트를 Observer 에 전달하여 onNext() 를 더 호출하지 않음을 나타냄
//  onError() : 오류가 발생했음을 Observer 에 전달한다.
//  위 이벤트들은 Emitter 라는 인터페이스에 의해 선언된다.






