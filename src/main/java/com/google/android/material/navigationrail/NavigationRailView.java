package com.google.android.material.navigationrail;
import static r.android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static java.lang.Math.min;
import r.android.content.Context;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarView;
public class NavigationRailView extends NavigationBarView {
  static final int DEFAULT_MENU_GRAVITY=Gravity.TOP | Gravity.CENTER_HORIZONTAL;
  static final int MAX_ITEM_COUNT=7;
  private static final int DEFAULT_HEADER_GRAVITY=Gravity.TOP | Gravity.CENTER_HORIZONTAL;
  private final int topMargin;
  private View headerView;
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    int minWidthSpec=makeMinWidthSpec(widthMeasureSpec);
    super.onMeasure(minWidthSpec,heightMeasureSpec);
    if (isHeaderViewVisible()) {
      int maxMenuHeight=getMeasuredHeight() - headerView.getMeasuredHeight() - topMargin;
      int menuHeightSpec=MeasureSpec.makeMeasureSpec(maxMenuHeight,MeasureSpec.AT_MOST);
      measureChild(getNavigationRailMenuView(),minWidthSpec,menuHeightSpec);
    }
  }
  protected void onLayout(  boolean changed,  int left,  int top,  int right,  int bottom){
    super.onLayout(changed,left,top,right,bottom);
    NavigationRailMenuView menuView=getNavigationRailMenuView();
    int offsetY=0;
    if (isHeaderViewVisible()) {
      int usedTop=headerView.getBottom() + topMargin;
      int menuTop=menuView.getTop();
      if (menuTop < usedTop) {
        offsetY=usedTop - menuTop;
      }
    }
 else     if (menuView.isTopGravity()) {
      offsetY=topMargin;
    }
    if (offsetY > 0) {
      menuView.layout(menuView.getLeft(),menuView.getTop() + offsetY,menuView.getRight(),menuView.getBottom() + offsetY);
    }
  }
  public void addHeaderView(  View headerView){
    removeHeaderView();
    this.headerView=headerView;
    FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
    params.gravity=DEFAULT_HEADER_GRAVITY;
    params.topMargin=topMargin;
    headerView.setLayoutParams(params);addView(headerView,0,params);
  }
  public View getHeaderView(){
    return headerView;
  }
  public void removeHeaderView(){
    if (headerView != null) {
      removeView(headerView);
      headerView=null;
    }
  }
  public void setMenuGravity(  int gravity){
    getNavigationRailMenuView().setMenuGravity(gravity);
  }
  public int getMenuGravity(){
    return getNavigationRailMenuView().getMenuGravity();
  }
  public int getMaxItemCount(){
    return MAX_ITEM_COUNT;
  }
  private NavigationRailMenuView getNavigationRailMenuView(){
    return (NavigationRailMenuView)getMenuView();
  }
  protected NavigationRailMenuView createNavigationBarMenuView(  Context context){
    String name = NavigationRailMenuView.class.getName();com.ashera.widget.IWidget widget= com.ashera.widget.WidgetFactory.createWidget(name, name, (com.ashera.widget.HasWidgets) ((com.ashera.widget.ILifeCycleDecorator) this).getWidget(), false);return (NavigationRailMenuView) widget.asWidget();//new NavigationRailMenuView();;
  }
  private int makeMinWidthSpec(  int measureSpec){
    int minWidth=getSuggestedMinimumWidth();
    if (MeasureSpec.getMode(measureSpec) != MeasureSpec.EXACTLY && minWidth > 0) {
      minWidth+=getPaddingLeft() + getPaddingRight();
      return MeasureSpec.makeMeasureSpec(min(MeasureSpec.getSize(measureSpec),minWidth),MeasureSpec.EXACTLY);
    }
    return measureSpec;
  }
  private boolean isHeaderViewVisible(){
    return headerView != null && headerView.getVisibility() != View.GONE;
  }
  public NavigationRailView(){
    topMargin=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("8dp");
  }
  @Override public void initNavigationBarView(){
    super.initNavigationBarView();
    setMenuGravity(DEFAULT_MENU_GRAVITY);
    setMinimumWidth((int)com.ashera.widget.PluginInvoker.convertDpToPixel("72dp"));
  }
}
