package com.example.myapplication.dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import javax.inject.Inject

class Dagger2Activity : AppCompatActivity() {

    @Inject
    lateinit var kitchen: Kitchen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger2)


        val myComponent: MyComponent = DaggerMyComponent.builder().build()
        myComponent.inject(this)

        isOrder()

    }

    private fun isOrder() {
        val isOrder = kitchen.isOrder()
        if (isOrder) {
            Log.d("MyTag", "order successful. ")
        } else {
            Log.d("MyTag", "order failed. ")
        }
    }
}

//Dagger
//  DI 를 도와주는 DI Framework 의미

//Dagger 5가지 필수 개념
// Inject
// Component
// SubComponent
// Module
// Scope


//Inject
// 필드, 생성자, 메서드 에 붙여 Component 로 부터 의존성 객체를 주입 요청한느 annotation
// @Inject 로 의존성 주입을 요청하면 연결된 Component 가 Module 로부터 객체를 생성하여 넘겨준다
// Component 는 @Inject 어노테이션을 의존성 주입할 멤버 변수와 생성자에 달아줌으로 DI 대상을 확인 가능
// 객체(인스턴스)의 생성이 클래스에서 이루어지지 않고, Component 가 생성해주기에 BoilerPlateCode(보일러 플레이트 코드) 를 작성할 필요없이
// 클래스를 테스트하기 쉬워짐

//class AA()
//
//class BB(val aa: AA)
//
//// 생성자에 @Inject 를 사용해서 CC Type 의 인스턴스를 주입하는 방식
//class CC @Inject constructor(val aa: AA, val bb: BB)
//
//@Inject
//lateinit var aa: AA // @Inject 로 필드에 의존성 주입을 하는 방식
//
//@Inject
//lateinit var bb: BB
//
//@Inject
//lateinit var cc: CC


// 기본적으로 Dagger 는 요청된 Type(자료형)에 맞게 Component 가 Module 로 부터 객체를 생성하여 주입한다.
// 단, @Inject 가 어디에서나 사용이 가능한 것은 아니며, @Inject 를 붇일 수 없는 경우는 다음과 같다
// 1. Interface 는 생성자 개념이 없으므로 불가능
// 2. 써드파티 라이브러리 등의 클래스는 참조가 불가능하여 annotation 프로세싱이 불가능

//Module
//  Component 에 연결되어 의존성 객체를 생성하는 역할이다. 생성 후 Scope 에 따라 객체를 관리하기도 한다.
//  @Module 은 클래스에만 붙이고, @Provides 는 반드시 @Module 클래스에 선언된 메서드에만 사용한다.
//  Module 클래스는 의존성 주입에 필요한 객체들을 @Provide @Binds 메서드를 통해 관리 합니다
//  @Provides @Binds 메서드의 파라미터 또한 Component 에게 객체를 전달받고 Component 에게 제공한다.

//@Module
//class Module_A {
//    @Provides
//    fun provideAA(): AA = AA() // AA 객체(인스턴스)를 Component 에게 제공
//
//    @Provides
//    fun provideBB(aa: AA): BB =
//        BB(aa) // 필요한 인자(AA)를 Component 로 부터 전달받아 BB 객체를 생성해서  Component 에게 제공
//}

// Dagger 는 기본적으로 Null 을 인젝션하는 것을 금지합니다. Null 을 인젝션하고 싶다면 @Nullable 애노테이션을
// @Provide 메서드와 객체 주입받을 타입에 모두 @Nullable 애노테이션을 사용할 경우에만 Null 주입을 허용하고, 그 이외의
// 경우에는 컴파일 에러를 발생한다.

//Component
//  @Component 는 Interface 또는 abstract class 에서만 사용이 가능하다
//  컴파일 타입에는 접두어 'Dagger' 와 Component 클래스 이름이 합쳐진 Dagger 클래스 자동 생성
//  ex : @Component interface MyComponent -> DaggerMyComponent 클래스 생성
//  연결된 Module 을 이용하여 의존성 객체를 생성하고, Inject 로 요청 받은 인스턴스에 생성한 객체를 전달(주입) 한다.
//  의존성을 요청 받고 전달(주입) 하는 Dagger 의 주된 역할을 수행하는 부분이다

//Component Methods
//  @Component 애노테이션이 달린 Interface 또는 abstract class 는 반드시 1개 이상의 abstract-method 가 있어야 한다.
//  Component Method 유형에는 Provision 메서드 + Member-Injection 메서드로 구분된다.

//Component Methods 유형
//@Component(modules = [Module_A, Module_B::class])
//interface MyComponent {
//
//    // provision 메서드 유형
//    fun makeAA(): AA
//
//    // Member-Injection 메서드 유형 - 인자로 받은 MainActivity 내부 멤버필드에 의존성 주입
//    fun inject(mainActivity: MainActivity)
//}

