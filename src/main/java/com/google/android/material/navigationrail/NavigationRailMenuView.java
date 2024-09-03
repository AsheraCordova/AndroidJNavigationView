package com.google.android.material.navigationrail;
import static r.android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static r.android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.material.navigationrail.NavigationRailView.DEFAULT_MENU_GRAVITY;
import static java.lang.Math.max;
import static java.lang.Math.min;
import r.android.content.Context;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
public class NavigationRailMenuView extends NavigationBarMenuView {
  private final FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);
  public void initNavigationRailMenuView(){
     
    layoutParams.gravity=DEFAULT_MENU_GRAVITY;
    setLayoutParams(layoutParams);
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    int maxHeight=MeasureSpec.getSize(heightMeasureSpec);
    int visibleCount=getMenu().getVisibleItems().size();
    int measuredHeight;
    if (visibleCount > 1 && isShifting(getLabelVisibilityMode(),visibleCount)) {
      measuredHeight=measureShiftingChildHeights(widthMeasureSpec,maxHeight,visibleCount);
    }
 else {
      measuredHeight=measureSharedChildHeights(widthMeasureSpec,maxHeight,visibleCount,null);
    }
    int parentWidth=MeasureSpec.getSize(widthMeasureSpec);
    setMeasuredDimension(View.resolveSizeAndState(parentWidth,widthMeasureSpec,0),View.resolveSizeAndState(measuredHeight,heightMeasureSpec,0));
  }
  protected void onLayout(  boolean changed,  int left,  int top,  int right,  int bottom){
    final int count=getChildCount();
    final int width=right - left;
    int used=0;
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() != GONE) {
        int childHeight=child.getMeasuredHeight();
        child.layout(0,used,width,childHeight + used);
        used+=childHeight;
      }
    }
  }
  protected NavigationBarItemView createNavigationBarItemView(  Context context){
    String name = NavigationRailItemView.class.getName();com.ashera.widget.IWidget widget= com.ashera.widget.WidgetFactory.createWidget(name, name, (com.ashera.widget.HasWidgets) ((com.ashera.widget.ILifeCycleDecorator) this).getWidget(), false);return (NavigationRailItemView) widget.asWidget();// new BottomNavigationMenuView();//    return new NavigationRailItemView();;
  }
  private int makeSharedHeightSpec(  int parentWidthSpec,  int maxHeight,  int shareCount){
    int maxAvailable=maxHeight / max(1,shareCount);
    return MeasureSpec.makeMeasureSpec(min(MeasureSpec.getSize(parentWidthSpec),maxAvailable),MeasureSpec.UNSPECIFIED);
  }
  private int measureShiftingChildHeights(  int widthMeasureSpec,  int maxHeight,  int shareCount){
    int selectedViewHeight=0;
    View selectedView=getChildAt(getSelectedItemPosition());
    if (selectedView != null) {
      int childHeightSpec=makeSharedHeightSpec(widthMeasureSpec,maxHeight,shareCount);
      selectedViewHeight=measureChildHeight(selectedView,widthMeasureSpec,childHeightSpec);
      maxHeight-=selectedViewHeight;
      --shareCount;
    }
    return selectedViewHeight + measureSharedChildHeights(widthMeasureSpec,maxHeight,shareCount,selectedView);
  }
  private int measureSharedChildHeights(  int widthMeasureSpec,  int maxHeight,  int shareCount,  View selectedView){
    int childHeightSpec=makeSharedHeightSpec(widthMeasureSpec,maxHeight,shareCount);
    if (selectedView == null) {
      childHeightSpec=makeSharedHeightSpec(widthMeasureSpec,maxHeight,shareCount);
    }
 else {
      childHeightSpec=MeasureSpec.makeMeasureSpec(selectedView.getMeasuredHeight(),MeasureSpec.UNSPECIFIED);
    }
    int childCount=getChildCount();
    int totalHeight=0;
    for (int i=0; i < childCount; i++) {
      final View child=getChildAt(i);
      if (child != selectedView) {
        totalHeight+=measureChildHeight(child,widthMeasureSpec,childHeightSpec);
      }
    }
    return totalHeight;
  }
  private int measureChildHeight(  View child,  int widthMeasureSpec,  int heightMeasureSpec){
    if (child.getVisibility() != GONE) {
      child.measure(widthMeasureSpec,heightMeasureSpec);
      return child.getMeasuredHeight();
    }
    return 0;
  }
  void setMenuGravity(  int gravity){
    if (layoutParams.gravity != gravity) {
      layoutParams.gravity=gravity;
      setLayoutParams(layoutParams);
    }
  }
  int getMenuGravity(){
    return layoutParams.gravity;
  }
  boolean isTopGravity(){
    return (layoutParams.gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP;
  }
}
