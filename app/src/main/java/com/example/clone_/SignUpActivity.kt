package com.example.clone_

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clone_.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SignUpActivity  : AppCompatActivity(),SignUpView {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
        }
    }


    // 이메일과 비밀번호 가져오기
    private fun getUser(): User {
        val email: String =
            binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val password: String = binding.signUpPasswordEt.text.toString()
        var name: String = binding.signUpNameEt.text.toString()

        return User(email, password, name)
    }


    // 회원가입
//    private fun signUp(){
//        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }

//        //정보db저장
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val user = userDB.userDao().getUser()
//        Log.d("SIGNUPACT", user.toString())
//    }


    private fun signUp() {
        if (binding.signUpIdEt.text.toString()
                .isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }


        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }


//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.singUp(getUser()).enqueue(object: retrofit2.Callback<AuthResponse> {
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("SIGNUP.SUCCESS",response.toString())
//                val resp:AuthResponse = response.body()!!
//                when(resp.code){
//                    1000->finish()
//                    2016, 2018 ->{
//                        binding.signUpEmailErrorTv.visibility= View.VISIBLE
//                        binding.signUpEmailErrorTv.text = resp.message
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("SIGNUP.FAILURE", t.message.toString())
//            }
//
//        })
//
//        Log.d("SIGUP.SUCCESS","HELLO")

        val authService = AuthService()
        authService.setSignUpView(this)


        authService.signUp(getUser())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        TODO("Not yet implemented")
    }
}