package com.example.gradient

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(Test(applicationContext))

    }
    inner class Test(context : Context) : View(context){
        var p = Paint(Paint.ANTI_ALIAS_FLAG)
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            var lg = LinearGradient(0f,0f,45f,45f,Color.RED,Color.GREEN,Shader.TileMode.MIRROR)
            p.shader = lg
            canvas?.drawCircle(250f,250f,200f,p)

            var rg = RadialGradient(200f,500f,200f,Color.CYAN,Color.MAGENTA,Shader.TileMode.MIRROR)
            p.shader = rg
            canvas?.drawCircle(250f,750f,200f,p)

            var sg = SweepGradient(250f,1200f, intArrayOf(Color.YELLOW,Color.RED,Color.BLACK),null)
            p.shader = sg
            canvas?.drawCircle(250f,1200f,200f,p)

        }
    }

}