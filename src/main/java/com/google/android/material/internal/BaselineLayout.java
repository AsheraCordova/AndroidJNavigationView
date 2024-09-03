package com.google.android.material.internal;
import r.android.view.View;
import r.android.view.ViewGroup;
public class BaselineLayout extends ViewGroup {
  private int baseline=-1;
  private boolean measurePaddingFromBaseline;
  public void setMeasurePaddingFromBaseline(  boolean measurePaddingFromBaseline){
    this.measurePaddingFromBaseline=measurePaddingFromBaseline;
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    final int count=getChildCount();
    int maxWidth=0;
    int maxHeight=0;
    int maxChildBaseline=-1;
    int maxChildDescent=-1;
    int maxMeasuredHeight=0;
    int childState=0;
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() == GONE) {
        continue;
      }
      measureChild(child,widthMeasureSpec,heightMeasureSpec);
      maxMeasuredHeight=Math.max(maxMeasuredHeight,child.getMeasuredHeight());
      final int baseline=child.getBaseline();
      if (baseline != -1) {
        maxChildBaseline=Math.max(maxChildBaseline,baseline);
        maxChildDescent=Math.max(maxChildDescent,child.getMeasuredHeight() - baseline);
      }
      maxWidth=Math.max(maxWidth,child.getMeasuredWidth());
      maxHeight=Math.max(maxHeight,child.getMeasuredHeight());
      childState=View.combineMeasuredStates(childState,child.getMeasuredState());
    }
    if (maxChildBaseline != -1) {
      if (measurePaddingFromBaseline) {
        maxChildDescent=Math.max(maxChildDescent,getPaddingBottom());
        maxHeight=Math.max(maxHeight,maxChildBaseline + maxChildDescent);
      }
      this.baseline=maxChildBaseline;
    }
    maxHeight=Math.max(measurePaddingFromBaseline ? maxHeight : maxMeasuredHeight + getPaddingBottom(),getSuggestedMinimumHeight());
    maxWidth=Math.max(maxWidth,getSuggestedMinimumWidth());
    setMeasuredDimension(View.resolveSizeAndState(maxWidth,widthMeasureSpec,childState),View.resolveSizeAndState(maxHeight,heightMeasureSpec,childState << MEASURED_HEIGHT_STATE_SHIFT));
  }
  protected void onLayout(  boolean changed,  int left,  int top,  int right,  int bottom){
    final int count=getChildCount();
    final int parentLeft=getPaddingLeft();
    final int parentRight=right - left - getPaddingRight();
    final int parentContentWidth=parentRight - parentLeft;
    final int parentTop=getPaddingTop();
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() == GONE) {
        continue;
      }
      final int width=child.getMeasuredWidth();
      final int height=child.getMeasuredHeight();
      final int childLeft=parentLeft + (parentContentWidth - width) / 2;
      final int childTop;
      if (baseline != -1 && child.getBaseline() != -1) {
        childTop=parentTop + baseline - child.getBaseline();
      }
 else {
        childTop=parentTop;
      }
      child.layout(childLeft,childTop,childLeft + width,childTop + height);
    }
  }
  public int getBaseline(){
    return baseline;
  }
}
