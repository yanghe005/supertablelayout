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
import com.hepeng.common.ScreenSizeUtils;
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

    private TableBuild tableBuild;

    // line是列
    private int rowStart = 1, lineStart = 1;

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
        setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (tableBuild == null) {
            tableBuild = new TableBuild();
        }
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.table_layout);
            tableBuild.tableHeaderFixed = ta.getBoolean(R.styleable.table_layout_table_header_fixed, true);

            tableBuild.width = (int) ta.getDimension(R.styleable.table_layout_width, 0);
            tableBuild.height = (int) ta.getDimension(R.styleable.table_layout_height, 0);

            tableBuild.textSize = ta.getDimension(R.styleable.table_layout_text_size, 0);
            if (tableBuild.textSize == 0) {
                tableBuild.textSize = (int) getResources().getDimension(R.dimen.table_item_text_size);
            }

            tableBuild.itemLeftRightMargin = (int) ta.getDimension(R.styleable.table_layout_item_left_right_margin, 0);
            if (tableBuild.itemLeftRightMargin == 0) {
                tableBuild.itemLeftRightMargin = (int) getResources().getDimension(R.dimen.table_item_margin);
            }

            tableBuild.itemWidth = (int) ta.getDimension(R.styleable.table_layout_item_width, 0);
            tableBuild.itemHeight = (int) ta.getDimension(R.styleable.table_layout_item_height, 0);
            if (tableBuild.itemHeight == 0) {
                tableBuild.itemHeight = (int) getResources().getDimension(R.dimen.table_item_default_height);
            }

            tableBuild.itemBgPureColor = (int) ta.getColor(R.styleable.table_layout_item_bg_pure_color, 0);

            String textEllipsize = ta.getString(R.styleable.table_layout_item_text_ellipsize);
            if (!TextUtils.isEmpty(textEllipsize)) {
                switch (textEllipsize) {
                    case "rowStart":
                        tableBuild.itemTextEllipsize = TextUtils.TruncateAt.START;
                        break;
                    case "middle":
                        tableBuild.itemTextEllipsize = TextUtils.TruncateAt.MIDDLE;
                        break;
                    case "end":
                        tableBuild.itemTextEllipsize = TextUtils.TruncateAt.END;
                        break;
                    case "marquee":
                        tableBuild.itemTextEllipsize = TextUtils.TruncateAt.MARQUEE;
                        break;
                    default:
                        break;
                }
            }

            tableBuild.itemBgIntervalColor1 = ta.getColor(R.styleable.table_layout_item_bg_interval_color1, 0);
            if (tableBuild.itemBgIntervalColor1 == 0) {
                tableBuild.itemBgIntervalColor1 = Color.WHITE;
            }
            tableBuild.itemBgIntervalColor2 = ta.getColor(R.styleable.table_layout_item_bg_interval_color2, 0);
            if (tableBuild.itemBgIntervalColor2 == 0) {
                tableBuild.itemBgIntervalColor2 = Color.parseColor("#fff6f6f6");
            }

            tableBuild.tableHeaderBgColor = ta.getColor(R.styleable.table_layout_table_header_bg_color, 0);
            tableBuild.rowHeaderBgColor = ta.getColor(R.styleable.table_layout_row_header_bg_color, 0);

            tableBuild.textColor = ta.getColor(R.styleable.table_layout_text_color, 0);
            if (tableBuild.textColor == 0) {
                tableBuild.textColor = getResources().getColor(R.color.table_title);
            }

            tableBuild.rowItemMergeIndex = ta.getString(R.styleable.table_layout_row_item_merge_index);
            ta.recycle();
        }

        if (!tableBuild.tableHeaderFixed) {
            findViewById(R.id.v_table_title_divide).setVisibility(View.GONE);
            findViewById(R.id.ll_table_title_layout).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tableContentContainer = findViewById(R.id.lv_table_content_container);
        if (tableBuild.tableHeaderFixed) {
            tableTitle = findViewById(R.id.tv_table_title);
            titleContainer = findViewById(R.id.ll_table_title_container);

            tableTitleLayout = findViewById(R.id.ll_table_title_layout);
            tableHeaderContainer = findViewById(R.id.lv_table_row_header_container);
            if (tableBuild.tableHeaderBgColor != 0) {
                tableTitleLayout.setBackgroundColor(tableBuild.tableHeaderBgColor);
            }
            if (tableBuild.rowHeaderBgColor != 0) {
                tableHeaderContainer.setBackgroundColor(tableBuild.rowHeaderBgColor);
            }
        }


        tableContentHor = findViewById(R.id.sv_table_content_hor);
        titleHor = findViewById(R.id.sv_title_hor);
    }

    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        if (tableBuild.height == 0) {
            tableBuild.height = getMeasuredHeight();
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(tableBuild.width == 0 ? getMeasuredWidth() : tableBuild.width,
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(tableBuild.height == 0 ? getMeasuredHeight() :
                tableBuild.height, MeasureSpec.EXACTLY));
    }

    public SuperTableLayout setTableBuild(TableBuild tableBuild) {
        if (tableBuild == null) {
            return this;
        }
        if (this.tableBuild == null) {
            this.tableBuild = tableBuild;
            return this;
        }

        if (tableBuild.textSize != 0) {
            this.tableBuild.textSize = tableBuild.textSize;
        }
        if (tableBuild.textColor != 0) {
            this.tableBuild.textColor = tableBuild.textColor;
        }
        if (tableBuild.itemWidth != 0) {
            this.tableBuild.itemWidth = tableBuild.itemWidth;
        }
        if (tableBuild.itemHeight != 0) {
            this.tableBuild.itemHeight = tableBuild.itemHeight;
        }
        if (tableBuild.itemLeftRightMargin != 0) {
            this.tableBuild.itemLeftRightMargin = tableBuild.itemLeftRightMargin;
        }
        if (tableBuild.itemBgIntervalColor1 != 0) {
            this.tableBuild.itemBgIntervalColor1 = tableBuild.itemBgIntervalColor1;
        }
        if (tableBuild.itemBgIntervalColor2 != 0) {
            this.tableBuild.itemBgIntervalColor2 = tableBuild.itemBgIntervalColor2;
        }
        if (tableBuild.tableHeaderBgColor != 0) {
            this.tableBuild.tableHeaderBgColor = tableBuild.tableHeaderBgColor;
        }
        if (tableBuild.rowHeaderBgColor != 0) {
            this.tableBuild.rowHeaderBgColor = tableBuild.rowHeaderBgColor;
        }
        if (tableBuild.itemBgPureColor != 0) {
            this.tableBuild.itemBgPureColor = tableBuild.itemBgPureColor;
        }
        if (tableBuild.width != 0) {
            this.tableBuild.width = tableBuild.width;
        }
        if (tableBuild.height != 0) {
            this.tableBuild.height = tableBuild.height;
        }
        if (tableBuild.itemTextEllipsize != null) {
            this.tableBuild.itemTextEllipsize = tableBuild.itemTextEllipsize;
        }

        return this;
    }

    /**
     * @param tableContent 最外层list是表格有几行数据，内层list是每行有多少数据
     */
    public void addData(final List<List<String>> tableContent) {
        if (!tableContentQualified(tableContent)) {
            return;
        }

        final int itemWidth = getListTextMaxWidth(tableContent, tableBuild.textSize, 0);

        if (tableBuild.tableHeaderFixed) {
            // 第一行表头
            // 第一行表头第一个
            tableTitle.setGravity(Gravity.CENTER);
            tableTitle.setText(tableContent.get(0).get(0));
            tableTitle.setSingleLine();
            tableTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableBuild.textSize);
            tableTitle.setTextColor(tableBuild.textColor);
            tableTitle.setEllipsize(tableBuild.itemTextEllipsize);
            tableTitle.setBackgroundColor(tableRowColor(0, 0));
            tableTitle.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT));


            // 第一行表头除第一个外其他
            titleContainer.removeAllViews();
            for (int i = 1; i < tableContent.get(0).size(); i++) {
                TextView tvTableTitle = new TextView(getContext());
                tvTableTitle.setGravity(Gravity.CENTER);
                tvTableTitle.setSingleLine();
                tvTableTitle.setEllipsize(tableBuild.itemTextEllipsize);
                tvTableTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableBuild.textSize);
                tvTableTitle.setTextColor(tableBuild.textColor);
                tvTableTitle.setBackgroundColor(tableRowColor(0, i));
                String itemName = tableContent.get(0).get(i);
                if (!TextUtils.isEmpty(itemName)) {
                    tvTableTitle.setText(itemName);
                }

                LinearLayout.LayoutParams tableTitleParams = new LinearLayout.LayoutParams(getListTextMaxWidth(tableContent, tableBuild.textSize, i), LinearLayout.LayoutParams.MATCH_PARENT);
                titleContainer.addView(tvTableTitle, tableTitleParams);


                int lineColor, lineWidth;
                if (i == tableContent.get(0).size() - 1) {
                    lineColor = getResources().getColor(R.color.table_border);
                    lineWidth = 1;
                } else {
                    lineColor = getResources().getColor(R.color.table_divide);
                    lineWidth = DensityUtils.dp2px(getContext(), 1);
                }
                View tableTitleEndLine = new View(getContext());
                tableTitleEndLine.setBackgroundColor(lineColor);
                titleContainer.addView(tableTitleEndLine, new LinearLayout.LayoutParams(lineWidth, LinearLayout.LayoutParams.MATCH_PARENT));
            }

            LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) tableTitleLayout.getLayoutParams();
            titleLayoutParams.height = tableBuild.itemHeight;
            tableTitleLayout.setLayoutParams(titleLayoutParams);
            // 第一行表头


            // 第一列
            TableListAdapter<String> tableHeaderContainerAdapter = new TableListAdapter<String>(getContext(), R.layout.table_item) {

                @Override
                public void convert(Context context, TableListViewHolder helper, String item, int pos) {
                    TextView tableTitleItem = helper.getView(R.id.tv_table_title_item);
                    tableTitleItem.setGravity(Gravity.CENTER);
                    tableTitleItem.setEllipsize(tableBuild.itemTextEllipsize);
                    tableTitleItem.setTextColor(tableBuild.textColor);
                    tableTitleItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableBuild.textSize);
                    if (!TextUtils.isEmpty(item)) {
                        tableTitleItem.setText(item);
                    }
                    tableTitleItem.setBackgroundColor(tableRowColor(pos + 1, 0));

                    AbsListView.LayoutParams tableTitleItemParams = (AbsListView.LayoutParams) tableTitleItem.getLayoutParams();
                    tableTitleItemParams.width = getListTextMaxWidth(tableContent, tableBuild.textSize, 0);
                    tableTitleItemParams.height = tableBuild.itemHeight;
                    tableTitleItem.setLayoutParams(tableTitleItemParams);
                }
            };
            tableHeaderContainer.setAdapter(tableHeaderContainerAdapter);


            List<String> titleItemData = new ArrayList<>();
            for (int i = 1; i < tableContent.size(); i++) {
                titleItemData.add(tableContent.get(i).get(0));
            }
            tableHeaderContainerAdapter.addItemData(titleItemData, true);
            // 第一列
        }


        if (!tableBuild.tableHeaderFixed) {
            rowStart = 0;
        }

        String[] rowItemMergeIndex = null;
        if (!TextUtils.isEmpty(tableBuild.rowItemMergeIndex)) {
            rowItemMergeIndex = tableBuild.rowItemMergeIndex.split(";");
        }
        final String[] finalRowItemMergeIndex = rowItemMergeIndex;

        // 第一行向下，第一列向右
        TableListAdapter<List<String>> tableContentContainerAdapter = new TableListAdapter<List<String>>(getContext(), new TableListAdapter.CreateViewHolder() {

            @SuppressLint("ResourceType")
            @Override
            public ViewGroup createView(Context context, int rowPosition, ViewGroup parent) {
                int realRowPosition = rowPosition + rowStart;

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tableBuild.itemHeight));

                String[] itemIndex = null;
                if (finalRowItemMergeIndex != null) {
                    for (String rowItemMerge : finalRowItemMergeIndex) {
                        String[] rowIndex = rowItemMerge.split("\\|");// 列和行index分割
                        if (TextUtils.equals(String.valueOf(realRowPosition), rowIndex[0])) {
                            itemIndex = rowIndex[1].split(",");// 行index分割
                        }
                    }
                }

                int mergeTableNum = 0;
                int size = tableContent.get(realRowPosition).size();
                if (itemIndex != null) {
                    mergeTableNum = Integer.valueOf(itemIndex[1]) - Integer.valueOf(itemIndex[0]);
                    size += mergeTableNum;
                }

                int tableNum = lineStart;
                for (int realLinePosition = lineStart; realLinePosition < size; realLinePosition++) {
                    int lineColor, lineWidth;
                    if (realLinePosition == size - 1) {
                        lineColor = getResources().getColor(R.color.table_border);
                        lineWidth = 1;
                    } else {
                        lineColor = getResources().getColor(R.color.table_divide);
                        lineWidth = DensityUtils.dp2px(getContext(), 1);
                    }


                    int textWidth = getListTextMaxWidth(tableContent, tableBuild.textSize, realLinePosition);
                    int mergeItemType = 0;
                    if (itemIndex == null) {
                        // 没有合并表格设置
                    } else {
                        // 有合并表格设置
                        if (realLinePosition == Integer.valueOf(itemIndex[0])) {
                            // 达到需要合并表格设置的位置
                            mergeItemType = 1;

                            // TODO:如果合并数据的表格对应其他行相应数据长度不同 则这样计算宽度不对
                            textWidth *= mergeTableNum + 1;
                            textWidth += lineWidth * mergeTableNum + 1;
                        } else if (realLinePosition > Integer.valueOf(itemIndex[0]) && realLinePosition < Integer.valueOf(itemIndex[1])) {
                            // 达到需要合并表格设置的位置中间
                            mergeItemType = 2;
                        } else if (realLinePosition == Integer.valueOf(itemIndex[1])) {
                            mergeItemType = 3;
                        }
                        // 小于合并表格设置和大于合并表格设置则mergeItemType = 0;
                    }


                    if (mergeItemType == 0 || mergeItemType == 1) {
                        TextView tvTableContent = new TextView(context);
                        tvTableContent.setId(tableNum);
                        tvTableContent.setGravity(Gravity.CENTER);
                        tvTableContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableBuild.textSize);
                        tvTableContent.setTextColor(tableBuild.textColor);
                        tvTableContent.setSingleLine();
                        tvTableContent.setEllipsize(tableBuild.itemTextEllipsize);

                        LinearLayout.LayoutParams tvTableContentParams = new LinearLayout.LayoutParams(textWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        tvTableContentParams.gravity = Gravity.CENTER;
                        linearLayout.addView(tvTableContent, tvTableContentParams);

                        linearLayout.setBackgroundColor(tableRowColor(realRowPosition, realLinePosition));
                        tableNum++;
                    }

                    if (mergeItemType == 0 || mergeItemType == 3) {
                        View tvTableContentEndLine = new View(context);
                        tvTableContentEndLine.setBackgroundColor(lineColor);
                        linearLayout.addView(tvTableContentEndLine, new LinearLayout.LayoutParams(lineWidth, LinearLayout.LayoutParams.MATCH_PARENT));
                    }
                }
                return linearLayout;
            }
        }) {

            @Override
            public void convert(Context context, TableListViewHolder helper, List<String> item, int position) {
                ViewGroup viewGroup;
                if (helper.getConvertView() instanceof ViewGroup) {
                    viewGroup = (ViewGroup) helper.getConvertView();
                } else {
                    return;
                }

                for (int i = lineStart; i < item.size(); i++) {
                    @SuppressLint("ResourceType")
                    TextView tvTableContent = viewGroup.findViewById(i);

                    String text = item.get(i);
                    if (tvTableContent != null && !TextUtils.isEmpty(text)) {
                        tvTableContent.setText(text);
                    }
                }
            }
        };
        tableContentContainer.setAdapter(tableContentContainerAdapter);
        // 第一行向下，第一列向右
        List<List<String>> list = new ArrayList<>();
        for (int i = rowStart; i < tableContent.size(); i++) {
            list.add(tableContent.get(i));
        }
        tableContentContainerAdapter.addItemData(list, true);


        // 以下设置表格宽度为真实宽度
        Utils.getViewSize(TAG, titleContainer, new Utils.MeasureViewSizeCallback() {

            @Override
            public void sizeCallback(int width, int height) {
                int realWidth = (int) (width + itemWidth + getResources().getDimension(R.dimen.table_divide_line_width) + getResources().getDimension(R.dimen.table_border_line_width));

                int screenWidth = ScreenSizeUtils.getInstance(getContext()).getScreenWidth();
                if (realWidth > screenWidth) {
                    realWidth = screenWidth;
                }
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = realWidth;
                setLayoutParams(layoutParams);
            }
        });


        if (tableBuild.tableHeaderFixed) {
            tableContentHor.setScrollView(titleHor);
            titleHor.setScrollView(tableContentHor);
        }
    }

    private int tableRowColor(int rowPosition, int columnPosition) {
        if (tableBuild.rowHeaderBgColor != 0) {
            if (columnPosition == 0) {
                return 0;
            }
        }
        if (tableBuild.tableHeaderBgColor != 0) {
            if (rowPosition == 0) {
                return 0;
            }
        }


        if (tableBuild.itemBgPureColor == 0) {
            if (rowPosition % 2 == 0) {
                // 偶数行
                return tableBuild.itemBgIntervalColor2;
            } else {
                // 奇数行
                return tableBuild.itemBgIntervalColor1;
            }
        } else {
            return tableBuild.itemBgPureColor;
        }
    }

    /**
     * 返回每一列字符数据中最大宽度
     *
     * @param tableContent
     * @param listNo       列号，第一列从0开始
     * @return 单位px
     */
    private int getListTextMaxWidth(List<List<String>> tableContent, float textSize, int listNo) {
        if (tableBuild.itemWidth == 0) {
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

            return listMaxTextWidth + tableBuild.itemLeftRightMargin;
        } else {
            return tableBuild.itemWidth;
        }
    }

    private boolean tableContentQualified(List<List<String>> tableContent) {
        if (tableContent == null || tableContent.size() <= 0) {
            Log.e(TAG, "表数据为空");
            return false;
        }
        String[] rowItemMergeIndex = null;
        if (!TextUtils.isEmpty(tableBuild.rowItemMergeIndex)) {
            rowItemMergeIndex = tableBuild.rowItemMergeIndex.split(";");
            for (String rowItemMerge : rowItemMergeIndex) {
                String[] rowIndex = rowItemMerge.split("\\|");// 列和行index分割
                if (Integer.valueOf(rowIndex[0]) > tableContent.size()) {
                    Log.e(TAG, "表合并数据行坐标大于数据总行数");
                    return false;
                }

                String[] itemIndex = rowIndex[1].split(",");// 行index分割
                if (Integer.valueOf(itemIndex[0]) >= Integer.valueOf(itemIndex[1])) {
                    Log.e(TAG, "表合并数据列上坐标大或等于列下坐标");
                    return false;
                }
            }
        }

        int lastRowSize = 0;
        for (int i = 0; i < tableContent.size(); i++) {
            List<String> rowList = tableContent.get(i);
            if (rowList == null || rowList.size() <= 0) {
                Log.e(TAG, "第" + (i + 1) + "行表数据为空");
                return false;
            }

            boolean breakData = false;
            if (lastRowSize != 0 && lastRowSize != rowList.size()) {
                if (rowItemMergeIndex != null) {
                    for (String rowItemMerge : rowItemMergeIndex) {
                        String[] rowIndex = rowItemMerge.split("\\|");// 列和行index分割
                        if (Integer.valueOf(rowIndex[0]) == i) {
                            breakData = true;
                        } else {
                            Log.e(TAG, "第" + (i + 1) + "行表数据数量与其他行不同");
                            return false;
                        }
                    }
                }
            }
            if (!breakData) {
                lastRowSize = rowList.size();
            }
        }
        return true;
    }
}