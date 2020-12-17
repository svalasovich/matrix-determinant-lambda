package com.matrix.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.security.SecureRandom

class MatrixHandler: RequestHandler<Int, Pair<Array<IntArray>, Int>> {

    override fun handleRequest(size: Int, context: Context): Pair<Array<IntArray>, Int> {
        if (size < 2) {
            throw NumberFormatException("Please enter other size")
        }
        val matrix = generateMatrix(size)
        return matrix to determinantOfMatrix(matrix, size, size)
    }

    private fun generateMatrix(size: Int): Array<IntArray> {
        val random = SecureRandom()
        return (0..size)
            .map { (0..size).map { random.nextInt(Int.MAX_VALUE/2) }.toIntArray() }
            .toTypedArray()
    }

    private fun determinantOfMatrix(mat: Array<IntArray>, n: Int, generalN: Int): Int {
        var d = 0
        if (n == 1) return mat[0][0]
        val temp = Array(generalN) { IntArray(generalN) }
        var sign = 1
        for (f in 0 until n) {
            getCofactor(mat, temp, 0, f, n)
            d += (sign * mat[0][f] * determinantOfMatrix(temp, n - 1, generalN))
            sign = -sign
        }
        return d
    }

    private fun getCofactor(mat: Array<IntArray>, temp: Array<IntArray>, p: Int, q: Int, n: Int) {
        var i = 0
        var j = 0
        for (row in 0 until n) {
            for (col in 0 until n) {
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col]
                    if (j == n - 1) {
                        j = 0
                        i++
                    }
                }
            }
        }
    }
}