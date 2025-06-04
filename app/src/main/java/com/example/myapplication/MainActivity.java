package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText,editText2;//这些都是默认类
    private Button button;
    private ListView bookListView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Bundle是页面的容器，暂时也不清楚
        super.onCreate(savedInstanceState); // 调用父类的创建方法
        setContentView(R.layout.activity_main); // ✅ 加载你定义的 XML 布局文件（activity_main.xml）
        Log.d("来吧",  "msg");

        editText=findViewById(R.id.editText);//获取几个对象的值
        editText2=findViewById(R.id.editText2);
        button= findViewById(R.id.addButton);
        bookListView=findViewById(R.id.bookListView);
        dbHelper = new DatabaseHelper(this);

        button.setOnClickListener(v -> {
            String title = editText.getText().toString().trim();
            if (!title.isEmpty()) {
                boolean success = dbHelper.addBook(title, "未知作者", "未知出版社");
                if (success) {
                    Toast.makeText(MainActivity.this, "书籍添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "书籍添加失败", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "请输入书名", Toast.LENGTH_SHORT).show();
            }
        });

        //监听编辑栏
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本变化前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本变化时
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本变化后
                // 比如把输入内容全部转成大写
                String upper = s.toString().toUpperCase();
                editText.removeTextChangedListener(this); // 避免死循环
                editText.setText(upper);
                editText.setSelection(upper.length()); // 光标移到最后
                editText.addTextChangedListener(this);
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本变化前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本变化时
                Log.d("调试", "当前内容：" + s);
                Log.d("调试", "起始位置：" + start);
                Log.d("调试", "删除字符数：" + before);
                Log.d("调试", "新增字符数：" + count);
                filterBook(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本变化后
            }
        });

        dbHelper = new DatabaseHelper(this);
        loadBooks();
    }

    private void loadBooks() {
        List<String> books = dbHelper.getAllBooks();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        bookListView.setAdapter(adapter);
    }

    private void filterBook(String query) {
        List<String> allBooks = dbHelper.getAllBooks();
        List<String> filteredBooks = new ArrayList<>();
        for (String book : allBooks) {
            if (book.toLowerCase().contains(query.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredBooks);
        bookListView.setAdapter(adapter);
    }
}