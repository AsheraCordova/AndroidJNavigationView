package com.google.android.material.bottomnavigation;
import r.android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.appcompat.view.menu.MenuBuilder;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
public class BottomNavigationMenuView extends NavigationBarMenuView {
  private final int inactiveItemMaxWidth;
  private final int inactiveItemMinWidth;
  private final int activeItemMaxWidth;
  private final int activeItemMinWidth;
  private final int itemHeight;
  private boolean itemHorizontalTranslationEnabled;
  private int[] tempChildWidths;
  public BottomNavigationMenuView(){
    //super(context);
    FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    params.gravity=Gravity.CENTER;
    setLayoutParams(params);
    //final Resources res=getResources();
    inactiveItemMaxWidth=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("96dp");//res.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
    inactiveItemMinWidth=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("56dp");//res.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
    activeItemMaxWidth=(int)com.ashera.widget.PluginInvoker.getScreenWidth();//res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
    activeItemMinWidth=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("96dp");//res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
    itemHeight=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("56dp");//res.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
    tempChildWidths=new int[BottomNavigationView.MAX_ITEM_COUNT];
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    final MenuBuilder menu=getMenu();
    final int width=MeasureSpec.getSize(widthMeasureSpec);
    final int visibleCount=menu.getVisibleItems().size();
    final int totalCount=getChildCount();
    final int heightSpec=MeasureSpec.makeMeasureSpec(itemHeight,MeasureSpec.EXACTLY);
    if (isShifting(getLabelVisibilityMode(),visibleCount) && isItemHorizontalTranslationEnabled()) {
      final View activeChild=getChildAt(getSelectedItemPosition());
      int activeItemWidth=activeItemMinWidth;
      if (activeChild.getVisibility() != View.GONE) {
        activeChild.measure(MeasureSpec.makeMeasureSpec(activeItemMaxWidth,MeasureSpec.AT_MOST),heightSpec);
        activeItemWidth=Math.max(activeItemWidth,activeChild.getMeasuredWidth());
      }
      final int inactiveCount=visibleCount - (activeChild.getVisibility() != View.GONE ? 1 : 0);
      final int activeMaxAvailable=width - inactiveCount * inactiveItemMinWidth;
      final int activeWidth=Math.min(activeMaxAvailable,Math.min(activeItemWidth,activeItemMaxWidth));
      final int inactiveMaxAvailable=(width - activeWidth) / (inactiveCount == 0 ? 1 : inactiveCount);
      final int inactiveWidth=Math.min(inactiveMaxAvailable,inactiveItemMaxWidth);
      int extra=width - activeWidth - inactiveWidth * inactiveCount;
      for (int i=0; i < totalCount; i++) {
        if (getChildAt(i).getVisibility() != View.GONE) {
          tempChildWidths[i]=(i == getSelectedItemPosition()) ? activeWidth : inactiveWidth;
          if (extra > 0) {
            tempChildWidths[i]++;
            extra--;
          }
        }
 else {
          tempChildWidths[i]=0;
        }
      }
    }
 else {
      final int maxAvailable=width / (visibleCount == 0 ? 1 : visibleCount);
      final int childWidth=Math.min(maxAvailable,activeItemMaxWidth);
      int extra=width - childWidth * visibleCount;
      for (int i=0; i < totalCount; i++) {
        if (getChildAt(i).getVisibility() != View.GONE) {
          tempChildWidths[i]=childWidth;
          if (extra > 0) {
            tempChildWidths[i]++;
            extra--;
          }
        }
 else {
          tempChildWidths[i]=0;
        }
      }
    }
    int totalWidth=0;
    for (int i=0; i < totalCount; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() == GONE) {
        continue;
      }
      child.measure(MeasureSpec.makeMeasureSpec(tempChildWidths[i],MeasureSpec.EXACTLY),heightSpec);
      ViewGroup.LayoutParams params=child.getLayoutParams();
      params.width=child.getMeasuredWidth();
      totalWidth+=child.getMeasuredWidth();
    }
    setMeasuredDimension(View.resolveSizeAndState(totalWidth,MeasureSpec.makeMeasureSpec(totalWidth,MeasureSpec.EXACTLY),0),View.resolveSizeAndState(itemHeight,heightSpec,0));
  }
  protected void onLayout(  boolean changed,  int left,  int top,  int right,  int bottom){
    final int count=getChildCount();
    final int width=right - left;
    final int height=bottom - top;
    int used=0;
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() == GONE) {
        continue;
      }
      if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
        child.layout(width - used - child.getMeasuredWidth(),0,width - used,height);
      }
 else {
        child.layout(used,0,child.getMeasuredWidth() + used,height);
      }
      used+=child.getMeasuredWidth();
    }
  }
  public void setItemHorizontalTranslationEnabled(  boolean itemHorizontalTranslationEnabled){
    this.itemHorizontalTranslationEnabled=itemHorizontalTranslationEnabled;
  }
  public boolean isItemHorizontalTranslationEnabled(){
    return itemHorizontalTranslationEnabled;
  }
  protected NavigationBarItemView createNavigationBarItemView(  Context context){
    String name = BottomNavigationItemView.class.getName();com.ashera.widget.IWidget widget= com.ashera.widget.WidgetFactory.createWidget(name, name, (com.ashera.widget.HasWidgets) ((com.ashera.widget.ILifeCycleDecorator) this).getWidget(), false);return (BottomNavigationItemView) widget.asWidget();// new BottomNavigationMenuView();//    return new BottomNavigationItemView();;
  }
}
