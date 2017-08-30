package rikao.bwiei.com.yangguodong1507c20170830;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.bean.bean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    //获取控件
    @ViewInject(R.id.lv_list)
    ListView lv_list;
    //适配器
    private Myadapter ma;
    private List<bean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(MainActivity.this);


        initPost();
        ma = new Myadapter();

    }

    private void initPost() {

        //获取网络数据
        RequestParams params = new RequestParams("http://v.juhe.cn/toutiao/index?&key=22a108244dbb8d1f49967cd74a0c144d&type=yule");
        x.http().post(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                //成功得到数据并解析
                try {
                    JSONObject obj = new JSONObject(result);


                    JSONObject result1 = obj.getJSONObject("result");
                    JSONArray data = result1.getJSONArray("data");
                    for (int i = 0; i <data.length() ; i++) {

                        JSONObject array = (JSONObject) data.get(i);
                        bean b = new bean();
                        b.title = array.getString("title");
                        b.author_name = array.getString("author_name");
                        b.date = array.getString("date");
                        b.thumbnail_pic_s = array.getString("thumbnail_pic_s");
                        list.add(b);
                        System.out.println(b.title);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                lv_list.setAdapter(ma);


            }
        });


    }



    class  Myadapter extends BaseAdapter{


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            //将解析到的数据展示到listview上
            view = View.inflate(MainActivity.this,R.layout.item,null);
            TextView te_date = view.findViewById(R.id.te_date);
            TextView te_name = view.findViewById(R.id.te_name);
            TextView te_title = view.findViewById(R.id.te_title);
            ImageView te_img = view.findViewById(R.id.te_img);


            te_date.setText(list.get(i).date);
            te_name.setText(list.get(i).author_name);
            te_title.setText(list.get(i).title);

            //ImageLoader获取图片
            ImageLoaderConfiguration con = new ImageLoaderConfiguration.Builder(MainActivity.this).build();
            ImageLoader.getInstance().init(con);
            ImageLoader.getInstance().displayImage(list.get(i).thumbnail_pic_s,te_img);

            return view;
        }
    }
}
