package com.hepeng.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hepeng.R;
import com.hepeng.adapter.TableListAdapter;
import com.hepeng.adapter.TableListViewHolder;
import com.hepeng.common.DensityUtils;
import com.hepeng.common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YangHePeng
 * @description
 * @data 18-8-5
 * @email yanghp@91power.com
 */
public class SuperTableLayout extends LinearLayout {
    private static final String TAG = SuperTableLayout.class.getSimpleName();

    private TextView tableTitle;
    private LinearLayout titleContainer;
    private TableListView tableHeaderContainer;
    private TableListView tableContentContainer;
    private LinearLayout tableTitleLayout;

    private SyncHorizontalScrollView tableContentHor;
    private SyncHorizontalScrollView titleHor;

    private List<List<String>> cacheTableContent;

    private float textSize;
    private int tableItemHeight;
    private int tableItemMargin;
    private int tableItemColor;
    private int height;

    public SuperTableLayout(Context context) {
        super(context);

        initView(context, null);
    }

    public SuperTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public SuperTableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.table_layout, this, true);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, 20));

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.table_layout);

            height = (int) ta.getDimension(R.styleable.table_layout_height, 0);

            textSize = ta.getDimension(R.styleable.table_layout_text_size, 0);
            if (textSize == 0) {
                textSize = (int) getResources().getDimension(R.dimen.statistics_table_item_text_size);
            }

            tableItemMargin = (int) ta.getDimension(R.styleable.table_layout_table_item_margin, 0);
            if (tableItemMargin == 0) {
                tableItemMargin = (int) getResources().getDimension(R.dimen.statistics_table_item_margin);
            }

            tableItemHeight = (int) ta.getDimension(R.styleable.table_layout_table_item_height, 0);
            if (tableItemHeight == 0) {
                tableItemHeight = (int) getResources().getDimension(R.dimen.statistics_table_item_default_height);
            }

            tableItemColor = (int)  ta.getColor(R.styleable.table_layout_table_item_color, 0);
            if (tableItemColor == 0) {
                tableItemColor = Color.parseColor("#fff6f6f6");
            }

            ta.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tableTitle = findViewById(R.id.tv_table_title);
        titleContainer = findViewById(R.id.ll_table_title_container);
        tableHeaderContainer = findViewById(R.id.lv_table_row_header_container);
        tableContentContainer = findViewById(R.id.lv_table_content_container);
        tableTitleLayout = findViewById(R.id.ll_table_title_layout);

        tableContentHor = findViewById(R.id.sv_table_content_hor);
        titleHor = findViewById(R.id.sv_title_hor);
    }

    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        if (height == 0) {
            height = getMeasuredHeight();
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    /**
     *
     * @param tableContent 最外层list是表格有几行数据，内层list是每行有多少数据
     */
    public void addData(final List<List<String>> tableContent) {
        if (tableContent != null && tableContent.size() > 0) {
            cacheTableContent = tableContent;
        }

        if (!tableContentQualified(cacheTableContent)) {
            return;
        }


        // 第一行表头
        // 第一行表头第一个
        tableTitle.setGravity(Gravity.CENTER);
        tableTitle.setText(cacheTableContent.get(0).get(0));
        tableTitle.setSingleLine();
        tableTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tableTitle.setEllipsize(TextUtils.TruncateAt.END);
        tableTitle.setBackgroundColor(tableRowColor(0));
        tableTitle.setLayoutParams(new LinearLayout.LayoutParams(getListTextMaxWidth(cacheTableContent, textSize, 0), LinearLayout.LayoutParams.MATCH_PARENT));


        // 第一行表头除第一个外其他
        titleContainer.removeAllViews();
        for (int i = 1; i < cacheTableContent.get(0).size(); i++) {
            TextView tvTableTitle = new TextView(getContext());
            tvTableTitle.setGravity(Gravity.CENTER);
            tvTableTitle.setSingleLine();
            tvTableTitle.setEllipsize(TextUtils.TruncateAt.END);
            tvTableTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tvTableTitle.setTextColor(getResources().getColor(R.color.statistics_table_title));
            tvTableTitle.setBackgroundColor(tableRowColor(0));
            String itemName = cacheTableContent.get(0).get(i);
            if (!TextUtils.isEmpty(itemName)) {
                tvTableTitle.setText(itemName);
            }

            LinearLayout.LayoutParams tableTitleParams = new LinearLayout.LayoutParams(getListTextMaxWidth(cacheTableContent, textSize, i), LinearLayout.LayoutParams.MATCH_PARENT);
            titleContainer.addView(tvTableTitle, tableTitleParams);


            int lineColor, lineWidth;
            if (i == cacheTableContent.get(0).size() - 1) {
                lineColor = getResources().getColor(R.color.statistics_table_border);
                lineWidth = 1;
            } else {
                lineColor = getResources().getColor(R.color.statistics_table_divide);
                lineWidth = DensityUtils.dp2px(getContext(), 1);
            }
            View tableTitleEndLine = new View(getContext());
            tableTitleEndLine.setBackgroundColor(lineColor);
            titleContainer.addView(tableTitleEndLine, new LinearLayout.LayoutParams(lineWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        }

        LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) tableTitleLayout.getLayoutParams();
        titleLayoutParams.height = tableItemHeight;
        tableTitleLayout.setLayoutParams(titleLayoutParams);
        // 第一行表头



        // 第一列
        TableListAdapter<String> tableHeaderContainerAdapter = new TableListAdapter<String>(getContext(), R.layout.table_item) {

            @Override
            public void convert(Context context, TableListViewHolder helper, String item, int pos) {
                TextView tableTitleItem = helper.getView(R.id.tv_table_title_item);
                tableTitleItem.setGravity(Gravity.CENTER);
                tableTitleItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                if (!TextUtils.isEmpty(item)) {
                    tableTitleItem.setText(item);
                }
                tableTitleItem.setBackgroundColor(tableRowColor(pos + 1));

                AbsListView.LayoutParams tableTitleItemParams = (AbsListView.LayoutParams) tableTitleItem.getLayoutParams();
                tableTitleItemParams.width = getListTextMaxWidth(cacheTableContent, textSize, 0);
                tableTitleItemParams.height = tableItemHeight;
                tableTitleItem.setLayoutParams(tableTitleItemParams);
            }
        };
        tableHeaderContainer.setAdapter(tableHeaderContainerAdapter);


        List<String> titleItemData = new ArrayList<>();
        for (int i = 1; i < cacheTableContent.size(); i++) {
            titleItemData.add(cacheTableContent.get(i).get(0));
        }
        tableHeaderContainerAdapter.addItemData(titleItemData, true);
        // 第一列



        // 第一行向下，第一列向右
        TableListAdapter<List<String>> tableContentContainerAdapter = new TableListAdapter<List<String>>(getContext(), new TableListAdapter.CreateViewHolder() {

            @SuppressLint("ResourceType")
            @Override
            public ViewGroup createView(Context context, int position, ViewGroup parent) {
                int realRowPosition = position + 1;

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tableItemHeight));
                linearLayout.setBackgroundColor(tableRowColor(realRowPosition));

                int size = cacheTableContent.get(realRowPosition).size();
                for (int i = 1; i < size; i++) {
                    TextView tvTableContent = new TextView(context);
                    tvTableContent.setId(i);
                    tvTableContent.setGravity(Gravity.CENTER);
                    tvTableContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tvTableContent.setTextColor(getResources().getColor(R.color.statistics_table_title));
                    tvTableContent.setSingleLine();
                    tvTableContent.setEllipsize(TextUtils.TruncateAt.END);

                    LinearLayout.LayoutParams tvTableContentParams = new LinearLayout.LayoutParams(getListTextMaxWidth(cacheTableContent, SuperTableLayout.this.textSize, i), LinearLayout.LayoutParams.MATCH_PARENT);
                    tvTableContentParams.gravity = Gravity.CENTER;
                    linearLayout.addView(tvTableContent, tvTableContentParams);


                    int lineColor, lineWidth;
                    if (i == size - 1) {
                        lineColor = getResources().getColor(R.color.statistics_table_border);
                        lineWidth = 1;
                    } else {
                        lineColor = getResources().getColor(R.color.statistics_table_divide);
                        lineWidth = DensityUtils.dp2px(getContext(), 1);
                    }
                    View tvTableContentEndLine = new View(context);
                    tvTableContentEndLine.setBackgroundColor(lineColor);
                    linearLayout.addView(tvTableContentEndLine, new LinearLayout.LayoutParams(lineWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                }
                return linearLayout;
            }
        }) {

            @Override
            public void convert(Context context, TableListViewHolder helper, List<String> item, int pos) {
                ViewGroup viewGroup;
                if (helper.getConvertView() instanceof ViewGroup) {
                    viewGroup = (ViewGroup) helper.getConvertView();
                } else {
                    return;
                }

                for (int i = 1; i < item.size(); i++) {
                    @SuppressLint("ResourceType")
                    TextView tvTableContent = viewGroup.findViewById(i);

                    String text = item.get(i);
                    if (!TextUtils.isEmpty(text)) {
                        tvTableContent.setText(text);
                    }
                }
            }
        };
        tableContentContainer.setAdapter(tableContentContainerAdapter);
        // 第一行向下，第一列向右
        List<List<String>> list = new ArrayList<>();
        for (int i = 1; i < cacheTableContent.size(); i++) {
            list.add(cacheTableContent.get(i));
        }
        tableContentContainerAdapter.addItemData(list, true);





        // 以下设置表格宽度为真实宽度
        Utils.getViewSize(TAG, tableTitleLayout, new Utils.MeasureViewSizeCallback() {

            @Override
            public void sizeCallback(int width, int height) {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = width;
                setLayoutParams(layoutParams);
            }
        });



        tableContentHor.setScrollView(titleHor);
        titleHor.setScrollView(tableContentHor);
    }

    private int tableRowColor(int rowPosition) {
        if (rowPosition % 2 == 0) {
            // 偶数行
            return tableItemColor;
        } else {
            // 奇数行
            return Color.WHITE;
        }
    }

    /**
     * 返回每一列字符数据中最大宽度
     *
     * @param tableContent
     * @param listNo      列号，第一列从0开始
     * @return 单位px
     */
    private int getListTextMaxWidth(List<List<String>> tableContent, float textSize, int listNo) {
        List<String> listTexts = new ArrayList<>();
        for (List<String> rowData : tableContent) {
            // 每行有多少数据
            if (listNo >= rowData.size()) {
                continue;
            }

            listTexts.add(rowData.get(listNo));
        }

        if (listTexts.size() <= 0) {
            return 0;
        }

        int listMaxTextWidth = 0;
        for (int i = 0; i < listTexts.size(); i++) {
            String text = listTexts.get(i);

            int textWidth = Utils.measureTextWidth(text, textSize);

            if (textWidth > listMaxTextWidth) {
                listMaxTextWidth = textWidth;
            }
        }

        return listMaxTextWidth + tableItemMargin;
    }

    private boolean tableContentQualified(List<List<String>> tableContent) {
        if (tableContent == null || tableContent.size() <= 0) {
            Log.e(TAG, "表数据为空");
            return false;
        }

        int lastRowSize = 0;
        for (int i = 0; i < tableContent.size(); i++) {
            List<String> rowList = tableContent.get(i);
            if (rowList == null || rowList.size() <= 0) {
                Log.e(TAG, "第" + (i + 1) + "行表数据为空");
                return false;
            }

            if (lastRowSize != 0 && lastRowSize != rowList.size()) {
                Log.e(TAG, "第" + (i + 1) + "行表数据数量与其他行不同");
                return false;
            }
            lastRowSize = rowList.size();
        }
        return true;
    }

    /**
     *
     * @param textSize px作为参数
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;

        addData(null);
    }

    public void setTableItemHeight(int tableItemHeight) {
        this.tableItemHeight = tableItemHeight;

        addData(null);
    }
}