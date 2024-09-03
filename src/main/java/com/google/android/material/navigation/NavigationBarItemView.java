package com.google.android.material.navigation;
import static java.lang.Math.max;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
import r.android.os.Build.VERSION;
import r.android.os.Build.VERSION_CODES;
import androidx.core.view.ViewCompat;
import androidx.appcompat.view.menu.MenuItemImpl;
import r.android.text.TextUtils;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.FrameLayout;
import r.android.widget.ImageView;
import r.android.widget.TextView;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
public abstract class NavigationBarItemView extends FrameLayout {
  private static final int INVALID_ITEM_POSITION=-1;
  private static final int[] CHECKED_STATE_SET={r.android.R.attr.state_checked};
  private int defaultMargin;
  private float shiftAmount;
  private float scaleUpFactor;
  private float scaleDownFactor;
  private int labelVisibilityMode;
  private boolean isShifting;
  private ImageView icon;
  private ViewGroup labelGroup;
  private TextView smallLabel;
  private TextView largeLabel;
  private int itemPosition=INVALID_ITEM_POSITION;
  private MenuItemImpl itemData;
  private Drawable originalIconDrawable;
  private BadgeDrawable badgeDrawable;
  public void initNavigationBarItemView( ){
    //super(context);
    View view = inflateView(getItemLayoutResId());//(context).inflate(getItemLayoutResId(),this,true);
    icon=view.findViewById(com.ashera.widget.IdGenerator.getId("@+id/navigation_bar_item_icon_view"));
    labelGroup=view.findViewById(com.ashera.widget.IdGenerator.getId("@+id/navigation_bar_item_labels_group"));
    smallLabel=view.findViewById(com.ashera.widget.IdGenerator.getId("@+id/navigation_bar_item_small_label_view"));
    largeLabel=view.findViewById(com.ashera.widget.IdGenerator.getId("@+id/navigation_bar_item_large_label_view"));
    //setBackgroundResource(getItemBackgroundResId());
    defaultMargin=(int) getItemDefaultMarginResId();//getResources().getDimensionPixelSize(getItemDefaultMarginResId());
    labelGroup.setTag(r.android.R.id.mtrl_view_tag_bottom_padding,labelGroup.getPaddingBottom());
    ViewCompat.setImportantForAccessibility(smallLabel,ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
    ViewCompat.setImportantForAccessibility(largeLabel,ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
    setFocusable(true);
    calculateTextScaleFactors(getTextSize(smallLabel),getTextSize(largeLabel));
    if (icon != null) {
      icon.addOnLayoutChangeListener(new OnLayoutChangeListener(){
        public void onLayoutChange(        View v,        int left,        int top,        int right,        int bottom,        int oldLeft,        int oldTop,        int oldRight,        int oldBottom){
          if (icon.getVisibility() == VISIBLE) {
            tryUpdateBadgeBounds(icon);
          }
        }
      }
);
    }
  }
  protected int getSuggestedMinimumWidth(){
    LayoutParams labelGroupParams=(LayoutParams)labelGroup.getLayoutParams();
    int labelWidth=labelGroupParams.leftMargin + labelGroup.getMeasuredWidth() + labelGroupParams.rightMargin;
    return max(getSuggestedIconWidth(),labelWidth);
  }
  protected int getSuggestedMinimumHeight(){
    LayoutParams labelGroupParams=(LayoutParams)labelGroup.getLayoutParams();
    return getSuggestedIconHeight() + labelGroupParams.topMargin + labelGroup.getMeasuredHeight()+ labelGroupParams.bottomMargin;
  }
  public void initialize(  MenuItemImpl itemData,  int menuType){
    this.itemData=itemData;
    setCheckable(itemData.isCheckable());
    setChecked(itemData.isChecked());
    setEnabled(itemData.isEnabled());
    setIcon(itemData.getIcon());
    setTitle(itemData.getTitle());
    setId(itemData.getItemId());
    if (!TextUtils.isEmpty("")) {
      //setContentDescription("");
    }
    CharSequence tooltipText=!TextUtils.isEmpty(itemData.getTooltipText()) ? itemData.getTooltipText() : itemData.getTitle();
    if (VERSION.SDK_INT < VERSION_CODES.LOLLIPOP || VERSION.SDK_INT > VERSION_CODES.M) {
      //setTooltipText(this,tooltipText);
    }
    setVisibility(itemData.isVisible() ? View.VISIBLE : View.GONE);
  }
  public void setItemPosition(  int position){
    itemPosition=position;
  }
  public int getItemPosition(){
    return itemPosition;
  }
  public void setShifting(  boolean shifting){
    if (isShifting != shifting) {
      isShifting=shifting;
      boolean initialized=itemData != null;
      if (initialized) {
        setChecked(itemData.isChecked());
      }
    }
  }
  public void setLabelVisibilityMode(  int mode){
    if (labelVisibilityMode != mode) {
      labelVisibilityMode=mode;
      boolean initialized=itemData != null;
      if (initialized) {
        setChecked(itemData.isChecked());
      }
    }
  }
  public MenuItemImpl getItemData(){
    return itemData;
  }
  public void setTitle(  CharSequence title){
    smallLabel.setMyAttribute("text",title);
    largeLabel.setMyAttribute("text",title);
    if (itemData == null || TextUtils.isEmpty("")) {
      //setContentDescription(title);
    }
    CharSequence tooltipText=itemData == null || TextUtils.isEmpty(itemData.getTooltipText()) ? title : itemData.getTooltipText();
    if (VERSION.SDK_INT < VERSION_CODES.LOLLIPOP || VERSION.SDK_INT > VERSION_CODES.M) {
      //setTooltipText(this,tooltipText);
    }
  }
  public void setCheckable(  boolean checkable){
    refreshDrawableState();
  }
  public void setChecked(  boolean checked){
    largeLabel.setPivotX(largeLabel.getWidth() / 2);
    largeLabel.setPivotY(largeLabel.getBaseline());
    smallLabel.setPivotX(smallLabel.getWidth() / 2);
    smallLabel.setPivotY(smallLabel.getBaseline());
switch (labelVisibilityMode) {
case NavigationBarView.LABEL_VISIBILITY_AUTO:
      if (isShifting) {
        if (checked) {
          setViewLayoutParams(icon,defaultMargin,Gravity.CENTER_HORIZONTAL | Gravity.TOP);
          updateViewPaddingBottom(labelGroup,(int)labelGroup.getTag(r.android.R.id.mtrl_view_tag_bottom_padding));
          largeLabel.setVisibility(VISIBLE);
        }
 else {
          setViewLayoutParams(icon,defaultMargin,Gravity.CENTER);
          updateViewPaddingBottom(labelGroup,0);
          largeLabel.setVisibility(INVISIBLE);
        }
        smallLabel.setVisibility(INVISIBLE);
      }
 else {
        updateViewPaddingBottom(labelGroup,(int)labelGroup.getTag(r.android.R.id.mtrl_view_tag_bottom_padding));
        if (checked) {
          setViewLayoutParams(icon,(int)(defaultMargin + shiftAmount),Gravity.CENTER_HORIZONTAL | Gravity.TOP);
          setViewScaleValues(largeLabel,1f,1f,VISIBLE);
          setViewScaleValues(smallLabel,scaleUpFactor,scaleUpFactor,INVISIBLE);
        }
 else {
          setViewLayoutParams(icon,defaultMargin,Gravity.CENTER_HORIZONTAL | Gravity.TOP);
          setViewScaleValues(largeLabel,scaleDownFactor,scaleDownFactor,INVISIBLE);
          setViewScaleValues(smallLabel,1f,1f,VISIBLE);
        }
      }
    break;
case NavigationBarView.LABEL_VISIBILITY_SELECTED:
  if (checked) {
    setViewLayoutParams(icon,defaultMargin,Gravity.CENTER_HORIZONTAL | Gravity.TOP);
    updateViewPaddingBottom(labelGroup,(int)labelGroup.getTag(r.android.R.id.mtrl_view_tag_bottom_padding));
    largeLabel.setVisibility(VISIBLE);
  }
 else {
    setViewLayoutParams(icon,defaultMargin,Gravity.CENTER);
    updateViewPaddingBottom(labelGroup,0);
    largeLabel.setVisibility(INVISIBLE);
  }
smallLabel.setVisibility(INVISIBLE);
break;
case NavigationBarView.LABEL_VISIBILITY_LABELED:
updateViewPaddingBottom(labelGroup,(int)labelGroup.getTag(r.android.R.id.mtrl_view_tag_bottom_padding));
if (checked) {
setViewLayoutParams(icon,(int)(defaultMargin + shiftAmount),Gravity.CENTER_HORIZONTAL | Gravity.TOP);
setViewScaleValues(largeLabel,1f,1f,VISIBLE);
setViewScaleValues(smallLabel,scaleUpFactor,scaleUpFactor,INVISIBLE);
}
 else {
setViewLayoutParams(icon,defaultMargin,Gravity.CENTER_HORIZONTAL | Gravity.TOP);
setViewScaleValues(largeLabel,scaleDownFactor,scaleDownFactor,INVISIBLE);
setViewScaleValues(smallLabel,1f,1f,VISIBLE);
}
break;
case NavigationBarView.LABEL_VISIBILITY_UNLABELED:
setViewLayoutParams(icon,defaultMargin,Gravity.CENTER);
largeLabel.setVisibility(GONE);
smallLabel.setVisibility(GONE);
break;
default :
break;
}
refreshDrawableState();
setSelected(checked);
}
private int getItemVisiblePosition(){
ViewGroup parent=(ViewGroup)getParent();
int index=parent.indexOfChild(this);
int visiblePosition=0;
for (int i=0; i < index; i++) {
View child=parent.getChildAt(i);
if (child instanceof NavigationBarItemView && child.getVisibility() == View.VISIBLE) {
visiblePosition++;
}
}
return visiblePosition;
}
private static void setViewLayoutParams(View view,int topMargin,int gravity){
LayoutParams viewParams=(LayoutParams)view.getLayoutParams();
viewParams.topMargin=topMargin;
viewParams.gravity=gravity;
view.setLayoutParams(viewParams);
}
private static void setViewScaleValues(View view,float scaleX,float scaleY,int visibility){
view.setScaleX(scaleX);
view.setScaleY(scaleY);
view.setVisibility(visibility);
}
private static void updateViewPaddingBottom(View view,int paddingBottom){
view.setPadding(view.getPaddingLeft(),view.getPaddingTop(),view.getPaddingRight(),paddingBottom);
}
public void setEnabled(boolean enabled){
super.setEnabled(enabled);
smallLabel.setEnabled(enabled);
largeLabel.setEnabled(enabled);
icon.setEnabled(enabled);
if (enabled) {
//(this,PointerIconCompat.getSystemIcon(getContext(),PointerIconCompat.TYPE_HAND));
}
 else {
//(this,null);
}
}
public int[] onCreateDrawableState(final int extraSpace){
final int[] drawableState=super.onCreateDrawableState(extraSpace + 1);
if (itemData != null && itemData.isCheckable() && itemData.isChecked()) {
mergeDrawableStates(drawableState,CHECKED_STATE_SET);
}
return drawableState;
}
public void setIcon(Drawable iconDrawable){
if (iconDrawable == originalIconDrawable) {
return;
}
originalIconDrawable=iconDrawable;
if (iconDrawable != null) {
//Drawable.ConstantState state=iconDrawable.getConstantState();
//iconDrawable=DrawableCompat.wrap(state == null ? iconDrawable : state.newDrawable()).mutate();
//wrappedIconDrawable=iconDrawable;
if (false) {
//DrawableCompat.setTintList(wrappedIconDrawable,iconTint);
}
}
this.icon.setMyAttribute("src",iconDrawable);
}
public void setIconSize(int iconSize){
LayoutParams iconParams=(LayoutParams)icon.getLayoutParams();
iconParams.width=iconSize;
iconParams.height=iconSize;
icon.setLayoutParams(iconParams);
}
public void setTextAppearanceInactive(String inactiveTextAppearance){
smallLabel.setMyAttribute("textAppearance", inactiveTextAppearance);//TextViewCompat.setTextAppearance(smallLabel,inactiveTextAppearance);
calculateTextScaleFactors(getTextSize(smallLabel),getTextSize(largeLabel));
}
public void setTextAppearanceActive(String activeTextAppearance){
largeLabel.setMyAttribute("textAppearance", activeTextAppearance);//TextViewCompat.setTextAppearance(largeLabel,activeTextAppearance);
calculateTextScaleFactors(getTextSize(smallLabel),getTextSize(largeLabel));
}
public void setTextColor(ColorStateList color){
if (color != null) {
smallLabel.setMyAttribute("textColor",color);
largeLabel.setMyAttribute("textColor",color);
}
}
private void calculateTextScaleFactors(float smallLabelSize,float largeLabelSize){
shiftAmount=smallLabelSize - largeLabelSize;
scaleUpFactor=1f * largeLabelSize / smallLabelSize;
scaleDownFactor=1f * smallLabelSize / largeLabelSize;
}
void setBadge(BadgeDrawable badgeDrawable){
this.badgeDrawable=badgeDrawable;
if (icon != null) {
tryAttachBadgeToAnchor(icon);
}
}
private boolean hasBadge(){
return badgeDrawable != null;
}
private void tryUpdateBadgeBounds(View anchorView){
if (!hasBadge()) {
return;
}
BadgeUtils.setBadgeDrawableBounds(badgeDrawable,anchorView,getCustomParentForBadge(anchorView));
}
private void tryAttachBadgeToAnchor(View anchorView){
if (!hasBadge()) {
return;
}
if (anchorView != null) {
setMyAttribute("clipChildren",false);
setClipToPadding(false);
BadgeUtils.attachBadgeDrawable(badgeDrawable,anchorView,getCustomParentForBadge(anchorView));
}
}
private FrameLayout getCustomParentForBadge(View anchorView){
if (anchorView == icon) {
return BadgeUtils.USE_COMPAT_PARENT ? ((FrameLayout)icon.getParent()) : null;
}
return null;
}
private int getSuggestedIconWidth(){
int badgeWidth=badgeDrawable == null ? 0 : badgeDrawable.getMinimumWidth() - badgeDrawable.getHorizontalOffset();
LayoutParams iconParams=(LayoutParams)icon.getLayoutParams();
return max(badgeWidth,iconParams.leftMargin) + icon.getMeasuredWidth() + max(badgeWidth,iconParams.rightMargin);
}
private int getSuggestedIconHeight(){
int badgeHeight=0;
if (badgeDrawable != null) {
badgeHeight=badgeDrawable.getMinimumHeight() / 2;
}
LayoutParams iconParams=(LayoutParams)icon.getLayoutParams();
return max(badgeHeight,iconParams.topMargin) + icon.getMeasuredWidth() + badgeHeight;
}
protected int getItemDefaultMarginResId(){
return (int) com.ashera.widget.PluginInvoker.convertDpToPixel("8dp");
}
protected abstract String getItemLayoutResId();
public void setItemBackground(Drawable background){
getChildAt(0).setMyAttribute("background",background);
}
public void setIconTintList(ColorStateList tint){
icon.setMyAttribute("tint",tint);
}
private int getTextSize(TextView label){
Number number=(Number)((com.ashera.widget.ILifeCycleDecorator)label).getWidget().getAttribute("textSize",true);
if (number != null) {
return number.intValue();
}
 else {
return 0;
}
}
@Override public void requestLayout(){
super.requestLayout();
if (icon != null) {
icon.requestLayout();
}
}
}
