package kr.easw.lesson06application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestMainActivity extends AppCompatActivity {
    private EditText componentText;

    private AppCompatTextView componentErrorText;

    private Button componentSendButton;

    private RequestQueue requestQueue;


    private String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 뷰를 test_main으로 설정하고,
        setContentView(R.layout.test_main);
        // 이전 액티비티에서 제공한 토큰을 캐시합니다.
        token = getIntent().getStringExtra("token");
        // 또한, 새 리퀘스트 큐를 생성합니다.
        requestQueue = Volley.newRequestQueue(this);
        // 컴포넌트를 초기화하고,
        initComponents();
        // 리스너를 추가합니다.
        addListener();
    }

    private void initComponents() {
        componentText = findViewById(R.id.textToAdd);
        componentSendButton = findViewById(R.id.sendText);
    }

    private void addListener() {
        // 전송 버튼이 눌리면
        componentSendButton.setOnClickListener(event -> {
            // 텍스트 필드와 버튼을 비활성화하고.
            componentSendButton.setEnabled(false);
            componentText.setEnabled(false);
            Toast.makeText(this, "서버와 연결하는 중..", Toast.LENGTH_SHORT).show();
            try {
                // 파라미터를 구성한 후
                JSONObject object = new JSONObject().put("text", componentText.getText().toString());
                // 요청을 보냅니다.
                requestQueue.add(new JsonObjectRequest(Request.Method.POST, Constants.SERVER_URL + Constants.TEXT_ADD_ENDPOINT, object, (Response.Listener<JSONObject>) response -> {
                    // 만약 성공했다면, 버튼을 활성화시키고, 텍스트 필드를 비웁니다.
                    Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                    componentSendButton.setEnabled(true);
                    componentText.setEnabled(true);
                    componentText.setText("");
                }, error -> {
                    // 실패했다면, 스택 트레이스를 출력합니다.
                    error.printStackTrace();
                    // 만약 네트워크 응답이 존재한다면, 인증 실패로 간주합니다.
                    if (error.networkResponse != null) {
                        Toast.makeText(this, "계정 인증 실패", Toast.LENGTH_SHORT).show();
                    } else {
                        // 아니라면, 서버 연결 실패로 간주합니다.
                        Toast.makeText(this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                    // 나머지 버튼을 활성화합니다.
                    componentSendButton.setEnabled(true);
                    componentText.setEnabled(true);
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        // JWT 인증을 사용하기 위해 헤더에 토큰을 보내고, 컨텐츠 타입을 json으로 지정해 서버에 전달합니다.
                        Map<String, String> map = new HashMap<>();
                        map.put("Authorization", token);
                        map.put("Content-Type", "application/json");
                        return map;
                    }

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        // API의 응답이 존재하지 않기에, 코드로 직접 분류합니다.
                        // 200이면 성공임으로, 새 JSONObject를 돌려줍니다.
                        if (response.statusCode == 200) {
                            return Response.success(new JSONObject(), null);
                        } else {
                            // 아니라면, 오류를 반환합니다.
                            return Response.error(new VolleyError());
                        }
                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
