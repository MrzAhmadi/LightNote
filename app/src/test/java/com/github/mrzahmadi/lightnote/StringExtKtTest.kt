package com.github.mrzahmadi.lightnote

import com.github.mrzahmadi.lightnote.utils.ext.removeEmptyLines
import com.github.mrzahmadi.lightnote.utils.ext.removeWhitespaces
import org.junit.Assert
import org.junit.Test

class StringExtKtTest {

    @Test
    fun removeWhiteSpaceTest() {
        Assert.assertEquals(" ".removeWhitespaces(), "")
        Assert.assertEquals("A ".removeWhitespaces(), "A")
        Assert.assertEquals(" A".removeWhitespaces(), "A")
        Assert.assertEquals(" A ".removeWhitespaces(), "A")
        Assert.assertEquals("This is a Text".removeWhitespaces(), "ThisisaText")
    }


    @Test
    fun removeEmptyLinesTest() {
        Assert.assertEquals("\n".removeEmptyLines(), "")
        Assert.assertEquals(" \n ".removeEmptyLines(), "  ")
        val textWithManyEmptyLines = """
Hello,

this is a

string with


empty lines.
"""
        val textWithoutEmptyLine = "Hello,this is astring withempty lines."
        Assert.assertEquals(textWithManyEmptyLines.removeEmptyLines(), textWithoutEmptyLine)
    }

}