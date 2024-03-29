package kr.co.kadb.cameralibrarykotlinexample

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import kr.co.kadb.cameralibrary.presentation.CameraIntent
import kr.co.kadb.cameralibrary.presentation.model.CropSize
import kr.co.kadb.cameralibrary.presentation.widget.util.IntentKey
import kr.co.kadb.cameralibrary.presentation.widget.util.UriHelper

class MainActivity : AppCompatActivity() {
    // Crop Size.
    private val cropSize = CropSize(0.7f, 0.5f)

    // Activity for result.
    // Example 2. 3. 의 결과 수신.
    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        val intent = result.data
        if (result.resultCode == RESULT_OK) {
            // 한 장, 여러 장.
            when (intent?.action) {
                IntentKey.ACTION_TAKE_PICTURE -> {
                    // 한장.
                    // 이미지 URI.
                    val imageUri = intent.data ?: return@registerForActivityResult
                    // 이미지 가로.
                    //val imageWidth = intent.getIntExtra(IntentKey.EXTRA_WIDTH, 0)
                    // 이미지 세로.
                    //val imageHeight = intent.getIntExtra(IntentKey.EXTRA_HEIGHT, 0)
                    // 이미지 방향.
                    //val imageRotation = intent.getIntExtra(IntentKey.EXTRA_ROTATION, 0)
                    // 썸네임 이미지.
                    //val thumbnailBitmap = intent.extras?.get("data") as? Bitmap

                    // Uri를 이미지로 변환.
                    val bitmap = UriHelper.toBitmap(baseContext, imageUri)

                    // 이미지 중앙을 기준으로 원본 사이즈에서 가로:70% 세로:50% 크롭.
                    /*val bitmap = UriHelper.rotateAndCenterCrop(
                        baseContext, imageUri, cropSize.width, cropSize.height
                    )*/

                    // 가로, 세로 중 큰 길이를 640(pixel)에 맞춰 비율 축소.
                    //val resizeBitmap = BitmapHelper.resize(bitmap, 640)
                    // 가로, 세로 중 큰 길이를 640(pixel)에 가깝게(640이상 ~ 1280미만) 맞춰 비율 축소.
                    // 예) resizePixcel이 640인 경우 결과는 640이상 ~ 1280미만.
                    // 성능 및 좋은 샘플링으로 이미지를 추출.
                    //val resizeBitmap = BitmapHelper.optimumResize(bitmap, 640)
                    bitmap?.recycle()

                    // Bitmap 저장.
                    // isPublicDirectory: true: 공용저장소, false: 개별저장소.
                    /*val resizeImageUri = BitmapHelper.save(
                        baseContext, resizeBitmap, true
                    )*/

                    // Base64로 인코딩 된 문자열 반환.
                    /*val base64 = BitmapHelper.toBase64(resizeBitmap)
                    findViewById<TextView>(R.id.textview).text = base64*/

                    // 촬영 원본 이미지.
                    findViewById<ImageView>(R.id.imageview).setImageURI(imageUri)
                    // 촬영 원본을 크롭 및 리사이즈한 이미지.
                    //findViewById<ImageView>(R.id.imageview_thumbnail).setImageBitmap(resizeBitmap)
                }
                IntentKey.ACTION_TAKE_MULTIPLE_PICTURES -> {
                    // 여러장.
                }
                IntentKey.ACTION_DETECT_MILEAGE_IN_PICTURES, IntentKey.ACTION_DETECT_VIN_NUMBER_IN_PICTURES -> {
                    // 감지한 텍스트.
                    val detectText = intent.getStringExtra(IntentKey.EXTRA_DETECT_TEXT)
                    findViewById<TextView>(R.id.textview).text = detectText
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 한장 촬영.
        findViewById<Button>(R.id.button_one_shoot).setOnClickListener {
            // Example 1.(권장되지 않음)
            /*Intent(IntentKey.ACTION_TAKE_PICTURE).also { takePictureIntent ->
                // 단말 음소거 시 셔터 무음.
                takePictureIntent.putExtra(IntentKey.EXTRA_CAN_MUTE, false)
                // 미리보기 화면에 수평선 표시.
                takePictureIntent.putExtra(IntentKey.EXTRA_HAS_HORIZON, true)
                // 크롭 영역 설정(Deprecated).
                //takePictureIntent.putExtra(IntentKey.EXTRA_CROP_PERCENT, cropPercent)
                // 크롭 영역 설정.
                takePictureIntent.putExtra(IntentKey.EXTRA_CROP_SIZE, cropSize)
                // 회전 가능.
                takePictureIntent.putExtra(IntentKey.EXTRA_CAN_UI_ROTATION, true)
                // 수평선 색상.
                takePictureIntent.putExtra(IntentKey.EXTRA_HORIZON_COLOR, Color.RED)
                // 크롭 인 라인 색상.
                takePictureIntent.putExtra(IntentKey.EXTRA_CROP_BORDER_COLOR, Color.GREEN)
                // 크롭 영역 설정 시 원본 대신 크롭 된 사진 저장 후 반환.
                takePictureIntent.putExtra(IntentKey.EXTRA_IS_SAVE_CROPPED_IMAGE, false)
            }.run {
                startActivityForResult(this, 0)
            }*/

            // Example 2.
            /*Intent(IntentKey.ACTION_TAKE_PICTURE).also { takePictureIntent ->
                // 단말 음소거 시 셔터 무음.
                takePictureIntent.putExtra(IntentKey.EXTRA_CAN_MUTE, false)
                // 미리보기 화면에 수평선 표시.
                takePictureIntent.putExtra(IntentKey.EXTRA_HAS_HORIZON, true)
                // 크롭 영역 설정(Deprecated).
                //takePictureIntent.putExtra(IntentKey.EXTRA_CROP_PERCENT, cropPercent)
                // 크롭 영역 설정.
                takePictureIntent.putExtra(IntentKey.EXTRA_CROP_SIZE, cropSize)
                // 회전 가능.
                takePictureIntent.putExtra(IntentKey.EXTRA_CAN_UI_ROTATION, true)
                // 수평선 색상.
                takePictureIntent.putExtra(IntentKey.EXTRA_HORIZON_COLOR, Color.RED)
                // 크롭 인 라인 색상.
                takePictureIntent.putExtra(IntentKey.EXTRA_CROP_BORDER_COLOR, Color.GREEN)
                // 크롭 영역 설정 시 원본 대신 크롭 된 사진 저장 후 반환.
                takePictureIntent.putExtra(IntentKey.EXTRA_IS_SAVE_CROPPED_IMAGE, false)
            }.run {
                resultLauncher.launch(this)
            }*/

            // Example 3.
            CameraIntent.Build(this).apply {
                setAction(IntentKey.ACTION_TAKE_PICTURE)
                setCanMute(false) // 단말 음소거 시 셔터 무음.
                setHasHorizon(true) // 미리보기 화면에 수평선 표시.
                //setCropPercent(cropPercent) // 크롭 영역 설정(Deprecated).
                setCropSize(cropSize) // 크롭 영역 설정.
                setCanUiRotation(true) // 회전 가능.
                setHorizonColor(Color.RED) // 수평선 색상.
                setUnusedAreaBorderColor(Color.GREEN) // 크롭 인 라인 색상.
                setSaveCropedImage(false) // 크롭 영역 설정 시 원본 대신 크롭 된 사진 저장 후 반환.
            }.run {
                resultLauncher.launch(this.build())
            }
        }

        // 여러장 촬영.
        findViewById<Button>(R.id.button_multiple_shoot).setOnClickListener {
            // 한 장 촬영 시 사용 된 구성 요소 사용 가능.
            CameraIntent.Build(this).apply {
                setAction(IntentKey.ACTION_TAKE_MULTIPLE_PICTURES) // 여러 장.
            }.run {
                resultLauncher.launch(this.build())
            }
        }

        // 주행거리 촬영.
        // 크롭 모드 사용 불가.
        findViewById<Button>(R.id.button_mileage_shoot).setOnClickListener {
            CameraIntent.Build(this).apply {
                setAction(IntentKey.ACTION_DETECT_MILEAGE_IN_PICTURES)
                setCanMute(false)
                setHasHorizon(true)
                setCanUiRotation(true)
                setHorizonColor(Color.RED)
            }.run {
                resultLauncher.launch(this.build())
            }
        }

        // 차대번호 촬영.
        // 크롭 모드 사용 불가.
        findViewById<Button>(R.id.button_vin_number_shoot).setOnClickListener {
            CameraIntent.Build(this).apply {
                setAction(IntentKey.ACTION_DETECT_VIN_NUMBER_IN_PICTURES)
                setCanMute(false)
                setHasHorizon(true)
                setCanUiRotation(true)
                setHorizonColor(Color.RED)
            }.run {
                resultLauncher.launch(this.build())
            }
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "androidx.appcompat.app.AppCompatActivity"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Example 1. 결과 수신.(권장되지 않음)
    }
}