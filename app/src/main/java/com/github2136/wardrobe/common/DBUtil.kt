package com.github2136.wardrobe.common

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.jxgis.forestfiredetection.common.GlideApp
import com.jxgis.forestfiredetection.common.glideOptionsCenterCrop
import java.math.BigDecimal

/**
 * Created by YB on 2020/3/11
 * DataBindingUtil
 */
object DBUtil {

    //加载drawable图片
    @BindingAdapter("android:src")
    @JvmStatic
    fun loadImage(view: ImageView, src: Int) {
        view.setImageResource(src)
    }

    //加载网络图片
    @BindingAdapter("android:src")
    @JvmStatic
    fun loadUrlImage(view: ImageView, url: String?) {
        url?.let {
            GlideApp
                .with(view)
                .load(it)
                .apply(glideOptionsCenterCrop)
                .into(view)
        }
    }

    @InverseMethod("str2int")
    @JvmStatic
    fun int2str(value: Int?): String? {
        return value?.let { BigDecimal(it.toString()).toPlainString() }
    }

    @JvmStatic
    fun str2int(value: String?): Int? {
        return value?.let {
            try {
                it.toInt()
            } catch (e: Exception) {
                null
            }
        }
    }

    @InverseMethod("str2double")
    @JvmStatic
    fun double2str(value: Double?): String? {
        return value?.let { BigDecimal(it.toString()).toPlainString() }
    }

    @JvmStatic
    fun str2double(value: String?): Double? {
        return value?.let {
            try {
                it.toDouble()
            } catch (e: Exception) {
                null
            }
        }
    }

    @InverseMethod("str2float")
    @JvmStatic
    fun float2str(value: Float?): String? {
        return value?.let { BigDecimal(it.toString()).toPlainString() }
    }

    @JvmStatic
    fun str2float(value: String?): Float? {
        return value?.let {
            try {
                it.toFloat()
            } catch (e: Exception) {
                null
            }
        }
    }

    /**
     * TextView
     */
    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Byte?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Short?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Int?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Long?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Float?) {
        view.text = value?.let {
            BigDecimal(it.toString()).toPlainString()
        }
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Double?) {
        view.text = value?.let {
            BigDecimal(it.toString()).toPlainString()
        }
    }

    @JvmStatic
    @BindingAdapter("android:adapter")
    fun <T> autoCompleteAdapter(autoComplete: AutoCompleteTextView, listAdapter: T) where T : ListAdapter, T : Filterable {
        autoComplete.setAdapter(listAdapter)
    }

    @JvmStatic
    @BindingAdapter("android:item_selected")
    fun spinnerItemSelected(spinner: Spinner, l: OnItemSelected?) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                l?.onItemSelected(parent, view, position, id)
            }
        }
    }

    interface OnItemSelected {
        fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    }
}