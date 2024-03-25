package kr.easw.lesson06application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText componentId;

    private EditText componentPassword;

    private AppCompatTextView componentErrorText;

    private Button componentLoginButton;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 뷰를 activity_main으로 설정합니다.
        setContentView(R.layout.activity_main);
        // 새 리퀘스트 큐를 생성합니다.
        requestQueue = Volley.newRequestQueue(this);
        // 컴포넌트를 초기화합니다.
        initComponents();
        // 리스너를 초기화합니다.
        initListeners();
    }

    private void initComponents() {
        componentId = findViewById(R.id.user_id);
        componentPassword = findViewById(R.id.user_password);
        componentErrorText = findViewById(R.id.error_text);
        componentLoginButton = findViewById(R.id.login);
    }

    private void initListeners() {
        // 만약 로그인 버튼을 누르면,
        componentLoginButton.setOnClickListener(event -> {
            // 로그인 버튼을 비활성화하고,
            componentLoginButton.setEnabled(false);
            Toast.makeText(this, "서버와 연결하는 중..", Toast.LENGTH_SHORT).show();
            try {
                // 파라미터를 작성한 후
                JSONObject object = new JSONObject().put("userId", componentId.getText().toString()).put("password", componentPassword.getText().toString());
                // 큐에 요청을 추가합니다.
                requestQueue.add(new JsonObjectRequest(Request.Method.POST, Constants.SERVER_URL + Constants.LOGIN_ENDPOINT, object, (Response.Listener<JSONObject>) response -> {
                    // 만약 성공했다면, 새 인텐트를 생성하고
                    Intent intent = new Intent(this, TestMainActivity.class);
                    try {
                        // 인텐트에 토큰 정보를 담은 다음
                        intent.putExtra("token", response.getString("token"));
                        // 새 액티비티를 시작합니다.
                        startActivity(intent);
                        // 이전에 사용하던 큐는 자원 정리를 위해 정지하고,
                        requestQueue.stop();
                        // 액티비티를 종료합니다.
                        finish();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
                    // 만약 오류가 발생했다면, 스택을 출력하고
                    error.printStackTrace();
                    // 네트워크 응답이 존재한다면 연결 성공이기 떄문에
                    if (error.networkResponse != null) {
                        // 계정 인증 실패로 간주합니다.
                        componentErrorText.setVisibility(View.VISIBLE);
                        componentErrorText.setText("계정 인증 실패");
                    } else {
                        // 아니라면, 서버 연결 실패로 간주합니다.
                        componentErrorText.setVisibility(View.VISIBLE);
                        componentErrorText.setText("서버 연결 실패");
                    }
                    // 로그인 버튼을 다시 활성화시킵니다.
                    componentLoginButton.setEnabled(true);
                }));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}