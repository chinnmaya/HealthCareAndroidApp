package com.example.healthcare.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.healthcare.APICalls.API
import com.example.healthcare.R
import com.example.healthcare.dialoughs.DoctorsListDialog
import com.example.healthcare.models.Doctor
import com.example.healthcare.models.User
import com.example.healthcare.utils.BaseActivity
import com.example.healthcare.utils.Constants.doctorlist
import kotlinx.android.synthetic.main.activity_diease_detection.*
import kotlinx.android.synthetic.main.dialog_list.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.channels.FileChannel

class DieaseDetectionActivity : BaseActivity() {


    lateinit var bitmap: Bitmap
    private lateinit var mSharedPrefernces: SharedPreferences
    lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diease_detection)
        val data = intent.getIntExtra("number",1)
        mSharedPrefernces = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        var email=mSharedPrefernces.getString("email","")
        API().getUserbyId(email!!){user->
            mUser=user
        }


        predict.setOnClickListener {
            showProgressDialog("wait..")
            API().getUserbyId(email!!){user->
                mUser=user
            }

            if (email != null) {
                try {
                    API().getMoney(email) { money ->
                        if (money < 20) {
                            showErrorSnackBar("You dont have enough money")

                        } else {
                            mUser.money=mUser.money-20
                            API().updateMoney(mUser){}
                            if (bitmap != null) {
                                if (data == 1) {
                                    detectmodel1("model") { dieasename ->
                                        doctorsListDialog(dieasename)
                                    }
                                }
                                if (data == 2) {
                                    detectmodel2("TumorModel") { dieasename ->
                                        Toast.makeText(this, dieasename, Toast.LENGTH_SHORT).show()
                                        doctorsListDialog(dieasename)

                                    }
                                }
                                //doctorsListDialog()
                            }
                        }
                        hideProgressDialog()
                    }

                }catch (e:Exception){
                    showProgressDialog("Something went wrong")
                    hideProgressDialog()
                }
            }
        }
        upload.setOnClickListener {
            var intent:Intent= Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent,100)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100){
            var uri=data?.data
            bitmap= MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            upload.setImageBitmap(bitmap)
        }

    }
    /**
     * A function to add some static label colors in the list.
     */
    private fun doctorsListDialog(diease:String) {

        API().getdocotorlistbycategory(this@DieaseDetectionActivity,diease){List ->
            if (List != null) {
                val listDialog = object : DoctorsListDialog(
                    this@DieaseDetectionActivity,
                    List,
                    diease

                ) {
                    override fun onItemSelected(doctor: Doctor) {

                    }
                }
                listDialog.show()
            }else{
                nodoctors.visibility=View.VISIBLE
                rvList.visibility=View.GONE

            }
        }
    }
    fun opendilogwindow(doctorList:ArrayList<Doctor>,diease: String){
        var msg="You have affected by ${diease}, Here's is the list of preferrable Doctor "
        if(diease=="Normal"){
            msg="Don't worry you have detected as NORMAL"
            nodoctors.visibility=View.GONE
            rvList.visibility=View.GONE

        }
        if(doctorList.size==0){
            nodoctors.visibility=View.VISIBLE
        }
    }
    fun detectmodel1(modelname: String, callback: (String) -> Unit) {
        // image processor
        var imageProcessor =
            ImageProcessor.Builder().add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR)).build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        // val model = Model.newInstance(this)
        val modelName = "model.tflite"
        val modelFileDescriptor = assets.openFd(modelName)
        val model =
            Interpreter(
                modelFileDescriptor
                    .createInputStream()
                    .channel
                    .map(
                        FileChannel.MapMode.READ_ONLY,
                        modelFileDescriptor.startOffset,
                        modelFileDescriptor.declaredLength
                    )
            )
        // Prepare input buffer.
        val inputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        inputBuffer.loadBuffer(tensorImage.buffer)
        // Prepare output buffer.
        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 256), DataType.FLOAT32)

        // Run inference.
        model.run(inputBuffer.buffer, outputBuffer.buffer)

        // Get the output as an array.
        val outputFeature0 = outputBuffer.floatArray

        // Releases model resources if no longer used.
        model.close()


        if (outputFeature0[0] > outputFeature0[1]) {

            callback("Normal")
             Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
        } else {

            callback("Phenuomia")

            Toast.makeText(this, "Phenuomia", Toast.LENGTH_LONG).show()
        }

        // Releases model resources if no longer used.
        model.close()
    }
    fun detectmodel2(modelname: String, callback: (String) -> Unit) {
        // image processor
        var imageProcessor =
            ImageProcessor.Builder().add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR)).build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        // val model = Model.newInstance(this)
        val modelName = "TumorModel.tflite"
        val modelFileDescriptor = assets.openFd(modelName)
        val model =
            Interpreter(
                modelFileDescriptor
                    .createInputStream()
                    .channel
                    .map(
                        FileChannel.MapMode.READ_ONLY,
                        modelFileDescriptor.startOffset,
                        modelFileDescriptor.declaredLength
                    )
            )
        // Prepare input buffer.
        val inputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        inputBuffer.loadBuffer(tensorImage.buffer)

        // Prepare output buffer.
        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 256), DataType.FLOAT32)

        // Run inference.
        model.run(inputBuffer.buffer, outputBuffer.buffer)

        // Get the output as an array.
        val outputFeature0 = outputBuffer.intArray

        // Releases model resources if no longer used.
        model.close()


        if (outputFeature0[0] > outputFeature0[1]) {

            callback("Normal")
            // Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
        } else {

            callback("Tumor")

            //Toast.makeText(this, "Phenuomia", Toast.LENGTH_LONG).show()
        }

        // Releases model resources if no longer used.
        model.close()
    }

}