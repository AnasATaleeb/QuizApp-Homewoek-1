package com.example.quizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.Models.QustionModel;
import com.example.quizapp.R;
import com.example.quizapp.databinding.ActivityQustionBinding;
import com.example.quizapp.databinding.ActivitySetsBinding;

import java.util.ArrayList;

public class QustionActivity extends AppCompatActivity {

    ArrayList<QustionModel> list = new ArrayList<>();
    private int count=0;
    private int position =0;
    private int Score =0;
    CountDownTimer timer;

    ActivityQustionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQustionBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_qustion);
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        restTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if(setName.equals("مجموعة - 1")){
            setOne();
        }else if(setName.equals("مجموعة - 2")){
            setTwo();
        }else if(setName.equals("مجموعة - 3")){
            setThree();
        }else if(setName.equals("مجموعة - 4")){
            setFour();
        }else if(setName.equals("مجموعة - 5")){
            setFive();
        }else if(setName.equals("مجموعة - 6")){
            setSex();
        }else if(setName.equals("مجموعة - 7")){
            setSeven();
        }else if(setName.equals("مجموعة - 8")){
            setEight();
        }else if(setName.equals("مجموعة - 9")){
            setNine();
        }else if(setName.equals("مجموعة - 10")){
            setTwn();
        }


        for (int i = 0; i < 4; i++) {
            binding.OptionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });

        }

        playAnimation(binding.qustion,0,list.get(position).getQustion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timer != null){
                    timer.cancel();
                }
                timer.start();

                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);
                enableOption(true);
                position++;

                if(position == list.size()){
                    Intent intent = new Intent(QustionActivity.this,ScoreActivity.class);
                    intent.putExtra("score",Score);
                    intent.putExtra("total",list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count =0;
                playAnimation(binding.qustion,0,list.get(position).getQustion());
            }
        });

    }



    private void restTimer() {
        timer = new CountDownTimer(30000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.time.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                Dialog dialog =  new Dialog(QustionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QustionActivity.this,SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        };
    }

    private void playAnimation(TextView qustion, int i, String qustion1) {
        qustion.animate().alpha(i).scaleX(i).scaleY(i).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if(i ==0 && count <4 ) {
                            String option = "";

                            if (count == 0) {
                                option  = list.get(position).getOptionA();
                            }else if (count == 1) {
                                option = list.get(position).getOptionB();
                            }else if (count == 2) {
                                option = list.get(position).getOptionC();
                            }else if (count == 3) {
                                option = list.get(position).getOptionD();
                            }

                            playAnimation((TextView) binding.OptionContainer.getChildAt(count),0,option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if(i==0){
                            try{
                                ((TextView)qustion).setText(qustion1);
                                binding.totalQustion.setText(position+1+"/"+list.size());
                            }catch (Exception e){
                                ((Button)qustion).setText(qustion1);
                            }

                            qustion.setTag(qustion1);
                            playAnimation(qustion,1,qustion1);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }

    private void enableOption(boolean b) {
        for (int i = 0; i < 4; i++) {
            binding.OptionContainer.getChildAt(i).setEnabled(b);
            if(b){
                binding.OptionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }


    }

    private void checkAnswer(Button selectedOption) {

        if(timer != null){
            timer.cancel();
        }

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){
            Score++;
            selectedOption.setBackgroundResource(R.drawable.right_ans);
        }
        else {
            selectedOption.setBackgroundResource(R.drawable.wrong_ans);

            Button correctOption = (Button) binding.OptionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.right_ans);
        }
    }

    private void setTwo() {

        list.add(new QustionModel("ما هي الآلات التي تستخدم في معالجة الخشب؟",
                "أ) المثقاب الكهربائي","ب) المنشار الكهربائي","ج) الفرز الكهربائي","د) جميع ما ذكر","د) جميع ما ذكر"));

        list.add(new QustionModel("ما هي الميزة الرئيسية للاستخدامات الصناعية للروبوتات؟",
                "أ) تقليل التكلفة","ب) زيادة الإنتاجية","ج) تحسين الدقة","د) جميع ما ذكر","د) جميع ما ذكر"));


        list.add(new QustionModel("ما هو النظام الذي يستخدمه الحاسوب لتنظيم وحفظ الملفات؟",
                "أ) نظام التشغيل","ب) برنامج الوورد","ج) برنامج الإكسل","د) برنامج الفوتوشوب","أ) نظام التشغيل"));

        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لتحرير وإنتاج الأفلام السينمائية؟",
                "أ) برنامج الفوتوشوب","ب) برنامج الإليستريتور","ج) برنامج الأدوبي بريمير","د) برنامج الإنديزاين","ج) برنامج الأدوبي بريمير"));

        list.add(new QustionModel("ما هي الوحدة الأساسية لقياس الحجم؟",
                "أ) المتر المكعب","ب)  اللتر","ج) الكيلوغرام","د) الجرام","ب)  اللتر"));

        
    }

    private void setOne() {
           list.add(new QustionModel("ما هي الآلة التي تستخدم لقياس قوة الرياح؟",
                "أ) الميزان","ب) الأنيموميتر","ج)  البروميتر","د)  الهيدروميتر","ب) الأنيموميتر"));

        list.add(new QustionModel("من مميزات التطبيقات الهجينة :",
                "عالي التكلفة","يحتاج الى مهارات عالية","تجده في أكثر من متجر","برمجته صعبة","تجده في أكثر من متجر"));


        list.add(new QustionModel("امتداد الملف في نظام IOS ؟",
                "Ipa","Aia","Apk","Xap","Ipa"));

        list.add(new QustionModel("ما هو الجزء المسؤول عن توزيع الطاقة الكهربائية في الحاسوب؟",
                "أ) وحدة المعالجة المركزية","ب) اللوحة الأم","ج)  بطاقة الرسومات","د) مزود الطاقة","د) مزود الطاقة"));

        list.add(new QustionModel("ما هو المصطلح الذي يشير إلى القدرة على استخدام التكنولوجيا لتحليل البيانات واستخلاص المعلومات منها؟",
                "أ) الإنتاجية","ب) البرمجة","ج) التحليل البياني","د) التعاون الإلكتروني","ج) التحليل البياني"));

    }

    private void setThree() {
        list.add(new QustionModel("ما هي الوحدة التي تستخدم لقياس التردد؟",
                "أ) الهيرتز (Hz)","ب) الأمبير (A)","ج) الفولت (V)","د) الواط (W)","أ) الهيرتز (Hz)"));

        list.add(new QustionModel("ما هي الوحدة التي تستخدم لقياس الكهرباء؟",
                "أ) الهيرتز (Hz)","ب) الأمبير (A)","ج) الفولت (V)","د) الواط (W)","ب) الأمبير (A)"));


        list.add(new QustionModel("ما هي الأداة التي تستخدم لتحديد اتجاه القبلة؟",
                "أ) البوصلة","ب) الليزر","ج) الميزان","د) المنظار","أ) البوصلة"));

        list.add(new QustionModel("ما هو النظام الذي يستخدم للتحقق من هوية المستخدم في الحواسيب؟",
                "أ) نظام التشغيل","ب) نظام إدارة قواعد البيانات","ج)  نظام تشفير البيانات","د) نظام تسجيل الدخول","د) نظام تسجيل الدخول"));

        list.add(new QustionModel("ما هي الطريقة التي يتم بها تخزين البيانات في الحواسيب؟",
                "أ)  بالطباعة","ب) على الأشرطة المغناطيسية","ج) على الأقراص المدمجة","د) على الأقراص المرنة","ج) على الأقراص المدمجة"));

    }

    private void setFour() {
        list.add(new QustionModel("ما هي الوحدة التي تستخدم لقياس الشدة الكهربائية؟",
                "أ) الهيرتز (Hz)","ب)  الأمبير (A)","ج) الفولت (V)","د) الواط (W)","ج) الفولت (V)"));

        list.add(new QustionModel("ما هو النظام الذي يستخدم لتخزين البيانات بشكل دائم في الحواسيب؟",
                "أ) الذاكرة العشوائية (RAM)","ب) الذاكرة الخارجية","ج) القرص الصلب (Hard Disk)","د) الذاكرة التخزينية الفلاشية (Flash Memory)","ج) القرص الصلب (Hard Disk)"));


        list.add(new QustionModel("ما هو النظام الذي يستخدم لإدارة وتحليل البيانات في الحواسيب؟",
                "أ) نظام التشغيل","ب) نظام إدارة قواعد البيانات","ج) نظام تشفير البيانات","د) نظام تسجيل الدخول","ب) نظام إدارة قواعد البيانات"));

        list.add(new QustionModel("ما هو النظام الذي يستخدم لتشفير البيانات وحمايتها من الاختراق؟",
                "أ) نظام التشغيل","ب) نظام إدارة قواعد البيانات","ج) نظام تشفير البيانات","د) نقل البيانات بين الحواسيب","ج) نظام تشفير البيانات"));

        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لإدخال البيانات في الحاسوب؟",
                "أ) الشاشة","ب) الطابعة","ج) الفأرة","د) لوحة المفاتيح","د) لوحة المفاتيح"));

    }

    private void setFive() {
        list.add(new QustionModel("ما هي الوحدة التي تستخدم لقياس سرعة المعالجة في الحاسوب؟",
                "أ) البت في الثانية (bps)","ب)  الهيرتز (Hz)","ج) الغيغاهيرتز (GHz)","د) الرامات (RAM)","ج) الغيغاهيرتز (GHz)"));

        list.add(new QustionModel("ما هو البرنامج الذي يتيح للمستخدمين تصفح الإنترنت؟",
                "أ) محرك البحث","ب) متصفح الويب","ج) برنامج البريد الإلكتروني","د) برنامج تحميل الملفات","ب) متصفح الويب"));


        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لتخزين الطاقة الكهربائية في الحواسيب؟",
                "أ) البطارية (Battery)","ب) الشاحن (Charger)","ج)  محول الطاقة (Power Supply Unit)","د) الجهاز المستقل (UPS)","ج)  محول الطاقة (Power Supply Unit)"));

        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لإنشاء الرسوم البيانية والتخطيطات البيانية؟",
                "أ) برنامج معالجة النصوص","ب) برنامج الرسم","ج)  برنامج قواعد البيانات","د) برنامج الجداول الإلكترونية","ب) برنامج الرسم"));

        list.add(new QustionModel("ما هو النظام الذي يستخدم للتواصل بين الأجهزة في شبكة الحاسوب؟",
                "أ) نظام التشغيل","ب) ظام البريد الإلكتروني","ج)  نظام الاتصالات","د) نظام الملفات","ج)  نظام الاتصالات"));

    }


    private void setSex() {
        list.add(new QustionModel("ما هو النظام الذي يستخدم لتخزين البيانات والبرامج في الحاسوب؟",
                "أ) وحدة المعالجة المركزية","ب) الذاكرة العشوائية","ج) الذاكرة الدائمة","د) اللوحة الأم","ج) الذاكرة الدائمة"));

        list.add(new QustionModel("ما هي العناصر التي تشكل مخطط الدائرة الكهربائية؟",
                "أ) المقاومة والكابلات","ب) المصابيح والمحركات","ج) المقاومة والمكثفات والملفات الكهربائية","د) المحولات والبطاريات","ج) المقاومة والمكثفات والملفات الكهربائية"));


        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لتحرير الصور وتعديلها؟",
                "أ) برنامج الرسم","ب) برنامج المعالجة النصية","ج)  برنامج المعالجة الصوتية","د) برنامج التحرير الرقمي للصور","د) برنامج التحرير الرقمي للصور"));

        list.add(new QustionModel("ما هي وظيفة الشاشة في الحاسوب؟",
                "أ) عرض النصوص والصور","ب) إدخال البيانات","ج) تخزين البيانات","د) نقل البيانات بين الحواسيب","أ) عرض النصوص والصور"));

        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لإدخال البيانات في الحاسوب؟",
                "أ) الشاشة","ب) الطابعة","ج) الفأرة","د) لوحة المفاتيح","د) لوحة المفاتيح"));

    }

    private void setSeven() {
        list.add(new QustionModel("ما هو النظام الذي يستخدم لتخزين البيانات والبرامج في الحاسوب؟",
                "أ) وحدة المعالجة المركزية","ب) الذاكرة العشوائية","ج) الذاكرة الدائمة","د) اللوحة الأم","ج) الذاكرة الدائمة"));

        list.add(new QustionModel("ما هي العناصر التي تشكل مخطط الدائرة الكهربائية؟",
                "أ) المقاومة والكابلات","ب) المصابيح والمحركات","ج) المقاومة والمكثفات والملفات الكهربائية","د) المحولات والبطاريات","ج) المقاومة والمكثفات والملفات الكهربائية"));


        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لتحرير الصور وتعديلها؟",
                "أ) برنامج الرسم","ب) برنامج المعالجة النصية","ج)  برنامج المعالجة الصوتية","د) برنامج التحرير الرقمي للصور","د) برنامج التحرير الرقمي للصور"));

        list.add(new QustionModel("ما هي وظيفة الشاشة في الحاسوب؟",
                "أ) عرض النصوص والصور","ب) إدخال البيانات","ج) تخزين البيانات","د) نقل البيانات بين الحواسيب","أ) عرض النصوص والصور"));

        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لإدخال البيانات في الحاسوب؟",
                "أ) الشاشة","ب) الطابعة","ج) الفأرة","د) لوحة المفاتيح","د) لوحة المفاتيح"));

    }

    private void setEight() {
        list.add(new QustionModel("ما هو النظام الذي يستخدم لتخزين البيانات والبرامج في الحاسوب؟",
                "أ) وحدة المعالجة المركزية","ب) الذاكرة العشوائية","ج) الذاكرة الدائمة","د) اللوحة الأم","ج) الذاكرة الدائمة"));

        list.add(new QustionModel("ما هي العناصر التي تشكل مخطط الدائرة الكهربائية؟",
                "أ) المقاومة والكابلات","ب) المصابيح والمحركات","ج) المقاومة والمكثفات والملفات الكهربائية","د) المحولات والبطاريات","ج) المقاومة والمكثفات والملفات الكهربائية"));


        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لتحرير الصور وتعديلها؟",
                "أ) برنامج الرسم","ب) برنامج المعالجة النصية","ج)  برنامج المعالجة الصوتية","د) برنامج التحرير الرقمي للصور","د) برنامج التحرير الرقمي للصور"));

        list.add(new QustionModel("ما هي وظيفة الشاشة في الحاسوب؟",
                "أ) عرض النصوص والصور","ب) إدخال البيانات","ج) تخزين البيانات","د) نقل البيانات بين الحواسيب","أ) عرض النصوص والصور"));

        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لإدخال البيانات في الحاسوب؟",
                "أ) الشاشة","ب) الطابعة","ج) الفأرة","د) لوحة المفاتيح","د) لوحة المفاتيح"));

    }


    private void setNine() {
        list.add(new QustionModel("ما هو النظام الذي يستخدم لتخزين البيانات والبرامج في الحاسوب؟",
                "أ) وحدة المعالجة المركزية","ب) الذاكرة العشوائية","ج) الذاكرة الدائمة","د) اللوحة الأم","ج) الذاكرة الدائمة"));

        list.add(new QustionModel("ما هي العناصر التي تشكل مخطط الدائرة الكهربائية؟",
                "أ) المقاومة والكابلات","ب) المصابيح والمحركات","ج) المقاومة والمكثفات والملفات الكهربائية","د) المحولات والبطاريات","ج) المقاومة والمكثفات والملفات الكهربائية"));


        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لتحرير الصور وتعديلها؟",
                "أ) برنامج الرسم","ب) برنامج المعالجة النصية","ج)  برنامج المعالجة الصوتية","د) برنامج التحرير الرقمي للصور","د) برنامج التحرير الرقمي للصور"));

        list.add(new QustionModel("ما هي وظيفة الشاشة في الحاسوب؟",
                "أ) عرض النصوص والصور","ب) إدخال البيانات","ج) تخزين البيانات","د) نقل البيانات بين الحواسيب","أ) عرض النصوص والصور"));

        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لإدخال البيانات في الحاسوب؟",
                "أ) الشاشة","ب) الطابعة","ج) الفأرة","د) لوحة المفاتيح","د) لوحة المفاتيح"));

    }

    private void setTwn() {
        list.add(new QustionModel("ما هو النظام الذي يستخدم لتخزين البيانات والبرامج في الحاسوب؟",
                "أ) وحدة المعالجة المركزية","ب) الذاكرة العشوائية","ج) الذاكرة الدائمة","د) اللوحة الأم","ج) الذاكرة الدائمة"));

        list.add(new QustionModel("ما هي العناصر التي تشكل مخطط الدائرة الكهربائية؟",
                "أ) المقاومة والكابلات","ب) المصابيح والمحركات","ج) المقاومة والمكثفات والملفات الكهربائية","د) المحولات والبطاريات","ج) المقاومة والمكثفات والملفات الكهربائية"));


        list.add(new QustionModel("ما هو البرنامج الذي يستخدم لتحرير الصور وتعديلها؟",
                "أ) برنامج الرسم","ب) برنامج المعالجة النصية","ج)  برنامج المعالجة الصوتية","د) برنامج التحرير الرقمي للصور","د) برنامج التحرير الرقمي للصور"));

        list.add(new QustionModel("ما هي وظيفة الشاشة في الحاسوب؟",
                "أ) عرض النصوص والصور","ب) إدخال البيانات","ج) تخزين البيانات","د) نقل البيانات بين الحواسيب","أ) عرض النصوص والصور"));

        list.add(new QustionModel("ما هو الجهاز الذي يستخدم لإدخال البيانات في الحاسوب؟",
                "أ) الشاشة","ب) الطابعة","ج) الفأرة","د) لوحة المفاتيح","د) لوحة المفاتيح"));

    }




}