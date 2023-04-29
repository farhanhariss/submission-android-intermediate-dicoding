package com.example.submissionapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.submissionapp.R

class Button : AppCompatButton {

    private lateinit var enabledBackground : Drawable
    private lateinit var disabledBackground : Drawable
    private var txtColor: Int = 0

    //===========================
    //Bikin tiga karena kebutuhan masing masing piranti berbeda
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    //=========================

    //untuk memasukkan background dan attribute lain pada MyButton.
    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground

        setTextColor(txtColor)
        textSize = 12f
        text = if (isEnabled) "Submit" else "Isi dulu"
    }

    private fun init(){
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_enabled) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disabled) as Drawable
    }
}