import org.junit.Assert
import kotlin.test.Test
class HomeWork1Test {

    @Test
    fun mapListToNamesTest() {
        val input = listOf(
            mapOf(
                "first" to "Иван",
                "middle" to "Васильевич",
                "last" to "Рюриков",
            ),
            mapOf(
                "first" to "Петька",
            ),
            mapOf(
                "first" to "Сергей",
                "last" to "Королев",
            ),
        )
        val expected = listOf(
            "Рюриков Иван Васильевич",
            "Петька",
            "Королев Сергей",
        )
        val res = mapListToNames(input)
        Assert.assertEquals(expected, res)
    }

    private fun mapListToNames(input: List<Map<String, String>>): List<String> {
        return input.map {
            nameConvert(it)
        }
    }

    private fun nameConvert(fullNameMap: Map<String, String>): String {
        return when(fullNameMap.size) {
            3 -> "${fullNameMap["last"]} ${fullNameMap["first"]} ${fullNameMap["middle"]}"
            2 -> "${fullNameMap["last"]} ${fullNameMap["first"]}"
            1 -> "${fullNameMap["first"]}"
            else -> "wrong input. Try again"
        }
    }
}