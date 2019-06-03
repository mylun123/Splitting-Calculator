package com.example.myapplication

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.widget.TextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import kotlinx.android.synthetic.main.content_main.*
import kotlin.math.roundToInt


class InsertPartyName : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_party_name)

        // Get the Intent that started this activity and extract the string
        val partyNumString = intent.getStringExtra("Party Num")
        val partyNum = Integer.parseInt(partyNumString)
        val mLayout = findViewById<ConstraintLayout>(R.id.mLayout)
        val viewArray = IntArray(partyNum * 2)
        val editTextArray = arrayOfNulls<EditText>(partyNum)
        val set = ConstraintSet()
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        val chainWeights = FloatArray((partyNum * 2))
        val chainArray = IntArray((partyNum * 2)- 2)
        var usableHeight = ((screenHeight - 280) * .8).roundToInt()
        if(partyNum > 4)
        {
            usableHeight += (usableHeight * .25f).roundToInt() * (partyNum - 4)
        }

        mLayout.minHeight = usableHeight
        for (i in 0..partyNum - 1)
        {
            val mTextView = TextView(this)
            val mEditText = EditText(this)
            mTextView.id = (ViewCompat.generateViewId())
            mTextView.text = "Party Member #" + (i + 1)
            mEditText.hint = "Name"
            mEditText.gravity = Gravity.CENTER_HORIZONTAL
            mEditText.id = (ViewCompat.generateViewId())
            viewArray[2*i] = mTextView.id
            viewArray[2*i+1] = mEditText.id
            editTextArray[i] = mEditText
            //mTextView.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
            mLayout.addView(mTextView)
            mLayout.addView(mEditText)

            set.constrainHeight(mTextView.id,
                ConstraintSet.WRAP_CONTENT)
            set.constrainWidth(mTextView.id,
                ConstraintSet.WRAP_CONTENT)

            set.constrainHeight(mEditText.id,
                160)
            set.constrainWidth(mEditText.id,
                (screenWidth * .75).roundToInt())

            set.connect(mTextView.id, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
            set.connect(mTextView.id, ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)

            set.connect(mEditText.id, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
            set.connect(mEditText.id, ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)

            set.connect(mTextView.id, ConstraintSet.BOTTOM,
                mEditText.id, ConstraintSet.TOP)
            if(i == 0)
            {
                set.connect(mTextView.id, ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            }
            else
            {
                set.connect(mTextView.id, ConstraintSet.TOP,
                    viewArray[2 * i - 1]!!, ConstraintSet.BOTTOM)
            }
            if(i == partyNum - 1)
            {
                set.connect(mEditText.id, ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }
            //set.setVerticalBias(mTextView.id, .5f)

            chainWeights[2*i] = 0f
            chainWeights[2*i+1] = 0f
        }


        set.createVerticalChain(ConstraintSet.PARENT_ID,ConstraintSet.TOP,
                                ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,
                                //viewArray[2 * partyNum - 1],ConstraintSet.BOTTOM,
                                viewArray,chainWeights,ConstraintSet.CHAIN_SPREAD)
        set.applyTo(mLayout)

    }


}
