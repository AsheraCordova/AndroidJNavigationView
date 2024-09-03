package com.google.android.material.navigationrail;
import static java.lang.Math.max;
import r.android.content.Context;
import r.android.view.View;
import com.google.android.material.navigation.NavigationBarItemView;
public class  NavigationRailItemView extends NavigationBarItemView {
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
      int preferredHeight=MeasureSpec.getSize(heightMeasureSpec);
      int measuredHeight=getMeasuredHeight();
      int bestHeight=max(measuredHeight,preferredHeight);
      setMeasuredDimension(getMeasuredWidthAndState(),View.resolveSizeAndState(bestHeight,heightMeasureSpec,0));hackForDimensionOnFrameLayoutWrapper(bestHeight);
    }
  }
  protected String getItemLayoutResId(){
    return "@layout/design_navigation_rail_item_new";
  }
  protected int getItemDefaultMarginResId(){
    return (int) com.ashera.widget.PluginInvoker.convertDpToPixel("6dp");
  }
  private void hackForDimensionOnFrameLayoutWrapper(  int bestHeight){
    View navigationBarFlWrapper=findViewById(com.ashera.widget.IdGenerator.getId("@+id/navigation_bar_fl_wrapper"));
    navigationBarFlWrapper.setMinimumHeight(bestHeight);
    navigationBarFlWrapper.setMinimumWidth(com.ashera.widget.PluginInvoker.getScreenWidth());
  }
}
