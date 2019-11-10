package lesson3

import java.util.*
import kotlin.test.*

abstract class AbstractHeadTailTest {
    private lateinit var tree: SortedSet<Int>
    private lateinit var randomTree: SortedSet<Int>
    private val randomTreeSize = 1000
    private val randomValues = mutableListOf<Int>()

    protected fun fillTree(create: () -> SortedSet<Int>) {
        this.tree = create()
        //В произвольном порядке добавим числа от 1 до 10
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)

        this.randomTree = create()
        val random = Random()
        for (i in 0 until randomTreeSize) {
            val randomValue = random.nextInt(randomTreeSize) + 1
            if (randomTree.add(randomValue)) {
                randomValues.add(randomValue)
            }
        }
    }


    protected fun doHeadSetTest() {
        var set: SortedSet<Int> = tree.headSet(5)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))


        set = tree.headSet(127)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

        set = tree.headSet(-10)
        for (i in 1..10)
            assertEquals(false, set.contains(i))
    }

    protected fun doTailSetTest() {
        var set: SortedSet<Int> = tree.tailSet(5)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))

        set = tree.tailSet(-128)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

        set = tree.tailSet(128)
        for (i in 1..10)
            assertEquals(false, set.contains(i))

    }

    protected fun doHeadSetRelationTest() {
        var set: SortedSet<Int> = tree.headSet(7)
        val e = IllegalArgumentException()
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        set = tree.headSet(7)
        assertTrue(set.contains(0))
        set.add(-2)
        tree.add(-2)
        assertTrue(tree.contains(-2))
        tree.add(12)
        assertFalse(set.contains(12))
        assertFailsWith<IllegalArgumentException> { if (!tree.add(8)) throw e }
        assertEquals(8, set.size)
        assertEquals(13, tree.size)
        tree.remove(0)
        set = tree.headSet(7)
        assertFalse(set.contains(0))
        assertEquals(7, set.size)
        assertEquals(12, tree.size)

        set = tree.headSet(100)
        for (i in 1..10)
            assertEquals(true, set.contains(i))
        assertEquals(true, set.contains(-2))
        assertEquals(true, set.contains(12))
        assertEquals(12, set.size)

        set = tree.headSet(-100)
        for (i in 1..10)
            assertEquals(false, set.contains(i))
        assertEquals(false, set.contains(-2))
        assertEquals(false, set.contains(12))
        assertEquals(0, set.size)
    }

    protected fun doTailSetRelationTest() {
        var set: SortedSet<Int> = tree.tailSet(4)
        val e = IllegalArgumentException()
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        set = tree.tailSet(4)
        assertTrue(set.contains(12))
        set.add(42)
        tree.add(42)
        assertTrue(set.contains(42))
        tree.add(0)
        set = tree.tailSet(4)
        assertFalse(set.contains(0))
        tree.add(-2)
        assertFailsWith<IllegalArgumentException> { if (!tree.add(-2)) throw e }
        tree.remove(-2)
        assertEquals(9, set.size)
        assertEquals(13, tree.size)
        tree.remove(42)
        set = tree.tailSet(4)
        assertFalse(set.contains(42))
        assertEquals(8, set.size)
        assertEquals(12, tree.size)

        set = tree.tailSet(-100)
        for (i in 0..10)
            assertEquals(true, set.contains(i))
        assertEquals(true, set.contains(12))
        assertEquals(12, set.size)

        set = tree.tailSet(100)
        for (i in 0..10)
            assertEquals(false, set.contains(i))
        assertEquals(false, set.contains(12))
        assertEquals(0, set.size)
    }

    protected fun doSubSetTest() {
        var smallSet: SortedSet<Int> = tree.subSet(3, 8)
        val e = IllegalArgumentException()
        assertEquals(false, smallSet.contains(1))
        assertEquals(false, smallSet.contains(2))
        assertEquals(true, smallSet.contains(3))
        assertEquals(true, smallSet.contains(4))
        assertEquals(true, smallSet.contains(5))
        assertEquals(true, smallSet.contains(6))
        assertEquals(true, smallSet.contains(7))
        assertEquals(false, smallSet.contains(8))
        assertEquals(false, smallSet.contains(9))
        assertEquals(false, smallSet.contains(10))

        assertFailsWith<IllegalArgumentException> { if (!tree.add(2)) throw e }
        assertFailsWith<IllegalArgumentException> { if (!tree.add(9)) throw e }

        val allSet = tree.subSet(-128, 128)
        for (i in 1..10)
            assertEquals(true, allSet.contains(i))

        val random = Random()
        val toElement = random.nextInt(randomTreeSize) + 1
        val fromElement = random.nextInt(toElement - 1) + 1

        val randomSubset = randomTree.subSet(fromElement, toElement)
        randomValues.forEach { element ->
            assertEquals(element in fromElement until toElement, randomSubset.contains(element))
        }

        smallSet = tree.subSet(0, 0)
        for (i in 1..10)
            assertEquals(false, smallSet.contains(i))
        smallSet = tree.subSet(1, 2)
        assertEquals(true, smallSet.contains(1))
        assertEquals(false, smallSet.contains(2))
    }

    protected fun doSubSetRelationTest() {
        var set: SortedSet<Int> = tree.subSet(2, 15)
        val e = IllegalArgumentException()
        assertEquals(9, set.size)
        assertEquals(10, tree.size)
        tree.add(11)
        set = tree.subSet(2, 15)
        assertTrue(set.contains(11))
        tree.add(14)
        assertTrue(tree.contains(14))
        tree.add(0)
        set = tree.subSet(2, 15)
        assertFalse(set.contains(0))
        tree.add(15)
        set = tree.subSet(2, 15)
        assertFalse(set.contains(15))
        assertFailsWith<IllegalArgumentException> { if (!tree.add(1)) throw e }
        tree.add(20)
        assertFailsWith<IllegalArgumentException> { if (!tree.add(20)) throw e }
        tree.remove(20)
        assertEquals(11, set.size)
        assertEquals(14, tree.size)

        tree.remove(14)
        tree.remove(15)
        set = tree.subSet(2, 15)
        assertFalse(set.contains(14))
        assertFalse(set.contains(15))
        for (i in 12..10000){
            tree.add(i)
        }

        set = tree.subSet(9000, 10000)
        for (i in 0..8999) {
            assertEquals(false, set.contains(i))
        }
        assertEquals(false, set.contains(10000))
        for (i in 9000..9999) {
            assertEquals(true, set.contains(i))
        }
    }

}