//  1. Provision Method
//      Provision 메서드 유형은 인자 (매개변수)가 없고, Module 이 제공하는 객체의 타입을 반환형으로 갖는다
//      생성된 Component 클래스에서 Provision 메서드를 통해 객체를 얻을 수 있다.

//  2. Member-Injection Method
//      의존성을 주입시킬 객체를 메서드의 파라미터로 넘기는 방식으로, Member-Injection 메서드를 호출하면 인자(매개변수)로 받은
//      타켓 클래스 내부 @Inject 가 붙은 필드에 객체를 주입한다.

//  +@Component.Builder
//  Component 인스턴스를 생성하기 위한 Builder 용 annotation 이다. Module 에 초기설정을 한다거나 할 때 사용한다.
//  Component 내의 interface 또는 abstract class 에 붙여서 사용하며, 다음과 같이 Builder 를 정의한다.
//
//
//@Component(modules = [Moduel_A::class, Module_B::class])
//interface MyComponent {
//
//    @Component.Builder
//    interface Builder {
//
//        fun moduleA(moduleA: Module_A): Builder
//
//        fun moduleB(moduleB: Module_B): Builder
//
//        fun build(): MyComponent
//    }
//    ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
// 사용방법
//
//    DaggerMyComponent.builder()
//    .moduleA(Module_A())
//    .moduleB(Module_B())
//    .build()
//  Builder 는 반드시 Component 를 반환하는 메서드 여기선 build() 와 Builder 를 반환 하면서 Component 가 필요로 하는
//  Module 을 파라미터로 받는 메서드(여기선 moduleA())를 가지고 있어야한다.

//  Builder 를 반환하며 Module 을 파라미터로 받는 메섣느느 무조건 1개의 파라미터 만 받는 추상 메서드로 구성해야 한다.
//  파리미터(인자)를 2개 이상 선언할 수 없기 떄문에, 여러 Module 을 초기화 하려면 각 Module 마다 메서드를 추가해서 메서드 체이닝
//  방식으로 Component 인스턴스를 생성해야 한다.

//  @Component.Factory
//      dagger-android 2.22 버전 부터 추가된 Factory annotation 이다.
//      Builder 와 의미는 동일하지만 사용방법이 조금 다른 형태이다. Builder 는 파라미터를 1개 만 받으면 여러
//      Module 을 설정해야 한다면 각각 메서드를 따로 선언해서 메서드 체이닝이 길어지는 점을 보완한 Factory 이다.
//      Factory 는 단 하나의 메서드(create()) 만 선언되어야 하고 반환타입 은 Component 인스턴스여야 한다.

//@Component(modules = [Moduel_A::class, Module_B::class])
//interface MyComponent {
//
//    @Component.Factory
//    interface Factory {
//
//        fun create(moduleA: Module_A, moduleB: Module_B) : MyComponent
//    }
//    ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//// 사용방법
//
//    DaggerMyComponent.factory()
//    .create(Module_A(), Module_B())

//      Builder 를 사용할 때는 각 메서드마다 호출해서 메서드 체이닝 으로 Component 인스턴스를 생성하기 때문에
//      코드가 길어지고, 빼먹을 실수가 발생하는데 Factory 는 create() 메서드 하나로 설정이 가능하기 떄문에 코드의 간결화가 구현된다.

//  SubComponent
//      dagger2 에서는 SubComponent 를 생성할 수 있다. SubComponent 는 말 그대로 부모 Component 가 있는 자식 Component 라고 보면 된다.
//      Component 는 SubComponent 로 계층관계를 만들 수 있다. inner Class 방식의 하위계층 Component 를 의미하고 연속된 Sub 의
//      Sub 도 구현이 가능하다. SubComponent 는 Dagger 의 중요한 컨셉인 그래프를 형성하는 역할이다.
//      Inject 로 의존성 주입을 요청 받으면 SubComponent 에서 먼저 의존성을 검색 하고, 없으면 부모로 올라가면서 검색한다.
//      SubComponent 는 Component 와 달리 코드 생성은 부모 Component 에서 이루어 진다.
//      SubComponent 는 Component 와 마찬가지로 interface 또는 abstract class 에 @SubComponent 에노테이션으로 생성

//  Scope
//      dagger2 는 Scope 를 지원한다. Component 별로 @Scope annotation 으로 주입되는 객체들을 관리한다.
//      생성된 객체의 Lifecycle 범위이다.  안드로이드는 주로 PerActivity, PerFragment 등으로 하면의 생명주기와 맞춰서 사용한다.
//      Module 에서 Scope 를 보고 객체를 관리하는 방식이다.






