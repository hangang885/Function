package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        sort()
        sortBy()
        sortWith()
        comparison()
        string()
    }

    fun sort(){
        //sort 기본적으로오름차순으로정렬
        val list = mutableListOf(1, 2, 7, 6, 5, 6)
        list.sort()
        println(list) // 1, 2, 5, 6, 6, 7


        // sort 메소드는 해당 Collection 의 원소 위치가  변경된다.
        // 기존 Collection 은 그대로 둔 채 새로운 Collection 으로 받기 원한다면 sorted 메소드를 사용해야 한다.
        // sorted 메소드를 사용하면 기존 Collection 은 변하지 않는다.
        var list2 = mutableListOf(1, 2, 7, 6, 5, 6)
        var sorted = list2.sorted()
        println(sorted)// 1, 2, 5, 6, 6, 7
        println(list2)// 1, 2, 7, 6, 5, 6

        // 내림차순으로 정렬하고 싶다면 sortByDescending 를 사용하거나 reverse 메소드를 사용하면 된다.
        // sortByDescending 를 사용하면 원래 Collection 의 변경 없이 내림차순으로 정렬된 값을 구할 수있습니다.
        list.sortByDescending { it }

        var sorted2 = list.sortedByDescending { it }

        // reverse 사용해서 정렬 후 뒤집기
        list.sort()
        list.reverse()

        val sorted3 = list.sorted().reversed()

    }

    fun sortBy(){
        // Object 의 특징 Property 들을 기준으로 정렬하고 싶다면 sortBy 를 사용하면 된다.
        // sortBy 메소드는 Object 를 받아서 Property 를 반환하는 람다식을 파라미터로 받는다.
        // sort 와 마찬가지로 기존 Collection 의 변경 없이 정렬된 값을 받고 싶다면 sortedBy 를 사용하면 됩니다.
        // 그리고 내림차순을 지원하는 sortByDescending 도 있다.
        val list = mutableListOf( 1 to "a", 2 to "b", 7 to "c", 6 to "d", 5 to "c", 6 to "e")
        list.sortBy { it.second }
        println(list) // [(1, a), (2, b), (7, c), (5, c), (6, d), (6, e)]
    }

    fun sortWith(){
        //sortWith 메소드를 사용하면 여러 가지 조건을 섞어서 정렬할 수 있다.
        //sortWith 메소드는 Comparator 를 파라미터로 받습니다.

        //Collection 은 it.second(문자)로 먼저 정렬된 후에 it.first(숫자)로 정렬됩니다.
        //그리고 역시 sortedWith 메소드가 존재하며, 역 순으로 정렬할 때는 reverse 를 사용하거나 Comparator 를 반대로 수정하면 됩니다.
        val list = mutableListOf( 1 to "a", 2 to "b", 7 to "c", 6 to "d", 5 to "c", 6 to "e")
        list.sortWith(compareBy({it.second}, {it.first}))
        println(list)
    }

    fun comparison(){
        // 가장 간단한 생성 메서드 아무런 파라미터를 필요로 하지 않으며, 오름차순을 기본으로 한다.
        val ascComparator = naturalOrder<Long>()


        // 여러 개의 속성을 사용하고싶다면 compareBy 메소드를 사용하면 된다.
        // 파라미터로는 Comparable 를 리턴하는 정렬  규칙을 여러 개 사용할 수 있다.
        // 그럼 넘겨진 규칙들은 순차적으로 호출되며, 원소들을 정렬한다.
        // 만약 먼저 나온 규칙에서 원소의 우열이 가려져 정렬 처리가 되었다면 뒤의 규칙들은 확인하지 않는다.
        val complexComparator = compareBy<Pair<Int, String?>>({it.first}, {it.second})

        // 간단하게 new Comparator 선언해서 만들 수 있다.
        // 자바와 마찬가지로 두 원소에 대한 비교 조건을 넣어줘야 한다.
        val comparator = Comparator<Int> { a, b -> a.compareTo(b)}

        // 정렬하려는 Collection 이 null 값을 갖고 있을 수도 있습니다.
        // nullsFirst 또는 nullsLast 와 함께 Comparator 를 사용하면 null 값을 가장 처음 또는 가장 마지막에 위치하도록 설정 할 수 있다.
        val list = mutableListOf(4, null, 1, -2, 3)
        list.sortWith(nullsFirst()) // null, -2, 1, 3, 4
        list.sortWith(nullsLast()) // -2, 1, 3, 4, null
        list.sortWith(nullsFirst(reverseOrder())) // null, 4, 3, 1, -2
        list.sortWith(nullsLast(compareBy{ it })) // -2, 1, 3, 4, null

        // Comparator 오브젝트는 추가적인 정렬 규칙과 혼합되거나 확장할 수 있습니다.
        // kotlin.comparable 패키지에 있는 then 키워드를 활용하면 됩니다.
        // 첫 번째 비교의 결과가 동일할 때만 두 번째 비교가 이뤄집니다.

        val students = mutableListOf( 21 to "Helen", 21 to "Tom", 20 to "Jim")

        val ageComparator = compareBy<Pair<Int, String?>>{ it.first}
        val ageAndNameComparator = ageComparator.thenByDescending { it.second }

        // {20, Jim}, {21, Tom}, {21, Helen}
        println(students.sortWith(ageAndNameComparator))
    }

    fun string(){

        // drop : 앞에서부터 n 개의 문자를 제거한 String 을 반환
        // dropLast : 뒤에서부터 n 개의 문자를 제거
        // dropWhile, dropLastWhile : 조건을 만족하지 않는 문자가 나올때까지 제거

//        /* definition */
//        fun String.drop(n: Int): String
//
//        fun String.dropLast(n: Int): String
//
//        fun String.dropWhile(
//            predicate: (Char) -> Boolean
//        ): String
//
//        fun String.dropLastWhile(
//            predicate: (Char) -> Boolean
//        ): String

        val string = "<<<First Grade>>>"

        println(string.drop(6)) // st Grade>>>
        println(string.dropLast(6)) // <<<First Gr
        println(string.dropWhile { !it.isLetter() }) // First Grade>>>
        println(string.dropLastWhile { !it.isLetter() }) // <<<First Grade

    }
}