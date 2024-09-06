package com.myproject;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TabHost;
import androidx.appcompat.app.AlertDialog;

import android.widget.ListView;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Kiosk extends AppCompatActivity {
    private int cnt = 0;
    private final int rowCount = 0;
    private ScrollView tab1, tab2, tab3, tab4, tab5;
    private ImageView iv1;
    private TextView optv1;
    private TextView optv2;
    private TextView tottv_cnt;
    private TextView tottv_price;
    private TextView rantv;
    private TextView payprice;
    private TextView paycount;
    private TextView paypoint;
    private TextView complete;
    private TableLayout tl;

    //main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kioskmain);
    }
    //메뉴 생성자 layout\menu\kiosk_menu.xml (수정)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kiosk_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            setContentView(R.layout.config);
            //config 옵션의 수정
            String[] option = {"매장 메뉴 작성", "매장 메뉴 삭제", "매장 메뉴 수정","포장 메뉴 작성","포장 메뉴 삭제",
                    "포장 메뉴 수정","메인으로 돌아가기"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, option);
            ListView lv = findViewById(R.id.lv_1);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                    if (position == 0) {
                        //setContentView(R.layout.1);
                    } else if (position == 1) {
                        //setContentView(R.layout.2);
                    } else if (position == 2) {
                        //setContentView(R.layout.3);
                    } else if (position == 3) {
                        //setContentView(R.layout.4);
                    } else if (position == 4) {
                        //setContentView(R.layout.5);
                    } else if (position == 5) {
                        //setContentView(R.layout.6);
                    } else if (position == 6) {
                        setContentView(R.layout.kioskmain);
                    }
                }
            });
            return true;
        } else if (id == R.id.item2) {
            return true;
        } else if (id == R.id.item3) {
            return true;
        } else if (id == R.id.item4) {
            return true;
        } else if (id == R.id.item5) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void bt_back(View v) {setContentView(R.layout.kioskmain);}

    //store1 (in_store)
    public void bt_in(View v) {
        setContentView(R.layout.store1);
        tab1 = findViewById(R.id.sv1);
        tab2 = findViewById(R.id.sv2);
        tab3 = findViewById(R.id.sv3);
        tab4 = findViewById(R.id.sv4);
        tab5 = findViewById(R.id.sv5);
    }
    public void tabbt(View v) {
        tab1.setVisibility(View.INVISIBLE);
        tab2.setVisibility(View.INVISIBLE);
        tab3.setVisibility(View.INVISIBLE);
        tab4.setVisibility(View.INVISIBLE);
        tab5.setVisibility(View.INVISIBLE);

        if (v.getId() == R.id.tabbt_1) {
            tab1.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_2) {
            tab2.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_3) {
            tab3.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_4) {
            tab4.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_5) {
            tab5.setVisibility(View.VISIBLE);
        }
    }
    public void stbt_2(View v) {
        setContentView(R.layout.store1);
        tab1 = findViewById(R.id.sv1);
        tab2 = findViewById(R.id.sv2);
        tab3 = findViewById(R.id.sv3);
        tab4 = findViewById(R.id.sv4);
        tab5 = findViewById(R.id.sv5);
    }
    // 이미지버튼 주문명령 처리부분(커피탭)(논커피탭)
    public void order_coffee(View v) {
        final int[] priceint = new int[1];
        final String[] selectedName = new String[1];
        final Dialog cd_option = new Dialog(this);
        cd_option.setContentView(R.layout.cd_option);
        cd_option.show();

        iv1 = cd_option.findViewById(R.id.op_iv1);
        optv1 = cd_option.findViewById(R.id.op_tv1);
        optv2 = cd_option.findViewById(R.id.op_tv2);
        tottv_cnt = cd_option.findViewById(R.id.tv_cnt);
        tottv_price = cd_option.findViewById(R.id.tv_price);


        // 버튼의 태그를 사용하여 관련 정보를 가져오기
        String tag = (String) v.getTag();
        if (tag.equals("101") || tag.equals("102")) {
            int imageId = getResources().getIdentifier("noncoffee" + tag, "drawable", getPackageName());
            int nameId = getResources().getIdentifier("noncoffee_name_" + tag, "id", getPackageName());
            int priceId = getResources().getIdentifier("noncoffee_price_" + tag, "id", getPackageName());
            iv1.setImageResource(imageId);
            selectedName[0] = ((TextView) findViewById(nameId)).getText().toString();
            String price = ((TextView) findViewById(priceId)).getText().toString();
            optv1.setText(selectedName[0]);
            optv2.setText(price);
            priceint[0] = Integer.parseInt(price.replace(",", "").replace("원", ""));
        } else {
            int imageId = getResources().getIdentifier("coffee" + tag, "drawable", getPackageName());
            int nameId = getResources().getIdentifier("coffee_name_" + tag, "id", getPackageName());
            int priceId = getResources().getIdentifier("coffee_price_" + tag, "id", getPackageName());
            iv1.setImageResource(imageId);
            selectedName[0] = ((TextView) findViewById(nameId)).getText().toString();
            String price = ((TextView) findViewById(priceId)).getText().toString();
            optv1.setText(selectedName[0]);
            optv2.setText(price);
            priceint[0] = Integer.parseInt(price.replace(",", "").replace("원", ""));
        }

        //토글 버튼 정의 부분
        ToggleButton tb1_1 = cd_option.findViewById(R.id.tb1_1);
        ToggleButton tb1_2 = cd_option.findViewById(R.id.tb1_2);
        ToggleButton tb2_1 = cd_option.findViewById(R.id.tb2_1);
        ToggleButton tb2_2 = cd_option.findViewById(R.id.tb2_2);
        ToggleButton tb2_3 = cd_option.findViewById(R.id.tb2_3);
        ToggleButton tb3_1 = cd_option.findViewById(R.id.tb3_1);
        ToggleButton tb3_2 = cd_option.findViewById(R.id.tb3_2);
        ToggleButton tb3_3 = cd_option.findViewById(R.id.tb3_3);
        ToggleButton tb3_4 = cd_option.findViewById(R.id.tb3_4);

        //토글 버튼 이벤트 처리 부분
        tb1_1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tb1_1.setAlpha(isChecked ? 1.0f : 0.5f);
            if (isChecked) {tb1_2.setChecked(false);}
        });
        tb1_2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tb1_2.setAlpha(isChecked ? 1.0f : 0.5f);
            if (isChecked) {tb1_1.setChecked(false);}
        });
        tb2_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb2_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb2_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb2_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb2_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb2_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_4.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_4.setAlpha(isChecked ? 1.0f : 0.5f));

        //선택 완료 버튼 이벤트 처리
        Button bt1 = cd_option.findViewById(R.id.op_bt1);
        tl = findViewById(R.id.order_show);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] order_cnt = {1};
                //토글버튼 미선택시 기본값 설정
                if (!tb2_1.isChecked() && !tb2_2.isChecked() && !tb2_3.isChecked()) {
                    tb2_3.setChecked(true);
                    tb2_3.setAlpha(1.0f);
                }
                //테이블로우 생성자
                TableRow tableRow = new TableRow(Kiosk.this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                //테이블로우 내의 구성품 생성자
                TextView order_tv1 = new TextView(Kiosk.this);
                order_tv1.setText(selectedName[0]);
                order_tv1.setLayoutParams(new TableRow.LayoutParams(355, 70));
                order_tv1.setTextSize(14);

                TextView order_tv2 = new TextView(Kiosk.this);
                order_tv2.setText(String.valueOf(order_cnt[0]));
                order_tv2.setLayoutParams(new TableRow.LayoutParams(60, 70));
                order_tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                order_tv2.setTextSize(14);

                TextView order_tv3 = new TextView(Kiosk.this);
                if (tb2_1.isChecked()) priceint[0] += 500;
                if (tb2_2.isChecked()) priceint[0] += 500;
                String formattedPrice = String.format("%,d원", priceint[0] * order_cnt[0]);
                order_tv3.setText(formattedPrice);
                order_tv3.setLayoutParams(new TableRow.LayoutParams(180, 70));
                order_tv3.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                order_tv3.setTextSize(16);

                Button order_ib1 = new Button(Kiosk.this);
                order_ib1.setBackgroundResource(R.drawable.minus2);
                order_ib1.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (order_cnt[0] > 1) {
                            order_cnt[0]--;
                            cnt--;
                            order_tv2.setText(String.valueOf(order_cnt[0]));
                            updatePrice(order_tv3, priceint[0], order_cnt[0]);
                            updateTotalPrice();
                        }
                    }
                });

                Button order_ib2 = new Button(Kiosk.this);
                order_ib2.setBackgroundResource(R.drawable.plus2);
                order_ib2.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_cnt[0]++;
                        cnt++;
                        order_tv2.setText(String.valueOf(order_cnt[0]));
                        updatePrice(order_tv3, priceint[0], order_cnt[0]);
                        updateTotalPrice();
                    }
                });

                Button order_ib3 = new Button(Kiosk.this);
                order_ib3.setBackgroundResource(R.drawable.del);
                order_ib3.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tl.removeView(tableRow);
                        cnt -= order_cnt[0];
                        updateTotalPrice();
                    }
                });
                //테이블로우에 구성품 추가
                tableRow.addView(order_tv1);
                tableRow.addView(order_ib1);
                tableRow.addView(order_tv2);
                tableRow.addView(order_ib2);
                tableRow.addView(order_tv3);
                tableRow.addView(order_ib3);
                //테이블레이아웃에 테이블로우 추가
                tl.addView(tableRow);
                //기타 이벤트 처리 부분
                cnt++;
                Toast.makeText(Kiosk.this, "주문 추가 완료!", Toast.LENGTH_SHORT).show();
                cd_option.dismiss();
                tottv_cnt = findViewById(R.id.tv_cnt);
                tottv_price = findViewById(R.id.tv_price);
                tottv_cnt.setText("총 " + cnt + "개 결제");
                updateTotalPrice();
            }
        });
    }
    // 이미지버튼 주문명령 처리부분(스무디탭)
    public void order_smoothie(View v) {
        final int[] priceint = new int[1];
        final String[] selectedName = new String[1];
        final Dialog cd_option2 = new Dialog(this);
        cd_option2.setContentView(R.layout.cd_option2);
        cd_option2.show();

        iv1 = cd_option2.findViewById(R.id.op_iv21);
        optv1 = cd_option2.findViewById(R.id.op_tv21);
        optv2 = cd_option2.findViewById(R.id.op_tv22);
        tottv_cnt = cd_option2.findViewById(R.id.tv_cnt);
        tottv_price = cd_option2.findViewById(R.id.tv_price);

        // 버튼의 태그를 사용하여 관련 정보를 가져오기
        String tag = (String) v.getTag();
        int imageId = getResources().getIdentifier("smoothie" + tag, "drawable", getPackageName());
        int nameId = getResources().getIdentifier("smoothie_name_" + tag, "id", getPackageName());
        int priceId = getResources().getIdentifier("smoothie_price_" + tag, "id", getPackageName());
        iv1.setImageResource(imageId);
        selectedName[0] = ((TextView) findViewById(nameId)).getText().toString();
        String price = ((TextView) findViewById(priceId)).getText().toString();
        optv1.setText(selectedName[0]);
        optv2.setText(price);
        priceint[0] = Integer.parseInt(price.replace(",", "").replace("원", ""));

        //토글 버튼 정의 부분
        ToggleButton tb21_1 = cd_option2.findViewById(R.id.tb21_2);
        ToggleButton tb22_1 = cd_option2.findViewById(R.id.tb22_1);
        ToggleButton tb22_2 = cd_option2.findViewById(R.id.tb22_2);
        ToggleButton tb22_3 = cd_option2.findViewById(R.id.tb22_3);
        ToggleButton tb23_1 = cd_option2.findViewById(R.id.tb23_1);
        ToggleButton tb23_2 = cd_option2.findViewById(R.id.tb23_2);
        ToggleButton tb23_3 = cd_option2.findViewById(R.id.tb23_3);
        ToggleButton tb23_4 = cd_option2.findViewById(R.id.tb23_4);

        //토글 버튼 이벤트 처리 부분
        tb21_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb21_1.setAlpha(1.0f));
        tb22_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb22_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb22_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb22_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb22_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb22_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_4.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_4.setAlpha(isChecked ? 1.0f : 0.5f));

        Button bt1 = cd_option2.findViewById(R.id.op_bt21);
        tl = findViewById(R.id.order_show);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] order_cnt = {1};
                //토글버튼 미선택시 기본값 설정
                if (!tb22_1.isChecked() && !tb22_2.isChecked() && !tb22_3.isChecked()) {
                    tb22_3.setChecked(true);
                    tb22_3.setAlpha(1.0f);
                }
                //테이블로우 생성자
                TableRow tableRow = new TableRow(Kiosk.this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                //테이블로우 내의 구성품 생성자
                TextView order_tv21 = new TextView(Kiosk.this);
                order_tv21.setText(selectedName[0]);
                order_tv21.setLayoutParams(new TableRow.LayoutParams(355, 70));
                order_tv21.setTextSize(14);

                TextView order_tv22 = new TextView(Kiosk.this);
                order_tv22.setText(String.valueOf(order_cnt[0]));
                order_tv22.setLayoutParams(new TableRow.LayoutParams(60, 70));
                order_tv22.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                order_tv22.setTextSize(14);

                TextView order_tv23 = new TextView(Kiosk.this);
                if (tb22_1.isChecked()) priceint[0] += 500;
                if (tb22_2.isChecked()) priceint[0] += 700;
                String formattedPrice = String.format("%,d원", priceint[0] * order_cnt[0]);
                order_tv23.setText(formattedPrice);
                order_tv23.setLayoutParams(new TableRow.LayoutParams(180, 70));
                order_tv23.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                order_tv23.setTextSize(16);

                Button order_ib21 = new Button(Kiosk.this);
                order_ib21.setBackgroundResource(R.drawable.minus2);
                order_ib21.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (order_cnt[0] > 1) {
                            order_cnt[0]--;
                            cnt--;
                            order_tv22.setText(String.valueOf(order_cnt[0]));
                            updatePrice(order_tv23, priceint[0], order_cnt[0]);
                            updateTotalPrice();
                        }
                    }
                });
                Button order_ib22 = new Button(Kiosk.this);
                order_ib22.setBackgroundResource(R.drawable.plus2);
                order_ib22.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_cnt[0]++;
                        cnt++;
                        order_tv22.setText(String.valueOf(order_cnt[0]));
                        updatePrice(order_tv23, priceint[0], order_cnt[0]);
                        updateTotalPrice();
                    }
                });

                Button order_ib23 = new Button(Kiosk.this);
                order_ib23.setBackgroundResource(R.drawable.del);
                order_ib23.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib23.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tl.removeView(tableRow);
                        cnt -= order_cnt[0];
                        updateTotalPrice();
                    }
                });
                //테이블로우에 구성품 추가
                tableRow.addView(order_tv21);
                tableRow.addView(order_ib21);
                tableRow.addView(order_tv22);
                tableRow.addView(order_ib22);
                tableRow.addView(order_tv23);
                tableRow.addView(order_ib23);
                //테이블레이아웃에 테이블로우 추가
                tl.addView(tableRow);
                //기타 이벤트 처리 부분
                cnt++;
                Toast.makeText(Kiosk.this, "주문 추가 완료!", Toast.LENGTH_SHORT).show();
                cd_option2.dismiss();
                tottv_cnt = findViewById(R.id.tv_cnt);
                tottv_price = findViewById(R.id.tv_price);
                tottv_cnt.setText("총 " + cnt + "개 결제");
                updateTotalPrice();
            }
        });
    }
    // 이미지버튼 주문명령 처리부분(디저트탭)
    public void order_dessert(View v) {
        final int[] order_cnt = {1};

        tottv_cnt = findViewById(R.id.tv_cnt);
        tottv_price = findViewById(R.id.tv_price);

        // 버튼의 태그를 사용하여 관련 정보를 가져오기
        String tag = (String) v.getTag();
        int nameId = getResources().getIdentifier("dessert_name_" + tag, "id", getPackageName());
        int priceId = getResources().getIdentifier("dessert_price_" + tag, "id", getPackageName());
        String name = ((TextView) findViewById(nameId)).getText().toString();
        String price = ((TextView) findViewById(priceId)).getText().toString();
        int priceint = Integer.parseInt(price.replace(",", "").replace("원", ""));

        tl = findViewById(R.id.order_show);
        //테이블로우 생성자
        TableRow tableRow = new TableRow(Kiosk.this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        //테이블로우 내의 구성품 생성자
        TextView order_tv31 = new TextView(Kiosk.this);
        order_tv31.setText(name);
        order_tv31.setLayoutParams(new TableRow.LayoutParams(355, 70));
        order_tv31.setTextSize(14);

        TextView order_tv32 = new TextView(Kiosk.this);
        order_tv32.setText(String.valueOf(order_cnt[0]));
        order_tv32.setLayoutParams(new TableRow.LayoutParams(60, 70));
        order_tv32.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        order_tv32.setTextSize(14);

        TextView order_tv33 = new TextView(Kiosk.this);
        String formattedPrice = String.format("%,d원", priceint * order_cnt[0]);
        order_tv33.setText(formattedPrice);
        order_tv33.setLayoutParams(new TableRow.LayoutParams(180, 70));
        order_tv33.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        order_tv33.setTextSize(16);

        Button order_ib31 = new Button(Kiosk.this);
        order_ib31.setBackgroundResource(R.drawable.minus2);
        order_ib31.setLayoutParams(new TableRow.LayoutParams(60, 60));
        order_ib31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order_cnt[0] > 1) {
                    order_cnt[0]--;
                    cnt--;
                    order_tv32.setText(String.valueOf(order_cnt[0]));
                    updatePrice(order_tv33, priceint, order_cnt[0]);
                    updateTotalPrice();
                }
            }
        });
        Button order_ib32 = new Button(Kiosk.this);
        order_ib32.setBackgroundResource(R.drawable.plus2);
        order_ib32.setLayoutParams(new TableRow.LayoutParams(60, 60));
        order_ib32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_cnt[0]++;
                cnt++;
                order_tv32.setText(String.valueOf(order_cnt[0]));
                updatePrice(order_tv33, priceint, order_cnt[0]);
                updateTotalPrice();
            }
        });

        Button order_ib33 = new Button(Kiosk.this);
        order_ib33.setBackgroundResource(R.drawable.del);
        order_ib33.setLayoutParams(new TableRow.LayoutParams(60, 60));
        order_ib33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tl.removeView(tableRow);
                cnt -= order_cnt[0];
                updateTotalPrice();
            }
        });

        //테이블로우에 구성품 추가
        tableRow.addView(order_tv31);
        tableRow.addView(order_ib31);
        tableRow.addView(order_tv32);
        tableRow.addView(order_ib32);
        tableRow.addView(order_tv33);
        tableRow.addView(order_ib33);
        //테이블레이아웃에 테이블로우 추가
        tl.addView(tableRow);

        //기타 이벤트 처리 부분
        cnt++;
        Toast.makeText(Kiosk.this, "주문 추가 완료!", Toast.LENGTH_SHORT).show();
        tottv_cnt.setText("총 " + cnt + "개 결제");
        updateTotalPrice();
    }
    public void ib_cancel(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("주문 내역 전체 삭제");
        builder.setMessage("주문을 취소 하시겠습니까?");
        builder.setNegativeButton("취소", null);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tl.removeAllViews();
                cnt = 0;
                updateTotalPrice();
                tottv_cnt.setText("총 " + cnt + "개 결제");
                Toast.makeText(Kiosk.this, "주문 취소 완료!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updatePrice(TextView order_tv3, int pricePerItem, int itemCount) {
        int totalPrice = pricePerItem * itemCount;
        String formattedPrice = String.format("%,d원", totalPrice);
        order_tv3.setText(formattedPrice);
    }
    private void updateTotalPrice() {
        int totalOrderPrice = 0;
        for (int i = 0; i < tl.getChildCount(); i++) {
            TableRow row = (TableRow) tl.getChildAt(i);
            TextView priceView = (TextView) row.getChildAt(4);
            String priceText = priceView.getText().toString().replace(",", "").replace("원", "");
            totalOrderPrice += Integer.parseInt(priceText);
        }
        String formattedTotal = String.format("%,d원", totalOrderPrice);
        tottv_price.setText(formattedTotal);
        tottv_cnt.setText("총 " + cnt + "개 결제");
    }
    public void ib_pay(View v) {
        if (cnt == 0) {
            Toast.makeText(Kiosk.this, "주문이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {setContentView(R.layout.payment);}
        // TableLayout에서 데이터 추출
        List<data> list = new ArrayList<>();
        for (int i = 0; i < tl.getChildCount(); i++) {
            TableRow row = (TableRow) tl.getChildAt(i);
            TextView nameView = (TextView) row.getChildAt(0);
            TextView countView = (TextView) row.getChildAt(2);
            TextView priceView = (TextView) row.getChildAt(4);
            String name = nameView.getText().toString();
            String count = countView.getText().toString();
            String price = priceView.getText().toString();
            data data = new data(name, count, price);
            list.add(data);
        }
        // ListView에 데이터 설정
        ListView lv_pay = findViewById(R.id.lv_pay);
        ArrayAdapter<data> adapter = new ArrayAdapter<data>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                list
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                data currentData = getItem(position);
                text1.setText(currentData.getName());
                text2.setText(currentData.getCount() + "개 - " + currentData.getPrice());

                return view;
            }
        };
        lv_pay.setAdapter(adapter);

        paycount = findViewById(R.id.payment_tv2);
        payprice = findViewById(R.id.payment_tv3);
        paypoint = findViewById(R.id.tv_point);
        paycount.setText(cnt + " 개");
        payprice.setText(tottv_price.getText().toString());
        int point = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
        int a = (int) (point * 0.1);
        paypoint.setText(String.format("%s P", a));
    }

    public class data {
        String name;
        String count;
        String price;

        public data(String name, String count, String price) {
            this.name = name;
            this.count = count;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getCount() {
            return count;
        }

        public String getPrice() {
            return price;
        }
    }

    //store2 (take_out)
    public void bt_out(View v) {
        setContentView(R.layout.store2);
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Drinks").setIndicator("커피");
        tabSpec.setContent(R.id.coffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("논커피");
        tabSpec.setContent(R.id.noncoffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("스무디");
        tabSpec.setContent(R.id.smoothie);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("주스");
        tabSpec.setContent(R.id.juice);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("디저트");
        tabSpec.setContent(R.id.dessert);
        tabHost.addTab(tabSpec);
    }
    public void stbt_1(View v) {
        setContentView(R.layout.store2);
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Drinks").setIndicator("커피");
        tabSpec.setContent(R.id.coffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("논커피");
        tabSpec.setContent(R.id.noncoffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("스무디");
        tabSpec.setContent(R.id.smoothie);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("주스");
        tabSpec.setContent(R.id.juice);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("디저트");
        tabSpec.setContent(R.id.dessert);
        tabHost.addTab(tabSpec);
    }
    //결제 창 작성
    public void bt_payment(View v) {
        final Dialog coupon = new Dialog(this);
        coupon.setContentView(R.layout.custom2);
        coupon.show();
        Button bt_coupon1 = coupon.findViewById(R.id.bt_payment_1);
        bt_coupon1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et_coupon = coupon.findViewById(R.id.et_payment_1);
                payprice = findViewById(R.id.payment_tv3);
                String coupon_num = et_coupon.getText().toString();
                int price = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
                int discountedPrice = price;

                switch (coupon_num) {
                    case "1111":
                        if (price > 5000) {
                            discountedPrice = price - 5000;
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 5000원 할인 적용.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Kiosk.this, "주문 가격이 5000원 이상이어야 쿠폰 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case "2222":
                        if (price > 10000) {
                            discountedPrice = price - 10000;
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 10000원 할인 적용.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Kiosk.this, "주문 가격이 10000원 이상이어야 쿠폰 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case "3333":
                        if (price > 20000) {
                            discountedPrice = price - 20000;
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 20000원 할인 적용.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Kiosk.this, "주문 가격이 20000원 이상이어야 쿠폰 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case "4444":
                        if (price > 0) {
                            discountedPrice = (int) Math.round(price * 0.8);
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 20% 할인 적용.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(Kiosk.this, "쿠폰 번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        return;
                }

                coupon.dismiss();
                payprice.setText(String.format("%,d원", discountedPrice));
            }
        });
        Button bt_coupon2 = coupon.findViewById(R.id.bt_payment_2);
        bt_coupon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupon.dismiss();
            }
        });
    }
    public void paycard_bt(View v) {
        final Dialog paycard = new Dialog(this);
        paycard.setContentView(R.layout.payment_card);
        TextView dialogpay = paycard.findViewById(R.id.tv_card2);
        dialogpay.setText(payprice.getText().toString());
        paycard.show();
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        paycard.dismiss();
                        setContentView(R.layout.paycomplete);
                        rantv = findViewById(R.id.tvpaycom_1);
                        complete = findViewById(R.id.tvpaycom_2);
                        if (rantv != null) {
                            Random random = new Random();
                            int rannum = random.nextInt(100) + 1;
                            rantv.setText("# " + rannum);
                        }
                        int point = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
                        int a = (int) (point * 0.1);
                        complete.setText(String.format("%s P", a));
                    }
                });
            }
        }, 3000);
    }
    public void payeasy_bt(View v) {
        final Dialog payeasy = new Dialog(this);
        payeasy.setContentView(R.layout.payment_easy);
        TextView dialogpay = payeasy.findViewById(R.id.tv_easy2);
        dialogpay.setText(payprice.getText().toString());
        payeasy.show();
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        payeasy.dismiss();
                        setContentView(R.layout.paycomplete);
                        rantv = findViewById(R.id.tvpaycom_1);
                        complete = findViewById(R.id.tvpaycom_2);
                        if (rantv != null) {
                            Random random = new Random();
                            int rannum = random.nextInt(100) + 1;
                            rantv.setText("# " + rannum);
                        }
                        int point = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
                        int a = (int) (point * 0.1);
                        complete.setText(String.format("%s P", a));
                    }
                });
            }
        }, 3000);
    }
    public void pay_bt1(View v) {
        cnt = 0;
        setContentView(R.layout.store1);
        tab1 = findViewById(R.id.sv1);
        tab2 = findViewById(R.id.sv2);
        tab3 = findViewById(R.id.sv3);
        tab4 = findViewById(R.id.sv4);
        tab5 = findViewById(R.id.sv5);
    }
    //결제 완료 후 창
    public void btpaycom(View v) {
        cnt = 0;
        setContentView(R.layout.kioskmain);
    }
    public void btpaycom2(View v) {
        Toast.makeText(Kiosk.this, "영수증 출력 완료!", Toast.LENGTH_SHORT).show();
    }
    //config
}
