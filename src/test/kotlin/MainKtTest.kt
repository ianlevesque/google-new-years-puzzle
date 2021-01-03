import org.junit.Assert.*

import org.junit.Test

class MainKtTest {
    @Test
    fun encrypt() {
        val output = encrypt("anagram")
        assertEquals("nr\$aaagm", output)
    }

    @Test
    fun rotationsOf() {
        val rotations = arrayOf(
            "anagram\$",
            "nagram\$a",
            "agram\$an",
            "gram\$ana",
            "ram\$anag",
            "am\$anagr",
            "m\$anagra",
            "\$anagram"
        )

        assertArrayEquals(rotations, rotationsOf("anagram$"));
    }

    @Test
    fun sorting() {
        val rotations = arrayOf(
            "anagram\$",
            "nagram\$a",
            "agram\$an",
            "gram\$ana",
            "ram\$anag",
            "am\$anagr",
            "m\$anagra",
            "\$anagram"
        )

        val sorted = arrayOf(
            "agram\$an",
            "am\$anagr",
            "anagram\$",
            "gram\$ana",
            "m\$anagra",
            "nagram\$a",
            "ram\$anag",
            "\$anagram"
        )

        assertArrayEquals(sorted, sorted(rotations))
    }

    @Test
    fun cracking() {
        assertEquals("anagram", crackPassword("nr\$aaagm"))
    }

    @Test
    fun decrypting() {
        assertEquals("anagram", decrypt("nr\$aaagm"))
    }

    @Test
    fun rotateArray() {
        val array = "elloH".toCharArray();
        rotateArray(array);
        assertArrayEquals("Hello".toCharArray(), array);
    }
}
