package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {
    private EditText editText;//这些都是默认类
    private Button button;
    private ListView bookListView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 调用父类的创建方法
        setContentView(R.layout.layout2); // ✅ 加载你定义的 XML 布局文件（activity_main.xml）
        Log.d("来吧",  "msg");

        editText=findViewById(R.id.editText);//获取
        button= findViewById(R.id.addButton);

        dbHelper = new DatabaseHelper(this);

        button.setOnClickListener(v -> {
            String title = editText.getText().toString().trim();
            if (!title.isEmpty()) {
                boolean success = dbHelper.addBook(title, "未知作者", "未知出版社");
                if (success) {
                    Toast.makeText(AddBookActivity.this, "书籍添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddBookActivity.this, "书籍添加失败", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddBookActivity.this, "请输入书名", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
