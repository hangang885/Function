package com.example.myapplication

class DiClass {
}

//  DI
//  의존성 주입
//  의존성이란 A 라는 객체(클래스)가 B라는 객체(클래스)를 사용한다는 의미로 이해하자

//class SomeDataRepository{
//    private val source: SomeDataSource = SomeDataSource()
//}
//class SomeDataSource{
//
//}

//  위 내용은 SomeDataRepository 내부에 SomeDataSource 객체를 생성하여 갖고있는것이다.
//  즉 위 내용은 SomeDataRepository 객체는 SomeDataSource 객체에 의존성을 갖고 있는 것이다.

//class SomeDataRepository(private val source: SomeDataSource) {
//}
//
//class SomeDataSource {
//
//}
//val someDataSource = SomeDataSource()
//val someDataRepository = SomeDataRepository(someDataSource)

//  변경된 부분은 클래스 내부에서 생성하는 SomeDataSource 객체의 위치를 생성자로 이동시킨 것이다.
//  이 경우 SomeDataRepository 를 생성하기 위해선 외부에서 생성자에 SomeDataSource 를 생성하여 넣어주어야 한다.
//  위와 같은 것을 "의존성 주입" 이라 한다.


class SomeDataRepository(private val source: SomeDataSource) {
    fun loadData(): SomeData? = source.loadData()

    fun saveData() {
        source.saveData(null)
    }
}

data class SomeData(
    val data: String
)

interface SomeDataSource {
    fun loadData(): SomeData?
    fun saveData(data: SomeData?)
}

class SomeRemoteDataSourceImpl : SomeDataSource {
    override fun loadData(): SomeData? = null

    override fun saveData(data: SomeData?) {
    }

}

class SomeLocalDataSourceImpl : SomeDataSource {
    override fun loadData(): SomeData? = null

    override fun saveData(data: SomeData?) {
    }

}